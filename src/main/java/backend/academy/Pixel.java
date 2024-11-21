package backend.academy;

import java.util.Objects;

public class Pixel {
    private double r;
    private double g;
    private double b;
    private double a;
    private int hitCount;

    public Pixel(double r, double g, double b, int hitCount) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = 1;
        this.hitCount = hitCount;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public int getHitCount() {
        return hitCount;
    }

    public void setHitCount(int hitCount) {
        this.hitCount = hitCount;
    }

    @SuppressWarnings("MagicNumber")
    public int getRGB() {
        return  scale(r) << 16 | scale(g) << 8 | scale(b);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pixel pixel)) {
            return false;
        }

        if (Double.compare(r, pixel.r) != 0) {
            return false;
        }

        if (Double.compare(g, pixel.g) != 0) {
            return false;
        }

        if (Double.compare(b, pixel.b) != 0) {
            return false;
        }

        if (Double.compare(a, pixel.a) != 0) {
            return false;
        }

        return hitCount == pixel.hitCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(r, g, b, a, hitCount);
    }

    @SuppressWarnings("MagicNumber")
    private int scale(double value) {
        return (int) (255 * value);
    }
}
