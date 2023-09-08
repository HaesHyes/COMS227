package Testers;

import java.util.Arrays;
import java.util.Random;

import api.Tile;
import hw3.ConnectGame;
import hw3.Grid;
import org.junit.Assert;
import org.junit.Test;
import speccheck.SpecCheckTest;
import ui.GameConsole;

/**
 * Examples of using the ConnectGame, GameFileUtil, and Grid classes. The main()
 * method in this class only displays to the console. For the full game GUI, run
 * the ui.GameMain class.
 */
public class GridTester {
    public static void main(String args[]) {
        // Example use of Grid
         {
            ConnectGame game = new ConnectGame(5, 6, 2, 6, new Random(0L));
            game.radomizeTiles();
            Grid grid = game.getGrid();
            Tile t1 = new Tile(6);
            Tile t2 = new Tile(6);
            Tile t3 = new Tile(7);
            grid.setTile(t1, 1, 2);
            grid.setTile(t2, 2, 2);
            grid.setTile(t3, 3, 2);
            game.tryFirstSelect(1, 2);
            game.tryFinishSelection(1, 2);
            Assert.assertFalse(t1.isSelected());
        }
    }
}
