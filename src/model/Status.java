package model;

import java.io.Serializable;

public enum Status implements Serializable {
    FIRED("fired"),
    HIRED("hired"),
    RECOMMENDED_FOR_PROMOTION("recommended for promotion"),
    REGULAR("regular"),
    PROBATION("probation");

    String name;

    Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static void main(String[] args) {
        String s = "FIRED";
        System.out.println(Status.fromStr(s).getName());

    }
    public static Status fromStr(String cmd) {
        for (Status s : Status.values()) {
            if (cmd != null && cmd.equals(s.name())) {
                return s;
            }
        }
        return null;
    }
}
