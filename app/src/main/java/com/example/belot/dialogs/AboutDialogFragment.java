package com.example.belot.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import com.example.belot.R;

public class AboutDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // setting title, icon and message of the dialog
        builder.setIcon(getResources().getDrawable(R.drawable.ic_action_about_orange))
            .setTitle(getString(R.string.about_title))
            .setMessage("Creator: Kristian Kolarić" + "\n" +
                "Name: Belot score" + "\n" +
                "Version: 1.0 "
            );

        // setting up button
        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }
}
