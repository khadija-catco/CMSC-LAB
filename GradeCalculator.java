import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class GradeCalculator {

    /*
     * Class: CMSC203
     * CRN: 32324
     * Instructor: Grigoriy Grinberg
     * Description: Grade Calculator that reads a grading configuration file and a student's
     *              category scores from an input file, validates input, computes category
     *              averages and an overall weighted average, determines the final letter
     *              grade (optionally with +/-), displays results to the console, and writes
     *              the same summary to an output report file.
     * Due: (Enter the due date from Blackboard)
     * Platform/compiler: Eclipse / Java
     * I pledge that I have completed the programming assignment independently.
     * I have not copied the code from a student or any source. I have not given my code
     * to any student.
     * Print your Name here: Khadija Kamara
     */

    public static void main(String[] args) {

        // =========================
        //   CONSTANT FILENAMES
        // =========================
        String configFileName = "gradeconfig.txt";
        String inputFileName = "grades_input.txt";
        String outputFileName = "grades_report.txt";

        System.out.println("========================================");
        System.out.println("   CMSC203 Project 1 - Grade Calculator");
        System.out.println("========================================\n");

        // =========================
        //   CONFIG DEFAULTS
        // =========================
        boolean defaultConfigUsed = false;

        String courseName = "CMSC203 Computer Science I";
        int numCategories = 3;

        // Up to 10 categories (NO arrays / ArrayList)
        String cat1Name = "Projects"; int cat1Weight = 40;
        String cat2Name = "Quizzes";  int cat2Weight = 30;
        String cat3Name = "Exams";    int cat3Weight = 30;
        String cat4Name = ""; int cat4Weight = 0;
        String cat5Name = ""; int cat5Weight = 0;
        String cat6Name = ""; int cat6Weight = 0;
        String cat7Name = ""; int cat7Weight = 0;
        String cat8Name = ""; int cat8Weight = 0;
        String cat9Name = ""; int cat9Weight = 0;
        String cat10Name = ""; int cat10Weight = 0;

        // Try to load config
        System.out.println("Loading configuration from " + configFileName + " ...");
        File configFile = new File(configFileName);

        if (configFile.exists() && configFile.isFile()) {
            Scanner configScanner = null;
            try {
                configScanner = new Scanner(configFile);

                // Course name line (allow spaces)
                if (configScanner.hasNextLine()) {
                    String line = configScanner.nextLine().trim();
                    if (line.length() > 0) courseName = line;
                } else {
                    throw new IllegalArgumentException("Missing course name.");
                }

                // Number of categories
                if (configScanner.hasNextInt()) {
                    numCategories = configScanner.nextInt();
                    configScanner.nextLine(); // consume rest of line
                } else {
                    throw new IllegalArgumentException("Invalid number of categories.");
                }

                if (numCategories < 1 || numCategories > 10) {
                    throw new IllegalArgumentException("Number of categories must be 1..10.");
                }

                // Read each category line: name weight
                int i = 1;
                int weightSum = 0;

                while (i <= numCategories) {
                    if (!configScanner.hasNext()) {
                        throw new IllegalArgumentException("Config missing category name.");
                    }
                    String cname = configScanner.next().trim();

                    if (!configScanner.hasNextInt()) {
                        throw new IllegalArgumentException("Config missing/invalid weight for " + cname);
                    }
                    int w = configScanner.nextInt();
                    weightSum += w;

                    // Assign into the correct variables (NO arrays)
                    if (i == 1) { cat1Name = cname; cat1Weight = w; }
                    else if (i == 2) { cat2Name = cname; cat2Weight = w; }
                    else if (i == 3) { cat3Name = cname; cat3Weight = w; }
                    else if (i == 4) { cat4Name = cname; cat4Weight = w; }
                    else if (i == 5) { cat5Name = cname; cat5Weight = w; }
                    else if (i == 6) { cat6Name = cname; cat6Weight = w; }
                    else if (i == 7) { cat7Name = cname; cat7Weight = w; }
                    else if (i == 8) { cat8Name = cname; cat8Weight = w; }
                    else if (i == 9) { cat9Name = cname; cat9Weight = w; }
                    else { cat10Name = cname; cat10Weight = w; }

                    i++;
                }

                // Validate weights sum to 100
                if (weightSum != 100) {
                    throw new IllegalArgumentException("Category weights must sum to 100, got " + weightSum);
                }

                System.out.println("Configuration loaded successfully.\n");

            } catch (Exception ex) {
                defaultConfigUsed = true;

                // Reset to defaults
                courseName = "CMSC203 Computer Science I";
                numCategories = 3;
                cat1Name = "Projects"; cat1Weight = 40;
                cat2Name = "Quizzes";  cat2Weight = 30;
                cat3Name = "Exams";    cat3Weight = 30;
                cat4Name = ""; cat4Weight = 0;
                cat5Name = ""; cat5Weight = 0;
                cat6Name = ""; cat6Weight = 0;
                cat7Name = ""; cat7Weight = 0;
                cat8Name = ""; cat8Weight = 0;
                cat9Name = ""; cat9Weight = 0;
                cat10Name = ""; cat10Weight = 0;

                System.out.println("Config file invalid. Using DEFAULT configuration.");
                System.out.println("Reason: " + ex.getMessage() + "\n");

            } finally {
                if (configScanner != null) configScanner.close();
            }
        } else {
            defaultConfigUsed = true;
            System.out.println("Config file missing. Using DEFAULT configuration.\n");
        }

        System.out.println("Using input file: " + inputFileName);
        System.out.println("Using output file: " + outputFileName + "\n");

        // =========================
        //   READ INPUT FILE
        // =========================
        File inputFile = new File(inputFileName);
        if (!inputFile.exists() || !inputFile.isFile()) {
            System.out.println("ERROR: Missing or unreadable input file: " + inputFileName);
            System.out.println("Exiting gracefully.");
            return;
        }

        String studentFirstName = "";
        String studentLastName = "";

        // Category averages (NO arrays)
        double avg1 = 0, avg2 = 0, avg3 = 0, avg4 = 0, avg5 = 0, avg6 = 0, avg7 = 0, avg8 = 0, avg9 = 0, avg10 = 0;
        boolean ok1 = false, ok2 = false, ok3 = false, ok4 = false, ok5 = false, ok6 = false, ok7 = false, ok8 = false, ok9 = false, ok10 = false;

        double overallAverage = 0.0;

        System.out.println("Reading student scores...\n");

        Scanner inputScanner = null;
        try {
            inputScanner = new Scanner(inputFile);

            // Student first + last name (each on its own line per example)
            if (inputScanner.hasNextLine()) studentFirstName = inputScanner.nextLine().trim();
            if (inputScanner.hasNextLine()) studentLastName = inputScanner.nextLine().trim();

            if (studentFirstName.length() == 0 || studentLastName.length() == 0) {
                System.out.println("ERROR: Student name missing/invalid in input file.");
                System.out.println("Exiting gracefully.");
                return;
            }

            System.out.println("Student: " + studentFirstName + " " + studentLastName);
            System.out.println("Course: " + courseName + "\n");
            System.out.println("Category Results:");

            // Loop through categories defined in config (required loop)
            int i = 1;
            while (i <= numCategories) {

                // Expected category name/weight for this index
                String expectedName = "";
                int expectedWeight = 0;

                if (i == 1) { expectedName = cat1Name; expectedWeight = cat1Weight; }
                else if (i == 2) { expectedName = cat2Name; expectedWeight = cat2Weight; }
                else if (i == 3) { expectedName = cat3Name; expectedWeight = cat3Weight; }
                else if (i == 4) { expectedName = cat4Name; expectedWeight = cat4Weight; }
                else if (i == 5) { expectedName = cat5Name; expectedWeight = cat5Weight; }
                else if (i == 6) { expectedName = cat6Name; expectedWeight = cat6Weight; }
                else if (i == 7) { expectedName = cat7Name; expectedWeight = cat7Weight; }
                else if (i == 8) { expectedName = cat8Name; expectedWeight = cat8Weight; }
                else if (i == 9) { expectedName = cat9Name; expectedWeight = cat9Weight; }
                else { expectedName = cat10Name; expectedWeight = cat10Weight; }

                // Read category name from file
                if (!inputScanner.hasNextLine()) {
                    System.out.println("ERROR: Input file ended early while reading categories.");
                    break;
                }
                String readCatName = inputScanner.nextLine().trim();

                // Validate category name match
                if (!readCatName.equals(expectedName)) {
                    System.out.println("  ERROR: Expected category '" + expectedName + "' but found '" + readCatName + "'. Skipping this category.");

                    // Skip count line
                    if (inputScanner.hasNextLine()) {
                        inputScanner.nextLine(); // count
                        // Skip scores line too (best-effort)
                        if (inputScanner.hasNextLine()) inputScanner.nextLine();
                    }
                    i++;
                    continue;
                }

                // Read number of scores
                if (!inputScanner.hasNextLine()) {
                    System.out.println("  ERROR: Missing score count for " + expectedName + ". Skipping.");
                    i++;
                    continue;
                }

                String countStr = inputScanner.nextLine().trim();
                int count = -1;
                try {
                    count = Integer.parseInt(countStr);
                } catch (Exception e) {
                    System.out.println("  ERROR: Invalid score count '" + countStr + "' for " + expectedName + ". Skipping.");
                    if (inputScanner.hasNextLine()) inputScanner.nextLine(); // scores line
                    i++;
                    continue;
                }

                if (count <= 0) {
                    System.out.println("  ERROR: Score count must be > 0 for " + expectedName + ". Skipping.");
                    if (inputScanner.hasNextLine()) inputScanner.nextLine(); // scores line
                    i++;
                    continue;
                }

                // Read the line of scores
                if (!inputScanner.hasNextLine()) {
                    System.out.println("  ERROR: Missing scores line for " + expectedName + ". Skipping.");
                    i++;
                    continue;
                }

                String scoresLine = inputScanner.nextLine().trim();
                Scanner scoreLineScanner = new Scanner(scoresLine);

                double sum = 0.0;
                int readCount = 0;

                // Loop through scores within a category (required loop)
                while (readCount < count) {
                    if (!scoreLineScanner.hasNext()) break;

                    String token = scoreLineScanner.next();
                    double score;

                    try {
                        score = Double.parseDouble(token);
                    } catch (Exception e) {
                        score = 0.0; // defensive
                    }

                    if (score < 0) score = 0.0;
                    if (score > 100) score = 100.0;

                    sum += score;
                    readCount++;
                }

                scoreLineScanner.close();

                if (readCount < count) {
                    System.out.println("  ERROR: Not enough scores provided for " + expectedName +
                                       " (expected " + count + ", got " + readCount + "). Using what was provided.");
                    if (readCount == 0) {
                        i++;
                        continue;
                    }
                    count = readCount;
                }

                double categoryAvg = sum / count;
                overallAverage += categoryAvg * (expectedWeight / 100.0);

                // Store average + ok flag (NO arrays)
                if (i == 1) { avg1 = categoryAvg; ok1 = true; }
                else if (i == 2) { avg2 = categoryAvg; ok2 = true; }
                else if (i == 3) { avg3 = categoryAvg; ok3 = true; }
                else if (i == 4) { avg4 = categoryAvg; ok4 = true; }
                else if (i == 5) { avg5 = categoryAvg; ok5 = true; }
                else if (i == 6) { avg6 = categoryAvg; ok6 = true; }
                else if (i == 7) { avg7 = categoryAvg; ok7 = true; }
                else if (i == 8) { avg8 = categoryAvg; ok8 = true; }
                else if (i == 9) { avg9 = categoryAvg; ok9 = true; }
                else { avg10 = categoryAvg; ok10 = true; }

                System.out.printf("  %s (%d%%): average = %.2f%n", expectedName, expectedWeight, categoryAvg);

                i++;
            }

        } catch (FileNotFoundException e) {
            System.out.println("ERROR: Unable to read input file.");
            System.out.println("Exiting gracefully.");
            return;
        } finally {
            if (inputScanner != null) inputScanner.close();
        }

        System.out.println();

        // =========================
        //   KEYBOARD INPUT (Y/N)
        // =========================
        Scanner keyboard = new Scanner(System.in);

        boolean plusMinusEnabled = false;

        // Input validation loop (required loop)
        while (true) {
            System.out.print("Apply +/- grading? (Y/N): ");
            String yn = keyboard.nextLine().trim();

            if (yn.equalsIgnoreCase("Y")) {
                plusMinusEnabled = true;
                break;
            } else if (yn.equalsIgnoreCase("N")) {
                plusMinusEnabled = false;
                break;
            } else {
                System.out.println("Invalid input. Please enter Y or N.");
            }
        }

        // =========================
        //   LETTER GRADE (BASE)
        // =========================
        String baseLetter;
        if (overallAverage >= 90.0) baseLetter = "A";
        else if (overallAverage >= 80.0) baseLetter = "B";
        else if (overallAverage >= 70.0) baseLetter = "C";
        else if (overallAverage >= 60.0) baseLetter = "D";
        else baseLetter = "F";

        String finalLetter = baseLetter;

        // =========================
        //   +/- LOGIC (DOCUMENTED)
        // =========================
        // A+: >= 97
        // A : 93–96.99
        // A-: 90–92.99
        // B+: >= 87, B: 83–86.99, B-: 80–82.99
        // C+: >= 77, C: 73–76.99, C-: 70–72.99
        // D+: >= 67, D: 63–66.99, D-: 60–62.99
        if (plusMinusEnabled) {
            if (baseLetter.equals("A")) {
                if (overallAverage >= 97.0) finalLetter = "A+";
                else if (overallAverage >= 93.0) finalLetter = "A";
                else finalLetter = "A-";
            } else if (baseLetter.equals("B")) {
                if (overallAverage >= 87.0) finalLetter = "B+";
                else if (overallAverage >= 83.0) finalLetter = "B";
                else finalLetter = "B-";
            } else if (baseLetter.equals("C")) {
                if (overallAverage >= 77.0) finalLetter = "C+";
                else if (overallAverage >= 73.0) finalLetter = "C";
                else finalLetter = "C-";
            } else if (baseLetter.equals("D")) {
                if (overallAverage >= 67.0) finalLetter = "D+";
                else if (overallAverage >= 63.0) finalLetter = "D";
                else finalLetter = "D-";
            } else {
                finalLetter = "F";
            }
        }

        System.out.printf("%nOverall numeric average: %.2f%n", overallAverage);
        System.out.println("Base letter grade: " + baseLetter);
        System.out.println("Final letter grade: " + finalLetter);
        System.out.println();

        // =========================
        //   WRITE REPORT FILE
        // =========================
        PrintWriter out = null;
        try {
            out = new PrintWriter(outputFileName);

            out.println("========================================");
            out.println("   CMSC203 Project 1 - Grade Calculator");
            out.println("========================================");
            out.println();

            out.println("Course name: " + courseName);
            out.println("Student name: " + studentFirstName + " " + studentLastName);
            out.println();

            out.println("Category Results:");
            int i = 1;
            while (i <= numCategories) {

                String cname = "";
                int w = 0;
                double a = 0.0;
                boolean ok = false;

                if (i == 1) { cname = cat1Name; w = cat1Weight; a = avg1; ok = ok1; }
                else if (i == 2) { cname = cat2Name; w = cat2Weight; a = avg2; ok = ok2; }
                else if (i == 3) { cname = cat3Name; w = cat3Weight; a = avg3; ok = ok3; }
                else if (i == 4) { cname = cat4Name; w = cat4Weight; a = avg4; ok = ok4; }
                else if (i == 5) { cname = cat5Name; w = cat5Weight; a = avg5; ok = ok5; }
                else if (i == 6) { cname = cat6Name; w = cat6Weight; a = avg6; ok = ok6; }
                else if (i == 7) { cname = cat7Name; w = cat7Weight; a = avg7; ok = ok7; }
                else if (i == 8) { cname = cat8Name; w = cat8Weight; a = avg8; ok = ok8; }
                else if (i == 9) { cname = cat9Name; w = cat9Weight; a = avg9; ok = ok9; }
                else { cname = cat10Name; w = cat10Weight; a = avg10; ok = ok10; }

                if (ok) out.printf("  %s (%d%%): average = %.2f%n", cname, w, a);
                else out.printf("  %s (%d%%): average = N/A%n", cname, w);

                i++;
            }

            out.println();
            out.printf("Overall numeric average: %.2f%n", overallAverage);
            out.println("Base letter grade: " + baseLetter);
            out.println("Final letter grade: " + finalLetter);
            out.println("Apply +/- grading: " + (plusMinusEnabled ? "Yes" : "No"));
            out.println("Default configuration used: " + (defaultConfigUsed ? "Yes" : "No"));

            System.out.println("Summary written to " + outputFileName);
            System.out.println("Program complete. Goodbye!");

        } catch (Exception e) {
            System.out.println("ERROR: Could not write output file: " + outputFileName);
        } finally {
            if (out != null) out.close();
            keyboard.close();
        }
    }
}
