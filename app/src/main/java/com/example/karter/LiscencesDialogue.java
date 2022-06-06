package com.example.karter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.DialogFragment;

public class LiscencesDialogue extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.liscences_dialogue_layout,null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view);


        return builder.create();
    }
}
