package backend.academy.transformation.implementation;

import backend.academy.Point;
import backend.academy.transformation.Transformation;

public class Horseshoe implements Transformation {
    @Override
    public Point apply(Point point) {
        double r = Math.sqrt(point.x() * point.x() + point.y() * point.y());
        double x = (point.x() - point.y()) * (point.x() + point.y()) / r;
        double y = 2 * point.x() * point.y() / r;
        return new Point(x, y);
    }
}
