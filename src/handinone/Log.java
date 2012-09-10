package handinone;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class Log {
  private static File log;
  private static PrintWriter print;
  
  public static void log(String s){
    if(!isInitialised()) initialise();
    print.println(new Date() + ": " + s);
  }
  
  public static void error(String s){
    System.err.println("Error: " + s);
    log(s);
  }
  
  private static void initialise(){
    try{
    log = new File("./log/log.txt");
    print = new PrintWriter(log);
    } catch(IOException e){
      e.printStackTrace();
    }
  }
  
  private static boolean isInitialised(){
    return (log != null && print != null);
  }
}
