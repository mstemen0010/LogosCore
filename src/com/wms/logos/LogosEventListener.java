/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wms.logos;

import com.wms.logos.exception.LogosException;
import com.wms.logos.iframe.gui.FWParameterData;
import com.wms.logos.iframe.gui.FWWriteLevel;
import com.wms.logos.iframe.gui.LogosViewInterface;
import java.util.EventListener;

/**
 *
 * @author Matt
 */
public class LogosEventListener implements EventListener {

    // Stuff for testing Events

    // private CimConnection m_Client = CimClientProxy.CIM_CONNECTION;
    private boolean m_EventReceived = false;
    private String m_Host;
    private LogosViewInterface viewToRouteTo = null;
    private FWParameterData EventParmData = new FWParameterData();
    private String className = null;
    // inner class used to parameterize FW Event notifications and subscriptions

    public LogosEventListener(String m_Host, LogosViewInterface viewInterface) {
        if (viewInterface != null) {
            routeTo(viewInterface);
        }
        className = this.getClass().getName();
        //TODO: this data should come from config files/scheams
        EventParmData.put("CreationClassName", "CIM_ListenerDestinationCIMXML");
        // For the current time being, this is were the filter search string can be changed
        //TODO: allow this to to be config set
//         EventParmData.put("Query", "SELECT * FROM CIM_Event" );
        EventParmData.put("Query", "SELECT * FROM CIM_AlertEvent");
        EventParmData.put("SourceNameSpace", "");


        // setup the interface view info
        viewInterface.initalizeFromFWParams(EventParmData);

//        writeStatus("Initializing CIMEventListener...");
//
//
//        try
//        {
//            writeStatus("Enumerating Instance Filters...");
//            this.enumerateInstances();
//            writeStatus("Enumerated Instance Filters");
//        }
//        catch (CIMException ex)
//        {
//            writeError("Failed to Enumerate Instance Filters", ex );
//        }
//
//        writeStatus("Initalized CIMEventListener");

        writeStatus("========================================");
        writeStatus("Starting EventListener...");
        writeStatus("========================================");
        this.m_Host = m_Host;
        // this.startListening();
       // try {

            writeStatus("Subscribing...");
            // this.subscribe();
            writeStatus("Subscribed");
            writeStatus("========================================");
            writeStatus("Started EventListener");
            writeStatus("========================================");
            writeStatus("");
            // enumerateInstances();
        //} catch (LogosException ex) {
            // writeError("Failed to start EventListener", ex);
       // }
    }

    protected void finalize() {
        System.out.println("CIM Event Listener Exited.");
    }

    /**
     * This method implements the CIMListener interface.  When an event occurs
     * that meets our subscription criteria, it is delivered via a call to this
     * method. <P>
     *
     * CIMEvent event - the Event event that is being delivered.
     */
    public void EventOccured(LogosEvent event) {
        if (event != null) {            
            m_EventReceived = true;
            writeAlert("Received Event: " + event.getEventMessage());
        } else {
            m_EventReceived = false;
            writeStatus("Received Event instance but associated event was null!");
        }
    }

//    /**
//     * Method to enumerate instances of filters, subscriptions and handlers.
//     */
//    public void enumerateInstances() throws CIMException {
//        Enumeration instances;
//        CIMInstance instance;
//        CIMObjectPath classPath;
//        CIMObjectPath instancePath;
//
//        // enumerate filters
//        classPath = new CIMObjectPath("CIM_EventFilter");
//
//        if (m_Client != null) {
//            // enumerate filters
//            instances = m_Client.enumerateInstanceNames(classPath);
//
//            writeStatus("========================================");
//            writeStatus("Enumerated Filters:");
//            writeStatus("========================================");
//            if (!instances.hasMoreElements()) {
//                writeStatus("No filters found");
//            }
//
//            while (instances.hasMoreElements()) {
//                instancePath = (CIMObjectPath) instances.nextElement();
//                instance = m_Client.getInstance(instancePath, false, false,
//                        false, null);
//                writeStatus(instance.toString());
//            }
//
//
//            // enumerate subscriptions
//            classPath = new CIMObjectPath("CIM_EventSubscription");
//            instances = m_Client.enumerateInstanceNames(classPath);
//            writeStatus("========================================");
//            writeStatus("Enumerated Subscriptions:");
//            writeStatus("========================================");
//            if (!instances.hasMoreElements()) {
//                writeStatus("No subscriptions found");
//            }
//            while (instances.hasMoreElements()) {
//                instancePath = (CIMObjectPath) instances.nextElement();
//                instance = m_Client.getInstance(instancePath, false, false,
//                        false, null);
//                writeStatus(instance.toString());
//            }
//
//            // enumerate handlers
//            classPath = new CIMObjectPath("CIM_ListenerDestinationCIMXML");
//            instances = m_Client.enumerateInstanceNames(classPath);
//            writeStatus("========================================");
//            writeStatus("Enumerated Handlers:");
//            writeStatus("========================================");
//            if (!instances.hasMoreElements()) {
//                writeStatus("No handlers found.");
//            }
//            while (instances.hasMoreElements()) {
//                instancePath = (CIMObjectPath) instances.nextElement();
//                instance = m_Client.getInstance(instancePath, false, false,
//                        false, null);
//                writeStatus(instance.toString());
//            }
//        }
//    }

