package backend.image.processor.implementation;

import backend.academy.FractalImage;
import backend.academy.Pixel;
import backend.image.processor.ImageProcessor;

public class LogarithmicGammaCorrection implements ImageProcessor {
    private static final double GAMMA = 2.2;

    @Override
    public void process(FractalImage image) {
        int width = image.width();
        int height = image.height();
        Pixel[] data = image.data();

        double max = correctByLogarithmicCorrection(width, height, data);
        correctByGammaCorrection(width, height, data, max);
    }

    private double correctByLogarithmicCorrection(int width, int height, Pixel[] data) {
        double max = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Pixel pixel = data[y * width + x];
                if (pixel.getHitCount() == 0) {
                    continue;
                }

                pixel.setA(Math.log10(pixel.getHitCount()));
                if (pixel.getA() > max) {
                    max = pixel.getA();
                }
            }
        }
        return max;
    }

    private void correctByGammaCorrection(int width, int height, Pixel[] data, double max) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Pixel pixel = data[y * width + x];

                pixel.setA(pixel.getA() / max);
                pixel.setR(pixel.getR() * Math.pow(pixel.getA(), 1.0 / GAMMA));
                pixel.setG(pixel.getG() * Math.pow(pixel.getA(), 1.0 / GAMMA));
                pixel.setB(pixel.getB() * Math.pow(pixel.getA(), 1.0 / GAMMA));
            }
        }
    }
}
