import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;


	// when a button is clicked, it generates an ActionEvent. Thus, each button needs an ActionListener. When it is clicked, it goes to this listener class that I have created and goes to the actionPerformed method. There (and in this class), we decide what we want to do.
   public class buttonListner implements ActionListener
    {       
	   static boolean ispieceSelected = false;
	   static PieceSet.Piece pieceSelected;
	   public void actionPerformed(ActionEvent e) 
        {
        	Board.Square buttonClicked = (Board.Square)e.getSource();
        	
        	if(buttonClicked.piece==null && buttonClicked.getBackground()!=Color.GREEN){
        		ispieceSelected=false;
        		pieceSelected=null;
        		return;
        	}
        	if(buttonClicked.getBackground()==Color.GREEN && pieceSelected!=null ) {
        		pieceSelected.getPlayer().guiMove(pieceSelected,buttonClicked);
        		pieceSelected.getPlayer().isOpponentUnderCheck();
        		pieceSelected.getPlayer().inmove=false;
        		if(pieceSelected.getPlayer().opponent.IsAI){
        			pieceSelected.getPlayer().opponent.takeAMove();
        			buttonClicked.piece.getPlayer().chessBoard.clearAllGreenSquare();
        		}
        		else{
        			pieceSelected.getPlayer().opponent.inmove=true;
        		}
        		pieceSelected.getPlayer().chessBoard.Resetboard(pieceSelected.getPlayer().game);
        		return;
        	}
        	if(ispieceSelected && buttonClicked.piece!=null ) {
        		buttonClicked.piece.getPlayer().chessBoard.clearAllGreenSquare();
        		ispieceSelected=false;
        	}
        	if(!ispieceSelected && checkForCorrectPiece(buttonClicked.piece)) {
            //get the moves of particular button that was clicked
        		
            ArrayList<Board.Square> colorSquares =  buttonClicked.legalMoves();
            ispieceSelected=true;
            pieceSelected= buttonClicked.piece;
            Game.GreenPossibleMoves(colorSquares);
        	}
          //  if(buttonClicked.piece.equals(obj))
        	
        }
   public  boolean checkForCorrectPiece(PieceSet.Piece piece) {
	   if(piece.getPlayer().inmove) {
		  return true;
	   }
	   
	   return false;
   }
    }
   
