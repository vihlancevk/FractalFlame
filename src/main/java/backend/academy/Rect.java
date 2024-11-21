package backend.academy;

import java.util.Random;

public record Rect(double x, double y, double width, double height) {
    public Point getRandomPoint(Random random) {
        double randomX = x + random.nextDouble(width);
        double randomY = y + random.nextDouble(height);
        return new Point(randomX, randomY);
    }

    public boolean contains(Point point) {
        return isInXRange(point.x()) && isInYRange(point.y());
    }

    private boolean isInXRange(double x) {
        return isInRange(this.x, this.x + width, x);
    }

    private boolean isInYRange(double y) {
        return isInRange(this.y, this.y + height, y);
    }

    private boolean isInRange(double start, double end, double value) {
        return start <= value && value <= end;
    }
}
