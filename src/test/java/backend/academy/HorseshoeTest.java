package backend.academy;

import backend.academy.transformation.Transformation;
import backend.academy.transformation.implementation.Horseshoe;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class HorseshoeTest {
    private final Transformation horseshoe = new Horseshoe();

    @Test
    public void apply() {
        // Arrange
        double x = 1;
        double y = -0.842;
        Point point = new Point(x, y);

        // Act
        Point actual = horseshoe.apply(point);

        // Assert
        double r = Math.sqrt(x * x + y * y);
        double expectedX = (x - y) * (x + y) / r;
        double expectedY = 2 * x * y / r;
        assertThat(actual.x()).isEqualTo(expectedX);
        assertThat(actual.y()).isEqualTo(expectedY);
    }
}
