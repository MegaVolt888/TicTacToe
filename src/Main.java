/*
  Tictactoe in object style
 */

import java.util.Scanner;

class Main {

    public static void main(String[] args) {

        TicTacToe tictac = new TicTacToe();
        int n = 0;
        do {
            System.out.println("Move number " + (++n));
        } while (!tictac.go());

    }
}


/*
   TickTackToe.
 */


/**
 * This code implementation is not optimal. Weights are unbalanced.
 * The code must be rewritten, but I really is short on time,
 * have a lot of work to do etc.
 */

class TicTacToe {

    private final int WIN_COINT = 5;
    private final int BOARD_SIZE = 9;
    private final char USER_CHAR = 'X';
    private final char COMP_CHAR = 'O';
    private final char EMPTY_CELL_CHAR = '.';
    private final char[][] board;
    /**
     * We assume the board is square, so we use only one dimension.
     */


    private int userMoves;
    private int compMoves;
    private int curX;
    private int curY;
    private boolean gameOver;


    public TicTacToe() {

        // Initialize count of moves;
        userMoves = 0;
        compMoves = 0;
        gameOver = false;
        board = new char[BOARD_SIZE][BOARD_SIZE];
        initBoard();
        printBoard();

    }

    public Boolean go() {


        userMove();
        printBoard();
        if (checkWin(USER_CHAR) != -1)
            gameOver = true;

        System.out.println("----------");
        if (!gameOver) {
            compMove();
            printBoard();
            if (checkWin(COMP_CHAR) != -1)
                gameOver = true;
        }


        return gameOver;

    }

    private void initBoard() {
        for (int i = 0; i < BOARD_SIZE; ++i)
            for (int j = 0; j < BOARD_SIZE; ++j)
                board[i][j] = EMPTY_CELL_CHAR;
    }

    private void printBoard() {

        System.out.print(".");
        for (int i = 1; i <= BOARD_SIZE; ++i)
            System.out.print(" " + i);
        System.out.println();
        for (int i = 0; i < BOARD_SIZE; ++i) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < BOARD_SIZE; ++j)
                System.out.print(board[i][j] + " ");
            System.out.println();
        }
        System.out.print(".");
        for (int i = 1; i <= BOARD_SIZE; ++i)
            System.out.print(" " + i);
        System.out.println();
        System.out.println("curX, curY : " + (curX + 1) + " | " + (curY + 1));
    }

    private void userMove() {
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("Enter X, Y");
            curX = sc.nextInt() - 1;
            curY = sc.nextInt() - 1;

            if (curX < 0 || curX >= BOARD_SIZE || curY < 0 || curY >= BOARD_SIZE || board[curY][curX] != EMPTY_CELL_CHAR)
                curX = -1;
        } while (curX == -1);
        board[curY][curX] = USER_CHAR;
        userMoves++;
    }

    private void compMove() {
        double maxMove, curMove;
        int maxX, maxY;
        maxMove = 0;
        maxX = maxY = 0;
        compMoves++;
        for (int i = 0; i < BOARD_SIZE; ++i)
            for (int j = 0; j < BOARD_SIZE; ++j) {
                if (board[i][j] != EMPTY_CELL_CHAR) continue;
                if (maxMove <= (curMove = moveWeigth(j, i, COMP_CHAR))) {
                    maxMove = curMove;
                    // Check here X Y assign for i j ? Debug
                    maxX = j;
                    maxY = i;

                }
            }

        for (int i = 0; i < BOARD_SIZE; ++i)
            for (int j = 0; j < BOARD_SIZE; ++j) {
                if (board[i][j] != EMPTY_CELL_CHAR) continue;
                if (maxMove < (curMove = moveWeigth(j, i, USER_CHAR))) {
                    maxMove = curMove;
                    // Check here X Y assign for i j ? Debug
                    maxX = j;
                    maxY = i;

                }
            }

        board[maxY][maxX] = COMP_CHAR;
        curY = maxY;
        curX = maxX;
    }

    private int checkWin(char whoisChar) {
        if (userMoves + compMoves == BOARD_SIZE * BOARD_SIZE) {
            System.out.println("Withdraw");
            return 0;
        }
        int vector[][] = {{0, 1}, {1, 0}, {1, 1}, {-1, 1}};
        int t_x, t_y;
        int n;
        for (int[] aVector : vector) {
            n = 1;

            t_x = curX;
            t_y = curY;
            while (t_x < BOARD_SIZE && t_x >= 0 && t_y < BOARD_SIZE && t_y >= 0) {
                if (board[t_y][t_x] != whoisChar) break;
                if (t_x != curX || t_y != curY) ++n;
                t_x += aVector[0];
                t_y += aVector[1];
            }

            t_x = curX;
            t_y = curY;

            while (t_x < BOARD_SIZE && t_x >= 0 && t_y < BOARD_SIZE && t_y >= 0) {
                if (board[t_y][t_x] != whoisChar) break;
                if (t_x != curX || t_y != curY) ++n;
                t_x -= aVector[0];
                t_y -= aVector[1];
            }
            if (n >= WIN_COINT) {
                System.out.println("User " + whoisChar + " wins! ");
                return 1;
            }
        }
        return -1;
    }

    private double moveWeigth(int a, int b, char moveChar) {
        double moveWeigth, curWeigth = 0.0;
        int n;
        int t_x, t_y;


        int vector[][] = {{0, 1}, {1, 0}, {1, 1}, {-1, 1}};

        for (int[] aVector : vector) {
            // Compare line finishing
            t_x = a;
            t_y = b;

            n = 1;
            moveWeigth = 0.0;

            while (t_x < BOARD_SIZE && t_x >= 0 && t_y < BOARD_SIZE && t_y >= 0) {
                if (t_x != a || t_y != b)
                    if (board[t_y][t_x] != moveChar) break;
                    else ++n;
                moveWeigth += 100;
                t_x += aVector[0];
                t_y += aVector[1];
                if (t_x < 0 || t_x == BOARD_SIZE || t_y < 0 || t_y == BOARD_SIZE) moveWeigth -= 30;
            }

            t_x = a;
            t_y = b;
            while (t_x < BOARD_SIZE && t_x >= 0 && t_y < BOARD_SIZE && t_y >= 0) {
                if (t_x != a || t_y != b)
                    if (board[t_y][t_x] != moveChar) break;
                    else ++n;
                moveWeigth += 100;
                t_x -= aVector[0];
                t_y -= aVector[1];
                if (t_x < 0 || t_x == BOARD_SIZE || t_y < 0 || t_y == BOARD_SIZE) moveWeigth -= 30;
            }

            if (n >= WIN_COINT && moveChar == COMP_CHAR) return 5000;
            if (n >= WIN_COINT && moveChar == USER_CHAR) return 2000;

            if (curWeigth < moveWeigth) curWeigth = moveWeigth;

        }
        if( a == 0 || a == BOARD_SIZE - 1|| b == 0 || b == BOARD_SIZE - 1) curWeigth -= 50;
        return curWeigth;
    }
}
