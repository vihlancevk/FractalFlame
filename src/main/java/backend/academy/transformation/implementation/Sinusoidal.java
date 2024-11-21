package backend.academy.transformation.implementation;

import backend.academy.Point;
import backend.academy.transformation.Transformation;

public class Sinusoidal implements Transformation {
    @Override
    public Point apply(Point point) {
        return new Point(Math.sin(point.x()), Math.sin(point.y()));
    }
}
