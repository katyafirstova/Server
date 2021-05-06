package model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;


public class Message implements Serializable {
    CommandCollection collection;
    Worker worker;
    Integer salary;
    Date date;
    LocalDate startDate;
    Long key;

    public Message(CommandCollection collection, Long key) {
        this.collection = collection;
        this.key = key;
    }

    public Message(CommandCollection collection) {
        this.collection = collection;
    }

    public Message(CommandCollection collection, Worker worker) {
        this.collection = collection;
        this.worker = worker;
    }

    public Message(CommandCollection collection, int salary) {
        this.collection = collection;
        this.salary = salary;
    }

    public Message(CommandCollection collection, Date date) {
        this.collection = collection;
        this.date = date;
    }

    public Message(CommandCollection collection, LocalDate startDate) {
        this.collection = collection;
        this.startDate = startDate;
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
}

