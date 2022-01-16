import model.Solver;

public class TestRushHour {
    //A boards
    private static String folderName = "testCases/";
    public static void aBoards(){
        for(int i = 0; i<10; i++){
            Solver tester1 = new Solver();
            char n = (char)(i + '0');
            tester1.solveFromFile(folderName + "A0"+ n +".txt", "A0"+ n +".sol");
        }
        Solver tester1 = new Solver();
        tester1.solveFromFile(folderName + "A10.txt", "A10.sol");
    }

    //B boards
    public static void bBoards(){

        for(int i = 1; i<10; i++){
            Solver tester2 = new Solver();
            char n = (char)(i + '0');
            tester2.solveFromFile(folderName +"B1"+ n +".txt", "B1"+ n +".sol");
        }
        Solver tester1 = new Solver();
        tester1.solveFromFile(folderName +"B20.txt", "B20.sol");
    }

    //C boards
    public static void cBoards(){
        for(int i = 1; i<10; i++){
            Solver tester3 = new Solver();
            char n = (char)(i + '0');
            tester3.solveFromFile(folderName +"C2"+ n +".txt", "C2"+ n +".sol");
        }
    }

    //D boards
    public static void dBoards(){
        for(int i = 0; i<6; i++){
            Solver tester4 = new Solver();
            char n = (char)(i + '0');
            tester4.solveFromFile(folderName +"D3"+ n +".txt", "D3"+ n +".sol");
        }
    }

    public static void main(String args[]) {
       // System.out.println("The start :)");

        long nano_startTime = System.nanoTime();
        long millis_startTime = System.currentTimeMillis();

        aBoards();
        System.out.println("Solved all A boards");
        bBoards();
        System.out.println("Solved all B boards");
        cBoards();
        System.out.println("Solved all C boards");
        dBoards();
        System.out.println("Solved all D boards");


        long nano_endTime = System.nanoTime();
        long millis_endTime = System.currentTimeMillis();

        // Print the time taken by subtracting the end-time from the start-time
        System.out.println("Time taken in nano seconds: "
                + (nano_endTime - nano_startTime));
        System.out.println("Time taken in milli seconds: "
                + (millis_endTime - millis_startTime));
    }
}


