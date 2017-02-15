package io.github.droidkaigi.confsched2017.viewmodel;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import java.util.List;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.model.MySession;
import io.github.droidkaigi.confsched2017.repository.sessions.MySessionsRepository;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public final class MySessionsViewModel extends BaseObservable implements ViewModel {

    private static final String TAG = MySessionsViewModel.class.getSimpleName();

    private MySessionsRepository mySessionsRepository;

    private final ObservableList<MySessionViewModel> mySessionViewModels;

    private final CompositeDisposable compositeDisposable;

    @Inject
    MySessionsViewModel(MySessionsRepository mySessionsRepository, CompositeDisposable compositeDisposable) {
        this.mySessionsRepository = mySessionsRepository;
        this.mySessionViewModels = new ObservableArrayList<>();
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void destroy() {
        compositeDisposable.clear();
    }

    public ObservableList<MySessionViewModel> getMySessionViewModels() {
        return mySessionViewModels;
    }


    private Single<List<MySession>> loadMySessions() {
        return mySessionsRepository.findAll()
                .map(mySessions -> Stream.of(mySessions)
                        .sorted((lhs, rhs) -> lhs.session.stime.compareTo(rhs.session.stime))
                        .collect(Collectors.toList()));
    }

    public void start(Context context) {
        Disposable disposable = loadMySessions()
                .map(mySession -> convertToViewModel(context, mySession))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::renderSponsorships,
                        throwable -> Timber.tag(TAG).e(throwable, "Failed to show my sessions.")
                );
        compositeDisposable.add(disposable);
    }

    private List<MySessionViewModel> convertToViewModel(Context context, List<MySession> mySessions) {
        return Stream.of(mySessions).map(mySession -> new MySessionViewModel(context , mySession)).collect(Collectors.toList());
    }

    private void renderSponsorships(List<MySessionViewModel> mySessionViewModels) {
        if(this.mySessionViewModels.size() == mySessionViewModels.size()) { return; }
        this.mySessionViewModels.clear();
        this.mySessionViewModels.addAll(mySessionViewModels);
    }
}
