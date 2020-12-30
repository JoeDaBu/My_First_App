package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {

    public static final String BOOK_ID_KEY = "bookId";

    private TextView txtPages, txtPageNum, txtAuthorName, txtBook, txtBookName, txtAuthorBlank, txtLongDesc2, txtLongDesc;
    private ImageView imgBook;
    private Button btnAddToCurrentlyReading, btnAddToWishlist, bthAddToAlreadyRead, btnAddToFavorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        init();

//        //Todo: get the data from the recycler view in here
//
//        Book book = new Book(1, "1Q84", "Haruki Murakami", 1350, "https://cdn.bulbagarden.net/upload/thumb/b/b8/059Arcanine.png/1200px-059Arcanine.png",
//                "A book", "A long desc book");

        Intent intent = getIntent();
        if (null != intent) {
            int bookId = intent.getIntExtra(BOOK_ID_KEY, -1);
            if (bookId != -1) {
                Book incomingBook = Utils.getInstance(this).getBookById(bookId);
                if (null != incomingBook) {
                    setData(incomingBook);

                    handleAlreadyRead(incomingBook);
                    handleWantToReadBooks(incomingBook);
                    handleCurrentlyReadingBooks(incomingBook);
                    handleFavoriteBooks(incomingBook);
                }
            }
        }
    }

    private void handleWantToReadBooks(final Book book) {
        ArrayList<Book> wishlistBooks = Utils.getInstance(this).getWishlistBooks();

        boolean existsInWishlistBooks = false;

        for (Book b : wishlistBooks) {
            if (b.getId() == book.getId()) {
                existsInWishlistBooks = true;
            }
        }
        if (existsInWishlistBooks) {
            btnAddToWishlist.setEnabled(false);
        } else {
            btnAddToWishlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.getInstance(BookActivity.this).addToWishlist(book)) {
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent (BookActivity.this, WishActivity.class);
                        startActivity(intent2);
                    } else {
                        Toast.makeText(BookActivity.this, "Something Wrong Happened, Try Again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void handleFavoriteBooks(final Book book) {
        ArrayList<Book> favoriteBooks = Utils.getInstance(this).getFavoriteBooks();

        boolean existsInFavoriteBooks = false;

        for (Book b: favoriteBooks) {
            if (b.getId() == book.getId()) {
                existsInFavoriteBooks = true;
            }
        }

        if (existsInFavoriteBooks) {
            btnAddToFavorites.setEnabled(false);
        } else {
            btnAddToFavorites.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.getInstance(BookActivity.this).addToFavoriteRead(book)) {
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this, FavouriteBooks.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Something Went Wrong, Please Try Again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void handleCurrentlyReadingBooks(final Book book) {
        ArrayList<Book> currentReadBooks = Utils.getInstance(this).getCurrentlyReadingBooks();

        boolean existsInCurrentBooks = false;

        for (Book b: currentReadBooks) {
            if (b.getId() == book.getId()) {
                existsInCurrentBooks = true;
            }
        }

        if (existsInCurrentBooks) {
            btnAddToCurrentlyReading.setEnabled(false);
        } else {
            btnAddToCurrentlyReading.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.getInstance(BookActivity.this).addToCurrentRead(book)) {
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this, CurrentlyReadBooks.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Something Went Wrong, try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /**
     * Enable and Disable button,
     * Add the book to Already Read Book ArrayList
     * @param book
     */
    private void handleAlreadyRead(final Book book) {
        ArrayList<Book> alreadyReadBooks = Utils.getInstance(this).getAlreadyReadBooks();

        boolean existsInAlreadyReadBooks = false;

        for (Book b : alreadyReadBooks) {
            if (b.getId() == book.getId()) {
                existsInAlreadyReadBooks = true;
            }
        }
        if (existsInAlreadyReadBooks) {
            bthAddToAlreadyRead.setEnabled(false);
        } else {
            bthAddToAlreadyRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.getInstance(BookActivity.this).addToAlreadyRead(book)) {
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
//                        finish();
//                        overridePendingTransition(0, 0);
//                        startActivity(getIntent());
//                        overridePendingTransition(0, 0);
                        //refreshes the page and cancels the refresh animation
                        Intent intent2 = new Intent (BookActivity.this, AlreadyReadBookActivity.class);
                        startActivity(intent2);
                    } else {
                        Toast.makeText(BookActivity.this, "Something Wrong Happened, Try Again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void setData(Book book) {
        txtBookName.setText(book.getName());
        txtAuthorName.setText(book.getAuthor());
        txtPageNum.setText(String.valueOf(book.getPages()));
        txtLongDesc2.setText(book.getLongDesc());
        Glide.with(this)
                .asBitmap().load(book.getImageUrl())
                .into(imgBook);
    }

    private void init() {
        txtBook  = findViewById(R.id.txtBook);
        txtBookName = findViewById(R.id.txtBookName);
        txtAuthorBlank = findViewById(R.id.txtAuthorBlank);
        txtAuthorName = findViewById(R.id.txtAuthorName);
        txtPages  = findViewById(R.id.txtPages);
        txtPageNum  = findViewById(R.id.txtPageNum);
        txtLongDesc  = findViewById(R.id.txtLongDesc);
        txtLongDesc2  = findViewById(R.id.txtLongDesc2);

        bthAddToAlreadyRead = findViewById(R.id.btnAddToAlreadyRead);
        btnAddToWishlist = findViewById(R.id.btnAddToWantToRead);
        btnAddToCurrentlyReading = findViewById(R.id.btnAddToCurrentlyReading);
        btnAddToFavorites = findViewById(R.id.btnAddToFavorites);

        imgBook = findViewById(R.id.imgBook);
    }
}