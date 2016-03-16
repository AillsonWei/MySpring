package com.myspring.bean;

import java.io.Serializable;

/**
 * Created by AillsonWei on 2016/3/16.
 */
public class Student implements Serializable{
    private String name;
    private Integer age;
    private Computer computer;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Computer getComputer() {
        return computer;
    }

    public void setComputer(Computer computer) {
        this.computer = computer;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", computer=" + computer +
                '}';
    }
}
