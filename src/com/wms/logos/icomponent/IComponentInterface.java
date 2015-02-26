package com.wms.logos.icomponent;

import com.wms.logos.iframe.gui.FWTargetException;
import com.wms.logos.iframe.gui.FWWriteLevel;


// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.8220528E-98BD-A7C6-A504-5952F49B745B]
// </editor-fold> 
public interface IComponentInterface {

    public IComponentInterface fwGetRoute();

    public void fwWrite( String msg );
    public void fwWrite( String msg, String target ) throws FWTargetException;
    public void fwWrite( Exception e );
    public void fwWrite( Exception e, String target ) throws FWTargetException;

    public void fwWrite( FWWriteLevel level, String msg );
    public void fwWrite( FWWriteLevel level, String msg, String target ) throws FWTargetException;
    public void fwWrite( FWWriteLevel level, Exception e );
    public void fwWrite( FWWriteLevel level, Exception e, String target ) throws FWTargetException;


    public String fwGetName();
}

