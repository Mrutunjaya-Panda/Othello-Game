public class Board {
    private char board[][];
    private int boardSize = 8;
    private char p1Symbol, p2Symbol;
    private int countP1symbol = 2; // represents the no. of cells that have been filled. Since at the
    // start of the game four cells have been filled already.
    private int countP2symbol = 2;
    private static final char Empty = ' ';
    // for a layman person not knowing anything about the code, we should declare
    // constatant values which will declare
    // it's purpose.
    public static final int PLAYER1WINS = 1;
    public static final int PLAYER2WINS = 2;
    public static final int DRAW = 3;
    public static final int INCOMPLETE = 4;// here incomplete means not all cells are filled.
    public static final int INVALIDMOVE = 5;

    public Board(char p1Symbol, char p2Symbol) {
        board = new char[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = Empty;
            }
        }
        this.p1Symbol = p1Symbol;
        this.p2Symbol = p2Symbol;
        // initial configuration
        board[3][3] = p1Symbol;
        board[3][4] = p2Symbol;
        board[4][3] = p2Symbol;
        board[4][4] = p1Symbol;
    }

    public int move(char symbol, int x, int y) {
        // first check if valid move or not.
        // for validity in othello, even if I am able to convert 1 opponent's colour
        // into mine from all the 8 directions,
        // then also it is a valid move. In other words, as long as you are profitable.

        // check all 8 directions and look for conversion.
        // If at least one direction results in flipping → valid move.

        // You flip opponent pieces only when:

        // There's a straight line (in any direction) of opponent pieces
        // Followed by your own piece
        if (x < 0 || x >= boardSize || y < 0 || y >= boardSize || board[x][y] != Empty) {
            return INVALIDMOVE;
        }

        char opponent = (symbol == p1Symbol) ? p2Symbol : p1Symbol;
        boolean flippedAtLeastOne = false;

        // Directions: N,NE,E,SE,S,SW,W,NW
        int[] dx = { -1, -1, 0, 1, 1, 1, 0, -1 };
        int[] dy = { 0, 1, 1, 1, 0, -1, -1, -1 };

        // check for all 8 direction
        for (int d = 0; d < boardSize; d++) {
            int i = x + dx[d];
            int j = y + dy[d];

            boolean hasOpponentBetween = false;

            while (i >= 0 && i < boardSize && j >= 0 && j < boardSize && board[i][j] == opponent) {
                i += dx[d];
                j += dy[d];
                hasOpponentBetween = true;
            }

            // If we find our symbol after opponent(s), flip all in between
            if (hasOpponentBetween && i >= 0 && i < boardSize && j >= 0 && j < boardSize && board[i][j] == symbol) {
                int flipI = x + dx[d];
                int flipJ = y + dy[d];
                while (flipI != i || flipJ != j) {
                    board[flipI][flipJ] = symbol;
                    if (symbol == p1Symbol) {
                        countP1symbol++;
                        countP2symbol--;
                    } else {
                        countP2symbol++;
                        countP1symbol--;
                    }
                    flipI += dx[d];
                    flipJ += dy[d];
                }
                flippedAtLeastOne = true;
            }

        }

        if (!flippedAtLeastOne) {
            return INVALIDMOVE;
        }

        // make the move
        board[x][y] = symbol;
        if (symbol == p1Symbol) {
            countP1symbol++;
        } else {
            countP2symbol++;
        }

        int totalCells = boardSize * boardSize;
        if (countP1symbol + countP2symbol == totalCells) {
            if (countP1symbol > countP2symbol) {
                // return who won.
                return PLAYER1WINS;
            }

            if (countP1symbol < countP2symbol) {
                // return who won.
                return PLAYER2WINS;
            }
            // now if no one has won, either it's incomplete or is draw.
            if (countP1symbol == countP2symbol) {
                return DRAW;
            }
        }

        // the game is still on..
        return INCOMPLETE;
    }

    // print method.
    public void print() {
        System.out.println("-------------");
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                System.out.print("| " + board[i][j] + " |");
            }
            System.out.println();

        }
        System.out.println("-------------");
    }
}
