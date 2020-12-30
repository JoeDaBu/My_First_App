package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class CurrentlyReadBooks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Test", "CurrentReadBookActivity: Started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currently_read_books);

        RecyclerView recyclerView = findViewById(R.id.currentBookRecView);
        BookRecViewAdapter adapter = new BookRecViewAdapter(this, Utils.CURRENT_BOOKS);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setBooks(Utils.getInstance(this).getCurrentlyReadingBooks());

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}