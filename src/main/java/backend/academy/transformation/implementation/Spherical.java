package backend.academy.transformation.implementation;

import backend.academy.Point;
import backend.academy.transformation.Transformation;

public class Spherical implements Transformation {
    @Override
    public Point apply(Point point) {
        double r2 = point.x() * point.x() + point.y() * point.y();
        return new Point(point.x() / r2, point.y() / r2);
    }
}
