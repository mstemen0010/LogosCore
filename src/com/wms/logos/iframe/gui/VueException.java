/*
 * VueException.java
 *
 * Created on May 24, 2007, 1:29 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.wms.logos.iframe.gui;

/**
 *
 * @author mstemen
 */
public class VueException extends Exception
{
    public final static int VIEW_UNDEFINED = 0;
    public final static int VIEW_NON_EXIST = 1;                
    
    private static int value = -1;   
    
    /**
     * Creates a new instance of VueException
     */
    public VueException( int newValue )
    {
        this.value = newValue;
    }
    
    public int getExceptionNumber()
    {
        return this.value;
    }
}
