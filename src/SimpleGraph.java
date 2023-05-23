import java.util.*;

class Vertex {
    public int Value;
    public boolean Hit;

    public Vertex(int val) {
        Value = val;
        Hit = false;
    }
}

class SimpleGraph {
    int max_vertex;
    int[][] m_adjacency;
    Vertex[] vertex;

    public SimpleGraph(int size) {
        max_vertex = size;
        m_adjacency = new int[size][size];
        vertex = new Vertex[size];
    }

    public void AddVertex(int value) {
        for (int i = 0; i < max_vertex; i++) {
            if (vertex[i] == null) {
                vertex[i] = new Vertex(value);
                break;
            }
        }
    }

    public void RemoveVertex(int v) {
        for (int i = 0; i < max_vertex; i++) {
            m_adjacency[v][i] = 0;
            m_adjacency[i][v] = 0;
        }

        vertex[v] = null;
    }

    public boolean IsEdge(int v1, int v2) {
        return m_adjacency[v1][v2] == 1;
    }

    public void AddEdge(int v1, int v2) {
        m_adjacency[v1][v2] = 1;
        m_adjacency[v2][v1] = 1;
    }

    public void RemoveEdge(int v1, int v2) {
        m_adjacency[v1][v2] = 0;
        m_adjacency[v2][v1] = 0;
    }

    public ArrayList<Vertex> DepthFirstSearch(int VFrom, int VTo) {
        Stack<Vertex> stack = new Stack<>();

        for (int i = 0; i < vertex.length; i++) {
            vertex[i].Hit = false;
        }

        int x = VFrom;
        boolean flag = false;

        while (true) {
            vertex[x].Hit = true;
            stack.push(vertex[x]);

            for (int i = 0; i < vertex.length; i++) {
                if (IsEdge(x, VTo)) {
                    stack.push(vertex[VTo]);
                    return new ArrayList<>(stack);
                }
            }

            for (int i = 0; i < vertex.length; i++) {
                if (IsEdge(x, i) && !vertex[i].Hit) {
                    x = i;
                    break;
                }

                if (i == vertex.length - 1 && !stack.isEmpty()) {
                    flag = true;
                    stack.pop();
                }
            }

            if (!flag) {
                continue;
            }

            if (stack.isEmpty()) {
                return new ArrayList<>();
            }

            for (int i = 0; i < vertex.length; i++) {
                if (vertex[i] == stack.peek()) {
                    x = i;
                    stack.pop();
                    flag = false;
                    break;
                }
            }
        }
    }

    public ArrayList<Vertex> DepthFirstSearchR(int VFrom, int VTo) {
        Stack<Vertex> stack = new Stack<>();

        for (int i = 0; i < vertex.length; i++) {
            vertex[i].Hit = false;
        }

        int x = VFrom;

        return DepthFirstSearchRec(stack, x, VTo);
    }

    public ArrayList<Vertex> DepthFirstSearchRec(Stack<Vertex> stack, int x, int VTo) {
        vertex[x].Hit = true;
        stack.push(vertex[x]);

        for (int i = 0; i < vertex.length; i++) {
            if (IsEdge(x, VTo)) {
                stack.push(vertex[VTo]);
                return new ArrayList<Vertex>(stack);
            }
        }

        for (int i = 0; i < vertex.length; i++) {
            if (IsEdge(x, i) && !vertex[i].Hit) {
                x = i;
                return DepthFirstSearchRec(stack, x, VTo);
            }

            if (i == vertex.length - 1) {
                DepthFirstSearchRec2(stack, x, VTo);
            }
        }

        return new ArrayList<Vertex>(stack);
    }

    public void DepthFirstSearchRec2(Stack<Vertex> stack, int x, int VTo) {
        if (stack.isEmpty()) {
            return;
        }

        stack.pop();

        if (stack.isEmpty()) {
            return;
        }

        for (int i = 0; i < vertex.length; i++) {
            if (vertex[i] == stack.peek()) {
                x = i;
                stack.pop();
                DepthFirstSearchRec(stack, x, VTo);
                return;
            }
        }
    }

    public static void main(String[] args) {
        SimpleGraph graph = new SimpleGraph(6);

        for (int i = 0; i < 6; i++) {
            graph.AddVertex(i);
        }

        graph.AddEdge(0, 1);
        graph.AddEdge(1, 3);
        ArrayList<Vertex> ls = graph.DepthFirstSearchR(0, 5);
        System.out.println(ls.toString());
        System.out.println(graph.IsEdge(0, 3));

    }
}