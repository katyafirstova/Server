package model;


import java.io.Serializable;

public class Coordinates implements Serializable {
    private Float x;
    private Integer y;

    public Coordinates(float x, Integer y) {
        this.setX(x);
        this.setY(y);
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        if(y == null) {
            throw new IllegalArgumentException("Can't be null");
        }
        if(y > 92) {
            throw new IllegalArgumentException("Can't be more than 92");
        }
        this.y = y;
    }
}
