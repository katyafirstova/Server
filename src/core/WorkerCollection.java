package core;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import core.interfaces.InterfaceWorkerCollection;
import db.UserUtils;
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
    private UserUtils current_user = new UserUtils();


    public WorkerCollection() {
        this.initData = LocalDateTime.now();
    }

    /**
     * Создаёт новый элемент коллекции
     */
    @Override
    public void insert(Worker worker) {
        DBWorkerUtils dbUtils = new DBWorkerUtils(current_user);
        long id = dbUtils.insertWorker(worker);
        if (id > 0) {
            workers.put(worker.getId(), worker);
        }
    }

    /**
     * удаляет элемента из коллекции по ключу
     */
    @Override
    public void removeKey(long id) {
        DBWorkerUtils dbUtils = new DBWorkerUtils(current_user);
        if(dbUtils.deleteWorkerById(id)) {
            workers.remove(id);
        }
    }


    /**
     * очистка коллекции
     */
    @Override
    public void clear() {
        DBWorkerUtils dbUtils = new DBWorkerUtils(current_user);
        if (dbUtils.deleteWorker()) {
            workers.clear();
        }
    }

    /**
     * удаляет все элементы коллекции, значение поля salary которых больше заданного
     */
    @Override
    public void removeGreater(int salary) {
        DBWorkerUtils dbUtils = new DBWorkerUtils(current_user);
        Iterator<Map.Entry<Long, Worker>> workerIterator = workers.entrySet().iterator();
        while (workerIterator.hasNext()) {
            Map.Entry<Long, Worker> entry = workerIterator.next();
            Worker w = entry.getValue();
            int curSalary = w.getSalary();
            if (curSalary > salary) {
                if (dbUtils.deleteWorkerByGreaterSalary(salary)) {
                    workerIterator.remove();
                }
            }
        }
    }


    /**
     * удаляет все элементы коллекции, значение поля salary которых меньше заданного
     */
    @Override
    public void removeLower(int salary) {
        DBWorkerUtils dbUtils = new DBWorkerUtils(current_user);
        Iterator<Map.Entry<Long, Worker>> workerIterator = workers.entrySet().iterator();
        while (workerIterator.hasNext()) {
            Map.Entry<Long, Worker> entry = workerIterator.next();
            Worker w = entry.getValue();
            int curSalary = w.getSalary();
            if (curSalary < salary) {
                if (dbUtils.deleteWorkerByLowerSalary(salary)) {
                    workerIterator.remove();
                }
            }
        }
    }

    /**
     * удаляет все элементы коллекции, значение поля endDate которых равно заданному
     */
    @Override
    public void removeAllByEndDate(Date endDate) {
        DBWorkerUtils dbUtils = new DBWorkerUtils(current_user);
        if (endDate == null)
            throw new IllegalArgumentException("Поле endDate не может быть пустым!");
        Iterator<Map.Entry<Long, Worker>> workerIterator = workers.entrySet().iterator();
        while (workerIterator.hasNext()) {
            Map.Entry<Long, Worker> entry = workerIterator.next();
            Worker w = entry.getValue();
            Date curEndDate = w.getEndDate();
            final long difference = curEndDate.getTime() - endDate.getTime();
            if (difference > -1000 && difference < 1000) {
                if (dbUtils.deleteWorkerByEndDate(endDate)) {
                    workerIterator.remove();
                }

            }
        }
    }

    /**
     * удаляет все элементы коллекции, значение поля startDate которых равно заданному
     */
    @Override
    public void removeAnyByStartDate(LocalDate startDate) {
        DBWorkerUtils dbUtils = new DBWorkerUtils(current_user);
        Iterator<Map.Entry<Long, Worker>> workerIterator = workers.entrySet().iterator();
        while (workerIterator.hasNext()) {
            Map.Entry<Long, Worker> entry = workerIterator.next();
            Worker w = entry.getValue();
            LocalDate curStartDate = w.getStartDate();
            if (curStartDate.isEqual(startDate)) {
                if (dbUtils.deleteWorkerByStartDate(startDate)) {
                    workerIterator.remove();
                }
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







