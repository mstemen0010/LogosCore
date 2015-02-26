/*
 * ViewInterface.java
 *
 * Created on May 24, 2007, 1:14 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.wms.logos.iframe.gui;


import com.wms.logos.config.IConfigParameter;
import com.wms.logos.icomponent.IComponentInterface;
import javax.swing.JPanel;
import javax.swing.JViewport;
/**
 *
 * @author mstemen
 */
public interface LogosViewInterface extends IComponentInterface
{

    public void initalizeFromFWParams(FWParameterData EventParmData);
           
    public void writeSpecial( String message );
    
    public void clear();
            
    public JViewport getViewPort();
    
    public JPanel getPanel();              
    
    public String getTitle();
    
    public void setAutoView( boolean choice );        
    
    public void initalizeFromFWParams( IConfigParameter data );
}
