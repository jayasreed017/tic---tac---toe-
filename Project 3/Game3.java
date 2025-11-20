import java.util.Scanner;

// Player class
class Player {
    private char symbol;

    public Player(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }
}

// Custom exception for invalid moves
class InvalidMoveException extends Exception {
    public InvalidMoveException(String message) {
        super(message);
    }
}

// Board class (throws InvalidMoveException on bad moves)
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

    // Now throws InvalidMoveException instead of returning false
    public void makeMove(int move, char symbol) throws InvalidMoveException {
        if (move < 1 || move > 9) {
            throw new InvalidMoveException("Move out of range. Enter a number between 1 and 9.");
        }

        if (board[move - 1] == 'X' || board[move - 1] == 'O') {
            throw new InvalidMoveException("Slot " + move + " is already taken.");
        }

        board[move - 1] = symbol;
    }

    public char checkWinner() {
        int[][] wins = {
            {0,1,2}, {3,4,5}, {6,7,8},
            {0,3,6}, {1,4,7}, {2,5,8},
            {0,4,8}, {2,4,6}
        };

        for (int[] w : wins) {
            if (board[w[0]] == board[w[1]] && board[w[1]] == board[w[2]]) {
                return board[w[0]];
            }
        }

        boolean full = true;
        for (char c : board) {
            if (c != 'X' && c != 'O') {
                full = false;
                break;
            }
        }

        return full ? 'D' : 'N';
    }
}

// Game class: reads moves, catches exceptions
class Game {
    private Board board;
    private Player playerX;
    private Player playerO;
    private Player currentPlayer;
    private Scanner sc;

    public Game() {
        board = new Board();
        playerX = new Player('X');
        playerO = new Player('O');
        currentPlayer = playerX;
        sc = new Scanner(System.in);
    }

    // Helper: read a move from the user and convert parse errors to InvalidMoveException
    private int readMove() throws InvalidMoveException {
        String line = sc.nextLine().trim();
        if (line.isEmpty()) {
            throw new InvalidMoveException("No input entered. Please type a number between 1 and 9.");
        }
        try {
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            throw new InvalidMoveException("Invalid input. Please enter a valid number between 1 and 9.");
        }
    }

    public void start() {
        char winner = 'N';

        while (winner == 'N') {
            board.printBoard();
            System.out.print(currentPlayer.getSymbol() + "'s turn. Enter slot (1-9): ");

            try {
                int move = readMove();                   // may throw InvalidMoveException
                board.makeMove(move, currentPlayer.getSymbol()); // may throw InvalidMoveException

                winner = board.checkWinner();
                // Only switch turn if move succeeded and game continues
                if (winner == 'N') switchTurn();

            } catch (InvalidMoveException ime) {
                // Handle invalid move gracefully and prompt again
                System.out.println("Invalid move: " + ime.getMessage());
                System.out.println("Please try again.\n");
                // loop continues without switching player
            }
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

// Main class
public class Game3 {
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}
