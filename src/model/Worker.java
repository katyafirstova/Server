package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


public class Worker implements Serializable {
    private final long max = 100000;
    private Long id;
    private String name;
    private Coordinates coordinates;
    private LocalDateTime creationDate;
    private Integer salary;
    private LocalDate startDate;
    private Date endDate;
    private Status status;
    private Person person;

    public Worker(String name, Coordinates coordinates, Integer salary,
                  LocalDate startDate, Date endDate, Status status, Person person) {
        this.id = (long) (Math.random()*max);
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDateTime.now();
        this.salary = salary;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.person = person;
    }

    public Worker() {
        this.id = (long) (Math.random()*max);
        this.creationDate = LocalDateTime.now();

    }

    @Override
    public String toString() {
        return "Worker{" +
                "id=" + id +
                ", name=" + name +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", salary=" + salary +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status=" + status +
                ", person=" + person +
                '}';
    }


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }


    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        if (salary < 0) {
            throw new IllegalArgumentException("Can't be less than 0");
        }
        if (salary == null) {
            throw new NullPointerException("Can't be null");
        }

    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        if(startDate == null){
            throw new NullPointerException("Can't be null");
        }
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }


}
