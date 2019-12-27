package com.grin.appforuniver.activity;

import android.app.Dialog;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.google.gson.Gson;
import com.grin.appforuniver.R;
import com.grin.appforuniver.data.WebServices.ConsultationInterface;
import com.grin.appforuniver.data.WebServices.ServiceGenerator;
import com.grin.appforuniver.data.model.consultation.Consultation;
import com.grin.appforuniver.data.utils.PreferenceUtils;
import com.grin.appforuniver.fragments.dialogs.ConsultationUpdateDialog;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsultationActivity extends AppCompatActivity {

    public final String TAG = ConsultationActivity.class.getSimpleName();

    private Menu mMenuList;

    private Consultation mConsultation;

    private Unbinder mUnbinder;

    @BindView(R.id.activity_consultation_FIO_tv)
    TextView fioTV;
    @BindView(R.id.activity_consultation_roomNum_tv)
    TextView roomTV;
    @BindView(R.id.activity_consultation_dateOfPassage_tv)
    TextView dateOfPassage;
    @BindView(R.id.activity_consultation_description_tv)
    TextView description;
    @BindView(R.id.activity_consultation_subscribe_btn)
    Button subscribeBTN;
    @BindView(R.id.activity_consultation_unsubscribe_btn)
    Button unSubscribeBTN;

    ConsultationInterface consultationInterface = ServiceGenerator.createService(ConsultationInterface.class);

    private boolean isCreatedConsultationByUser = false;

    public static String key = "idConsultation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation);

        mUnbinder = ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar_consultation);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        PreferenceUtils.context = this;
        mConsultation = new Gson().fromJson(getIntent().getStringExtra("Consultation"), Consultation.class);

        isCanUpdateConsultation();
        init();

    }

    @SuppressLint("SetTextI18n")
    private void init() {
        fioTV.setText(mConsultation.getCreatedUser().getLastName() + " " + mConsultation.getCreatedUser().getFirstName() + " " + mConsultation.getCreatedUser().getPatronymic());
        roomTV.setText(getResources().getString(R.string.consultation_activity_room) + mConsultation.getRoom().getName());
        dateOfPassage.setText(mConsultation.getDateOfPassage().toString());
        if (mConsultation.getDescription() != null) {
            findViewById(R.id.activity_consultation_descriptionText_tv).setVisibility(View.VISIBLE);
            description.setText(mConsultation.getDescription());
        }

        Call<Boolean> call = consultationInterface.isCanSubscribe(mConsultation.getId());
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(@NonNull Call<Boolean> call, @NonNull Response<Boolean> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null & response.body()) {
                        subscribeBTN.setVisibility(View.VISIBLE);
                    }
                } else if(isCreatedConsultationByUser) {
                    unSubscribeBTN.setVisibility(View.INVISIBLE);
                } else {
                    subscribeBTN.setVisibility(View.INVISIBLE);
                    unSubscribeBTN.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Boolean> call, @NonNull Throwable t) {
                Toasty.error(ConsultationActivity.this, Objects.requireNonNull(t.getMessage()), Toast.LENGTH_SHORT, true).show();
            }
        });
    }

    private void isCanUpdateConsultation() {

        Call<Boolean> call = consultationInterface.isCanUpdateConsultation(mConsultation.getId());
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(@NonNull Call<Boolean> call, @NonNull Response<Boolean> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        isCreatedConsultationByUser = true;
                        mMenuList.findItem(R.id.edit_consultation).setVisible(true);
                        mMenuList.findItem(R.id.delete_consultation).setVisible(true);
                        if(subscribeBTN != null) subscribeBTN.setVisibility(View.INVISIBLE);
                        if(unSubscribeBTN != null) unSubscribeBTN.setVisibility(View.INVISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Boolean> call, @NonNull Throwable t) {
                Toasty.error(ConsultationActivity.this, Objects.requireNonNull(t.getMessage()), Toast.LENGTH_SHORT, true).show();
            }
        });

        Call<Consultation> callConsultaion = consultationInterface.getConsultationById(mConsultation.getId());
        callConsultaion.enqueue(new Callback<Consultation>() {
            @Override
            public void onResponse(@NonNull Call<Consultation> call, @NonNull Response<Consultation> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        mConsultation = response.body();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Consultation> call, @NonNull Throwable t) {
                Toasty.error(ConsultationActivity.this, Objects.requireNonNull(t.getMessage()), Toast.LENGTH_SHORT, true).show();
            }
        });
    }

    @OnClick(R.id.activity_consultation_subscribe_btn)
    void subscribe() {
        Call<Void> call = consultationInterface.subscribeOnConsultationById(mConsultation.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                Toasty.success(ConsultationActivity.this, "Successful subscribe on consultation", Toast.LENGTH_SHORT, true).show();

                unSubscribeBTN.setVisibility(View.VISIBLE);
                subscribeBTN.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Toasty.error(ConsultationActivity.this, Objects.requireNonNull(t.getMessage()), Toast.LENGTH_SHORT, true).show();
            }
        });
    }

    @OnClick(R.id.activity_consultation_unsubscribe_btn)
    void unSubscribe() {
        Call<Void> call = consultationInterface.unsubscribeOnConsultationById(mConsultation.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Toasty.info(ConsultationActivity.this, "Successful unsubscribe on consultation", Toast.LENGTH_SHORT, true).show();

                    unSubscribeBTN.setVisibility(View.INVISIBLE);
                    subscribeBTN.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Toasty.error(ConsultationActivity.this, Objects.requireNonNull(t.getMessage()), Toast.LENGTH_SHORT, true).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        this.mMenuList = menu;
        inflater.inflate(R.menu.menu_activity_consultation, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_consultation: {
                DialogFragment dialogFragment = new ConsultationUpdateDialog();
                Bundle bundle = new Bundle();
                bundle.putInt(key, mConsultation.getId());
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getSupportFragmentManager(),"consultationUpdateDialog");

            }
                return true;
            case R.id.delete_consultation:
                Toasty.info(this, "sdf", Toasty.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

}
