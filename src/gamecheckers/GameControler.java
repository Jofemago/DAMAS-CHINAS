/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamecheckers;

import java.util.Observable;
import java.util.Observer;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import modelo.Board;
import modelo.BoardMinimax;
import modelo.Piece;

/**
 *
 * @author Felipe
 */
public class GameControler implements Observer {

    private final int casillas = 8;
    private final int tamPanel = 600;
    private Pane root;
    private Group SpaceGroup = new Group();
    private Group PieceGroup = new Group();

    private Board board;
    private BoardMinimax boardmnx; //se  activa dependiento del juegomaquia

    public Jugador blanco;
    public Jugador negro;

    public boolean juegamaquina ;

    public GameControler(Pane root, boolean juegomaquia) {
        juegamaquina = juegomaquia;
        this.root = root;
        GeneratorStrategy juego = new GeneratorStrategy();
        root.setPrefSize(tamPanel, tamPanel);

        if (this.juegamaquina) {
            boardmnx = new BoardMinimax(PieceGroup);
            blanco = new Jugador(boardmnx, true, true);
            negro = new Jugador(boardmnx, false, true);
            boardmnx.addObserver(this);
            boardmnx.CargarDamas();
            EstrategiaP2M jugadores = new EstrategiaP2M(SpaceGroup, PieceGroup, boardmnx, blanco, negro);
            juego.setStrategy(jugadores);
            juego.generateStrategy();

        } else {

            board = new Board(PieceGroup);
            blanco = new Jugador(board, true, true);
            negro = new Jugador(board, false, true);
            board.addObserver(this);
            board.CargarDamas();
            EstrategiaP2P jugadores = new EstrategiaP2P(SpaceGroup, PieceGroup, board, blanco, negro);
            juego.setStrategy(jugadores);
            juego.generateStrategy();
        }

        root.getChildren().addAll(SpaceGroup, PieceGroup);
    }

    public boolean checkgame() {

        return blanco.VerificarPlayer() && negro.VerificarPlayer();

    }

    public void EliminarPieeza(Piece piece) {
        PieceGroup.getChildren().remove(piece);
    }

    @Override
    public void update(Observable o, Object arg) {

        if (!this.juegamaquina) {

            board.canPlay = checkgame();
        } else {
            //System.out.println("holii");
            this.boardmnx.canPlay = checkgame();
        }
    }
}
