package backend.academy.transformation.implementation;

import backend.academy.Point;
import backend.academy.transformation.Transformation;

@SuppressWarnings("MultipleVariableDeclarations")
public class Affine implements Transformation {
    private final double a, b, c;
    private final double d, e, f;
    private final double rgbR, rgbG, rgbB;

    @SuppressWarnings("ParameterNumber")
    public Affine(
        double a, double b, double c,
        double d, double e, double f,
        double rgbR, double rgbG, double rgbB) {
        this.a = a;
        this.b = b;
        this.c = c;

        this.d = d;
        this.e = e;
        this.f = f;

        this.rgbR = rgbR;
        this.rgbG = rgbG;
        this.rgbB = rgbB;
    }

    @Override
    public Point apply(Point point) {
        double x = a * point.x() + b * point.y() + c;
        double y = d * point.x() + e * point.y() + f;
        return new Point(x, y);
    }

    public double getRgbR() {
        return rgbR;
    }

    public double getRgbG() {
        return rgbG;
    }

    public double getRgbB() {
        return rgbB;
    }
}