    /**
     * Add ourselves as a listener.
     */
//    private void startListening() {
//        try {
//            writeStatus("Adding EventListener as a CIMListener...");
//            if (m_Client != null) {
//                m_Client.addCIMListener(this);
//                writeStatus("EventListener added as a CIMListener");
//            } else {
//                writeError("Error during add listener, client was null", null);
//            }
//
//        } catch (CIMException e) {
//
//            writeError("Error during add listener:", e);
//        }
//    }

    /**
     * Create a filter identifying what kind of Events we are interested in.  Then, create
     * a handler object, identifying this class as an Event handler.  Finally, associate the
     * handler to the filter by creating a subscription association.
     */
//    private void subscribe() throws CIMException {
//
//        writeStatus("Attempting to Subscribe...");
//        String filterClassName = "CIM_EventFilter";
////      String CreationClassName = CimClientProxy.getSystemName();
//        String systemName = "LSISSI_Provider";
//
//        if (m_Client != null) {
//            CIMClass filterClass = m_Client.getClass(new CIMObjectPath(
//                    filterClassName), true, true, true, null);
//            CIMInstance filterInstance = filterClass.newInstance();
//            // String filterString = "SELECT * FROM CIM_AlertEvent";
//            String filterString = "SELECT * FROM CIM_Event";
//
//
//            // set key properties
//            /* better to let the following two values be set to null */
//            // filterInstance.setProperty("SystemName", new CIMValue("chelsey.ks.lsil.com") );
//            // filterInstance.setProperty("SystemCreationClassName", new CIMValue(CreationClassName));
//            filterInstance.setProperty("SystemName", null);
//            filterInstance.setProperty("SystemCreationClassName", null);
//
//            filterInstance.setProperty("CreationClassName", new CIMValue("CIM_EventFilter"));
//            filterInstance.setProperty("Name", new CIMValue("DeviceStatusFilter"));
//            // set the Namespace
//            filterInstance.setProperty("SourceNamespace", new CIMValue("/root/lsiir12"));
//
//            // non-key but required properties
//            filterInstance.setProperty("Query", new CIMValue(filterString));
//            filterInstance.setProperty("QueryLanguage", new CIMValue("WQL"));
//
//            // NOTE: Since empty CIMObjectPath is passed in, CIMObjectPath to
//            // filterInstance is returned
//
//            writeStatus("------------------- creating filter ----------------------");
//            m_FilterObjectPath = m_Client.createInstance(new CIMObjectPath(),
//                    filterInstance);
//
//            // Create a CIM_EventHandler subclass instance in the target namespace
//            CIMInstance handlerInstance = m_Client.getEventListener(null);
//
//            /* better to let the following two values be set to null */
//            handlerInstance.setProperty("SystemName", null);
//            handlerInstance.setProperty("SystemCreationClassName", null);
//            handlerInstance.setProperty("CreationClassName", new CIMValue("CIM_ListenerDestinationCIMXML"));
//            handlerInstance.setProperty("Name", new CIMValue("TestHandler"));
//
//            // NOTE: Since empty CIMObjectPath is passed in, CIMObjectPath to
//            // handlerInstance is returned
//            writeStatus("------------------- creating handler ----------------------");
//            m_HandlerObjectPath = m_Client.createInstance(new CIMObjectPath(),
//                    handlerInstance);
//
//            // Create an instance of association CIM_EventSubscription in
//            // target namespace to bind the filter to its handler. Doing so causes
//            // CIMOM to invoke the target provider's activateFilter method; i.e. subscribe
//            String subscriptionClassName = "CIM_EventSubscription";
//            CIMClass subscriptionClass = m_Client.getClass(new CIMObjectPath(
//                    subscriptionClassName), true, true, true, null);
//
//            CIMInstance subscriptionInstance = subscriptionClass.newInstance();
//
//            // Set association REFs to point to filter and handler instances
//            subscriptionInstance.setProperty("Filter", new CIMValue(m_FilterObjectPath));
//            subscriptionInstance.setProperty("Handler", new CIMValue(m_HandlerObjectPath));
//            // just to test
//            System.out.println("Filter Path: " + m_FilterObjectPath.toString());
//            System.out.println("Handler Path: " + m_HandlerObjectPath.toString());
//            // subscriptionInstance.setProperty("AlertOnStateChange", new CIMValue("true"));
//
//            // NOTE: Since empty CIMObjectPath is passed in, CIMObjectPath to
//            // subscriptionInstance is returned
//            writeStatus("------------------- creating subscription ----------------------");
//            m_SubscriptionObjectPath = m_Client.createInstance(new CIMObjectPath(),
//                    subscriptionInstance);
//            writeStatus("Subscribe succeeded!");
//
//        } else {
//            this.writeError("Unable to subscribe: m_Client == null", null);
//        }
//    // Useful filters for the future
//    // so this will catch drive fail, drive revive, volume fail, etc.
//    // this is a SMI-S required filter
////     	String filterString = "SELECT * FROM CIM_InstModification WHERE " +
////     	"sourceInstance ISA CIM_LogicalDevice AND " +
////   	   "SourceInstance.HealthState <> PreviousInstance.HealthState";
//
////     	   String filterString = "SELECT * FROM CIM_InstCreation";
//
//    /*     	String filterString = "SELECT * FROM CIM_InstModification WHERE " +
//    "SourceInstance ISA CIM_ComputerSystem AND " +
//    "SourceInstance.OperationalStatus <> PreviousInstance.OperationalStatus";
//     */
//    /*     	String filterString = "SELECT * FROM CIM_InstModification WHERE " +
//    "SourceInstance ISA CIM_StorageVolume AND " +
//    "SourceInstance.OperationalStatus <> PreviousInstance.OperationalStatus";
//     */
//
//    }

