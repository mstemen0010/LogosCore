/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.wms.logos.iworker;

import java.lang.reflect.Method;

/**
 *
 * @author mstemen
 */
public final class IWorkerContract {    
    IWorker.GenWorkerType workerType;
    IWorker.GenWorkerPrototype protoType;
    
    IWorkerBossInterface interfaceToCallBack;
    Object calledObject;
    String classToUse = null;
    Method methodToUse = null;
    String methodNameToCall = null;
    String className;
    int sleepTime = 5000;
    
    public IWorkerContract( IWorkerBossInterface interfaceToCallBack, IWorker.GenWorkerType workerType, IWorker.GenWorkerPrototype protoType,  Object calledObject, String classToUse, String methodNameToCall, String owningClassName, int sleepTime )
    {
        this.workerType = workerType;
        this.protoType = protoType;
        this.calledObject = calledObject;
        this.classToUse = classToUse;
        this.methodNameToCall = methodNameToCall;
        this.className = owningClassName;
        this.sleepTime = sleepTime;                     
    }
}
