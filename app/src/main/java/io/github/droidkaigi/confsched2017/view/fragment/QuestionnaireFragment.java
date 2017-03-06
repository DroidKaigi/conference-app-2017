package io.github.droidkaigi.confsched2017.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.databinding.FragmentQuestionnaireBinding;
import io.github.droidkaigi.confsched2017.viewmodel.QuestionnaireViewModel;

public class QuestionnaireFragment extends BaseFragment implements QuestionnaireViewModel.Callback {

    public static final String TAG = MySessionsFragment.class.getSimpleName();

    public static QuestionnaireFragment newInstance() {
        return new QuestionnaireFragment();
    }

    @Inject
    QuestionnaireViewModel viewModel;

    private FragmentQuestionnaireBinding binding;

    public QuestionnaireFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentQuestionnaireBinding.inflate(inflater, container, false);
        viewModel.setCallback(this);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void showSuccess() {
        // TODO: show success action
        Toast.makeText(getContext(), "submit success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError() {
        // TODO: show failure action
        Toast.makeText(getContext(), "submit failure", Toast.LENGTH_SHORT).show();
    }
}
