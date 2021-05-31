package core;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import core.interfaces.InterfaceWorkerCollection;
import model.Color;
import model.Message;
import model.Status;
import model.Worker;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import db.DBWorkerUtils;
import org.slf4j.*;

/**
 * {code WorkerCollection} Методы для работы с коллекцией
 */

public class WorkerCollection implements InterfaceWorkerCollection {

    static final Logger LOG = LoggerFactory.getLogger(WorkerCollection.class);
    private HashMap<Long, Worker> workers = new HashMap<Long, Worker>();
    private LocalDateTime initData;


    public WorkerCollection() {
        this.initData = LocalDateTime.now();
    }

    /**
     * Создаёт новый элемент коллекции
     */
    @Override
    public void insert(Worker worker) {
        DBWorkerUtils dbUtils = new DBWorkerUtils();
        if (dbUtils.insertWorker(worker)) {
            workers.put(worker.getId(), worker);
        }
    }

    /**
    * удаляет элемента из коллекции по ключу
    */
    @Override
    public void removeKey(long id) {
        workers.remove(id);
    }

    /**
     * очистка коллекции
     */
    @Override
    public void clear() {
        workers.clear();
    }


    /**
     * удаляет все элементы коллекции, значение поля salary которых больше заданного
     */
    @Override
    public void removeGreater(int salary) {
        Iterator<Map.Entry<Long, Worker>> workerIterator = workers.entrySet().iterator();
        while (workerIterator.hasNext()) {
            Map.Entry<Long, Worker> entry = workerIterator.next();
            Worker w = entry.getValue();
            int curSalary = w.getSalary();
            if (curSalary > salary) {
                workerIterator.remove();
            }
        }
    }


    /**
     * удаляет все элементы коллекции, значение поля salary которых меньше заданного
     */
    @Override
    public void removeLower(int salary) {
        Iterator<Map.Entry<Long, Worker>> workerIterator = workers.entrySet().iterator();
        while (workerIterator.hasNext()) {
            Map.Entry<Long, Worker> entry = workerIterator.next();
            Worker w = entry.getValue();
            int curSalary = w.getSalary();
            if (curSalary < salary) {
                workerIterator.remove();
            }
        }
    }

    /**
     * удаляет все элементы коллекции, значение поля endDate которых равно заданному
     */
    @Override
    public void removeAllByEndDate(Date endDate) {
        if (endDate == null)
            throw new IllegalArgumentException("Поле endDate не может быть пустым!");
        Iterator<Map.Entry<Long, Worker>> workerIterator = workers.entrySet().iterator();
        while (workerIterator.hasNext()) {
            Map.Entry<Long, Worker> entry = workerIterator.next();
            Worker w = entry.getValue();
            Date curEndDate = w.getEndDate();

            final long difference = curEndDate.getTime() - endDate.getTime();
            if (difference > -1000 && difference < 1000) {
                workerIterator.remove();

            }
        }
    }

    /**
     * удаляет все элементы коллекции, значение поля startDate которых равно заданному
     */
    @Override
    public void removeAnyByStartDate(LocalDate startDate) {
        Iterator<Map.Entry<Long, Worker>> workerIterator = workers.entrySet().iterator();
        while (workerIterator.hasNext()) {
            Map.Entry<Long, Worker> entry = workerIterator.next();
            Worker w = entry.getValue();
            LocalDate curStartDate = w.getStartDate();
            if (curStartDate.isEqual(startDate)) {
                workerIterator.remove();
            }
        }

    }

    public HashMap<Long, Worker> getWorkers() {
        return workers;
    }

    public LocalDateTime getInitData() {
        return initData;
    }
}







