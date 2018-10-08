package huang.bling.a8puzzle.puzzle;

import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

public class AndroidPuzzle {
    /**
     *  Handle Game logic. Maintain global variable.
     */
    //constant
    final String PUZZLE_STATE = "huang.bling.8puzzle.puzzle_current_state";
    final String PUZZLE_CUR_STEP = "huang.bling.8puzzle.puzzle_current_step";
    // variable
    private Puzzle puzzle;
    private int curstep;
    private int bestStep = 10;
    private boolean gameWin;
    private boolean AstarAnswerAvailable = false;
    private AstarSolution solution;
    private ArrayList<Integer> answer;
    private int nextAnswer;
    private boolean cheating = false;

    public AndroidPuzzle(){
        puzzle = new Puzzle();
        curstep = 0;
        gameWin = false;
    }
    public AndroidPuzzle(Bundle savedInstance){
        ArrayList<Integer> state = savedInstance.getIntegerArrayList(PUZZLE_STATE);
        puzzle = new Puzzle(state);
        curstep = savedInstance.getInt(PUZZLE_CUR_STEP);
        if(puzzle.isSolved()){
            gameWin = true;
        }
        else{
            gameWin = false;
        }
    }
    public void savePuzzle(Bundle bundle){
        bundle.putIntegerArrayList(PUZZLE_STATE,puzzle.puzzle);
        bundle.putInt(PUZZLE_CUR_STEP,curstep);
    }
    public void clickBox(int boxId){
        if(gameWin)
            return;
        if(puzzle.move(boxId)){
            AstarAnswerAvailable = false;
            if(!cheating)
                curstep++;
            else{
                curstep = 0;
            }
        }
        if(puzzle.isSolved()){
            gameWin = true;
            if(!cheating && curstep < bestStep){
                bestStep = curstep;
            }
            puzzle.puzzle.set(8,9);
        }
    }

    public void clickStart(){
        puzzle = new Puzzle();
        gameWin = false;
        curstep = 0;
        AstarAnswerAvailable = false;
        cheating = false;
    }

    public boolean giveAstarAnswer(){
        cheating = true;
        if(!AstarAnswerAvailable){
            solution = new AstarSolution();
            answer = solution.solveProblem(puzzle.puzzle);
            nextAnswer = 0;
            AstarAnswerAvailable = true;
        }
        if(answer.size() == 0)
            return false;
        if(nextAnswer <answer.size()){
            puzzle.move(answer.get(nextAnswer));
            nextAnswer++;
            if(puzzle.isSolved()){
                gameWin = true;
                puzzle.puzzle.set(8,9);
            }
        }
        return true;
    }

    public int getBoxValue(int boxId){
        return puzzle.puzzle.get(boxId);
    }
    public int getCurstep(){
        return curstep;
    }
    public int getBestStep(){
        return bestStep;
    }
    public boolean isGameWin(){
        return gameWin;
    }
    public boolean isCheating(){
        return cheating;
    }
}
