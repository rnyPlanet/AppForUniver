package com.grin.appforuniver.fragments.consultations;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.grin.appforuniver.activities.ConsultationActivity;
import com.grin.appforuniver.data.model.consultation.Consultation;
import com.grin.appforuniver.data.service.ConsultationService;
import com.grin.appforuniver.data.tools.AuthManager;
import com.grin.appforuniver.databinding.FragmentConsultationsAllBinding;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.adapters.ItemAdapter;

import java.util.List;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Response;

import static com.grin.appforuniver.utils.Constants.Roles.ROLE_TEACHER;

public abstract class ConsultationListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ConsultationService.OnRequestConsultationListListener {
    private static final String TAG = "ConsultationListFragment";
    ConsultationService mService;
    private FragmentConsultationsAllBinding binding;

    private ItemAdapter<Consultation> mItemAdapter;
    private FastAdapter<Consultation> mFastAdapter;
    OnRecyclerViewScrolled onRecyclerViewScrolled;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentConsultationsAllBinding.inflate(inflater, container, false);
        mService = ConsultationService.getService();

        binding.swipeToRefresh.setOnRefreshListener(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        binding.recyclerView.setVisibility(View.INVISIBLE);
        mItemAdapter = new ItemAdapter<>();
        mFastAdapter = FastAdapter.with(mItemAdapter);

        mFastAdapter.setOnClickListener((mView, adapter, item, position) -> {
                    Intent intent = new Intent(getContext(), ConsultationActivity.class);
                    intent.putExtra("Consultation", item.getId());
                    startActivity(intent);
                    return false;
                }
        );
        binding.recyclerView.setAdapter(mFastAdapter);
        if (AuthManager.getInstance().getUserRoles().contains(ROLE_TEACHER.toString())) {
            binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    onRecyclerViewScrolled.onScrolled(recyclerView, dx, dy);
                }
            });
        }
        return binding.getRoot();
    }

    public abstract void getConsultations(ConsultationService.OnRequestConsultationListListener l);

    @Override
    public void onRefresh() {
        getConsultations(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getConsultations(this);
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    public interface OnRecyclerViewScrolled {
        void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy);
    }

    @Override
    public void onRequestConsultationListSuccess(Call<List<Consultation>> call, Response<List<Consultation>> response) {
        if (response.isSuccessful()) {
            mItemAdapter.clear();
            if (response.body() != null) {
                mItemAdapter.add(response.body());
                binding.progressBar.setVisibility(View.GONE);
                binding.recyclerView.setVisibility(View.VISIBLE);
                binding.emptyStateLayout.setVisibility(View.GONE);
            } else {
                binding.progressBar.setVisibility(View.GONE);
                binding.recyclerView.setVisibility(View.GONE);
                binding.emptyStateLayout.setVisibility(View.VISIBLE);
            }
        } else {
            binding.progressBar.setVisibility(View.GONE);
            binding.recyclerView.setVisibility(View.GONE);
            binding.emptyStateLayout.setVisibility(View.VISIBLE);
        }
        binding.swipeToRefresh.setRefreshing(false);
    }

    @Override
    public void onRequestConsultationListFailed(Call<List<Consultation>> call, Throwable t) {
        Toasty.error(requireContext(), Objects.requireNonNull(t.getMessage()), Toast.LENGTH_SHORT, true).show();
        binding.progressBar.setVisibility(View.GONE);
        binding.emptyStateLayout.setVisibility(View.VISIBLE);
    }
}
