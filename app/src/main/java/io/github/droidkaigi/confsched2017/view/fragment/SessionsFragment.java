package io.github.droidkaigi.confsched2017.view.fragment;

import org.lucasr.twowayview.TwoWayLayoutManager;
import org.lucasr.twowayview.widget.DividerItemDecoration;
import org.lucasr.twowayview.widget.SpannableGridLayoutManager;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Display;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.databinding.FragmentSessionsBinding;
import io.github.droidkaigi.confsched2017.databinding.ViewSessionCellBinding;
import io.github.droidkaigi.confsched2017.model.Room;
import io.github.droidkaigi.confsched2017.model.Session;
import io.github.droidkaigi.confsched2017.util.ViewUtil;
import io.github.droidkaigi.confsched2017.view.activity.MySessionsActivity;
import io.github.droidkaigi.confsched2017.view.activity.SearchActivity;
import io.github.droidkaigi.confsched2017.view.activity.SessionDetailActivity;
import io.github.droidkaigi.confsched2017.view.customview.ArrayRecyclerAdapter;
import io.github.droidkaigi.confsched2017.view.customview.BindingHolder;
import io.github.droidkaigi.confsched2017.view.customview.TouchlessTwoWayView;
import io.github.droidkaigi.confsched2017.viewmodel.SessionViewModel;
import io.github.droidkaigi.confsched2017.viewmodel.SessionsViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class SessionsFragment extends BaseFragment implements SessionViewModel.Callback {

    public static final String TAG = SessionsFragment.class.getSimpleName();

    @Inject
    SessionsViewModel viewModel;

    @Inject
    CompositeDisposable compositeDisposable;

    private SessionsAdapter adapter;

    private FragmentSessionsBinding binding;

    public static SessionsFragment newInstance() {
        return new SessionsFragment();
    }

    public SessionsFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        binding = FragmentSessionsBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);

        initView();

        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu_sessions, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_search:
                startActivity(SearchActivity.createIntent(getActivity()));
                break;
            case R.id.item_my_sessions:
                startActivity(MySessionsActivity.createIntent(getActivity()));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        showSessions();
    }

    @Override
    public void onStop() {
        super.onStop();
        compositeDisposable.clear();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.destroy();
        compositeDisposable.dispose();
    }

    private int getScreenWidth() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    private void showSessions() {
        Disposable disposable = viewModel.getSessions(Locale.getDefault(), getContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::renderSessions,
                        throwable -> Timber.tag(TAG).e(throwable, "Failed to show sessions.")
                );
        compositeDisposable.add(disposable);
    }

    private void initView() {
        binding.recyclerView.setHasFixedSize(true);

        int sessionsTableWidth = getScreenWidth();
        int minWidth = (int) getResources().getDimension(R.dimen.session_table_min_width);
        if (sessionsTableWidth < minWidth) {
            sessionsTableWidth = minWidth;
        }
        binding.recyclerView.setMinimumWidth(sessionsTableWidth);

        final Drawable divider = ResourcesCompat.getDrawable(getResources(), R.drawable.divider, null);
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(divider));

        adapter = new SessionsAdapter(getContext());
        binding.recyclerView.setAdapter(adapter);

        final ClickGestureCanceller clickCanceller = new ClickGestureCanceller(getContext(), binding.recyclerView);

        binding.root.setOnTouchListener((v, event) -> {
            clickCanceller.sendCancelIfScrolling(event);

            MotionEvent e = MotionEvent.obtain(event);
            e.setLocation(e.getX() + binding.root.getScrollX(), e.getY() - binding.headerRow.getHeight());
            binding.recyclerView.forceToDispatchTouchEvent(e);
            return false;
        });

        ViewUtil.addOneTimeOnGlobalLayoutListener(binding.headerRow, () -> {
            if (binding.headerRow.getHeight() > 0) {
                binding.recyclerView.getLayoutParams().height = binding.root.getHeight() - binding.border.getHeight() - binding.headerRow.getHeight();
                binding.recyclerView.requestLayout();
                return true;
            } else {
                return false;
            }
        });

        binding.recyclerView.clearOnScrollListeners();
        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
                // Do nothing
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                SessionViewModel viewModel = adapter.getItem(binding.recyclerView.getFirstVisiblePosition());
                if (!TextUtils.isEmpty(viewModel.getFormattedDate())) {
                    binding.txtDate.setText(viewModel.getFormattedDate());
                }
            }
        });
    }

    private void renderSessions(List<SessionViewModel> adjustedSessionViewModels) {
        List<Date> stimes = viewModel.getStimes();
        List<Room> rooms = viewModel.getRooms();

        if (binding.recyclerView.getLayoutManager() == null) {
            RecyclerView.LayoutManager lm = new SpannableGridLayoutManager(
                    TwoWayLayoutManager.Orientation.VERTICAL, rooms.size(), stimes.size());
            binding.recyclerView.setLayoutManager(lm);
        }

        renderHeaderRow(rooms);

        adapter.reset(adjustedSessionViewModels);

        if (TextUtils.isEmpty(binding.txtDate.getText())) {
            binding.txtDate.setText(adjustedSessionViewModels.get(0).getFormattedDate());
            binding.txtDate.setVisibility(View.VISIBLE);
        }
    }

    private void renderHeaderRow(List<Room> rooms) {
        if (binding.headerRow.getChildCount() == 0) {
            for (Room room : rooms) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.view_sessions_header_cell, null);
                TextView txtRoomName = (TextView) view.findViewById(R.id.txt_room_name);
                txtRoomName.setText(room.name);
                LinearLayout.LayoutParams params =
                        new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
                txtRoomName.setLayoutParams(params);
                binding.headerRow.addView(view);
            }
        }
    }

    @Override
    public void showSessionDetail(@NonNull Session session) {
        startActivity(SessionDetailActivity.createIntent(getContext(), session.id));
    }

    public class SessionsAdapter extends ArrayRecyclerAdapter<SessionViewModel, BindingHolder<ViewSessionCellBinding>> {

        SessionsAdapter(@NonNull Context context) {
            super(context);
        }

        @Override
        public BindingHolder<ViewSessionCellBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BindingHolder<>(getContext(), parent, R.layout.view_session_cell);
        }

        @Override
        public void onBindViewHolder(BindingHolder<ViewSessionCellBinding> holder, int position) {
            SessionViewModel viewModel = getItem(position);
            viewModel.setCallback(SessionsFragment.this);
            holder.binding.setViewModel(viewModel);
            holder.binding.executePendingBindings();
        }
    }

    private static class ClickGestureCanceller
    {
        private GestureDetector gestureDetector;

        ClickGestureCanceller(final Context context, final TouchlessTwoWayView targetView) {
            gestureDetector = new GestureDetector(context, new GestureDetector.OnGestureListener() {
                private boolean ignoreMotionEventOnScroll = false;

                @Override
                public boolean onDown(MotionEvent motionEvent) {
                    ignoreMotionEventOnScroll = true;
                    return false;
                }

                @Override
                public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {

                    // Send cancel event for item clicked when horizontal scrolling.
                    if (ignoreMotionEventOnScroll) {
                        final long now = SystemClock.uptimeMillis();
                        MotionEvent cancelEvent = MotionEvent.obtain(now, now,
                                MotionEvent.ACTION_CANCEL, 0.0f, 0.0f, 0);
                        targetView.forceToDispatchTouchEvent(cancelEvent);
                        ignoreMotionEventOnScroll = false;
                    }

                    return false;
                }

                @Override
                public void onShowPress(MotionEvent motionEvent) {
                    // Do nothing
                }

                @Override
                public boolean onSingleTapUp(MotionEvent motionEvent) {
                    // Do nothing
                    return false;
                }


                @Override
                public void onLongPress(MotionEvent motionEvent) {
                    // Do nothing
                }

                @Override
                public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                    // Do nothing
                    return false;
                }
            });
        }

        public void sendCancelIfScrolling(MotionEvent event) {
            gestureDetector.onTouchEvent(event);
        }
    }

}
