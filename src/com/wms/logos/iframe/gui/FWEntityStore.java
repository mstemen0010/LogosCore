/*
 * FWEntityStore.java
 *
 * Created on June 21, 2007, 4:27 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.wms.logos.iframe.gui;

import com.wms.logos.icomponent.IComponentInterface;
import java.util.*;

/**
 *
 * @author mstemen
 */

/**
 * Creates a new instance of FWEntityStore
 */
public class FWEntityStore extends HashSet
{
    public FWEntity createEntry( IComponentInterface ci, String key, String name, String path, String className, boolean aust, boolean aush )
    {
        FWEntity newFwEntity = new FWEntity(ci, key, name, path, className, aust, aush );
        this.add( newFwEntity );
        
        return newFwEntity;
    }
    
    public class FWEntity
    {
        private IComponentInterface fwEntityInterface;
        private String key;
        private String name;
        private String path;
        private String className;
        
        private boolean autoStart;
        private boolean autoShow;
        
        public FWEntity( IComponentInterface ci, String key, String name, String path, String className, boolean aust, boolean aush )
        {
            
        }
        
        public IComponentInterface getFwEntityInterface()
        {
            return fwEntityInterface;
        }
                
        public String getKey()
        {
            return key;
        }
                
        public String getName()
        {
            return name;
        }
                
        public String getPath()
        {
            return path;
        }
        
        public String getClassName()
        {
            return className;
        }
        
        public boolean isAutoStart()
        {
            return autoStart;
        }
        
        public boolean isAutoShow()
        {
            return autoShow;
        }
    }
}

