package org.uppgift1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class ElectricityPriceAnalyzer {

    private static int minPrice;
    private static int maxPrice;
    private static final LinkedHashMap<String, Integer> map = new LinkedHashMap<>();

    public static void setPrices(Scanner sc) {

        System.out.println("Read from file? Y/N");
        map.clear();

        if (sc.nextLine().equalsIgnoreCase("N")) {
            for (int i = 0; i <= 23; i++) {

                // just gonna keep my first solution here since it was messy, and therefore now funny
                // String hour = (i < 10 ? "0" + i : i) + "-" + (i + 1 < 10 ? "0" + (i + 1) : i + 1);
                // if (i == 23) hour = i + "-" + "00";

                System.out.println("Type in the price for " + formatHours(i));
                int price = 0;
                try {
                    price = sc.nextInt();
                } catch (InputMismatchException e) {
                    e.printStackTrace();
                }
                map.put(formatHours(i), price);
            }

        } else {
            BufferedReader fileReader;
            String currentLine;

            try {
                fileReader = new BufferedReader(new FileReader("src/priser.csv"));

                while ((currentLine = fileReader.readLine()) != null) {
                    String[] line = currentLine.split(",");
                    map.put(line[0], Integer.parseInt(line[1]));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void sortByPrice() {

        map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(hour -> System.out.println(hour.getKey() + ": " + hour.getValue()));
    }

    public static void minMaxAveragePrice() {

        double average = map.values().stream()
                .mapToDouble(i -> i)
                .sum() / 24;

        minPrice = map.values()
                .stream()
                .mapToInt(Integer::intValue)
                .min()
                .orElse(0);

        maxPrice = map.values()
                .stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(0);

        System.out.println("\nMin price(s)\n -------------------------");
        map.entrySet()
                .stream()
                .filter(priceAtHour -> priceAtHour.getValue() == minPrice)
                .forEach(System.out::println);

        System.out.println("\nMax price(s)\n -------------------------");
        map.entrySet()
                .stream()
                .filter(priceAtHour -> priceAtHour.getValue() == maxPrice)
                .forEach(System.out::println);

        System.out.println("\n\nAverage: " + String.format("%.2f", average));

    }

    public static void optimalChargingHours(int hourSpan) {

        Integer[] prices = map.values().toArray(new Integer[0]);

        int totalPrice = Integer.MAX_VALUE;
        int fromIndex = 0;
        int sum = 0;

        // Implemented sliding window approach
        // sum gets set to the elements covered by the span(window)
        // then foreach iteration it subtracts sum with the indexvalue "behind" i
        // and add the value from the new end index
        // not really necessary in this case, but I learned something ¯\_(ツ)_/¯

        for (int i = 0; i< hourSpan; i++) {
            sum += prices[i];
        }

        for (int i = 1; i < prices.length; i++) {

            int endIndex = (i + hourSpan -1) % prices.length;
            sum = sum - prices[i -1] + prices[endIndex];

            if (sum < totalPrice) {
                totalPrice = sum;
                fromIndex = i;
            }
        }

        System.out.println("Optimal hours: " + formatHours(fromIndex, hourSpan) +
                "\nTotal cost: " + totalPrice +
                "\nAverage price: " + (double) totalPrice / hourSpan + "öre/h");

    }

    private static String formatHours(Integer hour){
        return String.format("%02d-%02d", hour, (hour +1) % 24);
    }
    private static String formatHours(Integer fromHour, Integer toHour){
        // Overloaded method to format the timespan from one hour to X hours.
        return String.format("%02d-%02d", fromHour % 24, (fromHour + toHour) % 24);
    }
}
