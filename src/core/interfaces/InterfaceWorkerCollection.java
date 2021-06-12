package core.interfaces;

import model.Worker;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.Date;

public interface InterfaceWorkerCollection {

    public void insert(Worker worker);
    public void removeKey(long id, long currentUserId);
    public void clear(long currentUserId);
    public void removeGreater(int salary, long currentUserId);
    public void removeLower(int salary, long currentUserId);
    public void removeAllByEndDate(Date endDate, long currentUserId);
    public void removeAnyByStartDate(LocalDate startDate, long currentUserId);
}
