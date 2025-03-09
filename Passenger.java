public class Passenger {
    private String name;
    private String passportNumber;
    private String contactNumber;

    public Passenger(String name, String passportNumber, String contactNumber) {
        this.name = name;
        this.passportNumber = passportNumber;
        this.contactNumber = contactNumber;
    }

    // Getters
    public String getName() { return name; }
    public String getPassportNumber() { return passportNumber; }
    public String getContactNumber() { return contactNumber; }

    @Override
    public String toString() {
        return "Passenger: " + name + " | Passport: " + passportNumber +
               " | Contact: " + contactNumber;
    }
}