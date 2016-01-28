package com.elvborn.notepad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

//TODO
/*
Save the checkbox state
Add delete by checkbox
*/

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

        if(dbHandler.checkListItem(inputText)) {
            dbHandler.addListItem(noteName, inputText);
            printListItems();
        }else{
            editText.setText("");
            editText.setHint("Item already in list");
        }
    }

    //Delete item button clicked
    public void deleteItemButtonClicked(View view){
        String inputText = editText.getText().toString().trim();
        dbHandler.deleteListItem(inputText);
        printListItems();
    }

    //Prints a list of items
    public void printListItems(){
        List listItems = dbHandler.getListItems(noteName);

        //Convert listItems from list to array
        String[] listItemsArray = new String[listItems.size()];
        for(int i=0; i < listItems.size(); i++){
            listItemsArray[i] = listItems.get(i).toString();
        }

        ListAdapter adapter = new CustomAdapter(this, listItemsArray);
        listView2.setAdapter(adapter);
        editText.setText("");
    }
}