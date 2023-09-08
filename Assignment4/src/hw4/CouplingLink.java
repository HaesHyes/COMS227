package hw4;
import api.Point;
/**
Models a link between two paths, when a train reaches the end it passes to the endpoint of the other path.
 @author Haesung M. Lee
 */
public class CouplingLink extends AbstractLink {
    /**
     * The first endpoint.
     */
    private final Point EndpointOne;
    /**
     * The first endpoint.
     */
    private final Point EndPointTwo;
    /**
     * Constructs a new CouplingLink with the inputted endpoints.
     * @param endpoint1 The first endpoint
     * @param endpoint2 The second endpoint
     */
    public CouplingLink(Point endpoint1, Point endpoint2) {
        EndpointOne = endpoint1;
        EndPointTwo = endpoint2;
    }
    /**
     * Returns the endpoint that is connected to the given point.
     * @param point The point used to find the connected point
     * @return Endpoint connected to the inputted point
     */
    @Override
    public Point getConnectedPoint(Point point) {
        return point.equals(EndPointTwo)?EndpointOne:EndPointTwo;
    }
    /**
     * Returns the number of paths,
     * there are always two possible paths.
     * @return Number of paths
     */
    public int getNumPaths() {
        return 2;
    }
}
