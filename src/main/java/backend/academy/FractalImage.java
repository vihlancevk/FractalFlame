package backend.academy;

public record FractalImage(Pixel[] data, int width, int height) {
    public static FractalImage create(int width, int height) {
        Pixel[] data = new Pixel[height * width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                data[y * width + x] = new Pixel(0, 0, 0, 0);
            }
        }

        return new FractalImage(data, width, height);
    }

    public boolean contains(double x, double y) {
        return isBelongToSegmentFromMinusOneToOne(x) && isBelongToSegmentFromMinusOneToOne(y);
    }

    /**
     * Get pixel by int coordinates.
     *
     * @param x [0, width - 1]
     * @param y [0, height - 1]
     * @return pixel
     */
    public Pixel pixel(int x, int y) {
        return data[y * width + x];
    }

    /**
     * Get pixel by double coordinates.
     *
     * @param x [-1, 1]
     * @param y [-1, 1]
     * @return pixel
     */
    public Pixel pixel(double x, double y) {
        return pixel(scale(x, width), scale(y, height));
    }

    private boolean isBelongToSegmentFromMinusOneToOne(double value) {
        return -1 <= value && value <= 1;
    }

    private int scale(double value, int dimension) {
        int scaledValue = (int) Math.round((value + 1) * dimension / 2);
        return scaledValue != dimension ? scaledValue : scaledValue - 1;
    }
}
