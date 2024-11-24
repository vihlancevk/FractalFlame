package backend.academy.transformation.implementation;

import backend.academy.Point;
import backend.academy.transformation.Transformation;

public class Polar implements Transformation {
    @Override
    public Point apply(Point point) {
        return new Point(calculateX(point), calculateY(point));
    }

    private double calculateX(Point point) {
        double theta = Math.atan(point.x() / point.y());
        return theta / Math.PI;
    }

    private double calculateY(Point point) {
        double r = Math.sqrt(point.x() * point.x() + point.y() * point.y());
        return r - 1;
    }
}
