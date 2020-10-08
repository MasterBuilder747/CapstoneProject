package Main;

import java.awt.*;
import java.util.Arrays;

public class ColorConvert {
    /*
    public int getRGB(Object inData) {
    return (getAlpha(inData) << 24)
        | (getRed(inData) << 16)
        | (getGreen(inData) << 8)
        | (getBlue(inData) << 0);
    }
    */
    public static int RGBtoInt(Pixel p) {
        int a = p.a;
        int r = p.r;
        int g = p.g;
        int b = p.b;
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    public static Pixel IntToRGB(int c) {
        int a = (c >> 24) & 0xff;
        int r = (c >> 16) & 0xff;
        int g = (c >> 8) & 0xff;
        int b = (c) & 0xff;
        //this returns a hex value
        return new Pixel(a, r, g, b);
    }

    public static String toString(Pixel p) {
        int r = p.r;
        int g = p.g;
        int b = p.b;
        int o = p.a;
        return r + ", " + b + ", " + g + ", " + o;
    }

//    public static void main(String[] args) {
//        System.out.println(new Pixel(255, 255, 255, 255).toInt());
//        System.out.println(Arrays.toString(ColorConvert.IntToRGB(-1).toArray()));
//        //System.out.println((int)Long.parseLong(Color.rgb(255, 0, 0, 1.0).toString(), 16));
//    }
}
