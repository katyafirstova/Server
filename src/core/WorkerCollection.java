package core;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import core.interfaces.InterfaceWorkerCollection;
import db.DBUserUtils;
import model.Color;
import model.Message;
import model.Status;
import model.Worker;


import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;

import db.DBWorkerUtils;
import org.slf4j.*;

/**
 * {code WorkerCollection} Методы для работы с коллекцией
 */

public class WorkerCollection implements InterfaceWorkerCollection, Serializable {

    static final Logger LOG = LoggerFactory.getLogger(WorkerCollection.class);
    private  ConcurrentHashMap<Long, Worker> workers = new ConcurrentHashMap<Long, Worker>();
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
    public void removeKey(long id, long currentUserId) {
        DBWorkerUtils dbUtils = new DBWorkerUtils();
        if(dbUtils.deleteWorkerById(id, currentUserId)) {
            this.load();
        }
    }


    /**
     * очистка коллекции
     */
    @Override
    public void clear(long currentUserId) {
        DBWorkerUtils dbUtils = new DBWorkerUtils();
        if (dbUtils.deleteWorker(currentUserId)) {
            this.load();
        }
    }

    /**
     * удаляет все элементы коллекции, значение поля salary которых больше заданного
     */
    @Override
    public void removeGreater(int salary, long currentUserId) {
        DBWorkerUtils dbUtils = new DBWorkerUtils();
        Iterator<Map.Entry<Long, Worker>> workerIterator = workers.entrySet().iterator();
        while (workerIterator.hasNext()) {
            Map.Entry<Long, Worker> entry = workerIterator.next();
            Worker w = entry.getValue();
            int curSalary = w.getSalary();
            if (curSalary > salary) {
                dbUtils.deleteWorkerByGreaterSalary(salary, currentUserId);
            }
        }
        this.load();
    }


    /**
     * удаляет все элементы коллекции, значение поля salary которых меньше заданного
     */
    @Override
    public void removeLower(int salary, long currentUserId) {
        DBWorkerUtils dbUtils = new DBWorkerUtils();
        Iterator<Map.Entry<Long, Worker>> workerIterator = workers.entrySet().iterator();
        while (workerIterator.hasNext()) {
            Map.Entry<Long, Worker> entry = workerIterator.next();
            Worker w = entry.getValue();
            int curSalary = w.getSalary();
            if (curSalary < salary) {
                dbUtils.deleteWorkerByLowerSalary(salary, currentUserId);
            }
        }
        this.load();
    }

    /**
     * удаляет все элементы коллекции, значение поля endDate которых равно заданному
     */
    @Override
    public void removeAllByEndDate(Date endDate, long currentUserId) {
        DBWorkerUtils dbUtils = new DBWorkerUtils();
        if (endDate == null)
            throw new IllegalArgumentException("Поле endDate не может быть пустым!");
        Iterator<Map.Entry<Long, Worker>> workerIterator = workers.entrySet().iterator();
        while (workerIterator.hasNext()) {
            Map.Entry<Long, Worker> entry = workerIterator.next();
            Worker w = entry.getValue();
            Date curEndDate = w.getEndDate();
            final long difference = curEndDate.getTime() - endDate.getTime();
            if (difference > -1000 && difference < 1000) {
                dbUtils.deleteWorkerByEndDate(endDate, currentUserId);
            }
        }
        this.load();
    }

    /**
     * удаляет все элементы коллекции, значение поля startDate которых равно заданному
     */
    @Override
    public void removeAnyByStartDate(LocalDate startDate, long currentUserId) {
        DBWorkerUtils dbUtils = new DBWorkerUtils();
        Iterator<Map.Entry<Long, Worker>> workerIterator = workers.entrySet().iterator();
        while (workerIterator.hasNext()) {
            Map.Entry<Long, Worker> entry = workerIterator.next();
            Worker w = entry.getValue();
            LocalDate curStartDate = w.getStartDate();
            if (curStartDate.isEqual(startDate)) {
                dbUtils.deleteWorkerByStartDate(startDate, currentUserId);
            }
        }
        this.load();

    }

    public ConcurrentHashMap<Long, Worker> getWorkers() {
        return workers;
    }

    public LocalDateTime getInitData() {
        return initData;
    }

    public synchronized void load() {
        DBWorkerUtils dbWorkerUtils = new DBWorkerUtils();
        this.workers.clear();
        this.workers = dbWorkerUtils.getWorkers();
    }
}







