package Main;

public class FrameBuffer {

    //this is a 2D representation of a 32 bit colored pixel array
    //a, r, g, b
    private final int[][] fb;

    public FrameBuffer(int w, int h) {
        //all values are set to 0 by default
        //Red, Green, Blue, Opacity (all are 0-255, 32 bit in total)
        this.fb = new int[h][w];
    }
    public FrameBuffer(int[][] a) {
        //all values are set to 0 by default
        //Red, Green, Blue, Opacity (all are 0-255, 32 bit in total)
        this.fb = a;
    }

    public void writePixel(int w, int h, int p) {
        //this writes the doubles into the array
        this.fb[h][w] = p;
    }

    public int readPixel(int w, int h) {
        //this returns the double version of rgb
        return this.fb[h][w];
    }

    public int height() {
        return this.fb.length;
    }
    public int width() {
        return this.fb[0].length;
    }
    public int width(int i) {
        return this.fb[i].length;
    }

    public int[][] returnFB() {
        return this.fb;
    }

    //this overrides all of the pixels with this one color
    public void setFill(int p) {
        for (int i = 0; i < this.fb.length; i++) {
            for (int j = 0; j < this.fb[0].length; j++) {
                this.writePixel(j, i, p);
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
