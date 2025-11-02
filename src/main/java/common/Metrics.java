package common;

public class Metrics {
    public long startNs;
    public long endNs;
    public long dfsVisits = 0;
    public long dfsEdges = 0;
    public long kahnPops = 0;
    public long kahnPushes = 0;
    public long relaxations = 0;
    public void start() { startNs = System.nanoTime(); }
    public void stop() { endNs = System.nanoTime(); }
    public long elapsedNs() { return endNs - startNs; }
}