package com.project.unice.embeddedprogproject.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class AbstractFragment extends Fragment implements IFragment {

    private String title;
    private int layout;


    public AbstractFragment(String title, int layout) {
        this.title = title;
        this.layout = layout;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(layout, container, false);
        onCreateFragment(rootView, savedInstanceState);
        return rootView;
    }

    protected abstract void onCreateFragment(ViewGroup rootView, Bundle savedInstanceState);
}
