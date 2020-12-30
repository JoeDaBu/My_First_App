package com.example.myfirstapp;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class Utils {

    private SharedPreferences sharedPreferences;

    private static Utils instance;

    public static final String ALL_BOOKS_KEY = "allBooksKey";
    public static final String ALREADY_BOOKS_KEY = "alreadyBooksKey";
    public static final String CURRENT_BOOKS_KEY = "currentBooksKey";
    public static final String WISH_BOOKS_KEY = "wishBooksKey";
    public static final String FAVORITE_BOOKS_KEY = "favoriteBooksKey";

    public static final String ALL_BOOKS = "allBooks";
    public static final String ALREADY_BOOKS = "alreadyBooks";
    public static final String CURRENT_BOOKS = "currentBooks";
    public static final String WISH_BOOKS = "wishBooks";
    public static final String FAVORITE_BOOKS = "favoriteBooks";

//    private static ArrayList<Book> allBooks;
//    private static ArrayList<Book> alreadyReadBooks;
//    private static ArrayList<Book> currentlyReadingBooks;
//    private static ArrayList<Book> wishlistBooks;
//    private static ArrayList<Book> favoriteBooks;

    public Utils(Context context) {

        sharedPreferences = context.getSharedPreferences("alternate_database", Context.MODE_PRIVATE);

        if (null == getAllBooks()) {
            initData();
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        if (null == getAlreadyReadBooks()) {
            addToEditor(editor, gson, ALREADY_BOOKS_KEY);
        }

        if (null == getCurrentlyReadingBooks()) {
            addToEditor(editor, gson, CURRENT_BOOKS_KEY);
        }

        if (null == getWishlistBooks()) { 
            addToEditor(editor, gson, WISH_BOOKS_KEY);
        }

        if (null == getFavoriteBooks()) {
            addToEditor(editor, gson, FAVORITE_BOOKS_KEY);
        }
    }

    private void addToEditor(SharedPreferences.Editor editor, Gson gson, final String key) {
        editor.putString(key, gson.toJson(new ArrayList<Book>()));
        editor.commit();
    }

    private void initData() {
        //Todo: ADD INITIAL DATA

        ArrayList<Book> books = new ArrayList<>();

        books.add(new Book(1, "1Q84", "Haruki Murakami", 1350, "https://cdn.bulbagarden.net/upload/thumb/b/b8/059Arcanine.png/1200px-059Arcanine.png",
                "A book", "A long desc book"));
        books.add(new Book(2, "The Legend of Korra", "Azariah Fransico", 250, "https://cdn.spacetelescope.org/archives/images/wallpaper2/heic2007a.jpg",
                "A a another book", "Not a real book"));

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String text = gson.toJson(books);
        editor.putString(ALL_BOOKS_KEY, text);
        editor.commit();
    }

    public static synchronized Utils getInstance(Context context) {//note there is only one thread
        if (null == instance) {
            instance = new Utils(context);
        }
        return instance;
    }

    public ArrayList<Book> getAllBooks() {
        return getBooks(ALL_BOOKS_KEY);
    }

    public ArrayList<Book> getAlreadyReadBooks() {
        return getBooks(ALREADY_BOOKS_KEY);
    }

    private ArrayList<Book> getBooks(final String bookType) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>() {}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(bookType, null), type);
        return books;
    }

    public ArrayList<Book> getCurrentlyReadingBooks() {
        return getBooks(CURRENT_BOOKS_KEY);
    }

    public ArrayList<Book> getWishlistBooks() {
        return getBooks(WISH_BOOKS_KEY);
    }

    public ArrayList<Book> getFavoriteBooks() {
        return getBooks(FAVORITE_BOOKS_KEY);
    }

    public Book getBookById(int id) {
        ArrayList<Book> books = getAllBooks();
        if (null != books) {
            for (Book book : books) {
                if (book.getId() == id) {
                    return book;
                }
            }
        }
        return null;
    }

    public boolean addToAlreadyRead(Book book) {
        return addBookToSub(book, getAlreadyReadBooks(), ALREADY_BOOKS_KEY);
    }

    public boolean addToFavoriteRead(Book book) {
        return addBookToSub(book, getFavoriteBooks(), FAVORITE_BOOKS_KEY);
    }

    private boolean addBookToSub(Book book, ArrayList<Book> getBooks, String booksKey) {
        ArrayList<Book> books = getBooks;
        if (null != books) {
            if (books.add(book)) {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(booksKey);
                editor.putString(booksKey, gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean addToCurrentRead(Book book) {
        return addBookToSub(book, getCurrentlyReadingBooks(), CURRENT_BOOKS_KEY);
    }

    public boolean addToWishlist(Book book) {
        return addBookToSub(book, getWishlistBooks(), WISH_BOOKS_KEY);
    }

    private boolean removeBookFromSub(Book book, ArrayList<Book> getBooks, String booksKey) {
        ArrayList<Book> books = getBooks;
        if (null != books) {
            if (books.remove(book)) {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(booksKey);
                editor.putString(booksKey, gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean removeBook(Book book, String list) {
        if (list == ALREADY_BOOKS) {
            return removeBookFromSub(book, getAlreadyReadBooks(), ALREADY_BOOKS_KEY);
        } else if (list == FAVORITE_BOOKS) {
            return removeBookFromSub(book, getFavoriteBooks(), FAVORITE_BOOKS_KEY);
        } else if (list == WISH_BOOKS) {
            return removeBookFromSub(book, getWishlistBooks(), WISH_BOOKS_KEY);
        } else if (list == CURRENT_BOOKS) {
            return removeBookFromSub(book, getCurrentlyReadingBooks(), CURRENT_BOOKS_KEY);
        } else {
            return false;
        }
    }
}
