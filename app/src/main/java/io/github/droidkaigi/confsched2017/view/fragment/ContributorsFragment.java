package io.github.droidkaigi.confsched2017.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
        Disposable disposable = viewModel.getContributors()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::renderContributors,
                        throwable -> Timber.tag(TAG).e(throwable, "Failed to show sessions.")
                );
        compositeDisposable.add(disposable);
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
        adapter = new Adapter(getContext());
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), COLUMN_COUNT));
    }

    private void renderContributors(List<ContributorViewModel> contributors) {
        adapter.addAllWithNotify(contributors);
    }

    @Override
    public void onClickContributor(String htmlUrl) {
        // TODO implement here
        Timber.d("htmlUrl: %s", htmlUrl);
    }

    private static class Adapter
            extends ArrayRecyclerAdapter<ContributorViewModel, BindingHolder<ViewContributorCellBinding>> {

        public Adapter(@NonNull Context context) {
            super(context);
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
