package graph.common;

/**
 * Tracks performance metrics and counters for graph algorithms.
 * This class measures:
 * Execution time (start/stop in nanoseconds)
 * DFS visits and edge traversals (for SCC)
 * Kahn’s queue operations (for topological sort)
 * Relaxations (for shortest-path computation)
 * Used across all modules to collect consistent timing and operation data
 * for algorithm analysis and reporting.
 */
public class Metrics {
    /** Start timestamp (nanoseconds). */
    public long startNs;

    /** End timestamp (nanoseconds). */
    public long endNs;

    /** DFS visits counter (Tarjan SCC). */
    public long dfsVisits = 0;

    /** DFS edge traversals counter (Tarjan SCC). */
    public long dfsEdges = 0;

    /** Number of nodes popped from Kahn's queue. */
    public long kahnPops = 0;

    /** Number of nodes pushed into Kahn's queue. */
    public long kahnPushes = 0;

    /** Number of relaxations during shortest-path computation. */
    public long relaxations = 0;

    /** Start timing measurement. */
    public void start() {
        startNs = System.nanoTime();
    }

    /** Stop timing measurement. */
    public void stop() {
        endNs = System.nanoTime();
    }

    /** Returns total elapsed time in nanoseconds. */
    public long elapsedNs() {
        return endNs - startNs;
    }
}