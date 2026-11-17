package railways;

// ================= Билет =================
public class Ticket {
    private int id;
    private double price;
    private String status;     // "Брондалған" / "Қайтарылған"
    private Passenger passenger;
    private Train train;
    private Wagon wagon;
    private Seat seat;

    public Ticket(int id, Passenger p, Train t, Wagon w, Seat s, double km) {
        this.id = id; this.passenger = p; this.train = t;
        this.wagon = w; this.seat = s;
        this.price = calcPrice(km);
        this.status = "Брондалған";
    }
    // бағаЕсептеу()
    private double calcPrice(double km) {
        return Math.round(km * 8 * wagon.getCoef());   // 1 км = 8 теңге * коэффициент
    }
    public void cancel() { status = "Қайтарылған"; }
    public int getId() { return id; }
    public double getPrice() { return price; }
    public String getStatus() { return status; }
    public Seat getSeat() { return seat; }

    @Override
    public String toString() {
        return "Билет №" + id + " | " + passenger.getName() + " | "
                + train.getNumber() + " " + train.getName()
                + " | вагон " + wagon.getNumber() + " (" + wagon.getType() + ")"
                + " | орын " + seat.getNumber()
                + " | " + price + " тг | " + status;
    }
}
