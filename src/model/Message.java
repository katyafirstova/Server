package model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;


public class Message implements Serializable {
    CommandCollection collection;
    Worker work;
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

    public Message(CommandCollection collection, Worker work) {
        this.work = work;
    }

    public Message(CommandCollection collection, int salary) {
        this.salary = salary;
    }

    public Message(CommandCollection collection, Date date) {
        this.date = date;
    }

    public Message(CommandCollection collection, LocalDate startDate) {
        this.startDate = startDate;
    }




}

