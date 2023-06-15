package Fractals;
import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
/**
 *
 * @author kk
 */

//This is responsible for generating the Fractal image
public class FractalGen extends JPanel{
    
    public static String fractals[] = {"empty","Mandelbrot","Burning ship","Julia"};
    //available fractals list
    
    public static float typeFractal = 1 ;
    //1 == mandelbrot set, 2 == burning ship fractal, 3 == julia set
    //initial is set to mandelbrot
 
    //inital RGB value 
    public static int red = 17;
    public static int green = 38;
    public static int blue = 108 ;
    
    
    public static int bound = 2;
    
    
    
    public static int sizeFrame = 1000;
    private static int sizeOval = 2;
    
    // range and mid point to enlarge (Like TI-84)
    // it will always have 1:1 ratio
    public static float midRe = (float) 0;
    public static float midIm = (float) 0;
    public static float range = (float) 4;
    public static float startRe = midRe - (range / 2);
    public static float startIm = midIm + (range / 2);
    public static float stepSize = range / sizeFrame;
    public static float minRe = midRe - ( range / 2 ) ;
    public static float maxIm = midIm + ( range / 2 ) ;
    public static int iter = 450;
    
 
 
    // each step, alpha changes in order to give more dynamic colour
    public static float hue = 0;
    public static float sat = 0;
    public static float bri = 0;
    public static float change = (float) 0.02;
 

    /**
     * @param args the command line arguments
     */
    
   //RGB to HSB converter
   //HSB is used for the fractal drawing
   //RGB is used for the UI, more intuitive to the end user
   private void converter(int r, int g,int b){
        float [] hsb = Color.RGBtoHSB(r, g, b, null);
        hue = hsb[0];
        sat = hsb[1];
        bri = hsb[2];        
    }
    
    //squre root function
    private float sq(float a){
        float b = a * a; 
        return (b); 
    }
    
    private int mandelbrot ( float Re, float Im, int n ) {
    //f(x) = Z^2 + C
      
        //real and imeginary part of Z_(n-1)
        float znRe = 0;
        float znIm = 0;
        //use to store re & im before updating other variables
        float dummyRe = 0;
        float dummyIe = 0;
        //the original c value * NOTE : c shouldn't be recursive!!!
        float cRe = Re;
        float cIm = Im;
       
        for (int i = 0; i < n; i++ ){
            dummyRe = Re;
            dummyIe = Im;
            Re = - (znIm * znIm) + (znRe * znRe) + cRe ;
            Im = (2 * znIm * znRe) +  cIm;
            znIm = dummyIe;
            znRe = dummyRe;
            double dis = Math.sqrt(sq(Re) + sq(Im));
            //checking |Z_n| < 2
            if (dis <= bound ){
                
            }else{
                return(i);   
            }            
        }
        return(n);         
    }
    
    private int burningship(float Re, float Im, int n){
        //real and imeginary part of Z_(n-1)
        float znRe = 0;
        float znIm = 0;
        //use to store re & im before updating other variables
        float dummyRe = 0;
        float dummyIe = 0;
        //the original c value * NOTE : c shouldn't be recursive!!!
        float cRe = Re;
        float cIm = Im;
        
        for (int i = 0 ; i < n ; i++){
            dummyRe = Re;
            dummyIe = Im;
            Re = sq(Math.abs(znRe)) - sq(Math.abs(znIm)) + cRe;
            Im = (2 * Math.abs(znRe) * Math.abs(znIm)) + cIm;
            double dis = Math.sqrt(sq(Re) + sq(Im));
            znRe = dummyRe;
            znIm = dummyIe;
            //checking |Z_n| < 2
            if (dis <= bound ){
                
            }else{
                return(i);   
            }
        }
        return(n);
    }
    
    private float seedRe =(float) -0.512511498387847167;
    private float seedIm = (float )0.521295573094847167;
    private int julia (float Re, float Im, int n){
        //real and imeginary part of Z_(n-1)
        float znRe = 0;
        float znIm = 0;
        //use to store re & im before updating other variables
        float dummyRe = 0;
        float dummyIe = 0;
        for (int i = 0; i < n; i++ ){
            dummyRe = Re;
            dummyIe = Im;
            Re = - (znIm * znIm) + (znRe * znRe) + seedRe ;
            Im = (2 * znIm * znRe) +  seedIm;
            znIm = dummyIe;
            znRe = dummyRe;
            double dis = Math.sqrt(sq(Re) + sq(Im));
            //checking |Z_n| < 2
            if (dis <= bound ){
                
            }else{
                return(i);   
            }            
        }
        
        return (n);
    }

    //Save image function
    public void save(){
        try{

            BufferedImage ScreenCap = new Robot().createScreenCapture(new Rectangle(getX(),getY(),sizeFrame,sizeFrame));
            setVisible(true);
            Graphics2D g2d = ScreenCap.createGraphics();
            this.paintComponent(g2d);
            String desktop = System.getProperty("user.home") + "/desktop";
            File img = new File(desktop,fractals[(int)typeFractal] +",RGB("+ red +"," + green +","+ blue + "),mid(" + midRe +"," + midIm + "),range(" +range + "), iter("+ iter +").png");
            ImageIO.write(ScreenCap,"png",img);
        }catch (Exception e){ 
            System.out.println("exception ");
        }
    }
    
 
    //painting
    public FractalGen(){
  
        this.setBorder(BorderFactory.createLineBorder(Color.black));
    }
    
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        converter(red,green,blue);
        startRe = midRe - (range / 2);
        startIm = midIm + (range / 2);
        stepSize = range / sizeFrame;
        for (int i = 0 ; i <= sizeFrame;i++){
            for (int r = 0 ; r <= sizeFrame; r ++){
                float im = (float) (startIm - (stepSize * i));
                float re = (float) (startRe + (stepSize * r));
                int result = 0;
                if (typeFractal == 1){
                    result = meadelbrot(re, im ,iter);
                }else if (typeFractal == 2){
                    result = burningship(-re, -im, iter);
                }else if (typeFractal == 3){
                    result = julia(re, im ,iter);
                }
                
                if (result == iter ){
                    g2d.setColor(Color.black);
                    g2d.fillOval( r, i, sizeOval, sizeOval);
                }else{
                    float diff = (bri + change * (result));
                    g2d.setColor(Color.getHSBColor(hue, sat, diff));
                    g2d.fillOval( r, i , sizeOval, sizeOval); 
                }
            }
        }
        FractalFrame.updating = false;
    }
        
}
