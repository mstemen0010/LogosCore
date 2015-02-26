/*
 
 * ParallaxTypes.java
 
 *
 
 * Created on December 7, 2004, 7:56 PM
 
 */



package com.wms.logos.util;

import javax.swing.JPanel;
import java.util.*;

/**
 *
 *
 *
 * @author Matt Stemen
 *
 */

public class LogosTypes
        
{
    
    
    
    /**
     * Creates a new instance of ParallaxTypes 
     */
    
    public LogosTypes() {
        
    }
    
    public enum ScriptType {
        None,
        FullConfigPartition,
        FullConfigConsolidation,
        ReConfigPartition,
        ReConfigConsolidation
    }
    
    public enum ScriptPage {
        None(0),
        Intro(1),
        PickScenario(2),
        AddServer(3),
        AddMediaMgr(4),
        AddRealLib(5),
        AddRealLibs(6),
        AddVirtLib(7),
        AddVirtLibs(8),
        AddVirtDrives(9),
        AssocRealTapes(10);
        
        private ScriptPage( int type ) {
            this.type = type;
        }
        
        public void setPagePanel( JPanel newPanel ) {
            pagePanel = newPanel;
        }
        
        public JPanel getPagePanel() {
            return pagePanel;
        }
        
        public void setTitle( String title ) {
            this.title = title;
        }
        
        public String getTitle() {
            return title;
        }
        
        public int toInt() {
            return type;
        }
        
        public int size() {
            return size;
        }
        private int type = 0;
        private int size = 11;
        private String title = null;
        private JPanel pagePanel;
    }
    
    public enum UDTFilterType {
        
        NONE,
        READ_ONLY,
        DROP_DOWN,
        NO_DISPLAY
    }
    
    
    
    public enum UDTObjectStatus
            
    {
        UNKNOWN,
        OFFLINE,
        ONLINE,
        ERROR,
        LOCKED
    }
    
    
    
    public enum ServerIcons {
        UNKNOWN,
        ONLINE,
        OFFLINE,
        MISSING
    }
    
    
    
    public enum LibDeviceIcons {
        HILITE,
        SELECTED,
        FADE,
        FADE_HILIT,
        OK,
        MISSING,
        OFFLINE,
        OFFLINE_SELECTED,
        ONLINE,
        ERROR,
        LOCKED,
        LOCKED_SELECTED,
        LOCKED_HILIT,
    }
    
    
    
    public enum DriveDeviceIcons {
        HILITE,
        SELECTED,
        SELECTED_DATA,
        FADE,
        FADE_HILIT,
        OK,
        MISSING,
        AVAILABLE,
        BUSY_SCRATCH,
        BUSY_DATA,
        ERROR,
        LOCKED
    }
    
    public enum VTapeIcons {
        HILITE,
        EXPORTED,
        DATAHILITE,
        SELECTED,
        DATASELECTED,
        FADE,
        FADE_HILIT,
        DATAFADE,
        UNKNOWN,
        ERROR,
        SCRATCH,
        DATA,
        CLEANING,
        DATAMISSING,
        MISSING,
        DATARELATED,
        RELATED,
        DIRTY,
        CLEAN,
        LOCKED,
        UNALLOCATED
    }
    
    
    public enum TapeIcons {
        HILITE,
        EXPORTED,
        DATAHILITE,
        SELECTED,
        DATASELECTED,
        FADE,
        FADE_HILIT,
        DATAFADE,
        UNKNOWN,
        ERROR,
        SCRATCH,
        DATA,
        CLEANING,
        DATAMISSING,
        MISSING,
        DATARELATED,
        RELATED,
        DIRTY,
        CLEAN,
        LOCKED,
        UNALLOCATED
    }
    
    public enum SystemGlyphs {
        ALIVE,
        DEAD,
        UNKNOWN,
        ADD,
        SUBTRACT,
        PASSED,
        FAILED,
        PENDING,
        TRASH_FULL,
        TRASH_EMPTY,
        TRASH_FLAMES        
    }
    
       /*
        *    public static final int LOG_OFF = 0;
    public static final int MESSAGE = 1;
    public static final int WARNING = 2;
    public static final int DEBUG = 3;
    public static final int GUI_TRACE = 4;
    public static final int TRACE = 5;
    public static final int ISO = 6;
    public static final int FATAL = 7;
        */
    
    public enum HelpGlyphs {
        CHILD_PAGE,
        CHAPTER_CLOSED,
        CHAPTER_OPEN
    }
    public enum LogGlyphs {
        LOG_INFO,
        MESSAGE,
        WARNING,
        DEBUG,
        GUI_TRACE,
        DB_TRACE,
        TRACE,
        FATAL,
        SPECIAL
    }
    
    public enum IconTypes
            
    {
        UNKNOWN,
        FOLDER,
        LEAF,
        SERVER,
        LIBRARY,
        DRIVE,
        TAPE,
        VTAPE,
        SYSTEM,
        LOG,
        HELP
    }
    
    
    
    public enum FolderIcons
            
    {
        OPEN,
        CLOSED
    }
    
    
    
    public enum LeafIcons {
        REGULAR
    }
    
    public enum ViewTypes
            
    {
        UNKNOWN,
        REAL,
        VIRTUAL,
        STOCKLIST,
        DEVICELIST,
        REALLIST,
        TAPELIST,
        ADHOC,
        TRASH
    }
    
