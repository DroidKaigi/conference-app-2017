package io.github.droidkaigi.confsched2017.view.fragment;

import com.annimon.stream.Optional;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.ObservableList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.databinding.FragmentContributorsBinding;
import io.github.droidkaigi.confsched2017.databinding.ViewContributorCellBinding;
import io.github.droidkaigi.confsched2017.view.customview.ArrayRecyclerAdapter;
import io.github.droidkaigi.confsched2017.view.customview.BindingHolder;
import io.github.droidkaigi.confsched2017.view.helper.IntentHelper;
import io.github.droidkaigi.confsched2017.viewmodel.ContributorViewModel;
import io.github.droidkaigi.confsched2017.viewmodel.ContributorsViewModel;

public class ContributorsFragment extends BaseFragment implements ContributorsViewModel.Callback {

    public static final String TAG = ContributorsFragment.class.getSimpleName();

    @Inject
    ContributorsViewModel viewModel;

    private FragmentContributorsBinding binding;

    private Adapter adapter;

    public static ContributorsFragment newInstance() {
        return new ContributorsFragment();
    }

    public ContributorsFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getComponent().inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setCallback(this);
        viewModel.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentContributorsBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);

        initView();
        return binding.getRoot();
    }

    @Override
    public void onStop() {
        super.onStop();
        viewModel.destroy();
    }

    private void initView() {
        adapter = new Adapter(getContext(), viewModel.getContributorViewModels());
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), getColumnCount()));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ((GridLayoutManager) binding.recyclerView.getLayoutManager()).setSpanCount(getColumnCount());
    }

    @Override
    public void onClickContributor(String htmlUrl) {
        Optional<Intent> intentOptional = IntentHelper.buildActionViewIntent(getContext(), htmlUrl);
        intentOptional.ifPresent(this::startActivity);
    }

    private int getColumnCount() {
        return getResources().getInteger(R.integer.contributors_columns);
    }

    private static class Adapter
            extends ArrayRecyclerAdapter<ContributorViewModel, BindingHolder<ViewContributorCellBinding>> {

        public Adapter(@NonNull Context context, @NonNull ObservableList<ContributorViewModel> list) {
            super(context, list);

            list.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<ContributorViewModel>>() {
                @Override
                public void onChanged(ObservableList<ContributorViewModel> contributorViewModels) {
                    notifyDataSetChanged();
                }

                @Override
                public void onItemRangeChanged(ObservableList<ContributorViewModel> contributorViewModels, int i, int i1) {
                    notifyItemRangeChanged(i, i1);
                }

                @Override
                public void onItemRangeInserted(ObservableList<ContributorViewModel> contributorViewModels, int i, int i1) {
                    notifyItemRangeInserted(i, i1);
                }

                @Override
                public void onItemRangeMoved(ObservableList<ContributorViewModel> contributorViewModels, int i, int i1,
                        int i2) {
                    notifyItemMoved(i, i1);
                }

                @Override
                public void onItemRangeRemoved(ObservableList<ContributorViewModel> contributorViewModels, int i, int i1) {
                    notifyItemRangeRemoved(i, i1);
                }
            });
        }

        @Override
        public BindingHolder<ViewContributorCellBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BindingHolder<>(getContext(), parent, R.layout.view_contributor_cell);
        }

        @Override
        public void onBindViewHolder(BindingHolder<ViewContributorCellBinding> holder, int position) {
            ContributorViewModel viewModel = getItem(position);
            ViewContributorCellBinding itemBinding = holder.binding;
            itemBinding.setViewModel(viewModel);
            itemBinding.executePendingBindings();
        }
    }
}
