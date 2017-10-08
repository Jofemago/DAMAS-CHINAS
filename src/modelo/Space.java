/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Felipe
 */
public class Space extends Rectangle {

    private int x;
    private int y;
    private Piece piece;
    private final boolean color;

    public Space(boolean color, int x, int y) {

        this.x = x;
        this.y = y;
        this.piece = null;
        this.color = color;
        setWidth(75);
        setHeight(75);
        super.relocate(y * 75, 75 * x);// x es vertical 
        super.setFill(color ? Color.DARKGRAY : Color.DARKBLUE);

    }
    
    /**
     *
     * @return
     */
    public Space clonar(){
        Space j = new Space(this.color, this.x, this. y);
        
        if (this.isOccupied() ){
            if(piece.isQueen()){
            MoveList pos = this.piece.getPos();
             Checker k = new Checker(this.piece.isWhite(), pos.getX(), pos.getY()) ;
             k.MakeQueen();
             j.setPiece(k);
             
            }else{
                MoveList pos = this.piece.getPos();
                Piece k = new Checker(this.piece.isWhite(), pos.getX(), pos.getY()) ;
                j.setPiece(k);
            }
        }
        
        return j;
    
    }

    /**
     * retorna un . si la casilla es blanca , retorna un * si la casilla es
     * negra
     *
     * @return
     */
    @Override
    public String toString() {
        boolean iswhite = this.color;
        if (iswhite) {
            return " . ";
        } else {
            return " * ";
        }
    }

    //retorna true si el espacio esta ocupado
    public boolean isOccupied() {

        return piece != null;
    }

    //remueve piece del espacio
    public Piece releasePiece() {

        Piece tmpPiece = piece;
        setPiece(null);
        return tmpPiece;
    }

    public Piece getPiece() {

        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public String getPieceColor() {

        if (getPiece() != null) {
            return getPiece().getColor();
        }
        return "";
    }

    public boolean PieceIsWhite() {

        return piece.isWhite();
    }

    public boolean getColorSpace() {
        return this.color;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getx() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int gety() {
        return this.y;
    }

}
