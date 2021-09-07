/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioviz;

import static java.lang.Integer.min;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Professor Wergeles
 */
public class EllipseVisualizer2 implements Visualizer {
    
    private final String name;
    
    private Integer numOfBands;
    private AnchorPane vizPane;
    
    private final Double bandHeightPercentage;
    private final Double minEllipseRadius;
    private final Double rotatePhaseMultiplier;
    
    private Double width;
    private Double height;
    
    private Double bandWidth;
    private Double bandHeight;
    private Double halfBandHeight;
    
    private final Double startHue;
    
    private Ellipse[] ellipses;
    
    public EllipseVisualizer2() {
        name = "Ellipse Visualizer 2";
        bandHeightPercentage = 1.3;
        minEllipseRadius = 10.0;
        rotatePhaseMultiplier = 300.0;
        width = 0.0;
        height = 0.0;
        bandWidth = 0.0;
        bandHeight = 0.0;
        halfBandHeight = 0.0;
        startHue = 260.0;
    }

    @Override
    public String getVizName() {
        return name;
    }
    
    @Override
    public void setup(Integer numBands, AnchorPane vizPane) {        
        destroy();
        
        this.numOfBands = numBands;
        this.vizPane = vizPane;
        
        height = vizPane.getHeight();
        width = vizPane.getWidth();
        
        Rectangle clip = new Rectangle(width, height);
        clip.setLayoutX(0);
        clip.setLayoutY(0);
        vizPane.setClip(clip);
        
        bandWidth = width / numBands;
        bandHeight = height * bandHeightPercentage;
        halfBandHeight = bandHeight / 2;
        ellipses = new Ellipse[numBands];
        
        for (int i = 0; i < numBands; i++) {
            Ellipse ellipse = new Ellipse();
            ellipse.setCenterX(bandWidth / 2 + bandWidth * i);
            ellipse.setCenterY(height / 2);
            ellipse.setRadiusX(bandWidth / 2);
            ellipse.setRadiusY(minEllipseRadius);
            ellipse.setFill(Color.hsb(startHue, 1.0, 1.0, 1.0));
            vizPane.getChildren().add(ellipse);
            ellipses[i] = ellipse;
        }
    }
    
    @Override
    public void destroy() {
        if (ellipses != null) {
            for (Ellipse ellipse : ellipses) {
                vizPane.getChildren().remove(ellipse);
            }
            ellipses = null;
            vizPane.setClip(null);
        }        
    }
   
    @Override
    public void visualize(double timestamp, double lenght, float[] magnitudes, float[] phases) {
        if (ellipses == null) {
            return;
        }
        
        Integer num = min(ellipses.length, magnitudes.length);
        
        for (int i = 0; i < num; i++) {
            ellipses[i].setRadiusY( ((60.0 + magnitudes[i])/60.0) * halfBandHeight + minEllipseRadius);
            ellipses[i].setFill(Color.hsb(startHue - (magnitudes[i] * -6.0), 1.0, 1.0, 1.0));
            ellipses[i].setRotate(phases[i] * rotatePhaseMultiplier);
        }
    }   
}
