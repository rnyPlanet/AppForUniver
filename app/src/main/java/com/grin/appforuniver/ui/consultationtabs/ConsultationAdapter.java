package com.grin.appforuniver.ui.consultationtabs;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grin.appforuniver.data.model.consultation.Consultation;
import com.grin.appforuniver.databinding.ItemConsultationBinding;
import com.grin.appforuniver.holders.consultation.ConsultationViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ConsultationAdapter extends RecyclerView.Adapter<ConsultationViewHolder> {
    private List<Consultation> consultations;

    public ConsultationAdapter() {
        this.consultations = new ArrayList<>();
    }

    @NonNull
    @Override
    public ConsultationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemConsultationBinding binding = ItemConsultationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ConsultationViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ConsultationViewHolder holder, int position) {
        holder.bind(consultations.get(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return consultations.size();
    }

    public void addAll(List<Consultation> consultations) {
        this.consultations = consultations;
        notifyDataSetChanged();
    }
}
