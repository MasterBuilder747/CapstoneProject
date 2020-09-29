package Main;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;

public class RenderSurface extends WritableImage {
	
	private FrameBuffer surface;
	private final int x;
	private final int y;

	//initial image when starting
	public RenderSurface(int width, int height) {
		super(width, height);
		this.x = width;
		this.y = height;
		this.surface = new FrameBuffer(width, height);
		for (int i = 0; i < width; ++i) {
			for (int j = 0; j < height; ++j) {
				surface.writePixel(i, j, Color.rgb(0, 0, 0, 1.0));
			}
		}
		this.insertArray();
	}

	public void clearSurface() {
		this.surface.setFill(Color.rgb(0, 0, 0, 1.0));
	}
	
	public FrameBuffer getSurface() {
		return this.surface;
		//return new int[3][3];
	}
	
    public void insertArray() {
        //Creating a writable image
    	int height = this.surface.fb.length;
    	int width = this.surface.fb[0].length;

        //getting the pixel writer 
        PixelWriter writer = this.getPixelWriter();

        for(int x = 0; x < height; x++) {
        	for(int y = 0; y < width; y++) {
           		//this inserts the fb into the screen
				writer.setColor(x, y, this.surface.readPixel(x, y));
           	}
        }
    }
    
	public BufferedImage toImage() {
		BufferedImage bi = new BufferedImage(surface.fb[0].length, surface.fb.length, BufferedImage.TYPE_INT_RGB);
    	
    	// -- prepare output image
    	for (int i = 0; i < bi.getHeight(); ++i) {
    	    for (int j = 0; j < bi.getWidth(); ++j) {
    			//int pixel =	(surface[i][j] << 16) | (surface[i][j] << 8) | (surface[i][j]);
//    			int pixel =	((int)(Math.random() * 255) << 16) | ((int)(Math.random() * 255) << 8) | ((int)(Math.random() * 255));
    			Color c = surface.readPixel(i, j);
				bi.setRGB(j, i, colorConvert.RGBtoInt(c));
    		}
    	}
    	return bi;
	}
}
