package hw4;
import api.Point;

/**
 * Models a switchable link with three paths.
 * @author Haesung M. Lee
 */
public class SwitchLink extends AbstractLink {
    /**
     * If the track switches or not
     */
    private boolean Switch;
    /**
     * First endpoint
     */
    private final Point EndpointA;
    /**
     * Second endpoint
     */
    private final Point EndpointB;
    /**
     * Third endpoint
     */
    private final Point EndpointC;
    /**
     * Constructs a new SwitchLink with three endpoints.
     *
     * @param endpointA First endpoint
     * @param endpointB Second endpoint
     * @param endpointC Third endpoint, which is connected to the other two endpoints
     */
    public SwitchLink(Point endpointA, Point endpointB, Point endpointC) {
        EndpointA = endpointA;
        EndpointB = endpointB;
        EndpointC = endpointC;
    }
    /**
     * Sets the switch to be in the specified position.
     * @param Switcher Sets it to change or not
     */
    public void setTurn(boolean Switcher) {Switch = Switcher;
    }
    /**
     * Returns the connected point of the specified endpoint, based on the current switch position.
     *
     * @param point the specified endpoint
     * @return Connected point of the specified endpoint, based on the current switch position
     */
    @Override
    public Point getConnectedPoint(Point point) {
        return (!Switch?(point ==  EndpointA?EndpointB:EndpointA):(point == EndpointA?EndpointC:EndpointC));
    }

}
