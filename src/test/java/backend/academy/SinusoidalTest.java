package backend.academy;

import backend.academy.transformation.Transformation;
import backend.academy.transformation.implementation.Sinusoidal;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SinusoidalTest {
    private final Transformation sinusoidal = new Sinusoidal();

    @Test
    public void apply() {
        // Arrange
        double x = -0.3231;
        double y = -0.254326;
        Point point = new Point(x, y);

        // Act
        Point actual = sinusoidal.apply(point);

        // Assert
        double expectedX = Math.sin(x);
        double expectedY = Math.sin(y);
        assertThat(actual.x()).isEqualTo(expectedX);
        assertThat(actual.y()).isEqualTo(expectedY);
    }
}
