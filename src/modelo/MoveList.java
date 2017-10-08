/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author Felipe
 */
public class MoveList {

    private int x;
    private int y;

    public MoveList(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public MoveList(MoveList m) {

        this.x = m.getX();
        this.y = m.getY();
    }

    public int getX() {
        return x;
    }

    private void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    private void setY(int y) {
        this.y = y;
    }

    public boolean isEqual(MoveList m) {
        return this.x == m.getX() && this.y == m.getY();
    }

    public boolean isEqual(int x, int y) {
        return this.x == x && this.y == y;
    }

    public static boolean isEqual(int x1, int y1, int x2, int y2) {
        return x1 == x2 && y2 == y1;
    }

    /**
     * Valida si la posicion indicada esta dentro de los limites de una Board y
     * casillas negras unicamente
     *
     * @param m
     * @return
     */
    public static boolean OnBoard(MoveList m) {
        boolean Xfine = m.x <= 7 && m.x >= 0;
        boolean Yfine = m.y <= 7 && m.y >= 0;
        boolean IsWhiteSpace = (m.x + m.y) % 2 == 0;

        return Xfine && Yfine && !(IsWhiteSpace);
    }

    public static boolean OnBoard(int x, int y) {
        boolean Xfine = x <= 7 && x >= 0;
        boolean Yfine = y <= 7 && y >= 0;
        boolean IsWhiteSpace = (x + y) % 2 == 0;

        return Xfine && Yfine && !(IsWhiteSpace);
    }

    /**
     * Las siguiente funciones validan la direccion del movimiento, osea donde
     * quiero movier una ficha siendo this la posicion inicial
     *
     *
     * @param other
     * @return
     */
    /*  public boolean isUpLeft(MoveList other){
        
        return (this.getX() > other.getX()) && (this.getY() < other.getY());
   }

    public boolean isUpRigth(MoveList other){
        
        return (this.getX() < other.getX()) && (this.getY() < other.getY());
   }    

    public boolean isDownLeft(MoveList other){
        
        return (this.getX() > other.getX()) && (this.getY() > other.getY());
   }

    public boolean isDownRigth(MoveList other){
        
        return (this.getX() < other.getX()) && (this.getY() > other.getY());
   }  */
    public boolean isUpLeft(MoveList other) {

        return (this.getX() < other.getX()) && (this.getY() > other.getY());
    }

    public boolean isUpRigth(MoveList other) {

        return (this.getX() < other.getX()) && (this.getY() < other.getY());
    }

    public boolean isDownLeft(MoveList other) {

        return (this.getX() > other.getX()) && (this.getY() > other.getY());
    }

    public boolean isDownRigth(MoveList other) {

        return (this.getX() > other.getX()) && (this.getY() < other.getY());
    }

    /**
     * suma dos lista de movimiento
     *
     * @param a
     * @param b
     * @return
     */
    public static MoveList add(MoveList a, MoveList b) {

        int aa = a.getX() + b.getX();
        int bb = a.getY() + b.getY();
        return new MoveList(aa, bb);

    }

    public static boolean isEqual(MoveList a, MoveList b) {

        return (a.getX() == b.getX()) && (a.getY() == b.getY());

    }

    public static boolean isDiagonal(MoveList a, MoveList b) {

        int diff = b.getY() - a.getY();
        return a.getX() + diff == b.getX() || a.getX() - diff == b.getX();
    }
}
