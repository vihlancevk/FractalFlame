package backend.academy;

import backend.academy.transformation.Transformation;
import backend.academy.transformation.implementation.Swirl;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SwirlTest {
    private final Transformation swirl = new Swirl();

    @Test
    public void apply() {
        // Arrange
        double x = -0.323211;
        double y = 0.334226;
        Point point = new Point(x, y);

        // Act
        Point actual = swirl.apply(point);

        // Assert
        double r2 = x * x + y * y;
        double expectedX = x * Math.sin(r2) - y * Math.cos(r2);
        double expectedY = x * Math.cos(r2) + y * Math.sin(r2);
        assertThat(actual.x()).isEqualTo(expectedX);
        assertThat(actual.y()).isEqualTo(expectedY);
    }
}
