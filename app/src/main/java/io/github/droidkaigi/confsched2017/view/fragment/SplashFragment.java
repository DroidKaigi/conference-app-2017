package io.github.droidkaigi.confsched2017.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import io.github.droidkaigi.confsched2017.databinding.FragmentSplashBinding;

/**
 * Show splash screen, responsible for handling ParticleView animation.
 */
public class SplashFragment extends Fragment {
    public static final String TAG = SplashFragment.class.getSimpleName();

    private FragmentSplashBinding binding;

    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSplashBinding.inflate(inflater, container, false);

        scheduledExecutorService.scheduleAtFixedRate(
                () -> binding.particleAnimationView.postInvalidate(),
                0L,
                40L,
                TimeUnit.MILLISECONDS);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        scheduledExecutorService.shutdown();
    }
}
