/*
 * ParallaxIconSet.java
 *
 * Created on January 14, 2005, 8:59 PM
 */

package com.wms.logos;

//import com.ges.parallax.LogosTypes.VTapeIcons;
import com.wms.logos.util.LogosTypes;
import java.util.Hashtable;
import javax.swing.Icon;
/**
 *
 * @author  mstemen
 */

public class LogosIconSet {

    // potential icon set types...
    Hashtable<LogosTypes.ServerIcons, Icon>serverIcons = null;
    Hashtable<LogosTypes.LibDeviceIcons, Icon>libDeviceIcons = null;
    Hashtable<LogosTypes.DriveDeviceIcons, Icon>driveDeviceIcons = null;
    Hashtable<LogosTypes.TapeIcons, Icon>tapeIcons = null;
    Hashtable<LogosTypes.VTapeIcons, Icon>vTapeIcons = null;
    Hashtable<LogosTypes.FolderIcons, Icon>folderIcons = null;
    Hashtable<LogosTypes.LeafIcons, Icon>leafIcons = null;
    Hashtable<LogosTypes.SystemGlyphs, Icon>systemGlyphs = null;
    Hashtable<LogosTypes.LogGlyphs, Icon>logGlyphs = null;
    Hashtable<LogosTypes.HelpGlyphs, Icon>helpGlyphs = null;
    
    LogosTypes.IconTypes iconSetType = LogosTypes.IconTypes.UNKNOWN;
    /**
     * Creates a new instance of ParallaxIconSet 
     */
    public LogosIconSet( LogosTypes.IconTypes type ) {
        if( type == LogosTypes.IconTypes.FOLDER )
        {
            folderIcons = new Hashtable<LogosTypes.FolderIcons, Icon>();
        }
        else if( type == LogosTypes.IconTypes.LEAF )
        {
            leafIcons = new Hashtable<LogosTypes.LeafIcons, Icon>();
        }
        else if( type == LogosTypes.IconTypes.LIBRARY )
        {
            libDeviceIcons = new Hashtable<LogosTypes.LibDeviceIcons, Icon>();
        }
        else if( type == LogosTypes.IconTypes.DRIVE )
        {
            driveDeviceIcons = new Hashtable<LogosTypes.DriveDeviceIcons, Icon>();
        }
        else if( type == LogosTypes.IconTypes.TAPE )
        {
            tapeIcons = new Hashtable<LogosTypes.TapeIcons, Icon>();
        }
        else if( type == LogosTypes.IconTypes.VTAPE )
        {
            vTapeIcons = new Hashtable<LogosTypes.VTapeIcons, Icon>();
        }        
        else if( type == LogosTypes.IconTypes.SERVER )
        {
            serverIcons = new Hashtable<LogosTypes.ServerIcons, Icon>();
        }
        else if( type == LogosTypes.IconTypes.SYSTEM)
        {
            systemGlyphs = new Hashtable<LogosTypes.SystemGlyphs, Icon>();
        }
        else if( type == LogosTypes.IconTypes.LOG)
        {
            logGlyphs = new Hashtable<LogosTypes.LogGlyphs, Icon>();
        }
        else if( type == LogosTypes.IconTypes.HELP )
        {
            helpGlyphs = new Hashtable<LogosTypes.HelpGlyphs, Icon>();
        }
        
        

        this.iconSetType = type;
    }
    
    public void addIcon( LogosTypes.FolderIcons type, Icon icon )
    {
        if( iconSetType == LogosTypes.IconTypes.FOLDER )
        {
            if( folderIcons != null )
                folderIcons.put( type, icon );
        }
    }

    public void addIcon( LogosTypes.LeafIcons type, Icon icon )
    {
        if( iconSetType == LogosTypes.IconTypes.LEAF )
        {
            if( leafIcons != null )
                leafIcons.put( type, icon );
        }
    }
    public void addIcon( LogosTypes.ServerIcons type, Icon icon )
    {
        if( iconSetType == LogosTypes.IconTypes.SERVER )
        {
            if( serverIcons != null )
                serverIcons.put( type, icon );
        }
    }
    public void addIcon( LogosTypes.SystemGlyphs type, Icon icon )
    {
        if( iconSetType == LogosTypes.IconTypes.SYSTEM )
        {
            if( systemGlyphs != null )
                systemGlyphs.put( type, icon );
        }
    }    
    public void addIcon( LogosTypes.LibDeviceIcons type, Icon icon )
    {
        if( iconSetType == LogosTypes.IconTypes.LIBRARY )
        {
            if( this.libDeviceIcons!= null )
                libDeviceIcons.put( type, icon );
        }
    }
    public void addIcon( LogosTypes.DriveDeviceIcons type, Icon icon )
    {
        if( iconSetType == LogosTypes.IconTypes.DRIVE )
        {
            if( this.driveDeviceIcons != null )
                driveDeviceIcons.put( type, icon );
        }
    }
    public void addIcon( LogosTypes.TapeIcons type, Icon icon )
    {
        if( iconSetType == LogosTypes.IconTypes.TAPE )
        {
            if( this.tapeIcons != null )
                tapeIcons.put( type, icon );
        }
    }

    public void addIcon( LogosTypes.VTapeIcons type, Icon icon )
    {
        if( iconSetType == LogosTypes.IconTypes.VTAPE )
        {
            if( this.vTapeIcons != null )
                vTapeIcons.put( type, icon );
        }
    }
    
    public void addIcon( LogosTypes.LogGlyphs type, Icon icon )
    {
        if( iconSetType == LogosTypes.IconTypes.LOG )
        {
            if( this.logGlyphs != null )
                logGlyphs.put( type, icon );
        }
    }
    
    public void addIcon( LogosTypes.HelpGlyphs type, Icon icon )
    {
        if( iconSetType == LogosTypes.IconTypes.HELP )
        {
            if( this.helpGlyphs != null )
                helpGlyphs.put( type, icon );
        }
    }
    
    public Icon getIcon( LogosTypes.FolderIcons type )
    {
        return folderIcons.get( type );
    }

    public Icon getIcon( LogosTypes.LeafIcons type )
    {
        return leafIcons.get( type );
    }
    
    public Icon getIcon( LogosTypes.ServerIcons type )
    {
        return serverIcons.get( type );
    }
    public Icon getIcon( LogosTypes.LibDeviceIcons type )
    {
        return libDeviceIcons.get( type );
    }
    public Icon getIcon( LogosTypes.DriveDeviceIcons type )
    {
        return driveDeviceIcons.get( type );
    }
    public Icon getIcon( LogosTypes.VTapeIcons type )
    {
        return vTapeIcons.get( type );
    }
    public Icon getIcon( LogosTypes.TapeIcons type )
    {
        return tapeIcons.get( type );       
    }   
    
    public Icon getIcon( LogosTypes.SystemGlyphs type )
    {
        return systemGlyphs.get( type );
    }
    public Icon getIcon( LogosTypes.LogGlyphs type )
    {
        return logGlyphs.get( type );
    }
    
    public Icon getIcon( LogosTypes.HelpGlyphs type )
    {
        return helpGlyphs.get( type );
    }
    
    
}
