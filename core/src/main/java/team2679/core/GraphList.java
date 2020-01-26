package team2679.core;

/**
 * A class that manages an array of graphs.
 */
public class GraphList {

    private IntervalGraph[] graphs;

    public GraphList() {
        graphs = new IntervalGraph[0];
    }

    /**
     * @param index The index of the requested Graph.
     * @return The graph at the provided index.
     */
    public IntervalGraph get(int index) {
        return graphs[index];
    }

    /**
     * @param graph The graph to add to the end of the GraphList.
     */
    public void add(IntervalGraph graph) {
        IntervalGraph[] tempGraphs = new IntervalGraph[graphs.length + 1];
        for (int g = 0; g < graphs.length; g++) { tempGraphs[g] = graphs[g]; }
        tempGraphs[graphs.length] = graph;
        graphs = tempGraphs;
    }

    /**
     * @param graph The graph to add in the appropriate index.
     * @param index The index to add the graph in.
     */
    public void insert(IntervalGraph graph, int index) {
        IntervalGraph[] tempGraphs = new IntervalGraph[graphs.length + 1];
        for (int g = 0; g < index; g++) { tempGraphs[g] = graphs[g]; }
        tempGraphs[index] = graph;
        for (int g = index + 1; g < graphs.length; g++) { tempGraphs[g] = graphs[g - 1]; }
        graphs = tempGraphs;
    }

    /**
     * @param graph The graph to replace the current graph at an index.
     * @param index The index to put the replacement graph in.
     */
    public void set(IntervalGraph graph, int index) {
        IntervalGraph[] tempGraphs = new IntervalGraph[graphs.length];
        for (int g = 0; g < index; g++) { tempGraphs[g] = graphs[g]; }
        tempGraphs[index] = graph;
        for (int g = index + 1; g < graphs.length + 1; g++) { tempGraphs[g] = graphs[g]; }
        graphs = tempGraphs;
    }

    /**
     * @param index Delete the graph at the provided index.
     */
    public void delete(int index) {
        IntervalGraph[] tempGraphs = new IntervalGraph[graphs.length - 1];
        for (int g = 0; g < index; g++) { tempGraphs[g] = graphs[g]; }
        for (int g = index + 1; g < graphs.length; g++) { tempGraphs[g] = graphs[g]; }
        graphs = tempGraphs;
    }

    /**
     * @param graph The graph to find the index of.
     * @return The first place that the graph was found in, if it is not found, returns -1.
     */
    public int index(IntervalGraph graph) {
        for (int g = 0; g < graphs.length; g++) {
            if (graphs[g] == graph) {
                return g;
            }
        }
        return -1;
    }

    /**
     * @return Length of the GraphList.
     */
    public int length() {
        return graphs.length;
    }

}