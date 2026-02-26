/* 
 * Class: CMSC203 
 * Instructor: Grigoriy Grinberg
 * Description: Procedure class stores medical procedure details and cost.
 * Due: 02/23/2026
 * Platform/compiler: Java / JavaFX
 * I pledge that I have completed the programming assignment independently. 
 * I have not copied the code from a student or any source. I have not given my code to any student.
 * Print your Name here: Khadija Kamara
 */

public class Procedure {
    private String procedureName;
    private String procedureDate;
    private String practitionerName;
    private double charges;

    // No-arg constructor
    public Procedure() {
        this.procedureName = "";
        this.procedureDate = "";
        this.practitionerName = "";
        this.charges = 0.0;
    }

    // Parameterized constructor (name + date)
    public Procedure(String procedureName, String procedureDate) {
        this();
        this.procedureName = safe(procedureName);
        this.procedureDate = safe(procedureDate);
    }

    // Parameterized constructor (all fields)
    public Procedure(String procedureName, String procedureDate, String practitionerName, double charges) {
        this.procedureName = safe(procedureName);
        this.procedureDate = safe(procedureDate);
        this.practitionerName = safe(practitionerName);
        this.charges = charges;
    }

    private String safe(String s) {
        return (s == null) ? "" : s.trim();
    }

    // Accessors (getters)
    public String getProcedureName() { return procedureName; }
    public String getProcedureDate() { return procedureDate; }
    public String getPractitionerName() { return practitionerName; }
    public double getCharges() { return charges; }

    // Mutators (setters)
    public void setProcedureName(String procedureName) { this.procedureName = safe(procedureName); }
    public void setProcedureDate(String procedureDate) { this.procedureDate = safe(procedureDate); }
    public void setPractitionerName(String practitionerName) { this.practitionerName = safe(practitionerName); }
    public void setCharges(double charges) { this.charges = charges; }

    @Override
    public String toString() {
        return "Procedure: " + procedureName + "\n"
             + "Date: " + procedureDate + "\n"
             + "Practitioner: " + practitionerName + "\n"
             + String.format("Charges: $%,.2f", charges);
    }
}