package graph.scc;

import graph.common.Metrics;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Simple deterministic test for Tarjan's SCC algorithm.
 * Graph: 0 → 1 → 2 → 0 (single cycle of three nodes).
 * Expected: one strongly connected component.
 */
public class SccTest {

    @Test
    public void testSimpleCycle() {
        // Build small cyclic graph
        List<List<Integer>> adj = List.of(
                List.of(1), // 0 → 1
                List.of(2), // 1 → 2
                List.of(0)  // 2 → 0 (completes cycle)
        );

        // Run SCC algorithm
        Metrics m = new Metrics();
        SccTarjan scc = new SccTarjan(3, adj, m);
        List<List<Integer>> comps = scc.run();

        // Verify one SCC detected
        assertEquals(1, comps.size(), "All nodes should form one SCC");
    }
}