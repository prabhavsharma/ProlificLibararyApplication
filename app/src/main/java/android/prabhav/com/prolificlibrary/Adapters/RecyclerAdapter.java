package android.prabhav.com.prolificlibrary.Adapters;

/**
 * Created by Prabhav on 06-12-2015.
 */
import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import android.prabhav.com.prolificlibrary.Activities.UpdateBookActivity;
import android.prabhav.com.prolificlibrary.AlertDialogFragments.NamePromptDialogue;
import android.prabhav.com.prolificlibrary.Fragments.BookListFragment;
import android.prabhav.com.prolificlibrary.Interfaces.retrofitInterface;
import android.prabhav.com.prolificlibrary.Model.Book;
import android.prabhav.com.prolificlibrary.R;

import java.util.Collections;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


//This class creates an adapter for Recycler view.





//This class creates an adapter for Recycler view.



//This class creates an adapter for Recycler view.
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    LayoutInflater inflater;
    List<Book> books = Collections.emptyList();
    public static Book sSelectedBook;
    public static List<Book> bookList;
    private Context mContext;
    private BookListFragment fragment_instance;
    private FragmentManager fragmentManager;
    public static final String POSITION = "position";


    /*Constructor is used to get the activity context from the calling activity and
     to get an instance of Layout Inflater which will be used to inflate the layout of the
    single item(i.e a card which displays the information of a book) in the recycler view list. */
    public RecyclerAdapter(Context context, FragmentManager fm, BookListFragment fragment) {
        mContext = context;
        this.fragmentManager = fm;
        this.fragment_instance = fragment;
        inflater = LayoutInflater.from(context);
    }


    //This method helps in updating the Recycler view list. Upon calling, this method updates the list of books in the recycler view.
    public void refresh(List<Book> books) {
        this.books = books;
    }


    //Every time a new row in recycler view list to be created this method is gonna be called.
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.custom_row_recycler, parent, false);      //Inflates the view for single item in the recycler view.

        return new MyViewHolder(view);       //Inflated view is assigned to the view holder, which is then returned and passed to onBindViewHolder method.
    }


    //This method updates View holder data to represent the content of the item at the given position in the data set.
    //and hence boost up the performance by not inflating view for each row.
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Book current = books.get(position);     //get the current book object.

        //Set the data of each child views.
        holder.title.setText(current.getTitle());
        holder.author.setText("- " + current.getAuthor());
        holder.details.setText("");

        //Checking each attribute before putting up in tbe view.
        if (current.getPublisher() != null) {
            holder.details.append("PUBLISHER - ");
            holder.details.append(current.getPublisher());
            holder.details.append("\n");
        }
        if (current.getCategories() != null) {
            holder.details.append("CATEGORIES - ");
            holder.details.append(current.getCategories());
            holder.details.append("\n");
        }
        if (current.getLastCheckedOutBy() != null) {
            holder.details.append("Last Checked out by - ");
            holder.details.append(current.getLastCheckedOutBy());
        }
        if (current.getLastCheckedOut() != null) {
            holder.details.append("@");
            holder.details.append(current.getLastCheckedOut());
        }

        setAnimation(holder.itemView);      //Calling setAnimation method, passing each item of the recycler view.
    }


    //This method keeps track of the number of books available in the book list.
    @Override
    public int getItemCount() {
        return books.size();
    }


    //Flip animation is applied to the view which is passed in this method.
    private void setAnimation(View view) {

        @SuppressWarnings("ResourceType") Animator animator = AnimatorInflater.loadAnimator(mContext, R.anim.flip);
        animator.setTarget(view);
        animator.start();
    }


    //Constructor of this class receives the view of a single item in the recycler view.
    // From which we can extract all the children of that view(i.e all the views which comprise the view of the single item in the recycler view ).
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView details;
        TextView author;
        ImageButton mCheckoutButton;
        ImageButton mDeleteBook;
        ImageButton mShareButton;
        ImageButton mEditButton;
        public ProgressBar progressBar;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title_recycler);
            author = (TextView) itemView.findViewById(R.id.author_recycler);
            details = (TextView) itemView.findViewById(R.id.detail_recycler);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar_recycler);

            //On clicking delete button, progress bar will become visible and
            // deleteABook method will be called with current position as an argument. Once done with the method, progress bar will be gone.
            mDeleteBook = (ImageButton) itemView.findViewById(R.id.delete_book);
            mDeleteBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    progressBar.setVisibility(ProgressBar.VISIBLE);
                    deleteABook(getPosition());
                    progressBar.setVisibility(ProgressBar.GONE);
                }
            });


            mEditButton = (ImageButton) itemView.findViewById(R.id.edit_book);
            mEditButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    sSelectedBook = books.get(getPosition());
                    bookList = books;
                    Intent intent = new Intent(mContext, UpdateBookActivity.class);
                    intent.putExtra(POSITION, getPosition());
                    mContext.startActivity(intent);
                }
            });


            //On clicking checkout button, a dialog pops up and ask users name and then call update method to update the same to the server.
            mCheckoutButton = (ImageButton) itemView.findViewById(R.id.checkout);
            mCheckoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    NamePromptDialogue dialog = NamePromptDialogue.newInstance(getPosition());
                    dialog.setTargetFragment(fragment_instance, 0);
                    dialog.show(fragmentManager, "enter name dialog");
                }
            });


            mShareButton = (ImageButton) itemView.findViewById(R.id.share_button);
            mShareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String text = "Book--> " + books.get(getPosition()).getTitle() + " \n" + "Author--> " + books.get(getPosition()).getAuthor();
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);

                    //Checking that atleast one app is available on the device that can perform this task.
                    if (sharingIntent.resolveActivity(mContext.getPackageManager()) != null)
                        mContext.startActivity(Intent.createChooser(sharingIntent, "Share Book via"));
                }
            });

        }

    }


    //This method takes the item position value and implements deleteABook method from retrofit Interface providing the "id" of the book to be deleted.
    //Once deletion is done, recycler view list gets updated.
    public void deleteABook(final int position) {

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(BookListFragment.ENDPOINT)
                .build();

        retrofitInterface retrofit = adapter.create(retrofitInterface.class);
        retrofit.deleteBook(books.get(position).getId(), new Callback<Book>() {
            @Override
            public void success(Book book, Response response) {
                Toast.makeText(mContext, R.string.delete_success, Toast.LENGTH_LONG).show();
                books.remove(position);
                notifyItemRemoved(position);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Toast.makeText(mContext, R.string.delete_failed, Toast.LENGTH_LONG).show();
            }
        });

    }


    //Updates a param "lastCheckedOutBy" on the server.
    public void checkOut(final int position, final String name) {

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(BookListFragment.ENDPOINT)
                .build();

        retrofitInterface retrofit = adapter.create(retrofitInterface.class);
        retrofit.updateCheckout(books.get(position).getId(), name, new Callback<Book>() {
            @Override
            public void success(Book book, Response response) {
                Toast.makeText(mContext, R.string.checkout_success, Toast.LENGTH_LONG).show();
                books.get(position).setLastCheckedOutBy(book.getLastCheckedOutBy());
                books.get(position).setLastCheckedOut(book.getLastCheckedOut());
                notifyItemChanged(position);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Toast.makeText(mContext, R.string.checkout_failed, Toast.LENGTH_LONG).show();
            }

        });

    }


}