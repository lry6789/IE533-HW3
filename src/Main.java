import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Question a:");
        // is_DAG
        List<List<Integer>> G = new ArrayList<>();
        G.add(Arrays.asList(0, 1, 1, 0));
        G.add(Arrays.asList(0, 0, 0, 1));
        G.add(Arrays.asList(0, 0, 0, 1));
        G.add(Arrays.asList(0, 0, 0, 0));
        System.out.println(G);
        is_DAG(G);

        G = new ArrayList<>();
        G.add(Arrays.asList(0, 1, 0, 0));
        G.add(Arrays.asList(0, 0, 0, 1));
        G.add(Arrays.asList(1, 0, 0, 0));
        G.add(Arrays.asList(0, 0, 1, 0));
        System.out.println(G);
        is_DAG(G);
        System.out.println("Question b:");
        //make_DAG
        make_dag(G);

        System.out.println("Question c:");
        //sort_dag
        sortDAG(G);
    }

    private static int[] visited;


    public static void is_DAG(List<List<Integer>> G) {
        visited = new int[G.size()];
        for (int i = 0; i < G.size(); i++) {
            if (visited[i] == 0) {
                if (!DFS(i, G)) {
                    System.out.println("The graph is not a DAG");
                    return;
                }
            }


        }
        System.out.println("The graph is a DAG");
    }


    public static boolean DFS(int node, List<List<Integer>> G) {
        //System.out.println(node);
        visited[node] = 1;
        for (int i = 0; i < G.size(); i++) {
            if (G.get(node).get(i) == 1) {
                //System.out.println(i);
                if (visited[i] == 1) {
                    return false;
                } else if (visited[i] == 0) {
                    return DFS(i, G);
                }
            }
        }

        visited[node] = -1;
        return true;
    }

    //
    public static void make_dag(List<List<Integer>> G) {
        int V = G.size();
        boolean[] visited = new boolean[V];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < V; i++) {
            if (!visited[i])
                topologicalSortUtil(G, i, visited, stack);
        }
        //System.out.println("Stack:"+stack);

        List<List<Integer>> dag = new ArrayList<>();
        dag.add(Arrays.asList(0, 0, 0, 0));
        dag.add(Arrays.asList(0, 0, 0, 0));
        dag.add(Arrays.asList(0, 0, 0, 0));
        dag.add(Arrays.asList(0, 0, 0, 0));
        System.out.println(G);
        is_DAG(G);
        while (!stack.isEmpty()) {
            int u = stack.pop();
            for (int i = 0; i < G.size(); i++) {
                if (G.get(u).get(i) == 1) {
                    if (stack.search(i) > stack.search(u)) {
                        dag.get(u).set(i, 1);
                    }
                }
            }
        }
        System.out.println("The new dag is : "+dag);
    }
    public static void topologicalSortUtil(List<List<Integer>> G, int v, boolean[] visited, Stack<Integer> stack) {
        visited[v] = true;
        for (int i = 0; i < G.size(); i++) {
            if (G.get(v).get(i) == 1) {
                if (!visited[i]){
                    topologicalSortUtil(G, i, visited, stack);
                }
            }

        }
        stack.push(v);
    }

    //
    public static void sortDAG(List<List<Integer>> G) {
        List<List<Integer>> sortedGraph = new ArrayList<>();
        Map<Integer, Integer> inDegree = new HashMap<>();
        for(int i = 0; i < G.size(); i++){
            sortedGraph.add(Arrays.asList(0, 0, 0, 0));

        }
        for (int i = 0 ; i < G.size(); i++) {
            int node = i;
            List<Integer> neighbors = G.get(node);
            inDegree.put(node, 0);
            for (int j = 0; j < G.size(); j++){
                if (neighbors.get(j) == 1) {
                    inDegree.put(j, inDegree.getOrDefault(j, 0) + 1);
                }
            }
        }


        Queue<Integer> queue = new LinkedList<>();
        for (Map.Entry<Integer, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.offer(entry.getKey());
            }
        }

        while (!queue.isEmpty()) {
            int node = queue.poll();
            List<Integer> neighbors = G.get(node);

            for (int neighbor : neighbors) {
                for (int j = 0; j < G.size(); j++) {
                    if (neighbors.get(j) == 1) {
                        int degree = inDegree.get(neighbor) - 1;
                        inDegree.put(neighbor, degree);
                        if (degree == 0) {
                            queue.offer(neighbor);
                        }
                    }

                }
            }
        }
        for (int i = 0 ; i < G.size(); i++) {
            int node = i;
            List<Integer> neighbors = G.get(node);
            for (int j = 0 ; j < G.size(); j++) {
                if (neighbors.get(j) == 1) {
                    sortedGraph.get(node).set(j,1);
                }
            }
        }

        System.out.println("The equivalent sorted graph is:"+sortedGraph);
    }
}