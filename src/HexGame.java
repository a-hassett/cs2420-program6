//i need to decide whether to have unionboard and color in hexgame or unionfind

import java.util.ArrayList;
import java.util.Objects;

public class HexGame {
    private static final int DEFAULT_BOARD_SIZE = 11;
    private static final String COLOR_RESET = "\u001B[0m";
    private static final String COLOR_RED = "\u001B[31m";
    private static final String COLOR_BLUE = "\u001B[34m";

    private int top;
    private int bottom;
    private int left;
    private int right;

    private boolean bluePlayerTurn = true;
    private int numberMoves = 0;

    private int boardSize;
    private String[] color;
    private UnionFind uf;

    // default initializer
    public HexGame(){
        this(DEFAULT_BOARD_SIZE);
    }

    // regular initializer
    public HexGame(int size){
        uf = new UnionFind(size * size + 5);
        boardSize = size;
        color = new String[size * size + 5];

        top = color.length - 4;
        color[top] = "R";

        bottom = color.length - 3;
        color[bottom] = "R";

        left = color.length - 2;
        color[left] = "B";

        right = color.length - 1;
        color[right] = "B";
    }

    /**
     * Print the game board in color
     */
    public void print(){
        StringBuilder board = new StringBuilder();
        String margin = " ";

        for(int i = 0; i < boardSize; i++){
            for(int j = 1; j < boardSize + 1; j++){
                String value = color[i * boardSize + j];

                if(value == "R"){
                    board.append(COLOR_RED).append(value).append(COLOR_RESET).append(" ");
                } else if(value == "B"){
                    board.append(COLOR_BLUE).append(value).append(COLOR_RESET).append(" ");
                } else{
                    board.append("0 ");
                }
            }
            board.append("\n");
            board.append(margin);
            margin += " ";
        }
        System.out.println(board);
    }

    // if the spot is taken, return false
    // if not taken, add the spot to the board with the correct color

    /**
     * Add a tile to the game board
     * Make sure the spot exists and that it's available
     * Then call getNeighbors for the new tile
     * @param choice index of the new tile
     * @return true if it's possible to add the tile, false if not
     */
    public boolean makeMove(int choice){
        // if choice is outside range, fix it
        if(choice > (boardSize * boardSize)){
            choice = boardSize * boardSize;
        } else if(choice <= 0){
            choice = 1;
        }

        // if the spot is available
        if(color[choice] == null){
            if(bluePlayerTurn){
                color[choice] = "B";
                getNeighbors(choice, "B");
            } else{
                color[choice] = "R";
                getNeighbors(choice, "R");
            }
            numberMoves ++;
            return true;
        }
        // if the spot is already used
        else{
            return false;
        }
    }

    /**
     * Find all the valid neighbors of the current tile
     * Union all the neighbors to it
     * @param index current tile
     * @param color blue or red tile
     */
    private void getNeighbors(int index, String color){
        ArrayList<Integer> neighbors = new ArrayList<>();
        neighbors.add(index - boardSize);
        neighbors.add(index - boardSize + 1);
        neighbors.add(index - 1);
        neighbors.add(index + 1);
        neighbors.add(index + boardSize - 1);
        neighbors.add(index + boardSize);

        boolean touchesTopOrRight = false;
        boolean touchesBottomOrLeft = false;
        if(index <= boardSize){
            touchesTopOrRight = true;
            neighbors.add(top);
            neighbors.remove(Integer.valueOf(index - boardSize));
        }
        if(Math.abs(index - boardSize * boardSize) < boardSize){
            touchesBottomOrLeft = true;
            neighbors.add(bottom);
            neighbors.remove(Integer.valueOf(index + boardSize));
        }
        if(index % boardSize == 1){
            touchesBottomOrLeft = true;
            neighbors.add(left);
            neighbors.remove(Integer.valueOf(index - 1));
        }
        if(index % boardSize == 0){
            touchesTopOrRight = true;
            neighbors.add(right);
            neighbors.remove(Integer.valueOf(index + 1));
        }
        if(touchesTopOrRight){
            neighbors.remove(Integer.valueOf(index - boardSize + 1));
        }
        if(touchesBottomOrLeft){
            neighbors.remove(Integer.valueOf(index + boardSize - 1));
        }

        for(int neighbor: neighbors) {
            try {
                Objects.checkIndex(neighbor, this.color.length);
            } catch (IndexOutOfBoundsException e) {
                neighbors.remove(Integer.valueOf(neighbor));
            }
        }

        for(Integer neighbor: neighbors){
            if(this.color[neighbor] != null && this.color[neighbor] == color){
                uf.union(index, neighbor);
            }
        }
    }

    /**
     * Check if the left and right are bound if blue's turn
     * Check if the top and bottom are bound if red's turn
     * If not, switch the turn
     */
    public void checkWin(){
        if(bluePlayerTurn && (uf.find(left) == uf.find(right))){
            System.out.print("Blue has won! ");
        } else if(uf.find(top) == uf.find(bottom)){
            System.out.print("Red has won! ");
        } else{
            bluePlayerTurn = !bluePlayerTurn;
            return;
        }
        System.out.printf("And it only took %d attempted moves! Here is the final board:\n", numberMoves);
        print();
        System.exit(0);
    }
}
