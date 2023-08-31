package com.medicinetake.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimeDialog extends DialogFragment {


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Calendar c = Calendar.getInstance();
        int h = c.get(Calendar.HOUR_OF_DAY);
        int m = c.get(Calendar.MINUTE);

        TimePickerDialog tPD = new TimePickerDialog(getActivity(), android.R.style.Theme_Holo_InputMethod,
                (TimePickerDialog.OnTimeSetListener) getActivity(), h, m, DateFormat.is24HourFormat(getActivity()));

        return tPD;
    }
}
