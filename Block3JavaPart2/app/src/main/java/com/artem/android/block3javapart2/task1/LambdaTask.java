package com.artem.android.block3javapart2.task1;

import android.util.Log;

public class LambdaTask {
    private static final String TAG = "LambdaTask";

    interface Printable { void print(); }

    public static void execute() {
        Printable myClosure = () -> Log.i(TAG, "i love Java");
        myClosure.print();
        repeatTask(10, myClosure::print);
    }

    public static void repeatTask(int times, Runnable task) {
        for (int i = 0; i < times; i++) {
            task.run();
        }
    }
}
