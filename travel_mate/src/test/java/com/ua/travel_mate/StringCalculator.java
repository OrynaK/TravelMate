package com.ua.travel_mate;

import java.util.ArrayList;
import java.util.List;

public class StringCalculator {
    public int add(String numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return 0;
        }

        //3
        numbers = numbers.replace("\n", ",");
        //4
        // -
        String[] nums = numbers.split(",");
        int sum = 0;
        //5
        List<Integer> negativeNumbers = new ArrayList<>();

        for (String num : nums) {
            if (!num.isEmpty()) {
                int number = Integer.parseInt(num.trim());
                if (number < 0) {
                    negativeNumbers.add(number);
                } else if (number < 1000) { //6
                    sum += number;
                }
            }
        }

        if (!negativeNumbers.isEmpty()) {
            throw new IllegalArgumentException("Negatives not allowed: " + negativeNumbers);
        }

        return sum;
    }

}
