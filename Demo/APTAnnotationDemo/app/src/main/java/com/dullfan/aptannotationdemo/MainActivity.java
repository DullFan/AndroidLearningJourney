package com.dullfan.aptannotationdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.dullfan.annotations.AptAnnotation;
import com.dullfan.api.MyAptApi;
import com.google.android.material.button.MaterialButton;

@AptAnnotation(desc = "我在MainActivity 上面的注解")
public class MainActivity extends AppCompatActivity {
    @AptAnnotation(desc = "我在onCreate 上面的注解")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyAptApi.init();
    }
}