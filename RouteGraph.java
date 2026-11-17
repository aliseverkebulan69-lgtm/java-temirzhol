package railways;

import java.util.*;

// ================= Бағыт (граф + Дейкстра) =================
public class RouteGraph {
    private Map<String, Map<String, Double>> adj = new HashMap<>();

    public void addRoad(String a, String b, double km) {
        adj.computeIfAbsent(a, k -> new HashMap<>()).put(b, km);
        adj.computeIfAbsent(b, k -> new HashMap<>()).put(a, km);
    }

    public Set<String> stations() { return adj.keySet(); }

    // қысқаЖолТабу() — Дейкстра алгоритмі
    public Result shortestPath(String from, String to) {
        if (!adj.containsKey(from) || !adj.containsKey(to)) return null;
        Map<String, Double> dist = new HashMap<>();
        Map<String, String> prev = new HashMap<>();
        for (String s : adj.keySet()) dist.put(s, Double.MAX_VALUE);
        dist.put(from, 0.0);

        PriorityQueue<Object[]> pq = new PriorityQueue<>(
                Comparator.comparingDouble(o -> (double) o[1]));
        pq.add(new Object[]{from, 0.0});

        while (!pq.isEmpty()) {
            Object[] cur = pq.poll();
            String u = (String) cur[0];
            double d = (double) cur[1];
            if (d > dist.get(u)) continue;
            if (u.equals(to)) break;
            for (Map.Entry<String, Double> e : adj.get(u).entrySet()) {
                double nd = d + e.getValue();
                if (nd < dist.get(e.getKey())) {
                    dist.put(e.getKey(), nd);
                    prev.put(e.getKey(), u);
                    pq.add(new Object[]{e.getKey(), nd});
                }
            }
        }
        if (dist.get(to) == Double.MAX_VALUE) return null;

        LinkedList<String> path = new LinkedList<>();
        for (String at = to; at != null; at = prev.get(at)) path.addFirst(at);
        return new Result(path, dist.get(to));
    }

    static class Result {
        List<String> path;
        double km;
        Result(List<String> path, double km) { this.path = path; this.km = km; }
    }
}
