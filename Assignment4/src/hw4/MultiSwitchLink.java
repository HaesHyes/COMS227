package hw4;
import api.Point;
import api.PointPair;
/**
 * Models a link with a minimum of 2 to a maximum of 6 paths.
 * @author Haesung M. Lee
 */
public class MultiSwitchLink extends AbstractLink {
    /**
     * Represents if the train is currently crossing or not.
     */
    private boolean Crossing;
    /**
     * An array of point pairs representing the connections of this link.
     */
   private final PointPair[] Connections;
    /**
     * Constructs a new MultiSwitchLink with the given connections array, sets the crossing state to false.
     * @param connections Array of point pairs representing the connections
     */
    public MultiSwitchLink(PointPair[] connections) {
        Crossing = false;
        Connections = connections;
    }
    /**
     * Switches the connection of the link at the specified index if the crossing is set to false..
     * @param pointPair Point pair to switch the connection to
     * @param index Index of the connection to switch
     */
    public void switchConnection(PointPair pointPair, int index) {
      if (!Crossing) Connections[index] = pointPair;
    }
    /**
     * Gets the connected point of the given point in this link.
     * @param point Point to get the connected point for
     * @return Connected point for the given point, null if the given point is not part of this link's connections
     */
    @Override
    public Point getConnectedPoint(Point point) {
        for (PointPair ConnectionsTemp : Connections) {
            if (point ==  ConnectionsTemp.getPointA()) {
                return ConnectionsTemp.getPointB();
            }
            if (point ==  ConnectionsTemp.getPointB()) {
                return ConnectionsTemp.getPointA();
            }
        }
        return null;
    }
    /**
     * Sets the state of the crossing to true when a train enters the crossing.
     */
    @Override
    public void trainEnteredCrossing() {
        Crossing = true;
    }
    /**
     * Sets the state of the crossing to false when a train exits the crossing.
     */
    @Override
    public void trainExitedCrossing() {
        Crossing = false;
    }
    /**
     * Gets the number of paths of this link.
     * @return Number of paths of this link
     */
    @Override
    public int getNumPaths() {
        return Connections.length * 2;
    }

}


