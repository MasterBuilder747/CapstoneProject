package Main;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import java.awt.image.BufferedImage;

public class RenderSurface extends WritableImage {
	
	private FrameBuffer surface;

	//initial image when starting
	public RenderSurface(int width, int height) {
		super(width, height);
		this.resizeWindow(width, height);
		this.insertArray();
	}

	public void resizeWindow(int width, int height) {
		this.surface = new FrameBuffer(width, height);
		for (int i = 0; i < width; ++i) {
			for (int j = 0; j < height; ++j) {
				this.surface.writePixel(i, j, new Pixel(0, 0, 0, 0).toInt());
			}
		}
	}

	public void clearSurface() {
		this.surface.setFill(new Pixel(0, 0, 0, 0).toInt());
	}
	
	public FrameBuffer getSurface() {
		return this.surface;
		//return new int[3][3];
	}
	
    public void insertArray() {
        //Creating a writable image
    	int width = this.surface.width();
		int height = this.surface.height();

        //getting the pixel writer 
        PixelWriter writer = this.getPixelWriter();

        for(int x = 0; x < width; x++) {
        	for(int y = 0; y < height; y++) {
           		//this inserts the fb into the screen
				writer.setArgb(x, y, this.surface.readPixel(x, y));
           	}
        }
    }
	public void resize(int width, int height) {
		this.surface = new FrameBuffer(width, height);
	}

	public BufferedImage toImage() {
		BufferedImage bi = new BufferedImage(surface.width(), surface.height(), BufferedImage.TYPE_INT_ARGB);
    	
    	// -- prepare output image
    	for (int i = 0; i < bi.getWidth(); ++i) {
    	    for (int j = 0; j < bi.getHeight(); ++j) {
    			//int pixel =	(surface[i][j] << 16) | (surface[i][j] << 8) | (surface[i][j]);
//    			int pixel =	((int)(Math.random() * 255) << 16) | ((int)(Math.random() * 255) << 8) | ((int)(Math.random() * 255));
				bi.setRGB(i, j, surface.readPixel(i, j));
    		}
    	}
    	return bi;
	}
}
