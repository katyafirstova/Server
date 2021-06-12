package model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;


public class Message implements Serializable {
    CommandCollection collection;
    Worker worker;
    Integer salary;
    Date date;
    LocalDate startDate;
    Long key;
    ConcurrentHashMap<Long, Worker> workers;
    LocalDateTime initData;

    public Message(CommandCollection collection) {
        this.collection = collection;
    }

    public Message(CommandCollection collection, ConcurrentHashMap<Long, Worker> workers) {
        this.collection = collection;
        this.workers = workers;
    }

    public Message(CommandCollection collection, LocalDateTime initData, ConcurrentHashMap<Long, Worker> workers) {
        this.collection = collection;
        this.initData = initData;
        this.workers = workers;

    }

    public Message(CommandCollection collection, Long key, User user) {
        this.collection = collection;
        this.key = key;
        this.worker = new Worker(user);
    }


    public Message(CommandCollection collection, User user) {
        this.collection = collection;
        this.worker = new Worker(user);
    }

    public Message(CommandCollection collection, Worker worker) {
        this.collection = collection;
        this.worker = worker;
    }

    public Message(CommandCollection collection, int salary, User user) {
        this.collection = collection;
        this.salary = salary;
        this.worker = new Worker(user);
    }

    public Message(CommandCollection collection, Date date, User user) {
        this.collection = collection;
        this.date = date;
        this.worker = new Worker(user);
    }

    public Message(CommandCollection collection, LocalDate startDate, User user) {
        this.collection = collection;
        this.startDate = startDate;
        this.worker = new Worker(user);
    }


    public CommandCollection getCollection() {
        return collection;
    }

    public Worker getWorker() {
        return worker;
    }

    public Integer getSalary() {

        return salary;
    }

    public Date getDate() {
        return date;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Long getKey() {
        return key;
    }

    public ConcurrentHashMap<Long, Worker> getWorkers() {
        return workers;
    }

    public LocalDateTime getInitData() {
        return initData;
    }
}

