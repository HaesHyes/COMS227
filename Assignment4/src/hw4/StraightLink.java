package hw4;
import api.Point;
/**
 * Models a fixed link with three paths.
 * @author Haesung M. Lee
 */
public class StraightLink extends AbstractLink {
    /**
     * First Point
     */
    private final Point EndpointA;
    /**
     * Second Point
     */
    private final Point EndpointB;
    //Never needed to use?
    //private Point EndpointC;
    /**
     * Constructs a StraightLink with the given endpoints A and B.
     * @param endpointA One endpoint of the link.
     * @param endpointB The other endpoint of the link.
     */
    public StraightLink(Point endpointA, Point endpointB, Point endpointC) {
        EndpointA = endpointA;
        EndpointB = endpointB;
    }
    /**
     * Given a point in the link, returns the other endpoint.
     * @param point The given point.
     * @return The other endpoint.
     */
    @Override
    public Point getConnectedPoint(Point point) {
        return point == EndpointA?EndpointB:EndpointA;
    }
    /**
     * Returns the number of paths available in the link, which is always three.
     * @return The number of paths available in the link.
     */
    @Override
    public int getNumPaths() {
        return 3;
    }
}
