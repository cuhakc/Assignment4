package scc;

import common.Metrics;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class SccTarjanTest {
    @Test
    public void testSimpleSCC() {
        int n = 4; List<List<Integer>> adj = new ArrayList<>(); for (int i=0;i<n;i++) adj.add(new ArrayList<>());
        adj.get(0).add(1); adj.get(1).add(2); adj.get(2).add(0); // cycle 0-1-2
        adj.get(2).add(3);
        Metrics m = new Metrics();
        SccTarjan s = new SccTarjan(n, adj, m);
        List<List<Integer>> comps = s.run();
        // expect one component of size 3 and one of size 1
        boolean ok=false;
        for (List<Integer> c: comps) if (c.size()==3) ok=true;
        assertTrue(ok);
    }
}