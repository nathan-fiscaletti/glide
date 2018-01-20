package glide.engine.graphics;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.RescaleOp;

public class ImageFunctions {
	public static BufferedImage lighten(BufferedImage img) {
		BufferedImage ret = img;
	    for (int x = 0; x < ret.getWidth(); x++) {
	        for (int y = 0; y < ret.getHeight(); y++) {

	            Color color = new Color(ret.getRGB(x, y));
	            
	            Color brighter = color.brighter();

	            ret.setRGB(x, y, brighter.getRGB());
	        }
	    }
	    return ret;
	}
	
	public static BufferedImage darken(BufferedImage img){
		BufferedImage ret = img;
		RescaleOp op = new RescaleOp(.9f, 0, null);
	    ret = op.filter(ret, null);
	    return ret;
	}
	
	public static BufferedImage setHue(BufferedImage img, float hue) {
		BufferedImage ret = img;
	    for (int x = 0; x < ret.getWidth(); x++) {
	        for (int y = 0; y < ret.getHeight(); y++) {

	            Color color = new Color(ret.getRGB(x, y));

	            float[] hsb = new float[3];
	            Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);

	            color = new Color(Color.HSBtoRGB(hue, hsb[1], hsb[2]));

	            ret.setRGB(x, y, color.getRGB());
	        }
	    }
	    return ret;
	}
	
	
	public static BufferedImage grayScale(BufferedImage img){
		BufferedImage ret = img;
		ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
		ColorConvertOp op = new ColorConvertOp(cs, null);
		ret = op.filter(ret, null);
		return ret;
	}
	
	public static BufferedImage setOpacity(BufferedImage img, float opacity){
		Graphics2D g = img.createGraphics();
	    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
	    g.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), null);
	    g.dispose();
	    return img;
	}
}
