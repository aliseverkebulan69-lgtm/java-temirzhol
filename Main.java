package railways;

import java.util.*;

// ================= Негізгі бағдарлама =================
public class Main {
    static Scanner sc = new Scanner(System.in);
    static Database db = new Database();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== ҚАЗАҚСТАН ТЕМІР ЖОЛЫ ===");
            System.out.println("1. Поездарды қарау");
            System.out.println("2. Вагон түрлерін қарау");
            System.out.println("3. Бос орындарды тексеру");
            System.out.println("4. Бағыт іздеу (Дейкстра)");
            System.out.println("5. Билет брондау");
            System.out.println("6. Билетті қайтару");
            System.out.println("7. Менің билеттерім");
            System.out.println("0. Шығу");
            System.out.print("Таңдаңыз: ");
            int c = readInt();
            switch (c) {
                case 1: showTrains(); break;
                case 2: showWagons(); break;
                case 3: showFreeSeats(); break;
                case 4: findRoute(); break;
                case 5: bookTicket(); break;
                case 6: refundTicket(); break;
                case 7: for (Ticket t : db.tickets) System.out.println(t); break;
                case 0: System.out.println("Сау болыңыз!"); return;
                default: System.out.println("Қате таңдау!");
            }
        }
    }

    static void showTrains() {
        for (Train t : db.trains)
            System.out.println(t.getId() + ". " + t.getNumber() + " \"" + t.getName() + "\" : "
                    + t.getFrom() + " → " + t.getTo());
    }

    static void showWagons() {
        showTrains();
        System.out.print("Поезд ID: ");
        Train t = db.findTrain(readInt());
        if (t == null) { System.out.println("Поезд табылмады!"); return; }
        for (Wagon w : t.wagonList())
            System.out.println("Вагон " + w.getNumber() + " — " + w.getType()
                    + ", орын саны: " + w.getSeatCount()
                    + ", бос: " + w.freeSeats().size());
    }

    static void showFreeSeats() {
        showTrains();
        System.out.print("Поезд ID: ");
        Train t = db.findTrain(readInt());
        if (t == null) { System.out.println("Поезд табылмады!"); return; }
        System.out.print("Вагон нөмірі: ");
        Wagon w = t.getWagon(readInt());
        if (w == null) { System.out.println("Вагон табылмады!"); return; }
        List<Seat> free = w.freeSeats();
        if (free.isEmpty()) { System.out.println("Орын бос емес!"); return; }
        System.out.print("Бос орындар: ");
        for (Seat s : free) System.out.print(s.getNumber() + " ");
        System.out.println();
    }

    static void findRoute() {
        System.out.println("Станциялар: " + db.graph.stations());
        System.out.print("Қайдан: ");
        String a = sc.nextLine().trim();
        System.out.print("Қайда: ");
        String b = sc.nextLine().trim();
        RouteGraph.Result r = db.graph.shortestPath(a, b);
        if (r == null) { System.out.println("Бағыт табылмады!"); return; }
        System.out.println("Ең қысқа жол: " + String.join(" → ", r.path));
        System.out.println("Қашықтық: " + r.km + " км");
    }

    static void bookTicket() {
        showTrains();
        System.out.print("Поезд ID: ");
        Train t = db.findTrain(readInt());
        if (t == null) { System.out.println("Поезд табылмады!"); return; }

        for (Wagon w : t.wagonList())
            System.out.println("Вагон " + w.getNumber() + " — " + w.getType()
                    + " (бос: " + w.freeSeats().size() + ")");
        System.out.print("Вагон нөмірі: ");
        Wagon w = t.getWagon(readInt());
        if (w == null) { System.out.println("Вагон табылмады!"); return; }

        System.out.print("Орын нөмірі: ");
        Seat s = w.getSeat(readInt());
        if (s == null) { System.out.println("Мұндай орын жоқ!"); return; }
        if (!s.isFree()) { System.out.println("Орын бос емес!"); return; }  // «Орын бос емес» хабары

        System.out.print("Жолаушы аты: ");
        String name = sc.nextLine();
        System.out.print("Телефон: ");
        String phone = sc.nextLine();

        RouteGraph.Result r = db.graph.shortestPath(t.getFrom(), t.getTo());
        double km = (r == null) ? 0 : r.km;

        Passenger p = new Passenger(db.nextTicketId(), name, phone);
        s.book();
        Ticket ticket = new Ticket(p.getId(), p, t, w, s, km);
        db.saveTicket(ticket);
        System.out.println("Билет дайын!\n" + ticket);
    }

    static void refundTicket() {
        System.out.print("Билет ID: ");
        Ticket t = db.findTicket(readInt());
        if (t == null) { System.out.println("Билет табылмады!"); return; }
        if (t.getStatus().equals("Қайтарылған")) {
            System.out.println("Билет бұрын қайтарылған!");
            return;
        }
        t.cancel();
        t.getSeat().release();      // орын күйін жаңарту
        System.out.println("Билет қайтарылды, орын босатылды.");
    }

    static int readInt() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Сан енгізіңіз: ");
            }
        }
    }
}
