package com.example.abm1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.abm1.R;
import com.example.abm1.models.NoteEntity;

import java.util.ArrayList;


public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private ArrayList<NoteEntity> mNotes;
    private int position;
    private final Context mContext;


    public NoteAdapter(ArrayList<NoteEntity> notes, Context context) {
        mNotes = notes;
        this.mContext = context;
    }

    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_list_item, parent, false);
        NoteAdapter.ViewHolder viewholder = new NoteAdapter.ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(NoteAdapter.ViewHolder holder, final int position) {
        final String note_text = mNotes.get(position).getNoteText();
        final Integer note_id = mNotes.get(position).getId();
        holder.noteTextView.setText(note_text);
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView noteTextView;
        public ViewHolder(View view) {
            super(view);
            noteTextView = (TextView) view.findViewById(R.id.note_text);
        }
    }

}
