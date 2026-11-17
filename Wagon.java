package railways;

import java.util.*;

// ================= Вагон (абстракт) =================
public abstract class Wagon {
    private int number;
    private String type;
    private double coef;               // баға коэффициенті
    protected List<Seat> seats = new ArrayList<>();

    public Wagon(int number, String type, int seatCount, double coef) {
        this.number = number;
        this.type = type;
        this.coef = coef;
        for (int i = 1; i <= seatCount; i++) seats.add(new Seat(i));
    }
    public int getNumber() { return number; }
    public String getType() { return type; }
    public double getCoef() { return coef; }
    public int getSeatCount() { return seats.size(); }
    public Seat getSeat(int n) {
        return (n >= 1 && n <= seats.size()) ? seats.get(n - 1) : null;
    }
    public List<Seat> freeSeats() {    // босОрындар()
        List<Seat> res = new ArrayList<>();
        for (Seat s : seats) if (s.isFree()) res.add(s);
        return res;
    }
}
