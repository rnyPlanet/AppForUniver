package com.grin.appforuniver.ui.detailConsultation;

import android.annotation.SuppressLint;
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
import androidx.lifecycle.ViewModelProviders;

import com.grin.appforuniver.R;
import com.grin.appforuniver.data.models.Consultation;
import com.grin.appforuniver.databinding.ActivityConsultationBinding;
import com.grin.appforuniver.ui.consultationActionsDialog.ConsultationActionsDialog;

import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class DetailConsultationActivity extends AppCompatActivity implements ConsultationActionsDialog.OnUpdate {
    public final String TAG = DetailConsultationActivity.class.getSimpleName();
    private ActivityConsultationBinding binding;
    private DetailConsultationViewModel viewModel;
    private int mIdConsultation;

    private Menu mMenuList;

    public static String KEY = "idConsultation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConsultationBinding.inflate(getLayoutInflater());
        viewModel = ViewModelProviders.of(this).get(DetailConsultationViewModel.class);
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mIdConsultation = getIntent().getIntExtra("Consultation", -1);
        viewModel.setIdConsultation(mIdConsultation);
        viewModel.requestConsultation(mIdConsultation);
        viewModel.getConsultation().observe(this, this::init);
        viewModel.isCanManage().observe(this, isCanManage -> {
            if (isCanManage) initMenu();
        });
        viewModel.isSubscribe().observe(this, this::manageSubscribeButton);
        viewModel.getErrorMessage().observe(this, errorMessage ->
                Toasty.error(this, errorMessage, Toast.LENGTH_SHORT, true).show());
    }

    private void initMenu() {
        mMenuList.findItem(R.id.edit_consultation).setVisible(true);
        mMenuList.findItem(R.id.delete_consultation).setVisible(true);
        binding.subscribeManageButton.setVisibility(View.GONE);
    }

    @SuppressLint("SetTextI18n")
    private void init(Consultation consultation) {
        binding.studentsCount.setText(Integer.toString(consultation.getUsersCollection().size()));
        binding.FIOTv.setText(consultation.getCreatedUser().getFullFIO());
        binding.roomNumberTv.setText(consultation.getRoom().getName());
        binding.dateOfPassageTv.setText(consultation.getDateOfEvent());
        binding.timeOfPassageTv.setText(consultation.getTimeOfEvent());
        if (consultation.getDescription() != null) {
            binding.consultationDescription.setVisibility(View.VISIBLE);
            binding.descriptionTv.setText(consultation.getDescription());
        } else {
            binding.consultationDescription.setVisibility(View.GONE);

            binding.emptyConsultationActivity.getRoot().setVisibility(View.VISIBLE);
        }
    }

    void manageSubscribeButton(boolean isSubscribed) {
        if (isSubscribed) {
            Toasty.success(this, getString(R.string.subscribe_on_consultation), Toast.LENGTH_SHORT, true).show();
            binding.subscribeManageButton.setOnClickListener(view -> viewModel.unSubscribe());
            binding.subscribeManageButton.setBackground(ContextCompat.getDrawable(this, R.drawable.outline));
            binding.subscribeManageButton.setTextColor(getResources().getColor(android.R.color.white));
            binding.subscribeManageButton.setText(getResources().getString(R.string.activity_consultation_unsubscribe));
        } else {
            Toasty.info(this, getString(R.string.unsubscrib_from_consultation), Toast.LENGTH_SHORT, true).show();
            binding.subscribeManageButton.setOnClickListener(view -> viewModel.subscribe());
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
                builderDeleteCounter.setPositiveButton(R.string.delete, (dialogInterface, i) -> viewModel.deleteConsultation(mIdConsultation).observe(this, isDeleted -> {
                    if (isDeleted) {
                        Toasty.success(getBaseContext(), getString(R.string.successful_deleted), Toast.LENGTH_SHORT, true).show();
                        finish();
                    }else{
                        viewModel.setErrorMessage("Consultation has not been delete");
                    }
                }));

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
        viewModel.requestConsultation(mIdConsultation);
    }
}
