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
public class Checker extends Piece {

    public Checker(boolean color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public MoveList[] getPieceMove() {
        MoveList[] m = null;
        if (this.hasMoved) {

            m = new MoveList[4];
            m[0] = new MoveList(1, 1);// ariba derecha
            m[1] = new MoveList(1, -1);// arriba izquierda  
            m[2] = new MoveList(-1, 1);//abajo a la derecha
            m[3] = new MoveList(-1, -1);//abajo a ala izquierda

            return m;
        } else {

            m = new MoveList[2];
            if (!this.color) {//negra

                m[0] = new MoveList(1, 1);// ariba derecha
                m[1] = new MoveList(1, -1);// arriba izquierda
//arriba es abajo
            } else {

                m[0] = new MoveList(-1, 1);//abajo a la derecha
                m[1] = new MoveList(-1, -1);//abajo a ala izquierda
//abajo es arriba
            }
        }

        return m;
    }

    @Override
    protected String getName() {
        return "checker";
    }

    /*
     *Imprime un O si la ficha es blancha,
     *Impime una X si la ficha es negra
     *
     * @return
     */
    @Override
    public String tostring() {

        String m;
        if (this.color) {
            m = " o ";
        } else {
            m = " x ";
        }
        return m;
    }

}
