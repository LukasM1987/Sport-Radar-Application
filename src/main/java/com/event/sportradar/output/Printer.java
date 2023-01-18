package com.event.sportradar.output;

public class Printer {

    public static void printResultWithVenue(final int skirmishResult, String awayTeam, String homeTeam, String city, String stadium, String date, String winner, int dateLength, String initColor, String reset) {
        switch (skirmishResult) {
            case 0:
                System.out.println(initColor + "{Event: " + homeTeam + " VS. " + awayTeam +
                        ", Where: " + city + ", " + stadium + ", When: " + date.substring(0, dateLength) +
                        ", Skirmish Result: " + winner + " probably will be the winner.}" + reset);
                break;
            case 1:
                System.out.println(initColor + "{Event: " + homeTeam + " VS. " + awayTeam +
                        ", Where: " + city + ", " + stadium + ", When: " + date.substring(0, dateLength) +
                        ", Skirmish Result: Probably will be draw.}" + reset);
                break;
        }
    }

    public static void printResultWithoutVenue(final int skirmishResult, String awayTeam, String homeTeam, String date, String winner, int dateLength, String initColor, String reset) {
        switch (skirmishResult) {
            case 0:
                System.out.println(initColor + "{Event: " + homeTeam + " VS. " + awayTeam +
                        ", When: " + date.substring(0, dateLength) +
                        ", Skirmish Result: " + winner + " probably will be the winner.}" + reset);
                break;
            case 1:
                System.out.println(initColor + "{Event: " + homeTeam + " VS. " + awayTeam +
                        ", When: " + date.substring(0, dateLength) +
                        ", Skirmish Result: Probably will be draw.}" + reset);
                break;
        }
    }
}
