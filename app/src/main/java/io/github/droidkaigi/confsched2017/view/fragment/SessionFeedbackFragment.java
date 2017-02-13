package io.github.droidkaigi.confsched2017.view.fragment;

import com.sys1yagi.fragmentcreator.annotation.Args;
import com.sys1yagi.fragmentcreator.annotation.FragmentCreator;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.databinding.FragmentSessionFeedbackBinding;
import io.github.droidkaigi.confsched2017.model.SessionFeedback;
import io.github.droidkaigi.confsched2017.viewmodel.SessionFeedbackViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

@FragmentCreator
public class SessionFeedbackFragment extends BaseFragment implements SessionFeedbackViewModel.Callback {

    public static final String TAG = SessionFeedbackFragment.class.getSimpleName();

    @Inject
    SessionFeedbackViewModel viewModel;

    @Inject
    CompositeDisposable compositeDisposable;

    @Args
    int sessionId;

    private FragmentSessionFeedbackBinding binding;

    public SessionFeedbackFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SessionFeedbackFragmentCreator.read(this);
        viewModel.setCallback(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSessionFeedbackBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);

        initView();
        return binding.getRoot();
    }

    private void initView() {
        Disposable disposable = viewModel.findSession(sessionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        session -> {
                            // TODO
                        },
                        throwable -> Timber.tag(TAG).e(throwable, "Failed to find session.")
                );
        compositeDisposable.add(disposable);
    }

    @Override
    public void onPause() {
        super.onPause();
        compositeDisposable.dispose();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.destroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        compositeDisposable.clear();
    }

    @Override
    public void onClickSubmitFeedback() {
        SessionFeedback sessionFeedback = new SessionFeedback(sessionId);
        compositeDisposable.add(viewModel.submitSessionFeedback(sessionFeedback)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success -> onSubmitSuccess(), failure -> onSubmitFailure()));
    }

    public void onSubmitSuccess() {
        // TODO: show success action
        Toast.makeText(getContext(), "submit success", Toast.LENGTH_SHORT).show();
    }

    public void onSubmitFailure() {
        // TODO: show failure action
        Toast.makeText(getContext(), "submit failure", Toast.LENGTH_SHORT).show();
    }
}
