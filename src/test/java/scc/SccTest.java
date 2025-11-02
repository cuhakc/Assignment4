package scc;

import scc.SccTarjan;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SccTest {
    @Test
    public void testSimpleCycle() {
        List<List<Integer>> adj = List.of(
                List.of(1), // 0 -> 1
                List.of(2), // 1 -> 2
                List.of(0)  // 2 -> 0 (cycle)
        );
        common.Metrics m = new common.Metrics();
        SccTarjan scc = new SccTarjan(3, adj, m);
        List<List<Integer>> comps = scc.run();
        assertEquals(1, comps.size(), "All nodes should form one SCC");
    }
}