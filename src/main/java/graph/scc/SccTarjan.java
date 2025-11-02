package graph.scc;

import graph.common.Metrics;
import java.util.*;

/**
 * Tarjan's algorithm for finding Strongly Connected Components (SCCs)
 * in a directed graph. Runs in O(V + E).
 */
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
        this.n = n;
        this.adj = adj;
        this.metrics = metrics;
        indices = new int[n];
        Arrays.fill(indices, -1);
        lowlink = new int[n];
        onStack = new boolean[n];
        stack = new ArrayDeque<>();
        components = new ArrayList<>();
    }

    // DFS helper for Tarjan’s algorithm
    private void strongConnect(int v) {
        metrics.dfsVisits++;
        indices[v] = index;
        lowlink[v] = index;
        index++;
        stack.push(v);
        onStack[v] = true;

        // Explore all outgoing edges from v
        for (int w : adj.get(v)) {
            metrics.dfsEdges++;
            if (indices[w] == -1) {
                // Recurse on unvisited node
                strongConnect(w);
                lowlink[v] = Math.min(lowlink[v], lowlink[w]);
            } else if (onStack[w]) {
                // Update lowlink if back edge to stack node
                lowlink[v] = Math.min(lowlink[v], indices[w]);
            }
        }

        // If v is root of an SCC, pop stack until v is found
        if (lowlink[v] == indices[v]) {
            List<Integer> comp = new ArrayList<>();
            while (true) {
                int w = stack.pop();
                onStack[w] = false;
                comp.add(w);
                if (w == v) break;
            }
            Collections.sort(comp);
            components.add(comp);
        }
    }

    /** Runs Tarjan's SCC algorithm. */
    public List<List<Integer>> run() {
        metrics.start();
        // Run DFS from all unvisited nodes
        for (int v = 0; v < n; v++)
            if (indices[v] == -1) strongConnect(v);
        metrics.stop();

        // Sort components for consistent output
        components.sort(Comparator.comparingInt(a -> a.get(0)));
        return components;
    }
}