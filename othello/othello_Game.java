import java.util.*;
public class othello_Game {
    private player player1,player2;
    private Board board;
    private int numPlayers;
    public static void main(String[] args) {
        othello_Game o = new othello_Game();
        o.startGame();
    }

    public void startGame(){
        Scanner s = new Scanner(System.in);
        player1 = takePlayerInput(++numPlayers);
        player2 = takePlayerInput(++numPlayers);

        //check that both the players shouldn't have same symbols.
        while(player1.getSymbol() == player2.getSymbol()){
            System.out.println("Symbol is already taken!! Please enter the symbol again");
            player2.setSymbol(s.next().charAt(0));
        }

        //create the board
        board = new Board(player1.getSymbol(), player2.getSymbol());

        //play the game
        boolean player1Turn = true;
        //initially the board is incomplete.
        int status = Board.INCOMPLETE;

        while(status == Board.INCOMPLETE || status == Board.INVALIDMOVE){
            if(player1Turn){
                System.out.println("Player1 - " + player1.getName() + "'s turn");
                //to make a move the player has to enter the cell in the board in which they want to make the move.
                System.out.println("Enter x: ");
                int x = s.nextInt();
                System.out.println("Enter y: ");
                int y = s.nextInt();

                //this board function will make the move if valid and will tell the status of the game.
                status = board.move(player1.getSymbol(),x,y);
                
                if(status == Board.INVALIDMOVE){
                    System.out.println("Invalid move. Please enter the move again!!");
                    continue;
                }
            }else{
                System.out.println("Player2 - " + player2.getName() + "'s turn");
                //to make a move the player has to enter the cell in the board in which they want to make the move.
                System.out.println("Enter x: ");
                int x = s.nextInt();
                System.out.println("Enter y: ");
                int y = s.nextInt();

                //this board function will make the move if valid and will tell the status of the game.
                status = board.move(player2.getSymbol(),x,y);
                if(status == Board.INVALIDMOVE){
                    System.out.println("Invalid move. Please enter the move again!!");
                    continue;
                }
            }
            player1Turn = !player1Turn;
            board.print();
        }
        if(status == Board.PLAYER1WINS){
            System.out.println("Player 1 - " + player1.getName() + " Wins !!");
        }else if(status == Board.PLAYER2WINS){
            System.out.println("Player 2 - " + player2.getName() + " Wins !!");
        }else{
            System.out.println("Draw !!");
        }

    }

    //player input method.
    private player takePlayerInput(int num){
        Scanner s = new Scanner(System.in);
        System.out.println("Enter player " + num + "name: ");
        String name = s.nextLine();
        System.out.println("Enter player " + num + "Symbol: ");
        char symbol = s.next().charAt(0);
        player p = new player(name,symbol);
        return p;
    }
}
