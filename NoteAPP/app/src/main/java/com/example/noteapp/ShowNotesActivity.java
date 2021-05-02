package com.example.noteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.noteapp.data.DatabaseHelper;
import com.example.noteapp.model.Note;
import com.example.noteapp.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShowNotesActivity extends AppCompatActivity {
    ListView noteListView;
    ArrayList<String> noteArrayList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notes);

        noteListView = findViewById(R.id.noteListView);
        noteArrayList = new ArrayList<>();

        DatabaseHelper db = new DatabaseHelper(ShowNotesActivity.this);

        List<Note> noteList = db.fetchAllNotes();

        for (Note note : noteList) {
            noteArrayList.add(note.getNote_desc());
        }

        adapter = new ArrayAdapter<>(this, R.layout.list_item, noteArrayList);
        noteListView.setAdapter(adapter);

        // OnItemClickListener
        // position is very important
        noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent operateIntent = new Intent(ShowNotesActivity.this, OperateNoteActivity.class);

                // Pass the position value of Item
                String text = (String) noteListView.getItemAtPosition(position);
                operateIntent.putExtra(Util.NOTE_ID, noteList.get(position).getNote_id());
                operateIntent.putExtra(Util.DESCRIPTION, noteList.get(position).getNote_desc());
                // Toast.makeText(ShowNotesActivity.this, text+position, Toast.LENGTH_SHORT).show();
                startActivityForResult(operateIntent, 1);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}