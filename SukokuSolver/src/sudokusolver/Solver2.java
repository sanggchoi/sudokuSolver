package sudokusolver;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;
/**
 * @author sanggon.choi
 */
public class Solver2 {
    private Stack<String> stack_;
    private int[][] grid_;
    public Solver2(String path){
        grid_ = new int[9][9];
        stack_ = new Stack();
        readFile(path);
        initialize(grid_);
    }
    
    public void readFile(String path){
        try{
            File unsolved = new File(path);
            FileReader fr = new FileReader(unsolved);
            Scanner sc = new Scanner(fr);
            for(int n = 0;n < 9;n++){
                String[] s = sc.nextLine().split(",");
                for(int n2 = 0;n2 < 9;n2++){
                    grid_[n][n2] = Integer.parseInt(s[n2]);
                }
            }
            fr.close();
        }catch(IOException e){
            System.out.println(e);
        }
    }
    
    public void initialize(int[][] board){
        for(int n = 0;n < 9;n++){
            for(int n2 = 0;n2 < 9;n2++){
                if(board[n][n2] > 0){
                    stack_.push(board[n][n2]+","+n+","+n2);
                }
            }
        }
    }
    
    public boolean validSquare(){
        if(stack_.size() > 0){
            String[] s = stack_.peek().split(",");
            int[] i = new int[3];
            for(int n = 0;n < 3;n++){
                i[n] = Integer.parseInt(s[n]);
            }        
            int num = i[0];
            int row = i[1];
            int col = i[2];
            for(int n = 0;n < 9;n++){
                if(stack_.search(num+","+n+","+col) > 1){
                    return false; 
                }
            }
            for(int n = 0;n < 9;n++){
                if(stack_.search(num+","+row+","+n) > 1){
                return false;
                }
            }
            for(int n = 0;n < 3;n++){
                for(int n2 = 0; n2<3; n2++){
                    if(stack_.search(num+","+(row-(row%3)+n)+","+(col-(col%3)+n2)) > 1){
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    public boolean canSolve(int row,int col,int[][] unsolved){  
        if(row == 9){
            row = 0;
            col++;
        }
        if(!validSquare()){ //isValid
            return false;
        }else if(col > 8){ //isSolution
            return true;
        }else{ //getFrontier
            if(unsolved[row][col] == 0){
                for(int n = 1;n < 10;n++){
                    stack_.push(n+","+row+","+col);
                    boolean solvable = canSolve(row+1,col,unsolved);
                    if(!solvable){
                        stack_.pop();
                    }else{
                        return solvable;
                    }
                }
                return false;
            }
            return canSolve(row+1,col,unsolved);
        }
    }
    
    public int[][] solvedBoard(){
        int[][] solved = new int[9][9];
        while(!stack_.empty()){
            String[] s = stack_.peek().split(",");
            int[] i = new int[3];
            for(int n = 0;n < 3;n++){
                i[n] = Integer.parseInt(s[n]);
            }
            solved[i[1]][i[2]] = i[0];
            stack_.pop();
        }
        return solved;
    }
    
    public static void print(int[][] grid){
        for(int n = 0;n < 9;n++){
            for(int n2 = 0;n2 < 9;n2++){
                System.out.print(grid[n][n2]);
                if(n2 < 8){
                    System.out.print(",");
                }else{
                    System.out.println();
                }
            }
        }
    }
    
    public void solve(){
        if(canSolve(0,0,grid_)){
            print(solvedBoard());
        }
    }
}
