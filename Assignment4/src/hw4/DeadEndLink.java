package hw4;
import api.Point;
import api.PositionVector;
/**
 Models a link that connects a single path to nothing.
 @author Haesung M. Lee
 */
public class DeadEndLink extends AbstractLink {
    /**
     * Constructs a new DeadEndLink.
     */
    public DeadEndLink() {
    }
    /**
     *Doesn't shift the points because there is nothing to shift.
     * @param positionVector Points to be shifted
     */
    public void shiftPoints(PositionVector positionVector) {
    }
    /**
     * Returns null because it doesn't connect to any other links or paths.
     * @param point Point to find the connected endpoint (unused)
     * @return null
     */
    @Override
    public Point getConnectedPoint(Point point) {
        return null;
    }
    /**
     * Returns the number of paths.
     * Since a DeadEndLink only connects to a single endpoint it is always one.
     * @return Number of paths
     */
    @Override
    public int getNumPaths() {
        return 1;
    }
}
