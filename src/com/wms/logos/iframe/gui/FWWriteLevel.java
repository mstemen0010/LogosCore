/*
 * FWWriteLevel.java
 *
 * Created on June 20, 2007, 3:00 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.wms.logos.iframe.gui;

/**
 *
 * @author mstemen
 */

/*
 *
 * This class roughly represents a 1.5 enum type.
 * The idea is to to be able to use the class like a static, but limit the how many values of the 
 * type can exist. In a method signature the type can either be general ( FWWriteLevel ) or more 
 * specific ( FWriteLevel.ERROR )
 *
 */
public final class FWWriteLevel
{
    // write levels for targets to choose a format with
    public final static FWWriteLevel ERROR = new FWWriteLevel();
    public final static FWWriteLevel DEBUG = new FWWriteLevel();
    public final static FWWriteLevel INFO =  new FWWriteLevel();
    public final static FWWriteLevel STATUS = new FWWriteLevel();
    
    private FWWriteLevel m_Value;

    // only the copy constructor is available 
    public FWWriteLevel( FWWriteLevel value )
    {
        this.m_Value = value;        
    }
       
    private FWWriteLevel()
    {        
    }
}
