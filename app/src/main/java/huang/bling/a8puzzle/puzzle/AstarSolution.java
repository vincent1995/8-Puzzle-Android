package huang.bling.a8puzzle.puzzle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

import static huang.bling.a8puzzle.puzzle.Solution.array2String;
import static huang.bling.a8puzzle.puzzle.Solution.string2Array;

class NodeComper implements Comparator<Node> {

    @Override
    public int compare(Node o1, Node o2) {
        if(o1.distance < o2.distance)
            return -1;
        return 1;

    }
}

public class AstarSolution {
    /**
     * A* algorithm.
     *
     *
     */

    Queue<Node> nodes;
    Map<String,Integer> nodeIds;
    Map<Integer,Node> nodeEdges;

    public ArrayList<Integer> solveProblem(ArrayList<Integer> problem){

        nodes = new PriorityQueue<>(30,new NodeComper());
        nodeIds = new HashMap<>();
        nodeEdges = new HashMap<>();
        int nextId = 0;

        // init state
        String nodeString = array2String(problem);
        int startId = nextId;
        nextId ++;
        int distance = distanceFunction(problem);
        Node initNode = new Node(nodeString,startId,distance);
        initNode.stepNum = 0;
        nodes.offer(initNode);

        while(nodes.size()>0){
            Node node = nodes.poll();
            //if this node distance is 0
            if(node.distance ==0){

                return getAnswerMove(extractAnswer(node));
            }

            //find possible move
            ArrayList<String> possibleMove = findPossibleMove(node.nodeString);
            for(String pNodeString:possibleMove){
                if(!nodeIds.containsKey(pNodeString)){
                    int id = nextId;
                    nextId++;
                    int dis = distanceFunction(Solution.string2Array(pNodeString));
                    Node nextNode = new Node(pNodeString,id,dis);

                    nodeIds.put(pNodeString,id);
                    nodeEdges.put(id,nextNode);
                }
                Node nextNode = nodeEdges.get(nodeIds.get(pNodeString));
                if( nextNode.stepNum > node.stepNum || nextNode.stepNum == -1){
                    nextNode.stepNum = node.stepNum +1;
                    nextNode.lastNode = node.id;
                    nodes.offer(nextNode);
                }
            }
        }
        return new ArrayList<>();
    }

    ArrayList<String> findPossibleMove(String state){
        ArrayList<String> endState = new ArrayList<>();
        ArrayList<Integer> startState = string2Array(state);
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

    ArrayList<ArrayList<Integer>> extractAnswer(Node node){
        ArrayList<ArrayList<Integer>> answer = new ArrayList<>();
        while(node != null){
            answer.add(string2Array(node.nodeString));
            node = nodeEdges.get(node.lastNode);
        }
        return answer;
    }
    ArrayList<Integer> getAnswerMove(ArrayList<ArrayList<Integer>> ans){
        ArrayList<Integer> move = new ArrayList<>();
        for(int i = ans.size()-1;i>=0;i--){
            move.add(ans.get(i).indexOf(0));
        }
        return move;
    }

    private int distanceFunction(ArrayList<Integer> state){
        int distance = 0;
        for(int i = 0;i<state.size();i++){
            int curx = i / 3;
            int cury = i % 3;

            int val = state.get(i);
            if(val == 0)
                val = 9;

            val -=1;
            int tarx = val / 3;
            int tary = val % 3;

            distance += Math.abs(curx-tarx) + Math.abs(cury-tary);
        }
        return distance;
    }
    public static void testsolveProblem(){
    }
}
