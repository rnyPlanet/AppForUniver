package com.grin.appforuniver.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.grin.appforuniver.R;
import com.grin.appforuniver.data.WebServices.ConsultationInterface;
import com.grin.appforuniver.data.WebServices.ServiceGenerator;
import com.grin.appforuniver.data.model.consultation.Consultation;
import com.grin.appforuniver.fragments.dialogs.ConsultationActionsDialog;

import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsultationActivity extends AppCompatActivity implements ConsultationActionsDialog.OnUpdate {
    public final String TAG = ConsultationActivity.class.getSimpleName();

    private Unbinder mUnbinder;
    private int mIdConsultation;
    private Consultation mConsultation;

    private Menu mMenuList;

    @BindView(R.id.activity_consultation_FIO_tv)
    TextView fioTV;
    @BindView(R.id.activity_consultation_roomNum_tv)
    TextView roomTV;
    @BindView(R.id.activity_consultation_dateOfPassage_tv)
    TextView dateOfPassage;
    @BindView(R.id.activity_consultation_timeOfPassage_tv)
    TextView timeOfPassage;
    @BindView(R.id.activity_consultation_description_tv)
    TextView description;
    @BindView(R.id.activity_consultation_subscribe_manage)
    Button subscribeManageBTN;

    @BindView(R.id.activity_consultation_pb)
    ProgressBar progressBar;

    ConsultationInterface consultationInterface = ServiceGenerator.createService(ConsultationInterface.class);

    public static String KEY = "idConsultation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation);

        mUnbinder = ButterKnife.bind(this);

        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mIdConsultation = getIntent().getIntExtra("Consultation", -1);

        getConsultationById(mIdConsultation);
        getStatusConsultation(mIdConsultation);
    }

    private void getConsultationById(int id) {
        Call<Consultation> callConsultation = consultationInterface.getConsultationById(id);
        callConsultation.enqueue(new Callback<Consultation>() {
            @Override
            public void onResponse(@NonNull Call<Consultation> call, @NonNull Response<Consultation> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        mConsultation = response.body();
                        init();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Consultation> call, @NonNull Throwable t) {
                Toasty.error(ConsultationActivity.this, Objects.requireNonNull(t.getMessage()), Toast.LENGTH_SHORT, true).show();
            }
        });


    }

    private void getStatusConsultation(int mIdConsultation) {
        Call<Map<Object, Boolean>> call = consultationInterface.statusConsultation(mIdConsultation);
        call.enqueue(new Callback<Map<Object, Boolean>>() {
            @Override
            public void onResponse(@NonNull Call<Map<Object, Boolean>> call, @NonNull Response<Map<Object, Boolean>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Map<Object, Boolean> map = response.body();
                        if (map.containsKey("isCanManage")) {
                            if (map.get("isCanManage")) {
                                initMenu();
                            }
                        }
                        if (map.containsKey("isSubscribed")) {
                            if (map.get("isSubscribed")) {
                                manageSubscribeButton(map.get("isSubscribed"));
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Map<Object, Boolean>> call, @NonNull Throwable t) {

            }
        });
    }

    private void initMenu() {
        mMenuList.findItem(R.id.edit_consultation).setVisible(true);
        mMenuList.findItem(R.id.delete_consultation).setVisible(true);
        subscribeManageBTN.setVisibility(View.GONE);
    }

    @SuppressLint("SetTextI18n")
    private void init() {
        fioTV.setText(mConsultation.getCreatedUser().getFullFIO());
        roomTV.setText(mConsultation.getRoom().getName());
        dateOfPassage.setText(mConsultation.getDateOfPassage());
        timeOfPassage.setText(mConsultation.getTimeOfPassage());
        if (mConsultation.getDescription() != null) {
            findViewById(R.id.consultation_description).setVisibility(View.VISIBLE);
            description.setText(mConsultation.getDescription());
        } else {
            View emptyState = findViewById(R.id.empty_consultation_activity);
            findViewById(R.id.consultation_description).setVisibility(View.GONE);

            emptyState.setVisibility(View.VISIBLE);
        }
    }

    void subscribe() {
        Call<Void> call = consultationInterface.subscribeOnConsultationById(mIdConsultation);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                Toasty.success(ConsultationActivity.this, getString(R.string.subscribe_on_consultation), Toast.LENGTH_SHORT, true).show();
                manageSubscribeButton(true);
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Toasty.error(ConsultationActivity.this, Objects.requireNonNull(t.getMessage()), Toast.LENGTH_SHORT, true).show();
            }
        });
    }

    void unSubscribe() {
        Call<Void> call = consultationInterface.unsubscribeOnConsultationById(mIdConsultation);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Toasty.info(ConsultationActivity.this, getString(R.string.unsubscrib_from_consultation), Toast.LENGTH_SHORT, true).show();
                    manageSubscribeButton(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Toasty.error(ConsultationActivity.this, Objects.requireNonNull(t.getMessage()), Toast.LENGTH_SHORT, true).show();
            }
        });
    }

    void manageSubscribeButton(boolean isSubscribed) {
        if (isSubscribed) {
            subscribeManageBTN.setOnClickListener(view -> unSubscribe());
            subscribeManageBTN.setBackground(ContextCompat.getDrawable(this, R.drawable.outline));
            subscribeManageBTN.setTextColor(getResources().getColor(android.R.color.white));
            subscribeManageBTN.setText(getResources().getString(R.string.activity_consultation_unsubscribe));
        } else {
            subscribeManageBTN.setOnClickListener(view -> subscribe());
            subscribeManageBTN.setBackground(ContextCompat.getDrawable(this, R.drawable.btn));
            subscribeManageBTN.setTextColor(getResources().getColor(R.color.colorPrimary));
            subscribeManageBTN.setText(getResources().getString(R.string.activity_consultation_subscribe));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_consultation, menu);
        this.mMenuList = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_consultation: {
                DialogFragment dialogFragment = new ConsultationActionsDialog(this, this);
                Bundle bundle = new Bundle();
                bundle.putInt(KEY, mIdConsultation);
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getSupportFragmentManager(), "consultationUpdateDialog");
            }
            return true;
            case R.id.delete_consultation: {
                AlertDialog.Builder builderDeleteCounter = new AlertDialog.Builder(this);
                builderDeleteCounter.setTitle(R.string.delete_consultation);
                builderDeleteCounter.setMessage(R.string.warning_delete_counter);
                builderDeleteCounter.setIcon(R.drawable.ic_warning);
                builderDeleteCounter.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ConsultationInterface consultationInterface = ServiceGenerator.createService(ConsultationInterface.class);

                        Call<Void> call = consultationInterface.delete(mIdConsultation);
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                                if (response.isSuccessful()) {
                                    Toasty.success(getBaseContext(), "Successful deleted", Toast.LENGTH_SHORT, true).show();
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                                Toasty.error(Objects.requireNonNull(getBaseContext()), Objects.requireNonNull(t.getMessage()), Toast.LENGTH_SHORT, true).show();
                            }
                        });
                    }
                });

                builderDeleteCounter.setNegativeButton(R.string.cancel, null);

                AlertDialog alert = builderDeleteCounter.create();

                alert.show();
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED);
            }
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

    @Override
    public void onUpdated(int idConsultation) {
        Toasty.success(this, getString(R.string.successful_updated), Toast.LENGTH_SHORT, true).show();
        getConsultationById(idConsultation);
    }
}
