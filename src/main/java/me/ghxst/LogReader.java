package me.ghxst;

import java.io.*;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Scanner;

public class LogReader {
    int errorCount = 0;
    int warnCount = 0;
    ArrayList<String> errorLines = new ArrayList<String>();
    ArrayList<String> warnLines = new ArrayList<String>();
    File errorFile = new File("errors_output.txt");

    public void readLog() {
        System.out.println("Please give the directory of the log file:");
        Scanner scanner = new Scanner(System.in);
        String filePath = scanner.nextLine();
        File LogFile = new File(filePath);
        if (!LogFile.exists()) {
            System.out.println("Log file does not exist");
            readLog();
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(LogFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("ERROR")) {
                    errorCount++;
                    errorLines.add(line);
                } else if (line.contains("WARN")) {
                    warnCount++;
                    warnLines.add(line);
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading file!");
        }
        System.out.println("Log file read!:");
        System.out.println("Errors: " + errorCount);
        System.out.println("Warns: " + warnCount);
        while (true){
            System.out.println("Would you like the see the errors? (yes/no)");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("yes")) {
                for (String err : errorLines) {
                    System.out.println(err);
                }
                for (String err : warnLines) {
                    System.out.println(err);
                }
                break;
            } else if (input.equalsIgnoreCase("no")) {
                break;
            } else {
                System.out.println("Invalid input, please type yes or no.");
            }
        }
        while (true){
            System.out.println("Would you like to save the errors? (yes/no)");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("yes")) {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(errorFile))) {
                    for (String err : errorLines) {
                        bw.write(err);
                        bw.newLine();
                    }
                    for (String wrr : warnLines) {
                        bw.write(wrr);
                        bw.newLine();
                    }
                } catch (IOException e) {
                    System.err.println("Error writing file!");
                }
                break;
            } else if (input.equalsIgnoreCase("no")) {
                break;
            } else {
                System.out.println("Invalid input, please type yes or no.");
            }
        }
    }
}
