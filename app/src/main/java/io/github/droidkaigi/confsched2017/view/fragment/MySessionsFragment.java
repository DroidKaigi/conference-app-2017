package io.github.droidkaigi.confsched2017.view.fragment;

import android.content.Context;
import android.databinding.ObservableList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.databinding.FragmentMySessionsBinding;
import io.github.droidkaigi.confsched2017.databinding.ViewMySessionBinding;
import io.github.droidkaigi.confsched2017.model.MySession;
import io.github.droidkaigi.confsched2017.view.activity.SessionDetailActivity;
import io.github.droidkaigi.confsched2017.view.customview.BindingHolder;
import io.github.droidkaigi.confsched2017.view.customview.ObservableListRecyclerAdapter;
import io.github.droidkaigi.confsched2017.view.customview.itemdecoration.DividerItemDecoration;
import io.github.droidkaigi.confsched2017.viewmodel.MySessionViewModel;
import io.github.droidkaigi.confsched2017.viewmodel.MySessionsViewModel;


public class MySessionsFragment extends BaseFragment {

    public static final String TAG = MySessionsFragment.class.getSimpleName();

    @Inject
    MySessionsViewModel viewModel;

    private FragmentMySessionsBinding binding;

    public static MySessionsFragment newInstance() {
        return new MySessionsFragment();
    }

    public MySessionsFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getComponent().inject(this);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMySessionsBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);

        initRecyclerView();

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.start(getContext());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.destroy();
    }

    private void initRecyclerView() {
        MySessionAdapter adapter = new MySessionAdapter(getContext(), viewModel.getMySessionViewModels());
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), R.drawable.divider));
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private class MySessionAdapter
            extends ObservableListRecyclerAdapter<MySessionViewModel, BindingHolder<ViewMySessionBinding>>
            implements MySessionViewModel.Callback {

        MySessionAdapter(@NonNull Context context, ObservableList<MySessionViewModel> list) {
            super(context, list);
            setHasStableIds(true);
        }

        @Override
        public BindingHolder<ViewMySessionBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BindingHolder<>(getContext(), parent, R.layout.view_my_session);
        }

        @Override
        public void onBindViewHolder(BindingHolder<ViewMySessionBinding> holder, int position) {
            MySessionViewModel viewModel = getItem(position);
            viewModel.setCallback(this);
            ViewMySessionBinding itemBinding = holder.binding;
            itemBinding.setViewModel(viewModel);
            itemBinding.executePendingBindings();

        }

        @Override
        public long getItemId(int position) {
            MySessionViewModel viewModel = getItem(position);
            return viewModel.mySession.id;
        }


        @Override
        public void showSessionDetail(@NonNull MySession mySession) {
            startActivity(SessionDetailActivity.createIntent(getContext(), mySession.session.id));
        }
    }
}
