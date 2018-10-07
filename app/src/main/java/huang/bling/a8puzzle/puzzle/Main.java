package huang.bling.a8puzzle.puzzle;

import java.util.ArrayList;
import java.util.Date;

public class Main {

    public static void main(String[] args) {

        Solution solution = new Solution();

        Date start = new Date();
        for(int i = 0;i<100;i++){
            Puzzle puzzle = new Puzzle();
            puzzle.print();
            solution.solveProblem(solution.array2String(puzzle.puzzle));
        }
        Date end = new Date();
        long msTime = end.getTime() - start.getTime();
        System.out.println((double)msTime/100);
    }
}
