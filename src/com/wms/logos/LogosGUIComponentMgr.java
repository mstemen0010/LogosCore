/*
 * GUIComponentMgr.java
 *
 * Created on June 14, 2007, 3:08 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.wms.logos;

import com.wms.logos.exception.LogosViewException;
import com.wms.logos.iframe.gui.*;
import com.wms.logos.icomponent.IComponentInterface;
import com.wms.logos.icomponent.IComponentMgrInterface;
import com.wms.logos.util.LogosTypes;
import java.util.*;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 *
 * @author mstemen
 */
public class LogosGUIComponentMgr implements IComponentMgrInterface {

    static private LogosGUIComponentMgr instance = null;
    static private HashMap viewMap = null;
    static private HashMap frameMap = null;
    static private JFrame parentFrame = null;
    final static String packageName = "devmgr.wbem.client.framework.gui";
    static String name = "view";
    static String subCommands[] = {"test", "alert"};

    public static LogosGUIComponentMgr getViewMgr() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Creates a new instance of GUIComponentMgr
     */
    private LogosGUIComponentMgr(JFrame parentFrameset) {
        //FIXME: this is just to get it working, look up the class names from the Jar
        viewMap = new HashMap();
        frameMap = new HashMap();
        this.parentFrame = parentFrameset;
        initGUI(new String[]{"AlertView", "TestVue"});
    }

    public static LogosGUIComponentMgr getInstance(JFrame parentFrameset) {
        if (instance == null) {
            // create an instance and use the parent frames graphics
            instance = new LogosGUIComponentMgr(parentFrameset);
        }

        return instance;
    }

    public static void setParentFrameset(JFrame parentFrameset) {
        if (parentFrameset != null) {
            parentFrame = parentFrameset;
        }
    }

    public void fwShow(String viewName) throws LogosViewException {

        // String appName = CimClientMgr.getInstance().getTestApplicationName();
        if (viewName.equalsIgnoreCase("all")) {
            parentFrame.setVisible(true);
            parentFrame.pack();
            return;
        }
        LogosViewInterface vi = getView(viewName);
        if (vi == null) {
            throw new LogosViewException(LogosViewException.VIEW_NON_EXIST);
        } else {
            vi.getPanel().setVisible(true);
        }
        if (!frameMap.containsKey(viewName)) {
            /*
            JFrame frame = new JFrame();
            frameMap.put( viewName, frame );
            frame.setSize(600,600);
            frame.getContentPane().setLayout( new java.awt.GridLayout() );
            
            frame.getContentPane().add( vi.getPanel() );
            frame.pack();
            frame.setVisible(true);            
             */
            parentFrame.getContentPane().removeAll();
            parentFrame.getContentPane().setLayout(new java.awt.GridLayout());
            parentFrame.getContentPane().add(vi.getPanel());
            // parentFrame.setTitle( appName + ":: " + vi.getTitle() );
            parentFrame.setVisible(true);
            parentFrame.pack();

        } else {
            JFrame frame = (JFrame) frameMap.get(viewName);
            frame.setVisible(true);
        }


    }

    public void fwClear(String viewName) throws LogosViewException {
        LogosViewInterface vi = getView(viewName);
        if (vi == null) {
            throw new LogosViewException(LogosViewException.VIEW_NON_EXIST);
        } else {
            vi.clear();
        }
    }

    public void fwHide(String viewName) throws LogosViewException {
        if (viewName.equalsIgnoreCase("all")) {
            parentFrame.setVisible(false);
            return;
        }
        LogosViewInterface vi = getView(viewName);
        if (vi == null) {
            throw new LogosViewException(LogosViewException.VIEW_NON_EXIST);
        } else {
            vi.getPanel().setVisible(false);
            if (frameMap.containsKey(viewName)) {
                JFrame frame = (JFrame) frameMap.get(viewName);
                frame.setVisible(false);
            }
        }
    }

    public String fwList() {
        StringBuffer frameList = new StringBuffer();
        frameList.append("Invalid GUI panel name, valid names are: all ");
        Iterator it = viewMap.keySet().iterator();
        int itCount = 0;
        while (it.hasNext()) {
            String frameName = (String) it.next();
            frameList.append(frameName).append(" ");
        }
        return frameList.toString();
    }

    public LogosViewInterface getView(String viewName) throws LogosViewException {
        LogosViewInterface view = null;
        if (viewMap == null) {
            throw new LogosViewException(LogosViewException.VIEW_UNDEFINED);
        }
        if (viewMap.containsKey(viewName)) {
            view = (LogosViewInterface) viewMap.get(viewName);
        } else {
            throw new LogosViewException(LogosViewException.VIEW_NON_EXIST);
        }
        return view;
    }

//    public static void start()
//    {
//        java.awt.EventQueue.invokeLater(new Runnable()
//        {
//            public void run()
//            {
//                instance = new VueComponentMgr();
//                // if( VueComponentMgr != null )
//                   // VueComponentMgr.setVisible(true);
//            }
//        });
//    }
    // Variables declaration - do not modify                     
    // End of variables declaration                   
    public IComponentInterface fwGetRoute() {
        return this;
    }

