package com.artem.android.block3javapart2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.artem.android.block3javapart2.task1.LambdaTask;
import com.artem.android.block3javapart2.task2.MovementTask;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LambdaTask.execute();
        MovementTask.makeSteps();
    }
}