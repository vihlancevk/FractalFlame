package backend.academy;

import backend.academy.transformation.Transformation;
import backend.academy.transformation.implementation.Spherical;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SphericalTest {
    private final Transformation spherical = new Spherical();

    @Test
    public void apply() {
        // Arrange
        double x = -0.3231;
        double y = 0.326;
        Point point = new Point(x, y);

        // Act
        Point actual = spherical.apply(point);

        // Assert
        double r2 = x * x + y * y;
        double expectedX = x / r2;
        double expectedY = y / r2;
        assertThat(actual.x()).isEqualTo(expectedX);
        assertThat(actual.y()).isEqualTo(expectedY);
    }
}
