package android.prabhav.com.prolificlibrary.Fragments;

/**
 * Created by Prabhav on 06-12-2015.
 */

import android.app.Fragment;
        import android.content.Context;
        import android.content.Intent;
        import android.net.ConnectivityManager;
        import android.net.NetworkInfo;
        import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.support.v7.widget.Toolbar;
        import android.view.LayoutInflater;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ProgressBar;
        import android.widget.Toast;

        import android.prabhav.com.prolificlibrary.Activities.AddBookActivity;
        import android.prabhav.com.prolificlibrary.Adapters.RecyclerAdapter;
        import android.prabhav.com.prolificlibrary.AlertDialogFragments.DeleteAllDialog;
        import android.prabhav.com.prolificlibrary.AlertDialogFragments.NamePromptDialogue;
        import android.prabhav.com.prolificlibrary.Interfaces.retrofitInterface;
        import android.prabhav.com.prolificlibrary.Model.Book;
        import android.prabhav.com.prolificlibrary.R;

        import java.util.List;

        import retrofit.Callback;
        import retrofit.RestAdapter;
        import retrofit.RetrofitError;
        import retrofit.client.Response;


/**
 * A simple {@link Fragment} subclass.
 */

//This fragment puts up the card view list displaying all the books and their details.
public class BookListFragment extends Fragment {


    static List<Book> bookList;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    public static final String ENDPOINT = "http://prolific-interview.herokuapp.com/558056b2ab36750009fb4138/";
    public static final int REQUEST_NAME = 1;
    public static final int DELETE_REQUEST = 2;
    private static final String DELETE_ALL_DIALOG_TITLE = "Delete All Books!";
    private static final String DELETE_ALL_DIALOG_MESSAGE = "Are you sure you want to delete all the books?";
 private FloatingActionButton fab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_book_list, container, false);
        View fab= view.findViewById(R.id.fab_add);
        fab.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddBookActivity.class);
                startActivity(intent);
            }
        }));

        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        //Instantiating the RecyclerAdapter class to get the adapter object.
        adapter = new RecyclerAdapter(getActivity(), getActivity().getFragmentManager(), BookListFragment.this);
        recyclerView.setAdapter(adapter);

        //Assign the layout manager to the recycler view. In this app, recycler view list is vertical hence linear layout is used.
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        //Setting up toolbar as the action bar
        toolbar = (Toolbar) view.findViewById(R.id.app_bar_fragmentBookList);
        ((ActionBarActivity) getActivity()).setSupportActionBar(toolbar);
        getActivity().setTitle(null);


        return view;
    }


    //requestData method is called in onResume method to update the list of book every time user gets back to the application.
    @Override
    public void onResume() {
        super.onResume();

        if (isOnline())
            requestData();

        else {
            progressBar.setVisibility(ProgressBar.GONE);
            Toast.makeText(getActivity(), R.string.no_connection, Toast.LENGTH_LONG).show();
        }

    }


    //This method checks for Network Connection(both Wifi and mobile). Return false if device is not connected to any network.
    public boolean isOnline() {

        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean isWifiConn = networkInfo.isConnected();
        networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isMobileConn = networkInfo.isConnected();

        return (isWifiConn || isMobileConn);
    }


    //This method uses RestAdapter and retrofit Interface to fetch and parse the JSON file.
    // Progress bar becomes visible in the beginning of the method and is gone once the is done.
    public void requestData() {

        progressBar.setVisibility(ProgressBar.VISIBLE);

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .build();

        retrofitInterface retrofit = adapter.create(retrofitInterface.class);
        retrofit.getData(new Callback<List<Book>>() {

            @Override
            public void success(List<Book> books, Response response) {
                bookList = books;
                progressBar.setVisibility(ProgressBar.GONE);
                updateDisplay();
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                progressBar.setVisibility(ProgressBar.GONE);
                Toast.makeText(getActivity(), R.string.unable_to_fetch, Toast.LENGTH_LONG).show();
            }
        });

    }


    //Updates the recycler view list.
    public void updateDisplay() {

        adapter.refresh(bookList);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_book_list, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.fab_add:
                Intent intent = new Intent(getActivity(), AddBookActivity.class);
                startActivity(intent);
                return true;

            case R.id.delete_all_books:
                DeleteAllDialog dialog = new DeleteAllDialog().newInstance(DELETE_ALL_DIALOG_TITLE, DELETE_ALL_DIALOG_MESSAGE);
                dialog.setTargetFragment(BookListFragment.this, DELETE_REQUEST);
                dialog.show(getFragmentManager(), "delete all dialog");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != ActionBarActivity.RESULT_OK)
            return;

        if (requestCode == REQUEST_NAME) {
            if (!data.getStringExtra(NamePromptDialogue.NAME).isEmpty())    //checking if the name enetered by the user is empty or not
                adapter.checkOut(data.getIntExtra(NamePromptDialogue.POSITION, 1), data.getStringExtra(NamePromptDialogue.NAME));
        }

        if (requestCode == DELETE_REQUEST) {
            deleteAllBooks();
        }

    }


    //This method implements deleteAllBooks method of retrofit Interface.
    public void deleteAllBooks() {

        progressBar.setVisibility(ProgressBar.VISIBLE);

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .build();

        retrofitInterface retro = adapter.create(retrofitInterface.class);
        retro.deleteAllBooks(new Callback<Book>() {
            @Override
            public void success(Book book, Response response) {
                bookList.clear();
                updateDisplay();
                Toast.makeText(getActivity(), R.string.delete_success, Toast.LENGTH_LONG).show();
                progressBar.setVisibility(ProgressBar.GONE);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                progressBar.setVisibility(ProgressBar.GONE);
                Toast.makeText(getActivity(), R.string.delete_failed, Toast.LENGTH_LONG).show();
            }
        });
    }


    //This method takes a book object and tells whether that book exist on the server or not by comparing title of the book.
    public static boolean bookAlreadyExist(Book newBook) {

        for (int i = 0; i < bookList.size(); i++) {

            if (newBook.getTitle().equalsIgnoreCase(bookList.get(i).getTitle())) {
                return true;
            }
        }
        return false;
    }


}