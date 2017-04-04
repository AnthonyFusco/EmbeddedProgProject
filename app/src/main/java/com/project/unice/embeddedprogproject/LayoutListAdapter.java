package com.project.unice.embeddedprogproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class LayoutListAdapter<T> extends BaseAdapter {

    private List<T> listElements;
    private LayoutInflater inflater;
    private int layout;

    public void setLayout(int layout) {
        this.layout = layout;
    }

    public void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public void setListElements(List<T> listElements) {
        this.listElements = listElements;
    }

    @Override
    public int getCount() {
        if(listElements != null){
            return listElements.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return listElements.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return rowListView.getView(position, layout, inflater, listElements);
        return null;
    }
}