package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.InvalidParameterException;
import java.util.*;
import java.io.*;


public class Solver {
    private int maincar; // 'X' car
    private Board starterboard;
    private Board solvedboard;

    public Solver(){
        starterboard = null;
        solvedboard = null;
    }


    public void solveFromFile(String input, String output) {
        try {
            setup(input); // Sets up the initial board and carArrayList
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //boardLinkedList for Queue
        LinkedList<Board> boardLinkedList = new LinkedList<>();
        boardLinkedList.add(starterboard);
        //Hashset to store visited states,
        Set<Board> visitedTest = new HashSet<>();
        visitedTest.add(starterboard);

        //Loop until the Queue is empty or we find a solution
        outerloop:
        while (!boardLinkedList.isEmpty()) {
            LinkedList<Board> newStates = getStates(boardLinkedList.removeFirst()); // Get new states for currentboard
            while (!newStates.isEmpty()) { // loop through all the new states we got
                Board remove = newStates.removeFirst();
                if (!visitedTest.contains(remove)) { // Check if each state is already visited or not
                    boardLinkedList.add(remove);  // If its a new state, add it to end of Queue
                    if (isSolved(remove)) { // If the state solves the puzzle, break outerloop and end loop
                        solvedboard = remove;
                        break outerloop;
                    }
                    visitedTest.add(remove);  // Add the new state to visited Hashset
                }
            }
        }
        //Output to solution file method
        outputToFile(output);
    }

    private void outputToFile(String output){
        if(solvedboard == null) return;

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("testCases/" + output);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        PrintStream ps = new PrintStream(fos);
        ArrayList<String> moves = new ArrayList<>();

        //Loop and get all the moves that were done using the "movedone" variable from the Board class.
        while (solvedboard.prev != null) {
            moves.add(solvedboard.moveDone);
            solvedboard = solvedboard.prev;
        }
        //Loop back and print into the solution file
        while (!moves.isEmpty()) {
            ps.println(moves.remove(moves.size() - 1));
        }
        ps.close();
    }

    //Helper method that sets up the intial board and car lists
    private void setup(String input) throws FileNotFoundException {
        //Fields used to make intial board
        ArrayList<Character> carnames = new ArrayList<>();
        ArrayList<Car> carArrayList = new ArrayList<>();
        char[][] board = new char[6][];

        File file = new File(input);
        if (!file.exists())
            throw new InvalidParameterException();
        Scanner scanner = new Scanner(file);
        int x =0;
        String line;
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            //Determines if each row has 6 spots
            if(line.length() != 6){throw new InvalidParameterException("Invalid board");}
            board[x] = line.toCharArray();
            ++x;
        }
        scanner.close();
        //Use x to determine if board has 6 rows
        if(x != 6){throw new InvalidParameterException("Invalid Board");}

        // set up the cars into an ArrayList
        for(int i = 0; i<6; i++){
            for(int j = 0; j<6; j++){
                if(board[i][j] != '.'){
                    if(!carnames.contains(board[i][j])){
                        makeCar(i ,j, board[i][j], carnames, carArrayList, board);
                    }
                }
            }
        }
        maincar = carnames.indexOf('X');
        starterboard = new Board(board, carnames, carArrayList);
    }

    // makes a brand new Car object from the provided board and x,y units. Stores it in next available carArrayList position.
    private void makeCar(int x, int y, char name, ArrayList<Character> carnames, ArrayList<Car> carArrayList, char[][] board){
        carnames.add(name);
        int length = 0;
        //Check if car is horizontal
        if(y != 5 && board[x][y+1] == name){
            while(y !=6 && board[x][y] == name){
                ++length;
                ++y;
            }
            Car newcar = new Car(x,y-length,69,length,name);
            carArrayList.add(newcar); // add car to end of Array
        }
        else{
            while(x !=6 && board[x][y] == name){
                ++length;
                ++x;
            }
            Car newcar = new Car(x-length,y,0,length,name);
            carArrayList.add(newcar); // Add car to end of Array
        }
        if(name == 'X'){
            maincar = carnames.size()-1;
        }
    }

    //returns a LinkedList with all possible Board states
    private LinkedList<Board> getStates(Board initial) {
        LinkedList<Board> states = new LinkedList<>();
        for (int i = 0; i < initial.carnames.size(); ++i) { //Loops through all the Cars in the board
            if (initial.carArrayList.get(i).direction == 0) {
                // up or down car states
                if (initial.canMove(initial.carnames.get(i), "up", 1)) {
                    Board newstate = new Board(initial.board, initial.carnames, initial.carArrayList);
                    newstate.makeMove(initial.carnames.get(i), "up", 1);
                    newstate.setPrevBoard(initial);
                    states.add(newstate);
                }

                if (initial.canMove(initial.carnames.get(i), "down", 1)) {
                    Board newstate2 = new Board(initial.board, initial.carnames, initial.carArrayList);
                    newstate2.makeMove(initial.carnames.get(i), "down", 1);
                    newstate2.setPrevBoard(initial);
                    states.add(newstate2);
                }
            }
            // left or right car states
            else {
                if (initial.canMove(initial.carnames.get(i), "left", 1)) {
                    Board newstate = new Board(initial.board, initial.carnames, initial.carArrayList);
                    newstate.makeMove(initial.carnames.get(i), "left", 1);
                    newstate.setPrevBoard(initial);
                    states.add(newstate);
                }
                if (initial.canMove(initial.carnames.get(i), "right", 1)) {
                    Board newstate2 = new Board(initial);
                    newstate2.makeMove(initial.carnames.get(i), "right", 1);
                    newstate2.setPrevBoard(initial);
                    states.add(newstate2);
                }
            }
        }
        return states;
    }

    //Checks if Board is solved
    public boolean isSolved(Board b){
        Car solved = b.carArrayList.get(maincar);
        return (solved.getY() + solved.getLength()) == 6;
    }

    // Prints the board
    public void PrintBoard(Board b){
        for(int z = 0; z<6; ++z) {
            for (int i = 0; i < 6; ++i) {
                System.out.print(b.board[z][i]);
            }
            System.out.println("");
        }
    }

    //Prints the SolvedBoard
    public void PrintSolvedBoard(){
        if(solvedboard == null) return;
        for(int z = 0; z<6; ++z) {
            for (int i = 0; i < 6; ++i) {
                System.out.print(solvedboard.board[z][i]);
            }
            System.out.println("");
        }
    }

}
