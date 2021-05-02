package com.example.noteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.noteapp.data.DatabaseHelper;
import com.example.noteapp.model.Note;

public class NewNoteActivity extends AppCompatActivity {
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        EditText noteDescriptionEditText = findViewById(R.id.noteDescriptionEditText);
        Button saveButton = findViewById(R.id.saveButton);
        db = new DatabaseHelper(this);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = noteDescriptionEditText.getText().toString();

                if (description.length() != 0) {
                    long result = db.insertNote(new Note(description));
                    if (result > 0) {
                        Toast.makeText(NewNoteActivity.this, "Created successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(NewNoteActivity.this, "Creation error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(NewNoteActivity.this, "The note can not be empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent= new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}