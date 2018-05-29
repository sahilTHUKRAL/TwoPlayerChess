import java.nio.charset.Charset;
import java.util.ArrayList;

public class Game{
	
public static void main(String A[])throws Exception{
	 Charset utf8Charset = Charset.forName("UTF-8");
	    Charset defaultCharset = Charset.defaultCharset();
	Player a = new Player();
	Player b = new Player();
	 Board chessBoard = new Board(a,b);
	 chessBoard.print();

	
	 //game loop
	 while(a.checkForMoveLeft() && b.checkForMoveLeft()) {
		 System.out.println(a.pieceSet.colour+"'s move");
		 a.move();
	 //chessBoard.turnTheBoard();
		 chessBoard.print();
		 System.out.println(b.pieceSet.colour+"'s move");
		 b.move();
	 chessBoard.print();
}
	 if(!a.moveLeft)
	 {
		 if(a.underCheck) {
		 System.out.println(" Player B wins");
		 }
		 else {
			 System.out.println("Match Draw By staleMate");
		 }
	 }
	 
	 if(!b.moveLeft)
	 {
		 if(b.underCheck) {
		 System.out.println(" Player A wins");
		 }
		 else {
			 System.out.println("Match Draw By staleMate");
		 }
	 }
}
public static boolean GameAlive(Player a, Player b) {
	if(a.checkForMoveLeft() && b.checkForMoveLeft()) {return true;}
	return false;
}
}