package com.grin.appforuniver.fragments.schedule.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.grin.appforuniver.R;
import com.grin.appforuniver.data.model.schedule.Classes;
import com.grin.appforuniver.data.model.schedule.Groups;
import com.grin.appforuniver.data.model.schedule.Professors;
import com.grin.appforuniver.data.model.schedule.Rooms;
import com.grin.appforuniver.fragments.schedule.ScheduleFiltrationManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;

import static com.grin.appforuniver.data.utils.Constants.Place;
import static com.grin.appforuniver.data.utils.Constants.TypesOfClasses;
import static com.grin.appforuniver.data.utils.Constants.Week;

public class ChipFilterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "ChipFilterAdapter";
    private static final int TYPE_PROFESSOR = 0;
    private static final int TYPE_ROOM = 1;
    private static final int TYPE_GROUP = 2;
    private static final int TYPE_STRING = 3;
    private Context context;
    private List<Object> listFilters;
    private ScheduleFiltrationManager scheduleFiltrationManager;

    public ChipFilterAdapter(Context context, Callback<List<Classes>> callbackRetrofitSchedule) {
        this.context = context;
        scheduleFiltrationManager = new ScheduleFiltrationManager(callbackRetrofitSchedule);
        this.listFilters = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        Object item = listFilters.get(position);
        if (item instanceof Professors) {
            return TYPE_PROFESSOR;
        } else if (item instanceof Rooms) {
            return TYPE_ROOM;
        } else if (item instanceof Groups) {
            return TYPE_GROUP;
        } else if (item instanceof String) {
            return TYPE_STRING;
        } else {
            return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_PROFESSOR) {
            View view = inflater.inflate(R.layout.cat_chip_group_item_choice, parent, false);
            return new ProfessorViewHolder(view);
        } else if (viewType == TYPE_ROOM) {
            View view = inflater.inflate(R.layout.cat_chip_group_item_choice, parent, false);
            return new RoomViewHolder(view);
        } else if (viewType == TYPE_GROUP) {
            View view = inflater.inflate(R.layout.cat_chip_group_item_choice, parent, false);
            return new GroupViewHolder(view);
        } else if (viewType == TYPE_STRING) {
            View view = inflater.inflate(R.layout.cat_chip_group_item_choice, parent, false);
            return new ViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ProfessorViewHolder) {
            ((ProfessorViewHolder) holder).bind((Professors) listFilters.get(position));
        } else if (holder instanceof RoomViewHolder) {
            ((RoomViewHolder) holder).bind((Rooms) listFilters.get(position));
        } else if (holder instanceof GroupViewHolder) {
            ((GroupViewHolder) holder).bind((Groups) listFilters.get(position));
        } else if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).bind((String) listFilters.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return listFilters.size();
    }

    public void setItemsFilter(Classes subject, TypesOfClasses type, Professors professor, Rooms room, Groups group, Place place, Week week) {
        listFilters.clear();
        if (subject != null) listFilters.add(subject);
        if (type != null) listFilters.add(type);
        if (professor != null) listFilters.add(professor);
        if (room != null) listFilters.add(room);
        if (group != null) listFilters.add(group);
        if (place != null) listFilters.add(place);
        if (week != null) listFilters.add(week);
        if (listFilters.size() == 0) {
            listFilters.add("Schedule current user");
        }
        notifyDataSetChanged();
        getSchedule(subject, type, professor, room, group, place, week);
    }

    private void deleteItemFilter(int position) {
        listFilters.remove(position);
        Classes subject = null;
        TypesOfClasses type = null;
        Professors professor = null;
        Rooms room = null;
        Groups group = null;
        Place place = null;
        Week week = null;
        for (Object object : listFilters) {
            if (object instanceof Classes) subject = (Classes) object;
            if (object instanceof TypesOfClasses) type = (TypesOfClasses) object;
            if (object instanceof Professors) professor = (Professors) object;
            if (object instanceof Rooms) room = (Rooms) object;
            if (object instanceof Groups) group = (Groups) object;
            if (object instanceof Place) place = (Place) object;
            if (object instanceof Week) week = (Week) object;
        }
        if (listFilters.size() == 0) {
            listFilters.add("Schedule current user");
        }
        notifyDataSetChanged();
        getSchedule(subject, type, professor, room, group, place, week);
    }

    void getSchedule(Classes subject, TypesOfClasses type, Professors professor, Rooms room, Groups group, Place place, Week week) {
        scheduleFiltrationManager.getSchedule(subject, type, professor, room, group, place, week);

    }

    public boolean isProfessorsSchedule() {
        return scheduleFiltrationManager.isProfessorsSchedule();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Chip chip;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            chip = (Chip) itemView;
        }

        void bind(String text) {
            chip.setText(text);
        }

    }

    public class ProfessorViewHolder extends RecyclerView.ViewHolder {
        Chip chip;

        ProfessorViewHolder(@NonNull View itemView) {
            super(itemView);
            chip = (Chip) itemView;
        }

        void bind(Professors professors) {
            String text = "Professor: " + professors.getUser().getShortFIO();
            chip.setText(text);
            chip.setOnCloseIconClickListener(view -> deleteItemFilter(getAdapterPosition()));
        }
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder {
        Chip chip;

        RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            chip = (Chip) itemView;
        }

        void bind(Rooms rooms) {
            String text = "Room: " + rooms.getName();
            chip.setText(text);
            chip.setOnCloseIconClickListener(view -> deleteItemFilter(getAdapterPosition()));
        }
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder {
        Chip chip;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            chip = (Chip) itemView;
        }

        void bind(Groups group) {
            String text = "Room: " + group.getmName();
            chip.setText(text);
            chip.setOnCloseIconClickListener(view -> deleteItemFilter(getAdapterPosition()));
        }
    }

}
