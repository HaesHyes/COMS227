public class CameraBattery {

    public static final int NUM_CHARGER_SETTINGS = 4;
    public static final double CHARGE_RATE = 2.0;
    public static final double DEFAULT_CAMERA_POWER_CONSUMPTION = 1.0;

    double BatteryPercentage;
    double Capacity;
    double CamCapacity;
    double BattCapacity;
    double CameraCharge = 0;
    double StartingBatteryCharge;
    int ChargerSetting = 0;
    //constructor
    double TotalDrain = 0;
    double PowerConsumption = DEFAULT_CAMERA_POWER_CONSUMPTION;
    public CameraBattery(double batteryStartingCharge, double batteryCapacity){
        BatteryPercentage = batteryStartingCharge;
        Capacity = batteryCapacity;
        CamCapacity = Capacity;
        BattCapacity = Capacity;
        StartingBatteryCharge = BatteryPercentage;

/*
Constructs a new camera battery simulation. The battery should start disconnected
from both the camera and the external charger. The starting battery charge and
maximum charge capacity are given. If the starting charge exceeds the batteries
capacity, the batteries charge is set to its capacity. The external charger
starts at setting 0.
 */
    }

    //public methods
    public void buttonPress(){
        /*
        Indicates the user has pressed the setting button one time on the external charger. The charge
setting increments by one or if already at the maximum setting wraps around to setting 0.
         */
        ChargerSetting += 1;
       ChargerSetting = ChargerSetting % NUM_CHARGER_SETTINGS;

    }

    public double cameraCharge(double minutes){
        double CameraCharging = minutes * CHARGE_RATE;
        double CheckIfInsideCamera = Math.min(CameraCharging,CamCapacity);

        CameraCharge = CameraCharge + CheckIfInsideCamera;
       BatteryPercentage = BatteryPercentage + CheckIfInsideCamera;
        return CameraCharging;

        /*
        Charges the battery connected to the camera (assuming it is connected) for a given number of
minutes. The amount of charging in milliamp-hours (mAh) is the number minutes times the
CHARGE_RATE constant. However, charging cannot exceed the capacity of the battery
connected to the camera, or no charging if the battery is not connected. The method returns the
actual amount the battery has been charged.
         */
        //JUST SO IT COMPILES CHANGE THE RETURN LATER
    }

    public double drain(double minutes){
        /*
        Drains the battery connected to the camera (assuming it is connected) for a given number of
minutes. The amount of drain in milliamp-hours (mAh) is the number of minutes times the
cameras power consumption. However, the amount cannot exceed the amount of charge
contained in the battery assuming it is connected to the camera, or no amount drain if the battery
is not connected. The method returns the actual amount drain from the battery
         */
        double DrainageAmount = minutes * DEFAULT_CAMERA_POWER_CONSUMPTION;
                //NEED TO MAKE IT SO IT CANNOT GO BELOW ZERO USING MATH. WHATEER
        double DrainInBounds = Math.min(DrainageAmount,CameraCharge);
        double DrainInBoundsOfCapacity = Math.min(DrainInBounds,CamCapacity);
        BatteryPercentage = BatteryPercentage - DrainInBoundsOfCapacity;
        CameraCharge = Math.min(BatteryPercentage,CamCapacity);
        TotalDrain += DrainInBounds;
       return DrainInBoundsOfCapacity;
    }

    public double externalCharge(double minutes){

        /*Charges the battery connected to the external charger (assuming it is connected) for a given
number of minutes. The amount of charging in milliamp-hours (mAh) is the number minutes
times the charger setting (a number between 0 inclusive and NUM_CHARGER_SETTINGS
exclusive) the CHARGE_RATE constant. However, charging cannot exceed the capacity of the
battery connected to the camera, or no charging if the battery is not connected. The method
returns the actual amount the battery has been charged.
         */
        double ChargeAmount = (minutes * (double) ChargerSetting * CHARGE_RATE);
        BatteryPercentage += Math.min(ChargeAmount, BattCapacity);
        return ChargeAmount; // JUST SO IT COMPILES
    }

    public void resetBatteryMonitor(){
        /*
        Reset the battery monitoring system by setting the total battery drain count back to 0.
         */
        TotalDrain = 0;

    }
    public double getBatteryCapacity(){
        /*
        Get the battery's capacity.
         */
        return BattCapacity;
    }

    public double getBatteryCharge(){
        /*
        Get the battery's current charge.
         */
        return BatteryPercentage; // same thing as above
    }

    public double getCameraCharge(){
    /*
    Get the current charge of the camera's battery.
     */
        return CameraCharge; // same thing as above
    }

    public double getCameraPowerConsumption(){
        /*Get the power consumption of the camera.
         */
        return PowerConsumption; //same
    }

    public int getChargerSetting(){
        /*
        Get the external charger setting.
         */
        return ChargerSetting;
    }

    public double getTotalDrain(){
        /*
        Get the total amount of power drained from the battery since the last time the battery monitor
was started or reset.
         */
        return TotalDrain; //above
    }

    public void moveBatteryExternal(){
        /*
        Move the battery to the external charger. Updates any variables as needed to represent the move.
         */
        BattCapacity = Capacity;

    }
    public void moveBatteryCamera()
    {
        /*
        Move the battery to the camera. Updates any variables as needed to represent the move.
         */
        CamCapacity = BattCapacity;
        CameraCharge  =BatteryPercentage;

    }
    public void removeBattery(){
        /* Remove the battery from either the camera or external charger. Updates any variables as needed
to represent the removal.
         */
        CamCapacity = 0;
        CameraCharge = 0;
    }

    public void setCameraPowerConsumption(double cameraPowerConsumption){
        /*Set the power consumption of the camera.
         */
        PowerConsumption = cameraPowerConsumption;
    }

}
