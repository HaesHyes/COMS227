package hw4;
import api.*;
/**
 Models a link between paths.
 @author Haesung M. Lee
 */
public abstract class AbstractLink implements Crossable {
    /**
     * Constructs a new AbstractLink.
     */
    public AbstractLink() {
    }
    /**
     * Shifts the given PositionVector along the next set of points.
     * @param positionVector The train to shift
     */
    public void shiftPoints(PositionVector positionVector) {
       Point TempA = this.getConnectedPoint(positionVector.getPointB());
       Point TempB;
       TempB = (TempA.getPointIndex()== 0)?TempA.getPath().getPointByIndex(1):TempA.getPath().getPointByIndex(TempA.getPointIndex() - 1);
       positionVector.setPointA(TempA);
       positionVector.setPointB(TempB);
    }
    /**
     * Notifies that a train has entered the crossing at this link.
     * This method is intentionally left blank and should be implemented
     * by subclasses as needed.
     */
    public void trainEnteredCrossing() {
    }
    /**
     * Notifies that a train has entered the crossing at this link.
     * This method is intentionally left blank and should be implemented
     * by subclasses as needed.
     */
    public void trainExitedCrossing() {
    }
    /**
     * Returns the number of paths in this AbstractLink.
     * Subclasses should override this method to return the correct number of paths need be.
     * @return Number of paths
     */
    public int getNumPaths() {
        return 0;
    }
}