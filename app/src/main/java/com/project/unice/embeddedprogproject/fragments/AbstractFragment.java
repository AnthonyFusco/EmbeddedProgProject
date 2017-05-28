package com.project.unice.embeddedprogproject.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Abstraction of the creation of a fragment.
 * The construction of a fragment now only requires a layout and a title.
 */
public abstract class AbstractFragment extends Fragment implements IFragment {

    private String title;
    private int layout;

    public AbstractFragment() {
        super();
    }

    /**
     * Constructor for AbstractFragment.
     * @param title the title to be displayed
     * @param layout the layout to be used in the view
     */
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
