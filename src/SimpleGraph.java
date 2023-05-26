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
        for (Vertex v : vertex) {
            v.Hit = false;
        }

        return DepthFirstSearchRec(stack, VFrom, VTo);
    }

    public ArrayList<Vertex> DepthFirstSearchRec(Stack<Vertex> stack, int x, int VTo) {
        vertex[x].Hit = true;

        stack.push(vertex[x]);

        if (IsEdge(x, VTo)) {
            stack.push(vertex[VTo]);
            return new ArrayList<>(stack);
        }

        for (int i = 0; i < max_vertex; i++) {
            if (IsEdge(x, i) && !vertex[i].Hit) {
                return DepthFirstSearchRec(stack, i, VTo);
            }

            if (stack.isEmpty()) {
                break;
            }

            if (i == max_vertex - 1) {
                DepthFirstSearchPop(stack, x, VTo);
            }
        }

        return new ArrayList<>(stack);
    }



    public void DepthFirstSearchPop(Stack<Vertex> stack, int x, int VTo) {
        stack.pop();

        if (stack.isEmpty()) {
            return;
        }

        for (int i = 0; i < max_vertex; i++) {
            if (vertex[i] == stack.peek()) {
                stack.pop();
                DepthFirstSearchRec(stack, i, VTo);
                return;
            }
        }
    }
}