package hw1;
/**
 * This program models a removable camera battery
 * @author Haesung Michael Lee
 */
public class CameraBattery {
    public static final int NUM_CHARGER_SETTINGS = 4;
    public static final double CHARGE_RATE = 2.0;
    public static final double DEFAULT_CAMERA_POWER_CONSUMPTION = 1.0;

    //My own variables
    /**This will act as the literal battery capacity, when the battery is removed only
     * CamCapacity and BattCapacity should be affected, then when the battery is inserted into the charger or camera
     * CamCapacity/BattCapacity will be set to this variable I do this because Capacity does not change at all while
     * it is running, meaning it basically is acting as a constant value while CamCapacity and BattCapacity work as
     * ways to check if a battery is in the camera or charger or neither without using an if statement
     */
    private final double Capacity;
    /** Used to set the CamCapacity to 0 or to what the global Capacity is, makes it so
     camera charging is not available when removed from the camera
     */
    private double CamCapacity;
    /** This acts as the charge for the battery, I have a distinction for this because
     *  the charge for the Camera are set to 0 depending on the method used
     */
    private double GlobalCharge;
    /** This is only important for when the battery is either added to the camera or
     removed from it, setting it from either GlobalCharge or to 0, doing so makes the cameraCharge either charge by
     the amount it's supposed to or 0 depending on the condition
     */
    private double CameraCharge;
    /** Unlike the BattCapacity I found that I could not replace the instances of this
     with a GlobalCharge, I think the reason for this is the code needs to have  way to hold onto what the previous
     charge was before the battery switch since I have switching make the percentage change to 0
     */
    private double BatteryPercentage;
    /** Charging setting selection */
    private int ChargerSetting;
    /** Total drained */
    private double TotalDrain;
    /** Because some tests required a switch
     in PowerConsumption level I had to make it a variable and have it equal to a static */
    private double PowerConsumption = DEFAULT_CAMERA_POWER_CONSUMPTION;

    // constructor
    /**
     * Makes the variables equal the inputs, sets up disconnected from both the charger and camera
     */
    public CameraBattery(double batteryStartingCharge, double batteryCapacity){

        BatteryPercentage = batteryStartingCharge;
        // Sets the global capacity
        Capacity = batteryCapacity;
        //Battery is not in the cam yet, it is not set as CamCapacity = Capacity;
        CamCapacity = 0;
        //Not in the cam yet there is no charge for it
        CameraCharge = 0;
        // A safeguard to make sure the charge is not  higher than the current capacity
        GlobalCharge = Math.min(BatteryPercentage,Capacity);
        // Sets battery to be equal to what the safeguarded amount is
        BatteryPercentage = GlobalCharge;
    }
    //public methods
    /**
     * Switches between the settings for the charger, takes in the ChargerSetting, adds one till it reaches 3, the next addition resets it to zero
     */
    public void buttonPress(){

        ChargerSetting += 1;
        ChargerSetting %= NUM_CHARGER_SETTINGS;
    }
    /**
     * Charges the battery when it is connected to the camera, does not do anything to the battery if it recognizes
     * that the battery is not connected
     * @param minutes Takes in the minutes and inputs it into the equation for camerae charging
     * @return How much the camera charged by
     */
    public double cameraCharge(double minutes){

        double CameraCharging = minutes * CHARGE_RATE;
        // A safeguard I put in place to ensure that the charge amount was capable
        double AmountAbleToCharge = CamCapacity - CameraCharge;
        double CheckIfInsideCamera = Math.min(CameraCharging,AmountAbleToCharge);
        CameraCharge += CheckIfInsideCamera;
        // Since the above has checked it is safe to assume that the Battery and the Camera Battery should be equal
        BatteryPercentage += CheckIfInsideCamera;
        // Makes sure everything is still equal to each other
        GlobalCharge = BatteryPercentage;
        // Returns the amount that the camera was charged by
        return CheckIfInsideCamera;
    }
    /**
     * Drains the battery but only if it is connected to the Camera
     * @param minutes
     * Minutes are taken in and then plugged into the drain formula
     * @return The amount drained from the battery is outputted
     */
    public double drain(double minutes){
        double DrainageAmount = minutes * PowerConsumption;
        // Ensures that the drainage amount is not a negative number
        double DrainageAmountWithinBounds = Math.max(DrainageAmount,0);
        // Makes sure the amount can actually be drained from the battery without going negative
        CameraCharge -= DrainageAmountWithinBounds = Math.min(DrainageAmountWithinBounds,CameraCharge);
        BatteryPercentage -= DrainageAmountWithinBounds;
        // Adds to total drained
        TotalDrain += DrainageAmountWithinBounds;
        // Ensuring the global charge is still equivalent to what the current charge is after this method runs
        GlobalCharge = BatteryPercentage;
        // Returning amount drained
        return DrainageAmountWithinBounds;
    }
    /**
     * Charges the battery via the external source
     * @param minutes  Plugged into the formula for external charging
     * @return Amount that was charged
     */
    public double externalCharge(double minutes){
        double OldBatteryPercent = BatteryPercentage;
        double ChargeAmount = (minutes * (double) ChargerSetting * CHARGE_RATE);
        BatteryPercentage += ChargeAmount;
        BatteryPercentage = Math.min(BatteryPercentage, Capacity);
        // Ensures the global charge is still equal to what the battery is
        GlobalCharge = BatteryPercentage;
        ChargeAmount = BatteryPercentage - OldBatteryPercent;
        // Returns amount battery was charged
        return ChargeAmount;
    }

