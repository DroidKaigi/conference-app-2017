package io.github.droidkaigi.confsched2017.view.fragment;

import com.sys1yagi.fragmentcreator.annotation.Args;
import com.sys1yagi.fragmentcreator.annotation.FragmentCreator;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.databinding.FragmentSessionDetailBinding;
import io.github.droidkaigi.confsched2017.view.activity.SessionFeedbackActivity;
import io.github.droidkaigi.confsched2017.view.helper.AnimationHelper;
import io.github.droidkaigi.confsched2017.viewmodel.SessionDetailViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

@FragmentCreator
public class SessionDetailFragment extends BaseFragment implements SessionDetailViewModel.Callback {

    private static final String TAG = SessionDetailFragment.class.getSimpleName();

    private static final String ARG_SESSION_ID = "session_id";

    @Inject
    SessionDetailViewModel viewModel;

    @Inject
    CompositeDisposable compositeDisposable;

    @Args
    int sessionId;

    private FragmentSessionDetailBinding binding;

    public SessionDetailFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SessionDetailFragmentCreator.read(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        compositeDisposable.dispose();
    }

    private void initTheme() {
        Activity activity = getActivity();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Change theme by topic
            activity.setTheme(viewModel.getTopicThemeResId());

            ActivityManager.TaskDescription taskDescription =
                    new ActivityManager.TaskDescription(viewModel.getSessionTitle(), null,
                            ContextCompat.getColor(activity, viewModel.getSessionVividColorResId()));
            activity.setTaskDescription(taskDescription);

            // Change status bar scrim color
            TypedValue typedValue = new TypedValue();
            activity.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
            int colorPrimaryDark = typedValue.data;
            if (colorPrimaryDark != 0) {
                binding.collapsingToolbar.setStatusBarScrimColor(colorPrimaryDark);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSessionDetailBinding.inflate(inflater, container, false);
        viewModel.setCallback(this);
        binding.setViewModel(viewModel);
        setHasOptionsMenu(true);
        Disposable disposable = viewModel.loadSession(sessionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            initTheme();
                            binding.setViewModel(viewModel);
                        },
                        throwable -> Timber.tag(TAG).e(throwable, "Failed to find session.")
                );
        compositeDisposable.add(disposable);
        initToolbar();
        initScroll();
        return binding.getRoot();
    }

    private void initScroll() {
        binding.nestedScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
                    binding.fab.hide();
                }
                if (scrollY < oldScrollY) {
                    binding.fab.show();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel.destroy();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getComponent().inject(this);
    }

    private void initToolbar() {
        AppCompatActivity activity = ((AppCompatActivity) getActivity());
        activity.setSupportActionBar(binding.toolbar);
        ActionBar bar = activity.getSupportActionBar();
        if (bar != null) {
            bar.setDisplayHomeAsUpEnabled(true);
            bar.setDisplayShowHomeEnabled(true);
            bar.setDisplayShowTitleEnabled(false);
            bar.setHomeButtonEnabled(true);
        }
    }

    @Override
    public void onClickFab(boolean selected) {
        AnimationHelper.startVDAnimation(binding.fab,
                R.drawable.avd_add_to_check_24dp, R.drawable.avd_check_to_add_24dp,
                getResources().getInteger(R.integer.fab_vector_animation_mills));
        int textId;
        int actionTextId;
        if (selected) {
            textId = R.string.session_checked;
            actionTextId = R.string.session_uncheck;
        } else {
            textId = R.string.session_unchecked;
            actionTextId = R.string.session_check;
        }
        TypedValue typedValue = new TypedValue();
        getActivity().getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        int actionTextColor = typedValue.data;
        Snackbar.make(binding.fab, textId, Snackbar.LENGTH_SHORT)
                .setAction(actionTextId, v -> binding.fab.performClick())
                .setActionTextColor(actionTextColor)
                .show();
    }

    @Override
    public void onClickFeedback() {
        startActivity(SessionFeedbackActivity.createIntent(getContext(), sessionId));
    }
}
