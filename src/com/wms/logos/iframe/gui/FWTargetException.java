/*
 * FWTargetException.java
 *
 * Created on June 20, 2007, 2:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.wms.logos.iframe.gui;


/**
 *
 * @author mstemen
 */
public class FWTargetException extends FWException
{
    public final static int TARGET_UNDEFINED = 0;
    public final static int TARGET_NON_EXIST = 1;                

    private static int value = -1;       
    /** Creates a new instance of FWTargetException */
    
    public FWTargetException( int newValue )
    {
        this.value = newValue;
    }
    public int getExceptionNumber()
    {
        return this.value;
    }            

    public String getStrForValue(int value)
    {
        return null;
    }
}
