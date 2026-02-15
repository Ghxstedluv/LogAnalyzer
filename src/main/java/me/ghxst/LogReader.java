package me.ghxst;

import java.io.*;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Scanner;

public class LogReader {

    FileReader fileReader;
    FileWriter fileWriter;
    int errorCount = 0;
    int warnCount = 0;
    ArrayList<String> errorLines = new ArrayList<String>();
    File errorFile = new File("errors_output.txt");

    public void readLog(){
        System.out.println("Please give the directory of the log file:");
        Scanner scanner = new Scanner(System.in);
        String filePath = scanner.nextLine();
        File LogFile = new File(filePath);
        try (BufferedReader br = new BufferedReader(new FileReader(LogFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("ERROR")) {
                    errorCount++;
                    errorLines.add(line);
                }
                else if (line.contains("WARN")) warnCount++;
            }

        } catch (IOException e) {
            System.err.println("Error reading file!");
        }
        System.out.println("Log file read!:");
        System.out.println("Errors: " + errorCount);
        System.out.println("Warns: " + warnCount);
        System.out.println("Would you like the see the errors? (yes/no)");
        if (scanner.nextLine().equals("yes")){
            for(String err : errorLines) {
                System.out.println(err);
            }
        }
        System.out.println("Would you like to save the errors? (yes/no)");
        if (scanner.nextLine().equals("yes")){
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(errorFile))){
                for (String err : errorLines) {
                    bw.write(err);
                    bw.newLine();
                }
            } catch (IOException e) {
                System.err.println("Error writing file!");
            }
        }

    }
}
