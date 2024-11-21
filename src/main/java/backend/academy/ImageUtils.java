package backend.academy;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;

public final class ImageUtils {
    private ImageUtils() {

    }

    public static void save(FractalImage image, ImageFormat format, Path filename) {
        int width = image.width();
        int height = image.height();

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pixel pixel = image.pixel(x, y);
                bufferedImage.setRGB(x, y, pixel.getRGB());
            }
        }
        try {
            ImageIO.write(bufferedImage, format.name(), filename.toFile());
        } catch (IOException e) {
            System.err.println("Oops!");
        }
    }
}
