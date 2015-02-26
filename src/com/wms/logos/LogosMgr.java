package com.wms.logos;

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
import com.wms.logos.iworker.IHeartBeat;
import com.wms.logos.iworker.IWorker;
import com.wms.logos.iworker.IWorkerBossInterface;
import com.wms.logos.util.LogosSimpleLog;
import com.wms.logos.util.LogosTypes;
import java.util.Observable;
import javax.swing.Icon;

// #[regen=yes,id=DCE.D8D0B2A9-E0BE-355D-8187-3D2705F5C716]
// </editor-fold> 
public class LogosMgr extends Observable implements LogosMgrInterface, IWorkerBossInterface {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.1C1D947E-F396-2406-1765-E524D2C31484]
    // </editor-fold>
    static LogosMgr logosMgrImpl;
    static LogosObserver observer;
    static LogosMgrInterface logosMgr;

    private static LogosSimpleLog log = new LogosSimpleLog( ".", "SCPMgr.log" );

    boolean testAlive;
    IHeartBeat heartBeat = null;

    private LogosMgr() {
        logosMgr = this;
        testAlive = true;
        heartBeat = new IHeartBeat();
    }

    LogosMgr create() {
        init();
        return logosMgrImpl;

    }

    static void setWatcher(LogosObserver observerInvoker) {
        observer = observerInvoker;
        logosMgrImpl.addObserver(observerInvoker);
    }

    public static LogosMgr summon() {
        init();
        return logosMgrImpl;
    }

    public static LogosMgrInterface getInterface() {
        if (logosMgr == null) {
            init();
        }
        return logosMgr;
    }

    public LogosMgrInterface getLogosInterface() {
        return this;
    }

    public boolean isAlive() {
        return testAlive;
    }

    public void die() {
        testAlive = false;
        heartBeat.die();
    }

    private static void init() {
        if (logosMgrImpl == null) {
            logosMgrImpl = new LogosMgr();
        }
    }

    public static LogosSimpleLog getMgrLog() {
        return log;
    }

    public LogosSimpleLog getLog() {
        return log;
    }

    public Icon getIcon(LogosTypes.HelpGlyphs type) {
        return LogosGUIComponentMgr.getIcon(type);
    }

    public Icon getIcon(LogosTypes.FolderIcons type) {
        return LogosGUIComponentMgr.getIcon(type);
    }

    public Icon getIcon(LogosTypes.LeafIcons type) {
        return LogosGUIComponentMgr.getIcon(type);
    }

    public Icon getIcon(LogosTypes.ServerIcons type) {
        return LogosGUIComponentMgr.getIcon(type);
    }

    public Icon getIcon(LogosTypes.LibDeviceIcons type) {
        return LogosGUIComponentMgr.getIcon(type);
    }

    public Icon getIcon(LogosTypes.DriveDeviceIcons type) {
        return LogosGUIComponentMgr.getIcon(type);
    }

    public Icon getIcon(LogosTypes.TapeIcons type) {
        return LogosGUIComponentMgr.getIcon(type);
    }

    public Icon getIcon(LogosTypes.VTapeIcons type) {
        return LogosGUIComponentMgr.getIcon(type);
    }

    public Icon getIcon(LogosTypes.SystemGlyphs type) {
        return LogosGUIComponentMgr.getIcon(type);
    }

    public Icon getIcon(LogosTypes.LogGlyphs type) {
        return LogosGUIComponentMgr.getIcon(type);
    }

    public void logMessage(String msg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

