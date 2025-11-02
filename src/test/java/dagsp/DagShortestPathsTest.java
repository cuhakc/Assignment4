package dagsp;

import common.Metrics;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class DagShortestPathsTest {
    @Test
    public void testShortestAndLongest() {
        int n=4; List<List<Integer>> adj = new ArrayList<>(); for (int i=0;i<n;i++) adj.add(new ArrayList<>());
        adj.get(0).add(1); adj.get(1).add(2); adj.get(2).add(3);
        Map<Long, Long> w = new HashMap<>();
        long key01 = (((long)0)<<32) | 1;
        long key12 = (((long)1)<<32) | 2;
        long key23 = (((long)2)<<32) | 3;
        w.put(key01, 2L); w.put(key12, 3L); w.put(key23, 4L);
        Metrics m = new Metrics();
        DagShortestPaths dsp = new DagShortestPaths(n, adj, w, m);
        List<Integer> topo = Arrays.asList(0,1,2,3);
        DagShortestPaths.SspResult sp = dsp.shortestFrom(0, topo);
        assertEquals(0L, sp.dist[0]);
        assertEquals(2L, sp.dist[1]);
        assertEquals(5L, sp.dist[2]);
        assertEquals(9L, sp.dist[3]);
        DagShortestPaths.LongestResult lr = dsp.longestPath(topo);
        assertEquals(9L, lr.value);
        assertEquals(Arrays.asList(0,1,2,3), lr.compPath);
    }
}
