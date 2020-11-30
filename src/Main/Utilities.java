package Main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Utilities {
    //IMAGE RENDERING
    /*
    public int getRGB(Object inData) {
    return (getAlpha(inData) << 24)
        | (getRed(inData) << 16)
        | (getGreen(inData) << 8)
        | (getBlue(inData) << 0);
    }
    */
    public static void copyFrameBuffer(FrameBuffer fb1, FrameBuffer fb2) {
        if (fb1.length() == fb2.length() && fb1.length(0) == fb2.length(0)) {
            for (int i = 0; i < fb1.length(); i++) {
                for (int j = 0; j < fb1.length(0); j++) {
                    fb2.writePixel(i, j, fb1.readPixel(i, j));
                }
            }
        } else {
            throw new ArrayIndexOutOfBoundsException("FrameBuffer sizes do not match.");
        }
    }
    public static FrameBuffer BiToFB(BufferedImage bi) {
        FrameBuffer fb = new FrameBuffer(bi.getHeight(), bi.getWidth());
        for (int i = 0; i < bi.getWidth(); i++) {
            for (int j = 0; j < bi.getHeight(); j++) {
                //System.out.println(Math.abs(bi.getRGB(i, j)));
                fb.writePixel(i, j, bi.getRGB(i, j));
                //fb.writePixel(i, j, Color.rgb(255, 0, 0, 1.0));
                //System.out.println(((Math.abs(bi.getRGB(i, j)))));
                //System.out.println(colorConvert.toString((colorConvert.IntToRGB(16711680))));
            }
        }
        return fb;
    }
    //RESIZE: http://tech-algorithm.com/articles/nearest-neighbor-image-scaling/
    public int[] resizeA(int[] pixels,int w1,int h1,int w2,int h2) {
        int[] temp = new int[w2*h2] ;
        double x_ratio = w1/(double)w2 ;
        double y_ratio = h1/(double)h2 ;
        double px, py ;
        for (int i=0;i<h2;i++) {
            for (int j=0;j<w2;j++) {
                px = Math.floor(j*x_ratio) ;
                py = Math.floor(i*y_ratio) ;
                temp[(i*w2)+j] = pixels[(int)((py*w1)+px)] ;
            }
        }
        return temp ;
    }
    public int[] resizeB(int[] pixels,int w1,int h1,int w2,int h2) {
        int[] temp = new int[w2*h2] ;
        // EDIT: added +1 to account for an early rounding problem
        int x_ratio = (int)((w1<<16)/w2) +1;
        int y_ratio = (int)((h1<<16)/h2) +1;
        //int x_ratio = (int)((w1<<16)/w2);
        //int y_ratio = (int)((h1<<16)/h2);
        int x2, y2 ;
        for (int i=0;i<h2;i++) {
            for (int j=0;j<w2;j++) {
                x2 = ((j*x_ratio)>>16) ;
                y2 = ((i*y_ratio)>>16) ;
                temp[(i*w2)+j] = pixels[(y2*w1)+x2] ;
            }
        }
        return temp ;
    }

    //FILES
    //load image
    public static BufferedImage ImageRead(File file) throws IOException {
        BufferedImage bfile = ImageIO.read(file);
        int[][] img = new int[bfile.getHeight()][bfile.getWidth()];
        BufferedImage bi = new BufferedImage(img[0].length, img.length, BufferedImage.TYPE_INT_ARGB);

        for (int i = 0; i < bi.getHeight(); ++i) {
            for (int j = 0; j < bi.getWidth(); ++j) {
                int pixel =	(img[i][j] << 24) | (img[i][j] << 16) | (img[i][j] << 8) | (img[i][j]);
                bi.setRGB(i, j, pixel);
            }
        }
        return ImageIO.read(file);
    }
    //save image
    //bi: the loaded image format
    //fb: the converted framebuffer that will write a new saving bi to
    public static void ImageWrite(BufferedImage bi1, FrameBuffer fb, String filename) throws IOException {
        BufferedImage bi2 = new BufferedImage(fb.length(), fb.length(0), bi1.getType());
        //int[][] fb2 = fb.returnFB();

        for (int i = 0; i < bi2.getHeight(); ++i) {
            for (int j = 0; j < bi2.getWidth(); ++j) {
                //a, r, g, b
                //int pixel =	(fb2[i][j] << 24) | (fb2[i][j] << 16) | (fb2[i][j] << 8) | (fb2[i][j]);
                bi2.setRGB(i, j, fb.readPixel(i, j));
            }
        }
        //save the file
        File outputFile = new File(filename);
        ImageIO.write(bi2, "png", outputFile);
    }
}
