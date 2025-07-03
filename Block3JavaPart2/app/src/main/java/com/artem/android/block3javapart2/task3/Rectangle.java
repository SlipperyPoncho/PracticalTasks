package com.artem.android.block3javapart2.task3;

public class Rectangle implements Shape {

    private final int width;
    private final int height;

    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double perimeter() {
        return height * 2 + width * 2;
    }

    @Override
    public double area() {
        return width * height;
    }
}

