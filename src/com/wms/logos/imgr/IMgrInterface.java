package com.wms.logos.imgr;

import java.util.Observer;


// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.9D46C9B5-E41C-AAF6-4CD6-CEAD058BCBEF]
// </editor-fold> 
public interface IMgrInterface extends Observer {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.67F329A2-09AA-7C54-838C-9A33BF1C9023]
    // </editor-fold> 
    public enum IMgrType
    {
        Base,
        Queue,
        Event,
        Config,
        Procedural,
        Metrics,
        View,        
    }
            
    IMgrType getIMgrType();
            

}

