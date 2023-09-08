package hw3;

import api.ScoreUpdateListener;
import api.ShowDialogListener;
import api.Tile;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

/**
 * Class that models a game.
 * @author Haesung M. Lee
 *
 *
 */
public class ConnectGame {
	private ShowDialogListener dialogListener;
	private ScoreUpdateListener scoreListener;
	/**
	 * Tracks the width of the grid
	 */
	private int Width;
	/**
	 * Tracks the Height of the grid
	 */
	private int Height;
	/**
	 * Tracks the minimum of the level
	 */
	private int Min;
	/**
	 * Tracks the maximum of the level
	 */
	private int Max;
	/**
	 * The grid itself
	 */
	private Grid Set;
	/**
	 * The random seed
	 */
	private java.util.Random Random;
	/**
	 * Array of all the selected tiles
	 */
	private Tile[] Selected;
	/**
	 * Keeps the previous X value for checking if adjacent
	 */
	private int FirstSelectX = 0;
	/**
	 * Keeps the previous Y Value for checking if adjacent
	 */
	private int FirstSelectY = 0;
	/**
	 * Keeps track of if this is the first selection the user has made
	 */
	private boolean FirstSelection = true;
	/**
	 * Initializing the score
	 */
	private long Score = 0;

	/**
	 * Constructs a new ConnectGame object with given grid dimensions and minimum and maximum tile levels.
	 * @param width
	 * @param height
	 * @param min
	 * @param max
	 * @param rand
	 */

	public ConnectGame(int width, int height, int min, int max, Random rand) {
		Width = width;
		Height = height;
		Min = min;
		Max = max;
		Random = rand;
		Set = new Grid(Width,Height);
	}

	/**
	 * Removes all tiles of a particular level from the grid.
	 * @param level
	 */
	public void dropLevel(int level){
		for (int i = 0; i < Width-1; i++){
			for (int j = 0; j < Height-1; j ++){
				Tile temp = Set.getTile(i,j);
				if (temp.getLevel() == level){
					temp.setLevel(level-1);
				}
			}
		}
	}

	/**
	 * Removes all selected tiles from the grid.
	 */
	public void dropSelected(){
		for(int i  = 0; i< Selected.length; i++){
			Set.getTile(Selected[i].getX(),Selected[i].getY()).setSelect(false);
			Set.getTile(Selected[i].getX(),Selected[i].getY()).setLevel(Set.getTile(Selected[i].getX(),Selected[i].getY()).getLevel()-1);

		}
		Selected = new Tile[0];

	}

	/**
	 * Gets the game grid.
	 * @return
	 */
	public Grid getGrid(){
		return Set;
	}

	/**
	 * Gets the maximum tile level.
	 * @return
	 */
	public int getMaxTileLevel(){
		return Max;
	}

	/**
	 * Gets the minimum tile level.
	 * @return
	 */
	public int getMinTileLevel(){
		return Min;
	}

	/**
	 * Gets a random tile with level between minimum tile level
	 * inclusive and maximum tile level exclusive.
	 * @return
	 */
	public Tile getRandomTile(){
		return new Tile(Random.nextInt(Max-Min)+Min);
	}

	/**
	 * Gets the player's score.
	 * @return
	 */
	public long getScore(){
		return Score;
	}

	/**
	 * Gets the selected tiles in the form of an array.
	 * @return
	 */
	public Tile[] getSelectedAsArray(){

		return Selected;
	}

	/**
	 * Helper which puts the selected into the selection array
	 * @param x Tile's X coordinate
	 * @param y Tile's Y coordinate
	 */
	private void SelectionIntoArray(int x, int y) {
		Tile[] temp = Selected;
		Selected = new Tile[temp.length +2];
		int i;
		for (i = 0; i < temp.length; i++){
			Selected[i] = temp[i];
		}
		Selected[i+1] = Set.getTile(x,y);
		Selected = Arrays.stream(Selected).filter(Objects::nonNull).toArray(Tile[]::new);
	}

