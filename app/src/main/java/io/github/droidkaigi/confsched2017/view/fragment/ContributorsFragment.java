package io.github.droidkaigi.confsched2017.view.fragment;

import com.annimon.stream.Optional;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.databinding.FragmentContributorsBinding;
import io.github.droidkaigi.confsched2017.databinding.ViewContributorCellBinding;
import io.github.droidkaigi.confsched2017.view.customview.ArrayRecyclerAdapter;
import io.github.droidkaigi.confsched2017.view.customview.BindingHolder;
import io.github.droidkaigi.confsched2017.view.helper.IntentHelper;
import io.github.droidkaigi.confsched2017.viewmodel.ContributorViewModel;
import io.github.droidkaigi.confsched2017.viewmodel.ContributorsViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class ContributorsFragment extends BaseFragment implements ContributorsViewModel.Callback {

    public static final String TAG = ContributorsFragment.class.getSimpleName();

    private static final int COLUMN_COUNT = 3;

    @Inject
    ContributorsViewModel viewModel;

    @Inject
    CompositeDisposable compositeDisposable;

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
        loadContributors(false);
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
        compositeDisposable.dispose();
    }

    private void initView() {
        adapter = new Adapter(getContext(), viewModel.getContributorViewModels());
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), COLUMN_COUNT));
    }

    private void renderContributors(List<ContributorViewModel> contributors) {
        Optional.ofNullable(getActivity())
                .select(AppCompatActivity.class)
                .map(AppCompatActivity::getSupportActionBar)
                .ifPresent(actionBar -> actionBar.setTitle(
                        getString(R.string.contributors) + " " + getString(R.string.contributors_people, contributors.size())));
    }

    @Override
    public void onClickContributor(String htmlUrl) {
        Optional<Intent> intentOptional = IntentHelper.buildActionViewIntent(getContext(), htmlUrl);
        intentOptional.ifPresent(this::startActivity);
    }

    @Override
    public void onSwipeRefresh() {
        loadContributors(true);
    }

    private void loadContributors(boolean refresh) {
        Disposable disposable = viewModel.getContributors(refresh)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::renderContributors,
                        throwable -> Timber.tag(TAG).e(throwable, "Failed to show contributors.")
                );
        compositeDisposable.add(disposable);
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
                    notifyDataSetChanged();
                }

                @Override
                public void onItemRangeInserted(ObservableList<ContributorViewModel> contributorViewModels, int i, int i1) {
                    notifyDataSetChanged();
                }

                @Override
                public void onItemRangeMoved(ObservableList<ContributorViewModel> contributorViewModels, int i, int i1,
                        int i2) {
                    notifyDataSetChanged();
                }

                @Override
                public void onItemRangeRemoved(ObservableList<ContributorViewModel> contributorViewModels, int i, int i1) {
                    notifyDataSetChanged();
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