    public enum UDTOperationTypes {
        AddVirtualDrive,
        AddVirtualLibrary,
        AddRealLibraryToRelation,
        AddVirtualDriveToLibrary,
        CreateVirtualDriveFromStock,
        CreateVirtualLibraryFromStock,
        AssignVolumeToVirtualLibrary,
        AddUDTMediaManager,
        SetDriveElement,
    }
    
    
    public enum UDTScenarioTypes {
        None,
        Consolidation,
        Partition
    }
    
    
    public enum MediaManagerTypes {
        UNKNOWN,
        ADIC,
        ACSLS,
        SCSI_DIRECT
    }
    
    public enum VTapeLocations {
        Unknown,
        MediaXPort,
        Storage,
        Cap,
        DataTransPort
    }
    
    public enum UDTLogType {
        UNKNOWN,
        DSS,
        SCSISQL,
        PARALLAXMGR,
        SYSTEM
    }
    
    public enum UDTLogClass {
        NONE(0),
        LEVELMASK(0x0F),
        CLASSMASK(~0x0F),
        GENERALDEBUG(0x000010),
        SCSICHANGER(0x000020),
        ELEMENTMANAGER(0x000020),
        DRIVEINFOMANAGER(0x000020),
        REMOTECHANGER(0x000020),
        REMOTECHANGERCLIENT(0x000020),
        SCSIMIDDLE(0x000040),
        SCSILOW(0x000080),
        EVENTHANDLER(0x000100),
        SCSIPROBER(0x000200),
        TCPCOMM(0x000400),
        CACHEMANAGER(0x000800),
        VTSD(0x001000),
        ACSLS(0x002000),
        XMLPARSER(0x004000),
        XMLSCSIPROBE(0x004000),
        XMLDRIVEINFO(0x004000),
        VIRTUALTAPE(0x008000),
        VIRTUALBRIDGE(0x010000),
        SCSITAPEMIDDLE(0x020000),
        SCSITARGETPROVIDER(0x040000),
        VIRTCOMMANDHANDLER(0x080000),
        VIRTUALCHANGER(0x100000),
        ISCSI(0x200000),
        DBMGR(0x400000),
        VSCSIMGR(0x800000),
        LOGSENSE(0x900000);//ECB
        
        private int type = 0;
        private static int ignoreClasses = 0;
        private static int watchClasses = 0;
        
        private UDTLogClass( int type ) {
            this.type = type;
        }
        
        
        public int toInt() {
            return type;
        }
        
        public int applyMask() {
            return getInt(LEVELMASK) & type;
        }
        
        public static UDTLogClass getBit( String bitName ) {
            UDTLogClass bit = NONE;
            UDTLogClass[] a = values();
            for( int i = 0; i < a.length; i++ ) {
                if( a[i].toString().equals(bitName)) {
                    bit = a[i];
                    break;
                }
                
            }
            return bit;
        }
        
        public void add( ) {
            watchClasses |= type;
        }
        
        public void remove() {
            ignoreClasses |= type;
        }
        
        public static int getIgnoreClasses() {
            return ignoreClasses;
        }
        
        public static int getWatchClasses() {
            return watchClasses;
        }
        
        public static int getInt( UDTLogClass type ) {
            int retVal = NONE.ordinal();
            switch( type ) {
                case LEVELMASK:
                    retVal = 0x0F;
                    break;
                    
                case GENERALDEBUG:
                    retVal = 0x000010;
                    break;
                    
                case SCSICHANGER:
                    retVal = 0x000020;
                    break;
                    
                case ELEMENTMANAGER:
                    retVal = 0x000020;
                    break;
                    
                case DRIVEINFOMANAGER:
                    retVal = 0x000020;
                    break;
                    
                case REMOTECHANGER:
                    retVal = 0x000020;
                    break;
                    
                case REMOTECHANGERCLIENT:
                    retVal = 0x000020;
                    break;
                    
                case SCSIMIDDLE:
                    retVal = 0x000040;
                    break;
                    
                case SCSILOW:
                    retVal = 0x000080;
                    break;
                case EVENTHANDLER:
                    retVal = 0x000100;
                    break;
                case SCSIPROBER:
                    retVal = 0x000200;
                    break;
                case TCPCOMM:
                    retVal = 0x000400;
                    break;
                case CACHEMANAGER:
                    retVal = 0x000800;
                    break;
                case VTSD:
                    retVal = 0x001000;
                    break;
                case ACSLS:
                    retVal = 0x002000;
                    break;
                case XMLPARSER:
                    retVal = 0x004000;
                    break;
                case XMLSCSIPROBE:
                    retVal = 0x004000;
                    break;
                case XMLDRIVEINFO:
                    retVal = 0x004000;
                    break;
                case VIRTUALTAPE:
                    retVal = 0x008000;
                    break;
                case VIRTUALBRIDGE:
                    retVal = 0x010000;
                    break;
                case SCSITAPEMIDDLE:
                    retVal = 0x020000;
                    break;
                case SCSITARGETPROVIDER:
                    retVal = 0x040000;
                    break;
                case VIRTCOMMANDHANDLER:
                    retVal = 0x080000;
                    break;
                case VIRTUALCHANGER:
                    retVal = 0x100000;
                    break;
                case ISCSI:
                    retVal = 0x200000;
                    break;
                case DBMGR:
                    retVal = 0x400000;
                    break;
                case VSCSIMGR:
                    retVal = 0x800000;
                    break;
                case LOGSENSE:
                    retVal = 0x900000;
                    break;
                    
            }
            
            return retVal;
        }
        
        
    }
    
    public static final int UNKNOWN = 0;
    
    public static final int  MEDIAXPORT = 1;  // arm
    
    public static final int STORAGE  = 2;       // slot
    
    public static final int CAP = 3;
    
    public static final int DATATRANSPORT = 4; // drive
    
    
    
    
}