package backend.academy;

import java.util.Random;

public record Rect(double x, double y, double width, double height) {
    public Point getRandomPoint(Random random) {
        double randomX = x + random.nextDouble(width);
        double randomY = y + random.nextDouble(height);
        return new Point(randomX, randomY);
    }
}
