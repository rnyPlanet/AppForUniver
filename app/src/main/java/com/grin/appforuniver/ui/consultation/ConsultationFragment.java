package com.grin.appforuniver.ui.consultation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.grin.appforuniver.R;
import com.grin.appforuniver.data.tools.AuthManager;
import com.grin.appforuniver.databinding.FragmentConsultationsBinding;
import com.grin.appforuniver.ui.consultationActionsDialog.ConsultationActionsDialog;
import com.grin.appforuniver.ui.consultationtabs.ConsultationListFragment;

import es.dmoral.toasty.Toasty;

import static com.grin.appforuniver.utils.Constants.Roles.ROLE_TEACHER;

public class ConsultationFragment extends Fragment implements ConsultationListFragment.OnRecyclerViewScrolled,
        ConsultationActionsDialog.OnCreate {

    public final String TAG = ConsultationFragment.class.getSimpleName();

    private PagerAdapter pagerAdapter;
    private FragmentConsultationsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentConsultationsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pagerAdapter = new PagerAdapter(getChildFragmentManager(), this);
        binding.viewpager.setAdapter(pagerAdapter);
        binding.tabLayout.setVisibility(View.VISIBLE);
        binding.tabLayout.setupWithViewPager(binding.viewpager);
        if (AuthManager.getInstance().getUserRoles().contains(ROLE_TEACHER.toString())) {
            binding.fab.show();
        }
        binding.fab.setOnClickListener(view1 -> {
            ConsultationActionsDialog consultationCreateDialog = new ConsultationActionsDialog(ConsultationFragment.this);
            consultationCreateDialog.show(getFragmentManager(), "consultationCreateDialog");
        });
    }

    @Override
    public void onCreated() {
        Toasty.success(getContext(), getString(R.string.successful_created), Toast.LENGTH_SHORT, true).show();
        binding.viewpager.setAdapter(pagerAdapter);
    }


    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        if (AuthManager.getInstance().getUserRoles().contains(ROLE_TEACHER.toString())) {
            if (dy > 0 && binding.fab.getVisibility() == View.VISIBLE) {
                binding.fab.hide();
            } else if (dy < 0 && binding.fab.getVisibility() != View.VISIBLE) {
                binding.fab.show();
            }
        }
    }
}
