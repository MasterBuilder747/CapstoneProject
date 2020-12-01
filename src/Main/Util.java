package Main;

import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Util {
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
        if (fb1.height() == fb2.height() && fb1.width(0) == fb2.width(0)) {
            for (int i = 0; i < fb1.height(); i++) {
                for (int j = 0; j < fb1.width(0); j++) {
                    fb2.writePixel(i, j, fb1.readPixel(i, j));
                }
            }
        } else {
            throw new ArrayIndexOutOfBoundsException("FrameBuffer sizes do not match.");
        }
    }
    //change one pixel to another color
    public static FrameBuffer colorPixel(FrameBuffer fb, int oldColor, int newColor) {
        FrameBuffer fb2 = new FrameBuffer(fb.width(), fb.height());
        for (int i = 0; i < fb.width(); i++) {
            for (int j = 0; j < fb.height(); j++) {
                if (fb.readPixel(i, j) == oldColor) {
                    fb2.writePixel(i, j, newColor);
                } else {
                    fb2.writePixel(i, j, fb.readPixel(i, j));
                }
            }
        }
        return fb2;
    }
    //change one pixel to another color
    public static FrameBuffer changeOpacity(FrameBuffer fb, int opacity) {
        FrameBuffer fb2 = new FrameBuffer(fb.width(), fb.height());
        Pixel p;
        for (int i = 0; i < fb.width(); i++) {
            for (int j = 0; j < fb.height(); j++) {
                p = ColorConvert.IntToRGB(fb.readPixel(i, j));
                p.a = opacity;
                fb2.writePixel(i, j, p.toInt());
            }
        }
        return fb2;
    }
    //tint the image to a color, will fill with the remaining opacity
    public static FrameBuffer greyScale(FrameBuffer fb) {
//        Blend b = new Blend();
//        b.setMode(BlendMode.MULTIPLY);
//        b.setOpacity(opacity);

        FrameBuffer fb2 = new FrameBuffer(fb.width(), fb.height());
        Pixel p;

        //source: https://techvidvan.com/tutorials/java-digital-image-processing/
        for (int i = 0; i < fb.width(); i++) {
            for (int j = 0; j < fb.height(); j++) {
                p = ColorConvert.IntToRGB(fb.readPixel(i, j));
                int ave = (int)((p.r + p.g + p.b)/3.0);
                p.r = ave;
                p.g = ave;
                p.b = ave;
                fb2.writePixel(i, j, p.toInt());
            }
        }
        return fb2;
    }
    public static FrameBuffer negative(FrameBuffer fb) {
        FrameBuffer fb2 = new FrameBuffer(fb.width(), fb.height());
        Pixel p;

        //source: https://techvidvan.com/tutorials/java-digital-image-processing/
        for (int i = 0; i < fb.width(); i++) {
            for (int j = 0; j < fb.height(); j++) {
                p = ColorConvert.IntToRGB(fb.readPixel(i, j));
                p.r = 255-p.r;
                p.g = 255-p.g;
                p.b = 255-p.b;
                fb2.writePixel(i, j, p.toInt());
            }
        }
        return fb2;
    }
    public static FrameBuffer sepia(FrameBuffer fb) {
        FrameBuffer fb2 = new FrameBuffer(fb.width(), fb.height());
        Pixel p;

        //source: https://techvidvan.com/tutorials/java-digital-image-processing/
        for (int i = 0; i < fb.width(); i++) {
            for (int j = 0; j < fb.height(); j++) {
                p = ColorConvert.IntToRGB(fb.readPixel(i, j));
                int R = p.r;
                int G = p.g;
                int B = p.g;
                int newR = (int)(0.393*R + 0.769*G + 0.189*B);
                int newG = (int)(0.349*R + 0.686*G + 0.168*B);
                int newB = (int)(0.272*R + 0.534*G + 0.131*B);
                p.r = Math.min(newR, 255);
                p.g = Math.min(newG, 255);
                p.b = Math.min(newB, 255);
                fb2.writePixel(i, j, p.toInt());
            }
        }
        return fb2;
    }
    public static FrameBuffer mirrorY(FrameBuffer fb) {
        BufferedImage bi1 = Util.FBtoBi(fb);
        BufferedImage bi2 = new BufferedImage(fb.width(), fb.height(), 6);

        //source: https://techvidvan.com/tutorials/java-digital-image-processing/
        for (int y = 0; y < fb.height(); y++) {
            //lx = left moving pixel
            //rx = right moving pixel
            for (int lx = 0, rx = fb.width() - 1; lx < fb.width(); lx++, rx--) {
                int p = bi1.getRGB(lx, y);
                bi2.setRGB(rx, y, p);
            }
        }
        return Util.BiToFB(bi2);
    }
    public static FrameBuffer mirrorX(FrameBuffer fb) {
        BufferedImage bi1 = Util.FBtoBi(fb);
        BufferedImage bi2 = new BufferedImage(fb.width(), fb.height(), 6);

        //source: https://techvidvan.com/tutorials/java-digital-image-processing/
        for (int x = 0; x < fb.width(); x++) {
            //dy = down (++)
            //uy = up (--)
            for (int dy = 0, uy = fb.height() - 1; dy < fb.height(); dy++, uy--) {
                int p = bi1.getRGB(x, dy);
                bi2.setRGB(x, uy, p);
            }
        }
        return Util.BiToFB(bi2);
    }
    public static FrameBuffer resizeCanvas(FrameBuffer fb1, int width, int height) {
        FrameBuffer fb2 = new FrameBuffer(width, height);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                fb2.writePixel(i, j, fb1.readPixel(i, j));
            }
        }
        return fb2;
    }
    //RESIZE: http://tech-algorithm.com/articles/nearest-neighbor-image-scaling/
    public static int[] resizeA(int[] pixels,int w1,int h1,int w2,int h2) {
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
        //the single dimension int[] need to be converted to a 2d int[][]...
        return temp;
    }
    public static int[] resizeB(int[] pixels,int w1,int h1,int w2,int h2) {
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
        return temp;
    }

    //BLUR:
    //source https://stackoverflow.com/questions/39684820/java-implementation-of-gaussian-blur
    public static BufferedImage blur(BufferedImage image, int[] filter, int filterWidth) {
        if (filter.length % filterWidth != 0) {
            throw new IllegalArgumentException("filter contains a incomplete row");
        }

        final int width = image.getWidth();
        final int height = image.getHeight();
        final int sum = IntStream.of(filter).sum();

        int[] input = image.getRGB(0, 0, width, height, null, 0, width);
        int[] output = new int[input.length];

        final int pixelIndexOffset = width - filterWidth;
        final int centerOffsetX = filterWidth / 2;
        final int centerOffsetY = filter.length / filterWidth / 2;

        // apply filter
        for (int h = height - filter.length / filterWidth + 1, w = width - filterWidth + 1, y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int r = 0;
                int g = 0;
                int b = 0;
                for (int filterIndex = 0, pixelIndex = y * width + x;
                     filterIndex < filter.length;
                     pixelIndex += pixelIndexOffset) {
                    for (int fx = 0; fx < filterWidth; fx++, pixelIndex++, filterIndex++) {
                        int col = input[pixelIndex];
                        int factor = filter[filterIndex];

                        // sum up color channels seperately
                        r += ((col >>> 16) & 0xFF) * factor;
                        g += ((col >>> 8) & 0xFF) * factor;
                        b += (col & 0xFF) * factor;
                    }
                }
                r /= sum;
                g /= sum;
                b /= sum;
                // combine channels with full opacity
                output[x + centerOffsetX + (y + centerOffsetY) * width] = (r << 16) | (g << 8) | b | 0xFF000000;
            }
        }

        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        result.setRGB(0, 0, width, height, output, 0, width);
        return result;
    }

    //IMPORTANT
    public static FrameBuffer BiToFB(BufferedImage bi) {
        FrameBuffer fb = new FrameBuffer(bi.getWidth(), bi.getHeight());
        for (int i = 0; i < fb.width(); i++) {
            for (int j = 0; j < fb.height(); j++) {
                //System.out.println(Math.abs(bi.getRGB(i, j)));
                fb.writePixel(i, j, bi.getRGB(i, j));
                //fb.writePixel(i, j, Color.rgb(255, 0, 0, 1.0));
                //System.out.println(((Math.abs(bi.getRGB(i, j)))));
                //System.out.println(colorConvert.toString((colorConvert.IntToRGB(16711680))));
            }
        }
        return fb;
    }
    public static BufferedImage FBtoBi(FrameBuffer fb) {
        BufferedImage bi = new BufferedImage(fb.width(), fb.height(), 6);
        for (int i = 0; i < bi.getWidth(); i++) {
            for (int j = 0; j < bi.getHeight(); j++) {
                //System.out.println(Math.abs(bi.getRGB(i, j)));
                bi.setRGB(i, j, fb.readPixel(i, j));
                //fb.writePixel(i, j, Color.rgb(255, 0, 0, 1.0));
                //System.out.println(((Math.abs(bi.getRGB(i, j)))));
                //System.out.println(colorConvert.toString((colorConvert.IntToRGB(16711680))));
            }
        }
        return bi;
    }
    //FILES
    //load image
    public static BufferedImage ImageRead(File file) throws IOException {
        BufferedImage bfile = ImageIO.read(file);
        int[][] img = new int[bfile.getHeight()][bfile.getWidth()];
        BufferedImage bi = new BufferedImage(img[0].length, img.length, BufferedImage.TYPE_INT_ARGB);

        for (int i = 0; i < bi.getWidth(); ++i) {
            for (int j = 0; j < bi.getHeight(); ++j) {
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
        BufferedImage bi2 = new BufferedImage(fb.width(), fb.height(), bi1.getType());
        //int[][] fb2 = fb.returnFB();

        for (int i = 0; i < bi2.getWidth(); ++i) {
            for (int j = 0; j < bi2.getHeight(); ++j) {
                //a, r, g, b
                //int pixel =	(fb2[i][j] << 24) | (fb2[i][j] << 16) | (fb2[i][j] << 8) | (fb2[i][j]);
                bi2.setRGB(i, j, fb.readPixel(i, j));
            }
        }
        //save the file
        File outputFile = new File(filename);
        ImageIO.write(bi2, "png", outputFile);
    }

    //other tools
    public static int[] stringToIntArray(String[] a) {
        int[] b = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            b[i] = Integer.parseInt(a[i]);
        }
        return b;
    }
}
