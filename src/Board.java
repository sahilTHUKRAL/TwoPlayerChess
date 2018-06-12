import java.awt.Color;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
public class Board{
	int colourOfSquare;
// Internal Square class for Each Individual Squares 
	class Square  extends JButton{
		PieceSet.Piece piece;
		int x;
		int y;
		int colour;
		public Square(){}
		Square(int colour,int i ,int j){
			this.x=i;
			this.y=j;
			this.colour = colour;
		}
		public void printAllPossibleMoves(){
			ArrayList<Board.Square> legalMoves = legalMoves();
			for(Square s: legalMoves) {
				System.out.println(s.x + ","+s.y);			
				}
		}
		//All Possible Moves without checking for the check 
		public ArrayList<Board.Square> legalMovesWithOutCheck(){
			PieceSet.Piece CurrentPiece = this.piece;
			ArrayList<Board.Square> AllMoves=new ArrayList<Board.Square>();
			ArrayList<Board.Square> legalMoves=new ArrayList<Board.Square>();
			AllMoves=CurrentPiece.getMovesWithOutCheck(board);
			for(Board.Square move : AllMoves){
				if(move.piece==null || move.piece.getColour()!=this.piece.getColour()) {
					legalMoves.add(move);
				}
			}
			return legalMoves;
		}
		//All possible Square one can move from this square
		public ArrayList<Board.Square> legalMoves(){
			PieceSet.Piece CurrentPiece = this.piece;
			ArrayList<Board.Square> AllMoves=new ArrayList<Board.Square>();
			ArrayList<Board.Square> legalMoves=new ArrayList<Board.Square>();
			if(CurrentPiece!=null)AllMoves=CurrentPiece.getMoves(board);
			for(Board.Square move : AllMoves){
				if(move.piece==null || move.piece.getColour()!=this.piece.getColour()) {
					legalMoves.add(move);
				}
			}
			return legalMoves;
			
		}

		Square(PieceSet.Piece piece,int colour,int x,int y){
			this.x=x;
			this.y=y;
			this.piece=piece;
			this.colour=colour;
		}
		public void killPiece(Player p){
		this.piece=null;
		}
	}
	Square[][] board; 
	final int ststlow_x=0;
	final int high_x=7;
	final int low_x=0;
	final int low_y=0;
	final int high_y=7;
	PieceSet pieceSetWhite; 
	PieceSet pieceSetBlack;
	int turn;
	Board(Player a,Player b){
		board = new Square[8][8];
		this.pieceSetWhite = new PieceSet(0, this, a);
		a.assignPieceSet(pieceSetWhite);
		a.assignBoard(this);
		this.pieceSetBlack = new PieceSet(1, this,b);
		b.assignPieceSet(pieceSetBlack);
		b.assignBoard(this);
		a.setOpponent(b);
		b.setOpponent(a);
//setting the White pieceSet Initial Values
		board[0][0] = new Square(pieceSetWhite.rook1,1,0,0);
		board[0][1] = new Square(pieceSetWhite.knight1,0,0,1);
		board[0][2] = new Square(pieceSetWhite.bishop1,1,0,2);
		board[0][3] = new Square(pieceSetWhite.king,0,0,3);
		board[0][4] = new Square(pieceSetWhite.queen,1,0,4);
		board[0][5] = new Square(pieceSetWhite.bishop2,0,0,5);
		board[0][6] = new Square(pieceSetWhite.knight2,1,0,6);
		board[0][7] = new Square(pieceSetWhite.rook2,0,0,7);
		board[1][0] = new Square(pieceSetWhite.pawn1,0,1,0);
		board[1][1] = new Square(pieceSetWhite.pawn2,1,1,1);
		board[1][2] = new Square(pieceSetWhite.pawn3,0,1,2);
		board[1][3] = new Square(pieceSetWhite.pawn4,1,1,3);
		board[1][4] = new Square(pieceSetWhite.pawn5,0,1,4);
		board[1][5] = new Square(pieceSetWhite.pawn6,1,1,5);
		board[1][6] = new Square(pieceSetWhite.pawn7,0,1,6);
		board[1][7] = new Square(pieceSetWhite.pawn8,1,1,7);
//setting the black pieceSet Initial Values		
		board[7][0] = new Square(pieceSetBlack.rook1,0,7,0);
		board[7][1] = new Square(pieceSetBlack.knight1,1,7,1);
		board[7][2] = new Square(pieceSetBlack.bishop1,0,7,2);
		board[7][3] = new Square(pieceSetBlack.king,1,7,3);
		board[7][4] = new Square(pieceSetBlack.queen,0,7,4);
		board[7][5] = new Square(pieceSetBlack.bishop2,1,7,5);
		board[7][6] = new Square(pieceSetBlack.knight2,0,7,6);
		board[7][7] = new Square(pieceSetBlack.rook2,1,7,7);
		board[6][0] = new Square(pieceSetBlack.pawn1,1,6,0);
		board[6][1] = new Square(pieceSetBlack.pawn2,0,6,1);
		board[6][2] = new Square(pieceSetBlack.pawn3,1,6,2);
		board[6][3] = new Square(pieceSetBlack.pawn4,0,6,3);
		board[6][4] = new Square(pieceSetBlack.pawn5,1,6,4);
		board[6][5] = new Square(pieceSetBlack.pawn6,0,6,5);
		board[6][6] = new Square(pieceSetBlack.pawn7,1,6,6);
		board[6][7] = new Square(pieceSetBlack.pawn8,0,6,7);
//Setting Up the Rest of the board with empty Squares	
		int k=1;
	for(int i=low_x+2;i<high_x-1;i++){
			for(int j =low_y; j<=high_y;j++){
				if((i+j)%2==0){k=1;}
				else{k=0;}
				board[i][j] = new Square(k,i,j);
				}
			}
	intevertColour();
	turnTheBoard();
	/*board[4][4] = new Square(pieceSetBlack.queen,0,4,4);
	board[4][4].piece.x=4;
	board[4][4].piece.y=4;
	*/
	}

