package railways;

import java.util.*;

// ================= Дерекқор (Деректор) =================
public class Database {
    List<Train> trains = new ArrayList<>();
    List<Ticket> tickets = new ArrayList<>();
    RouteGraph graph = new RouteGraph();
    private int ticketCounter = 1;

    public Database() { seed(); }

    private void seed() {
        // Бағыттар (шамамен км)
        graph.addRoad("Алматы", "Шымкент", 690);
        graph.addRoad("Алматы", "Қарағанды", 1030);
        graph.addRoad("Қарағанды", "Астана", 220);
        graph.addRoad("Алматы", "Астана", 1280);
        graph.addRoad("Шымкент", "Түркістан", 160);
        graph.addRoad("Шымкент", "Қызылорда", 520);
        graph.addRoad("Астана", "Көкшетау", 290);
        graph.addRoad("Астана", "Павлодар", 470);

        // Поездар
        Train t1 = new Train(1, "001Т", "Тұлпар", "Алматы", "Астана");
        t1.addWagon(new SV(1));
        t1.addWagon(new Coupe(2));
        t1.addWagon(new Platzkart(3));
        t1.addWagon(new General(4));

        Train t2 = new Train(2, "007Ц", "Шымкент экспрессі", "Алматы", "Шымкент");
        t2.addWagon(new Coupe(1));
        t2.addWagon(new Platzkart(2));

        trains.add(t1);
        trains.add(t2);
    }

    public Train findTrain(int id) {
        for (Train t : trains) if (t.getId() == id) return t;
        return null;
    }
    public Ticket findTicket(int id) {
        for (Ticket t : tickets) if (t.getId() == id) return t;
        return null;
    }
    public int nextTicketId() { return ticketCounter++; }

    // орынКүйінЖаңарту() + сақтау()
    public void saveTicket(Ticket t) { tickets.add(t); }
}
