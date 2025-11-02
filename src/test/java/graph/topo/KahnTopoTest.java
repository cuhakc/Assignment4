package graph.topo;

import graph.common.Metrics;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for Kahn's topological sort algorithm.
 * Graph: 0 → 1 → 2 (simple DAG).
 * Expected topological order: [0, 1, 2].
 */
public class KahnTopoTest {

    @Test
    public void testTopoOrder() {
        int n = 3;

        // Build simple DAG 0 → 1 → 2
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        adj.get(0).add(1);
        adj.get(1).add(2);

        // Run Kahn's topological sort
        Metrics m = new Metrics();
        KahnTopo topo = new KahnTopo(n, adj, m);
        List<Integer> order = topo.sort();

        // Verify correct order and size
        assertEquals(3, order.size(), "Order should include all 3 nodes");
        assertTrue(order.indexOf(0) < order.indexOf(1), "0 should come before 1");
        assertTrue(order.indexOf(1) < order.indexOf(2), "1 should come before 2");
    }
}