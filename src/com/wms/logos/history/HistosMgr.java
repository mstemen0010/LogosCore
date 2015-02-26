/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.wms.logos.history;

import javax.swing.JComponent;

/**
 *
 * @author mstemen
 */
public class HistosMgr implements HistosMgrInterface {

    public static HistosMgr m_instance = null;

    private JComponent loggingViewPort;


    public static HistosMgr getInstance()
    {
        if ( m_instance == null )
        {
            m_instance = new HistosMgr();
        }

        return m_instance;
    }
    private HistosMgr()
    {

    }
    public HistosMgr(String historyStore)
    {
        // throw new UnsupportedOperationException("Not yet implemented");
    }

    public void write(String msg, LogType type )
    {
        switch(type)
        {
            case File:
                break;
            case StdOut:
                break;
            case GUI:
                break;
        }

    }
}
