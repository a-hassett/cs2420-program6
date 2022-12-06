import java.io.File;
import java.util.Scanner;

public class PlayHex {
    public static void main(String[] args){
        interactiveGame();
        //presetGame("moves1.txt");
        //presetGame("moves2.txt");
    }

    /**
     * Pass a file full of integers into this function to play Hex
     * @param filename file of moves
     */
    public static void presetGame(String filename){
        HexGame game = new HexGame();
        int move;
        boolean spotTaken;

        System.out.println("Welcome to HEX !\n");

        try {
            Scanner reader = new Scanner(new File(filename));
            while (reader.hasNext()) {
                do{
                    System.out.print("Blue player's turn: ");
                    move = Integer.parseInt(reader.next());
                    System.out.println(move);
                    spotTaken = !game.makeMove(move);
                } while(spotTaken);

                game.print();
                game.checkWin();

                do{
                    System.out.print("Red player's turn: ");
                    move = Integer.parseInt(reader.next());
                    System.out.println(move);
                    spotTaken = !game.makeMove(move);
                } while(spotTaken);

                game.print();
                game.checkWin();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Run this function when you want to choose the board size and moves
     * Has the same functionality just isn't automatic
     */
    public static void interactiveGame(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to HEX !\n");
        System.out.print("How large would you like your Hex board to be? ");
        int boardSize = scanner.nextInt();

        HexGame game = new HexGame(boardSize);

        System.out.printf("\nYour moves can be between 1 and %d\n", boardSize * boardSize);
        System.out.printf("Anything larger than that will default to %d\n", boardSize * boardSize);
        System.out.println("Anything smaller will default to 1\n");

        boolean done = false;
        int move;
        boolean spotTaken;

        while(!done){
            do{
                System.out.print("Blue player's turn: ");
                move = scanner.nextInt();
                spotTaken = !game.makeMove(move);
            } while(spotTaken);

            game.print();
            game.checkWin();

            do{
                System.out.print("Red player's turn: ");
                move = scanner.nextInt();
                spotTaken = !game.makeMove(move);
            } while(spotTaken);

            game.print();
            game.checkWin();
        }
    }
}
