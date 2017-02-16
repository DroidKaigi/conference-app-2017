package io.github.droidkaigi.confsched2017.view.fragment;

import android.content.Context;
import android.databinding.ObservableList;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.databinding.FragmentSponsorsBinding;
import io.github.droidkaigi.confsched2017.databinding.ViewSponsorCellBinding;
import io.github.droidkaigi.confsched2017.databinding.ViewSponsorshipCellBinding;
import io.github.droidkaigi.confsched2017.view.activity.SponsorsActivity;
import io.github.droidkaigi.confsched2017.view.customview.BindingHolder;
import io.github.droidkaigi.confsched2017.view.customview.ObservableListRecyclerAdapter;
import io.github.droidkaigi.confsched2017.viewmodel.SponsorViewModel;
import io.github.droidkaigi.confsched2017.viewmodel.SponsorshipViewModel;
import io.github.droidkaigi.confsched2017.viewmodel.SponsorshipsViewModel;

public class SponsorsFragment extends BaseFragment {

    public static final String TAG = SponsorsFragment.class.getSimpleName();

    @Inject
    SponsorshipsViewModel viewModel;

    private FragmentSponsorsBinding binding;

    public static SponsorsFragment newInstance() {
        return new SponsorsFragment();
    }

    public SponsorsFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getComponent().inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSponsorsBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);
        initView();
        return binding.getRoot();
    }

    @Override
    public void onStop() {
        super.onStop();
        viewModel.destroy();
    }

    private void initView() {
        SponsorshipsAdapter adapter = new SponsorshipsAdapter(getContext(), viewModel.getSponsorShipViewModels());
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private class SponsorAdapter
            extends ObservableListRecyclerAdapter<SponsorViewModel, BindingHolder<ViewSponsorCellBinding>> {

        public SponsorAdapter(@NonNull Context context, @NonNull ObservableList<SponsorViewModel> list) {
            super(context, list);
        }

        @Override
        public BindingHolder<ViewSponsorCellBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BindingHolder<>(getContext(), parent, R.layout.view_sponsor_cell);
        }

        @Override
        public void onBindViewHolder(BindingHolder<ViewSponsorCellBinding> holder, int position) {
            SponsorViewModel viewModel = getItem(position);
            ViewSponsorCellBinding itemBinding = holder.binding;
            itemBinding.sponsorLogo.setMinimumHeight(getScreenWidth() / 3);
            itemBinding.setViewModel(viewModel);
            itemBinding.executePendingBindings();
        }

        private int getScreenWidth() {
            Display display = ((SponsorsActivity) getContext()).getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            return size.x;
        }
    }

    private class SponsorshipsAdapter
            extends ObservableListRecyclerAdapter<SponsorshipViewModel, BindingHolder<ViewSponsorshipCellBinding>> {

        public SponsorshipsAdapter(@NonNull Context context, @NonNull ObservableList<SponsorshipViewModel> list) {
            super(context, list);
        }

        @Override
        public BindingHolder<ViewSponsorshipCellBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BindingHolder<>(getContext(), parent, R.layout.view_sponsorship_cell);
        }

        @Override
        public void onBindViewHolder(BindingHolder<ViewSponsorshipCellBinding> holder, int position) {
            SponsorshipViewModel viewModel = getItem(position);
            ViewSponsorshipCellBinding itemBinding = holder.binding;
            SponsorAdapter adapter = new SponsorAdapter(holder.itemView.getContext(), viewModel.getSponsorViewModels());
            itemBinding.sponsorshipRecyclerView.setAdapter(adapter);
            itemBinding.setViewModel(viewModel);
            itemBinding.executePendingBindings();
        }
    }
}
