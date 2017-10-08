/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamecheckers;

/**
 *
 * @author Felipe
 */
public class GeneratorStrategy {
    
    private EstrategyGame estrategia;
    
    public void setStrategy(EstrategyGame e){
        
        this.estrategia = e;
    }
    
    public EstrategyGame getStrategy(){
        return this.estrategia;
    }
    
    public void generateStrategy(){
        
        this.estrategia.generate();
    }
}
