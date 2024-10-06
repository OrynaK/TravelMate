package com.ua.travel_mate;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {

    public int add(String numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return 0;
        }

        List<String> delimiters = new ArrayList<>();
        delimiters.add(",|\n");

        if (numbers.startsWith("//")) {
            int delimiterEndIndex = numbers.indexOf("\n");
            String delimiterSection = numbers.substring(2, delimiterEndIndex);

            Matcher matcher = Pattern.compile("\\[(.*?)\\]").matcher(delimiterSection);
            if (matcher.find()) {
                matcher.reset();
                while (matcher.find()) {
                    delimiters.add(Pattern.quote(matcher.group(1)));
                }
            } else {
                delimiters.add(Pattern.quote(delimiterSection));
            }

            numbers = numbers.substring(delimiterEndIndex + 1);
        }

        String delimiterPattern = String.join("|", delimiters);
        String[] nums = numbers.split(delimiterPattern);

        int sum = 0;
        List<Integer> negativeNumbers = new ArrayList<>();

        for (String num : nums) {
            if (!num.isEmpty()) {
                int number = Integer.parseInt(num.trim());
                if (number < 0) {
                    negativeNumbers.add(number);
                } else if (number < 1000) {
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
