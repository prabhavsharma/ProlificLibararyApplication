package android.prabhav.com.prolificlibrary.AlertDialogFragments;

/**
 * Created by Prabhav on 06-12-2015.
 */

import android.app.AlertDialog;
        import android.app.Dialog;
        import android.app.DialogFragment;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;

import android.prabhav.com.prolificlibrary.R;


//This class provides a simple Alert Dialog Fragment.
// It has a static method newInstance which takes two arguments title and message of the Alert Dialog.
public class AlertDialogFragment extends DialogFragment {

    private static final String DIALOG_TITLE = "dialog title";
    private static final String DIALOG_MESSAGE = "dialog message";
    private Button mYesButton;
    private Button mNoButton;
    private TextView dialogTitle;
    private TextView dialogMessage;


    //takes title and message and pass these arguments to the fragment.
    public static AlertDialogFragment newInstance(String title, String message) {

        Bundle args = new Bundle();
        args.putString(DIALOG_TITLE, title);
        args.putString(DIALOG_MESSAGE, message);

        AlertDialogFragment fragment = new AlertDialogFragment();
        fragment.setArguments(args);

        return fragment;
    }

    //Creates and returns a dialog.
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
                dismiss();
            }
        });

        //onClick Listener on "no" button.
        mNoButton = (Button) view.findViewById(R.id.no_button);
        mNoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        //finally Alert Dialog is created with the help of Builder, assigned the inflated view to it.
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .create();

    }


}