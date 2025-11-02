package dagsp;

import common.Metrics;
import java.util.*;

public class DagShortestPaths {
    private final int n;
    private final List<List<Integer>> adj;
    private final Map<Long, Long> edgeW; // key: (u<<32)|v -> weight
    private Metrics metrics;

    public DagShortestPaths(int n, List<List<Integer>> adj, Map<Long, Long> edgeW, Metrics metrics) {
        this.n = n; this.adj = adj; this.edgeW = edgeW; this.metrics = metrics;
    }
    private long key(int u, int v) { return (((long)u) << 32) | (v & 0xffffffffL); }

    public static class SspResult {
        public long[] dist; public int[] parent;
        public SspResult(long[] dist, int[] parent) { this.dist = dist; this.parent = parent; }
    }
    public SspResult shortestFrom(int src, List<Integer> topoOrder) {
        metrics.start();
        final long INF = Long.MAX_VALUE / 4;
        long[] dist = new long[n]; Arrays.fill(dist, INF);
        int[] parent = new int[n]; Arrays.fill(parent, -1);
        dist[src] = 0;
        int[] pos = new int[n]; for (int i = 0; i < topoOrder.size(); i++) pos[topoOrder.get(i)] = i;
        for (int u : topoOrder) {
            if (dist[u] == INF) continue;
            for (int v : adj.get(u)) {
                metrics.relaxations++;
                long w = edgeW.getOrDefault(key(u,v), Long.MAX_VALUE/4);
                if (dist[v] > dist[u] + w) { dist[v] = dist[u] + w; parent[v] = u; }
            }
        }
        metrics.stop();
        return new SspResult(dist, parent);
    }
    public static class LongestResult {
        public long value; public List<Integer> compPath; public int[] parent;
        public LongestResult(long value, List<Integer> compPath, int[] parent) { this.value = value; this.compPath = compPath; this.parent = parent; }
    }
    public LongestResult longestPath(List<Integer> topoOrder) {
        metrics.start();
        final long NEG_INF = Long.MIN_VALUE / 4;
        long[] dp = new long[n]; Arrays.fill(dp, NEG_INF);
        int[] parent = new int[n]; Arrays.fill(parent, -1);
        int[] indeg = new int[n]; for (int u = 0; u < n; u++) for (int v : adj.get(u)) indeg[v]++;
        for (int i = 0; i < n; i++) if (indeg[i] == 0) dp[i] = 0;
        for (int u : topoOrder) {
            if (dp[u] == NEG_INF) continue;
            for (int v : adj.get(u)) {
                long w = edgeW.getOrDefault(key(u,v), 0L);
                if (dp[v] < dp[u] + w) { dp[v] = dp[u] + w; parent[v] = u; }
            }
        }
        long best = NEG_INF; int end = -1;
        for (int i = 0; i < n; i++) if (dp[i] > best) { best = dp[i]; end = i; }
        List<Integer> path = new ArrayList<>();
        if (end != -1) {
            for (int cur = end; cur != -1; cur = parent[cur]) path.add(cur);
            Collections.reverse(path);
        }
        metrics.stop();
        return new LongestResult(best, path, parent);
    }
}