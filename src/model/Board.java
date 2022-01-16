package model;
import java.util.ArrayList;

public class Board {
    public char[][] board;
    public ArrayList<Character> carnames = new ArrayList<>();
    public ArrayList<Car> carArrayList = new ArrayList<>();
    public Board prev = null;
    public String moveDone = null;

    public Board(char[][] b, ArrayList<Character> names, ArrayList<Car> carList){
        board = deepCopy(b);
        for(int i =0; i<names.size(); ++i) {
            this.carnames.add(names.get(i));
            this.carArrayList.add(new Car(carList.get(i)));
        }
    }
    //Found this on stackoverflow for deepCopying a 2D Array
    <Character> char[][] deepCopy(char[][] matrix) {
        return java.util.Arrays.stream(matrix).map(el -> el.clone()).toArray($ -> matrix.clone());
    }

    //copy constructor
    Board(Board b) {
        this.board = deepCopy(b.board);
        for(int i =0; i<b.carnames.size(); ++i) {
            this.carnames.add(b.carnames.get(i));
            this.carArrayList.add(new Car(b.carArrayList.get(i)));
        }
    }

    public void setPrevBoard(Board b){
        prev = b;
    }

    public Board getPrevBoard(){
        return prev;
    }

    //Checks if specific car can move in indicated direction
    public boolean canMove(char name, String direction, int amount){
        int index = carnames.indexOf(name);
        Car c = carArrayList.get(index);

        if(direction == "up"){
            if(c.getX() - amount >= 0){
                if(board[c.getX() - amount][c.getY()] == '.')
                    return true;
            }
        }
        else if(direction == "down"){
            if(c.getX() + c.getLength()-1 + amount <= 5){
                if(board[c.getX() + c.getLength()-1 + amount][c.getY()] == '.')
                    return true;
            }
        }
        else if(direction == "left"){
            if(c.getY() - amount >= 0){
                if(board[c.getX()][c.getY() - amount] == '.')
                     return true;
            }
        }
        else{
            if(c.getY() + c.getLength()-1 + amount <= 5){
                if((board[c.getX()][c.getY() + c.getLength()-1 + amount]) == '.')
                    return true;
            }
        }
        return false;

    }

    //Moves the specified car in the 2d board and updates the Car
    public void makeMove(char name, String direction, int amount){
        int index = carnames.indexOf(name);
        Car c = carArrayList.get(index);

        if(direction == "up"){
            for(int i = c.getX(); i<c.getLength() + c.getX(); i++){
                board[i][c.getY()] = '.';
            }
                c.setX(c.getX() - amount);
            for(int i = c.getX(); i<c.getLength() + c.getX(); i++){
                board[i][c.getY()] = c.getName();
            }
            moveDone = name + "U" + '1';
        }
        else if(direction == "down"){
            for(int i = c.getX(); i<c.getLength() + c.getX(); i++){
                board[i][c.getY()] = '.';
            }
                c.setX(c.getX() + amount);
            for(int i = c.getX(); i<c.getLength() + c.getX(); i++){
                board[i][c.getY()] = c.getName();
            }
            moveDone = name + "D" + '1';
        }
        else if(direction == "left"){
            for(int i = c.getY(); i<c.getLength() + c.getY(); i++){
                board[c.getX()][i] = '.';
            }
                c.setY(c.getY() - amount);
            for(int i = c.getY(); i<c.getLength() + c.getY(); i++){
                board[c.getX()][i] = c.getName();
            }
            moveDone = name + "L" + '1';
        }
        else{
            for(int i = c.getY(); i<c.getLength() + c.getY(); i++){
                board[c.getX()][i] = '.';
            }
                c.setY(c.getY() + amount);
            for(int i = c.getY(); i<c.getLength() + c.getY(); i++){
                board[c.getX()][i] = c.getName();
            }
            moveDone = name + "R" + '1';
        }
    }

    @Override
    public boolean equals(Object o){
        if (o == this) {
            return true;
        }
        Board oo = (Board)o;
        return java.util.Arrays.deepEquals(this.board, oo.board);
    }

    @Override
    public int hashCode() {
        int result = 37 * java.util.Arrays.deepHashCode(board) ;
        return result;
    }
}

