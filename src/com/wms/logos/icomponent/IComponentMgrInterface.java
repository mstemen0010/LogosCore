/*
 * ComponentMgrInterface.java
 *
 * Created on June 1, 2007, 1:48 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.wms.logos.icomponent;

import com.wms.logos.exception.LogosViewException;

/**
 * Interface to allow components to be "pluged in" differently to allow different 
 * configurations
 * @author mstemen
 */
public interface IComponentMgrInterface extends IComponentInterface
{
    String[] getSubComponents();
  
    public void fwSetRoute( IComponentInterface ci_out );

    void fwClear(String viewName) throws LogosViewException;

    void fwHide(String viewName) throws LogosViewException;

    String fwList();

    void fwShow(String viewName) throws LogosViewException;

    
}
