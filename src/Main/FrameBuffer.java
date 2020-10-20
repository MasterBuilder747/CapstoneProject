package Main;

public class FrameBuffer {

    //this is a 2D representation of a 32 bit colored pixel array
    //a, r, g, b
    private final int[][] fb;

    public FrameBuffer(int h, int w) {
        //all values are set to 0 by default
        //Red, Green, Blue, Opacity (all are 0-255, 32 bit in total)
        this.fb = new int[h][w];
    }

    public void writePixel(int x, int y, int p) {
        //this writes the doubles into the array
        this.fb[x][y] = p;
    }

    public int readPixel(int x, int y) {
        //this returns the double version of rgb
        return this.fb[x][y];
    }

    public int length() {
        return this.fb.length;
    }
    public int length(int i) {
        return this.fb[i].length;
    }

    public int[][] returnFB() {
        return this.fb;
    }

    //this overrides all of the pixels with this one color
    public void setFill(int p) {
        for (int i = 0; i < this.fb.length; i++) {
            for (int j = 0; j < this.fb[0].length; j++) {
                this.writePixel(i, j, p);
            }
        }
    }

    //check if the exact color in the parm is equal to the specified pixel color in this fb
    //this excludes opacity for now
//    public boolean compareColor(int x, int y, Color c) {
//        //this compares the double values but uses ints for the actual comparison
//        return (int)this.fb[x][y][0] * 255 == (int)c.getRed() * 255 && (int)this.fb[x][y][1] * 255 == (int)c.getGreen() * 255 && (int)this.fb[x][y][2] * 255 == (int)c.getBlue() * 255 && this.fb[x][y][3] == c.getOpacity();
//    }

//    public void print() {
//        for (int i = 0; i < this.fb.length; ++i) {
//            for (int j = 0; j < this.fb[0].length; ++j) {
//                try {
//                    int out = this.readPixel(i, j);
//                    System.out.print("<" + c.getRed() + ", " + c.getGreen() + ", " + c.getBlue() + ", " + c.getOpacity() + ">;\t");
//                } catch (NullPointerException e) {
//                    System.out.print("NULL;\t");
//                }
//            }
//            System.out.println();
//        }
//    }
}