		/**
         * Determines if two tiles are adjacent to each other.
         * @param t1
         * @param t2
         * @return
         */
	public boolean	isAdjacent(Tile t1, Tile t2){

		return Neighbors(t1.getX(), t1.getY(), t2.getX(), t2.getY());
	}

	/**
	 * Helper method which will check if the two tiles given their location are adjacent.
	 * @param x1 First Tile x
	 * @param x2 Second Tile x
	 * @param y1 First Tile y
	 * @param y1 Second Tile y
	 * @return Boolean which is true or false
	 */
	private boolean Neighbors(int x1, int y1 , int x2 , int y2){
		if(x1== x2 && y1 -1 == y2){
			return true;
		} else if(x1== x2 && y1 +1 == y2){
			return true;
		}
		else if(x1 +1 == x2 && y1 == y2){
			return true;
		}
		else if(x1 -1 == x2 && y1 == y2){
			return true;
		}
		else if(x1 -1 == x2 && y1 -1 == y2){
			return true;
		}
		else if(x1 +1 == x2 && y1 -1 == y2){
			return true;
		}
		else if(x1 -1 == x2 && y1 +1 == y2){
			return true;
		}
		else return x1 + 1 == x2 && y1 + 1 == y2;
	}


	/**
	 * Regenerates the grid with all random tiles produced by getRandomTile().
	 */
	public void radomizeTiles(){
		for (int i = 0; i < Height; i++){
			for (int j = 0; j < Width; j++){
				Set.setTile(getRandomTile(),j,i);
			}
		}
	}

	/**
	 * Sets the game's grid.
	 * @param grid
	 */
	public void setGrid(Grid grid){
		this.Set = grid;
	}
	/**
	 * Sets callback listeners for game events.
	 * 
	 * @param dialogListener listener for creating a user dialog
	 * @param scoreListener  listener for updating the player's score
	 */
	public void setListeners(ShowDialogListener dialogListener, ScoreUpdateListener scoreListener) {
		this.dialogListener = dialogListener;
		this.scoreListener = scoreListener;
	}

	/**
	 * Save the game to the given file path.
	 * 
	 * @param filePath location of file to save
	 */
	public void save(String filePath) {
		GameFileUtil.save(filePath, this);
	}

	/**
	 * Load the game from the given file path
	 * 
	 * @param filePath location of file to load
	 */
	public void load(String filePath) throws FileNotFoundException {
		GameFileUtil.load(filePath, this);
	}

	/**
	 *Sets the maximum tile level.
	 * @param maxTileLevel
	 */
	public void setMaxTileLevel(int maxTileLevel){
		Max = maxTileLevel;
	}

	/**
	 * Sets the minimum tile level.
	 * @param minTileLevel
	 */
	public void	setMinTileLevel(int minTileLevel){
		Min = minTileLevel;
	}
	/**
	 * Sets the player's score.
	 */
	public void setScore(long score){
		Score = score;
	}

	/**
	 * Indicates the user is trying to select (mouse over) a tile to add to the selected sequence of tiles.
	 * @param x Tile's X coordinate
	 * @param y Tile's Y coordinate
	 */
	public void	tryContinueSelect(int x, int y) {
		for (int i = 0; i < Selected.length; i++){
			if(Set.getTile(Selected[i].getX(),Selected[i].getY()) ==  Set.getTile(x,y)){
				Tile[] temp = Selected;
				Selected = new Tile[temp.length];
				System.arraycopy(temp, 0, Selected, 0, i + 1);
				for (int j = temp.length-1; j > i; j--){
					Set.getTile(temp[j].getX(),temp[j].getY()).setSelect(false);
				}
				Selected = Arrays.stream(Selected).filter(Objects::nonNull).toArray(Tile[]::new);
				FirstSelectX = Selected[Selected.length-1].getX();
				FirstSelectY = Selected[Selected.length-1].getY();
			}

		}if (isAdjacent(Set.getTile(x, y), Set.getTile(FirstSelectX, FirstSelectY))) {
			int sum = 0;
			sum = Set.getTile(FirstSelectX, FirstSelectY).getLevel();
			sum += Selected.length - 1;
			if ((Set.getTile(x, y).getLevel() <= sum) && (!Set.getTile(x, y).isSelected())) {
				FirstSelectX = x;
				FirstSelectY = y;
				AddToSelectList(x, y);
			}
		}
	}

