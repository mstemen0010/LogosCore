/*
 * LogosSimpleLogView.java
 *
 * Created on May 23, 2005, 5:39 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.wms.logos.util;
import java.util.*;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author mstemen
 */

/*
 */


class LogosSimpleLogCellRenderer extends JLabel implements ListCellRenderer {
     final static ImageIcon longIcon = new ImageIcon("long.gif");
     final static ImageIcon shortIcon = new ImageIcon("short.gif");

     private boolean useColor = false;
     private boolean useGlyphs = false;
     // This is the only method defined by ListCellRenderer.
     // We just reconfigure the JLabel each time we're called.

     public Component getListCellRendererComponent(
       JList list,
       Object value,            // value to display
       int index,               // cell index
       boolean isSelected,      // is the cell selected
       boolean cellHasFocus)    // the list and the cell have the focus
     {
         LogosSimpleLogEntry newEntry = null;
         if( LogosSimpleLogEntry.class.isInstance( value ))
         {
             newEntry = LogosSimpleLogEntry.class.cast(value);
         }
         if( value == null )
         {
             return this;
         }
         String s = value.toString();
         if( s != null )
         {
            setText(s);
            setIcon((s.length() > 10) ? longIcon : shortIcon);
            if (isSelected) {
                 setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            }
            else {
                   if( newEntry != null && useColor )
                    {
                        setBackground(newEntry.getBackground());
                        setForeground(newEntry.getForeground());

                    }
                    else
                    {
                        setBackground(list.getBackground());
                        setForeground(list.getForeground());
                    }                   
                    if( useGlyphs )                        
                        setIcon( newEntry.getIcon() );               
            }
         }
           setEnabled(list.isEnabled());
           setFont(list.getFont());
         setOpaque(true);
         return this;
     }
     
     public void useColor( boolean flag )
     {          
         useColor = flag;
     }
     
     public void useGlyphs( boolean flag )
     {
         useGlyphs = flag;
     }
 }



class LogosSimpleLogModel extends DefaultListModel
{
    // class to only allow LogosSimpleLogEntry into the view
    Stack<LogosSimpleLogEntry> store = new Stack<LogosSimpleLogEntry>();
    int currentLineCount = 0;
    int lineCountMax = 0;
    public LogosSimpleLogModel()
    {
        store.setSize(10);
        lineCountMax = 10;
    }
    
    public synchronized void add( int index, LogosSimpleLogEntry entry  )
    {
        append( entry );
        //  store.add(  entry );
        // super.add( index, entry );
    }

    public synchronized void addElement( LogosSimpleLogEntry entry  )
    {        
        append( entry );
        //  store.add(  entry );
        // super.add( index, entry );
    }
    
    public synchronized LogosSimpleLogEntry lastElement()
    {
        return store.lastElement();
    }
    
    
    private synchronized void append( LogosSimpleLogEntry entry )
    {
        try
        {
            currentLineCount++;
            if( currentLineCount > lineCountMax )
            {
                store.pop();
            }        
            store.push( entry );
            // store.addElement( entry );        
        }
        catch( OutOfMemoryError e )
        {
//            SCPMgr.getMgrLog().write( "Out of memory in LogosSimpleLogView");
        }
    }
    
    public synchronized LogosSimpleLogEntry remove( int index )
    {
        Object entry = super.remove(index);
        LogosSimpleLogEntry logEntry = null;
        if( LogosSimpleLogEntry.class.isInstance( entry ))
            logEntry = LogosSimpleLogEntry.class.cast(entry);
            
        
        return logEntry;
    }
        
    public synchronized LogosSimpleLogEntry getElementAt( int index )
    {
        
        return store.elementAt(index);
    }
    
    public synchronized void setMaxLines( int newMax )
    {
        lineCountMax = newMax;
        store.setSize(newMax);
        
        
    }
    
    public synchronized int getCurrentLines()
    {
        return currentLineCount;
    }
    
    public synchronized Iterator<LogosSimpleLogEntry> iterator()
    {
        return store.iterator();
    }
  
    public synchronized int getSize()
    {
        return store.size();
    }
    
}

public class LogosSimpleLogView extends JList implements Scrollable
{
    
    /** Creates a new instance of LogosSimpleLogView */
    private DefaultListModel listModel = null;
    private int maxListSize = 10;
    private int lowestLine = 0;
    private int maxHeight = 0;
    private int maxWidth = 0;
    private int highestLine = maxListSize;
    private int listElements = 0;
    private LogosSimpleLogModel logModel = new LogosSimpleLogModel();
            
    private boolean updating = false;
    
    private Icon[] logGylphs = new Icon[ LogosSimpleLog.getNumLogStates()];
    private String[] logStatesMap = new String[ LogosSimpleLog.getNumLogStates() ];
    private LogosSimpleLogCellRenderer renderer = new LogosSimpleLogCellRenderer();

