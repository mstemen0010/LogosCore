/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.wms.logos.util;

/**
 * This is like UNIX signal, but is not to be confused with 
 * Javas signal implementation /src/share/classes/sun/misc 
 * @author mstemen
 */
public interface ISignal {

    public enum SigType
    {
        // add as needed
        SigKill,
        SigHup,
        SigTerm,  
        SigClear; // signal  to reset state
       
    }
}
