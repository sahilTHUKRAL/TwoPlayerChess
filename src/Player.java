import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JOptionPane;



public class Player{
		PieceSet pieceSet;
		Game game;
		boolean IsAI=false;
		boolean underCheck=false;
		Board chessBoard;
		boolean moveLeft=true;
		Scanner scanner;
		boolean inmove=false;
		Player opponent;
		 ArrayList<PieceSet.Piece> piceseAlive;
		public void assignPieceSet(PieceSet pieceSet) {
			this.pieceSet= pieceSet;
		}
		public void assignBoard(Board board) {
			this.chessBoard= board;
		}
		public void setOpponent(Player opponent) {
			this.opponent = opponent;
		}
		Player(){
		this.scanner= new Scanner(System.in);	
			piceseAlive = new ArrayList<PieceSet.Piece>();
		}
		public void addToPiceseAlive(PieceSet.Piece piece) {
			piceseAlive.add(piece);
		}
		public void printPieceAlive() {
			ArrayList<PieceSet.Piece> pAlive = piceseAlive;
			System.out.println("number of Pieces alive is :"+pAlive.size());
			for(PieceSet.Piece p : pAlive) {
				System.out.println(p.name+ "at"+p.x+" "+p.y+":");
			}
			
		}
		public ArrayList<Board.Square> getAllPossibleMoves(){
			ArrayList<Board.Square> possibleMoves =  new ArrayList<Board.Square>();
			for(PieceSet.Piece p : piceseAlive) {
				//chessBoard.board[p.x][p.y].printAllPossibleMoves();
				possibleMoves.addAll(chessBoard.board[p.x][p.y].legalMoves());
			}
			
			return possibleMoves;
		}
		public void printAllPossibleMoves(){
			ArrayList<Board.Square> possibleMoves= getAllPossibleMoves();
			for(Board.Square s:possibleMoves) {
				System.out.println(s.x+","+s.y);
			}
		}
		public boolean checkForMoveLeft() {
			if(getAllPossibleMoves().isEmpty()) {
				moveLeft=false;
				return false;
			}
			return true;
		}
		public void checkGivenCheck(){
				Board.Square OpponentKingPosition = chessBoard.board[this.opponent.pieceSet.king.x][this.opponent.pieceSet.king.y];
				opponent.underCheck=chessBoard.CheckForCurrentCheck(OpponentKingPosition,this.opponent);
		}
		public void move(int i,int j) {
			if(underCheck) {
				JOptionPane.showMessageDialog(null, "You Are Under Check! Save Your King");
				
			}			
			PieceSet.Piece currPiece = piceseAlive.get(i-1);
			ArrayList<Board.Square> moves = chessBoard.board[currPiece.x][currPiece.y].legalMoves();
			Board.Square squareToMove = moves.get(j-1);
			opponent.piceseAlive.remove(squareToMove.piece);
			chessBoard.movePiece(chessBoard.board[currPiece.x][currPiece.y], squareToMove);
		
		}
		public boolean isUnderCheck() {
			return this.underCheck;
		}
		public void checkPrompter() {
			if(this.underCheck==true) {
				JOptionPane.showMessageDialog(null, "You Are Under Check! Save Your King");
			}
		}
		public void move() {
			if(underCheck) {
				JOptionPane.showMessageDialog(null, "You Are Under Check! Save Your King");
			}
			int i=1;
			System.out.println("Players Alive please Choose the Number to move the player:");
			for(PieceSet.Piece p : piceseAlive) {
				System.out.print(i+": ");
				System.out.println(p.name+" at "+p.x+","+p.y);
				i++;
			}
			moveHelperSelectPiece();
		}
	public void moveHelperSelectPiece() {
		int i = scanner.nextInt();
		while(i>piceseAlive.size()) {
			 System.out.println("Enter Value in limits");
			 i=scanner.nextInt();
		 }
		PieceSet.Piece currPiece = piceseAlive.get(i-1);
		ArrayList<Board.Square> moves = chessBoard.board[currPiece.x][currPiece.y].legalMoves();
		if(moves.isEmpty()) {
			System.out.println(
					"The piece you selected has no moves please select another one");
			moveHelperSelectPiece();
			return;
		}
		moveHelperSelectMove(currPiece,moves);
	}
	public void moveHelperSelectMove(PieceSet.Piece currPiece,ArrayList<Board.Square> moves){
		System.out.println("Moves Avalible for "+currPiece.name+" , Please Select the move:");
		int i =1;
		for(Board.Square s:moves) {
			System.out.println(i+" "+s.x+" "+s.y);
			i++;
		}
		if(currPiece.name.equals("Pawn")) {currPiece.setFirstMove(false);}
		 i=scanner.nextInt();
		 while(i>moves.size()) {
			 System.out.println("Enter Value in limits");
			 i=scanner.nextInt();
		 }
		Board.Square squareToMove = moves.get(i-1);
		opponent.piceseAlive.remove(squareToMove.piece);
		chessBoard.movePiece(chessBoard.board[currPiece.x][currPiece.y], squareToMove);
		boolean pawnConverted= false;
		if(currPiece.name.equals("Pawn") && (currPiece.x==0 || currPiece.x==7)){
			System.out.println("Choose The Piece to be replaced by Pawn:\n 1: Rook \n 2: Bishop \n 3: Knight \n 4: Queen");
			String pieceCode= scanner.nextLine();
			PieceSet.Piece cPiece = chessBoard.getPawnConverted(currPiece,pieceCode);
			pawnConverted=true;
			piceseAlive.remove(currPiece);
			piceseAlive.add(cPiece);
			
		}
		underCheck=false;
		pawnConverted=false;
		checkGivenCheck();
	}
	public void isOpponentUnderCheck() {
		String ColorOfOpponent;
		if(this.pieceSet.colour==0) {
			ColorOfOpponent="Black";
		}
		else {
			ColorOfOpponent="White";
		}
		if(opponent.underCheck) {
			JOptionPane.showMessageDialog(null,""+ ColorOfOpponent+ " is Under Check! Save Your King");
		}
	}
	public void guiMove(PieceSet.Piece currPiece, Board.Square squareToMove) {
		
		if(currPiece.name.equals("Pawn") && currPiece.getFirstMove()) {
			currPiece.setFirstMove(false);
		}
		if(squareToMove.piece!=null)
			opponent.piceseAlive.remove(squareToMove.piece);
		chessBoard.movePiece(chessBoard.board[currPiece.x][currPiece.y], squareToMove);
		
		boolean pawnConverted= false;
		if(currPiece.name.equals("Pawn") && (currPiece.x==0 || currPiece.x==7)){
			//System.out.println("Choose The Piece to be replaced by Pawn:\n 1: Rook \n 2: Bishop \n 3: Knight \n 4: Queen");
			
			String[] values = {"1", "2", "3", "4"};
			String pieceCode ="4";
			Object selected = JOptionPane.showInputDialog(null, "Choose The Piece to be replaced by Pawn:\n 1: Rook \n 2: Bishop \n 3: Knight \n 4: Queen", "Selection", JOptionPane.DEFAULT_OPTION, null, values, "4");
			if ( selected != null ){//null if the user cancels. 
			    pieceCode = selected.toString();
			    //do something
			}else{
			    System.out.println("User cancelled");
			}
		//	int pieceCode= scanner.nextInt();
			PieceSet.Piece cPiece = chessBoard.getPawnConverted(currPiece,pieceCode);
			pawnConverted=true;
			piceseAlive.remove(currPiece);
			piceseAlive.add(cPiece);
			System.out.println("Reached Here");
		}
		underCheck=false;
		pawnConverted=false;
		checkGivenCheck();
		if(!opponent.checkForMoveLeft() && !opponent.underCheck )
		   {
				JOptionPane.showMessageDialog(null, "Match Draw by Stale Mate");
				System.exit(1);
		   }
		if(!opponent.checkForMoveLeft() && opponent.underCheck )
		   {
				JOptionPane.showMessageDialog(null, "Check Mate");
				System.exit(2);
		   }
		
	}
	public void takeAMove() {
		int isMovesThere = 0;
		ArrayList<Board.Square> allPossibleMovesForThisPiece= null;
		PieceSet.Piece randPieceSelectet=null ;
		while(isMovesThere<1) {
		randPieceSelectet = piceseAlive.get(new Random().nextInt(piceseAlive.size())); 
		allPossibleMovesForThisPiece= chessBoard.board[randPieceSelectet.x][randPieceSelectet.y].legalMoves();
		isMovesThere=allPossibleMovesForThisPiece.size();
		}
		Board.Square squareToMove= allPossibleMovesForThisPiece.get(new Random().nextInt(allPossibleMovesForThisPiece.size()));

		guiMove(randPieceSelectet, squareToMove);
		this.inmove=false;
		this.opponent.inmove=true;
		isOpponentUnderCheck();
	}
}