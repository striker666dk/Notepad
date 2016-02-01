package com.elvborn.notepad;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    EditText editText;
    Button addItemButton;
    ListView listView2;
    DBHandler dbHandler;

    String noteName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        editText = (EditText)findViewById(R.id.editText);
        addItemButton = (Button)findViewById(R.id.addItemButton);
        listView2 = (ListView)findViewById(R.id.listView2);
        dbHandler = new DBHandler(this, null, null, 1);

        Bundle noteData = getIntent().getExtras();
        noteName = noteData.getString("noteName");

        setTitle(noteName + ":");

        printListItems();
    }

    //Add item button clicked
    public void addItemButtonClicked(View view){
        String inputText = editText.getText().toString().trim();

        if(dbHandler.checkListItem(noteName, inputText)) {
            ListItem listItem = new ListItem(noteName, inputText, 0, 1);
            dbHandler.addListItem(listItem);
            printListItems();
        }else{
            editText.setText("");
            Toast.makeText(getApplicationContext(), "Item already in list", Toast.LENGTH_LONG).show();
        }
    }

    //Delete item button clicked
    public void deleteSelectedItems(View view){
        if(dbHandler.checkCheckboxes(noteName)) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Delete items");
            alert.setTitle(noteName);
            alert.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dbHandler.deleteSelectedItems(noteName);
                    printListItems();
                }
            });
            alert.setCancelable(true);
            alert.create().show();
        }

        Toast.makeText(getApplicationContext(), "No items selected", Toast.LENGTH_LONG).show();
    }

    //Prints a list of items
    public void printListItems(){
        ListItem[] listItems = dbHandler.getListItems(noteName);
        ListAdapter adapter = new CustomAdapter(this, listItems);
        listView2.setAdapter(adapter);
        editText.setText("");
    }
}