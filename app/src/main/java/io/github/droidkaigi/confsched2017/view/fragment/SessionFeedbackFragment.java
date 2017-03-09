package io.github.droidkaigi.confsched2017.view.fragment;

import com.sys1yagi.fragmentcreator.annotation.Args;
import com.sys1yagi.fragmentcreator.annotation.FragmentCreator;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.databinding.FragmentSessionFeedbackBinding;
import io.github.droidkaigi.confsched2017.model.SessionFeedback;
import io.github.droidkaigi.confsched2017.viewmodel.SessionFeedbackViewModel;

@FragmentCreator
public class SessionFeedbackFragment extends BaseFragment implements SessionFeedbackViewModel.Callback {

    public static final String TAG = SessionFeedbackFragment.class.getSimpleName();

    @Inject
    SessionFeedbackViewModel viewModel;

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
        viewModel.findSession(sessionId);
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
        return binding.getRoot();
    }

    @Override
    public void onDetach() {
        viewModel.destroy();
        super.onDetach();
    }

    @Override
    public void onSuccessSubmit() {
        showToast(R.string.session_feedback_submit_success);
    }

    @Override
    public void onErrorSubmit() {
        showToast(R.string.session_feedback_submit_failure);
    }

    @Override
    public void onErrorUnFilled() {
        showToast(R.string.session_feedback_error_not_filled);
    }

    @Override
    public void onSessionFeedbackInitialized(@NonNull SessionFeedback sessionFeedback) {
        String title = sessionFeedback.isSubmitted
                ? getString(R.string.session_feedback_submitted_title, sessionFeedback.sessionTitle)
                : sessionFeedback.sessionTitle;
        setActionBarTitle(title);
    }

    private void setActionBarTitle(String title) {
        if (getActivity() instanceof AppCompatActivity) {
            ActionBar bar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (bar != null) {
                bar.setTitle(title);
            }
        }
    }

    private void showToast(@StringRes int messageResId) {
        Toast.makeText(getContext(), messageResId, Toast.LENGTH_SHORT).show();
    }
}
