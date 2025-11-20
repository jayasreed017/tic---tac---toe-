
import java.util.Scanner;
// getting symbol
class Player {
    private char symbol;

    public Player(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }
}

// printing board
class Board {
    private char[] board;
    
    public Board() {
        board = new char[]{'1','2','3','4','5','6','7','8','9'};
    }
    public void printBoard() {
         System.out.println("|---|---|---|");
        for (int i = 0; i < 9; i += 3) {
            System.out.println("| " + board[i] + " | " + board[i+1] + " | " + board[i+2] + " |");
            System.out.println("|---|---|---|");
    }
}
public boolean makeMove(int move , char symbol) {
    if (move < 1 || move > 9) return false;
    if(board[move - 1] != 'x' && board[move -1]!= 0){
        board[move - 1] = symbol;
        return true;
    }
    return false;
}
// checking winner
 public char checkWinner() {
        int[][] wins = {
            {0,1,2}, {3,4,5}, {6,7,8},
            {0,3,6}, {1,4,7}, {2,5,8},
            {0,4,8}, {2,4,6}
        };
// returning the winners
        for (int[] w : wins) {
            if (board[w[0]] == board[w[1]] && board[w[1]] == board[w[2]]) {
                return board[w[0]];
            }
        }
// checking if draw or not 
boolean full = true;
        for (char c : board) {
            if (c != 'X' && c != 'O') {
                full = false;
                break;
            }
        }

        return full ? 'D' : 'N'; // D = Draw, N = No winner yet
    }
}

// controlling the full game 

// instance variable
class Game {
    private Board board;
    private Player playerX;
    private Player playerO;
    private Player currentPlayer;
    private Scanner sc;
    
// constructor

public Game() {
    board = new Board();
    playerX = new Player('X');
    playerO = new Player('O');
    currentPlayer = playerX;
    sc = new Scanner(System.in);
}

// method for starting the game 

public void start() {
    char winner = 'N';
    
    // game loop
    while (winner == 'N') {
            board.printBoard();
            System.out.print(currentPlayer.getSymbol() + "'s turn. Enter slot (1-9): ");

            int move = sc.nextInt();

            if (!board.makeMove(move, currentPlayer.getSymbol())) {
                System.out.println("Invalid move! Try again.");
                continue;
            }

            winner = board.checkWinner();
            switchTurn();
        }

        board.printBoard();

        if (winner == 'D') {
            System.out.println("It's a Draw!");
        } else {
            System.out.println("Winner is " + winner);
        }
    }

    private void switchTurn() {
        currentPlayer = (currentPlayer == playerX) ? playerO : playerX;
    }
}
public class Game2 {
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}

