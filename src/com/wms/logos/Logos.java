///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package com.wms.logos;
//
//import com.wms.logos.LogosGUIComponentMgr;
//import com.wms.logos.exception.LogosViewException;
//import java.lang.reflect.InvocationTargetException;
//import java.util.Observable;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
///**
// *
// * @author Matt
// */
//public class Logos extends javax.swing.JFrame implements LogosObserver {
//
//    LogosMgrInterface mgr;
//    LogosMgrInterface logosMgrInterface;
//
//    public static LogosRunLoop m_LogosLoop;
//    private static LogosEventListener m_EventListener = null;
//    public static Thread logosThread = null;
//    public static LogosGUIComponentMgr m_ViewMgr = null;
//    private String m_Host;
//
//    public void Logos()
//    {
//        // mgr = LogosMgr.create();
//        // LogosMgrInterface mgrInterface = mgr.getInterface();
//    }
//    public void setLogos(com.wms.logos.LogosMgrInterface logosMgr )
//    {
//        mgr = logosMgr;
//    }
//
//    public boolean isAlive()
//    {
//        return mgr.isAlive();
//    }
//
//    public void setWatched( LogosMgrInterface logosObservable )
//    {
//        mgr = logosObservable;
//    }
//    public static void main(String args[])
//    {
//        if( args.length <= 0 )
//        {
//            // Trace.debug("Client takes in 1 argument");
//            System.exit(-1);
//        }
//        // the following must be "final" due to the scoping in main()
//        final LogosFrameMgr cimClientMgr = LogosFrameMgr.getInstance();
//        try
//        {
//            java.awt.EventQueue.invokeAndWait(new Runnable()
//            {
//                public void run()
//                {
//                    // for Swing to function properly
//                    if( cimClientMgr != null )
//                    {
//                        m_ViewMgr = LogosGUIComponentMgr.getInstance(cimClientMgr);
//
//                    }
//
//                }
//            });
//            if( cimClientMgr == null )
//            {
//                bail("CimClientMgr was null");
//            }
//            m_LogosLoop = new LogosRunLoop( args[0] );
//            // init and create component managers on the frameworks
////            cimClientMgr.initCimClientComponents();
//            // setup the Threads
//
//
//            logosThread = new Thread( m_LogosLoop );
//            logosThread.setDaemon(true);
//            if( logosThread != null )
//            {
//                logosThread.setName("CimClientThread::" + m_LogosLoop.hashCode());
//                logosThread.start();
//            }
//        }
//        catch (InvocationTargetException ex)
//        {
//            bail( ex.getMessage());
//        }
//        catch (InterruptedException ex)
//        {
//            bail( ex);
//        }
//        catch( RuntimeException ex )
//        {
//            bail( ex );
//        }
//
//    }
//    // Variables declaration - do not modify
//    // End of variables declaration
//    private static void bail(String message )
//    {
//        StringBuffer outMessage = new StringBuffer("Some went fatally wrong during start. Message was: " ).append(message);
//        outMessage.append(" Exiting with errors.");
//        System.out.println( outMessage.toString());
//        System.exit(-1);
//    }
//    private static void bail(Exception e  )
//    {
//        StringBuffer outMessage = new StringBuffer("Some went fatally wrong during start. Message was: " ).append(e.getMessage() );
//        outMessage.append(" Exiting with errors.");
//        System.out.println( outMessage.toString());
//        System.exit(-1);
//    }
//
//    private static void bail(RuntimeException e )
//    {
//        StringBuffer outMessage = new StringBuffer("Some went fatally wrong during start. Message was: " ).append( e.getMessage() );
//
//        outMessage.append("\nExiting with errors, stack trace follows.\n");
//        System.out.println( outMessage.toString());
//        e.printStackTrace();
//        System.exit(-1);
//    }
//
//    void clear( String frameName )
//    {
//        try
//        {
//            m_ViewMgr.fwClear( frameName );
//        }
//        catch (LogosViewException ex)
//        {
//            // this.println( m_ViewMgr.fwList() );
//        }
//    }
//
//    void show( String frameName )
//    {
//        try
//        {
//            m_ViewMgr.fwShow( frameName );
//        }
//        catch (LogosViewException ex)
//        {
//            System.out.println( m_ViewMgr.fwList() );
//        }
//    }
//
//    void hide( String frameName )
//    {
//        try
//        {
//            m_ViewMgr.fwHide( frameName );
//        }
//        catch (LogosViewException ex)
//        {
//            System.out.println( m_ViewMgr.fwList() );
//        }
//        // this.println( listFrames() );
//    }
//
//    public void setHost( String host )
//    {
//        this.m_Host = host;
//    }
//
//    public static void shutdown( int status )
//    {
//        System.out.println("\n=================================================");
//        System.out.println("Shutting Down....");
//        System.out.println("CimClientMgr: Closing Resources...");
//        shutdownResources();
//        System.out.println("CimClientMgr: Resources Closed");
//        System.out.println("CimClientMgr: Exiting...");
//        System.gc();
//        System.out.println("CimClientMgr: Exited");
//        System.out.println("Shut Down");
//        System.out.println("=================================================");
//        System.exit(status);
//    }
//
//    private static void shutdownResources()
//    {
//        if( m_EventListener != null )
//        {
//            m_EventListener.stop();
//
//        }
//        if( logosThread != null )
//        {
//            try
//            {
//                logosThread.join(5000);
//            }
//            catch (InterruptedException ex)
//            {
//                ex.printStackTrace();
//            }
//        }
//    }
//
//    protected void finalize()
//    {
//        System.out.println("CimClientMgr: Exited via GC");
//    }
//
//
////    public static void main( String[] args )
////    {
////
////
////        Logos logosWatcher = null;
////        logosWatcher = new Logos();
////        // logosWatcher.setLogos(mgrInterface);
////        LogosInput logosIn = new LogosInput();
////        // logosIn.show(args);
////        while( logosWatcher.isAlive() )
////        {
////            try {
////                Thread.sleep(1000);
////            } catch (InterruptedException ex) {
////                Logger.getLogger(Logos.class.getName()).log(Level.SEVERE, null, ex);
////
////            }
////        }
////
////    }
//
//    public void update(Observable o, Object arg) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//}
