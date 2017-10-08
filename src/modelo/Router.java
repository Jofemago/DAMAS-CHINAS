/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @author Felipe
 */
public class Router implements Iterable<MoveList> {

    private MoveList inicio;
    private MoveList fin;
    private MoveList step = null;
    private Piece pieza;

    private boolean inBoard;
    private boolean inDiagonal;
    private boolean isPosible = false; // valida que el color de la ficha y la direccion coincida

    public Router(MoveList inicio, MoveList fin, Piece pieza) {

        this.fin = fin;
        this.inicio = inicio;
        this.pieza = pieza;
        this.inDiagonal = MoveList.isDiagonal(this.fin, this.inicio);
        this.inBoard = MoveList.OnBoard(inicio) && MoveList.OnBoard(fin);
    }

    public void calc() {

        if (!pieza.isQueen()) {

            //la pieza es blanca
            if (pieza.isWhite()) {
                if (inicio.isDownRigth(fin)) {
                    step = pieza.getPieceMove()[0];
                    isPosible = true;
                } else if (inicio.isDownLeft(fin)) {
                    step = pieza.getPieceMove()[1];
                    isPosible = true;
                }

                // la pieza es negra   
            } else {
                assert (!pieza.isWhite());
                if (inicio.isUpLeft(fin)) {
                    step = pieza.getPieceMove()[1];
                    isPosible = true;
                } else if (inicio.isUpRigth(fin)) {
                    step = pieza.getPieceMove()[0];
                    isPosible = true;
                }

            }

        } else {
            assert (pieza.isQueen());
            if (inicio.isDownRigth(fin)) {
                step = pieza.getPieceMove()[2];
                isPosible = true;
            } else if (inicio.isDownLeft(fin)) {
                step = pieza.getPieceMove()[3];
                isPosible = true;
            } else if (inicio.isUpLeft(fin)) {
                step = pieza.getPieceMove()[1];
                isPosible = true;
            } else if (inicio.isUpRigth(fin)) {
                step = pieza.getPieceMove()[0];
                isPosible = true;
            }
        }
    }

    public boolean IsRoute() {
        return this.inBoard && this.inDiagonal && this.isPosible;
    }

    public Iterator<MoveList> iterator() {
        return new iterRoute(step);
    }    //metodo para escoger el paso 
    // validar que este en la misma diagonal

    private class iterRoute implements Iterator<MoveList> {

        private final MoveList Paso;
        private MoveList start = new MoveList(inicio);
        private MoveList end;

        public iterRoute(MoveList Paso) {

            this.Paso = Paso;
            this.end = fin;//MoveList.add(Paso, fin);
            // this.start = MoveList.add(Paso, start);
            //this.start = MoveList.add(Paso, fin);
        }

        @Override
        public boolean hasNext() {

            return !MoveList.isEqual(start, end);
        }

        @Override
        public MoveList next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            this.start = MoveList.add(Paso, start);
            return start;

        }

    }

}
