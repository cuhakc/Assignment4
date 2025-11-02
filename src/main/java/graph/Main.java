package graph;

import common.GraphIO;
import common.Metrics;
import scc.SccTarjan;
import topo.KahnTopo;
import dagsp.DagShortestPaths;

import java.io.File;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.err.println("Provide path to tasks.json"); System.exit(1);
        }
        File in = new File(args[0]);
        GraphIO.Input input = GraphIO.readInput(in);

        int n = input.n;
        List<List<Integer>> adj = new ArrayList<>(); for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        Map<Long, Long> edgeW = new HashMap<>();
        for (GraphIO.Edge e : input.edges) {
            adj.get(e.u).add(e.v);
            long key = (((long)e.u) << 32) | (e.v & 0xffffffffL);
            edgeW.put(key, Math.min(edgeW.getOrDefault(key, Long.MAX_VALUE/4), e.w));
        }

        Metrics metrics = new Metrics();
        SccTarjan scc = new SccTarjan(n, adj, metrics);
        List<List<Integer>> comps = scc.run();

        int numComps = comps.size();
        int[] nodeToComp = new int[n];
        for (int i = 0; i < numComps; i++) for (int v : comps.get(i)) nodeToComp[v]=i;

        List<List<Integer>> cadj = new ArrayList<>(); for (int i = 0; i < numComps; i++) cadj.add(new ArrayList<>());
        Map<Long, Long> cEdgeW = new HashMap<>();
        for (GraphIO.Edge e : input.edges) {
            int cu = nodeToComp[e.u], cv = nodeToComp[e.v];
            if (cu != cv) {
                if (!cadj.get(cu).contains(cv)) cadj.get(cu).add(cv);
                long k = (((long)cu) << 32) | (cv & 0xffffffffL);
                cEdgeW.put(k, Math.min(cEdgeW.getOrDefault(k, Long.MAX_VALUE/4), e.w));
            }
        }

        KahnTopo topo = new KahnTopo(numComps, cadj, metrics);
        List<Integer> topoOrder = topo.sort();

        int srcComp = nodeToComp[input.source];
        DagShortestPaths dsp = new DagShortestPaths(numComps, cadj, cEdgeW, metrics);
        DagShortestPaths.SspResult sres = dsp.shortestFrom(srcComp, topoOrder);
        DagShortestPaths.LongestResult lres = dsp.longestPath(topoOrder);

        Map<String, Object> out = new LinkedHashMap<>();
        out.put("num_components", numComps);
        out.put("components", comps);
        out.put("condensation_edges", cEdgeW);
        out.put("topo_order", topoOrder);
        out.put("shortest_distances", sres.dist);
        out.put("shortest_parents", sres.parent);
        out.put("critical_path_value", lres.value);
        out.put("critical_path_comps", lres.compPath);
        out.put("metrics", metrics);

        GraphIO.writeOutput(out, new File("tasks_output.json"));
        System.out.println("Wrote tasks_output.json");
    }
}
