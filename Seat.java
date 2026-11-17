package railways;

// ================= Орын =================
public class Seat {
    private int number;
    private boolean free = true;

    public Seat(int number) { this.number = number; }
    public int getNumber() { return number; }
    public boolean isFree() { return free; }
    public boolean book() {            // брондау()
        if (!free) return false;
        free = false;
        return true;
    }
    public void release() { free = true; }   // босату()
}
