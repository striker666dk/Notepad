package com.elvborn.notepad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText input;
    TextView text;
    ListView listView;
    DBHandler dbHandler;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Notes");

        input = (EditText)findViewById(R.id.input);
        text = (TextView)findViewById(R.id.text);
        listView = (ListView)findViewById(R.id.listView);
        dbHandler = new DBHandler(this, null, null, 1);

        intent = new Intent(this, SecondActivity.class);

        printNotes();
    }

    //Add button pressed
    public void addButtonClicked(View view){
        String inputText = input.getText().toString().trim();

        if(dbHandler.checkNote(inputText)) {
            Notes note = new Notes(inputText);
            dbHandler.addNote(note);

            printNotes();
        }
        else{
            input.setText("");
            input.setHint("Name already used");
        }
    }

    //Delete button pressed
    public void deleteButtonClicked(View view){
        String inputText = input.getText().toString().trim();
        dbHandler.deleteNote(inputText);

        printNotes();
    }

    public void printNotes(){
        List notes = dbHandler.getAllNotes();
        ListAdapter adapter = new ArrayAdapter<List>(this, android.R.layout.simple_list_item_1, notes);
        listView.setAdapter(adapter);
        input.setText("");

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String noteName = String.valueOf(parent.getItemAtPosition(position));

                        intent.putExtra("noteName", noteName);

                        startActivity(intent);
                    }
                }
        );
    }
}
