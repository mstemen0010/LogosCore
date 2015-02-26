/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.wms.logos.iworker;

import com.wms.logos.thread.LogosRunnable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Matt
 */
public class IHeartBeat implements LogosRunnable {

    boolean isAlive = false;
    Thread heartBeat = null;

    public IHeartBeat()
    {
        isAlive = true;
        heartBeat = new Thread(this);
        heartBeat.run();
    }

    public void die()
    {
        isAlive = false;
    }
    
    public void run() {
        while( isAlive )
        {
            try {
                System.out.println("Ba-Bump");
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(IHeartBeat.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