	public void print(){
		System.out.print(" ");
		for(int k=0;k<8;k++) {
			System.out.print("\t"+k);
		}
		System.out.println("\t");
	for(int i=low_x;i<=high_x;i++){
		System.out.print(i);
		for(int j=low_y;j<=high_y;j++){
			
			if(board[i][j].piece == null) {
				//System.out.print("("+board[i][j].x+","+board[i][j].y+") "+board[i][j].colour+"<"+"-1"+","+"-1"+">"+"pColor:"+-1+"\t");
				if(board[i][j].colour==0)System.out.print("\t\u2610");
				else {System.out.print("\t\u25a0");}
			}
			else {
//				System.out.print("("+board[i][j].x+","+board[i][j].y+") "+board[i][j].colour+" "+board[i][j].piece.name+"\t");
			//	System.out.print("("+board[i][j].x+","+board[i][j].y+") "+board[i][j].colour+"<"+board[i][j].piece.x+","+board[i][j].piece.y+">"+"pColor:"+board[i][j].piece.getColour()+"\t");
				System.out.print("\t"+board[i][j].piece.icon+"  ");
			}
		}
		System.out.println();
		}
	}
	private void swap(Square s1,Square s2) {
			Square temp = new Square(s1.piece,s1.colour,s1.x,s1.y);
			s1.colour=s2.colour;
			s2.colour= temp.colour;
			
			s1.piece=s2.piece;
			if(s1.piece!=null) {
				s1.piece.x=s1.x;
				s1.piece.y=s1.y;
			}
			s2.piece=temp.piece;
			if(s2.piece!=null) {
				s2.piece.x=s2.x;
				s2.piece.y=s2.y;
			}
			
}
	public void exchangePiece(Square s1,Square s2) {
		Square temp = new Square(s1.piece,s1.colour,s1.x,s1.y);		
		s1.piece=s2.piece;
		if(s1.piece!=null) {
			s1.piece.x=s1.x;
			s1.piece.y=s1.y;
		}
		s2.piece=temp.piece;
		if(s2.piece!=null) {
			s2.piece.x=s2.x;
			s2.piece.y=s2.y;
		}
		
}
	//change color of every Board
	public void intevertColour() {
		for(int i=low_x;i<=high_x;i++) {
			for(int j=low_y;j<=high_y;j++) {
				board[i][j].colour=((board[i][j].colour==0)?1:0);
			}
			}
		
	}
	public void turnTheBoard(){
		int k=0;
		for(int i=low_x;i<=high_x;i++) {
			if(k<4) {
			k++;
			for(int j=low_y;j<=high_y;j++) {
				swap(board[i][j],board[7-i][7-j]);
			}
			}
		}
	}
	public void movePiece(Square s1,Square s2) {
		s2.piece=s1.piece;
		if(s2.piece!=null) {
			s2.piece.x=s2.x;
			s2.piece.y=s2.y;
		}		
		//// error in removing the pAlive
		//s1.piece.getPlayer().piceseAlive.remove(s1.piece);
		s1.piece=null;
}
	public boolean tempmove(Square s1,Square s2, PieceSet.Piece piece) {
		//exchangePiece(s1, s2);
		Square temp1 = new Square(s1.piece,s1.colour,s1.x,s1.y);
		if(temp1.piece!=null) {
			temp1.piece.x=s1.piece.x;
			temp1.piece.y=s1.piece.y;
		}
		Square temp2 = new Square(s2.piece,s2.colour,s2.x,s2.y);
		if(temp2.piece!=null) {
			temp2.piece.x=s2.piece.x;
			temp2.piece.y=s2.piece.y;
		}
		movePiece(s1,s2);
		//printing the board after the temp move
		Square OurKingPosition= board[piece.x][piece.y];
		//printing kings Position 
		//System.out.println(piece.x+"and"+piece.y);
		boolean willUnderCheck = CheckForCurrentCheck(OurKingPosition,piece.getPlayer());
		movePiece(s2,s1);
		movePiece(temp2,s2);
		return willUnderCheck;
	}
	public boolean CheckForCurrentCheck(Board.Square target,Player player) {
		ArrayList<PieceSet.Piece> pAlive = player.opponent.piceseAlive;                     //list of opponent Player alive
		ArrayList<PieceSet.Piece> ThreatningPieces = new ArrayList<PieceSet.Piece>();        //list of Pieces which are threatning to us on this move
		ArrayList<Square> possibleAttackSquares = new ArrayList<Square>();                   //list of moves opponent is having after our moves
		boolean check=false;                                                                 // boolean for check condition
		// For Each opponent Player piece tell me who are they / where are they /where they can attack //
		for(PieceSet.Piece opPiece :pAlive) {                                               
			// Who are they and where are they 
			//System.out.println("opponent Player positioned at"+opPiece.name+"/"+opPiece.x+","+opPiece.y);
			for(Board.Square s:board[opPiece.x][opPiece.y].legalMovesWithOutCheck()) {
				//Where they can attack 
				if(s==target) {
					check=true;
					}
				//System.out.println(s.x+"&"+s.y);
				}
				possibleAttackSquares.addAll(board[opPiece.x][opPiece.y].legalMovesWithOutCheck());
			}
		
		//System.out.println(possibleAttackSquares.size());
		//Printing of moves opponent is having after our moves
		for(Square s: possibleAttackSquares) {
			//System.out.println("oppponent can move to :"+s.x+","+s.y);
		}
		// print all of the threatning pieces
		for(PieceSet.Piece p:ThreatningPieces) {
			System.out.println(p.name);
		}
			return check;
}

