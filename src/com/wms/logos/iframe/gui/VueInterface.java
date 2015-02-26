/*
 * VueInterface.java
 *
 * Created on May 24, 2007, 1:14 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.wms.logos.iframe.gui;


import com.wms.logos.icomponent.IComponentInterface;
import javax.swing.JPanel;
import javax.swing.JViewport;
/**
 *
 * @author mstemen
 */
public interface VueInterface extends IComponentInterface
{    
    public void writeSpecial( String message );
    
    public void clear();
            
    public JViewport getViewPort();
    
    public JPanel getPanel();              
    
    public String getTitle();
    
}
