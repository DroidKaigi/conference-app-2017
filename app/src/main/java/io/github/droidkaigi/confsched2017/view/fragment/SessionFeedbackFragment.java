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
    public void onDestroy() {
        super.onDestroy();
        viewModel.destroy();
    }

    @Override
    public void onSuccessSubmit() {
        // TODO: show success action
        Toast.makeText(getContext(), "submit success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorSubmit() {
        // TODO: show failure action
        Toast.makeText(getContext(), "submit failure", Toast.LENGTH_SHORT).show();
    }
}
