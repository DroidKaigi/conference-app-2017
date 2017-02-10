package io.github.droidkaigi.confsched2017.view.customview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public abstract class ArrayRecyclerAdapter<T, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> implements Iterable<T> {

    protected final List<T> list;

    final Context context;

    public ArrayRecyclerAdapter(@NonNull Context context) {
        this(context, new ArrayList<>());
    }

    public ArrayRecyclerAdapter(@NonNull Context context, @NonNull List<T> list) {
        this.context = context;
        this.list = list;
    }

    @UiThread
    public void reset(Collection<T> items) {
        clear();
        addAll(items);
        notifyDataSetChanged();
    }

    public void clear() {
        list.clear();
    }

    public void addAll(Collection<T> items) {
        list.addAll(items);
    }

    public T getItem(int position) {
        return list.get(position);
    }

    public void addItem(T item) {
        list.add(item);
    }

    public void addAll(int position, Collection<T> items) {
        list.addAll(position, items);
    }

    @UiThread
    public void addAllWithNotify(Collection<T> items) {
        int position = getItemCount();
        addAll(items);
        notifyItemInserted(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public Context getContext() {
        return context;
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }
}
