package graph.topo;

import graph.common.Metrics;
import java.util.*;

/**
 * Performs topological sorting on a Directed Acyclic Graph (DAG)
 * using Kahn's algorithm (BFS-based approach).
 */
public class KahnTopo {
    private final int n;
    private final List<List<Integer>> adj;
    private Metrics metrics;

    public KahnTopo(int n, List<List<Integer>> adj, Metrics metrics) {
        this.n = n;
        this.adj = adj;
        this.metrics = metrics;
    }

    /** Computes a valid topological order using Kahn's algorithm. */
    public List<Integer> sort() {
        metrics.start();

        // Step 1: Compute indegrees of all nodes
        int[] indeg = new int[n];
        for (int u = 0; u < n; u++)
            for (int v : adj.get(u))
                indeg[v]++;

        // Step 2: Initialize queue with nodes having zero indegree
        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++)
            if (indeg[i] == 0)
                q.add(i);

        // Step 3: Repeatedly remove nodes and update indegrees
        List<Integer> order = new ArrayList<>();
        while (!q.isEmpty()) {
            int u = q.poll();
            metrics.kahnPops++;
            order.add(u);

            // Decrease indegree of neighbors
            for (int v : adj.get(u)) {
                indeg[v]--;
                // When a neighbor becomes zero indegree, add to queue
                if (indeg[v] == 0) {
                    q.add(v);
                    metrics.kahnPushes++;
                }
            }
        }

        metrics.stop();
        return order;
    }
}