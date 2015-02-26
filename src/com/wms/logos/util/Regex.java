/**************************************************************************
 *                                                                        *
 *  Copyright 2006 LSI Logic Incorporated.  All rights reserved.          *
 *                                                                        *
 *  This file is confidential and a trade secret of LSI Logic.  The       *
 *  receipt of or possession of this file does not convey any rights to   *
 *  reproduce or disclose its contents or to manufacture, use, or sell    *
 *  any thing it may describe, in whole, or in part, without the specific *
 *  written consent of LSI Logic Incorporated.                            *
 *                                                                        *
 **************************************************************************/

package com.wms.logos.util;

/**
 * VERSION:        %version: 1 %                                           <BR>
 * UPDATE DATE:    %date_created: Mon Aug 14 15:04:07 2006 %               <BR><BR>
 * 
 * CONTACTS:                                                               <BR>
 * Originator:   bcampbell                                               <BR>
 * Updater:      %created_by: bcampbel %                                     <BR><BR>
 * 
 * DESCRIPTION:                                                            <BR>
 * This is a convenient way to put all the regulat expressions that are used often, into one 
 *  static container.
 * <BR><BR>
 * 
 * NOTES/ASSUMPTIONS:                                                      <BR>
 * None.
 * <BR><BR>
 * 
 * REFERENCE:                                                              <BR>
 * None.
 */
public class Regex
{
   // Examples: 678697, 1, 0, 789789,...
   public final static String INTEGER = "(\\d+)";

   // Examples: 678697, 1, 0, 789789,...
   public final static String INTEGER_SIGNED = "-?\\d+";

   // Examples: 6789KB, 678ByTEs, 6786786tB,...
   public final static String SIZE = "(\\d+)([Kk][Bb]|[Mm][Bb]|[Gg][Bb]|[Tt][Bb]|[Bb][Yy][Tt][Ee][Ss])";

   // Examples: 0, 1, 5, 6,...
   public final static String RAID = "(0|1|3|5|6){1}";

   // Exampls 192.168.1.1, 168.123.111.140,..
   public final static String IP4 = "\\d+\\.\\d+\\.\\d+\\.\\d+";

   // Example: 600A0B80000F6B8800000000445A4667
   public static final String WWN = "[0-9a-fA-F]{32}";

   // Example: 20000004CFDB0135
   public static final String DISK_DRIVE_WWN = "[0-9a-fA-F]{16}";

   // Example: This_is_a_long_volumename
   public static final String NAME = "[a-zA-Z_0-9]+";   

   // Example: true | false
   public static final String BOOL = "([tT][rR][uU][eE]|[fF][aA][lL][sS][eE])";

    public static final String ALL = ".*";

}
