package Main;

public class Pixel {
    //also not used as fb is rgb int
    //stores a format as follows:
    //A, R, G, B
    int a;
    int r;
    int g;
    int b;

    public Pixel(int a, int r, int g, int b) {
        this.a = a;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    //always size 4
    public int[] toArray() {
        return new int[] {this.a, this.r, this.g, this.b};
    }

    public int toInt() {
        return ColorConvert.RGBtoInt(this);
    }
}
