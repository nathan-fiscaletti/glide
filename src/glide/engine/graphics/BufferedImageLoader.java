package glide.engine.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BufferedImageLoader {
	public static BufferedImage load(String path) throws IOException {
		return ImageIO.read((new BufferedImageLoader()).getClass().getResource(path));
	}
}
