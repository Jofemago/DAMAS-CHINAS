/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamecheckers;

import java.util.ArrayList;
import java.util.List;
import modelo.Board;
import modelo.MoveList;
import modelo.Piece;

/**
 *
 * @author Felipe
 */
public class Jugador {
    private Board tablero ;
    private boolean color;
    private boolean canMove,perdio = false;
    private int numFichas = 0;
    
    public Jugador (Board board, boolean color, boolean canMove){
        this.tablero = board;
        this.color = color;
        this.canMove = canMove;
    }
    
    public boolean PieceIsBlock(MoveList inicio, MoveList fin, boolean color, int k){
        if(k == 0){
            return false;
        }else{
            MoveList m = MoveList.add(inicio, fin);
            if (MoveList.OnBoard(m)){
                if (tablero.board[m.getX()][ m.getY()].isOccupied()){
                    if (tablero.board[m.getX()][m.getY()].getPiece().isWhite()!=color){
                        return PieceIsBlock(m, fin, color, k-1);
                    }else{return false;}
                }else{return true;}
            }else{return false;}
        }
    }
    
    /**
     *
     */
    public void VeficarBloqueos(){
        this.numFichas = 0;
        List<Boolean> verificar;
        verificar = new ArrayList<>();
        for (int i=0; i<8 ; i++){
            for (int j=0; j<8; j++){
                if(tablero.board[i][j].isOccupied()){
                    if(tablero.board[i][j].getPiece().isWhite()==color){
                        if(!tablero.board[i][j].getPiece().isQueen()){
                            Piece piece = tablero.board[i][j].getPiece();
                            MoveList[] m = piece.getPieceMove(); 
                            boolean a = PieceIsBlock(piece.getPos() ,m[0], piece.isWhite(),2);
                            boolean b = PieceIsBlock(piece.getPos(),m[1], piece.isWhite(),2);
                            verificar.add(new Boolean(a || b));
                            this.numFichas += 1;
                        }else{
                            Piece piece = tablero.board[i][j].getPiece();
                            MoveList[] m = piece.getPieceMove(); 
                            boolean a = PieceIsBlock(piece.getPos(),m[0], piece.isWhite(),20);
                            boolean b = PieceIsBlock(piece.getPos(),m[1], piece.isWhite(),20);
                            boolean c = PieceIsBlock(piece.getPos(),m[2], piece.isWhite(),20);
                            boolean d = PieceIsBlock(piece.getPos(),m[3], piece.isWhite(),20);
                            verificar.add(new Boolean(a || b || c || d));
                            this.numFichas += 1;
                        }
                    }
                }
            }
        }
    boolean j = false;
    for(Boolean a: verificar){
        j = j || a.booleanValue();
    }

     this.canMove = j;  
    // this.perdio = !j;
     if(canMove)
           DiferenciaFichas();
     else{
         String ColorMov = this.color ? "blanco": "negro";
         System.out.println("Bloqueado Jugador "+ ColorMov);
     }
    }
    
    public void DiferenciaFichas(){
        
  
        if(this.numFichas == 0){
            canMove = false;
            String ColorMov;
            ColorMov = this.color ? "blanco": "negro";
            System.out.println("Sin fichas jugador"+ ColorMov);
        }
        
        
        if( canMove)
            numeroReinas();
    }
    
    public void numeroReinas(){
        int numReinas = 0;
        for(int i = 0; i<8; i++){
            for (int j = 0; j<8 ; j++){
                if(tablero.board[i][j].isOccupied()){
                    if(tablero.board[i][j].getPiece().isWhite()==color){
                        if(tablero.board[i][j].getPiece().isQueen()){
                        numReinas += 1;
                        }
                    }
                }
            }
        }
        
        if (numReinas >= 3){
            String ColorMov = this.color ? "blanco": "negro";
            System.out.println("Gano, Corono3 reinas jugador "+ ColorMov);
            System.out.println("" + "GanooPorReinas");
            //this.perdio = false;
            this.canMove = false;
        }
    }
    
    public boolean VerificarPlayer(){
        this.VeficarBloqueos();
        
        
        return this.canMove ;
    }
    
    public boolean Iloose(){
        
        return this.perdio;
    }
    
}
    
    
