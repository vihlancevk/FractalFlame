package backend.academy.transformation.implementation;

import backend.academy.Point;
import backend.academy.transformation.Transformation;
import java.util.List;

@SuppressWarnings({"MultipleVariableDeclarations", "MagicNumber"})
public class Affine implements Transformation {
    private final double a, b, c;
    private final double d, e, f;
    private final double rgbR, rgbG, rgbB;

    public Affine(List<Double> matrix) {
        this.a = matrix.get(0);
        this.b = matrix.get(1);
        this.c = matrix.get(2);

        this.d = matrix.get(3);
        this.e = matrix.get(4);
        this.f = matrix.get(5);

        this.rgbR = matrix.get(6);
        this.rgbG = matrix.get(7);
        this.rgbB = matrix.get(8);
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
