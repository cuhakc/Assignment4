package common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class GraphIO {
    public static class Edge { public int u; public int v; public long w; }
    public static class Input {
        public boolean directed = true;
        public int n;
        public List<Edge> edges = new ArrayList<>();
        public int source = 0;
        public String weight_model = "edge";
    }
    private static final ObjectMapper M = new ObjectMapper();

    public static Input readInput(File f) throws IOException {
        return M.readValue(f, Input.class);
    }
    public static void writeOutput(Object obj, File f) throws IOException {
        M.writerWithDefaultPrettyPrinter().writeValue(f, obj);
    }
}