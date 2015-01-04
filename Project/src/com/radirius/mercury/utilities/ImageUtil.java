package com.radirius.mercury.utilities;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import com.radirius.mercury.math.MathUtil;

/**
 * A utility for modifying BufferedImages. Can be used to modify source images
 * of textures.
 *
 * @author wessles
 * @author Sri Harsha Chilakapati
 */
public class ImageUtil {
	public static BufferedImage flip(BufferedImage image, boolean x, boolean y) {
		// Flip the BufferedImage
		if (x || y) {
			AffineTransform tx = new AffineTransform();
			tx.scale(x ? -1 : 1, y ? -1 : 1);
			tx.translate(x ? -image.getWidth() : 0, y ? -image.getHeight() : 0);

			AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BICUBIC);
			return op.filter(image, null);
		}

		return image;
	}

	public static BufferedImage rotate(BufferedImage image, float rot) {
		// Rotate the BufferedImage
		if (rot != 0) {
			rot *= -1;
			rot -= 90;

			AffineTransform transform = new AffineTransform();
			transform.rotate(MathUtil.toRadians(rot), image.getWidth() / 2, image.getHeight() / 2);

			AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
			return op.filter(image, null);
		}

		return image;
	}
}
