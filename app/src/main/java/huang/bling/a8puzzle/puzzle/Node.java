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
    int distance;
    int stepNum;

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
    Node(String s,int id, int distance){
        nodeString = s;
        connectedNode = new ArrayList<>();
        lastNode = -1;
        visited = false;
        this.id = id;
        this.distance = distance;
        stepNum = -1;
    }
}
