/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import javafx.scene.paint.Color;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Ellipse;

/**
 *
 * @author Felipe
 */
public abstract class Piece extends StackPane {

    public boolean inAction = true;
    protected boolean hasMoved;//especificada para la reina, para que se peuda mover dos casillas
    //protected Image image; //importar imagenes
    protected boolean color;// color de la ficha, blanco -> true, negro -> falso
    //solo objetos que heredan de aca pueden acceder a la parte protegida
    private Ellipse bg;
    private Ellipse ellipse;
    public double iniciox, inicioy;
    public double finx, finy;

    public void EliminarColorFicha() {
        bg.setFill(Color.DARKBLUE);

        bg.setStroke(Color.DARKBLUE);

        ellipse.setFill(Color.DARKBLUE);

        ellipse.setStroke(Color.DARKBLUE);

    }

    public Piece(boolean color, int x, int y) {

        this.color = color;

        move(x, y);

        bg = new Ellipse(25.5, 14.5);
        bg.setFill(Color.BLACK);

        bg.setStroke(Color.BLACK);
        bg.setStrokeWidth(3);

        bg.setTranslateX((75 - 25.5 * 2) / 2);
        bg.setTranslateY((75 - 14.5 * 2) / 2 + 75 * 0.07);

        ellipse = new Ellipse(25.5, 14.5);
        ellipse.setFill(color ? Color.WHITE : Color.BLACK);

        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(3);

        ellipse.setTranslateX((75 - 25.5 * 2) / 2);
        ellipse.setTranslateY((75 - 14.5 * 2) / 2);

        getChildren().addAll(bg, ellipse);

        setOnMousePressed(e -> {
            this.finx = e.getSceneY();
            this.finy = e.getSceneX();

            // System.out.println(""+ finx+ " " + finy);
        });

        setOnMouseDragged(e -> {

            relocate(e.getSceneX() - 25, e.getSceneY() - 25);
        });

        hasMoved = false;

        String location = "assests/pieces/";
        // String filename = getColor() + "_" + getName() + ".png";
        //image = new Image(location + filename);
    }

    /**
     *
     * @return
     */
    public MoveList getPos() {
        MoveList m = new MoveList((int) (iniciox / 75), (int) (inicioy / 75));
        return m;
    }

    public void move(int x, int y) {
        iniciox = y * 75;
        inicioy = x * 75;
        relocate(iniciox, inicioy);
    }

    public void RestartPos() {
        relocate(inicioy, iniciox);
    }

    //public void (){
    public boolean isQueen() {

        return hasMoved;
    }

    public void MakeQueen() {

        this.hasMoved = true;
        this.bg.setStroke(Color.WHITE);
    }

    //retorna la imagen de la pieza de ajedrez
    /*public Image getImage(){
        
        return image;
    }*/
    public String getColor() {

        return color ? "white" : "black";
    }

    //retorna true si el color es blanco
    public boolean isWhite() {
        //System.out.println(color);
        return color;
    }

    @Override
    public String toString() {

        return getName() + " " + getColor();
    }

    /**
     * Este metodo se sobre escribe para imprimir un simbolo de iterpretación
     * para trabajar el juego sin interfaz grafica
     *
     * @return
     */
    public abstract String tostring();

    public abstract MoveList[] getPieceMove();

    protected abstract String getName();
}
