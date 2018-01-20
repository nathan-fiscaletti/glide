package two.d.engine.graphics;

import java.awt.image.BufferedImage;

public class SpriteSheet {
	
	private BufferedImage baseImage;
	
	/**
	 * The width of each column.
	 */
	private int colWidth;
	
	/**
	 * The height of each row.
	 */
	private int rowHeight;
	
	/**
	 * Create a SpriteSheet from a BufferedImage.
	 * @param ss
	 */
	public SpriteSheet(BufferedImage baseImage, int colWidth, int rowHeight){
		this.baseImage = baseImage;
		this.colWidth = colWidth;
		this.rowHeight = rowHeight;
	}
	
	/**
	 * Pull a Sprite from the SpriteSheet.
	 * 
	 * @param col
	 * @param row
	 * @param width
	 * @param height
	 * @return
	 */
	public BufferedImage grabSprite(int col, int row, int width, int height){
		return this.baseImage.getSubimage (
				( col-1 ) * this.colWidth, 
				( row-1 ) * this.rowHeight, 
				width, 
				height
		);
	}
	
}
