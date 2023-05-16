package com.example.demo;

import java.util.Arrays;

public class Utilities {
    public Utilities() {
    }

    public static double median(double... values) {
        Arrays.sort(values);
        int index = values.length / 2;
        return values.length % 2 == 1 ? values[index] : (values[index - 1] + values[index + 1]) / 2.0;
    }
}
