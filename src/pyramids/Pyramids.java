/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pyramids;

import java.awt.image.BufferedImage;

/**
 *
 * @author Jonathan Gonzalez
 */
public class Pyramids {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BufferedImage inImage   = ImageIo.readImage("utb.jpg");
       
       BufferedImage grayImage = ImageIo.toGray(inImage);
       ImageIo.writeImage(grayImage, "jpg", "grayed.jpg");  
       
        byte[][] grayInput  = ImageIo.getGrayByteImageArray2DFromBufferedImage(grayImage);
        
        // Get pyramids
        Pyramid obj = new Pyramid();
        
        byte[][] pyramidOutput = obj.zoomedOutPyramid(grayInput, 6);
        byte[][] pyramidOutputG = obj.gradientPyramid(grayInput, 6);
        
        BufferedImage pyramidImage = ImageIo.setGrayByteImageArray2DToBufferedImage(pyramidOutput);
        BufferedImage pyramidImageG = ImageIo.setGrayByteImageArray2DToBufferedImage(pyramidOutputG);

        ImageIo.writeImage(pyramidImage, "jpg", "pyramid.jpg");
        ImageIo.writeImage(pyramidImageG, "jpg", "pyramid_gradient.jpg");
        
    }
    
}
