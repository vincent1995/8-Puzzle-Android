package huang.bling.a8puzzle.puzzle;

import java.util.*;

// main idea:
// Find the shortest path between two node in a network.
// Use Dj
public class Solution {
    Map<String,Integer> nodeIds;
    Map<Integer,Node> nodeEdges;
    String target = "123456780";
    Solution(){
        createNetwork();
    }

    void solveProblem(String s){
        resetSolution();
        int startState = nodeIds.get(s);
        int endState = nodeIds.get(target);
        djAlgorithm(startState,endState);
    }
    // reset all the flags.
    void resetSolution(){
        for(Node node:nodeEdges.values()){
            node.visited = false;
        }
    }
    private void printSolution(int endState){
        int curNode = endState;
        ArrayList<String> states = new ArrayList<>();
        while(curNode>=0){
            states.add(nodeEdges.get(curNode).nodeString);
            curNode = nodeEdges.get(curNode).lastNode;
        }
        for(int i = states.size()-1;i>=0;i--){
            Puzzle puzzle = new Puzzle(string2Array(states.get(i)));
            puzzle.print();
        }
    }
    private void djAlgorithm(int startState,int endState){
        Queue<Node> queue = new LinkedList<>();
        Node startNode = nodeEdges.get(startState);
        startNode.visited = true;
        queue.offer(startNode);
        while(queue.size() > 0){
            Node node = queue.poll();
            if(node.id == endState){
                System.out.println("Solution found");
                //printSolution(endState);
                return;
            }
            for(int i = 0;i<node.connectedNode.size();i++){
                int nextNodeId = node.connectedNode.get(i);
                Node nextNode = nodeEdges.get(nextNodeId);
                if(!nextNode.visited){
                    nextNode.lastNode = node.id;
                    nextNode.visited = true;
                    queue.offer(nextNode);
                }
            }
        }
        System.out.println("Solution not found");
    }
    private void createNetwork(){
        // create all nodes
        nodeIds =  new HashMap<>();
        nodeEdges = new HashMap<>();
        ArrayList<Integer> elements = new ArrayList<>();
        for(int i = 0;i<=8;i++){
            elements.add(i);
        }
        ArrayList<String> nodeStrings = permutation(elements);
        for(int i = 0;i<nodeStrings.size();i++){
            nodeIds.put(nodeStrings.get(i),i);
            nodeEdges.put(i, new Node(nodeStrings.get(i),i));
        }
        // for each node, find edges
        for(String nodeString:nodeStrings){
            int startPoint = nodeIds.get(nodeString);
            ArrayList<String> possibleMove = findPossibleMove(nodeString);
            for(String nextNode:possibleMove){
                int endPoint = nodeIds.get(nextNode);
                // add edges in nodeEdges
                nodeEdges.get(startPoint).addEdge(endPoint);
                nodeEdges.get(endPoint).addEdge(startPoint);
            }
        }
    }
    private ArrayList<String> findPossibleMove(String nodeString){
        ArrayList<String> endState = new ArrayList<>();
        ArrayList<Integer> startState = string2Array(nodeString);
        Puzzle puzzle = new Puzzle(startState);
        if(puzzle.curx != 0){
            puzzle.moveTo(puzzle.curx-1,puzzle.cury);
            endState.add(array2String(puzzle.puzzle));
            puzzle.moveTo(puzzle.curx+1,puzzle.cury);
        }
        if(puzzle.curx != 2){
            puzzle.moveTo(puzzle.curx+1,puzzle.cury);
            endState.add(array2String(puzzle.puzzle));
            puzzle.moveTo(puzzle.curx-1,puzzle.cury);
        }
        if(puzzle.cury != 0){
            puzzle.moveTo(puzzle.curx,puzzle.cury-1);
            endState.add(array2String(puzzle.puzzle));
            puzzle.moveTo(puzzle.curx,puzzle.cury+1);
        }
        if(puzzle.cury != 2){
            puzzle.moveTo(puzzle.curx,puzzle.cury+1);
            endState.add(array2String(puzzle.puzzle));
            puzzle.moveTo(puzzle.curx,puzzle.cury-1);
        }
        return endState;
    }
    public static ArrayList<Integer> string2Array(String s){
        ArrayList<Integer> rtn = new ArrayList<>();
        for(int i = 0;i<s.length();i++){
            int val = Integer.parseInt(s.substring(i,i+1));
            rtn.add(val);
        }
        return rtn;
    }
    public static String array2String(ArrayList<Integer> a){
        String s = "";
        for(int i:a){
            s += String.valueOf(i);
        }
        return s;
    }
    public static ArrayList<String> permutation(ArrayList<Integer> a){
        ArrayList<String> rtn = null;
        if(a.size() == 0){
            rtn = new ArrayList<>();
            rtn.add("");
            return rtn;
        }

        for(int i = 0;i<a.size();i++){
            ArrayList<Integer> temp = new ArrayList<>(a);
            temp.remove(i);
            ArrayList<String> s = permutation(temp);
            for(int j = 0;j<s.size();j++){
                s.set(j,  String.valueOf(a.get(i)) + s.get(j));
            }
            if(rtn == null)
                rtn = s;
            else
                rtn.addAll(s);
        }
        return rtn;
    }

    public static void testPermutation(){
        Solution solution = new Solution();
        ArrayList<Integer> array = new ArrayList<>();
        array.add(1);
        array.add(2);
        array.add(3);
        array.add(4);
        array.add(5);
        array.add(6);
        array.add(7);
        array.add(8);
        array.add(0);
        ArrayList<String> s = solution.permutation(array);
        for(String str:s){
            System.out.println(str);
        }
    }
    public static void testFindPossibleMove(){
        Solution solution = new Solution();
        String s = "123405678";
        ArrayList<String> endState = solution.findPossibleMove(s);
        for(String state:endState){
            System.out.println(state);
        }
    }
}
