package graph.scc;

import graph.common.Metrics;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for Tarjan's SCC algorithm.
 * Graph: 0 → 1 → 2 → 0 forms one cycle (SCC of size 3), plus node 3 as a singleton SCC.
 */
public class SccTarjanTest {

    @Test
    public void testSimpleSCC() {
        int n = 4;

        // Build directed graph
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

        // Cycle: 0 → 1 → 2 → 0
        adj.get(0).add(1);
        adj.get(1).add(2);
        adj.get(2).add(0);
        // Extra edge: 2 → 3 (makes node 3 its own SCC)
        adj.get(2).add(3);

        // Run Tarjan’s SCC
        Metrics m = new Metrics();
        SccTarjan scc = new SccTarjan(n, adj, m);
        List<List<Integer>> comps = scc.run();

        // Expect one SCC of size 3 (nodes 0,1,2) and one of size 1 (node 3)
        boolean hasCycleScc = comps.stream().anyMatch(c -> c.size() == 3);
        boolean hasSingleton = comps.stream().anyMatch(c -> c.size() == 1);

        assertTrue(hasCycleScc, "Should detect one cycle SCC of size 3");
        assertTrue(hasSingleton, "Should detect one single-node SCC");
    }
}