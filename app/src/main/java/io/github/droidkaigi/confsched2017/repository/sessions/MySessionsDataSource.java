package io.github.droidkaigi.confsched2017.repository.sessions;

import android.support.annotation.NonNull;

import java.util.List;

import io.github.droidkaigi.confsched2017.model.MySession;
import io.github.droidkaigi.confsched2017.model.Session;
import io.reactivex.Completable;
import io.reactivex.Single;

public interface MySessionsDataSource {

    Single<List<MySession>> findAll();

    public Completable save(@NonNull Session session);

    public Single<Integer> delete(@NonNull Session session);

    public boolean isExist(int sessionId);

}
