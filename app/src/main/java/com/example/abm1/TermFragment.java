package com.example.abm1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abm1.adapters.TermAdapter;
import com.example.abm1.models.TermEntity;
import com.example.abm1.utilities.SampleData;

import java.util.ArrayList;
import java.util.List;

public class TermFragment extends Fragment {
private List<TermEntity> termData = new ArrayList<>();


    @Override
    public View onCreateView(

            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_term, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        termData.addAll(SampleData.getTerms());
        RecyclerView termRec = (RecyclerView) getActivity().findViewById(R.id.termRecView);
        TermAdapter tAdapter = new TermAdapter(termData);
        termRec.setAdapter(tAdapter);
        termRec.setLayoutManager(new LinearLayoutManager(getView().getContext()));


    }
}