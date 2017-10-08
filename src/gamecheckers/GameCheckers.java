/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamecheckers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import modelo.Board;
import modelo.MoveList;
import modelo.Piece;
import modelo.Space;

/**
 *
 * @author Felipe
 */
public class GameCheckers extends Application {

    private Parent CreateContent() {
        Pane root = new Pane();
        GameControler juego = new GameControler(root,false);// falso juega jugador, verdadero juega maquina
        return root;
    }

    @Override
    public void start(Stage stage) throws Exception {

        Scene scene;
        scene = new Scene(CreateContent());
        stage.setTitle("LAS DAMAS DE PIPE AL CUADRADO");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        launch(args);

    }

}
