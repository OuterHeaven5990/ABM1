package com.example.abm1.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abm1.R;
import com.example.abm1.models.TermEntity;

import java.util.ArrayList;
import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.ViewHolder> {

    private ArrayList<TermEntity> mTerms;
    private int position;



    public TermAdapter(ArrayList<TermEntity> terms) {
        mTerms = terms;
    }


    @Override
    public TermAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.term_list_item, parent, false);
        ViewHolder viewholder = new ViewHolder(view);
        return viewholder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String term_text = mTerms.get(position).getText();
        holder.termTextView.setText(term_text);

    }


    @Override
    public int getItemCount() {
        return mTerms.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView termTextView;
        public ViewHolder(View view) {
            super(view);
            termTextView = (TextView) view.findViewById(R.id.term_text);
        }
    }

}