	/**
	 * Indicates the user is trying to finish selecting (click on) a sequence of tiles.
	 * @param x
	 * @param y
	 * @return Boolean if selection was valid
	 */
	public boolean tryFinishSelection(int x, int y){
		int[] Temp = new int[Selected.length];
		int TempSingle = 0;
		int Count = 0;
		boolean WasSelectionValid = (Set.getTile(x,y) == Set.getTile(FirstSelectX,FirstSelectY)) && Set.getTile(x,y).isSelected();
		if (WasSelectionValid){
			if(Selected.length > 1) {
				for (int i : Temp){
					Temp[Count] = Selected[Count].getLevel();
					Count++;
				}
				for (int j : Temp) {
					if (j > 0) {
						TempSingle = ((int) (Math.pow(2, j)) + TempSingle);
					}
				}
				Score += TempSingle;
				scoreListener.updateScore(Score);
				for (int i = 0; i < Selected.length; i++) {
					upgradeLastSelectedTile();
					dropSelected();
				}
			}else{
				Set.getTile(x,y).setSelect(false);
				WasSelectionValid = true;
			}


			dropSelected();
		}
		return WasSelectionValid;
	}

	/**
	 * Indicates the user is trying to select (clicked on) a tile to start a new selection of tiles.
	 * @param x Tile's X coordinate
	 * @param y Tile's Y coordinate
	 * @return Boolean if the tile was selected
	 */
	public boolean tryFirstSelect(int x, int y) {
		boolean WasItSelected = true;
		FirstSelectX = 0;
		FirstSelectY = 0;
			if (Set.getTile(x, y).isSelected()) {
				WasItSelected = false;
			}
		if(FirstSelection) {
			if (WasItSelected) {
				Selected = new Tile[1];

				AddToSelectList(x, y);
				FirstSelectX = x;
				FirstSelectY = y;
			}
		}else{
			WasItSelected = false;
		}
		return WasItSelected;

	}
	/**
	 * Helper method to add the tiles to the selected array
	 * @param x  Tile's X coordinate
	 * @param y Tile's Y coordinate
	 */
	private void AddToSelectList(int x, int y){
		Set.getTile(x,y).setSelect(true);
		if (FirstSelection) {
			Selected = new Tile[1];
			Selected[0] = Set.getTile(x, y);
			FirstSelection = false;
		} else if (!FirstSelection){
			SelectionIntoArray(x,y);
		}
	}

	/**
	 * Remove the tile from the selected tiles.
	 * @param x
	 * @param y
	 */
	public void	unselect(int x, int y){
		int Count = 0;
		if(Set.getTile(x,y).isSelected()){
			Tile[] temp = Selected;
			Selected = new Tile[temp.length+2];
			int i;
			for (i = 0; i < temp.length-1; i++){
				if(temp[i] !=  Set.getTile(x,y)){
					Selected[Count] = temp[i];
					Count++;

				}
			}
			Selected[i+1] = Set.getTile(x,y);
			Selected = Arrays.stream(Selected).filter(Objects::nonNull).toArray(Tile[]::new);
			if(Selected.length == 1){
				FirstSelection = true;
			}
			Set.getTile(x,y).setSelect(false);
		}

	}

	/**
	 * Increases the level of the last selected tile by 1 and removes that tile from the list of selected tiles.
	 */
	public void	upgradeLastSelectedTile(){
		Selected[Selected.length-1].setLevel(Selected[Selected.length-1].getLevel()+1);
		Selected[Selected.length-1].setSelect(false);
		if (Selected[Selected.length-1].getLevel() > Max){
			do{

				Max += 1;
				Min += 1;
			} while(Selected[Selected.length-1].getLevel() != Max);
			dialogListener.showDialog(Selected[Selected.length-1].toString());

		}
	}
}
