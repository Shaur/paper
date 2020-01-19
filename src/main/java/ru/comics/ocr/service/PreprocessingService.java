package ru.comics.ocr.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

@Service
@Slf4j
public class PreprocessingService {

    private static final int CONTROL_PIX = 25;
    
    public BufferedImage process(Path imagePath) {
        BufferedImage image;
        try {
             image = ImageIO.read(imagePath.toFile());
        } catch (IOException ex) {
            log.error("Can't load image", ex);
            return null;
        }
        
        var controlImage = deepCopy(image);
        var imageMask = createMask(image);
        
        return applyMask(controlImage, imageMask);
    }
    
    private BufferedImage createMask(BufferedImage img) {
        for (int y = 0; y < img.getHeight() - CONTROL_PIX; y++) {
            boolean isWhite = false;
            for (int x = 1; x < img.getWidth(); x++) {
                var current = img.getRGB(x, y);
                if(current == Color.WHITE.getRGB() && !isWhite)
                    isWhite = true;

                if(isWhite) {
                    if(x + CONTROL_PIX < img.getWidth()) {
                        int h = CONTROL_PIX;
                        if(y + CONTROL_PIX > img.getHeight())
                            h = img.getHeight() - y;

                        var rgb = img.getRGB(x, y, CONTROL_PIX, h, null, 0, CONTROL_PIX);
                        isWhite = hasWhite(rgb);
                    }
                }

                if(isWhite)
                    img.setRGB(x, y, Color.WHITE.getRGB());
            }
        }
        
        return img;
    }
    
    private BufferedImage applyMask(BufferedImage img, BufferedImage mask) {
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 1; x < img.getWidth(); x++){
                var maskRGB = mask.getRGB(x, y);
                if(maskRGB != Color.WHITE.getRGB())
                    img.setRGB(x, y, Color.BLACK.getRGB());
            }
        }
        
        return img;
    }

    private boolean hasWhite(int[] pixelsColor) {
        return Arrays.stream(pixelsColor).anyMatch(pc -> pc == Color.WHITE.getRGB());
    }

    private BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
}
