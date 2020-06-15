package com.grin.appforuniver.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.grin.appforuniver.R;
import com.grin.appforuniver.data.model.consultation.Consultation;
import com.grin.appforuniver.data.service.ConsultationService;
import com.grin.appforuniver.databinding.ActivityConsultationBinding;
import com.grin.appforuniver.dialogs.ConsultationActionsDialog;

import java.util.Map;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Response;

public class ConsultationActivity extends AppCompatActivity implements ConsultationActionsDialog.OnUpdate, ConsultationService.OnRequestConsultationListener, ConsultationService.OnSetSubscribeStatusListener {
    public final String TAG = ConsultationActivity.class.getSimpleName();
    private ConsultationService mService;
    private ActivityConsultationBinding binding;
    private int mIdConsultation;
    private Consultation mConsultation;

    private Menu mMenuList;

    public static String KEY = "idConsultation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConsultationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mService = ConsultationService.getService();

        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mIdConsultation = getIntent().getIntExtra("Consultation", -1);

        mService.requestConsultationById(mIdConsultation, this);
        getStatusConsultation(mIdConsultation);
    }

    private void getStatusConsultation(int mIdConsultation) {
        mService.requestStatusConsultation(mIdConsultation, new ConsultationService.OnRequestStatusConsultationListener() {
            @Override
            public void onRequestStatusConsultationSuccess(Call<Map<Object, Boolean>> call, Response<Map<Object, Boolean>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Map<Object, Boolean> map = response.body();
                        if (map.containsKey("isCanManage")) {
                            if (map.get("isCanManage")) {
                                initMenu();
                            }
                        }
                        if (map.containsKey("isSubscribed")) {
                            manageSubscribeButton(map.get("isSubscribed"));
                        }
                    }
                }
            }

            @Override
            public void onRequestStatusConsultationFailed(Call<Map<Object, Boolean>> call, Throwable t) {

            }
        });
    }

    private void initMenu() {
        mMenuList.findItem(R.id.edit_consultation).setVisible(true);
        mMenuList.findItem(R.id.delete_consultation).setVisible(true);
        binding.subscribeManageButton.setVisibility(View.GONE);
    }

    @SuppressLint("SetTextI18n")
    private void init() {
        binding.studentsCount.setText(Integer.toString(mConsultation.getUsersCollection().size()));
        binding.FIOTv.setText(mConsultation.getCreatedUser().getFullFIO());
        binding.roomNumberTv.setText(mConsultation.getRoom().getName());
        binding.dateOfPassageTv.setText(mConsultation.getDateOfPassage());
        binding.timeOfPassageTv.setText(mConsultation.getTimeOfPassage());
        if (mConsultation.getDescription() != null) {
            binding.consultationDescription.setVisibility(View.VISIBLE);
            binding.descriptionTv.setText(mConsultation.getDescription());
        } else {
            binding.consultationDescription.setVisibility(View.GONE);

            binding.emptyConsultationActivity.getRoot().setVisibility(View.VISIBLE);
        }
    }

    void subscribe() {
        mService.setSubscribeStatus(mIdConsultation, false, this);
    }

    void unSubscribe() {
        mService.setSubscribeStatus(mIdConsultation, true, this);
    }

    void manageSubscribeButton(boolean isSubscribed) {
        if (isSubscribed) {
            binding.subscribeManageButton.setOnClickListener(view -> unSubscribe());
            binding.subscribeManageButton.setBackground(ContextCompat.getDrawable(this, R.drawable.outline));
            binding.subscribeManageButton.setTextColor(getResources().getColor(android.R.color.white));
            binding.subscribeManageButton.setText(getResources().getString(R.string.activity_consultation_unsubscribe));
        } else {
            binding.subscribeManageButton.setOnClickListener(view -> subscribe());
            binding.subscribeManageButton.setBackground(ContextCompat.getDrawable(this, R.drawable.btn));
            binding.subscribeManageButton.setTextColor(getResources().getColor(R.color.colorPrimary));
            binding.subscribeManageButton.setText(getResources().getString(R.string.activity_consultation_subscribe));
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
                DialogFragment dialogFragment = new ConsultationActionsDialog(this);
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
                        mService.deleteConsultation(mIdConsultation, new ConsultationService.OnDeleteConsultationListener() {
                            @Override
                            public void onDeleteConsultationSuccess(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    Toasty.success(getBaseContext(), getString(R.string.successful_deleted), Toast.LENGTH_SHORT, true).show();
                                    finish();
                                }
                            }

                            @Override
                            public void onDeleteConsultationFailed(Call<Void> call, Throwable t) {
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
        binding = null;
        super.onDestroy();
    }

    @Override
    public void onUpdated(int idConsultation) {
        Toasty.success(this, getString(R.string.successful_updated), Toast.LENGTH_SHORT, true).show();
        mService.requestConsultationById(mIdConsultation, this);
    }

    @Override
    public void onRequestConsultationSuccess(Call<Consultation> call, Response<Consultation> response) {
        if (response.isSuccessful()) {
            if (response.body() != null) {
                mConsultation = response.body();
                init();
            }
        }
    }

    @Override
    public void onRequestConsultationFailed(Call<Consultation> call, Throwable t) {
        Toasty.error(ConsultationActivity.this, Objects.requireNonNull(t.getMessage()), Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void onSetSubscribeConsultationSuccess(boolean statusSubscribe, Call<Void> call, Response<Void> response) {
        if (statusSubscribe)
            Toasty.success(ConsultationActivity.this, getString(R.string.subscribe_on_consultation), Toast.LENGTH_SHORT, true).show();
        else
            Toasty.info(ConsultationActivity.this, getString(R.string.unsubscrib_from_consultation), Toast.LENGTH_SHORT, true).show();
        manageSubscribeButton(statusSubscribe);
    }

    @Override
    public void onSetSubscribeConsultationFailed(boolean statusSubscribe, Call<Void> call, Throwable t) {
        Toasty.error(ConsultationActivity.this, Objects.requireNonNull(t.getMessage()), Toast.LENGTH_SHORT, true).show();
    }
}
