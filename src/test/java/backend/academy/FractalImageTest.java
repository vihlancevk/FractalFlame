package backend.academy;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FractalImageTest {
    private final int width = 192;
    private final int height = 108;

    @Test
    public void contains() {
        // Arrange
        FractalImage fractalImage = create();
        double x = 0;
        double y = 0;

        // Act
        boolean actual = fractalImage.contains(x, y);

        // Assert
        boolean expected = true;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void pixel() {
        // Arrange
        FractalImage fractalImage = create();
        double x = 0.056;
        double y = -0.231;

        // Act
        Pixel actual = fractalImage.pixel(x, y);

        // Assert
        int realX = 101;
        int realY = 42;
        double c = 1.0 * (realY * width + realX) / (height * width);
        Pixel expected = new Pixel(c, c, c, 0);
        assertThat(actual).isEqualTo(expected);
    }

    private FractalImage create() {
        FractalImage fractalImage = FractalImage.create(width, height);
        Pixel[] data = fractalImage.data();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double c = 1.0 * (y * width + x) / (height * width);
                data[y * width + x] = new Pixel(c, c, c, 0);
            }
        }
        return fractalImage;
    }
}
