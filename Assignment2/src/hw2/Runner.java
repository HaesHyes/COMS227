package hw2;

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
                         hw2.SpecChecker.class, // change example to the new spechchekcer
                        "SUBMIT_THIS",
         "hw2", //change this to the dir of a new prjkt
                        new String[]{"src/hw2/ThreeCushion.java"}); //also change this
 }
 });
 }
 }
}

