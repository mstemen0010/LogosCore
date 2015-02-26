package com.wms.logos.iframe;

import java.util.ArrayList;
// </editor-fold> 
public interface IFrameMgrInterface extends IFrameMgrType {

    public enum IMode
    {
        Mode1,
        Mode2,
        Mode3; // no real values in this, we are just a wrapper of a type
        
        IType it = IType.Smart;
        IMode im = null;
        
        IMode()
        {      
            im = this;
        }
        IMode(IMode newMode)
        {
            setMode(newMode);
        }
        public IType getType() 
        {
            return it;
        }
        
        public void setType( IType t )
        {
            it = t;
        }
        
        public IType value()
        {
            return it;
        }
        public IMode getMode()
        {
            return im;
        }
        public void setMode( IMode m )
        {
            setModeType( m );            
        }
        private void setModeType( IMode m )
        {
            switch( m.getMode() )
            {
            case Mode1:
                it = IType.Smart;
                break;            
            case Mode2:
                it = IType.Smarter;
                break;            
            case Mode3:
                it = IType.Smartist;
                break;
            }
        }
        
    }    
    // test to "extend" the parent Interfaces "types"
    public enum EType 
    {
       
        Extend1,
        Extend2;
        
        int minValue = 0;
        int maxValue = 0;
        
        public Object[] getValues()
        {
            minValue = IType.values().length;                        
            maxValue = minValue + this.values().length;
            
            IType[] parentTypes = IType.values();
            EType[] myTypes = this.values();
            maxValue = minValue + myTypes.length;
            int r[] = null;
            ArrayList a = new ArrayList();
            // copy our parents values
            for( int i = 0; i < minValue; i++ )
            {
                a.add( myTypes[i]);
            }
            // add our values
            for( int i = minValue; i < maxValue; i++ )
            {
                a.add( parentTypes[i]);
            }
                                                  
            return a.toArray();
        }
        
        public int getMax()
        {
            return maxValue;
        }
        
        public int getMin()
        {
            return minValue;
        }
        
        public IType getValue( int i )
        {
            IType t = null;
            IType[] parentTypes = IType.values();
            if( i < maxValue )
            {
                t = parentTypes[i];
            }
            
            return t;
        }
                
    }
    
    IType getType();    
    void setType( IType t ); 
                      
    IType iType = IMode.Mode1.it;
    IMode iMode = IMode.Mode1;
    EType eType = EType.Extend1;
   

}

