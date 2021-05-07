package core.interfaces;

import model.Worker;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.Date;

public interface InterfaceWorkerCollection {

    public void info();
    public void show();
    public void insert(Worker worker);
    public void removeKey(long id);
    public void clear();
    public void save(String filename) throws IOException;
    public void load(String filename);
    public void removeGreater(int salary);
    public void removeLower(int salary);
    public void removeAllByEndDate(Date endDate);
    public void removeAnyByStartDate(LocalDate startDate);
    public void printEndDate(Date endDate);
}
