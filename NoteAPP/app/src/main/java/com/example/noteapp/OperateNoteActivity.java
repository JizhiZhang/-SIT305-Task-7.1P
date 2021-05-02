package com.example.noteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noteapp.data.DatabaseHelper;
import com.example.noteapp.model.Note;
import com.example.noteapp.util.Util;

public class OperateNoteActivity extends AppCompatActivity {
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operate_note);

        EditText noteContentEditText = findViewById(R.id.noteContentEditText);
        Button updateButton = findViewById(R.id.updateButton);
        Button deleteButton = findViewById(R.id.deleteButton);
        db = new DatabaseHelper(this);

        Intent getContentIntent = getIntent();
        String noteContent = getContentIntent.getStringExtra(Util.DESCRIPTION);
        Integer noteID = getContentIntent.getIntExtra(Util.NOTE_ID,0);

        noteContentEditText.setText(noteContent);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = noteContentEditText.getText().toString();
                Integer id = noteID;

                int updateRow = db.updatePassword(new Note(id, content));
                if (updateRow > 0) {
                    Toast.makeText(OperateNoteActivity.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(OperateNoteActivity.this, "No row found", Toast.LENGTH_SHORT).show();
                }

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = noteContentEditText.getText().toString();
                Integer id = noteID;

                int deleteRow = db.deletePassword(new Note(id, content));
                if (deleteRow > 0) {
                    Toast.makeText(OperateNoteActivity.this, "Deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(OperateNoteActivity.this, "No row found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent= new Intent(this, ShowNotesActivity.class);
        startActivity(intent);
        finish();
    }
}