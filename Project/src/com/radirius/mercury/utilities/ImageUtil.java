package com.radirius.mercury.utilities;

import java.awt.geom.AffineTransform;
import java.awt.image.*;

/**
 * A utility for modifying BufferedImages. Can be used to modify source images
 * of textures.
 *
 * @author wessles
 * @author Sri Harsha Chilakapati
 */
public class ImageUtil {
	public static BufferedImage flip(BufferedImage image, boolean x, boolean y) {
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
		if (rot != 0) {
			rot *= -1;
			rot -= Math.PI / 2;

			AffineTransform transform = new AffineTransform();
			transform.rotate(rot, image.getWidth() / 2, image.getHeight() / 2);

			AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
			return op.filter(image, null);
		}

		return image;
	}
}
