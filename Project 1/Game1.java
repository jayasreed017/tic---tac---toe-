import java.util.*;

public class Game1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        char[] board = {'1','2','3','4','5','6','7','8','9'};
        char turn = 'X';
        char winner = 'N';

        while (winner == 'N') {
            // Print board
            System.out.println("|---|---|---|");
            for (int i = 0; i < 9; i += 3) {
                System.out.println("| " + board[i] + " | " + board[i+1] + " | " + board[i+2] + " |");
                System.out.println("|---|---|---|");
            }

            // Ask for input
            System.out.print(turn + "'s turn. Enter slot (1-9): ");
            int move = sc.nextInt();

            // Check valid move
            if (board[move-1] != 'X' && board[move-1] != 'O') {
                board[move-1] = turn;
            } else {
                System.out.println("Slot already taken, try again!");
                continue;
            }

            // Check winner
            int[][] wins = {
                {0,1,2}, {3,4,5}, {6,7,8}, // rows
                {0,3,6}, {1,4,7}, {2,5,8}, // cols
                {0,4,8}, {2,4,6}           // diagonals
            };

            for (int[] w : wins) {
                if (board[w[0]] == board[w[1]] && board[w[1]] == board[w[2]]) {
                    winner = board[w[0]];
                }
            }

            // Check draw
            boolean full = true;
            for (int i = 0; i < 9; i++) {
                if (board[i] != 'X' && board[i] != 'O') {
                    full = false;
                    break;
                }
            }
            if (full && winner == 'N') {
                winner = 'D';
            }

            // Switch turn
            if (winner == 'N') {
                turn = (turn == 'X') ? 'O' : 'X';
            }
        }

        // Print final board
        System.out.println("|---|---|---|");
        for (int i = 0; i < 9; i += 3) {
            System.out.println("| " + board[i] + " | " + board[i+1] + " | " + board[i+2] + " |");
            System.out.println("|---|---|---|");
        }

        // Result
        if (winner == 'D') {
            System.out.println("It's a Draw!");
        } else {
            System.out.println("Winner is " + winner);
        }

        sc.close();
    }
}
