package hw3;

import api.Tile;

/**
 *
 * Represents the game's grid.
 * @author Haesung M. Lee
 */
public class Grid {
	private int Width;
	private int Height;
	private api.Tile[][] Arr;

	/**
	 * Creates a new grid.
	 * @param width
	 */
	public Grid(int width, int height){
		Width = width;
		Height = height;
		Arr = new Tile[Height][Width];

	}

	/**
	 * Get the grid's height.
	 * @return
	 */
	public int getHeight(){
		return Height;
	}

	/**
	 * Gets the tile for the given column and row.
	 * @param x
	 * @param y
	 * @return
	 */
	public api.Tile	getTile(int x, int y){
		return Arr[y][x];
	}

	/**
	 * Get the grid's width.
	 * @return
	 */
	public int getWidth(){
		return Width;
	}
	/**
	 * Sets the tile for the given column and row.
	 * @param tile
	 * @param x
	 * @param y
	 */
	public void	setTile(api.Tile tile, int x, int y){
	Arr[y][x]=tile;
	Arr[y][x].setLocation(x,y);
	}

	@Override
	public String toString() {
		String str = "";
		for (int y=0; y<getHeight(); y++) {
			if (y > 0) {
				str += "\n";
			}
			str += "[";
			for (int x=0; x<getWidth(); x++) {
				if (x > 0) {
					str += ",";
				}
				str += getTile(x, y);
			}
			str += "]";
		}
		return str;
	}
}
