import javax.swing.JOptionPane;
import java.awt.Font;

public class NoFileEnteredException extends Exception {

/**
 * Establishes the constructor.
 */

   public NoFileEnteredException() {
      JOptionPane.showMessageDialog(null, "Computers aren't 'literally' magic", 
         "DERP", JOptionPane.ERROR_MESSAGE);
      System.exit(0);
   }

}