    private LogosSimpleLog log = null;
        
    public LogosSimpleLogView( LogosSimpleLog log )
    {        
		super();
                this.log = log;
        listModel = new DefaultListModel();
        super.setModel( listModel );
        // logModel.addListDataListener(this);
        //= new JList( listModel );
        setSelectionBackground( Color.YELLOW);
        setCellRenderer( renderer );   
        setBackground( Color.WHITE );
        setDoubleBuffered( true );
    }
    
    public void paintComponent( Graphics g )
    {
      
        int tries = 100;
        while( updating )
        {
//            SCPMgr.getMgr().getLog().write( LogosSimpleLog.WARNING, "The Modeling is being updated, waiting...");
            try
            {
                tries--;
                Thread.sleep(100);
                if( tries <= 0 )
                    return;
            }
            catch( InterruptedException e )
            {
                
            }
        }
        try
        {
            super.paintComponent(g);
        }
        catch( ArrayIndexOutOfBoundsException e )
        {
            log.write( "paint error:  " + e.toString() );
        }
    }
    
    public void init()
    {
        setInitLogStateMap();
    }
    private void setInitLogStateMap()
    {
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
        logStatesMap[LogosSimpleLog.DEBUG] = LogosSimpleLog.getLevelStr( LogosSimpleLog.DEBUG );
        logStatesMap[LogosSimpleLog.FATAL] = LogosSimpleLog.getLevelStr( LogosSimpleLog.FATAL );
        logStatesMap[LogosSimpleLog.GUI_TRACE] = LogosSimpleLog.getLevelStr( LogosSimpleLog.GUI_TRACE );
        logStatesMap[LogosSimpleLog.ISO] = LogosSimpleLog.getLevelStr( LogosSimpleLog.ISO );
        logStatesMap[LogosSimpleLog.LOG_OFF] = LogosSimpleLog.getLevelStr( LogosSimpleLog.LOG_OFF);
        logStatesMap[LogosSimpleLog.MESSAGE] = LogosSimpleLog.getLevelStr( LogosSimpleLog.MESSAGE );
        logStatesMap[LogosSimpleLog.TRACE] = LogosSimpleLog.getLevelStr( LogosSimpleLog.TRACE );
        logStatesMap[LogosSimpleLog.WARNING] = LogosSimpleLog.getLevelStr( LogosSimpleLog.WARNING );
        logStatesMap[LogosSimpleLog.CRITICAL] = LogosSimpleLog.getLevelStr( LogosSimpleLog.CRITICAL );
        logStatesMap[LogosSimpleLog.ERROR] = LogosSimpleLog.getLevelStr( LogosSimpleLog.ERROR );
                
        logGylphs[LogosSimpleLog.DEBUG] = LogosSimpleLog.getLevelIcon( LogosSimpleLog.DEBUG );
        logGylphs[LogosSimpleLog.FATAL] = LogosSimpleLog.getLevelIcon( LogosSimpleLog.FATAL );
        logGylphs[LogosSimpleLog.GUI_TRACE] = LogosSimpleLog.getLevelIcon( LogosSimpleLog.GUI_TRACE );
        logGylphs[LogosSimpleLog.ISO] = LogosSimpleLog.getLevelIcon( LogosSimpleLog.ISO );
        logGylphs[LogosSimpleLog.LOG_OFF] = LogosSimpleLog.getLevelIcon( LogosSimpleLog.LOG_OFF);
        logGylphs[LogosSimpleLog.MESSAGE] = LogosSimpleLog.getLevelIcon( LogosSimpleLog.MESSAGE );
        logGylphs[LogosSimpleLog.TRACE] = LogosSimpleLog.getLevelIcon( LogosSimpleLog.TRACE );
        logGylphs[LogosSimpleLog.WARNING] = LogosSimpleLog.getLevelIcon( LogosSimpleLog.WARNING );
        logGylphs[LogosSimpleLog.CRITICAL] = LogosSimpleLog.getLevelIcon( LogosSimpleLog.CRITICAL );
        logGylphs[LogosSimpleLog.ERROR] = LogosSimpleLog.getLevelIcon( LogosSimpleLog.ERROR );
                
    }
    public void setListMaxSize( int newSize )
    {
        maxListSize = newSize;
        highestLine = newSize;
    }
    
    public boolean getScrollableTracksViewportHeight()
    {
        return true;
    }
    
