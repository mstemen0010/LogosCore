/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.wms.logos.iworker;

import com.wms.logos.thread.LogosRunnable;

/**
 *
 * @author mstemen
 */
public interface IWorkerInterface extends Runnable {

    public void logMessage( String msg );
    
    public String getWorkerName();
    
    public void setRunState(boolean newRunState);
    
    public boolean isWorkerRunning();
    
    public boolean checkForExit();
    
    public void addObserver( IWorkerBossInterface bossIterface );
}
