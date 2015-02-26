/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wms.logos.iworker;

import com.wms.logos.icomponent.IComponentInterface;
import com.wms.logos.iframe.gui.FWTargetException;
import com.wms.logos.iframe.gui.FWWriteLevel;
import java.lang.reflect.InvocationTargetException; 
import java.util.Observable;
import java.lang.reflect.Method;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mstemen
 */
public class IWorker extends Observable implements Runnable, IWorkerInterface, IComponentInterface, Observer
{

    public IComponentInterface fwGetRoute() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void fwWrite(String msg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void fwWrite(String msg, String target) throws FWTargetException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void fwWrite(Exception e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void fwWrite(Exception e, String target) throws FWTargetException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void fwWrite(FWWriteLevel level, String msg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void fwWrite(FWWriteLevel level, String msg, String target) throws FWTargetException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void fwWrite(FWWriteLevel level, Exception e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void fwWrite(FWWriteLevel level, Exception e, String target) throws FWTargetException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String fwGetName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public enum GenWorkerType
    {
        Simple, // is Runnable through an owner thread;        
        Deamon, // will create own thread and run in background
        SelfStarter; // will create own thread with it returns to the creating object
    }

    public enum GenWorkerPrototype
    {
        Unknown, // default, uninit 
        Basic, /// the worker only has one method to run again and again
        Complex; // the worker has a sequence of methods to run again and again                           
    }
    private boolean running = false;
    private Object bossObject;
    private Class classToUse = null;
    private Method methodToUse = null;
    private String methodNameToCall = null;
    String className;
    int sleepTime = 5000;
    Class[] types = new Class[]{};
    boolean isRunning = false;
    private String workerName = null;
    private IWorkerBossInterface ownerInterface;
    private GenWorkerPrototype workerProtoType;
    static public IWorkerInterface genWorkerFactory(IWorkerContract contract) throws NoSuchMethodException
    {
        IWorker newWorker = new IWorker(contract.interfaceToCallBack, contract.calledObject, contract.className, contract.classToUse, contract.methodNameToCall, contract.sleepTime);
        return newWorker;
    }
    // Constructor for method with no arguments
    private IWorker(IWorkerBossInterface ownerInterface, Object objectToUse, String classTypeStr, String classInstNameStr, String methodName, int sleepTime) throws NoSuchMethodException
    {
        this.ownerInterface = ownerInterface;
        types = null;
        workerName = new String(classInstNameStr + "$$" + this.hashCode());
        init(objectToUse, classTypeStr, classInstNameStr, methodName, sleepTime);
    }
    private IWorker(IWorkerBossInterface ownerInterface, Object objectToUse, String classTypeStr, String classInstNameStr, String methodName, Class[] types, int sleepTime) throws NoSuchMethodException
    {
        this.ownerInterface = ownerInterface;
        this.types = types;
        // use reflection to access the class
        init(objectToUse, classTypeStr, classInstNameStr, methodName, sleepTime);
    }
    private void init(Object objectToUse, String classTypeStr, String classInstNameStr, String methodName, int sleepTime) throws NoSuchMethodException
    {
        running = true;
        this.sleepTime = sleepTime;
        bossObject = objectToUse;

        workerName = new String("::" + classInstNameStr + "$$" + this.hashCode() + "GenWorker::");
        // use reflection to access the class and method
        Class testClass = null;
        try {
            testClass = Class.forName(classTypeStr);
        }
        catch (ClassNotFoundException ex) {
            logMessage(ex.getMessage());
            Logger.getLogger(IWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (testClass != null && testClass.isInstance(bossObject)) {
            methodToUse = bossObject.getClass().getMethod(methodName, types);
        }
    }

    public void run()
    {
        while (running) {
            Object retObject = null;
            if (methodToUse != null) {
                try {
                    // retObject = methodToUse.invoke(bossObject, (Object []) null);
                    if (types == null) {
                        retObject = methodToUse.invoke(bossObject, (Object[]) null);
                    }
                    else {
                        retObject = methodToUse.invoke(bossObject, (Object[]) types);
                    }
                    if (retObject != null) {
                        setChanged();
                        notifyObservers(retObject);
                    }
                }


                catch (IllegalAccessException ex) {
                    logMessage(ex.getMessage());
                    Logger.getLogger(IWorker.class.getName()).log(Level.SEVERE, null, ex);
                }                catch (IllegalArgumentException ex) {
                    logMessage(ex.getMessage());
                    Logger.getLogger(IWorker.class.getName()).log(Level.SEVERE, null, ex);
                }                catch (InvocationTargetException ex) {
                    logMessage(ex.getMessage());
                    Logger.getLogger(IWorker.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            try {
                Thread.sleep(sleepTime);
            }
            catch (InterruptedException ex) {
                logMessage(ex.getMessage());
                Logger.getLogger(IWorker.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        logMessage("Worker exiting...");
    }
    public boolean isWorkerRunning()
    {
        return isRunning;
    }
    public boolean checkForExit()
    {
        if (!running) {
            logMessage("Exited");
        }
        return !running;
    }
    @Override
    protected void finalize()
    {
        logMessage("Worker done!");
    }
    public void setRunState(boolean newRunState)
    {
        running = newRunState;
        isRunning = running;
    }
    public String getWorkerName()
    {
        return workerName;
    }
    public void logMessage(String msg)
    {
        StringBuilder sb = new StringBuilder(workerName);
        sb.append(": ").append(msg);
        if (ownerInterface != null) {
            ownerInterface.logMessage(sb.toString());
        }
    }
   
    public void addObserver(IWorkerBossInterface bossIterface)
    {
        super.addObserver(bossIterface);
    }
    public void update(Observable o, Object arg)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
