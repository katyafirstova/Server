package model;

import java.io.Serializable;

public class Person implements Serializable {
    private float height;
    private Integer weight;
    private Color hairColor;

    public Person(float height, Integer weight, Color hairColor) {
        this.height = height;
        this.weight = weight;
        this.hairColor = hairColor;
    }

    @Override
    public String toString() {
        return "Person{" +
                "height=" + height +
                ", weight=" + weight +
                ", hairColor=" + hairColor +
                '}';
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        if(height < 0) {
            throw new IllegalArgumentException("Can't be less than 0");
        }
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        if(weight < 0) {
            throw new IllegalArgumentException("Can't be less than 0");
        }
        this.weight = weight;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public void setHairColor(Color hairColor) {
        this.hairColor = hairColor;
    }
}