    /**penis penis penis penis penis penis penis penis penis penis penis
     * Move the battery to the external charger. Updates any variables as needed to represent the move.
     */
    public void moveBatteryExternal(){

        BatteryPercentage = GlobalCharge;
    }
    /**
     * Move the battery to the camera. Updates any variables as needed to represent the move.
     */
    public void moveBatteryCamera()
    {

        CamCapacity = Capacity;
        CameraCharge  = GlobalCharge;
    }
    /**
     * Remove the battery from either the camera or external charger. Updates any variables as needed
     * to represent the removal.
     */
    public void removeBattery(){

        //  CameraCharge is the only one affected because the Camera is now at zero due to no longer
        //  having a battery, but the battery doesn't drain to 0 since it doesn't require an outside source
        CameraCharge = 0;
        CamCapacity = 0;
    }

    /**
     * Sets the power consumption of the camera to whatever it had been defined as, by default it is the static variable
     * @param cameraPowerConsumption What the current power consumption will be set as
     */
    public void setCameraPowerConsumption(double cameraPowerConsumption){
        PowerConsumption = cameraPowerConsumption;
    }
    /**
     * Reset the battery monitoring system by setting the total battery drain count back to 0.
     */
    public void resetBatteryMonitor(){
        TotalDrain = 0;
    }

    /**
     * Get the battery's capacity.
     * @return Returns the capacity of the current battery
     */
    public double getBatteryCapacity(){

        return Capacity;
    }
    /**
     * Get the battery's current charge.
     * @return Returns the current amount of charge within the battery
     */
    public double getBatteryCharge(){

        return BatteryPercentage;
    }
    /**
     * Get the current charge of the camera's battery.
     * @return Returns the current charge of the camera's battery assuming a battery is within the camera, otherwise returns 0
     */
    public double getCameraCharge(){

        return CameraCharge;
    }
    /**
     * Get the power consumption of the camera.
     * @return The current set power consumption level
     */
    public double getCameraPowerConsumption(){

        return PowerConsumption;
    }
    /**
     * Get the external charger setting.
     * @return The current setting of the external charger from 0-3
     */
    public int getChargerSetting(){

        return ChargerSetting;
    }
    /**
     * Get the total amount of power drained from the battery since the last time the battery monitor
     * was started or reset.
     * @return The total amount drained from the battery at the current time
     */
    public double getTotalDrain(){

        return TotalDrain;
    }
}

