package hw4;
import api.Point;
/**
 * Models a fixed link with three paths.
 * @author Haesung M. Lee
 */
public class TurnLink extends AbstractLink {
    /**
     * First Endpoint
     */
    private final Point EndpointA;
    //Endpoint B is not actually needed for this class
    //private final Point EndpointB;
    /**
     * Third Endpoint
     */
    private final Point EndpointC;
    /**
     * Constructs a TurnLink with the given three endpoints, Endpoint B is not necessary.
     * @param endpointA
     * @param endpointB
     * @param endpointC
     */
    public TurnLink(Point endpointA, Point endpointB, Point endpointC) {
        EndpointA = endpointA;
      //EndpointB = endpointB;
        EndpointC = endpointC;
    }
    /**
     * Returns the point that is connected to the given point.
     * If the given point is EndpointA, returns EndpointC.
     * If the given point is EndpointB, returns EndpointA.
     * It will always return A if it is B or C
     * @param point The given point.
     * @return The point connected to the given point.
     */
    @Override
    public Point getConnectedPoint(Point point) {
        return point ==  EndpointA?EndpointC:EndpointA;
    }
}
