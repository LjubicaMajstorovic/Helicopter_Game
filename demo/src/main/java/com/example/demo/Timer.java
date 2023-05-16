package com.example.demo;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class Timer extends AnimationTimer {
    private Label label;
    private long startTime;

    public Timer(Label label){
        this.label = label;
        startTime = System.nanoTime();
    }
    @Override
    public void handle(long now){
        long elapsed = (now - startTime)/1_000_000_000;
        long seconds = elapsed%60;
        long minutes = elapsed/60;
        String min;
        String sec;
        if(minutes < 10){
            min = "0" + minutes;
        } else {
            min = "" + minutes;
        }
        if(seconds < 10){
            sec = "0" +seconds;
        } else{
            sec = "" +seconds;
        }

        label.setText("" + min + ":" + sec);
    }
}
