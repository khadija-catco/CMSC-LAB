/* 
 * Class: CMSC203 
 * Instructor: Grigoriy Grinberg
 * Description: Patient class stores patient info and provides formatted build methods.
 * Due: 02/23/2026
 * Platform/compiler: Java / JavaFX
 * I pledge that I have completed the programming assignment independently. 
 * I have not copied the code from a student or any source. I have not given my code to any student.
 * Print your Name here: Khadija Kamara
 */

public class Patient {
    private String firstName;
    private String middleName;
    private String lastName;

    private String streetAddress;
    private String city;
    private String state;
    private String zipCode;

    private String phoneNumber;

    private String emergencyName;
    private String emergencyPhone;

    // No-arg constructor
    public Patient() {
        this.firstName = "";
        this.middleName = "";
        this.lastName = "";
        this.streetAddress = "";
        this.city = "";
        this.state = "";
        this.zipCode = "";
        this.phoneNumber = "";
        this.emergencyName = "";
        this.emergencyPhone = "";
    }

    // Parameterized constructor (name only)
    public Patient(String firstName, String middleName, String lastName) {
        this();
        this.firstName = safe(firstName);
        this.middleName = safe(middleName);
        this.lastName = safe(lastName);
    }

    // Parameterized constructor (all fields)
    public Patient(String firstName, String middleName, String lastName,
                   String streetAddress, String city, String state, String zipCode,
                   String phoneNumber, String emergencyName, String emergencyPhone) {
        this.firstName = safe(firstName);
        this.middleName = safe(middleName);
        this.lastName = safe(lastName);
        this.streetAddress = safe(streetAddress);
        this.city = safe(city);
        this.state = safe(state);
        this.zipCode = safe(zipCode);
        this.phoneNumber = safe(phoneNumber);
        this.emergencyName = safe(emergencyName);
        this.emergencyPhone = safe(emergencyPhone);
    }

    // Small helper to avoid null strings
    private String safe(String s) {
        return (s == null) ? "" : s.trim();
    }

    // Accessors (getters)
    public String getFirstName() { return firstName; }
    public String getMiddleName() { return middleName; }
    public String getLastName() { return lastName; }
    public String getStreetAddress() { return streetAddress; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getZipCode() { return zipCode; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmergencyName() { return emergencyName; }
    public String getEmergencyPhone() { return emergencyPhone; }

    // Mutators (setters)
    public void setFirstName(String firstName) { this.firstName = safe(firstName); }
    public void setMiddleName(String middleName) { this.middleName = safe(middleName); }
    public void setLastName(String lastName) { this.lastName = safe(lastName); }
    public void setStreetAddress(String streetAddress) { this.streetAddress = safe(streetAddress); }
    public void setCity(String city) { this.city = safe(city); }
    public void setState(String state) { this.state = safe(state); }
    public void setZipCode(String zipCode) { this.zipCode = safe(zipCode); }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = safe(phoneNumber); }
    public void setEmergencyName(String emergencyName) { this.emergencyName = safe(emergencyName); }
    public void setEmergencyPhone(String emergencyPhone) { this.emergencyPhone = safe(emergencyPhone); }

    // Build methods
    public String buildFullName() {
        return (firstName + " " + middleName + " " + lastName).trim().replaceAll("\\s+", " ");
    }

    public String buildAddress() {
        return (streetAddress + " " + city + " " + state + " " + zipCode).trim().replaceAll("\\s+", " ");
    }

    public String buildEmergencyContact() {
        return (emergencyName + " " + emergencyPhone).trim().replaceAll("\\s+", " ");
    }

    @Override
    public String toString() {
        return "Patient Name: " + buildFullName() + "\n"
             + "Address: " + buildAddress() + "\n"
             + "Phone Number: " + phoneNumber + "\n"
             + "Emergency Contact: " + buildEmergencyContact();
    }
}