package com.example.abm1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abm1.R;
import com.example.abm1.models.TermEntity;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.ViewHolder> {

    /** Private variables for adapter */
    private List<TermEntity> mterms;
    public TextView termTextView;
    /****************************************************************************/

    /** Constructor **/

    public  TermAdapter(List<TermEntity> terms) {
        mterms = terms;
    }

    /****************************************************************************/

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View termView = inflater.inflate(R.layout.term_list_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(termView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TermAdapter.ViewHolder holder, int position) {
        TermEntity term = mterms.get(position);

        TextView textview = holder.termTextView;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView termTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            termTextView = (TextView) itemView.findViewById(R.id.term_text);
        }
    }

}
