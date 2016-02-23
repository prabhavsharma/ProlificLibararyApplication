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
import android.widget.ImageButton;
import android.widget.ProgressBar;

       import android.prabhav.com.prolificlibrary.AlertDialogFragments.AlertDialogFragment;
        import android.prabhav.com.prolificlibrary.Fragments.BookListFragment;
        import android.prabhav.com.prolificlibrary.Interfaces.retrofitInterface;
        import android.prabhav.com.prolificlibrary.Model.Book;
        import android.prabhav.com.prolificlibrary.R;

        import retrofit.Callback;
        import retrofit.RestAdapter;
        import retrofit.RetrofitError;
        import retrofit.client.Response;


public class AddBookActivity extends ActionBarActivity {

    private Toolbar mToolbar;
    private ImageButton mDoneButton;
    private Button mCancelButton;
    private Button mAddButton;
    private EditText mBookTitle;
    private EditText mAuthor;
    private EditText mPublisher;
    private EditText mCategories;
    private Book mNewBook;
    private ProgressBar progressBar;
    private static final String WARNING_DIALOG_TITLE = "Unsaved Changes!";
    private static final String WARNING_DIALOG_MESSAGE = "Do you wish to continue and Add a book!!";
    private static final String WARNING_DIALOG = "warning dialog";
    private static final String SUCCESS_DIALOG_TITLE = "Book Added Successfully!";
    private static final String SUCCESS_DIALOG_MESSAGE = "Do you want to add more books?";
    private static final String SUCCESS_DIALOG = "success dialog";
    private static final String FAILURE_DIALOG_TITLE = "Book Upload Failed!";
    private static final String FAILURE_DIALOG_MESSAGE = "Do you want to try again?";
    private static final String FAILURE_DIALOG = "failure dialog";
    private static final String BOOK_EXIST_DIALOG_TITLE = "Book Already Exist!";
    private static final String BOOK_EXIST_DIALOG_MESSAGE = "Do you wish to add more books?";
    private static final String BOOK_EXIST_DIALOG = "book exist dialog";
    private static final String EMPTY_FIELD_DIALOG_TITLE = "Complete all Fields!";
    private static final String EMPTY_FIELD_DIALOG_MESSAGE = "Do you Wish to Continue?";
    private static final String EMPTY_FIELD_DIALOG = "empty field dialog";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        //Setting up toolbar as the action bar
        mToolbar = (Toolbar) findViewById(R.id.app_bar_addBookActivity);
        setSupportActionBar(mToolbar);
        setTitle(null);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar_add_book);

        mNewBook = new Book();    //an object of book class is created to store all the attributes of the new book added by the user.


        //Initializing all the book attributes with an empty string.
        // This is then used to check if the user has added text to any of the field or not.
        mNewBook.setTitle("");
        mNewBook.setCategories("");
        mNewBook.setAuthor("");
        mNewBook.setPublisher("");


        //Listener is added to all the edit text box.

        mBookTitle = (EditText) findViewById(R.id.book_title_editText);
        mBookTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mNewBook.setTitle(String.valueOf(charSequence));     //title entered by the user is added in the book object.
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        mAuthor = (EditText) findViewById(R.id.author_editText);
        mAuthor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mNewBook.setAuthor(String.valueOf(charSequence));        //author entered by the user is added in the book object.
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        mPublisher = (EditText) findViewById(R.id.publisher_editText);
        mPublisher.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mNewBook.setPublisher(String.valueOf(charSequence));     //publisher entered by the user is added in the book object.
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        mCategories = (EditText) findViewById(R.id.categories_editText);
        mCategories.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mNewBook.setCategories(String.valueOf(charSequence));        //categories entered by the user is added in the book object.
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        mDoneButton = (ImageButton) findViewById(R.id.done_button_toolbar);
        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Checking if user has entered any data in any of the field.
                if (areFieldsClear()) {
                    finish();
                }
                //if any of the fields are not empty then a Dialog is presented on screen to confirm whether or not user really want to exit.
                else {
                    AlertDialogFragment.newInstance(WARNING_DIALOG_TITLE, WARNING_DIALOG_MESSAGE).show(getFragmentManager(), WARNING_DIALOG);
                }
            }
        });


        mCancelButton = (Button) findViewById(R.id.cancel_button_addBookActivity);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Checking if user has entered any data in any of the field.
                if (areFieldsClear()) {
                    finish();
                }
                //if any of the fields are not empty then a Dialog is presented on screen to confirm whether or not user really want to exit.
                else {
                    AlertDialogFragment.newInstance(WARNING_DIALOG_TITLE, WARNING_DIALOG_MESSAGE).show(getFragmentManager(), WARNING_DIALOG);
                }
            }
        });


        mAddButton = (Button) findViewById(R.id.add_button_addBookActivity);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (allFieldsFilled()) {        //checks if all the fields are completed

                    if (BookListFragment.bookAlreadyExist(mNewBook)) {       //checks if the book exist on the server or not

                        AlertDialogFragment.newInstance(BOOK_EXIST_DIALOG_TITLE, BOOK_EXIST_DIALOG_MESSAGE).show(getFragmentManager(), BOOK_EXIST_DIALOG);
                        clearEditTextBoxes();

                    } else {

                        postData();

                    }
                } else {

                    AlertDialogFragment.newInstance(EMPTY_FIELD_DIALOG_TITLE, EMPTY_FIELD_DIALOG_MESSAGE).show(getFragmentManager(), EMPTY_FIELD_DIALOG);

                }
            }
        });

    }


    //This method post a new book to the server by implementing addBook method of retrofit interface.
    public void postData() {

        progressBar.setVisibility(ProgressBar.VISIBLE);

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(BookListFragment.ENDPOINT)
                .build();

        retrofitInterface retrofit = adapter.create(retrofitInterface.class);
        retrofit.addBook(mNewBook, new Callback<Book>() {

            @Override
            public void success(Book book, Response response) {
                progressBar.setVisibility(ProgressBar.GONE);
                AlertDialogFragment.newInstance(SUCCESS_DIALOG_TITLE, SUCCESS_DIALOG_MESSAGE).show(getFragmentManager(), SUCCESS_DIALOG);
                clearEditTextBoxes();
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                progressBar.setVisibility(ProgressBar.GONE);
                AlertDialogFragment.newInstance(FAILURE_DIALOG_TITLE, FAILURE_DIALOG_MESSAGE).show(getFragmentManager(), FAILURE_DIALOG);
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


    //This method checks whether all the fields are empty or not.
    public boolean areFieldsClear() {

        return mNewBook.getTitle().equals("") && mNewBook.getAuthor().equals("") && mNewBook.getPublisher().equals("") && mNewBook.getCategories().equals("");
    }


    //Checks whether or not all the book attributes are provided by the user.
    public boolean allFieldsFilled() {
        return !(mNewBook.getTitle().equals("") || mNewBook.getAuthor().equals("") || mNewBook.getPublisher().equals("") || mNewBook.getCategories().equals(""));
    }


}