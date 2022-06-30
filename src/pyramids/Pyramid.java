/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pyramids;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jonathan Gonzalez
 */
public class Pyramid {
    public void handleBorder2(byte[][] input, byte[][]output, int hmask, int vmask) {
        int h = input.length;
        int w = input[0].length;
        //top rows
        for (int i = 0; i < hmask; i++) {
            for (int j = 0; j < w; j++) {
                output[i][j] = (byte) 0;
            }
        }
        //bottom rows
        for (int i = h - hmask; i < h; i++) {
            for (int j = 0; j < w; j++) {
                output[i][j] = (byte) 0;
            }
        }
        //left columns
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < vmask; j++) {
                output[i][j] = (byte) 0;
            }
        }
        //right columns
        for (int i = 0; i < h; i++) {
            for (int j = w - vmask; j < w; j++) {
                output[i][j] = (byte) 0;
            }
        }
    }
    
    public byte[][] calc_sobel(byte[][]input, int maskSize) {

        // Decide border handling regions
        int h = (int) Math.floor((maskSize / 2));
        int v = (int) Math.floor((maskSize / 2));

        byte[][] output = new byte[input.length][input[0].length];
        
        // Handle borders:
        //handleBorder(input, output, h, v);
        handleBorder2(input, output, h, v);
        
        float gradient_value = 0;
        for (int i = h; i < output.length - v; i++) {
            for (int j = h; j < output[0].length - v; j++) {
                
                gradient_value = (float) (Math.abs(-(input[i-1][j-1] & 0xFF) - 2*(input[i-1][j] & 0xFF) - (input[i-1][j+1] & 0xFF) + 
                                                    (input[i+1][j-1] & 0xFF) + 2*(input[i+1][j] & 0xFF) + (input[i+1][j+1] & 0xFF)) +
                                        Math.abs(-(input[i-1][j-1] & 0xFF) - 2*(input[i][j-1] & 0xFF) - (input[i+1][j-1] & 0xFF) + 
                                                  (input[i-1][j+1] & 0xFF) + 2*(input[i][j+1] & 0xFF) + (input[i+1][j+1] & 0xFF)));
                
                output[i][j] = (byte) ImageIo.clip(gradient_value);
            }
        }
        return output;
    }
    
    public byte[][] zoomedOutPyramid (byte[][] input, int levels) {
        byte[][] pyramidOutput = new byte[input.length * 2][ input.length * 2];
        
        // Make list for all images 
        List<byte[][]> imageLevels = new ArrayList<>();
        imageLevels.add(input);
        
        // byte arrays of inputs and outputs to get each image level
        byte[][] currentInput = input;
        byte[][] currentOutput = new byte[currentInput.length/2][currentInput[0].length/2];
        
        // loop through each level to create the images
        int currLevel = 0;
        while(currLevel < levels) {
            int h = currentInput.length;
            int w = currentInput[0].length;
            
            // get 1/4 image size from the current input
            for (int i = 0; i < h/2; i++) {
                for (int j = 0; j < w/2; j ++) {
                    currentOutput[i][j] = (byte) (((currentInput[2*i][2*j] & 0xFF) + (currentInput[2*i+1][2*j] & 0xFF) +
                                                (currentInput[2*i][2*j+1] & 0xFF) + (currentInput[2*i+1][2*j+1] & 0xFF)) / 4);
                }
            }
            // add new level to the list, make current input the new level and
            // make current output 1/4 the size of the new current input
            imageLevels.add(currentOutput);
            currentInput = currentOutput;
            currentOutput = new byte[currentInput.length/2][currentInput[0].length/2];
            currLevel++;
        }
        
        
        byte[][] currImage; // the current image
        int start;          // the value where to start the iteration through the pyramid output
        int end;            // the value where to end the iteration through the pyramid output
        int x;              // the first index for the current image
        int y;              // the second index for the current image
        
        // loops through the images in imageLevels
        for (int i = 0; i < levels; i++) {
            // get current image of level i
            currImage = imageLevels.get(i);
            
            // get start and end to iterate through the pyramid output
            start = pyramidOutput.length - (currImage.length * 2);
            end = pyramidOutput.length - currImage.length;
            
            // set starting indices for the current image
            x = 0;
            y = 0;
            
            // add current image into the pyramid output
            // j,k are the indices for the pyramid output
            // x,y are the indices for the current image
            for (int j = start; j < end; j++) {
                for (int k = start; k < end; k++) {
                    pyramidOutput[j][k] = currImage[x][y];
                    y++;
                }
                x++;
                y = 0;
            }
        }
        
        return pyramidOutput;
    }
    
    public byte[][] gradientPyramid (byte[][] input, int levels) {
        byte[][] pyramidOutput = new byte[input.length * 2][ input.length * 2];
        
        // Make list for all images 
        List<byte[][]> imageLevels = new ArrayList<>();
        imageLevels.add(input);
        
        // byte arrays of inputs and outputs to get each image level
        byte[][] currentInput = input;
        byte[][] currentOutput = new byte[currentInput.length/2][currentInput[0].length/2];
        
        // loop through each level to create the images
        int currLevel = 0;
        while(currLevel < levels) {
            int h = currentInput.length;
            int w = currentInput[0].length;
            
            // get 1/4 image size from the current input
            for (int i = 0; i < h/2; i++) {
                for (int j = 0; j < w/2; j ++) {
                    currentOutput[i][j] = (byte) (((currentInput[2*i][2*j] & 0xFF) + (currentInput[2*i+1][2*j] & 0xFF) +
                                                (currentInput[2*i][2*j+1] & 0xFF) + (currentInput[2*i+1][2*j+1] & 0xFF)) / 4);
                }
            }
            // add new level to the list, make current input the new level and
            // make current output 1/4 the size of the new current input
            imageLevels.add(currentOutput);
            currentInput = currentOutput;
            currentOutput = new byte[currentInput.length/2][currentInput[0].length/2];
            currLevel++;
        }
        
        
        byte[][] currImage; // the current image
        int start;          // the value where to start the iteration through the pyramid output
        int end;            // the value where to end the iteration through the pyramid output
        int x;              // the first index for the current image
        int y;              // the second index for the current image
        
        // loops through the images in imageLevels
        for (int i = 0; i < levels; i++) {
            // get current image of level i
            currImage = imageLevels.get(i);
            
            // get the sobel gradient of the current image
            currImage = calc_sobel(currImage, 3);
            
            // get start and end to iterate through the pyramid output
            start = pyramidOutput.length - (currImage.length * 2);
            end = pyramidOutput.length - currImage.length;
            
            // set starting indices for the current image
            x = 0;
            y = 0;
            
            // add current image into the pyramid output
            // j,k are the indices for the pyramid output
            // x,y are the indices for the current image
            for (int j = start; j < end; j++) {
                for (int k = start; k < end; k++) {
                    pyramidOutput[j][k] = currImage[x][y];
                    y++;
                }
                x++;
                y = 0;
            }
        }
        
        return pyramidOutput;
    }
}
