package io.github.droidkaigi.confsched2017.viewmodel;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.github.droidkaigi.confsched2017.model.Room;
import io.github.droidkaigi.confsched2017.model.Session;
import io.github.droidkaigi.confsched2017.repository.sessions.SessionsRepository;
import io.github.droidkaigi.confsched2017.util.DateUtil;
import io.reactivex.Single;

public class SessionsViewModel implements ViewModel {

    private SessionsRepository sessionsRepository;

    private List<Room> rooms;

    private List<Date> stimes;

    @Inject
    SessionsViewModel(SessionsRepository sessionsRepository) {
        this.sessionsRepository = sessionsRepository;
    }

    @Override
    public void destroy() {
        // Do nothing
    }

    public Single<List<SessionViewModel>> getSessions(String languageId, Context context) {
        return sessionsRepository.findAll(languageId)
                .map(sessions -> {
                    this.rooms = createRooms(sessions);
                    this.stimes = createStimes(sessions);

                    List<SessionViewModel> viewModels = Stream.of(sessions)
                            .map(session -> {
                                // TODO Check the session is checked or not.
                                return new SessionViewModel(session, context, rooms.size(), true);
                            })
                            .collect(Collectors.toList());
                    return adjustViewModels(viewModels, context);
                });
    }

    private List<SessionViewModel> adjustViewModels(List<SessionViewModel> sessionViewModels, Context context) {
        // Prepare sessions map
        final Map<String, SessionViewModel> sessionMap = new LinkedHashMap<>();
        for (SessionViewModel viewModel : sessionViewModels) {
            String roomName = viewModel.getRoomName();
            if (TextUtils.isEmpty(roomName)) {
                // In the case of Welcome talk and lunch time, set dummy room
                roomName = rooms.get(0).name;
            }
            sessionMap.put(generateStimeRoomKey(viewModel.getStime(), roomName), viewModel);
        }

        final List<SessionViewModel> adjustedViewModels = new ArrayList<>();

        // Formatted date that user can see. Ex) 9, March
        String lastFormattedDate = null;
        for (Date stime : stimes) {
            if (lastFormattedDate == null) {
                lastFormattedDate = DateUtil.getMonthDate(stime, context);
            }

            final List<SessionViewModel> sameTimeViewModels = new ArrayList<>();
            int maxRowSpan = 1;
            for (int i = 0, size = rooms.size(); i < size; i++) {
                Room room = rooms.get(i);
                SessionViewModel viewModel = sessionMap.get(generateStimeRoomKey(stime, room.name));
                if (viewModel != null) {
                    if (!lastFormattedDate.equals(viewModel.getFormattedDate())) {
                        // Changed the date
                        lastFormattedDate = viewModel.getFormattedDate();
                        // Added empty row which divides the days
                        adjustedViewModels.add(SessionViewModel.createEmpty(1, rooms.size()));
                    }
                    sameTimeViewModels.add(viewModel);

                    if (viewModel.getRowSpan() > maxRowSpan) {
                        maxRowSpan = viewModel.getRowSpan();
                    }

                    for (int j = 1, colSize = viewModel.getColSpan(); j < colSize; j++) {
                        // If the col size is over 1, skip next loop.
                        i++;
                    }
                } else {
                    SessionViewModel empty = SessionViewModel.createEmpty(1);
                    sameTimeViewModels.add(empty);
                }
            }

            List<SessionViewModel> copiedTmpViewModels = new ArrayList<>(sameTimeViewModels);
            for (SessionViewModel tmpViewModel : sameTimeViewModels) {
                int rowSpan = tmpViewModel.getRowSpan();
                if (rowSpan < maxRowSpan) {
                    // Fill for empty cell
                    copiedTmpViewModels.add(SessionViewModel.createEmpty(maxRowSpan - rowSpan));
                }
            }

            adjustedViewModels.addAll(copiedTmpViewModels);
        }

        return adjustedViewModels;
    }

    private String generateStimeRoomKey(@NonNull Date stime, @NonNull String roomName) {
        return DateUtil.getLongFormatDate(stime) + "_" + roomName;
    }

    private List<Date> createStimes(List<Session> sessions) {
        return Stream.of(sessions)
                .map(session -> session.stime)
                .sorted()
                .distinct()
                .collect(Collectors.toList());
    }

    private List<Room> createRooms(List<Session> sessions) {
        return Stream.of(sessions)
                .map(session -> session.room)
                .filter(room -> room != null && room.id != 0)
                .sorted((lhs, rhs) -> lhs.name.compareTo(rhs.name))
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public List<Date> getStimes() {
        return stimes;
    }
}
