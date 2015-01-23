package util;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

/**
 * *Kesken R G B 255 255 255 = valkoinen 254 0 0 = punainen 0 0 0 = musta 0 255
 * 1 = vihreä
 *
 * @author kristianw
 */
public class Kuva {

    private Image kuva;
    private BufferedImage bufferoituKuva;
    private int[][] rgb;

    public Kuva(BufferedImage bufKuva, int skaalattuKorkeus, int skaalattuLeveys) {
        this.bufferoituKuva = bufKuva;
        this.kuva = bufferoituKuva.getScaledInstance(skaalattuLeveys, skaalattuKorkeus, Image.SCALE_SMOOTH);

    }

    public Image getKuva() {
        return kuva;
    }

    public BufferedImage getBufferoituKuva() {
        return bufferoituKuva;
    }

    public int getKorkeus() {
        return bufferoituKuva.getHeight();
    }

    public int getLeveys() {
        return bufferoituKuva.getWidth();
    }

    public int[][] getRGPArvot() {
        return rgb;
    }

    public void printPixelARGB(int pixel) {
        int alpha = (pixel >> 24) & 0xff;
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
        System.out.println("argb: " + alpha + ", " + red + ", " + green + ", " + blue);
    }

    public void marchThroughImage(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        System.out.println("Analysing...");
        System.out.println("width, height: " + w + ", " + h);

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                System.out.println("x,y: " + j + ", " + i);
                int pixel = image.getRGB(j, i);
                printPixelARGB(pixel);
                System.out.println("");
            }
        }
    }

    public void convertTo2DWithoutUsingGetRGB(BufferedImage image) {

        final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        final int width = image.getWidth();
        final int height = image.getHeight();
        final boolean hasAlphaChannel = image.getAlphaRaster() != null;

        int[][] result = new int[height][width];
        if (hasAlphaChannel) {
            final int pixelLength = 4;
            for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
                int argb = 0;
                argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
                argb += ((int) pixels[pixel + 1] & 0xff); // blue
                argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
                result[row][col] = argb;
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        } else {
            final int pixelLength = 3;
            for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
                int argb = 0;
                argb += -16777216; // 255 alpha
                argb += ((int) pixels[pixel] & 0xff); // blue
                argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
                result[row][col] = argb;
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        }

        this.rgb = result;
    }

    public int getRed(int rgb) {
        return (rgb >> 16) & 0x000000FF;
    }

    public int getGreen(int rgb) {
        return (rgb >> 8) & 0x000000FF;
    }

    public int getBlue(int rgb) {
        return (rgb) & 0x000000FF;
    }

}
