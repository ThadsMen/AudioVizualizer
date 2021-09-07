/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioviz;

import static java.lang.Integer.min;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author thads
 * I tried to make this look like a Ship going through light speed
 */
public class Tvmfp8LightSpeedTravelS21 implements Visualizer {
    private final String name;
    private String vizPaneInitialStyle = "";
    private Integer numOfBands;
    private AnchorPane vizPane;
    
    private final Double bandHeightPercentage;
    private final Double minEllipseRadius;
    
    private Double width;
    private Double height;
    
 
    private Double bandHeight;
    private Double halfBandHeight;
    
    private Double hue = 0.0;
    private final Double startHue;
    Random rand = new Random();
    
    private Rectangle[] rectangles;
    private Rectangle[] rectangles2;
    
    public Tvmfp8LightSpeedTravelS21(){
        name = "Tvmfp8LightSpeedTravelS21";
        bandHeightPercentage = 1.3;
        minEllipseRadius = 10.0;
        width = 0.0;
        height = 0.0;
        bandHeight = 0.0;
        halfBandHeight = 0.0;
        startHue = 260.0;
    }

    @Override
    public void setup(Integer numBands, AnchorPane vizPane) {
        destroy();
        vizPaneInitialStyle=vizPane.getStyle();
        this.numOfBands = numBands;
        this.vizPane = vizPane;
        height = vizPane.getHeight();
        width = vizPane.getWidth();
        
        vizPane.setStyle("-fx-background-color: #000000;");
        Rectangle clip = new Rectangle(width, height);        
        clip.setLayoutX(0);
        clip.setLayoutY(0);
        vizPane.setClip(clip);
        
        
        bandHeight = height / numBands;
       
        halfBandHeight= (height*bandHeightPercentage) / 2;
        rectangles = new Rectangle[numOfBands];
        rectangles2 = new Rectangle[numOfBands];
        
        for(int i = 0; i < numOfBands; i++){
            
            Rectangle rec = new Rectangle();
            rec.setX(15);
            rec.setY(bandHeight*i);
            rec.setWidth(minEllipseRadius);
            rec.setHeight(bandHeight);
          
            rec.setFill(Color.DARKBLUE);
            vizPane.getChildren().add(rec);
            rectangles[i]=rec;
            
            Rectangle rec2 = new Rectangle();
            rec2.setX(width-25);
            rec2.setY(bandHeight*i);
            rec2.setWidth(minEllipseRadius);
            rec2.setHeight(bandHeight);
          
            rec2.setFill(Color.DARKBLUE);
            vizPane.getChildren().add(rec2);
            rectangles2[i]=rec2;
     
        }
    }

    @Override
    public void destroy() {
        if(rectangles != null || rectangles2 != null){
            for(Rectangle rectangles : rectangles){
                vizPane.getChildren().remove(rectangles);      
            }
            for(Rectangle rectangles2 : rectangles2){
                vizPane.getChildren().remove(rectangles2);
            }
        rectangles = null;
        rectangles2 = null;
        vizPane.setClip(null);
        vizPane.setStyle(vizPaneInitialStyle);
        }
    }

    @Override
    public String getVizName() {
        return name;
    }

    @Override
    public void visualize(double timestamp, double lenght, float[] magnitudes, float[] phases) {
        if(rectangles==null){
            return;
        }
        
        Integer num = min(rectangles.length, magnitudes.length);
      
        for (int i = 0; i<num/2; i++){
            int randomNumber = rand.nextInt(num);
            rectangles[i].setWidth((((60.0 + magnitudes[i])/60.0) * halfBandHeight + minEllipseRadius)+50);
            rectangles[num-i-1].setWidth((((60.0 + magnitudes[i])/60.0) * halfBandHeight + minEllipseRadius)+50);
            //rectangles2[i].setX(width-(((60.0 + magnitudes[i])/60.0) * halfBandHeight + minEllipseRadius));
            rectangles2[i].setScaleX((((60.0 + magnitudes[i])/60.0) * halfBandHeight + minEllipseRadius)-30);
            //rectangles2[num-i-1].setX(width-(((60.0 + magnitudes[i])/60.0) * halfBandHeight + minEllipseRadius));
            rectangles2[num-i-1].setScaleX((((60.0 + magnitudes[i])/60.0) * halfBandHeight + minEllipseRadius)-30);
            rectangles[i].setFill(Color.hsb(startHue - (magnitudes[i] * -6.0), 1.0, 1.0, 1.0));
            rectangles[num-i-1].setFill(Color.hsb(startHue - (magnitudes[i] * -6.0), 1.0, 1.0, 1.0));
            rectangles2[i].setFill(Color.hsb(startHue - (magnitudes[i] * -6.0), 1.0, 1.0, 1.0));
            rectangles2[num-i-1].setFill(Color.hsb(startHue - (magnitudes[i] * -6.0), 1.0, 1.0, 1.0));
           /* if(randomNumber%5==0){
            rectangles[i].setFill(Color.LIGHTPINK);
            rectangles[num-(i)-1].setFill(Color.LIGHTPINK);
            rectangles2[i].setFill(Color.LAVENDER);
            rectangles2[num-i-1].setFill(Color.LAVENDER);
            }else if(randomNumber%4==0){
                rectangles[i].setFill(Color.BLACK);
                rectangles[num-(i)-1].setFill(Color.BLACK);
                rectangles2[i].setFill(Color.BLACK);
                rectangles2[num-i-1].setFill(Color.BLACK); 
            }else{
                rectangles[i].setFill(Color.DARKBLUE);
                rectangles[num-(i)-1].setFill(Color.DARKBLUE);
                rectangles2[i].setFill(Color.DARKBLUE);
                rectangles2[num-i-1].setFill(Color.DARKBLUE);
            }
            */
            
            
        }
   
    }
    
}
