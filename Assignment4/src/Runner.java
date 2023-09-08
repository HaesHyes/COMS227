import javax.swing.SwingUtilities;

import speccheck.SpecCheck;

public class Runner
{
 public static void main(String[] args)
 {
 {
 SwingUtilities.invokeLater(() -> {
 SpecCheck.testAndZip(
                         hw4.SpecChecker.class, // change example to the new spechchekcer
                        "SUBMIT_THIS",
         "C:\\Users\\heyha\\Desktop\\COMS227Notes\\Assignments\\Assignment4\\COMS227_HW4_S23_skeleton\\src\\hw4", //change this to the dir of a new prjkt
         "hw4/AbstractLink.java",
         "hw4\\CouplingLink.java",
         "hw4\\DeadEndLink.java",
         "hw4\\MultiFixedLink.java",
         "hw4\\MultiSwitchLink.java",
         "hw4\\StraightLink.java",
         "hw4\\SwitchLink.java",
         "hw4\\TurnLink.java"); //also change this
 });
 }
 }
}

