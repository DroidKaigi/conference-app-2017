package io.github.droidkaigi.confsched2017.viewmodel;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import android.content.Context;
import android.databinding.BaseObservable;
import android.view.View;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.model.Session;
import io.github.droidkaigi.confsched2017.repository.sessions.MySessionsRepository;
import io.github.droidkaigi.confsched2017.repository.sessions.SessionsRepository;
import io.reactivex.Single;

public final class SearchViewModel extends BaseObservable implements ViewModel {

    private SessionsRepository sessionsRepository;

    private MySessionsRepository mySessionsRepository;

    private Callback callback;

    @Inject
    SearchViewModel(SessionsRepository sessionsRepository, MySessionsRepository mySessionsRepository) {
        this.sessionsRepository = sessionsRepository;
        this.mySessionsRepository = mySessionsRepository;
    }

    @Override
    public void destroy() {
        //
    }

    public void onClickCover(@SuppressWarnings("unused") View view) {
        if (callback != null) {
            callback.closeSearchResultList();
        }
    }

    public Single<List<SearchResultViewModel>> getSearchResultViewModels(Context context,
            SearchResultViewModel.Callback callback) {
        return sessionsRepository.findAll(Locale.getDefault())
                .map(sessions -> {
                    List<Session> filteredSessions = Stream.of(sessions)
                            .filter(session -> session.isSession() && session.speaker != null)
                            .collect(Collectors.toList());

                    List<SearchResultViewModel> titleResults = Stream.of(filteredSessions)
                            .map(session -> {
                                SearchResultViewModel viewModel = SearchResultViewModel
                                        .createTitleType(session, context, mySessionsRepository);
                                viewModel.setCallback(callback);
                                return viewModel;
                            }).collect(Collectors.toList());

                    List<SearchResultViewModel> descriptionResults = Stream.of(filteredSessions)
                            .map(session -> {
                                SearchResultViewModel viewModel =
                                        SearchResultViewModel.createDescriptionType(session, context,
                                                mySessionsRepository);
                                viewModel.setCallback(callback);
                                return viewModel;
                            }).collect(Collectors.toList());

                    List<SearchResultViewModel> speakerResults = Stream.of(filteredSessions)
                            .map(session -> {
                                SearchResultViewModel viewModel = SearchResultViewModel
                                        .createSpeakerType(session, context, mySessionsRepository);
                                viewModel.setCallback(callback);
                                return viewModel;
                            }).collect(Collectors.toList());

                    titleResults.addAll(descriptionResults);
                    titleResults.addAll(speakerResults);

                    return titleResults;
                });
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public interface Callback {

        void closeSearchResultList();
    }

}
