package backend.academy;

import java.util.Objects;

public class Pixel {
    private double r;
    private double g;
    private double b;
    private int hitCount;

    public Pixel(double r, double g, double b, int hitCount) {
        this.r = r;
        this.g = g;
        this.b = b;
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

    public int getHitCount() {
        return hitCount;
    }

    public void setHitCount(int hitCount) {
        this.hitCount = hitCount;
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

        return hitCount == pixel.hitCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(r, g, b, hitCount);
    }
}
