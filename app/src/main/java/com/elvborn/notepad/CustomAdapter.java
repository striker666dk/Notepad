package com.elvborn.notepad;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

class CustomAdapter extends ArrayAdapter<ListItem>{

    ListItem[] listItems;

    public CustomAdapter(Context context, ListItem[] _listItems) {
        super(context, R.layout.custom_row, _listItems);

        listItems = _listItems;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.custom_row, parent, false);

        final TextView listItemName = (TextView)view.findViewById(R.id.listItemName);
        listItemName.setText(listItems[position].get_listItemName());

        final CheckBox listItemCheckBox = (CheckBox)view.findViewById(R.id.listItemCkeckBox);
        if(listItems[position].get_checkbox() == 0)
            listItemCheckBox.setChecked(false);
        else
            listItemCheckBox.setChecked(true);

        return view;
    }
}
