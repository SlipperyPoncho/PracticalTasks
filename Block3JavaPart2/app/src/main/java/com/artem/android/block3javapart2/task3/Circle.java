package com.artem.android.block3javapart2.task3;

public class Circle implements Shape{

    private final double diameter;

    public Circle(int diameter) {
        this.diameter = diameter;
    }

    @Override
    public double perimeter() {
        return 2 * Math.PI * (diameter / 2);
    }

    @Override
    public double area() {
        return Math.pow(Math.PI * (diameter / 2), 2);
    }
}
