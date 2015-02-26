package com.wms.logos.iframe;

import com.wms.logos.iframe.IFrameMgrType.IType;


// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.20B228ED-B4AD-439C-8918-8EEAAEA025FC]
// </editor-fold> 
public interface IFrameMgrType {
    
    public enum IType
    {
        Smart,
        Smarter,
        Smartist;
        
        IType it; 
        
        public IType getType()
        {
            return it;
        }
        
        public void setType( IType t )
        {
            it = t;
        }
        
        IType( IType t)
        {
            it = t;
        }
        // do not allow contruct of with setting it
        IType()
        {
            setType(this);
        }
    } 
}

