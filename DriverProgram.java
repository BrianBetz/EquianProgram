import java.io.IOException;
import java.util.Arrays;
import javax.swing.JOptionPane;
import java.io.FileWriter;

/**
 * DriverProgram class handles user input message, which notifies user
 * to input name of file to be run through Converter. Also handles the printing
 * of the information to the desired format.
 *
 * @author Brian Betz
 * @version 01/03/2016
*/
 
public class DriverProgram {

/**
 * Main method.
 *
 * @param args is used.
 * @throws IOException (FileWriter)
 */

   public static void main(String[] args) throws IOException, NoFileEnteredException {
   
      Processor p = new Processor();
      FileWriter writer = new FileWriter("file.txt");
      String fileInput = 
          JOptionPane.showInputDialog("Enter file name (with extension)"
                  + " you would like to run."); 
   
      args = Arrays.copyOf(args, args.length + 1);
      args[0] = fileInput;
              
      if (args.length == 0 || args[0] == null) {
         throw new NoFileEnteredException();
      }
             
      else {     
         try {
            p.readFile(fileInput);
            writer.write(p.generateReport());
            writer.close();
               
            JOptionPane.showMessageDialog(null, 
                     "File created in current directory");
         }
               
         catch (IOException e) {
               
            JOptionPane.showMessageDialog(null, 
                     "*** Attempted to read file: " + args[0]
                     + " (The system cannot find the file specified)"
                     + " \n\tPlease try again. ***", 
                     "DERP", JOptionPane.ERROR_MESSAGE);         
         }
               
         catch (NullPointerException e) {
         }
               
         catch (IllegalArgumentException e) {
         }
      }
   }
}