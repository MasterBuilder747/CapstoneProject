package Main;

import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Utilities {
    //IMAGE RENDERING
    public static void renderImage(BufferedImage bi, FrameBuffer fb) {
        for (int i = 0; i < bi.getWidth(); i++) {
            for (int j = 0; j < bi.getHeight(); j++) {
                //System.out.println(Math.abs(bi.getRGB(i, j)));
                fb.writePixel(i, j, colorConvert.IntToRGB(bi.getRGB(i, j)));
                //fb.writePixel(i, j, Color.rgb(255, 0, 0, 1.0));
                //System.out.println(((Math.abs(bi.getRGB(i, j)))));
                //System.out.println(colorConvert.toString((colorConvert.IntToRGB(16711680))));
            }
        }
    }

    //FILES
    //read the framebuffer from file
    public static BufferedImage ImageRead(File file) throws IOException {
        BufferedImage bfile = ImageIO.read(file);
        int[][] img = new int[bfile.getWidth()][bfile.getHeight()];
        BufferedImage bi = new BufferedImage(img[0].length, img.length, BufferedImage.TYPE_INT_RGB);

        // -- prepare output image
        for (int i = 0; i < bi.getHeight(); ++i) {
            for (int j = 0; j < bi.getWidth(); ++j) {
                int pixel =	(img[i][j] << 16) | (img[i][j] << 8) | (img[i][j]);
                // int pixel =	((int)(Math.random() * 255) << 16) | (img[i][j] << 8) | (img[i][j]);
                bi.setRGB(j, i, pixel);
            }
        }

        // -- write output image
        //ImageIO.write(bi, "png", outputFile);
        return ImageIO.read(file);
    }
    public static void ImageWrite(int[][] img, String filename) throws IOException {
        BufferedImage bi = new BufferedImage(img[0].length, img.length, BufferedImage.TYPE_INT_RGB);

        // -- prepare output image
        for (int i = 0; i < bi.getHeight(); ++i) {
            for (int j = 0; j < bi.getWidth(); ++j) {
                int pixel =	(img[i][j] << 16) | (img[i][j] << 8) | (img[i][j]);
                // int pixel =	((int)(Math.random() * 255) << 16) | (img[i][j] << 8) | (img[i][j]);
                bi.setRGB(j, i, pixel);
            }
        }

        // -- write output image
        File outputFile = new File(filename);
        ImageIO.write(bi, "png", outputFile);
    }
}
