package huang.bling.a8puzzle.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import huang.bling.a8puzzle.R;
import huang.bling.a8puzzle.puzzle.Puzzle;

// todo  set difficult
// todo change language
public class MainActivity extends AppCompatActivity {

    Puzzle puzzle;
    Map<Integer,Integer> boxResIds;
    Map<Integer,Integer> boxIds;
    String PUZZLE_STATE = "huang.bling.8puzzle.puzzle_current_state";
    String PUZZLE_CUR_STEP = "huang.bling.8puzzle.puzzle_current_step";
    TextView stepView;
    TextView bestView;
    TextView winView;
    int curStep;
    int bestValue = 10;
    boolean gameWin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stepView = findViewById(R.id.stepValue);
        bestView = findViewById(R.id.bestValue);
        winView = findViewById(R.id.winView);
        gameWin = false;
        //todo read bestValue


        if(savedInstanceState == null){
            initGame();
        }
        else{
            resumeGame(savedInstanceState);
        }

        boxResIds = new HashMap<>();
        boxResIds.put(0,R.id.box0);
        boxResIds.put(1,R.id.box1);
        boxResIds.put(2,R.id.box2);
        boxResIds.put(3,R.id.box3);
        boxResIds.put(4,R.id.box4);
        boxResIds.put(5,R.id.box5);
        boxResIds.put(6,R.id.box6);
        boxResIds.put(7,R.id.box7);
        boxResIds.put(8,R.id.box8);

        boxIds = new HashMap<>();
        boxIds.put(R.id.box0,0);
        boxIds.put(R.id.box1,1);
        boxIds.put(R.id.box2,2);
        boxIds.put(R.id.box3,3);
        boxIds.put(R.id.box4,4);
        boxIds.put(R.id.box5,5);
        boxIds.put(R.id.box6,6);
        boxIds.put(R.id.box7,7);
        boxIds.put(R.id.box8,8);
        updateBox();
    }
    void initGame(){
        puzzle = new Puzzle();
        curStep = 0;
        stepView.setText(String.valueOf(curStep));
        gameWin = false;
        winView.setVisibility(View.INVISIBLE);
    }
    void resumeGame(Bundle savedInstanceState){
        ArrayList<Integer> curState = savedInstanceState.getIntegerArrayList(PUZZLE_STATE);
        curStep = savedInstanceState.getInt(PUZZLE_CUR_STEP);
        puzzle = new Puzzle(curState);
        gameWin = false;
        winView.setVisibility(View.INVISIBLE);
        if(puzzle.isSolved()){
            gameWin = true;
            winView.setVisibility(View.VISIBLE);
        }
    }
    void showGameWinStage(){
        Toast toast = Toast.makeText(this,"Great Job",Toast.LENGTH_LONG);
        toast.show();
        TextView box = findViewById(boxResIds.get(8));
        box.setText("9");
        box.setBackgroundColor(getResources().getColor(R.color.filledGridColor));

        if(curStep < bestValue){
            bestValue = curStep;
            bestView.setText(String.valueOf(bestValue));
            //todo  save best value
        }
        // show congratulation
        winView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntegerArrayList(PUZZLE_STATE,puzzle.puzzle);
        outState.putInt(PUZZLE_CUR_STEP,curStep);
    }

    void updateBox(){
        for(int i = 0;i<puzzle.puzzle.size();i++){
            int val = puzzle.puzzle.get(i);
            TextView box = findViewById(boxResIds.get(i));
            if(val != 0){
                box.setText(String.valueOf(val));
                box.setBackgroundColor(getResources().getColor(R.color.filledGridColor));
            }

            else{
                if(gameWin){
                    box.setText("9");
                    box.setBackgroundColor(getResources().getColor(R.color.filledGridColor));
                }
                else{
                    box.setText(" ");
                    box.setBackgroundColor(getResources().getColor(R.color.emptyGridColor));
                }
            }
        }
    }

    public void clickBox(View view) {
        int id = boxIds.get(view.getId());
        if(gameWin)
            return;
        if(puzzle.move(id)){
            updateBox();
            curStep++;
            stepView.setText(String.valueOf(curStep));
        }
        if(puzzle.isSolved()){
            gameWin = true;
            showGameWinStage();

        }
    }

    public void clickStart(View view) {
        initGame();
        updateBox();
    }
}
