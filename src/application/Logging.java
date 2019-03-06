/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class Logging {

 private final static Logger LOG = Logger.getLogger(Logging.class.getName());
 private static FileHandler handler = null;

 public static void LogInformation(){
    try {
    handler = new FileHandler("Information.%u.%g.txt", 1024 * 1024, 10, true);
    } catch (SecurityException | IOException e) {
        e.printStackTrace();
        }
    Logger logger = Logger.getLogger("");
    handler.setFormatter(new SimpleFormatter());
    logger.addHandler(handler);
    logger.setLevel(Level.INFO);
 }


}
