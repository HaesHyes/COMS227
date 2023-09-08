package hw3;

import api.Tile;

import java.io.*;
import java.util.Scanner;

/**
 * Utility class with static methods for saving and loading game files.
 *  @author Haesung M. Lee
 */
public class GameFileUtil {
	/**
	 * Saves the current game state to a file at the given file path.
	 * <p>
	 * The format of the file is one line of game data followed by multiple lines of
	 * game grid. The first line contains the: width, height, minimum tile level,
	 * maximum tile level, and score. The grid is represented by tile levels. The
	 * conversion to tile values is 2^level, for example, 1 is 2, 2 is 4, 3 is 8, 4
	 * is 16, etc. The following is an example:
	 * 
	 * <pre>
	 * 5 8 1 4 100
	 * 1 1 2 3 1
	 * 2 3 3 1 3
	 * 3 3 1 2 2
	 * 3 1 1 3 1
	 * 2 1 3 1 2
	 * 2 1 1 3 1
	 * 4 1 3 1 1
	 * 1 3 3 3 3
	 * </pre>
	 * 
	 * @param filePath the path of the file to save
	 * @param game     the game to save
	 */
	public static void save(String filePath, ConnectGame game) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
			writer.write(((Integer) game.getGrid().getWidth()).toString());
			writer.write(" ");
			writer.write(((Integer) game.getGrid().getHeight()).toString());
			writer.write(" ");
			writer.write(((Integer) game.getMinTileLevel()).toString());
			writer.write(" ");
			writer.write(((Integer) game.getMaxTileLevel()).toString());
			writer.write(" ");
			writer.write(((Integer) (int) game.getScore()).toString());
			writer.write("\n");
			for (int i = 0; i < game.getGrid().getHeight() -1; i++) {
				for (int j = 0; j < game.getGrid().getWidth() - 1; j++) {
					writer.write(((Integer) game.getGrid().getTile(j, i).getLevel()).toString());
					writer.write(" ");
				}
				writer.write(((Integer) game.getGrid().getTile(game.getGrid().getWidth() - 1, i).getLevel()).toString());
				writer.write("\n");
			}
			for (int j =0;j< game.getGrid().getWidth()-1; j++){
				writer.write(((Integer) game.getGrid().getTile(j, game.getGrid().getHeight()-1).getLevel()).toString());
				writer.write(" ");

			}
			writer.write(((Integer) game.getGrid().getTile(game.getGrid().getWidth()-1, game.getGrid().getHeight()-1).getLevel()).toString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Loads the file at the given file path into the given game object.
	 * @param filePath
	 * @param game
	 */
	public static void	load(String filePath, ConnectGame game) throws FileNotFoundException {
		File file = new File(filePath);
		Scanner scnr = new Scanner(file);
		int width = scnr.nextInt();
		int height = scnr.nextInt();
		game.setMinTileLevel(scnr.nextInt());
		game.setMaxTileLevel(scnr.nextInt());
		game.setScore(scnr.nextLong());

		Grid Grids = new Grid(width,height);
		Tile tile;
		for (int j = 0; j < height ; j++) {
			for (int i = 0; i < width; i++) {
				tile = new Tile(scnr.nextInt());
				Grids.setTile(tile, i, j);
			}
		}
		game.setGrid(Grids);
	}
}

