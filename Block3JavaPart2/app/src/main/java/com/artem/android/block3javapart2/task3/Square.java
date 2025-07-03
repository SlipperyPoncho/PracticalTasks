package com.artem.android.block3javapart2.task3;

public class Square implements Shape {

    private final int sideLength;

    public Square(int length) {
        this.sideLength = length;
    }

    @Override
    public double perimeter() {
        return sideLength * 4;
    }

    @Override
    public double area() {
        return sideLength * sideLength;
    }
}
