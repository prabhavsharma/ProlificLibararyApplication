package android.prabhav.com.prolificlibrary.Activities;

/**
 * Created by Prabhav on 06-12-2015.
 */
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import android.prabhav.com.prolificlibrary.Fragments.BookListFragment;
import android.prabhav.com.prolificlibrary.R;

//This activity acts as a container for the BookListFragment.

public class ListBookActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_container);

        FragmentManager manager = getFragmentManager();         //getting the instance of fragment manager.
        Fragment fragment = manager.findFragmentById(R.id.fragment_container);    //fetching the fragment from the fragment_container layout


        //Now if the fetched fragment is null then create an instance of the BookListFragment and
        // attach it to fragment_container layout with the help of fragment manager.

        if (fragment == null) {
            fragment = new BookListFragment();
            manager.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

}
