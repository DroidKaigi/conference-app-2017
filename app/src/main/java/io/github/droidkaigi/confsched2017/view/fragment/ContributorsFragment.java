package io.github.droidkaigi.confsched2017.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.databinding.FragmentContributorsBinding;
import io.github.droidkaigi.confsched2017.viewmodel.ContributorViewModel;
import io.github.droidkaigi.confsched2017.viewmodel.ContributorsViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class ContributorsFragment extends BaseFragment {

    public static final String TAG = ContributorsFragment.class.getSimpleName();

    @Inject
    ContributorsViewModel viewModel;

    @Inject
    CompositeDisposable compositeDisposable;

    private FragmentContributorsBinding binding;

    public static ContributorsFragment newInstance() {
        return new ContributorsFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getComponent().inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        //
    }

    private void renderContributors(List<ContributorViewModel> contributors) {
        // TODO render contributors
    }
}
