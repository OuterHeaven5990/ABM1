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

    private ArrayList<TermEntity> terms;
    private int selectedPos = RecyclerView.NO_POSITION;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ViewHolder(View v) {
            super(v);
            textView = (TextView) v.findViewById(R.id.term_text);
        }
    }

    public TermAdapter(ArrayList<TermEntity> terms) {
        this.terms = terms;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TermAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.term_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String term_title = terms.get(position).getText();
        holder.textView.setText(term_title);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return terms.size();
    }

}
