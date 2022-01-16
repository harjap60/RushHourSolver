package model;

import java.util.Objects;

public class Car {
    private int length;
    private char name;
    public int direction;
    private int x;
    private int y;


    public Car(int x, int y, int direction, int length, char name){
        this.name = name;
        this.length = length;
        this.x = x;
        this.y = y;
        this.direction = direction;
    }
    //Copy constructor
    public Car(Car c){
        this.length = c.getLength();
        this.name = c.getName();
        this.direction = c.direction;
        this.x = c.getX();
        this.y = c.getY();
    }
    public void setX(int n){
        x = n;
    }

    public void setY(int n){
        y = n;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getLength(){
        return length;
    }

    public char getName(){ return name;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return length == car.length && x == car.x && y == car.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(length, x, y);
    }
}
