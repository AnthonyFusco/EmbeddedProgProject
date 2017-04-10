package com.project.unice.embeddedprogproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.project.unice.embeddedprogproject.fragments.views.ListContacts;
import com.project.unice.embeddedprogproject.pages.Contact;

import java.util.List;

public class LayoutListAdapter<T> extends BaseAdapter {

    private List<T> listElements;
    private LayoutInflater inflater;
    private int layout;
    private ViewHolder holder;

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
        View view;
        view = inflater.inflate(layout, null);
        holder.configure(view);
        //holder.nom = (TextView) view.findViewById(R.id.textnamecontact);
        view.setTag(holder);
        holder.initialize(position);
        //holder.ref = position;
        //holder.nom.setText(((Contact)listElements.get(position)).name);
        return view;
    }

    public void setViewHolder(ViewHolder viewHolder) {
        this.holder = viewHolder;
    }
}