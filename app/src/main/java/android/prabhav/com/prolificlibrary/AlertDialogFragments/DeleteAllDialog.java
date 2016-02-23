package android.prabhav.com.prolificlibrary.AlertDialogFragments;

/**
 * Created by Prabhav on 06-12-2015.
 */

import android.app.AlertDialog;
        import android.app.Dialog;
        import android.app.DialogFragment;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.ActionBarActivity;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;

        import android.prabhav.com.prolificlibrary.Fragments.BookListFragment;
        import android.prabhav.com.prolificlibrary.R;


//While deleting all the books in the list, this dialog confirms the user's choice again.
public class DeleteAllDialog extends DialogFragment {

    private Button mYesButton;
    private Button mNoButton;
    private TextView dialogTitle;
    private TextView dialogMessage;
    private static final String DIALOG_TITLE = "dialog title";
    private static final String DIALOG_MESSAGE = "dialog message";


    //takes dialog title and dialog message and pass these arguments to the fragment.
    public static DeleteAllDialog newInstance(String title, String message) {

        Bundle args = new Bundle();
        args.putString(DIALOG_TITLE, title);
        args.putString(DIALOG_MESSAGE, message);

        DeleteAllDialog fragment = new DeleteAllDialog();
        fragment.setArguments(args);

        return fragment;
    }


    private void sendResult(int resultCode) {
        if (getTargetFragment() == null)
            return;

        Intent i = new Intent();

        getTargetFragment().onActivityResult(BookListFragment.DELETE_REQUEST, resultCode, i);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //With the help of activity and layout inflater, dialog_fragment layout is inflated.
        //Using this custom layout makes it possible to change the background color, text color and text size.
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_fragment, null);

        //passed dialog title is assigned to the text view
        dialogTitle = (TextView) view.findViewById(R.id.dialog_title_textBox);
        dialogTitle.setText(getArguments().getString(DIALOG_TITLE));

        //passed message is assigned to the text view.
        dialogMessage = (TextView) view.findViewById(R.id.dialog_message_textBox);
        dialogMessage.setText(getArguments().getString(DIALOG_MESSAGE));

        //onClick Listener on "yes" button
        mYesButton = (Button) view.findViewById(R.id.yes_button);
        mYesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendResult(ActionBarActivity.RESULT_OK);
                dismiss();

            }
        });

        //onClick Listener on "no" button.
        mNoButton = (Button) view.findViewById(R.id.no_button);
        mNoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        //finally Alert Dialog is created with the help of Builder, assigned the inflated view to it.
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .create();

    }


}