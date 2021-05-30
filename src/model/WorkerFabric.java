package model;

import java.time.LocalDate;
import java.util.Date;
import db.DBUtils;

/**
 * {@code WorkerFabric} Создание элемента коллекции
 */
public class WorkerFabric {
    public static Worker create(String name, float x, Integer y, Integer salary,
                                LocalDate startDate, Date endDate, Status status, float height, Integer weight,
                                Color hairColor) {
        DBUtils dbUtils = new DBUtils();
        Coordinates coordinates = new Coordinates(x, y);
        int coordinates_id = dbUtils.insertCoordinates(x, y);
        Person person = new Person(height, weight, hairColor);
        int person_id = dbUtils.insertPerson(height, weight, hairColor);
        Worker worker = new Worker(name, coordinates, salary, startDate, endDate, status, person);
        dbUtils.insertWorker(name, coordinates_id, salary, startDate, endDate, status, person_id);
        return worker;
    }
}
