package Main;

import javafx.scene.paint.Color;

public class FrameBuffer {

    //this is a 2D representation of a 0 to 255 colored pixel
    //r, g, b, alpha(true double)
    double[][][] fb;

    public FrameBuffer(int w, int h) {
        //all values are set to 0.0 by default
        this.fb = new double[w][h][4];
    }

    //this overrides all of the pixels with this one color
    public void setFill(Color c) {
        for (int i = 0; i < this.fb.length; i++) {
            for (int j = 0; j < this.fb[0].length; j++) {
                this.writePixel(i, j, c);
            }
        }
    }

    public void writePixel(int x, int y, Color c) {
        //this writes the doubles into the array
        this.fb[x][y][0] = c.getRed();
        this.fb[x][y][1] = c.getGreen();
        this.fb[x][y][2] = c.getBlue();
        this.fb[x][y][3] = 1.0;//c.getOpacity();
    }

    public Color readPixel(int x, int y) {
        //this returns the double version of rgb
        return Color.color(this.fb[x][y][0], this.fb[x][y][1], this.fb[x][y][2], this.fb[x][y][3]);
    }

    //check if the exact color in the parm is equal to the specified pixel color in this fb
    //this excludes opacity for now
    public boolean compareColor(int x, int y, Color c) {
        //this compares the double values but uses ints for the actual comparison
        return (int)this.fb[x][y][0] * 255 == (int)c.getRed() * 255 && (int)this.fb[x][y][1] * 255 == (int)c.getGreen() * 255 && (int)this.fb[x][y][2] * 255 == (int)c.getBlue() * 255 && this.fb[x][y][3] == c.getOpacity();
    }

    public void print() {
        for (int i = 0; i < this.fb.length; ++i) {
            for (int j = 0; j < this.fb[0].length; ++j) {
                try {
                    Color c = this.readPixel(i, j);
                    System.out.print("<" + c.getRed() + ", " + c.getGreen() + ", " + c.getBlue() + ", " + c.getOpacity() + ">;\t");
                } catch (NullPointerException e) {
                    System.out.print("NULL;\t");
                }
            }
            System.out.println();
        }
    }
}
