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
import io.github.droidkaigi.confsched2017.model.Questionnaire;
import io.github.droidkaigi.confsched2017.viewmodel.QuestionnaireViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class QuestionnaireFragment extends BaseFragment implements QuestionnaireViewModel.Callback {

    public static QuestionnaireFragment newInstance() {
        return new QuestionnaireFragment();
    }

    public static final String TAG = MySessionsFragment.class.getSimpleName();

    @Inject
    QuestionnaireViewModel viewModel;

    @Inject
    CompositeDisposable compositeDisposable;

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
        binding.setViewModel(viewModel);

        return binding.getRoot();
    }

    @Override
    public void onPause() {
        super.onPause();
        compositeDisposable.dispose();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        compositeDisposable.clear();
    }

    @Override
    public void onClickSubmitQuestionnaire() {
        Questionnaire questionnaire = new Questionnaire();
        // TODO: change or create questionnaire from view.
        compositeDisposable.add(viewModel.submitQuestionnaire(questionnaire)
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
