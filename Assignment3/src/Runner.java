import javax.swing.SwingUtilities;

import speccheck.SpecCheck;

public class Runner
{
 public static void main(String args[])
 {
 {
 SwingUtilities.invokeLater(new Runnable()
                {
 public void run()
 {
 SpecCheck.testAndZip(
                         hw3.SpecChecker.class, // change example to the new spechchekcer
                        "SUBMIT_THIS",
         "hw3", //change this to the dir of a new prjkt
         "hw3/Grid.java",
        "hw3/ConnectGame.java",
         "hw3/GameFileUtil.java");//also change this
 }
 });
 }
 }
}

