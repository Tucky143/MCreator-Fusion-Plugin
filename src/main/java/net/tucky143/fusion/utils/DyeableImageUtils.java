//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.tucky143.fusion.utils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import javax.annotation.Nullable;
import net.mcreator.util.image.ImageUtils;

public class DyeableImageUtils {
    public static BufferedImage createDyeableImage(Image layer1, @Nullable Image layer2, @Nullable Color color) {
        return createDyeableImage(layer1, layer2, color, 32);
    }

    public static BufferedImage createDyeableImage(Image layer1, @Nullable Image layer2, @Nullable Color color, int size) {
        BufferedImage base;
        if (size == -1) {
            base = ImageUtils.toBufferedImage(layer1);
        } else {
            base = ImageUtils.resizeAndCrop(layer1, size);
        }

        BufferedImage image$res = new BufferedImage(base.getWidth(), base.getHeight(), 2);
        Graphics2D g2d = image$res.createGraphics();
        g2d.drawImage(base, 0, 0, (ImageObserver)null);
        if (color != null) {
            g2d.setColor(color);
            g2d.setComposite(AlphaComposite.getInstance(10, 0.5F));

            for(int x = 0; x < image$res.getWidth(); ++x) {
                for(int y = 0; y < image$res.getHeight(); ++y) {
                    int pixel = image$res.getRGB(x, y);
                    int alpha = pixel >> 24 & 255;
                    if (alpha != 0) {
                        g2d.fillRect(x, y, 1, 1);
                    }
                }
            }
        }

        if (layer2 != null) {
            BufferedImage overlay = ImageUtils.resizeAndCrop(layer2, 32);
            g2d.setComposite(AlphaComposite.getInstance(3));
            g2d.drawImage(overlay, 0, 0, (ImageObserver)null);
        }

        g2d.dispose();
        return image$res;
    }
}
