package huang.bling.a8puzzle.puzzle;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.logging.Logger;

public class Puzzle {
    static Random random;
    static Logger logger = Logger.getAnonymousLogger();
    int curx;
    int cury;
    public ArrayList<Integer> puzzle;
    // The direction of puzzle index;
    //    0    y   2
    //  0 ----------
    //    |
    //  x |
    //    |
    public Puzzle(){
        // init variable
        Date date = new Date();
        random  = new Random(date.getTime());
        //create puzzle
        createPuzzle();
    }
    public Puzzle(ArrayList<Integer> startState){
        puzzle = startState;
        int index = puzzle.indexOf(0);
        curx = index / 3;
        cury = index % 3;
    }
    public boolean isSolved(){
        for(int i= 0;i<puzzle.size()-1;i++){
            if(puzzle.get(i) != i+1)
                return false;
        }
        return true;
    }

    int convertIndex(int x,int y){
        return x*3 + y;
    }

    int getElement(int x,int y){
        return puzzle.get(convertIndex(x,y));
    }

    void setElement(int x,int y, int val){
        puzzle.set(convertIndex(x,y),val);
    }

    void moveTo(int x,int y){
        int tarVal = getElement(x,y);
        int curVal = getElement(curx,cury);
        setElement(x,y,curVal);
        setElement(curx,cury,tarVal);
        curx = x;
        cury = y;
    }
    public boolean move(int index){
        int x = index / 3;
        int y = index % 3;

        if(x == curx-1 && y == cury
                || x ==curx+1 && y == cury
                || y == cury-1 && x == curx
                || y == cury+1 && x == curx){
            moveTo(x,y);
            return true;
        }
        return false;
    }

    private void createPuzzle(){
        //Create Puzzle
        puzzle = new ArrayList<Integer>();
        for(int i = 1;i<=8;i++){
            puzzle.add(i);
        }
        puzzle.add(0);
        curx = 2;
        cury = 2;

        // shuffle puzzle
        // move randomly 100 time
        int count = 0;
        while(true){
            if( count == 100)
                break;

            int dir = random.nextInt(4);
            //logger.info(String.valueOf(dir));
            switch(dir){
                // move up
                case 0:
                    if(curx != 0){
                        moveTo(curx-1,cury);
                        count++;
                    }
                    break;
                // move down
                case 1:
                    if(curx != 2){
                        moveTo(curx+1,cury);
                        count++;
                    }
                    break;
                // move left
                case 2:
                    if(cury!=0){
                        moveTo(curx,cury-1);
                        count++;
                    }
                    break;
                // move right
                case 3:
                    if(cury!=2){
                        moveTo(curx,cury+1);
                        count++;
                    }
                    break;
            }
        }
    }
    void print(){
        ArrayList<Integer> a = puzzle;
        System.out.printf(  " %d %d %d \n" +
                        " %d %d %d \n" +
                        " %d %d %d \n\n",  a.get(0),a.get(1),a.get(2)
                ,a.get(3),a.get(4),a.get(5)
                ,a.get(6),a.get(7),a.get(8));
    }
}
