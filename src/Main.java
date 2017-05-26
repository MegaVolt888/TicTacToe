/**
 * Lesson 4. Student Vladimir Mushkin
 * Objects and tictactoe in object style
 */

import java.util.Scanner;
import java.util.StringJoiner;
import java.util.function.BooleanSupplier;

public class Main {

    public static void main(String[] args) {

        TicTacToe tictac = new TicTacToe();
        int n = 0;
        do {
            System.out.println("Move number " + (++n));
        } while (!tictac.go());

    }
}


/**
 * Lesson3. TickTackToe. Student - Vladimir Mushkin
 */


/**
 * This code implementation is not optimal. Weights are unbalanced.
 * The code must be rewritten, but I really is short on time,
 * have a lot of work to do etc.
 */

class TicTacToe {

    /**
     * We assume the board is square, so we use only one dimension.
     */


    int userMoves, compMoves, curX, curY, cellsCount;
    char[][] board;
    boolean gameOver;

    final int WIN_COINT = 4;
    final int BOARD_SIZE = 5;
    final char USER_CHAR = 'X';
    final char COMP_CHAR = 'O';
    final char EMPTY_CELL_CHAR = '.';


    public TicTacToe() {

        // Initialize count of moves;
        userMoves = 0;
        compMoves = 0;
        gameOver = false;
        cellsCount = BOARD_SIZE * BOARD_SIZE;
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

    void initBoard() {
        for (int i = 0; i < BOARD_SIZE; ++i)
            for (int j = 0; j < BOARD_SIZE; ++j)
                board[i][j] = EMPTY_CELL_CHAR;
    }

    void printBoard() {
        System.out.println(". 1 2 3 4 5 ");
        for (int i = 0; i < BOARD_SIZE; ++i) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < BOARD_SIZE; ++j)
                System.out.print(board[i][j] + " ");
            System.out.println();
        }
        System.out.println(". 1 2 3 4 5 ");
        System.out.println("curX, curY : " + (curX + 1) + " | " + (curY + 1));
    }

    void userMove() {
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("Enter X, Y");
            curY = sc.nextInt() - 1;
            curX = sc.nextInt() - 1;
            if (curX < 0 || curX >= BOARD_SIZE || curY < 0 || curY >= BOARD_SIZE || board[curX][curY] != EMPTY_CELL_CHAR)
                curX = -1;
        } while (curX == -1);
        board[curX][curY] = USER_CHAR;
        userMoves++;
    }

    void compMove() {
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

    int checkWin(char whoisChar) {
        if (userMoves + compMoves == BOARD_SIZE * BOARD_SIZE) {
            System.out.println("Withdraw");
            return 0;
        }
        int vector[][] = {{0, 1}, {1, 0}, {1, 1}, {-1, 1}};
        int t_x, t_y;
        int n;
        for (int i = 0; i < vector.length; ++i) {
            n = 1;

            t_x = curX;
            t_y = curY;
            while (t_x < BOARD_SIZE && t_x >= 0 && t_y < BOARD_SIZE && t_y >= 0) {
                if (board[t_y][t_x] != whoisChar) break;
                if (t_x != curX || t_y != curY) ++n;
                t_x += vector[i][0];
                t_y += vector[i][1];
            }

            t_x = curX;
            t_y = curY;

            while (t_x < BOARD_SIZE && t_x >= 0 && t_y < BOARD_SIZE && t_y >= 0) {
                if (board[t_y][t_x] != whoisChar) break;
                if (t_x != curX || t_y != curY) ++n;
                t_x -= vector[i][0];
                t_y -= vector[i][1];
            }
            if (n >= WIN_COINT) {
                System.out.println("User " + whoisChar + " wins! ");
                return 1;
            }
        }
        return -1;
    }

    boolean checkFull() {
        return userMoves + compMoves == BOARD_SIZE * BOARD_SIZE;
    }

    double moveWeigth(int a, int b, char moveChar) {
        double moveWeigth = 0.00, curWeigth = 0.0;
        int n;
        int t_x, t_y;


        int vector[][] = {{0, 1}, {1, 0}, {1, 1}, {-1, 1}};

        for (int i = 0; i < vector.length; ++i) {
            // Compare line finishing
            t_x = a;
            t_y = b;

            n = 0;
            moveWeigth = 0;

            while (t_x < BOARD_SIZE && t_x >= 0 && t_y < BOARD_SIZE && t_y >= 0) {
                if (t_x == a && t_y == b) {
                    moveWeigth += 100;
                    t_x += vector[i][0];
                    t_y += vector[i][1];
                    continue;
                }
                if (board[t_y][t_x] != moveChar) break;
                moveWeigth += 100;
                ++n;
                t_x += vector[i][0];
                t_y += vector[i][1];
            }

            t_x = a;
            t_y = b;

            while (t_x < BOARD_SIZE && t_x >= 0 && t_y < BOARD_SIZE && t_y >= 0) {
                if (t_x == a && t_y == b) {
                    t_x -= vector[i][0];
                    t_y -= vector[i][1];
                    continue;
                }
                if (board[t_y][t_x] != moveChar) break;
                moveWeigth += 100;
                ++n;
                t_x -= vector[i][0];
                t_y -= vector[i][1];
            }

            if (n >= 4 && curWeigth == COMP_CHAR) return 5000;
            if (n >= 4 && curWeigth == USER_CHAR) return 2000;
            if (curWeigth < moveWeigth) curWeigth = moveWeigth;

        }

        return curWeigth;
    }
}
