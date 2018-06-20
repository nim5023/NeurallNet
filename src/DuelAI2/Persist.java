package DuelAI2;

import DuelAI2.Constants.Color;
import java.io.*;  
import java.util.logging.Level;
import java.util.logging.Logger;

class Persist{  
    
    public void save(Net net, int generation, Color color){
        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream(generation+"_"+color.toString()+".txt");
            ObjectOutputStream out=new ObjectOutputStream(fout);
            out.writeObject(net);
            out.flush();
            System.out.println("success: " + generation);
        } catch (Exception ex) {
            Logger.getLogger(Persist.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fout.close();
            } catch (IOException ex) {
                Logger.getLogger(Persist.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public Net get(int generation, Color color){
        ObjectInputStream in = null;
        Net net = null;
        try {        
            in = new ObjectInputStream(new FileInputStream(generation+"_"+color.toString()+".txt"));
            net =(Net)in.readObject();
        } catch (Exception ex) {
            Logger.getLogger(Persist.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(Persist.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return net;
        
        
    }
    
  
}  