	public  PieceSet.Piece getPawnConverted(PieceSet.Piece pawn,String pieceCode) {
		PieceSet.Piece cPiece=null;
		PieceSet pPieceSet = pawn.getPlayer().pieceSet;
		switch(pieceCode){
		case "1":
				cPiece = pPieceSet.new Rook(pawn.x,pawn.y);
			break;
		case "2":
				cPiece = pPieceSet.new Bishop(pawn.x,pawn.y);
			break;
		case "3":
				cPiece = pPieceSet.new Knight(pawn.x,pawn.y);
			break;
		default:
				cPiece = pPieceSet.new Queen(pawn.x,pawn.y);
			break;
		}
		board[pawn.x][pawn.y].piece= cPiece;
		return cPiece;
	}

	public void Resetboard(Game game) {
		 {
		        for(int i = 0; i <8; i++){
		        for(int j = 0; j <8; j++)
		   	        {
		        	Square s = board[i][j];  
		        	if(s.colour==0)
		            	board[i][j].setBackground(Color.WHITE);
		            if(s.colour==1)
		            	board[i][j].setBackground(Color.GRAY);
		            if(s.piece==null) {
		            	board[i][j].setIcon(null);	  	
		            }
		            if(s.piece!=null) {
		            	board[i][j].setIcon(new ImageIcon(s.piece.pathToImage));	
		            }
		            board[i][j].addActionListener(new buttonListner());
		            game.add(board[i][j]); //adds this button to JPanel (note: no need for JPanel.add(...)
		                                //because this whole class is a JPanel already           
		        }
		        }
		    }
		
	}

	public void clearAllGreenSquare() {
		for(Square[] s : board) {
			for(Square s1 :s) {
				if(s1.getBackground()==Color.GREEN) {
					if(s1.colour==1)
						s1.setBackground(Color.GRAY);
					else
						s1.setBackground(Color.white);
				}
			}
		}
	}
}
