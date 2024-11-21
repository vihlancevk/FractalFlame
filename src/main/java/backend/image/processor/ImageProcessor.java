package backend.image.processor;

import backend.academy.FractalImage;

@FunctionalInterface
public interface ImageProcessor {
    void process(FractalImage image);
}
