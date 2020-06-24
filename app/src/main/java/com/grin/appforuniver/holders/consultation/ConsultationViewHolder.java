package com.grin.appforuniver.holders.consultation;

import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;

import com.grin.appforuniver.data.models.Consultation;
import com.grin.appforuniver.databinding.ItemConsultationBinding;
import com.grin.appforuniver.ui.detailConsultation.DetailConsultationActivity;

import org.jetbrains.annotations.NotNull;

public class ConsultationViewHolder extends RecyclerView.ViewHolder {

    private ItemConsultationBinding binding;

    public ConsultationViewHolder(ItemConsultationBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(@NotNull Consultation item) {
        StringBuilder fioText = new StringBuilder();
        fioText.append(item.getCreatedUser().getLastName());
        fioText.append(" ");
        fioText.append(item.getCreatedUser().getFirstName());
        fioText.append(" ");
        fioText.append(item.getCreatedUser().getPatronymic());
        binding.fioTv.setText(fioText);
        binding.roomNumberTv.setText(item.getRoom().getName());
        String str = item.getDateOfEvent();
        binding.dateOfPassageTv.setText(str);
        binding.timeTv.setText(item.getTimeOfEvent());
        binding.getRoot().setOnClickListener(view -> {
            Intent intent = new Intent(binding.getRoot().getContext(), DetailConsultationActivity.class);
            intent.putExtra("Consultation", item.getId());
            binding.getRoot().getContext().startActivity(intent);
        });
    }
}
