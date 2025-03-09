public class Flight {
    private String flightNumber;
    private String origin;
    private String destination;
    private String departureTime;
    private int availableSeats;
    private double price;

    public Flight(String flightNumber, String origin, String destination,
                  String departureTime, int totalSeats, double price) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.availableSeats = totalSeats;
        this.price = price;
    }

    // Getters
    public String getFlightNumber() { return flightNumber; }
    public String getOrigin() { return origin; }
    public String getDestination() { return destination; }
    public String getDepartureTime() { return departureTime; }
    public int getAvailableSeats() { return availableSeats; }
    public double getPrice() { return price; }

    // Book a seat
    public boolean bookSeat() {
        if (availableSeats > 0) {
            availableSeats--;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Flight " + flightNumber + ": " + origin + " to " + destination +
               " at " + departureTime + ", Seats: " + availableSeats + ", Price: $" + price;
    }
}