    public boolean getScrollableTracksViewportWidth()
    {
        return true;
    }
    
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction)    
    {
        return 1;
    }
    
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction)    
    {
        return 1;
    }
    
    public Dimension getPreferredScrollableViewportSize()
    {
        return new Dimension( maxWidth, maxHeight);
    }
    
    public void insertEntries( ArrayList<String> entries )
    {
        maxListSize = entries.size() - 1;
        Iterator<String> it = entries.iterator();
        int counter = 0;
        while( it.hasNext() )
        {
            // LogosSimpleLogEntry entry = new LogosSimpleLogEntry( String.valueOf( counter++ ) + " :: "  + it.next() );
            LogosSimpleLogEntry entry = new LogosSimpleLogEntry(  it.next() );
            setupEntry( entry );
            // add( entry );
            addEntry( entry );
            // listModel.addElement(entry);
        }
        
        validate();
    }
    
    private LogosSimpleLogEntry setupEntry( LogosSimpleLogEntry newEntry )
    {
        if( newEntry.getHeight() > maxHeight )
            maxHeight = newEntry.getHeight();
        if( newEntry.getWidth() > maxWidth )
            maxWidth = newEntry.getWidth();
        if( newEntry.getText() == null )
            return newEntry;
        
        if( newEntry.getText().contains( logStatesMap[LogosSimpleLog.GUI_TRACE]))
        {
                newEntry.setLevel( LogosSimpleLog.GUI_TRACE );
                newEntry.setForeground( log.getLogLevelFg( LogosSimpleLog.GUI_TRACE ));
                newEntry.setBackground( log.getLogLevelBg( LogosSimpleLog.GUI_TRACE ));
                newEntry.setIcon( LogosSimpleLog.getLevelIcon(LogosSimpleLog.GUI_TRACE) );
        }        
        else if( newEntry.getText().contains( logStatesMap[LogosSimpleLog.DEBUG]))
        {       
                newEntry.setLevel( LogosSimpleLog.DEBUG );
                newEntry.setForeground( log.getLogLevelFg( LogosSimpleLog.DEBUG ));
                newEntry.setBackground( log.getLogLevelBg( LogosSimpleLog.DEBUG ));
                newEntry.setIcon( LogosSimpleLog.getLevelIcon(LogosSimpleLog.DEBUG) );
        }
        else if( newEntry.getText().contains( logStatesMap[LogosSimpleLog.CRITICAL]))
        {       
                newEntry.setLevel( LogosSimpleLog.CRITICAL );
                newEntry.setForeground( log.getLogLevelFg( LogosSimpleLog.CRITICAL ));
                newEntry.setBackground( log.getLogLevelBg( LogosSimpleLog.CRITICAL ));
                newEntry.setIcon( LogosSimpleLog.getLevelIcon(LogosSimpleLog.FATAL) );
        }
        else if( newEntry.getText().contains( logStatesMap[LogosSimpleLog.ERROR]))
        {       
                newEntry.setLevel( LogosSimpleLog.CRITICAL );
                newEntry.setForeground( log.getLogLevelFg( LogosSimpleLog.ERROR ));
                newEntry.setBackground( log.getLogLevelBg( LogosSimpleLog.ERROR ));
                newEntry.setIcon( LogosSimpleLog.getLevelIcon(LogosSimpleLog.FATAL) );
        }
        
        else if( newEntry.getText().contains( logStatesMap[LogosSimpleLog.FATAL]))
        {
                newEntry.setLevel( LogosSimpleLog.FATAL );
                newEntry.setForeground( log.getLogLevelFg( LogosSimpleLog.FATAL ));
                newEntry.setBackground( log.getLogLevelBg( LogosSimpleLog.FATAL ));
                newEntry.setIcon( LogosSimpleLog.getLevelIcon(LogosSimpleLog.DEBUG) );
        }

        else if( newEntry.getText().contains( logStatesMap[LogosSimpleLog.ISO]))
        {                
                newEntry.setLevel( LogosSimpleLog.GUI_TRACE );
                newEntry.setForeground( log.getLogLevelFg( LogosSimpleLog.ISO ));
                newEntry.setBackground( log.getLogLevelBg( LogosSimpleLog.ISO ));
                newEntry.setIcon( LogosSimpleLog.getLevelIcon(LogosSimpleLog.ISO) );
        }
        else if( newEntry.getText().contains( logStatesMap[LogosSimpleLog.LOG_OFF]))
        {       
                newEntry.setLevel( LogosSimpleLog.LOG_OFF );
                newEntry.setForeground( log.getLogLevelFg( LogosSimpleLog.LOG_OFF ));
                newEntry.setBackground( log.getLogLevelBg( LogosSimpleLog.LOG_OFF ));
                newEntry.setBackground( Color.BLACK);            
                newEntry.setIcon( LogosSimpleLog.getLevelIcon(LogosSimpleLog.LOG_OFF) );
        }
        else if( newEntry.getText().contains( logStatesMap[LogosSimpleLog.MESSAGE]))
        {                
                newEntry.setLevel( LogosSimpleLog.MESSAGE );
                newEntry.setForeground( log.getLogLevelFg( LogosSimpleLog.MESSAGE ));
                newEntry.setBackground( log.getLogLevelBg( LogosSimpleLog.MESSAGE ));
                newEntry.setIcon( LogosSimpleLog.getLevelIcon(LogosSimpleLog.MESSAGE) );
        }
        else if( newEntry.getText().contains( logStatesMap[LogosSimpleLog.TRACE]))
        {
                newEntry.setLevel( LogosSimpleLog.TRACE );
                newEntry.setForeground( log.getLogLevelFg( LogosSimpleLog.TRACE ));
                newEntry.setBackground( log.getLogLevelBg( LogosSimpleLog.TRACE ));
                newEntry.setIcon( LogosSimpleLog.getLevelIcon(LogosSimpleLog.TRACE) );
        }
        else if( newEntry.getText().contains( logStatesMap[LogosSimpleLog.WARNING]))
        {
                newEntry.setLevel( LogosSimpleLog.WARNING );
                newEntry.setForeground( log.getLogLevelFg( LogosSimpleLog.WARNING ));
                newEntry.setBackground( log.getLogLevelBg( LogosSimpleLog.WARNING ));
                newEntry.setIcon( LogosSimpleLog.getLevelIcon(LogosSimpleLog.WARNING) );
        }
        
        return newEntry;
    }

    public synchronized void addEntry( LogosSimpleLogEntry newEntry )
    {
        updating = true;		
        invalidate();
        setupEntry( newEntry );
        if( listModel.size() < maxListSize )
            listModel.addElement(newEntry);
        else
        {
            shuffleIn( newEntry );
        }
       //  if( listModel.size() > 0)
       //     ensureIndexIsVisible( listModel.size() - 1);
        updating = false;
        validate();
    }
    private synchronized void shuffleIn( LogosSimpleLogEntry newEntry )
    {
        updating = true;
        DefaultListModel tempModel = new DefaultListModel(  );
        listModel.remove( maxListSize - 1 );
        
        for( int i =  0; i < maxListSize - 1; i++ )
        {
            LogosSimpleLogEntry currentEntry = (LogosSimpleLogEntry) listModel.getElementAt(i + 1);
            
            listModel.setElementAt( currentEntry, i );            
            // currentEntry.setText( String.valueOf( i ) + " " + currentEntry.getText() );
        }        
        listModel.insertElementAt( newEntry, this.maxListSize - 2 );
        updating = false;
        // newEntry.setText( String.valueOf( maxListSize - 1 ) + "::" + newEntry.getText() );
    }
    
    public JList getList()
    {
        return this;
    }
    /*
    public void addEntry( LogosSimpleLogEntry newEntry )
    {
             
        setupEntry( newEntry );
        logModel.addElement( newEntry );
        
        // shuffle 'up' the existing elements 
        
        if( listElements < maxListSize)
        {
            add( newEntry, listElements++ );
        }
        else
        {
            Component [] comps = getComponents();
            //removeAll();
            for( int i = 0; i < comps.length; i++ )
            {
           
            }
        }
        
        // add( logModel.lastElement(), this.maxListSize - 1 ); // put the new entry in the highest ( last/lowest) list element
        
        
        
        // add( newEntry );
        // validate();
        // repaint();
        // add( logModel.lastElement());
        
    }
    */
    private void shuffleUp()
    {
        // setVisible(false);
        removeAll(); // remove the top element(line)
        Iterator<LogosSimpleLogEntry> it = logModel.iterator();
        while( it.hasNext() )
        {
            LogosSimpleLogEntry entry = it.next();
            if( entry != null )
                add( entry );
        }
//        SCPMgr.getMgr().sendStatusMessage( "Line Count is: " + logModel.getCurrentLines() + " store size is: " + logModel.getSize() );
        // setVisible(true);
    }
    
    public void setMaxLines( int maxLines )
    {
        logModel.setMaxLines( maxLines );
        this.maxListSize = maxLines;
    }
    
    public void setUseColor( boolean flag )
    {
        renderer.useColor(flag);
        repaint();
    }
    public void setUseGlyphs(  boolean flag )
    {
        renderer.useGlyphs(flag);
        repaint();
    }
    
    public void refreshColors()
    {
        Enumeration it = listModel.elements();
        while( it.hasMoreElements() )
        {
            LogosSimpleLogEntry entry = (LogosSimpleLogEntry) it.nextElement();
            
            if( entry != null )
            {
                entry.setForeground( log.getLogLevelFg(entry.getLevel()));
                entry.setBackground( log.getLogLevelBg(entry.getLevel()));
                entry.validate();
            }
        }        
    }
}
