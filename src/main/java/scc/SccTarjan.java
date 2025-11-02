package scc;

import common.Metrics;
import java.util.*;

public class SccTarjan {
    private final int n;
    private final List<List<Integer>> adj;
    private int index = 0;
    private int[] indices;
    private int[] lowlink;
    private boolean[] onStack;
    private Deque<Integer> stack;
    private List<List<Integer>> components;
    private Metrics metrics;

    public SccTarjan(int n, List<List<Integer>> adj, Metrics metrics) {
        this.n = n; this.adj = adj; this.metrics = metrics;
        indices = new int[n]; Arrays.fill(indices, -1);
        lowlink = new int[n]; onStack = new boolean[n]; stack = new ArrayDeque<>();
        components = new ArrayList<>();
    }
    private void strongConnect(int v) {
        metrics.dfsVisits++;
        indices[v] = index; lowlink[v] = index; index++;
        stack.push(v); onStack[v] = true;
        for (int w : adj.get(v)) {
            metrics.dfsEdges++;
            if (indices[w] == -1) {
                strongConnect(w);
                lowlink[v] = Math.min(lowlink[v], lowlink[w]);
            } else if (onStack[w]) {
                lowlink[v] = Math.min(lowlink[v], indices[w]);
            }
        }
        if (lowlink[v] == indices[v]) {
            List<Integer> comp = new ArrayList<>();
            while (true) {
                int w = stack.pop(); onStack[w] = false; comp.add(w);
                if (w == v) break;
            }
            Collections.sort(comp);
            components.add(comp);
        }
    }
    public List<List<Integer>> run() {
        metrics.start();
        for (int v = 0; v < n; v++) if (indices[v] == -1) strongConnect(v);
        metrics.stop();
        components.sort(Comparator.comparingInt(a -> a.get(0)));
        return components;
    }
}