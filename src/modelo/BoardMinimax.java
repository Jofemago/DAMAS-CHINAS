/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import MiniMax.ProcesadorM;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Felipe
 */
public class BoardMinimax extends Board {

    public ProcesadorM Minimax;

    public BoardMinimax(Group PieceGroup) {
        super(PieceGroup);
        // this.StartNode();
    }

    public void StartNode() {

        this.Minimax = new ProcesadorM(this.board, 4, false);

    }

    @Override
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
                // turno = !turno;
                piece.move(finalX, finalY);
                this.CleanWindow(); // actualizo la vista

                //como el movimiento fue bueno, juega la maquina 
                this.StartNode();
                this.board = MoveMachine();
                this.CleanWindow();
                this.notifyObservers();
                //this.StartNode();

            } else {
                piece.move(iniciox, inicioy);

            }
        } else {
            piece.move(iniciox, inicioy);
        }

        //this.StartNode();
    }

    private Space[][] MoveMachine() {
        //this.StartNode();
        /*
            Llamar Minimax
         */

        this.Minimax.MiniMax();
        this.Minimax.Finiquitar();

        //this.Minimax.MakeBasicMov();
        //System.out.println( this.Minimax.PieceIsBlock(new MoveList(6,1), new MoveList(1,1), true, 2));
        Space[][] b = this.Minimax.board;
        MakeMovPiecesVisual();
        //this.StartNode();
        return b;
    }

    private void MakeMovPiecesVisual() {
        // asigna la posibilidad a las fichas copiadas de moverse en el recuadro original

        Space[][] b = this.Minimax.board;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (b[i][j].isOccupied()) {
                    Piece piece = b[i][j].getPiece();
                    piece.setOnMouseReleased((MouseEvent e) -> {
                        MovePersona(piece);
                    });
                }
            }
        }
    }
}
