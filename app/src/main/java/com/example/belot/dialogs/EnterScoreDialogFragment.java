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
import android.widget.EditText;
import android.widget.Toast;

import com.example.belot.MainActivity;
import com.example.belot.R;

public class EnterScoreDialogFragment extends DialogFragment {
    private EditText etTeamA, etTeamB;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // call ing layout from resource
        LayoutInflater li =getActivity().getLayoutInflater();
        View view = li.inflate(R.layout.insert_dialog, null);

        // setting title, icon and layout of the  dialog
        builder.setTitle("Enter scores for teams")
                .setIcon(getResources().getDrawable(R.drawable.ic_action_add_orange))
                .setView(view);

        // initialize editTexts
        etTeamA = view.findViewById(R.id.et_team_a_score);
        etTeamB = view.findViewById(R.id.et_team_b_score);

        // setting up buttons
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                MainActivity ma = (MainActivity) getActivity();
                String scoreA = etTeamA.getText().toString();
                String scoreB = etTeamB.getText().toString();

                if (scoreA.length() > 0 && scoreB.length() > 0) {
                    ma.calculateScores(scoreA, scoreB);
                    dismiss();
                } else {
                    Toast.makeText(getActivity(), "Enter both numbers", Toast.LENGTH_LONG).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        return builder.create();
    }
}
