import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.stream.Collector.Characteristics;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Game extends JPanel{
	static Player a = new Player();
	 static Player b = new Player();
	 Board chessBoard = new Board(a,b);
	 boolean SquareSelected= false;
	
public Game()
	    {
	b.game= this;
	a.game = this;
	      setLayout(new GridLayout(8,8));
	      chessBoard.Resetboard(this);
	      a.inmove=true;
	    }

	
	
	public static void main(String a[])throws Exception{
	 /*Charset utf8Charset = Charset.forName("UTF-8");
	    Charset defaultCharset = Charset.defaultCharset();*/
	    //printing the window for chess
	    JFrame window = new JFrame("CHESS");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().add(new Game());
       window.setBounds(600,600,600,600);
        window.setVisible(true);        
        /* chessBoard.print();
	 //game loop
	 while(a.checkForMoveLeft() && b.checkForMoveLeft()) {
		// System.out.println(a.pieceSet.colour+"'s move");
		 move(a);
	 //chessBoard.turnTheBoard();
		// chessBoard.print();
		 chessBoard.Resetboard(this); 
		 //System.out.println(b.pieceSet.colour+"'s move");
		move(b);
	chessBoard.Resetboard(this); 
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
	 }*/
        
        //move(a);
}

public static boolean GameAlive(Player a, Player b) {
	if(a.checkForMoveLeft() && b.checkForMoveLeft()) {return true;}
	return false;
}

public static void GreenPossibleMoves(ArrayList<Board.Square> colorSquares) {
	for(Board.Square colorSquare : colorSquares) {
		colorSquare.setBackground(Color.GREEN);
	}
}	
}
