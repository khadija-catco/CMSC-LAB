/* 
 * Class: CMSC203 
 * Instructor: 
 * Description: JavaFX Driver App that collects patient/procedure input and displays output.
 * Due: 02/23/2026
 * Platform/compiler: Java / JavaFX
 * I pledge that I have completed the programming assignment independently. 
 * I have not copied the code from a student or any source. I have not given my code to any student.
 * Print your Name here: Khadija Kamara
 */

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class PatientDriverAppGUI extends Application {

    private Patient patient = null;
    private Procedure procedure1 = null;
    private Procedure procedure2 = null;
    private Procedure procedure3 = null;

    private final TextArea outputArea = new TextArea();

    // ---------------- Required Methods ----------------

    /** displayPatient: displays patient info in output area */
    public void displayPatient(Patient p) {
        outputArea.appendText(p.toString() + "\n\n");
    }

    /** displayProcedure: displays procedure info in output area */
    public void displayProcedure(Procedure proc) {
        outputArea.appendText(proc.toString() + "\n\n");
    }

    /** calculateTotalCharges: sums charges for 3 procedures */
    public double calculateTotalCharges(Procedure p1, Procedure p2, Procedure p3) {
        double total = 0.0;
        if (p1 != null) total += p1.getCharges();
        if (p2 != null) total += p2.getCharges();
        if (p3 != null) total += p3.getCharges();
        return total;
    }

    // ---------------- JavaFX UI ----------------

    @Override
    public void start(Stage stage) {
        stage.setTitle("CMSC203 Assignment #2 - Patient & Procedures");

        // Patient Fields
        TextField tfFirst = new TextField();
        TextField tfMiddle = new TextField();
        TextField tfLast = new TextField();
        TextField tfStreet = new TextField();
        TextField tfCity = new TextField();
        TextField tfState = new TextField();
        TextField tfZip = new TextField();
        TextField tfPhone = new TextField();
        TextField tfEmergName = new TextField();
        TextField tfEmergPhone = new TextField();

        GridPane patientGrid = new GridPane();
        patientGrid.setHgap(10);
        patientGrid.setVgap(8);

        int r = 0;
        patientGrid.add(new Label("First Name:"), 0, r); patientGrid.add(tfFirst, 1, r++);
        patientGrid.add(new Label("Middle Name:"), 0, r); patientGrid.add(tfMiddle, 1, r++);
        patientGrid.add(new Label("Last Name:"), 0, r); patientGrid.add(tfLast, 1, r++);
        patientGrid.add(new Label("Street Address:"), 0, r); patientGrid.add(tfStreet, 1, r++);
        patientGrid.add(new Label("City:"), 0, r); patientGrid.add(tfCity, 1, r++);
        patientGrid.add(new Label("State:"), 0, r); patientGrid.add(tfState, 1, r++);
        patientGrid.add(new Label("ZIP Code:"), 0, r); patientGrid.add(tfZip, 1, r++);
        patientGrid.add(new Label("Phone Number:"), 0, r); patientGrid.add(tfPhone, 1, r++);
        patientGrid.add(new Label("Emergency Name:"), 0, r); patientGrid.add(tfEmergName, 1, r++);
        patientGrid.add(new Label("Emergency Phone:"), 0, r); patientGrid.add(tfEmergPhone, 1, r++);

        Button btnSavePatient = new Button("Save Patient");

        btnSavePatient.setOnAction(e -> {
            patient = new Patient(
                    tfFirst.getText(), tfMiddle.getText(), tfLast.getText(),
                    tfStreet.getText(), tfCity.getText(), tfState.getText(), tfZip.getText(),
                    tfPhone.getText(), tfEmergName.getText(), tfEmergPhone.getText()
            );
            outputArea.appendText("Patient saved.\n\n");
        });

        VBox patientBox = new VBox(10, new Label("Patient Information"), patientGrid, btnSavePatient);
        patientBox.setPadding(new Insets(10));
        patientBox.setStyle("-fx-border-color: black; -fx-border-width: 1;");

        // Procedure fields builder
        VBox procBox1 = buildProcedureBox(1);
        VBox procBox2 = buildProcedureBox(2);
        VBox procBox3 = buildProcedureBox(3);

        // Output area
        outputArea.setEditable(false);
        outputArea.setWrapText(true);
        outputArea.setPrefHeight(260);

        Button btnShowOutput = new Button("Show Output");
        btnShowOutput.setOnAction(e -> showAllOutput());

        VBox outputBox = new VBox(10, new Label("Output"), outputArea, btnShowOutput);
        outputBox.setPadding(new Insets(10));
        outputBox.setStyle("-fx-border-color: black; -fx-border-width: 1;");

        // Layout
        HBox topRow = new HBox(10, patientBox, procBox1);
        HBox midRow = new HBox(10, procBox2, procBox3);

        VBox root = new VBox(12, topRow, midRow, outputBox);
        root.setPadding(new Insets(10));

        stage.setScene(new Scene(root, 980, 820));
        stage.show();
    }

    private VBox buildProcedureBox(int procNumber) {
        TextField tfName = new TextField();
        TextField tfDate = new TextField();
        TextField tfPract = new TextField();
        TextField tfCharge = new TextField();

        GridPane gp = new GridPane();
        gp.setHgap(10);
        gp.setVgap(8);

        gp.add(new Label("Procedure Name:"), 0, 0); gp.add(tfName, 1, 0);
        gp.add(new Label("Date (MM/DD/YYYY):"), 0, 1); gp.add(tfDate, 1, 1);
        gp.add(new Label("Practitioner:"), 0, 2); gp.add(tfPract, 1, 2);
        gp.add(new Label("Charges:"), 0, 3); gp.add(tfCharge, 1, 3);

        Button btnSave = new Button("Save Procedure " + procNumber);

        btnSave.setOnAction(e -> {
            double chargeVal = parseCharge(tfCharge.getText());

            if (procNumber == 1) {
                // Use no-arg constructor, then set all fields
                procedure1 = new Procedure();
                procedure1.setProcedureName(tfName.getText());
                procedure1.setProcedureDate(tfDate.getText());
                procedure1.setPractitionerName(tfPract.getText());
                procedure1.setCharges(chargeVal);
            } else if (procNumber == 2) {
                // Use (name, date) constructor, then set remaining fields
                procedure2 = new Procedure(tfName.getText(), tfDate.getText());
                procedure2.setPractitionerName(tfPract.getText());
                procedure2.setCharges(chargeVal);
            } else {
                // Use all-args constructor
                procedure3 = new Procedure(tfName.getText(), tfDate.getText(), tfPract.getText(), chargeVal);
            }

            outputArea.appendText("Procedure " + procNumber + " saved.\n\n");
        });

        VBox box = new VBox(10, new Label("Procedure " + procNumber), gp, btnSave);
        box.setPadding(new Insets(10));
        box.setStyle("-fx-border-color: black; -fx-border-width: 1;");
        return box;
    }

    private double parseCharge(String text) {
        try {
            return Double.parseDouble(text.trim());
        } catch (Exception ex) {
            return 0.0; // safe default for assignment
        }
    }

    private void showAllOutput() {
        outputArea.appendText("====================================\n");
        outputArea.appendText("The program was developed by a Student: Khadija Kamara <07/27/24>\n");
        outputArea.appendText("====================================\n\n");

        if (patient != null) {
            outputArea.appendText("PATIENT INFORMATION\n");
            outputArea.appendText("------------------------------------\n");
            displayPatient(patient);
        } else {
            outputArea.appendText("No patient has been saved yet.\n\n");
        }

        outputArea.appendText("PROCEDURE INFORMATION\n");
        outputArea.appendText("------------------------------------\n");

        // “tab separated” display line + also show toString blocks
        outputArea.appendText("ProcedureName\tDate\tPractitioner\tCharges\n");

        if (procedure1 != null) {
            outputArea.appendText(formatProcLine(procedure1) + "\n");
        } else {
            outputArea.appendText("(Procedure 1 not saved)\n");
        }

        if (procedure2 != null) {
            outputArea.appendText(formatProcLine(procedure2) + "\n");
        } else {
            outputArea.appendText("(Procedure 2 not saved)\n");
        }

        if (procedure3 != null) {
            outputArea.appendText(formatProcLine(procedure3) + "\n");
        } else {
            outputArea.appendText("(Procedure 3 not saved)\n");
        }

        double total = calculateTotalCharges(procedure1, procedure2, procedure3);
        outputArea.appendText("\nTOTAL CHARGES\n");
        outputArea.appendText("------------------------------------\n");
        outputArea.appendText(String.format("$%,.2f\n\n", total));
    }

    private String formatProcLine(Procedure p) {
        return p.getProcedureName() + "\t" +
               p.getProcedureDate() + "\t" +
               p.getPractitionerName() + "\t" +
               String.format("$%,.2f", p.getCharges());
    }

    public static void main(String[] args) {
        launch(args);
    }
}