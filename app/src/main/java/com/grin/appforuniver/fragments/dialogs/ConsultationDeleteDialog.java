package com.grin.appforuniver.fragments.dialogs;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputLayout;
import com.grin.appforuniver.R;
import com.grin.appforuniver.activity.ConsultationActivity;
import com.grin.appforuniver.data.WebServices.ConsultationInterface;
import com.grin.appforuniver.data.WebServices.RoomInterface;
import com.grin.appforuniver.data.WebServices.ServiceGenerator;
import com.grin.appforuniver.data.model.consultation.Consultation;
import com.grin.appforuniver.data.model.dto.ConsultationRequestDto;
import com.grin.appforuniver.data.model.schedule.Rooms;
import com.grin.appforuniver.data.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsultationDeleteDialog extends DialogFragment {

    public final String TAG = ConsultationDeleteDialog.class.getSimpleName();
    private Unbinder mUnbinder;

    private AlertDialog dialog;
    private Button positiveButton;

    private int mIdConsultation;

    public interface OnDeleteConsultation {
        void onDeleteConsultation();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_consultation_delete, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        mIdConsultation = Objects.requireNonNull(getArguments()).getInt(ConsultationActivity.key, 0);

        mUnbinder = ButterKnife.bind(this, view);

        builder.setTitle(R.string.delete_consultation);
        builder.setView(view);
        builder.setPositiveButton(R.string.delete, null);
        builder.setNegativeButton(R.string.cancel, (dialogInterface, i) -> {});

        dialog = builder.create();
        dialog.setOnShowListener(dialogInner -> {
            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            if (button != null) {
                positiveButton = button;
                positiveButton.setOnClickListener(view12 -> delete());
            }
        });
        return dialog;

    }

    @Override
    public void onStart() {
        super.onStart();
        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED);
    }

    private void delete() {

        ConsultationInterface consultationInterface = ServiceGenerator.createService(ConsultationInterface.class);

        Call<Void> call = consultationInterface.delete(mIdConsultation);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Toasty.success(Objects.requireNonNull(getContext()), "Successful deleted", Toast.LENGTH_SHORT, true).show();

                    ConsultationDeleteDialog.OnDeleteConsultation activity = (ConsultationDeleteDialog.OnDeleteConsultation) getActivity();
                    Objects.requireNonNull(activity).onDeleteConsultation();

                    dialog.dismiss();

                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Toasty.error(Objects.requireNonNull(getContext()), Objects.requireNonNull(t.getMessage()), Toast.LENGTH_SHORT, true).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}