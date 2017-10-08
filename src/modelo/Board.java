/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;


import MiniMax.ProcesadorM;
import static java.lang.Math.abs;
import static java.lang.Math.floor;
import java.util.ArrayList;
import java.util.List;
import gamecheckers.GameCheckers;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
//impor java.util.iterable;
//import java.util.Observer;

/**
 *
 * @author Felipe
 */
public class Board extends Observable {

    private Observer observer;// observamos el grupo de fichas 
    public static Group PieceGroup;
    public Space[][] board = new Space[8][8];
    protected boolean turno = true;
    public boolean canPlay = true;
    

    public Board(Group PieceGroup) {

        this.PieceGroup = PieceGroup;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boolean color = (j + i) % 2 == 0;
                board[i][j] = new Space(color, i, j);
            }
        }

    }
    
    /**
     *
     */

    

    @Override
    public void addObserver(Observer observer) {
        this.observer = observer;
    }

    @Override
    public void notifyObservers() {
        //System.out.println("" + "notify");
        if (observer != null) {

            observer.update(this, "");
        }
    }

    /**
     * Ubica 12 damas blanacs y 12 damas negras
     *
     *
     */
    public void CargarDamas() {
        //cargo damas blancas
        for (int i = 5; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boolean blanca = (j + i) % 2 == 0;
                if (!blanca) {
                    this.board[i][j].setPiece(makePiece(true, i, j));

                }

            }
        }

        //cargo damas negras
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 8; j++) {
                boolean blanca = (j + i) % 2 == 0;
                if (!blanca) {
                    this.board[i][j].setPiece(makePiece(false, i, j));
                }

            }
        }

        // this.notifyObservers();
    }

    private Checker makePiece(boolean color, int x, int y) {

        Checker piece = new Checker(color, x, y);
        piece.setOnMouseReleased((MouseEvent e) -> {
            MovePersona(piece);
        });
        return piece;
    }

    protected int ConvertPos(double n) {

        return (int) floor(n / 75);
    }

    protected void MovePersona(Piece piece) {

        int finalX = ConvertPos(piece.getLayoutY());
        int finalY = ConvertPos(piece.getLayoutX());

        if (finalX == -1 && finalY == -1) {
            finalX = 0;
            finalY = 0;
        } else if (finalX == -1 && finalY != -1) {
            finalX = 1;
        } else if (finalY == -1 && finalX != -1) {
            finalY = 1;
        }

        int inicioy = ConvertPos(piece.iniciox);
        int iniciox = ConvertPos(piece.inicioy);
        MoveList ini = new MoveList(iniciox, inicioy);
        MoveList fin = new MoveList(finalX, finalY);
        if (this.canPlay) {

            boolean goodMov = MakeMove(ini, fin, turno);//el turno en estos momentos lo tiene cualqueir pieza
            if (goodMov) {
                turno = !turno;
                piece.move(finalX, finalY);
                this.CleanWindow(); // actualizo la vista
            } else {
                piece.move(iniciox, inicioy);

            }
        } else {
            piece.move(iniciox, inicioy);
        }

    }
    
    

    protected void CleanWindow() {
        PieceGroup.getChildren().clear();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (this.board[i][j].isOccupied()) {
                    Piece a = this.board[i][j].getPiece();
                    a.move(i, j);
                    PieceGroup.getChildren().add(a);
                }
            }
        }
    //String a = this.toString();
   // System.out.print(a);
        this.CheckQueen();
        this.notifyObservers();
    }

    /*
    Impreme una matriz que representa 
    el estado actual del tablero
    retornando un objeto del tipo String
     */
   @Override
    public String toString() {
        String k = "";

        for (Space[] s : this.board) {
            for (Space a : s) {

                String space = a.toString();
                if (!a.isOccupied()) {
                    k = k + a.toString();
                } else {
                    k = k + a.getPiece().tostring();
                }
            }
            k = k + "\n";
        }
        //System.out.println(k);
        return k;
    }


    public void CheckQueen() {

        int a = 0, b = 7;
        boolean colora = true;
        boolean colorb = false;
        for (int i = 0; i < 8; i++) {

            if (this.board[a][i].isOccupied()) {
                boolean pieza = this.board[a][i].getPiece().isWhite();
                if (pieza == colora) {
                    this.board[a][i].getPiece().MakeQueen();

                }
            }

            if (this.board[b][i].isOccupied()) {
                boolean pieza = this.board[b][i].getPiece().isWhite();
                if (pieza == colorb) {
                    this.board[b][i].getPiece().MakeQueen();

                }
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
                    //Piece a = this.board[j.getX()][j.getY()].releasePiece();
                    Piece otherPiece = this.board[j.getX()][j.getY()].getPiece();
                    //System.out.println(""+ j.getX()+ " "+ j.getY());

                    this.board[j.getX()][j.getY()].setPiece(null);
                    //PieceGroup.getChildren().remove(otherPiece);

                    //a.getChildren().clear();
                    // a.getChildren().clear();
                    //PieceGroup.getChildren().remove(a);
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