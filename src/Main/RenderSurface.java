package Main;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;

public class RenderSurface extends WritableImage {
	
	private final FrameBuffer surface;

	//initial image when starting
	public RenderSurface(int width, int height) {
		super(width, height);
		this.surface = new FrameBuffer(width, height);
		for (int i = 0; i < width; ++i) {
			for (int j = 0; j < height; ++j) {
				surface.writePixel(i, j, new Pixel(255, 0, 0, 0).toInt());
			}
		}
		this.insertArray();
	}

	public void clearSurface() {
		this.surface.setFill(new Pixel(255, 0, 0, 0).toInt());
	}
	
	public FrameBuffer getSurface() {
		return this.surface;
		//return new int[3][3];
	}
	
    public void insertArray() {
        //Creating a writable image
    	int height = this.surface.length();
    	int width = this.surface.length(0);

        //getting the pixel writer 
        PixelWriter writer = this.getPixelWriter();

        for(int x = 0; x < height; x++) {
        	for(int y = 0; y < width; y++) {
           		//this inserts the fb into the screen
				writer.setArgb(x, y, this.surface.readPixel(x, y));
           	}
        }
    }

	public BufferedImage toImage() {
		BufferedImage bi = new BufferedImage(surface.length(0), surface.length(), BufferedImage.TYPE_INT_RGB);
    	
    	// -- prepare output image
    	for (int i = 0; i < bi.getHeight(); ++i) {
    	    for (int j = 0; j < bi.getWidth(); ++j) {
    			//int pixel =	(surface[i][j] << 16) | (surface[i][j] << 8) | (surface[i][j]);
//    			int pixel =	((int)(Math.random() * 255) << 16) | ((int)(Math.random() * 255) << 8) | ((int)(Math.random() * 255));
				bi.setRGB(i, j, surface.readPixel(i, j));
    		}
    	}
    	return bi;
	}
}
