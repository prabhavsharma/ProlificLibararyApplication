package android.prabhav.com.prolificlibrary.Activities;

/**
 * Created by Prabhav on 06-12-2015.
 */
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import android.prabhav.com.prolificlibrary.Adapters.RecyclerAdapter;
import android.prabhav.com.prolificlibrary.AlertDialogFragments.AlertDialogFragment;
import android.prabhav.com.prolificlibrary.Fragments.BookListFragment;
import android.prabhav.com.prolificlibrary.Interfaces.retrofitInterface;
import android.prabhav.com.prolificlibrary.Model.Book;
import android.prabhav.com.prolificlibrary.R;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class UpdateBookActivity extends ActionBarActivity {

    private Toolbar mToolbar;
    private Button mBackButton;
    private Button mCancelButton;
    private Button mUpdateButton;
    private EditText mBookTitle;
    private EditText mAuthor;
    private EditText mPublisher;
    private EditText mCategories;
    private Book mNewBook;
    private int position;
    List<Book> bookList;
    private Book selectedBook;
    private ProgressBar progressBar;
    private static final String WARNING_DIALOG_TITLE = "Unsaved Changes!";
    private static final String WARNING_DIALOG_MESSAGE = "wish to continue and update a book?";
    private static final String WARNING_DIALOG = "warning dialog";
    private static final String FAILURE_DIALOG_TITLE = "Book Update Failed!";
    private static final String FAILURE_DIALOG_MESSAGE = "Do you want to try again?";
    private static final String FAILURE_DIALOG = "failure dialog";
    private static final String BOOK_EXIST_DIALOG_TITLE = "Book Already Exist!";
    private static final String BOOK_EXIST_DIALOG_MESSAGE = "Do you want to make any update?";
    private static final String BOOK_EXIST_DIALOG = "book exist dialog";
    private static final String EMPTY_FIELD_DIALOG_TITLE = "All Fields are mandatory";
    private static final String EMPTY_FIELD_DIALOG_MESSAGE = "Do you Wish to Continue?";
    private static final String EMPTY_FIELD_DIALOG = "empty field dialog";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book);

        position = getIntent().getIntExtra(RecyclerAdapter.POSITION, 1);
        this.bookList = RecyclerAdapter.bookList;
        selectedBook = RecyclerAdapter.sSelectedBook;


        //Setting up toolbar as the action bar
        mToolbar = (Toolbar) findViewById(R.id.app_bar_updateBookActivity);
        setSupportActionBar(mToolbar);
        setTitle(null);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar_update_book);

        mNewBook = new Book();


        //Initializing all the book attributes with an empty string.
        // This is then used to check if the user has added text to any of the field or not.
        mNewBook.setTitle("");
        mNewBook.setCategories("");
        mNewBook.setAuthor("");
        mNewBook.setPublisher("");


        //Listener is added to all the edit text box.

        mBookTitle = (EditText) findViewById(R.id.book_title_editText_update);
        mBookTitle.setText(selectedBook.getTitle());
        mBookTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mNewBook.setTitle(String.valueOf(charSequence));     //title entered by the user is updated in the book object.
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        mAuthor = (EditText) findViewById(R.id.author_editText_update);
        mAuthor.setText(selectedBook.getAuthor());
        mAuthor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mNewBook.setAuthor(String.valueOf(charSequence));        //author entered by the user is updated in the book object.
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        mPublisher = (EditText) findViewById(R.id.publisher_editText_update);
        mPublisher.setText(selectedBook.getPublisher());
        mPublisher.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mNewBook.setPublisher(String.valueOf(charSequence));     //publisher entered by the user is updated in the book object.
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        mCategories = (EditText) findViewById(R.id.categories_editText_update);
        mCategories.setText(selectedBook.getCategories());
        mCategories.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mNewBook.setCategories(String.valueOf(charSequence));        //categories entered by the user is updated in the book object.
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        mBackButton = (Button) findViewById(R.id.back_button_toolbar);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Checking if user has entered any data in any of the field.
                if (allFieldsSame()) {
                    finish();
                }
                //if any of the fields are not empty then a Dialog is presented on screen to confirm whether or not user really want to exit.
                else {
                    AlertDialogFragment.newInstance(WARNING_DIALOG_TITLE, WARNING_DIALOG_MESSAGE).show(getFragmentManager(), WARNING_DIALOG);
                }
            }
        });


        mCancelButton = (Button) findViewById(R.id.cancel_button_updateBookActivity);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Checking if user has entered any data in any of the field.
                if (allFieldsSame()) {
                    finish();
                }
                //if any of the fields are not empty then a Dialog is presented on screen to confirm whether or not user really want to exit.
                else {
                    AlertDialogFragment.newInstance(WARNING_DIALOG_TITLE, WARNING_DIALOG_MESSAGE).show(getFragmentManager(), WARNING_DIALOG);
                }
            }
        });


        mUpdateButton = (Button) findViewById(R.id.add_button_updateBookActivity);
        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (allFieldsFilled()) {

                    if (!allFieldsSame()) {

                        if (bookAlreadyExist()) {       //checks if the book exist on the server or not

                            AlertDialogFragment.newInstance(BOOK_EXIST_DIALOG_TITLE, BOOK_EXIST_DIALOG_MESSAGE).show(getFragmentManager(), BOOK_EXIST_DIALOG);
                            clearEditTextBoxes();

                        } else {
                            updateData();
                        }
                    } else {
                        AlertDialogFragment.newInstance(BOOK_EXIST_DIALOG_TITLE, BOOK_EXIST_DIALOG_MESSAGE).show(getFragmentManager(), BOOK_EXIST_DIALOG);
                    }
                } else {
                    AlertDialogFragment.newInstance(EMPTY_FIELD_DIALOG_TITLE, EMPTY_FIELD_DIALOG_MESSAGE).show(getFragmentManager(), EMPTY_FIELD_DIALOG);
                }
            }
        });

    }


    //Implements retrofit method and updates a book.
    public void updateData() {

        completeNewBookAttributes();
        progressBar.setVisibility(ProgressBar.VISIBLE);

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(BookListFragment.ENDPOINT)
                .build();

        retrofitInterface retrofit = adapter.create(retrofitInterface.class);
        retrofit.updateBook(selectedBook.getId(), mNewBook, new Callback<Book>() {
            @Override
            public void success(Book book, Response response) {
                progressBar.setVisibility(ProgressBar.GONE);
                Toast.makeText(UpdateBookActivity.this, R.string.update_book_success, Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                progressBar.setVisibility(ProgressBar.GONE);
                Toast.makeText(UpdateBookActivity.this, R.string.update_book_failed, Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }


    //This method clear each edit text box.
    public void clearEditTextBoxes() {
        mBookTitle.setText("");
        mAuthor.setText("");
        mPublisher.setText("");
        mCategories.setText("");
    }


    //Checks whether or not all the book attributes are provided by the user.
    public boolean allFieldsFilled() {
        return !(mBookTitle.getText().toString().equals("") || mAuthor.getText().toString().equals("") || mPublisher.getText().toString().equals("") || mCategories.getText().toString().equals(""));
    }


    //Checks if the new values of atleast one field is different.
    public boolean allFieldsSame() {
        return (mNewBook.getTitle().equals(selectedBook.getTitle()) && mNewBook.getAuthor().equals(selectedBook.getAuthor()) && mNewBook.getPublisher().equals(selectedBook.getPublisher()) && mNewBook.getCategories().equals(selectedBook.getCategories()));
    }


    //This method checks if the updated book already exist or not.
    private boolean bookAlreadyExist() {
        RecyclerAdapter.bookList.remove(selectedBook);
        boolean answer = false;
        for (int i = 0; i < RecyclerAdapter.bookList.size(); i++) {

            if (RecyclerAdapter.bookList.get(i).getTitle().equalsIgnoreCase(String.valueOf(mBookTitle.getText()))) {
                answer = true;
            }
        }
        RecyclerAdapter.bookList.add(selectedBook);

        return answer;
    }


    //Fields which are not edited by the user are assigned the old values.
    public void completeNewBookAttributes() {
        if (mNewBook.getTitle().equals(""))
            mNewBook.setTitle(selectedBook.getTitle());

        if (mNewBook.getAuthor().equals(""))
            mNewBook.setAuthor(selectedBook.getAuthor());

        if (mNewBook.getPublisher().equals(""))
            mNewBook.setPublisher(selectedBook.getPublisher());

        if (mNewBook.getCategories().equals(""))
            mNewBook.setCategories(selectedBook.getCategories());
    }


}