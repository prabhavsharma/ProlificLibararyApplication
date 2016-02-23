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
        import android.text.Editable;
        import android.text.TextWatcher;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;

        import android.prabhav.com.prolificlibrary.Fragments.BookListFragment;
        import android.prabhav.com.prolificlibrary.R;


//On pressing Checkout button, this dialog fragment pops up and ask the name of the User and returns the value back to the calling fragment.
public class NamePromptDialogue extends DialogFragment {

    public static final String POSITION = "position";
    public static final String NAME = "name";
    private EditText mNameEditText;
    private Button mDoneButton;
    public String name;
    private int position;


    //takes book position for which this dialog has been generated for and pass it to the fragment.
    public static NamePromptDialogue newInstance(int position) {

        Bundle args = new Bundle();
        args.putInt(POSITION, position);

        NamePromptDialogue fragment = new NamePromptDialogue();
        fragment.setArguments(args);

        return fragment;
    }

    //Send the data back to the fragment which has created this Dialog.
    private void sendResult(int resultCode) {
        if (getTargetFragment() == null)
            return;

        Intent i = new Intent();
        i.putExtra(NAME, name);
        i.putExtra(POSITION, position);

        getTargetFragment().onActivityResult(BookListFragment.REQUEST_NAME, resultCode, i);
    }


    //Creates and returns a dialog.
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        name = ""; // Every time user creates a dialog box, String name is assigned an empty string to avoid the usage of the previous data.

        //With the help of activity and layout inflater, dialog_fragment layout is inflated.
        View view = getActivity().getLayoutInflater().inflate(R.layout.enter_name_dialog, null);

        position = getArguments().getInt(POSITION);

        mNameEditText = (EditText) view.findViewById(R.id.enter_name_dialog_editText);
        mNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                name = String.valueOf(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        mDoneButton = (Button) view.findViewById(R.id.done_button);
        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendResult(ActionBarActivity.RESULT_OK);        //Send the name of the user back to the called fragment.
                dismiss();
            }
        });


        //finally Alert Dialog is created with the help of Builder, assigned the inflated view to it.
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .create();

    }


}