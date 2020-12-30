package com.example.myfirstapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button allBooks, currentBooks, readBooks, wishlist, favorites, about;
    private TextView developmentLicense, txtLibrary;
    private ImageView imgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        attachObjects();

        Utils.getInstance(this);
    }

    private void attachObjects() {
        allBooks = findViewById(R.id.btnAllBooks);
        currentBooks = findViewById(R.id.btnCurrentlyReading);
        readBooks = findViewById(R.id.btnAlreadyRead);
        wishlist = findViewById(R.id.btnWantToRead);
        favorites = findViewById(R.id.btnFavoriteBooks);
        about = findViewById(R.id.btnAbout);
        imgLogo = findViewById(R.id.imgLogo);
        developmentLicense = findViewById(R.id.txtLicense);
        txtLibrary = findViewById(R.id.txtLibrary);

        allBooks.setOnClickListener(this::onClick);
        readBooks.setOnClickListener(this);
        wishlist.setOnClickListener(this);
        currentBooks.setOnClickListener(this);
        favorites.setOnClickListener(this);
        about.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAllBooks:
                Intent intent = new Intent(MainActivity.this, AllBooksActivity.class);
                startActivity(intent);
                break;
            case R.id.btnAlreadyRead:
                Intent intent2 = new Intent(this, AlreadyReadBookActivity.class);
                startActivity(intent2);
                break;
            case R.id.btnWantToRead:
                System.out.println("working");
                Intent intent3 = new Intent(this, WishActivity.class);
                startActivity(intent3);
                break;
            case R.id.btnCurrentlyReading:
                System.out.println("working");
                Intent intent4 = new Intent(this, CurrentlyReadBooks.class);
                startActivity(intent4);
                break;
            case R.id.btnFavoriteBooks:
                Intent intent5 = new Intent(this, FavouriteBooks.class);
                startActivity(intent5);
                break;
            case R.id.btnAbout:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(getString(R.string.app_name));
                builder.setMessage("Designed and Developed with Innovation by Joe Bu\n"
                        + "Check my website for more cool applications:");
                builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setPositiveButton("Visit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO: SHOW MY WEBSITE
                        Intent intent1 = new Intent(MainActivity.this, WebsiteActivity.class);
                        intent1.putExtra("url", "https://www.google.com/");
                        startActivity(intent1);
                    }
                });
                builder.create().show();
                break;
            default:
                break;
        }
    }
}