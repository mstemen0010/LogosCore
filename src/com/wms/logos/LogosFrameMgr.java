/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * LogosInput.java
 *
 * Created on Mar 18, 2009, 2:47:48 PM
 */
package com.wms.logos;

import com.wms.logos.exception.LogosViewException;
import com.wms.logos.LogosGUIComponentMgr;
import com.wms.logos.history.HistosMgr;
import com.wms.logos.iframe.gui.FWEntityStore;
import com.wms.logos.iframe.gui.LogosViewInterface;
import com.wms.logos.iframe.gui.ViewException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

/**
 *
 * @author Matt
 */
public class LogosFrameMgr extends javax.swing.JFrame {

    private final static String testApplicationName = "CimClient Test";
    public static LogosRunLoop m_LogosLoop;
    public static LogosGUIComponentMgr m_ViewMgr = null;
    public static HistosMgr m_LogMgr = null;
    public static Thread m_LogosLoopThread = null;
    public static LogosFrameMgr m_Instance;
    private static LogosEventListener m_EventListener = null;
    private static Properties m_LogosProperties;
    private String m_Host;
    private static File fwEntityFile;
    private static Properties m_FWEntityProperties;
    private static String fwEntityFileName = "fwEntities.cfg";
    private static FWEntityStore fwEntityStore = new FWEntityStore();
//    private static FWEntityStore fwEntityStore = new FWEntityStore();
    /** Creates new form LogosInput */
    static LogosFrameMgr m_LogosFrameMgr = null;

    public static void main(String args[])
    {
        if( args.length <= 0 )
        {
            LogosTrace.debug("Client takes in 1 argument");
            System.exit(-1);
        }
        // the following must be "final" due to the scoping in main()
        final LogosFrameMgr cimClientMgr = LogosFrameMgr.getInstance();
        try
        {
            java.awt.EventQueue.invokeAndWait(new Runnable()
            {
                public void run()
                {
                    // for Swing to function properly
                    if( cimClientMgr != null )
                    {
                        m_ViewMgr = LogosGUIComponentMgr.getInstance(cimClientMgr);

                    }

                }
            });
            if( cimClientMgr == null )
            {
                bail("CimClientMgr was null");
            }
            m_LogosLoop = new LogosRunLoop( args[0] );
            // init and create component managers on the frameworks
            cimClientMgr.initCimClientComponents();
            // setup the Threads


            m_LogosLoopThread = new Thread( m_LogosLoop );
            m_LogosLoopThread.setDaemon(true);
            if( m_LogosLoopThread != null )
            {
                m_LogosLoopThread.setName("CimClientThread::" + m_LogosLoop.hashCode());
                m_LogosLoopThread.start();
            }
        }
        catch (InvocationTargetException ex)
        {
            bail( ex.getMessage());
        }
        catch (InterruptedException ex)
        {
            bail( ex);
        }
        catch( RuntimeException ex )
        {
            bail( ex );
        }

    }
    private static void bail(String message )
    {
        StringBuffer outMessage = new StringBuffer("Some went fatally wrong during start. Message was: " ).append(message);
        outMessage.append(" Exiting with errors.");
        System.out.println( outMessage.toString());
        System.exit(-1);
    }
    private static void bail(Exception e  )
    {
        StringBuffer outMessage = new StringBuffer("Some went fatally wrong during start. Message was: " ).append(e.getMessage() );
        outMessage.append(" Exiting with errors.");
        System.out.println( outMessage.toString());
        System.exit(-1);
    }

    private static void bail(RuntimeException e )
    {
        StringBuffer outMessage = new StringBuffer("Some went fatally wrong during start. Message was: " ).append( e.getMessage() );

        outMessage.append("\nExiting with errors, stack trace follows.\n");
        System.out.println( outMessage.toString());
        e.printStackTrace();
        System.exit(-1);
    }

    /** Creates new form LogosFrameMgr */
    private LogosFrameMgr() {

        initComponents();
        this.setVisible(false);
        m_Instance = this;
    }

    void clear(String frameName) {
        try {
            m_ViewMgr.fwClear(frameName);
        } catch (LogosViewException ex) {
            // this.println( m_ViewMgr.fwList() );
        }
    }

    void hide(String frameName) {
        try {
            m_ViewMgr.fwHide(frameName);
        } catch (LogosViewException ex) {
            System.out.println(m_ViewMgr.fwList());
        }
    }

    void show(String frameName) {
        try {
            m_ViewMgr.fwShow(frameName);
        } catch (LogosViewException ex) {
            System.out.println(m_ViewMgr.fwList());
        }

    }

    public void setHost(String host) {
        this.m_Host = host;
    }

    void shutdown(int status) {
        System.out.println("\n=================================================");
        System.out.println("Shutting Down....");
        System.out.println("CimClientMgr: Closing Resources...");
        shutdownResources();
        System.out.println("CimClientMgr: Resources Closed");
        System.out.println("CimClientMgr: Exiting...");
        System.gc();
        System.out.println("CimClientMgr: Exited");
        System.out.println("Shut Down");
        System.out.println("=================================================");
        System.exit(status);
    }

    private static void shutdownResources() {
        if (m_EventListener != null) {
            m_EventListener.stop();

        }
        if (m_LogosLoopThread != null) {
            try {
                m_LogosLoopThread.join(5000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    protected void finalize() {
        System.out.println("CimClientMgr: Exited via GC");
    }

    private void initConfigs() throws FileNotFoundException {
        m_FWEntityProperties = new Properties();
        try {
            FileInputStream in = new FileInputStream(new File(fwEntityFileName));
            m_FWEntityProperties.load(new BufferedInputStream(in));
        } catch (IOException e) {
        }

    }

    public static LogosFrameMgr getInstance() {
        if (m_Instance == null) {
            m_Instance = new LogosFrameMgr();
        }
        return m_Instance;
    }

    public static LogosGUIComponentMgr getViewMgr() {
        return m_ViewMgr;
    }

    private void initCimClientComponents() {
        m_ViewMgr = LogosGUIComponentMgr.getInstance(this);
        m_LogMgr = HistosMgr.getInstance();
        LogosViewInterface eventInterface = null;

        try {
            eventInterface = m_ViewMgr.getView("alert");
        } catch (LogosViewException ex) {
            ex.printStackTrace();
        }
        fwEntityStore.createEntry(eventInterface, "AlertView", "alert", "no_path", "AlertView", true, true);
        m_EventListener = new LogosEventListener(m_Host, eventInterface);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        logosIn = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        logosReply = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        jLabel1.setText("Logs is Listening...");

        logosIn.setText("<type here>");
        logosIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logosInActionPerformed(evt);
            }
        });

        jButton1.setText("Send");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Logos says: ");

        logosReply.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        logosReply.setText("Temp");

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(logosIn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 293, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jButton1))
                    .add(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jLabel1))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(9, 9, 9)
                        .add(jLabel2)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(logosReply, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 305, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(14, 14, 14)
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(logosIn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jButton1))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(logosReply))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_jButton1ActionPerformed

    private void logosInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logosInActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_logosInActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField logosIn;
    private javax.swing.JLabel logosReply;
    // End of variables declaration//GEN-END:variables
}
