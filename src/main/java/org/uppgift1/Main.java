package org.uppgift1;

import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        boolean running = true;

        while(running) {

            renderMenu();
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine().toUpperCase();

            switch(input){

                case "1" -> {
                    ElectricityPriceAnalyzer.setPrices(sc);
                    sc.nextLine();
                }
                case "2" -> {
                    ElectricityPriceAnalyzer.minMaxAveragePrice();
                    sc.nextLine();
                }
                case "3" -> {
                    ElectricityPriceAnalyzer.sortByPrice();
                    sc.nextLine();
                }
                case "4" -> {
                    // Argument is the width of the hourspan
                    ElectricityPriceAnalyzer.optimalChargingHours(4);
                    sc.nextLine();
                }
                case "E" -> {
                    sc.close();
                    running = false;
                }
                default -> System.out.println("Error: Incorrect input\n");
            }
        }
    }

    public static void renderMenu() {
        System.out.println("""
                Elpriser
                ========
                1. Inmatning
                2. Min, Max och Medel
                3. Sortera
                4. Bästa Laddningstid (4h)
                e. Avsluta""");
    }
}