    public void fwWrite(String msg) {
    }

    public String[] getSubComponents() {
        return subCommands;
    }

    public void fwSetRoute(IComponentInterface ci_out) {
    }

    public String fwGetName() {
        return this.name;
    }

    private static void initGUI(String[] views) {
        if (views != null) {
            for (int i = 0; i < views.length; i++) {
                if (views[i] != null) {
                    try {
                        StringBuffer sb = new StringBuffer(packageName).append(".");
                        String frameName = views[i];

                        sb.append(frameName);

                        Class c = Class.forName(sb.toString());
                        LogosViewInterface viewIntf;
                        try {
                            viewIntf = (LogosViewInterface) c.newInstance();

                            if (viewIntf != null) {
                                // int lastDot = frameName.lastIndexOf(".") + 1;
                                // String frameJustName = frameName.substring( lastDot, frameName.length() );                                
                                //TODO: Fix the following to mesh with Logos
                                // viewMap.put( viewIntf.fwGetName(), viewIntf );
                            }
                        } catch (InstantiationException ex) {
                            ex.printStackTrace();
                        } catch (IllegalAccessException ex) {
                            ex.printStackTrace();
                        }

                    } catch (ClassNotFoundException e) {
                    }

                }
            }
        }
    }

    public void fwWrite(String msg, String target) throws FWTargetException {
    }

    public void fwWrite(Exception e) {
    }

    public void fwWrite(Exception e, String target) throws FWTargetException {
    }

    public void fwWrite(FWWriteLevel level, String msg) {
    }

    public void fwWrite(FWWriteLevel level, String msg, String target) throws FWTargetException {
    }

    public void fwWrite(FWWriteLevel level, Exception e) {
    }

    public void fwWrite(FWWriteLevel level, Exception e, String target) throws FWTargetException {
    }

