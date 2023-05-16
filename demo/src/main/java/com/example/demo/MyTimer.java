package com.example.demo;

import javafx.animation.AnimationTimer;

public class MyTimer extends AnimationTimer {
    private long previous;
    private IUpdatable[] updatables;

    public MyTimer(IUpdatable... updatables) {
        this.updatables = new IUpdatable[updatables.length];

        for(int i = 0; i < updatables.length; ++i) {
            this.updatables[i] = updatables[i];
        }

    }

    public void handle(long now) {
        if (this.previous == 0L) {
            this.previous = now;
        }

        double ds = (double)(now - this.previous) / 1.0E9;
        this.previous = now;

        for(int i = 0; i < this.updatables.length; ++i) {
            this.updatables[i].update(ds);
        }

    }

    public interface IUpdatable {
        void update(double var1);
    }
}
