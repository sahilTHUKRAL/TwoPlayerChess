import java.util.ArrayList;
import java.util.Scanner;

public class Player{
		PieceSet pieceSet;
		boolean underCheck=false;
		Board chessBoard;
		boolean moveLeft=true;
		Scanner scanner;
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
				System.out.println("You Are Under Check! Save Your King");
			}			
			PieceSet.Piece currPiece = piceseAlive.get(i-1);
			ArrayList<Board.Square> moves = chessBoard.board[currPiece.x][currPiece.y].legalMoves();
			Board.Square squareToMove = moves.get(j-1);
			opponent.piceseAlive.remove(squareToMove.piece);
			chessBoard.movePiece(chessBoard.board[currPiece.x][currPiece.y], squareToMove);
		
		}
		public void move() {
			if(underCheck) {
				System.out.println("You Are Under Check! Save Your King");
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
		underCheck=false;
		checkGivenCheck();
	}
	
}