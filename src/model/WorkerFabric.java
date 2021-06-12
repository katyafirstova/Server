package model;

import java.time.LocalDate;
import java.util.Date;

/**
 * {@code WorkerFabric} Создание элемента коллекции
 */
public class WorkerFabric {
    public static Worker create(String name, float x, Integer y, Integer salary,
                                LocalDate startDate, Date endDate, Status status, float height, Integer weight,
                                Color hairColor, User user) {
        Coordinates coordinates = new Coordinates(x, y);
        Person person = new Person(height, weight, hairColor);
        Worker worker = new Worker(name, coordinates, salary, startDate, endDate, status, person, user);
        return worker;
    }
}
