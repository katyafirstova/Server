package core.interfaces;

import model.Worker;

import java.time.LocalDate;
import java.util.Date;

public interface InterfaceWorkerCollection {

    void insert(Worker worker);
    void update(Worker worker);
    void removeKey(long id, long currentUserId);
    void clear(long currentUserId);
    void removeGreater(int salary, long currentUserId);
    void removeLower(int salary, long currentUserId);
    void removeAllByEndDate(Date endDate, long currentUserId);
    void removeAnyByStartDate(LocalDate startDate, long currentUserId);
}
