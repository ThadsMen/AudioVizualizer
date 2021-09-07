/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioviz;

import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Professor Wergeles 
 */
public interface Visualizer {
    public void setup(Integer numBands, AnchorPane vizPane);
    public void destroy();
    public String getVizName();
    public void visualize(double timestamp, double lenght, float[] magnitudes, float[] phases);
}