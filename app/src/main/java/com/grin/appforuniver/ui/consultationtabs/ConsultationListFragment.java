package com.grin.appforuniver.ui.consultationtabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.grin.appforuniver.data.tools.AuthManager;
import com.grin.appforuniver.databinding.FragmentConsultationsAllBinding;

import es.dmoral.toasty.Toasty;

import static com.grin.appforuniver.utils.Constants.Roles.ROLE_TEACHER;

public abstract class ConsultationListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "ConsultationListFragment";

    private ConsultationListViewModel viewModel;
    private FragmentConsultationsAllBinding binding;
    private ConsultationAdapter mAdapter;
    protected OnRecyclerViewScrolled onRecyclerViewScrolled;

    protected enum TypeConsultationList {
        ALL {
            @Override
            protected void requireConsultation(ConsultationListViewModel viewModel) {
                viewModel.requestAllConsultation();
            }
        },
        MY {
            @Override
            protected void requireConsultation(ConsultationListViewModel viewModel) {
                viewModel.requestMyConsultations();
            }
        },
        SUBSCRIBE {
            @Override
            protected void requireConsultation(ConsultationListViewModel viewModel) {
                viewModel.requestSubscribedConsultations();
            }
        };

        protected abstract void requireConsultation(ConsultationListViewModel viewModel);

        public static void requireConsultationByType(TypeConsultationList list, ConsultationListViewModel viewModel) {
            list.requireConsultation(viewModel);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this).get(ConsultationListViewModel.class);
        binding = FragmentConsultationsAllBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.swipeToRefresh.setOnRefreshListener(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        binding.recyclerView.setVisibility(View.INVISIBLE);
        mAdapter = new ConsultationAdapter();
        binding.recyclerView.setAdapter(mAdapter);
        if (AuthManager.getInstance().getUserRoles().contains(ROLE_TEACHER.toString())) {
            binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    onRecyclerViewScrolled.onScrolled(recyclerView, dx, dy);
                }
            });
        }
        viewModel.getConsultations().observe(getViewLifecycleOwner(), consultations -> mAdapter.addAll(consultations));
        viewModel.getIsGettingData().observe(getViewLifecycleOwner(), this::manageVisibility);
        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage ->
                Toasty.error(requireContext(), errorMessage, Toast.LENGTH_SHORT, true).show());
    }

    protected abstract TypeConsultationList getConsultations();

    @Override
    public void onRefresh() {
        TypeConsultationList.requireConsultationByType(getConsultations(), viewModel);
    }

    @Override
    public void onResume() {
        super.onResume();
        TypeConsultationList.requireConsultationByType(getConsultations(), viewModel);
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    private void manageVisibility(boolean isVisible) {
        if (isVisible) {
            binding.progressBar.setVisibility(View.GONE);
            binding.recyclerView.setVisibility(View.VISIBLE);
            binding.emptyStateLayout.setVisibility(View.GONE);
        } else {
            binding.progressBar.setVisibility(View.GONE);
            binding.recyclerView.setVisibility(View.GONE);
            binding.emptyStateLayout.setVisibility(View.VISIBLE);
        }
        binding.swipeToRefresh.setRefreshing(false);
    }

    public interface OnRecyclerViewScrolled {
        void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy);
    }
}
