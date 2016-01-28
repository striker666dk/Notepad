package com.elvborn.notepad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

class CustomAdapter extends ArrayAdapter<String>{

    public CustomAdapter(Context context, String[] listItems) {
        super(context, R.layout.custom_row, listItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.custom_row, parent, false);

        String singleListItem = getItem(position);
        TextView listItemName = (TextView)view.findViewById(R.id.listItemName);
        CheckBox listItemCheckBox = (CheckBox)view.findViewById(R.id.listItemCkeckBox);

        listItemName.setText(singleListItem);

        return view;
    }
}
