package backend.academy.transformation.implementation;

import backend.academy.Point;
import backend.academy.transformation.Transformation;

public class Swirl implements Transformation {
    @Override
    public Point apply(Point point) {
        double r2 = point.x() * point.x() + point.y() * point.y();
        double x = point.x() * Math.sin(r2) - point.y() * Math.cos(r2);
        double y = point.x() * Math.cos(r2) + point.y() * Math.sin(r2);
        return new Point(x, y);
    }
}
