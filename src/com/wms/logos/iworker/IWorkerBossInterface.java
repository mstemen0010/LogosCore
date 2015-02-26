/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.wms.logos.iworker;

import java.util.Observer;

/**
 *
 * @author mstemen
 */
public interface IWorkerBossInterface extends Observer {
    public void logMessage(String msg);
}
