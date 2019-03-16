package com.example.martin.tictactoe.feature;

import java.util.Random;

public class GameEngine {
    private static final Random RANDOM = new Random();
    private char[] field;
    private char currentPlayer;
    private boolean ended;

    // start new game
    public GameEngine() {
        field = new char[9];
        newGame();
    }

    // return the flag whether the game is finished
    public boolean isEnded() {
        return ended;
    }

    public char play(int x, int y) {
        // check if field is not used
        // return error if not
        if (' ' != field[3 * y + x]) {
            return 'F';
        } else if (!ended && field[3 * y + x] == ' ') {
            // normal move
            field[3 * y + x] = currentPlayer;
            changePlayer();
        }
        return checkEnd();
    }

    public void changePlayer() {
        currentPlayer = (currentPlayer == 'X' ? 'O' : 'X');
    }

    // get the status of a field
    public char getField(int x, int y) {
        return field[3 * y + x];
    }

    // start a new game
    public void newGame() {
        // set all fields to ' ' empty
        for (int i = 0; i < field.length; i++) {
            field[i] = ' ';
        }
        // start with player 'X'
        currentPlayer = 'X';
        // set ended flag to false
        ended = false;
    }

    // check whether one player has 3 in a row
    public char checkEnd() {
        for (int i = 0; i < 3; i++) {
            if (getField(i, 0) != ' ' &&
                    getField(i, 0) == getField(i, 1) &&
                    getField(i, 1) == getField(i, 2)) {
                ended = true;
                return getField(i, 0);
            }

            if (getField(0, i) != ' ' &&
                    getField(0, i) == getField(1, i) &&
                    getField(1, i) == getField(2, i)) {
                ended = true;
                return getField(0, i);
            }
        }

        if (getField(0, 0) != ' ' &&
                getField(0, 0) == getField(1, 1) &&
                getField(1, 1) == getField(2, 2)) {
            ended = true;
            return getField(0, 0);
        }

        if (getField(2, 0) != ' ' &&
                getField(2, 0) == getField(1, 1) &&
                getField(1, 1) == getField(0, 2)) {
            ended = true;
            return getField(2, 0);
        }

        for (int i = 0; i < 9; i++) {
            if (field[i] == ' ')
                return ' ';
        }


        return 'T';
    }

    public int checkDefense() {

        for (int y = 0; y<=2;y++ ) {
            if ((getField(0, y) == 'X') && (getField(1, y) == 'X') && (getField(2, y) == ' ')) {
                return 2 + (y * 3);
            } else if ((getField(0, y) == 'X') && (getField(2, y) == 'X') && (getField(1, y) == ' ')) {
                return 1 + (y * 3);
            } else if ((getField(1, y) == 'X') && (getField(2, y) == 'X') && (getField(0, y) == ' ')) {
                return 0 + (y * 3);
            }
        }
        for (int y = 0; y<=2;y++ ) {
            if ((getField(y, 0) == 'X') && (getField(y, 1) == 'X') && (getField(y, 2) == ' ')) {
                return y + (2 * 3);
            } else if ((getField(y, 0) == 'X') && (getField(y, 2) == 'X') && (getField(y, 1) == ' ')) {
                return y + (1 * 3);
            } else if ((getField(y, 1) == 'X') && (getField(y, 2) == 'X') && (getField(y, 0) == ' ')) {
                return y + (0 * 3);
            }
        }
        return -1;

    }

    // the computer player
    public char computer() {
        if (!ended) {

            int f = checkDefense();
            if (f != -1) {
                field[f] = currentPlayer;
                changePlayer();
            } else if (field[4] == ' ') {
                // prüfe und setze in die mitte
                field[4] = currentPlayer;
                changePlayer();
            } else {
                //prüfe und setze ecken
                if (field[0] == ' ') {
                    field[0] = currentPlayer;
                    changePlayer();

                } else if (field[2] == ' ') {
                    field[2] = currentPlayer;
                    changePlayer();
                } else if (field[6] == ' ') {
                    field[6] = currentPlayer;
                    changePlayer();
                } else if (field[8] == ' ') {
                    field[8] = currentPlayer;
                    changePlayer();
                } else {
                    int position = -1;
                    // try setting a random field which is not used
                    do {

                        position = RANDOM.nextInt(9);
                    } while (field[position] != ' ');

                    field[position] = currentPlayer;
                    changePlayer();
                }
            }
        }
        return checkEnd();

    }
}