    public static void addIcons() {
        // java.net.URL url  = getClass().getResource( "/com/ges/images/udt/Con_lib_green_sm_fade_40.png" );
        // new javax.swing.ImageIcon(getClass().getResource("/com/ges/images/Host_green.gif")))
        folderSet.addIcon(LogosTypes.FolderIcons.CLOSED, folderIcon);
        folderSet.addIcon(LogosTypes.FolderIcons.OPEN, folderIcon);
        librarySet.addIcon(LogosTypes.LibDeviceIcons.ONLINE, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/Con_lib_green_sm.png")));
        librarySet.addIcon(LogosTypes.LibDeviceIcons.OFFLINE, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/Con_lib_green_sm_offline.png")));
        librarySet.addIcon(LogosTypes.LibDeviceIcons.OFFLINE_SELECTED, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/Con_lib_green_sm_offline_selected.png")));
        librarySet.addIcon(LogosTypes.LibDeviceIcons.HILITE, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/Con_lib_green_sm_hilight.png")));
        librarySet.addIcon(LogosTypes.LibDeviceIcons.SELECTED, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/Con_lib_green_sm_selected.png")));
        librarySet.addIcon(LogosTypes.LibDeviceIcons.FADE, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/Con_lib_green_sm_fade_40.png")));
        librarySet.addIcon(LogosTypes.LibDeviceIcons.FADE_HILIT, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/Con_lib_green_sm_hilight_40.png")));
        librarySet.addIcon(LogosTypes.LibDeviceIcons.LOCKED, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/Con_lib_green_sm_door_locked.png")));
        librarySet.addIcon(LogosTypes.LibDeviceIcons.LOCKED_HILIT, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/Con_lib_green_sm_door_locked_hilite.png")));
        librarySet.addIcon(LogosTypes.LibDeviceIcons.LOCKED_SELECTED, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/Con_lib_green_sm_door_locked_selected.png")));

        driveSet.addIcon(LogosTypes.DriveDeviceIcons.AVAILABLE, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/Drive2_sm.png")));
        driveSet.addIcon(LogosTypes.DriveDeviceIcons.SELECTED, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/Drive2_sm_selected.png")));
        driveSet.addIcon(LogosTypes.DriveDeviceIcons.SELECTED_DATA, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/Drive2_scratch_tape_sm_selected.png")));
        driveSet.addIcon(LogosTypes.DriveDeviceIcons.BUSY_DATA, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/Drive2_scratch_tape_sm.png")));
        driveSet.addIcon(LogosTypes.DriveDeviceIcons.BUSY_SCRATCH, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/Drive2_scratch_tape_sm.png")));
        driveSet.addIcon(LogosTypes.DriveDeviceIcons.FADE, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/Drive2_sm_fade_40.png")));
        driveSet.addIcon(LogosTypes.DriveDeviceIcons.MISSING, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/Drive2_sm_missing.png")));
        // standard
        tapeSet.addIcon(LogosTypes.TapeIcons.HILITE, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/Vol_in_slot_hilite_sm_scratch.png")));
        tapeSet.addIcon(LogosTypes.TapeIcons.DATAHILITE, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/Vol_in_slot_hilite_sm_data.png")));
        tapeSet.addIcon(LogosTypes.TapeIcons.SELECTED, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/Vol_in_slot_sm_selected_scratch.png")));
        tapeSet.addIcon(LogosTypes.TapeIcons.DATASELECTED, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/Vol_in_slot_sm_selected_data.png")));
        tapeSet.addIcon(LogosTypes.TapeIcons.SCRATCH, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/Vol_in_slot_sm_scratch.png")));
        tapeSet.addIcon(LogosTypes.TapeIcons.DATA, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/Vol_in_slot_sm_data_sm.png")));
        // fades
        tapeSet.addIcon(LogosTypes.TapeIcons.FADE, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/Vol_in_slot_sm_fade_40.png")));
        tapeSet.addIcon(LogosTypes.TapeIcons.FADE_HILIT, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/Vol_slot_data_hilite_sm_40.png")));
        tapeSet.addIcon(LogosTypes.TapeIcons.DATAFADE, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/Vol_slot_data_sm_fade_40.png")));
        tapeSet.addIcon(LogosTypes.TapeIcons.UNKNOWN, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/Vol_in_slot_sm_unknown.png")));
        // extended
        tapeSet.addIcon(LogosTypes.TapeIcons.CLEANING, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/Vol_in_slot_cleaning.png")));
        tapeSet.addIcon(LogosTypes.TapeIcons.MISSING, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/Vol_in_slot_sm_missing_scratch.png")));
        tapeSet.addIcon(LogosTypes.TapeIcons.DATAMISSING, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/Vol_in_slot_sm_missing_data.png")));
        tapeSet.addIcon(LogosTypes.TapeIcons.DATAMISSING, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/Vol_in_slot_sm_missing_data.png")));
        tapeSet.addIcon(LogosTypes.TapeIcons.DATARELATED, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/Vol_in_slot_sm_related_data.png")));
        tapeSet.addIcon(LogosTypes.TapeIcons.RELATED, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/Vol_in_slot_sm_related_scratch.png")));
        tapeSet.addIcon(LogosTypes.TapeIcons.DIRTY, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/Vol_in_slot_sm_dirty.png")));
        tapeSet.addIcon(LogosTypes.TapeIcons.CLEAN, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/Vol_in_slot_sm_clean.png")));
        tapeSet.addIcon(LogosTypes.TapeIcons.UNALLOCATED, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/Vol_in_slot_sm_unallocated.png")));
        tapeSet.addIcon(LogosTypes.TapeIcons.EXPORTED, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/Vol_in_slot_sm_exported2.png")));
        tapeSet.addIcon(LogosTypes.TapeIcons.LOCKED, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/Vol_in_slot_sm_locked.png")));

        vTapeSet.addIcon(LogosTypes.VTapeIcons.SCRATCH, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/Vol_in_slot_sm_scratch_Virtual.png")));
        vTapeSet.addIcon(LogosTypes.VTapeIcons.LOCKED, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/Vol_in_slot_sm_locked_Virtual.png")));
        vTapeSet.addIcon(LogosTypes.VTapeIcons.DIRTY, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/Vol_in_slot_sm_dirty_Virtual.png")));
        vTapeSet.addIcon(LogosTypes.VTapeIcons.CLEAN, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/Vol_in_slot_sm_clean_Virtual.png")));
        vTapeSet.addIcon(LogosTypes.VTapeIcons.UNALLOCATED, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/Vol_in_slot_sm_unallocated_Virtual.png")));

        serverSet.addIcon(LogosTypes.ServerIcons.ONLINE, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/Host_green.gif")));

        glyphSet.addIcon(LogosTypes.SystemGlyphs.ALIVE, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/status_light_green.GIF")));
        glyphSet.addIcon(LogosTypes.SystemGlyphs.DEAD, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/status_light_red.GIF")));
        glyphSet.addIcon(LogosTypes.SystemGlyphs.UNKNOWN, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/status_light_unknown.GIF")));
        glyphSet.addIcon(LogosTypes.SystemGlyphs.PASSED, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/success_gylph.png")));
        glyphSet.addIcon(LogosTypes.SystemGlyphs.PENDING, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/pending_gylph.png")));
        glyphSet.addIcon(LogosTypes.SystemGlyphs.FAILED, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/failed_gylph.png")));
        glyphSet.addIcon(LogosTypes.SystemGlyphs.TRASH_EMPTY, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/udt_trash_2.png")));
        glyphSet.addIcon(LogosTypes.SystemGlyphs.TRASH_FULL, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/udt_trash_full_2.png")));
        glyphSet.addIcon(LogosTypes.SystemGlyphs.TRASH_FLAMES, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/udt_trash_fire_2.png")));

        logGlyphSet.addIcon(LogosTypes.LogGlyphs.DB_TRACE, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/logGlypthSet_trace.png")));
        logGlyphSet.addIcon(LogosTypes.LogGlyphs.DEBUG, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/logGlypthSet_debug.png")));
        logGlyphSet.addIcon(LogosTypes.LogGlyphs.FATAL, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/logGlypthSet_fatal.png")));
        logGlyphSet.addIcon(LogosTypes.LogGlyphs.GUI_TRACE, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/logGlypthSet_trace.png")));
        logGlyphSet.addIcon(LogosTypes.LogGlyphs.LOG_INFO, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/logGlypthSet_info.png")));
        logGlyphSet.addIcon(LogosTypes.LogGlyphs.MESSAGE, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/logGlypthSet_message.png")));
        logGlyphSet.addIcon(LogosTypes.LogGlyphs.WARNING, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/logGlypthSet_warning.png")));
        logGlyphSet.addIcon(LogosTypes.LogGlyphs.SPECIAL, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/logGlypthSet_empty.png")));
        logGlyphSet.addIcon(LogosTypes.LogGlyphs.TRACE, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/logGlypthSet_trace.png")));

        helpGlyphs.addIcon(LogosTypes.HelpGlyphs.CHAPTER_CLOSED, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/help_chapter_closed.png")));
        helpGlyphs.addIcon(LogosTypes.HelpGlyphs.CHAPTER_OPEN, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/help_chapter.png")));
        helpGlyphs.addIcon(LogosTypes.HelpGlyphs.CHILD_PAGE, new ImageIcon(resourceClass.getResource("/com/ges/images/udt/help_page.png")));
    }

    public static Icon getIcon(LogosTypes.FolderIcons type) {
        return folderSet.getIcon(type);
    }

    public static Icon getIcon(LogosTypes.LeafIcons type) {
        return leafSet.getIcon(type);
    }

    public static Icon getIcon(LogosTypes.ServerIcons type) {
        return serverSet.getIcon(type);
    }

    public static Icon getIcon(LogosTypes.LibDeviceIcons type) {
        return librarySet.getIcon(type);
    }

    public static Icon getIcon(LogosTypes.DriveDeviceIcons type) {
        return driveSet.getIcon(type);
    }

    public static Icon getIcon(LogosTypes.TapeIcons type) {
        return tapeSet.getIcon(type);
    }

    public static Icon getIcon(LogosTypes.VTapeIcons type) {
        return vTapeSet.getIcon(type);
    }

    public static Icon getIcon(LogosTypes.SystemGlyphs type) {
        return glyphSet.getIcon(type);
    }

    public static Icon getIcon(LogosTypes.LogGlyphs type) {
        return logGlyphSet.getIcon(type);
    }

    public static Icon getIcon(LogosTypes.HelpGlyphs type) {
        return helpGlyphs.getIcon(type);
    }
    private static LogosIconSet folderSet = new LogosIconSet(LogosTypes.IconTypes.FOLDER);
    private static LogosIconSet leafSet = new LogosIconSet(LogosTypes.IconTypes.LEAF);
    private static LogosIconSet serverSet = new LogosIconSet(LogosTypes.IconTypes.SERVER);
    private static LogosIconSet librarySet = new LogosIconSet(LogosTypes.IconTypes.LIBRARY);
    private static LogosIconSet driveSet = new LogosIconSet(LogosTypes.IconTypes.DRIVE);
    private static LogosIconSet tapeSet = new LogosIconSet(LogosTypes.IconTypes.TAPE);
    private static LogosIconSet vTapeSet = new LogosIconSet(LogosTypes.IconTypes.VTAPE);
    private static LogosIconSet glyphSet = new LogosIconSet(LogosTypes.IconTypes.SYSTEM);
    private static LogosIconSet logGlyphSet = new LogosIconSet(LogosTypes.IconTypes.LOG);
    private static LogosIconSet helpGlyphs = new LogosIconSet(LogosTypes.IconTypes.HELP);
    static DefaultTreeCellRenderer tempRend = new DefaultTreeCellRenderer();
    static Icon folderIcon = tempRend.getClosedIcon();
    static Icon leafIcon = tempRend.getOpenIcon();
    private static Class resourceClass = new String().getClass();
}

