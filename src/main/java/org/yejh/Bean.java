package org.yejh;

import java.util.Date;

public class Bean implements Cloneable {
    private Integer number;
    private String name;
    private Date birthday;

    public Bean() {
    }

    public Bean(Integer number, String name, Date birthday) {
        this.number = number;
        this.name = name;
        this.birthday = birthday;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "number=" + number +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}