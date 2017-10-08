/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MiniMax;

import static java.lang.Math.max;
import static java.lang.Math.min;
import java.util.ArrayList;
import java.util.List;
import modelo.MoveList;
import modelo.Piece;
import modelo.Router;
import modelo.Space;

/**
 *
 * @author Felipe&Felipe
 */
public class ProcesadorM {

    private int profundidad;
    private boolean player;
    private int valor = 0;
    public boolean hasValor = false; // valor que tiene de peso el ultimo hijo(hoja)

    public Space[][] board = new Space[8][8];
    private List<ProcesadorM> hijos = new ArrayList<>();

    public ProcesadorM(Space[][] m, int profundidad, boolean player) {
        this.player = player;
        this.profundidad = profundidad;
        this.clonacion(m);

        BuscarHijos();// siempre busca sus hijos cuando se crea, y solo crea si no es 0 la profundidad

    }

    private void clonacion(Space[][] m) {
        int a = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                //System.out.println("hola" + a++);
                this.board[i][j] = m[i][j].clonar();
            }
        }
    }

    public void PieceIsBlock(MoveList inicio, MoveList fin, boolean color, int k, List<MoveList> PosMovs) {
        // validar a que posiciones se puede mover una pieza, si no se puede mover no agregara nada a posMovs
        if (k == 0) {

        } else {
            MoveList m = MoveList.add(inicio, fin);
            if (MoveList.OnBoard(m)) {
                if (board[m.getX()][m.getY()].isOccupied()) {
                    if (board[m.getX()][m.getY()].getPiece().isWhite() != color) {
                        PieceIsBlock(m, fin, color, k - 1, PosMovs);
                    }
                } else {
                    PosMovs.add(m);
                }
            }
        }
    }

    public void BuscarHijos() {//lenar la lista con todos los hijos de nodos 
// busca las fichas a las que le peudo realizar operacion y obtener hijos
        if (profundidad > 0) {

            List<Boolean> verificar;
            verificar = new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (board[i][j].isOccupied()) {
                        if (board[i][j].getPiece().isWhite() == player) {
                            if (!board[i][j].getPiece().isQueen()) {

                                Piece piece = board[i][j].getPiece();
                                List<MoveList> Moves = new ArrayList();
                                MoveList[] m = piece.getPieceMove();

                                PieceIsBlock(piece.getPos(), m[0], player, 2, Moves);
                                PieceIsBlock(piece.getPos(), m[1], player, 2, Moves);
                                this.HacerHijos(Moves, piece.getPos());

                            } else {
                                Piece piece = board[i][j].getPiece();
                                List<MoveList> Moves = new ArrayList();
                                MoveList[] m = piece.getPieceMove();

                                PieceIsBlock(piece.getPos(), m[0], player, 20, Moves);
                                PieceIsBlock(piece.getPos(), m[1], player, 20, Moves);
                                PieceIsBlock(piece.getPos(), m[2], player, 20, Moves);
                                PieceIsBlock(piece.getPos(), m[3], player, 20, Moves);
                                this.HacerHijos(Moves, piece.getPos());

                            }
                        }
                    }
                }
            }
        }

    }

    private void HacerHijos(List<MoveList> Moves, MoveList inicio) {
        // crea los hijos de estado de board
        for (MoveList m : Moves) {
            ProcesadorM hijo = createNode(inicio, m);
            this.hijos.add(hijo);
        }

    }

    private void DefUtilidad(MoveList inicio, boolean color) {// para las hojas obetener el peso especifico que les corrensponde en un 
        //determinado estado del minimax

        if (profundidad == 0) {
            hasValor = true;
            List<MoveList> Moves = new ArrayList();
            int i = inicio.getX();
            int j = inicio.getY();
            if (board[i][j].isOccupied()) {
                if (!board[i][j].getPiece().isQueen()) {

                    Piece piece = board[i][j].getPiece();

                    MoveList[] m = piece.getPieceMove();

                    PieceIsBlock(piece.getPos(), m[0], player, 2, Moves);
                    PieceIsBlock(piece.getPos(), m[1], player, 2, Moves);

                } else {
                    Piece piece = board[i][j].getPiece();
                    //List<MoveList> Moves = new ArrayList();
                    MoveList[] m = piece.getPieceMove();

                    PieceIsBlock(piece.getPos(), m[0], player, 20, Moves);
                    PieceIsBlock(piece.getPos(), m[1], player, 20, Moves);
                    PieceIsBlock(piece.getPos(), m[2], player, 20, Moves);
                    PieceIsBlock(piece.getPos(), m[3], player, 20, Moves);

                }

                this.valor = Moves.size();
            }
        }

    }

    public ProcesadorM createNode(MoveList inicio, MoveList fnall) {
//crea un nodo con mov especifico
        ProcesadorM newNodo = new ProcesadorM(this.board, this.profundidad - 1, !this.player);
        boolean a = newNodo.MakeMove(inicio, fnall, player);
        newNodo.DefUtilidad(fnall, player);
        return newNodo;
    }

    public void MakeBasicMov() {

        //hace el rpimer movimiento valido que encuentra
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j].isOccupied()) {
                    if (board[i][j].getPiece().isWhite() == player) {
                        if (!board[i][j].getPiece().isQueen()) {
                            Piece piece = board[i][j].getPiece();
                            List<MoveList> Moves = new ArrayList();
                            MoveList[] m = piece.getPieceMove();
                            PieceIsBlock(piece.getPos(), m[0], player, 2, Moves);
                            PieceIsBlock(piece.getPos(), m[1], player, 2, Moves);
                            if (Moves.size() > 0) {
                                ProcesadorM newNodo;
                                newNodo = createNode(piece.getPos(), Moves.get(0));
                                this.board = newNodo.board;
                                return;
                            }
                        }

                    }
                }
            }
        }
    }

    public int MiniMax() {

        if (this.profundidad == 0) {// es una hoja 
            return this.valor; //me importa saber su utilidad
        } else if (!this.player) {// voy a maximixar
            this.hasValor = true;

            for (ProcesadorM hijo : this.hijos) {
                this.valor = -1;
                this.valor = max(this.valor, hijo.MiniMax());
            }
        } else {
            assert (this.player); // voy a minimizar
            this.hasValor = true;

            for (ProcesadorM hijo : this.hijos) {
                this.valor = 100;
                this.valor = min(this.valor, hijo.MiniMax());
            }
        }
        return 0;
    }

    public void Finiquitar() {

        for (ProcesadorM posible : this.hijos) {

            if (posible.valor == this.valor) {

                this.board = posible.board;

            }
        }

    }

    //----------------------------------------------------------------------------------------------------   
    public boolean MakeMove(MoveList inicio, MoveList fin, boolean turno) {
        //System.out.println(this.board[inicio.getX()][inicio.getY()].isOccupied() + "ocupado");
        if (this.board[inicio.getX()][inicio.getY()].isOccupied()) {
            Piece pieza = this.board[inicio.getX()][inicio.getY()].getPiece();

            if (pieza.isWhite() == turno) {

                boolean result = MovePiece(inicio, fin, pieza);
                //this.notifyObservers();
                return result;
            } else {
                return false;
            }
        }

        return false;
    }

    private boolean MovePiece(MoveList inicio, MoveList fin, Piece pieza) {

        Router r = new Router(inicio, fin, pieza);
        r.calc();
        List<MoveList> rute;
        rute = new ArrayList<>();
        boolean color = pieza.isWhite();
        //System.out.println(r.IsRoute());
        if (r.IsRoute()) {
            for (MoveList m : r) {
                if (!this.IsMovPosible(m, color)) {
                    return false;
                }
                //System.out.println(m.getX()+ " "+m.getY());
                rute.add(m);
            }

            if (this.board[fin.getX()][fin.getY()].isOccupied()) {
                return false;
            }// si donde quiero ir esta ocupado
            if (!pieza.isQueen() && rute.size() > 2) {
                return false;
            }//si no soy reina y tengo mas de dos mov es galso
            if (!this.board[rute.get(0).getX()][rute.get(0).getY()].isOccupied()
                    && rute.size() > 1 && !pieza.isQueen()) {
                return false;
            } // si la siuiente no es ocuapda no me puedo mover mas de un movimiento siempre y cuando no sea reina

            Piece aux = this.board[inicio.getX()][inicio.getY()].releasePiece();
            for (MoveList j : rute) {

                if (this.board[j.getX()][j.getY()].isOccupied()) {

                    Piece otherPiece = this.board[j.getX()][j.getY()].getPiece();
                    this.board[j.getX()][j.getY()].setPiece(null);

                }
                //a = null;
            }
            this.board[fin.getX()][fin.getY()].setPiece(aux);

            return true;

        } else {
            return false;
        }
    }

    private boolean IsMovPosible(MoveList k, boolean color) {

        if (!this.board[k.getX()][k.getY()].isOccupied()) {
            return true;
        } else {
            //Piece pieza = this.board[k.getX()][k.getY()].getPiece();
            return this.board[k.getX()][k.getY()].getPiece().isWhite() != color;
            //pieza = null;
        }
    }

}
