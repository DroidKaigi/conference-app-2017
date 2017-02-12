package io.github.droidkaigi.confsched2017.view.customview;

import android.content.Context;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

public abstract class ObservableListRecyclerAdapter<T, VH extends RecyclerView.ViewHolder>
        extends ArrayRecyclerAdapter<T, VH> {

    public ObservableListRecyclerAdapter(@NonNull Context context, @NonNull ObservableList<T> list) {
        super(context, list);

        list.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<T>>() {
            @Override
            public void onChanged(ObservableList<T> contributorViewModels) {
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(ObservableList<T> contributorViewModels, int i, int i1) {
                notifyItemRangeChanged(i, i1);
            }

            @Override
            public void onItemRangeInserted(ObservableList<T> contributorViewModels, int i, int i1) {
                notifyItemRangeInserted(i, i1);
            }

            @Override
            public void onItemRangeMoved(ObservableList<T> contributorViewModels, int i, int i1,
                    int i2) {
                notifyItemMoved(i, i1);
            }

            @Override
            public void onItemRangeRemoved(ObservableList<T> contributorViewModels, int i, int i1) {
                notifyItemRangeRemoved(i, i1);
            }
        });
    }
}
