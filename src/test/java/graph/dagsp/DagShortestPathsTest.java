package graph.dagsp;

import graph.common.Metrics;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for DAG shortest and longest path algorithms.
 * Uses a simple linear DAG: 0 → 1 → 2 → 3
 */
public class DagShortestPathsTest {

    @Test
    public void testShortestAndLongest() {
        int n = 4;

        // Build simple linear DAG (0→1→2→3)
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        adj.get(0).add(1);
        adj.get(1).add(2);
        adj.get(2).add(3);

        // Define edge weights
        Map<Long, Long> w = new HashMap<>();
        w.put((((long)0)<<32) | 1, 2L);
        w.put((((long)1)<<32) | 2, 3L);
        w.put((((long)2)<<32) | 3, 4L);

        Metrics m = new Metrics();
        DagShortestPaths dsp = new DagShortestPaths(n, adj, w, m);
        List<Integer> topo = Arrays.asList(0, 1, 2, 3);

        // Test shortest path from node 0
        DagShortestPaths.SspResult sp = dsp.shortestFrom(0, topo);
        assertEquals(0L, sp.dist[0]);
        assertEquals(2L, sp.dist[1]);
        assertEquals(5L, sp.dist[2]);
        assertEquals(9L, sp.dist[3]);

        // Test longest (critical) path
        DagShortestPaths.LongestResult lr = dsp.longestPath(topo);
        assertEquals(9L, lr.value);
        assertEquals(Arrays.asList(0, 1, 2, 3), lr.compPath);
    }
}