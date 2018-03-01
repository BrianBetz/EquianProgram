//test

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.SortedSet;

/** 
 *
 *@author Brian Betz
 *@version 01/13/2016 v_1.01
 */

public class Processor {

   private HashMap<Integer, LinkedList<String>> map = new HashMap<Integer,
           LinkedList<String>>();
   private String[] columns;
   
   /**
    * Constructor. 
    */

   public Processor() {
   }
   
   /**
    * readFile() method reads in data and stores quantity as the key
    * and CPT code as the value.
    *
    * @param fileIn is the file to be converted.
    * @throws IOException for file.
    */
      
   public void readFile(String fileIn) throws IOException {
    
      String fileName = fileIn;
      
      
     
      try {
         
         if (!fileName.equals("")) {
         
            Files.lines(Paths.get(fileName)).forEach(
                  s -> {
                     columns = s.split("\t");
                     Integer key = Integer.parseInt(columns[0]);
                     if (!map.containsKey(key)) {
                        map.put(key, new LinkedList<String>());
                     }
                     map.get(key).add(columns[1]);
                  });    
         }
         
         else {
            throw new NoFileEnteredException();
         }           
      }
      
      catch (NoFileEnteredException e) {
         String exception = e.getMessage();
      }
   }
   
   /**
    * generateReport() prints the output in the desired format.
    * 
    * @return output.
    */
         
   public String generateReport() {

      StringBuilder dhoutput = new StringBuilder();
      String s = "";
      
      
      TreeSet<Integer> keys = new TreeSet<Integer>(map.keySet());
      for (Integer key : keys) { 
         LinkedList<String> value = map.get(key);
      
         dhoutput.append("If IsCPT(\"");
         
         
         if (value.toString().length() > 310) {
            
            dhoutput.append(addLineBreaks(value.toString().replace(" ", "").replace("[", "").replace("]", "")
                          + "\"" + ")", 315));
                         
            int index = dhoutput.toString().lastIndexOf(',');
            dhoutput = dhoutput.deleteCharAt(index);               
         }
           
         else {
            dhoutput.append(value.toString().replace(" ", "").replace("[", "").replace("]", "")
                          + "\"" + ")");
         }
                           
         dhoutput.append(" Then\n   If Quantity > " + key + " AND Allowance >"
                          + " 0.0 Then \n      HoldBill(\"MUE Edits\")"
                          + "\n   End If\n");
      
         if (key >= 1) {
            dhoutput.append("Else");
         }
      } 
      
          
      String o = dhoutput.toString();
      return o.substring(0, o.length() - 5);
   }
              
   /**
    * addLineBreaks() sets the character limit per line. No more that
    * 330 per line.
    *
    * @param input in string input.
    * @param maxLineLength is the maximum line length.
    * @return output.
    */     
   
   public String addLineBreaks(String input, int maxLineLength) {
      StringTokenizer tok = new StringTokenizer(input, ",");
      StringBuilder output = new StringBuilder(input.length());
      int lineLen = 0;
      while (tok.hasMoreTokens()) {
         String word = tok.nextToken() + ",";
         
         if (lineLen + word.length() > maxLineLength) {
            lineLen = 0;
            if (lineLen == 0) {
               int start = output.length() - 1;
               int end = output.length();
               output.delete(start, end);
            }
            output.append("\")" + " or\nIsCPT(\"");  
         }
            
         output.append(word);
         lineLen += word.length();
      }
      
      String o = output.toString();
      return o;
   } 
}

