/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.wms.logos.exception;

/**
 *
 * @author Matt
 */
public class LogosViewException extends Exception {
    public final static int VIEW_UNDEFINED = 0;
    public final static int VIEW_NON_EXIST = 1;

    private static int value = -1;

    /**
     * Creates a new instance of ViewException
     */
    public LogosViewException( int newValue )
    {
        this.value = newValue;
    }

    public int getExceptionNumber()
    {
        return this.value;
    }

}
