/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.wms.logos.iframe;

import java.util.HashMap;
import java.util.Observer;

/**
 *
 * @author mstemen
 */
public interface IFrameInterface extends Observer {
    
    // enum to "checksum" the id and key, only can be accessed/changed from this package
     
    enum IDomainCheckSum
    {
        Unassigned,
        Experiment,
        View,
        Event,
        Metrics,
        Config;
        
        void Evaluate( IDomainID testId )
        {
            // each value of this against the IDomainID values
        }
    }
           
            
    // each iterface belongs to a unique "Domain"    
    public enum IDomainID
    {
        Unassigned,
        Experiment,
        View,
        Event,
        Metrics,
        Config;
    }
    // used by the object key to validate and create keys
    public enum IDomainKey
    {               
        Unassigned,
        Experiment,
        View,
        Event,
        Metrics,
        Config;
        
        HashMap<IDomainKey, Class> classAssocList = new HashMap<IDomainKey, Class>();
        
        public void setClassMgrForDomain(Class classToSet )
        {
            IDomainKey keyValue = this;
            if( ! classAssocList.containsKey( keyValue ))
            {
                
            }
            else
            {
                //TODO: need to thown some kind of "no reset" error
            }
        }
    }
    
    public IDomainKey getIKeyID();
   
    public void setKeyID(Class classToSet);
    
}
