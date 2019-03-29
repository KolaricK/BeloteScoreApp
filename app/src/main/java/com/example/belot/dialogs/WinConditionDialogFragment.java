package com.example.belot.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.example.belot.MainActivity;
import com.example.belot.R;


public class WinConditionDialogFragment extends DialogFragment {

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private String win;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // call ing layout from resource
        LayoutInflater li = getActivity().getLayoutInflater();
        final View view = li.inflate(R.layout.win_condition_layout, null);

        // initialize radio group
        radioGroup = view.findViewById(R.id.radio_group);

        // setting title, icon and layout of the  dialog
        builder.setTitle("Win condition")
                .setIcon(getResources().getDrawable(R.drawable.ic_action_goal_orange))
                .setView(view);

        // setup button
        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = view.findViewById(selectedId);
                win = radioButton.getText().toString();

                MainActivity ma = (MainActivity) getActivity();
                ma.winCondition = Integer.valueOf(win);
                ma.displayWinCondition();

                dismiss();
            }
        });

        return builder.create();
    }


}
