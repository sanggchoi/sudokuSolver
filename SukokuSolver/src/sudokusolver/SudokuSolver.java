package sudokusolver;

/**
 * @author sanggon.choi
 */
public class SudokuSolver{
    public static void main(String[] args){
        Solver2 solver = new Solver2("C:\\Users\\sangg\\Desktop\\sudoku.txt");
        solver.solve();
    }
}