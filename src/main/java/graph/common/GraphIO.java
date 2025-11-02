package graph.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Utility class for reading and writing graph data in JSON format.
 * This class uses Jackson for serialization and deserialization
 * of graph datasets used in SCC, Topological Sort, and DAG-SP algorithms.
 * {
 *   "directed": true,
 *   "n": 10,
 *   "edges": [ {"u":0,"v":1,"w":3}, {"u":1,"v":2,"w":5} ],
 *   "source": 0,
 *   "weight_model": "edge"
 * }
 * Outputs are written with indentation using Jackson’s
 * pretty printer for readability.
 */
public class GraphIO {

    /** Represents a single weighted directed edge (u → v, w). */
    public static class Edge {
        public int u;
        public int v;
        public long w;
    }

    /** Represents the input structure parsed from JSON. */
    public static class Input {
        public boolean directed = true;
        public int n;
        public List<Edge> edges = new ArrayList<>();
        public int source = 0;
        public String weight_model = "edge";
    }

    /** Shared Jackson ObjectMapper instance. */
    private static final ObjectMapper M = new ObjectMapper();
    public static Input readInput(File f) throws IOException {
        return M.readValue(f, Input.class);
    }

    public static void writeOutput(Object obj, File f) throws IOException {
        M.writerWithDefaultPrettyPrinter().writeValue(f, obj);
    }
}