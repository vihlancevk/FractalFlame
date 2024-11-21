package backend.academy;

import backend.academy.transformation.Transformation;
import backend.academy.transformation.implementation.Linear;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LinearTest {
    private final Transformation linear = new Linear();

    @Test
    public void apply() {
        // Arrange
        double x = 0.183;
        double y = -0.2312;
        Point point = new Point(x, y);

        // Act
        Point actual = linear.apply(point);

        // Assert
        double expectedX = x;
        double expectedY = y;
        assertThat(actual.x()).isEqualTo(expectedX);
        assertThat(actual.y()).isEqualTo(expectedY);
    }
}
