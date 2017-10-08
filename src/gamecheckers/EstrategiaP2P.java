/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamecheckers;

import javafx.scene.Group;
import modelo.Board;

/**
 *
 * @author Felipe
 */
public class EstrategiaP2P implements EstrategyGame{
    
    private Group SpaceGroup ;
    private Group PieceGroup ;
    private Board board;
    public Jugador blanco;
    public Jugador negro;
   
    
    public EstrategiaP2P(Group SpaceGroup,Group PieceGroup,Board board,Jugador blanco,Jugador negro  ){
        
        this.PieceGroup = PieceGroup;
        this.SpaceGroup = SpaceGroup;
        this.board = board;
        this.blanco = blanco;
        this.negro = negro;
 
        
    }
    
    @Override
    public void generate() {
        
            
       

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                SpaceGroup.getChildren().add(board.board[x][y]);
                
                if (board.board[x][y].isOccupied()) {
                    PieceGroup.getChildren().add(board.board[x][y].getPiece());
                }
            }
        }
    }
    
}
