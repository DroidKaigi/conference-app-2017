package io.github.droidkaigi.confsched2017.view.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.databinding.FragmentSessionFeedbackBinding;
import io.github.droidkaigi.confsched2017.viewmodel.SessionFeedbackViewModel;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class SessionFeedbackFragment extends BaseFragment {

    public static final String TAG = SessionFeedbackFragment.class.getSimpleName();

    private static final String ARG_SESSION_ID = "session_id";

    @Inject
    SessionFeedbackViewModel viewModel;

    @Inject
    CompositeDisposable compositeDisposable;

    private int sessionId;

    private FragmentSessionFeedbackBinding binding;

    public static SessionFeedbackFragment newInstance(int sessionId) {
        SessionFeedbackFragment fragment = new SessionFeedbackFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SESSION_ID, sessionId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionId = getArguments().getInt(ARG_SESSION_ID);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_session_feedback, container, false);
        binding = DataBindingUtil.bind(view);
        binding.setViewModel(viewModel);

        initView();
        return binding.getRoot();
    }

    private void initView() {
        Disposable disposable = viewModel.findSession(sessionId)
                .subscribe(
                        session -> {
                            // TODO
                        },
                        throwable -> Log.e(TAG, "Failed to find session.", throwable)
                );
        compositeDisposable.add(disposable);
    }

    @Override
    public void onPause() {
        super.onPause();
        compositeDisposable.dispose();
    }
}
