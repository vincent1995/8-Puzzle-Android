package huang.bling.a8puzzle.puzzle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Node {
    ArrayList<Integer> connectedNode;
    String nodeString;
    int id;
    int lastNode;
    boolean visited;

    void addEdge(int i){
        connectedNode.add(i);
    }
    Node(String s,int id){
        nodeString = s;
        connectedNode = new ArrayList<>();
        lastNode = -1;
        visited = false;
        this.id = id;
    }
}
