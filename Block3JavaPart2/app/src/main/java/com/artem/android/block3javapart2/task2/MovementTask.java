package com.artem.android.block3javapart2.task2;

import android.graphics.Point;
import android.util.Log;

public class MovementTask {
    private static final String TAG = "MovementTask";

    private enum Directions {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    private static void makeStep(Point p, Directions direction) {
        switch (direction) {
            case UP:
                p.y++;
                break;
            case DOWN:
                p.y--;
                break;
            case LEFT:
                p.x--;
                break;
            case RIGHT:
                p.x++;
                break;
        }
    }

    public static void makeSteps() {
        Point location = new Point(0, 0);

        Directions[] directions = new Directions[] {
                Directions.UP,
                Directions.UP,
                Directions.LEFT,
                Directions.DOWN,
                Directions.LEFT,
                Directions.DOWN,
                Directions.DOWN,
                Directions.RIGHT,
                Directions.RIGHT,
                Directions.DOWN,
                Directions.RIGHT
        };

        for (Directions direction : directions) {
            makeStep(location, direction);
            Log.i(TAG, "location = (" + location.x + ", " + location.y + ")");
        }
    }
}

