/*
 * GESListEntry.java
 *
 * Created on May 23, 2005, 5:38 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.wms.logos.util;
import com.wms.logos.util.*;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author mstemen
 */
public class LogosSimpleLogEntry extends JLabel {
    
    int logLevel = LogosSimpleLog.DEBUG;
    /** Creates a new instance of GESListEntry */
    public LogosSimpleLogEntry( String text)
    {
        this.logLevel = logLevel;
        this.setText(text);            
        setBackground( Color.WHITE );
        this.setDoubleBuffered(true);
    }
    
    public LogosSimpleLogEntry( int logLevel, Icon icon, String text )
    {
        this.logLevel = logLevel;
        this.setIcon(icon);
        this.setText(text);
        setBackground( Color.WHITE );
        this.setDoubleBuffered(true);
    }
    
    public void setColor( Color newColor )
    {
        setForeground( newColor );
    }
    
    public void setLevel( int logLevel )
    {
        this.logLevel = logLevel;
    }
    
    public int getLevel()
    {
        return logLevel;
    }
    public String toString()
    {
        return getText();
    }
    
    
}
