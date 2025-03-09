public class Reservation {
    private String reservationId;
    private Flight flight;
    private Passenger passenger;
    private String bookingDate;

    public Reservation(String reservationId, Flight flight,
                       Passenger passenger, String bookingDate) {
        this.reservationId = reservationId;
        this.flight = flight;
        this.passenger = passenger;
        this.bookingDate = bookingDate;
    }

    // Getters
    public String getReservationId() { return reservationId; }
    public Flight getFlight() { return flight; }
    public Passenger getPassenger() { return passenger; }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId + "\n" +
               "Flight: " + flight.toString() + "\n" +
               "Passenger: " + passenger.toString() + "\n" +
               "Booked on: " + bookingDate;
    }
}