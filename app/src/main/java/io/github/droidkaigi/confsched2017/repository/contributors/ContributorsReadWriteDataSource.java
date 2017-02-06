package io.github.droidkaigi.confsched2017.repository.contributors;

import java.util.List;

import io.github.droidkaigi.confsched2017.model.Contributor;

public interface ContributorsReadWriteDataSource extends ContributorsReadDataSource {
    void updateAllAsync(List<Contributor> contributors);
}
