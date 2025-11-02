package topo;

import common.Metrics;
import java.util.*;

public class KahnTopo {
    private final int n;
    private final List<List<Integer>> adj;
    private Metrics metrics;

    public KahnTopo(int n, List<List<Integer>> adj, Metrics metrics) {
        this.n = n; this.adj = adj; this.metrics = metrics;
    }
    public List<Integer> sort() {
        metrics.start();
        int[] indeg = new int[n];
        for (int u = 0; u < n; u++) for (int v : adj.get(u)) indeg[v]++;
        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++) if (indeg[i] == 0) q.add(i);
        List<Integer> order = new ArrayList<>();
        while (!q.isEmpty()) {
            int u = q.poll(); metrics.kahnPops++; order.add(u);
            for (int v : adj.get(u)) {
                indeg[v]--;
                if (indeg[v] == 0) { q.add(v); metrics.kahnPushes++; }
            }
        }
        metrics.stop();
        return order;
    }
}
