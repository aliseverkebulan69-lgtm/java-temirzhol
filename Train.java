package railways;

import java.util.*;

// ================= Поезд =================
public class Train {
    private int id;
    private String number;
    private String name;
    private String from, to;
    private List<Wagon> wagons = new ArrayList<>();

    public Train(int id, String number, String name, String from, String to) {
        this.id = id; this.number = number; this.name = name;
        this.from = from; this.to = to;
    }
    public void addWagon(Wagon w) { wagons.add(w); }
    public List<Wagon> wagonList() { return wagons; }   // вагондарТізімі()
    public Wagon getWagon(int n) {
        for (Wagon w : wagons) if (w.getNumber() == n) return w;
        return null;
    }
    public int getId() { return id; }
    public String getNumber() { return number; }
    public String getName() { return name; }
    public String getFrom() { return from; }
    public String getTo() { return to; }
}
