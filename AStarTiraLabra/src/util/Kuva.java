package util;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

/**
 * Kuvan esittämistä ja muokkaamista varten oleva luokka. Kuva vastaa
 * käytännössä aina karttaa ohjelmassa.
 *
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

    public void setKuva(BufferedImage bufferoituKuva, int skaalattuKorkeus, int skaalattuLeveys) {
        this.bufferoituKuva = bufferoituKuva;
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

    /**
     * Muuttaa kuvan RPG-esitykseen
     * @param image kartta kuvana
     */
    
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

}