    /**
     * Remove all the instances created for this test.
     */
//    public void unsubscribe() {
//        Enumeration instances;
//        CIMObjectPath classPath;
//        CIMObjectPath instancePath;
//
//        try {
//            // delete subscriptions
//            classPath = new CIMObjectPath("CIM_EventSubscription");
//            instances = m_Client.enumerateInstanceNames(classPath);
//            while (instances.hasMoreElements()) {
//                instancePath = (CIMObjectPath) instances.nextElement();
//                m_Client.deleteInstance(instancePath);
//            }
//        } catch (CIMException e1) {
//            e1.printStackTrace();
//        }
//        try {
//            // delete handlers
//            classPath = new CIMObjectPath("CIM_ListenerDestinationCIMXML");
//            instances = m_Client.enumerateInstanceNames(classPath);
//            while (instances.hasMoreElements()) {
//                instancePath = (CIMObjectPath) instances.nextElement();
//                m_Client.deleteInstance(instancePath);
//            }
//        } catch (CIMException e1) {
//            e1.printStackTrace();
//        }
//
//        try {
//            // delete filters
//            classPath = new CIMObjectPath("CIM_EventFilter");
//            instances = m_Client.enumerateInstanceNames(classPath);
//            while (instances.hasMoreElements()) {
//                instancePath = (CIMObjectPath) instances.nextElement();
//                m_Client.deleteInstance(instancePath);
//            }
//        } catch (CIMException e1) {
//            e1.printStackTrace();
//        }
//    }

    private void writeAlert(String message) {
        if (viewToRouteTo != null) {
            viewToRouteTo.fwWrite(FWWriteLevel.INFO, message);
        } else {
            System.out.println(message);
        }

    }

    private void writeStatus(String message) {
        if (viewToRouteTo != null) {
            viewToRouteTo.fwWrite(FWWriteLevel.STATUS, message);
        } else {
            System.out.println(message);
        }

    }

    public void routeTo(LogosViewInterface viewInterface) {
        this.viewToRouteTo = viewInterface;
    }

    public void writeError(String message, Exception e) {
        writeStatus("*** " + message + " ***");
        writeStatus("Error was: ");
        writeStatus("### Begin Error ###");
        if (e != null) {
            writeStatus(e.getMessage());
        } else {
            writeStatus("No error message supplied");
        }
        writeStatus("### End Trans ###");
    }

    public void clearAlerts() {
    }

    public void stop() {
        // unsubscribe();
        //TODO: does this resource need to be threaded?
        // i.e. running = false; at this point?
        System.out.println("     " + className + " : Exiting");
    }

    public void setQueryString(String value) {
    }

    public void setFilterCreationClassName(String value) {
    }

    public void setSystemName(String value) {
    }

    public void setSystemCreationClass(String value) {
    }

    public void setHandlerName(String value) {
    }

    public void setHandlerCreationClassName(String value) {
    }

    public void setSourceNameSpace(String value) {
    }

    public void setHandlerDestination(String value) {
    }

}
