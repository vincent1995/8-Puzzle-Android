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
import huang.bling.a8puzzle.puzzle.AndroidPuzzle;
import huang.bling.a8puzzle.puzzle.Puzzle;

// todo  set difficult
// todo change language
public class MainActivity extends AppCompatActivity {
    /**
     * Only UI related operation in this class,
     * otherwise, in the AndroidPuzzle.
     */
    AndroidPuzzle puzzle;

    Map<Integer,Integer> boxResIds;
    Map<Integer,Integer> boxIds;
    TextView stepView;
    TextView bestView;
    TextView winView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stepView = findViewById(R.id.stepValue);
        winView = findViewById(R.id.winView);
        bestView = findViewById(R.id.bestValue);
        //todo read bestValue


        if(savedInstanceState == null){
            puzzle = new AndroidPuzzle();
        }
        else{
            puzzle = new AndroidPuzzle(savedInstanceState);
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
        refreshUI();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        puzzle.savePuzzle(outState);
    }

    /*****************************************************
     *  handle View event in here.
     *  call AndroidPuzzle instance to handle the events.
     *
     *  At the end of every event, refresh UI.
     *
     */
    public void clickBox(View view) {
        int id = boxIds.get(view.getId());
        puzzle.clickBox(id);
        refreshUI();
    }

    public void clickStart(View view) {
        puzzle.clickStart();
        refreshUI();
    }
    public void clickShowAnswer(View view) {
        if(puzzle.giveAstarAnswer()){
            refreshUI();
        }
        else{
            // answer not exists
            Toast toast = Toast.makeText(this,"answer not exists",Toast.LENGTH_LONG);
            toast.show();
        }
    }

    /**
     *********************************************************
     */

    void refreshUI(){
        // update the value on box:
        for(int i = 0;i<9;i++){
            int val = puzzle.getBoxValue(i);
            TextView box = findViewById(boxResIds.get(i));
            if(val != 0){
                box.setText(String.valueOf(val));
                box.setBackgroundColor(getResources().getColor(R.color.filledGridColor));
            }
            else{
                box.setText(" ");
                box.setBackgroundColor(getResources().getColor(R.color.emptyGridColor));
            }
        }

        // update curStep and bestStep
        int curstep = puzzle.getCurstep();
        int beststep = puzzle.getBestStep();
        stepView.setText(String.valueOf(curstep));
        bestView.setText(String.valueOf(beststep));

        // Show win Stage if possible
        if(puzzle.isGameWin() && !puzzle.isCheating()){
            Toast toast = Toast.makeText(this,"Great Job",Toast.LENGTH_LONG);
            toast.show();
            winView.setVisibility(View.VISIBLE);
        }
        else{
            winView.setVisibility(View.INVISIBLE);
        }
    }
}
