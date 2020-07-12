package com.example.abm1.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.abm1.EditTermActivity;
import com.example.abm1.R;
import com.example.abm1.ViewTermActivity;
import com.example.abm1.models.TermEntity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.ViewHolder> {

    private ArrayList<TermEntity> mTerms;
    private int position;
    private final Context mContext;



    public TermAdapter(ArrayList<TermEntity> terms, Context context) {
        mTerms = terms;
        this.mContext = context;
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
        final String term_text = mTerms.get(position).getTermTitle();
        final Integer term_id = mTerms.get(position).getId();
        holder.termTextView.setText(term_text);



        holder.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, EditTermActivity.class);
                intent.putExtra("Term_ID", term_id);
                mContext.startActivity(intent);
            }
        });

        holder.termTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ViewTermActivity.class);
                intent.putExtra("Term_ID", term_id );
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mTerms.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView termTextView;
        private FloatingActionButton fab;
        public ViewHolder(View view) {
            super(view);
            termTextView = (TextView) view.findViewById(R.id.term_text);
            fab = (FloatingActionButton) view.findViewById(R.id.fab);
        }
    }

}
