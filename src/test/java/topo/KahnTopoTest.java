package topo;

import common.Metrics;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class KahnTopoTest {
    @Test
    public void testTopoOrder() {
        int n=3; List<List<Integer>> adj = new ArrayList<>(); for (int i=0;i<n;i++) adj.add(new ArrayList<>());
        adj.get(0).add(1); adj.get(1).add(2);
        Metrics m = new Metrics();
        KahnTopo k = new KahnTopo(n, adj, m);
        List<Integer> order = k.sort();
        assertEquals(3, order.size());
        assertTrue(order.indexOf(0) < order.indexOf(1));
        assertTrue(order.indexOf(1) < order.indexOf(2));
    }
}