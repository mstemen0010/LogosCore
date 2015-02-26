/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.wms.logos;


/**
 *
 * @author Matt
 */

public class LogosTrace  {

    static public void verbose(String arg)
    {
        System.out.println("Arg is:" + arg );
    }

    static void debug(String string) {
        System.out.println("Debug message is: " + string );
    }
}
