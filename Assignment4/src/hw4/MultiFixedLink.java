package hw4;
import api.Point;
import api.PointPair;
/**
 * Models a link with a minimum of 2 to a maximum of 6 paths.
 * @author Haesung M. Lee
 */
public class MultiFixedLink extends AbstractLink{
    /**
     * Array of the PointPairs, shows the connections between endpoints
     */
    private final PointPair[] Connections;
    /**
     * Constructs a new MultiFixedLink with the given connections.
     * @param connections Array of PointPairs representing the connections between endpoints
     */
    public MultiFixedLink(PointPair[] connections) {
        Connections = connections;
    }
    /**
     * Returns the endpoint connected to the given point.
     * @param point Point to find the connected endpoint
     * @return Endpoint connected to the given point
     */
    @Override
    public Point getConnectedPoint(Point point) {
        Point Temp = null;
        for (PointPair ConnectionsTemp : Connections) {
            if (point ==  ConnectionsTemp.getPointA()) {
                Temp = ConnectionsTemp.getPointB();
            }
            if (point ==  ConnectionsTemp.getPointB()) {
                Temp = ConnectionsTemp.getPointA();
            }
        }
        return Temp ;
    }
}
