/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Fractals;


import java.awt.event.MouseListener;
import javax.swing.*;
import java.awt.event.MouseEvent;

/**
 *
 * @author kk
 */
class FractalFrame extends JFrame implements MouseListener  {
    public static boolean updating = false;
    static JFrame FF = new JFrame("Fractals");    
    public FractalFrame(){
        FF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FF.setSize(FractalGen.sizeFrame,FractalGen.sizeFrame);
        FF.setLocationRelativeTo(null);
        FF.addMouseListener(this);  
        FF.setResizable(false);
        FF.add(new FractalGen());
        FF.setVisible(true);
        
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if (updating == false){
            updating = true;
            UtilityBox.push(UtilityBox.undo);
            if (UtilityBox.zoom == true){
                enlarge(e.getX(),e.getY());
            }else if(UtilityBox.zoom == false){
                reduce(e.getX(),e.getY());
            }    
        update();}
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        
    }
    @Override 
    public void mouseReleased(MouseEvent e) {
        
    }
    @Override 
    public void mouseEntered(MouseEvent e) {
 
    }
    @Override 
    public void mouseExited(MouseEvent e) {

    }
    
    
    
    private void enlarge(float x_p, float y_p){
        mouseLoc(x_p, y_p);
        FractalGen.range = FractalGen.range / 2;
    }
    
    private void reduce(float x_p, float y_p){
        mouseLoc(x_p, y_p);
        FractalGen.range = 2 * FractalGen.range;
    }
    
    
    private void mouseLoc(float x_p, float y_p){
        //this gives the coordinate of pointer on the screen
        //would want it to give coordinate on the JPannal 
       
        //have to convert java coordinate to normal cartaisan coordinate
        FractalGen.midRe = FractalGen.startRe + ( FractalGen.stepSize * ((float)x_p) );
        FractalGen.midIm = FractalGen.startIm - ( FractalGen.stepSize * ((float)y_p) );  
    }  
    public static void update(){
        FF.dispose();
        new FractalFrame();
        
    }
}

