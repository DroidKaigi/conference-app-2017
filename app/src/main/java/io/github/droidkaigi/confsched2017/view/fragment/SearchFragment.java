package io.github.droidkaigi.confsched2017.view.fragment;

import com.annimon.stream.Stream;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.R;
import io.github.droidkaigi.confsched2017.databinding.FragmentSearchBinding;
import io.github.droidkaigi.confsched2017.databinding.ViewSearchResultBinding;
import io.github.droidkaigi.confsched2017.model.Session;
import io.github.droidkaigi.confsched2017.view.activity.SessionDetailActivity;
import io.github.droidkaigi.confsched2017.view.customview.ArrayRecyclerAdapter;
import io.github.droidkaigi.confsched2017.view.customview.BindingHolder;
import io.github.droidkaigi.confsched2017.view.customview.itemdecoration.DividerItemDecoration;
import io.github.droidkaigi.confsched2017.viewmodel.SearchResultViewModel;
import io.github.droidkaigi.confsched2017.viewmodel.SearchViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


public class SearchFragment extends BaseFragment implements SearchViewModel.Callback, SearchResultViewModel.Callback {

    public static final String TAG = SearchFragment.class.getSimpleName();

    @Inject
    SearchViewModel viewModel;

    @Inject
    CompositeDisposable compositeDisposable;

    private SearchResultsAdapter adapter;

    private FragmentSearchBinding binding;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    public SearchFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getComponent().inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        viewModel.setCallback(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        MenuItemCompat.setOnActionExpandListener(menuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Activity activity = getActivity();
                if (activity != null) {
                    activity.onBackPressed();
                }
                return false;
            }
        });
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return onQueryTextChange(query);
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.setPreviousSearchText(newText);
                adapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItemCompat.expandActionView(menu.findItem(R.id.action_search));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);

        initRecyclerView();

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        compositeDisposable.clear();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.destroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        compositeDisposable.dispose();
    }

    private void initRecyclerView() {
        adapter = new SearchResultsAdapter(getContext());

        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), R.drawable.divider));
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void loadData() {
        Disposable disposable = viewModel.getSearchResultViewModels(getContext(), this)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::renderSearchResults,
                        throwable -> Timber.tag(TAG).e(throwable, "Search result load failed.")
                );
        compositeDisposable.add(disposable);
    }

    @Override
    public void closeSearchResultList() {
        adapter.clearAllResults();
    }

    @Override
    public void showSessionDetail(@NonNull Session session) {
        startActivity(SessionDetailActivity.createIntent(getContext(), session.id));
    }

    private void renderSearchResults(List<SearchResultViewModel> searchResultViewModels) {
        adapter.setAllList(searchResultViewModels);
        String searchText = adapter.getPreviousSearchText();
        if (!TextUtils.isEmpty(searchText)) {
            adapter.getFilter().filter(searchText);
        }
    }

    private class SearchResultsAdapter
            extends ArrayRecyclerAdapter<SearchResultViewModel, BindingHolder<ViewSearchResultBinding>>
            implements Filterable {

        private List<SearchResultViewModel> filteredList;

        private List<SearchResultViewModel> allList;

        private String previousSearchText;

        SearchResultsAdapter(@NonNull Context context) {
            super(context);
            this.filteredList = new ArrayList<>();
            setHasStableIds(true);
        }

        void setAllList(List<SearchResultViewModel> viewModels) {
            this.allList = viewModels;
        }

        void setPreviousSearchText(String previousSearchText) {
            this.previousSearchText = previousSearchText;
        }

        String getPreviousSearchText() {
            return previousSearchText;
        }

        void clearAllResults() {
            clear();
            notifyDataSetChanged();
        }

        @Override
        public BindingHolder<ViewSearchResultBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BindingHolder<>(getContext(), parent, R.layout.view_search_result);
        }

        @Override
        public void onBindViewHolder(BindingHolder<ViewSearchResultBinding> holder, int position) {
            SearchResultViewModel viewModel = getItem(position);
            ViewSearchResultBinding itemBinding = holder.binding;
            itemBinding.setViewModel(viewModel);
            itemBinding.executePendingBindings();

            itemBinding.txtSearchResult.setText(viewModel.getMatchedText(previousSearchText));
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    filteredList.clear();
                    FilterResults results = new FilterResults();

                    if (constraint.length() > 0) {
                        final String filterPattern = constraint.toString().toLowerCase().trim();
                        Stream.of(allList)
                                .filter(viewModel -> viewModel.match(filterPattern))
                                .forEach(filteredList::add);
                    }

                    results.values = filteredList;
                    results.count = filteredList.size();

                    return results;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    clear();
                    addAll((List<SearchResultViewModel>) results.values);
                    notifyDataSetChanged();
                }
            };
        }

        @Override
        public long getItemId(int position) {
            SearchResultViewModel viewModel = getItem(position);
            return viewModel.getSearchResultId();
        }
    }
}
