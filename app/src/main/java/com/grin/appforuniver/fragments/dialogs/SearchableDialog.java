package com.grin.appforuniver.fragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grin.appforuniver.R;

import java.util.ArrayList;
import java.util.List;

public class SearchableDialog<T> extends DialogFragment {
    private Context mContext;
    private List<T> mList;
    private SearchAdapter mSearchAdapter;
    public OnSelectListener<T> onSelectListener;
    public OnFiltration<T> onFiltration;
    public OnBindItem<T> onBindItem;


    public SearchableDialog(Context mContext, List<T> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        View rootView = getActivity().getLayoutInflater().inflate(R.layout.searchable_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        EditText editText = rootView.findViewById(R.id.search_text);
        RecyclerView recyclerView = rootView.findViewById(R.id.search_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mSearchAdapter = new SearchAdapter(mContext, mList);
        recyclerView.setAdapter(mSearchAdapter);

        if (onFiltration == null) {
            OnFiltration<T> defaultFiltration = (filter, list) -> {
                List<T> filteredList = new ArrayList<>();
                for (T item : mList) {
                    if (item.toString().toLowerCase().contains(filter.toLowerCase())) {
                        filteredList.add(item);
                    }
                }
                return filteredList;
            };
            setOnFiltration(defaultFiltration);
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mSearchAdapter.filterList(onFiltration.filter(editable.toString(), mList));
            }
        });

        builder.setTitle("Select item");
        builder.setView(rootView);
        builder.setPositiveButton("Clear", (dialogInterface, i) -> {
            if (onSelectListener != null) {
                onSelectListener.onSelected(null);
            }
            dismiss();
        });
        builder.setNegativeButton("Close", (dialogInterface, i) -> dismiss());
        AlertDialog alertDialog = builder.create();

        return alertDialog;
    }


    public class SearchAdapter extends RecyclerView.Adapter<ViewHolder> {
        public Context mContext;
        private List<T> mList;

        public SearchAdapter(Context mContext, List<T> mList) {
            this.mContext = mContext;
            this.mList = mList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            View view = layoutInflater.inflate(R.layout.searchable_list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.bind(mList.get(holder.getAdapterPosition()));
            holder.itemView.setOnClickListener(view -> {
                if (onSelectListener != null) {
                    onSelectListener.onSelected(mList.get(holder.getAdapterPosition()));
                }
                dismiss();
            });
        }


        @Override
        public int getItemCount() {
            return mList.size();
        }

        void filterList(List<T> mList) {
            this.mList = mList;
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textItem = itemView.findViewById(R.id.searchable_text_item);
        }

        public void bind(T object) {
            String text;
            if (onBindItem != null) {
                text = onBindItem.bind(object);
            } else {
                text = object.toString();
            }
            textItem.setText(text);
        }
    }

    public void setOnSelectListener(OnSelectListener<T> onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    public void setOnFiltration(OnFiltration<T> onFiltration) {
        this.onFiltration = onFiltration;
    }

    public void setOnBindItem(OnBindItem<T> onBindItem) {
        this.onBindItem = onBindItem;
    }

    public interface OnSelectListener<T> {
        void onSelected(T selectedItem);
    }

    public interface OnFiltration<T> {
        List<T> filter(String filter, List<T> list);
    }

    public interface OnBindItem<T> {
        String bind(T item);
    }
}
