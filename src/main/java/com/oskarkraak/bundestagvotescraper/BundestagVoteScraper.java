package com.oskarkraak.bundestagvotescraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BundestagVoteScraper {

    private static final String DEFAULT_OUTPUT_PATH = "./";
    private static final String DEFAULT_FILE_NAME = "BundestagVotes.xlsx";
    private static final boolean DEFAULT_VERBOSE = false;

    public static void main(String[] args) {
        // Handle input
        String outputPath = DEFAULT_OUTPUT_PATH;
        String fileName = DEFAULT_FILE_NAME;
        boolean verbose = DEFAULT_VERBOSE;
        for (String arg : args) {
            String param = arg.split("=")[0];
            switch (param) {
                case "-h":
                case "--help":
                    System.out.println("Any of these parameters are optional. Their order does not matter.\n" +
                            "If not specified the output will be saved in the working directory.");
                    System.out.println("-v   gives verbose output   (optional)");
                    System.out.println("-path=\"[Path]\"   saves the output file in the folder [Path]   (optional)");
                    System.out.println("-name=\"[Name]\"   names the output file [Name]   (optional)");
                    System.out.println("Example (replace scraper.jar with your jar file): \n" +
                            "java -jar scraper.jar -path=\"C:\\Users\\Public\\Documents\\\" -name=\"votes.xlsx\" -v");
                    return;
                case "-v":
                case "--verbose":
                    verbose = true;
                    break;
                case "-path":
                    outputPath = arg.split("=")[1] + "\\";
                    break;
                case "-name":
                    fileName = arg.split("=")[1];
                    break;
                default:
                    System.out.println("Invalid parameter: " + param);
                    System.out.println("Use -h to get help.");
                    return;
            }
        }
        new BundestagVoteScraper(outputPath, fileName, verbose);
    }

    public BundestagVoteScraper(String outputPath, String fileName, boolean verbose) {
        VoteScraper votes = new VoteScraper();
        ArrayList<XLSX> files = new ArrayList<>();
        int i = 1;
        for (Vote vote : votes) {
            XLSX results = vote.getResults();
            if (results != null) {
                if (i != 1)
                    // Remove first line (heading)
                    results.getWorkbook().getSheetAt(0).removeRow(results.getWorkbook().getSheetAt(0).getRow(0));
                files.add(results);
            }
            if (verbose) {
                // Console output
                System.out.println(i++ + " " + vote.getDate() + " " + vote.getName());
            }
        }
        // Concatenate XLSX files
        List<List<Integer>> rowsToSkip = new ArrayList<>();
        XLSX[] arr = new XLSX[files.size()];
        for (int j = 0; j < files.size(); j++) {
            arr[j] = files.get(j);
            // Create List to skip the first row
            ArrayList<Integer> a = new ArrayList<>();
            if (j != 0)
                a.add(0);
            rowsToSkip.add(a);
            // Remove last row (1,048,575) if it exists
            // This is necessary because in one file this row exists for no reason, which messes up the XLSX.concatenate() method
            if (arr[j].getWorkbook().getSheetAt(0).getLastRowNum() == 1048575) {
                arr[j].getWorkbook().getSheetAt(0).removeRow(arr[j].getWorkbook().getSheetAt(0).getRow(1048575));
            }
        }
        XLSX concatenated = XLSX.concatenate(arr, rowsToSkip);
        // Write file
        try {
            concatenated.write(outputPath + fileName);
            System.out.println("Data has been written to " + outputPath + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
