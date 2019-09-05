/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grin.appforuniver.data.model.consultation;
import android.view.View;

import com.google.gson.annotations.SerializedName;
import com.grin.appforuniver.R;
import com.grin.appforuniver.data.model.schedule.Rooms;
import com.grin.appforuniver.data.model.user.User;
import com.mikepenz.fastadapter.AbstractAdapter;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.items.AbstractItem;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Сonsultation extends AbstractItem<Сonsultation.ViewHolder> {

    @SerializedName("id")
    private Integer mId;

    @SerializedName("idCreatedUser")
    private User mCreatedUser;

    @SerializedName("idRoom")
    private Rooms idRoom;
    
    @SerializedName("date_of_passage")
    private Date date_of_passage;
    
    @Override
    public String toString() {
        return "consultationsconsultations";
    }

    @NotNull
    @Override
    public ViewHolder getViewHolder(@NotNull View view) {
        return new ViewHolder(view);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.list_item_consultation;
    }

    @Override
    public int getType() {
        return 0;
    }

    public class ViewHolder extends FastAdapter.ViewHolder<Сonsultation> {

        public ViewHolder(@NotNull View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(@NotNull Сonsultation item, @NotNull List<Object> list) {

        }

        @Override
        public void unbindView(@NotNull Сonsultation item) {

        }
    }
}
