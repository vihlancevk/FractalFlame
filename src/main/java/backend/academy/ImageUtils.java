package backend.academy;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

public final class ImageUtils {
    private ImageUtils() {

    }

    public static void save(FractalImage image, ImageFormat format, Path filename) {
        int width = image.width();
        int height = image.height();

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);

        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                Pixel pixel = image.pixel(x, y);
                int rgb = (int) (255 * pixel.getR()) << 16 | (int) (255 * pixel.getG()) << 8 | (int) (255 * pixel.getB());
                bufferedImage.setRGB(x, y, rgb);
            }
        }
        try {
            ImageIO.write(bufferedImage, format.name(), filename.toFile());
        } catch (IOException e) {
            System.err.println("Oops!");
        }
    }
}
