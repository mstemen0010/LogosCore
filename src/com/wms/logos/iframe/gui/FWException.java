/*
 * FWException.java
 *
 * Created on June 20, 2007, 2:26 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.wms.logos.iframe.gui;

/**
 *
 * @author mstemen
 */
public abstract class FWException extends Exception
{
 
    abstract public String getStrForValue( int value );
    
    abstract public int getExceptionNumber();
}
