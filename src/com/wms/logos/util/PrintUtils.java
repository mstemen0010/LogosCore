/*******************************************************************************
 *                                                                             *
 * Copyright 2005, Engenio Information Technologies, Inc. All rights reserved. *
 *                                                                             *
 * This file is confidential and a trade secret of Engenio Information         *
 * Technologies. The receipt of, or possession of this file does not convey    *
 * any rights to reproduce or disclose its contents or to manufacture, use,    *
 * or sell anything it may describe, in whole, or in part, without the         *
 * specific written consent of Engenio Information Technologies Inc.           *
 *                                                                             *
 ******************************************************************************/
package com.wms.logos.util;

import java.util.Iterator;
import java.util.Vector;

public class PrintUtils
{

    
    public static String stripCIM( String CIMString)
    {
        String retStr = new String();
        String tmpStr = new String(CIMString);
        retStr = tmpStr.replaceAll("\\\"", "");
        
        return retStr;
    }
    
    public static String removeDoubleLines( String msg, boolean removeAll )
    {
        String tmpStr = new String( msg );
        String retStr = null;
        if( removeAll )
        {
            retStr = tmpStr.replaceAll("\n\n", "");
        }
        else
        {
            retStr = tmpStr.replaceAll("\n\n", "\n");
        }
        return retStr;
    }
}
