/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wms.logos;

import com.wms.logos.exception.LogosException;
import com.wms.logos.exception.LogosViewException;
import com.wms.logos.iframe.gui.LogosViewInterface;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Matt
 */
public class LogosRunLoop implements Runnable {
    // This will hold one instance of each command that is available

    private HashMap<String, LogosCommand> m_Commands = null;

    // Sorted array of all the commands that are available
    private String[] m_SortedCommands = null;

    // These 5 variable are the defaults.  They are overwritten though by whatever is
    //   specified in the cimclient.config file
    public static String m_Host = "172.23.4.79";
    public static String m_HostStr = "";
    public static String m_Namespace = "/root/lsissi11";
    public static String m_NamespaceInterop = "/root";
    public static String m_UserName = "root";
    public static String m_Password = "user";

    // The connection will be used to make calls through the JWS adapter
    // public static CimConnection CIM_CONNECTION;
    public static LogosGUIComponentMgr viewMgr = null;
//    private int traceLevel = ClientConstants.TRACE_VERBOSE;
    private LogosFrameMgr m_LogosFrameMgr;
    private boolean running = true; // for the "while 1" loop

    // property files keys
    private static final String INI_FILE_NAME = "cimclient.conf";
    private static final String HOST_PROPERTY = "host";
    private static final String NAMESPACE_PROPERTY = "namespace";
    private static final String INTEROP_PROPERTY = "namespaceinterop";
    private static final String USER_PROPERTY = "username";
    private static final String PASSWORD_PROPERTY = "password";

    public LogosRunLoop(String configName) {
        m_LogosFrameMgr = LogosFrameMgr.getInstance();
        // Set Trace Level
//        Trace.setTraceLevel(traceLevel);
        // this is merely for user feedback, this value may not reflect a true host name
        m_HostStr = configName;
        this.println("You may type \"help\" at anytime");
        this.println("For usage, type the command name followed by \"usage\"");

        // Initialize all of the cimom parametrs from the cimconfig file
        initializeCimomParameters(configName);
        viewMgr = LogosFrameMgr.getViewMgr();
        m_LogosFrameMgr.setHost(m_Host);

//      viewMgr.start();

        // Try to make a connection to the CIMOM
        try {
//            CIM_CONNECTION = new CimConnection(m_Host, m_Namespace, m_UserName, m_Password);
//            // We need to take the port off the host if it is there.
//            if(m_Host.split(":").length > 0)
//            {
//                m_Host = m_Host.split(":")[0];
//            }
        } catch (Exception c) {
            this.println("\n=================================================");
            this.println("Cannot connect to CIMOM");
            this.println("Error was: " + c.getMessage());
            this.println("=================================================");
            this.println("Attempted Connection Info:");
            this.println("  " + "Host: " + m_Host);
            this.println("  " + "Hostname: " + m_HostStr);
            this.println("  " + "Interop: " + m_NamespaceInterop);
            this.println("  " + "Namespace: " + m_Namespace);
            this.println("  " + "UserName: " + m_UserName);
            this.println("  " + "Password: " + m_Password);
            this.println("Error is Fatal, Exiting...");
//            m_CimClientMgr.shutdown(-1);
        }

        // Initialize all of the commands from the properties file
        LogosViewInterface eventInterface = null;
        try {
            eventInterface = viewMgr.getView("alert");
        } catch (LogosViewException ex) {
            Logger.getLogger(LogosRunLoop.class.getName()).log(Level.SEVERE, null, ex);
        }


        initializeCommands();
    }

    protected void finalize() {
        System.out.println("CimClient Exited");
    }

    /**
     * DESCRIPTION: Starts the read-parse-process loop.
     *
     * NOTES/ASSUMPTIONS: NONE
     *
     */
    public void run() {
        String output;

        while (running) {
            try {
                output = readInput(30);
                if (output != null) {
                    this.println(output);
                }
            } catch (Exception e) {
//                Trace.debug(e.getDescription());
//                Trace.debug(e);
            }
        }
        System.out.println("CimClient exiting...");
    }

    /**
     * DESCRIPTION: Read in user input
     * <P>
     *
     * NOTES/ASSUMPTIONS: NONE
     * <BR>
     *
     * @return String - Returns results from calling the command
     * @throws LogosException
     */
    public String readInput(int timeout) throws LogosException {
        System.out.println();
        System.out.print(m_Host + "> ");
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        String input = null;
//      int countDownTime = timeout;
        try {
//         while( countDownTime > 0 )
            {
//             if( userInput.ready() )
                {
                    input = userInput.readLine();
//                break;
                }
//            Thread.sleep( 1000 ); // sleep one second
//            countDownTime--;
            }
//         if( countDownTime <= 0 )
//         {
//             return new StringBuffer("Command timed out after: ").append( timeout ).append(" secs").toString();
//         }

        } //      catch( InterruptedException e )
        //      {
        //
        //      }
        catch (IOException ioe) {
            this.println("IO error trying to read command");
            exit(1);
        }

        // if the given input is null or if it is all whitespace, ignore it and
        // try again
        if (input == null || input.matches("(\\s)*")) {
            return null;
        }

        return parseInput(input);
    }

    /**
     * DESCRIPTION: Runs a batch file of commands
     * <br>
     * <br>
     * NOTES/ASSUMPTIONS: A batch file is very easy to write.  It's just a text file with
     *   a set of commands separated by new lines.  The downside is that you cannot catch
     *   feedback from the client - thus not being ablt to conditionally change actions based on
     *   feedback...since you don't get any.  To do this, use the test hardness.
     * <br>
     * @param fileName - Name of batch file to run
     * @throws LogosException
     */
    public void runBatchFile(String fileName) throws LogosException {
        try {
            //Connect to file
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            this.println("");
            this.println("**Running batch file: " + fileName + " **\n");
            String line = readLine(br);

            // Walk through every line in the file and run it through
            while (line != null) {
                parseInput(line);
                line = readLine(br);
            }

            this.println("\n**Finished running batch file \"" + fileName + "\" **\n");
        } catch (IOException ioe) {
            this.println("Trouble connecting to the batch file: \"" + fileName + "\"");
        }
    }

    /**
     * DESCRIPTION: Parses the input given by the user
     * <br>
     * <br>
     * NOTES/ASSUMPTIONS: NONE <br>
     *
     * @param input - The command that was given from readInput
     * @return String - Returns results from calling the command
     * @throws LogosException
     */
    public String parseInput(String input) throws LogosException {
        String commandName;
        String[] validCommandArguments = null;
        String argumentName;
        String argumentValue;
        String keyValuePair = null;
        ArrayList keyValuePairs = new ArrayList();
//        Command command;
        String[] tokens;
        HashMap kvp = new HashMap();
//      {
//         public Object get(Object key)
//         {
//            return null;
//         }
//      };

        tokens = input.split(" ");

        commandName = tokens[0];
        commandName = preProcessCommandName(commandName);


        // Check if the command is valid (if it is in the hashmap)
        if (!isValidCommand(commandName)) {
            showCommands();
            return null;
        }
        String frameName = "";
        if (tokens.length > 1) {
            frameName = tokens[1];
        }

        if (commandName.equals("show")) {
            this.show(frameName);
            return null;
        }

        if (commandName.equals("hide")) {
            this.hide(frameName);
            return null;
        }

        if (commandName.equals("clear")) {
            this.clear(frameName);
            return null;
        }

        if ("run".equals(commandName)) {
            kvp.put("batchFile", tokens[1]);
            return processInput(commandName, kvp);
        }

        if (tokens.length > 1 && "usage".equals(tokens[1].toLowerCase())) {
            showUsage(commandName);
            return null;
        }

//        command = null;
//        if(m_Commands.containsKey(commandName))
//        {
//            command = (Command)m_Commands.get(commandName);
//        }
//
//        validCommandArguments = null;
//        if(command != null)
//        {
//            validCommandArguments = command.getArgumentNames();
//        }

        int i = 1;
        while (i < tokens.length) {
            // If the token contains a valid command
            if (arrayContains(validCommandArguments, tokens[i])) {
                // If it's not the first time
                if (keyValuePair != null) {
                    keyValuePairs.add(keyValuePair);
                }
                keyValuePair = "";
                keyValuePair += tokens[i];
            } else {
                keyValuePair += " ";
                keyValuePair += tokens[i];
            }
            i++;
        }
        // if we have arguments
        if (keyValuePair != null) {
            keyValuePairs.add(keyValuePair);
        }

        // Each key value pair will be similar in from to:
        //    key=value
        //    there could be spaces anywhere though - if they are
        //    in the value we still want to preserve them
        for (i = 0; i < keyValuePairs.size(); i++) {
            keyValuePair = (String) keyValuePairs.get(i);
            // get the equals sign out of there
            keyValuePair = keyValuePair.replaceFirst("=", " ");
            // Split it up using spaces.
            tokens = keyValuePair.split(" ");
            // The first token will be the argument name
            argumentName = tokens[0];

            // Take off the argument name, leaving us with the argumentValue
            argumentValue = keyValuePair.replaceFirst(argumentName, "");
            // Trim off the leading or trailing spaces
            argumentValue = argumentValue.trim();

            // Put it in the Hashmap
            kvp.put(argumentName.toLowerCase(), argumentValue);
        }
        return processInput(commandName, kvp);
    }


//    public static Enumeration CIMLookUp( String nameSpace, String className )
//    {
////        CIMObjectPath oPath = null;
//        Enumeration retEnum = null;
//        CIMInstance instance = null;
//        String[] row = null;
//
//        GenericTable tableView = null;
//
//        try
//        {
//            // Object Path of
//            oPath = new CIMObjectPath();
//            oPath.setHost( m_Host );
//            oPath.setNameSpace( nameSpace );
//            oPath.setObjectName(className);
//
//            retEnum = CIM_CONNECTION.enumerateInstances(oPath, true, false, false, false, null);
//
//        }
//        catch (LogosException c)
//        {
//            Trace.debug(c.toString());
//        }
//
//        return retEnum;
//    }
    /**
     * DESCRIPTION: If the array contains the element, returns true.  Else false
     * <P>
     *
     * NOTES/ASSUMPTIONS:  There will never be more than 4 or 5 elements in the array so
     *   it will be plenty fast.
     * <P>
     *
     * @param array
     * @param element
     * @return
     */
    private boolean arrayContains(String[] array, String element) {
        for (int i = 0; i < array.length; i++) {
            array[i] = array[i].toLowerCase();
            element = element.toLowerCase();
            element = element.trim();

            if (element.matches(array[i])) {
                return true;
            } else if (element.matches(array[i] + "( )*=.*")) {
                return true;
            }
        }

        return false;
    }

    /**
     * DESCRIPTION: Processes the given command, and key value pairs.  This method will
     * 	validate the command and then execute the command
     * <br>
     * <br>
     * NOTES/ASSUMPTIONS: NONE
     * <br>
     * @param command
     * @param keyValuePairs
     * @return String - Returns results from calling the command
     * @throws LogosException
     */
    public String processInput(String command, HashMap keyValuePairs) throws LogosException {
        command = preProcessCommandName(command);

        // Check if the command is valid (it is in the hashmap)
        if (!isValidCommand(command)) {
            showCommands();
            return null;
        }

        // If the command is "help" then list all of the commands
        if (command.equals("help")) {
            showCommands();
            return null;
        }

        //If the command is "showcommands" then list all of the commands
        if (command.equals("showcommands")) {
            showCommands();
            return null;
        }

        // If the command is "describecommands" then list the descriptions
        if (command.equals("describecommands")) {
            showCommandDescriptions();
            return null;
        }

        // If the command is "run batch.txt"
        if (command.equals("run")) {
            String fileName = (String) keyValuePairs.get("batchFile");
            if (fileName == null) {
                showUsage("run");
                return null;
            }

            runBatchFile(fileName);
            return null;
        }

        //If the command is "exit" then exit the system
        if (command.equals("exit")) {
            exit(1);
        }

        LogosCommand com = m_Commands.get(command);

        if (com != null && !com.validateArgs(keyValuePairs)) {
            showUsage(command);
            return null;
        }

        // No arguments were passed in but there should be.
        if (com != null && (keyValuePairs.size() == 0 || keyValuePairs == null) && com.hasReqArguments() == true) {
            showUsage(command);
            return null;
        }

        // EXECUTE THE COMMAND
        String outPut = "";
        if (com != null) {
            outPut = com.execute(keyValuePairs);
        }
        return (outPut);
    }

    /**
     * DESCRIPTION: Checks if the given command is valid
     * <br>
     * <br>
     * NOTES/ASSUMPTIONS: NONE
     * <br>
     *
     * @param command
     * @return boolean - true if the command name is valid, false otherwise
     */
    public boolean isValidCommand(String command) {
        if (m_Commands.containsKey(command)) {
            return true;
        }

        // Some "built in" commands that don't have their own classes
        if (command.equals("help") || command.equals("exit") || command.equals("run") || command.equals("describecommands") || command.equals("rebuild") || command.equals("show") || command.equals("hide") || command.equals("clear")) {
            return true;
        }

        return false;
    }

    /**
     * DESCRIPTION: Assign values to all the cimom parameters.  These include host, namespace,
     *   interopnamespace, username, and password
     * <P>
     *
     * NOTES/ASSUMPTIONS: NONE
     * <BR>
     *
     */
    public void initializeCimomParameters(String configName) {
        m_HostStr = configName;
    }

    /**
     * DESCRIPTION: This method initializes all of the commands found in the
     * cimtest.properties file. It will be called only once when the program is
     * first started.<P>
     * <br>
     * NOTES/ASSUMPTIONS: NONE <br>
     *
     */
    public void initializeCommands() {
        m_Commands = new HashMap();
        String commandName = null;

        final String packageName = "devmgr.wbem.client.commands";
        final String pathSeperator = File.separator;
        final String directoryName = "devmgr" + pathSeperator + "wbem" + pathSeperator + "client" + pathSeperator + "commands";
        File directory = new File(directoryName);

        String[] commandNameList = null;
        String fileName = null;
        FilenameFilter filter = null;
        Class c = null;
        LogosCommand commandInstance = null;

        JarFile jarFile = null;
        String jarFileName = "CimClient.jar";
        Enumeration jarEntries;
        JarEntry jarEntry;
        String jarEntryName;
        String[] jarEntryNameSplit;
        ArrayList commandNames = new ArrayList();
        String commandDirectoryName = "devmgr/wbem/client/commands/";

        boolean isJarFilePresent = false;

        try {
            // FIRST LOOK FOR COMMANDS IN THE DEVMGR DIRECTORY

            // Make a file filter to only accept .class files.
            filter = new FilenameFilter() {

                public boolean accept(File dir, String name) {
                    return name.endsWith(".class");
                }
            };

            // Get a list of all the class files in the directory
            commandNameList = directory.list(filter);

            if (commandNameList != null) {
                // Add each of the command names to the ArrayList without the file extension
                for (int i = 0; i < commandNameList.length; i++) {
                    // Get filename of file or directory
                    fileName = commandNameList[i];

                    commandName = fileName.split("\\Q.\\E")[0];

                    commandNames.add(commandName);
                }
            } else {
                LogosTrace.verbose("Command directory not found");
            }

            // SECONDLY, LOOK IN THE JAR FILE FOR COMMANDS (if it exists)

            try {
                jarFile = new JarFile(jarFileName);
                isJarFilePresent = true;
            } catch (FileNotFoundException e) {
                LogosTrace.verbose(jarFileName + " not found");
            } catch (IOException e) {
                LogosTrace.verbose("IOException while loading jarFile");
            }

            if (isJarFilePresent == true) {
                jarEntries = jarFile.entries();

                // Walk through all the entries in the jar file
                while (jarEntries != null && jarEntries.hasMoreElements()) {
                    // Get the element
                    jarEntry = (JarEntry) jarEntries.nextElement();

                    // Get the element name which will be the relative path to it
                    jarEntryName = jarEntry.getName();

                    // If the entry starts with the correct command directory path
                    //   AND it is not the command directory reference itself.
                    if (jarEntryName.startsWith(commandDirectoryName) && !(jarEntryName.equals(commandDirectoryName))) {
                        // Break up the directory reference btw the backslashes
                        jarEntryNameSplit = jarEntryName.split("/");

                        // get the last token in the split (which will be the commandName.class)
                        commandName = (jarEntryNameSplit[jarEntryNameSplit.length - 1]);

                        // Add the command name (minus the ".class") to the arrayList if it is
                        // not already there
                        if (!commandNames.contains(commandName.split("\\Q.\\E")[0])) {
                            commandNames.add(commandName.split("\\Q.\\E")[0]);
                        }
                    }
                }
            }

            // Put instances of the commands into the command hashmap
            for (int i = 0; i < commandNames.size(); i++) {
                commandName = (String) commandNames.get(i);

                // Create an instance of the command
                c = Class.forName(packageName + "." + commandName);
                commandInstance = (LogosCommand) c.newInstance();

                // Put command into hashmap
                m_Commands.put(commandName.toLowerCase(), commandInstance);
            }
        } catch (ClassNotFoundException e2) {
            LogosTrace.debug("ERROR: Unable to load class \"" + commandName + "\".  Make sure is exists. ");
            exit(1);
        } catch (InstantiationException e3) {
            LogosTrace.debug("ERROR: Unable to create an instance of type \"" + commandName + "\".");
            exit(1);
        } catch (IllegalAccessException e4) {
            LogosTrace.debug("ERROR:  You do not have proper access to create \"" + commandName + "\".");
            exit(1);
        }

        createSortedCommandList();
    }

    /**
     * DESCRIPTION: Reads a line of input from the given BufferedReader, skipping over
     * 		blank lines, comments (preceeded by '//')<br>
     * <br>
     * NOTES/ASSUMPTIONS: Used for both reading a config or properties file, and for reading
     *      lines from the batch/script file.
     *
     * @param br
     * @return String - A valid line of text from the input file.  null if it's the end of file
     *
     */
    public String readLine(BufferedReader br) {
        String line = null;
        boolean done = false;

        try {
            while (!done) {
                line = br.readLine();

                if (line == null) {
                    return null;
                } else if (line.startsWith("//")) {
                    done = false;
                } else if (line.equals("")) {
                    done = false;
                } else {
                    done = true;
                }
            }
        } catch (IOException e1) {
            print("ERROR: IO problem while reading file");
            exit(1);
        }

        return line;
    }

    /**
     * DESCRIPTION: Lists all the available commands. This method is invoked
     * when the user types help or showcommands. <br>
     * <br>
     * NOTES/ASSUMPTIONS: NONE
     *
     */
    public void showCommands() {
//        GenericTable gTable = new GenericTable();
//        int numColumns = 4;
//        int numRows = m_SortedCommands.length/(numColumns-1);
//        gTable.leftOffset("  ");
//
//        for (int i=0;i < m_SortedCommands.length;i++)
//        {
//            gTable.addToRow(i % numRows,m_SortedCommands[i]);
//        }
//
//        print(gTable.toString());
    }

    /**
     * DESCRIPTION: Lists all of the commands and their coresponding functionality.
     * <br>
     * <br>
     * NOTES/ASSUMPTIONS: NONE
     *
     */
    public void showCommandDescriptions() {
//        GenericTable gTable = new GenericTable();
//        String commandName = null;
//        String description = null;
//        Command tempCommand = null;
//
//        gTable.leftOffset("  ");
//
//        for (int i=0;i < m_SortedCommands.length;i++)
//        {
//            commandName = (String)m_SortedCommands[i];
//
//            // Get the Command instance that we want
//            tempCommand = (Command)m_Commands.get(commandName);
//
//            // The command will be null for the build in commands.  So we just skip it and go on.
//            if(tempCommand == null)
//            {
//                continue;
//            }
//
//            description = tempCommand.getDescription();
//
//            // Put the Command name and the description into the table
//            gTable.addRow(new String[]{commandName,description});
//        }
//
//        print(gTable.toString());
    }

    /**
     * DESCRIPTION: Creates a sorted array of commands from the m_Commands
     * Hashmap <br>
     * <br>
     * NOTES/ASSUMPTIONS: This will be used for printing out a list of commands
     * 	in alphabetic order.
     *
     */
    public void createSortedCommandList() {
        Set s = m_Commands.keySet();
        Iterator iter = s.iterator();
        int i = 0;

        // initialize this string array to be the same size as the arraylist
        m_SortedCommands = new String[m_Commands.size() + 7];

        // store all the command names in the string array
        while (iter.hasNext()) {
            m_SortedCommands[i] = (String) iter.next();
            i++;
        }
        // Add built in Commands
        m_SortedCommands[i] = "help";
        m_SortedCommands[i + 1] = "exit";
        m_SortedCommands[i + 2] = "describecommands";
        m_SortedCommands[i + 3] = "run";
        m_SortedCommands[i + 4] = "show";
        m_SortedCommands[i + 5] = "hide";
        m_SortedCommands[i + 6] = "clear";

        // Sort
        java.util.Arrays.sort(m_SortedCommands);
    }

    /**
     * DESCRIPTION: Prints out the usage for the given command <br>
     * <br>
     * NOTES/ASSUMPTIONS: NONE <br>
     *
     * @param command
     */
    public void showUsage(String command) {
        if (command.equals("run")) {
            print("\nRUN <scriptFileName>\n");
            return;
        }

    // print("\n" + (m_Commands.get(command)).getUsage());
    }

    /**
     * DESCRIPTION: Set the host to the specified IP address
     * <br>
     * <br>
     * NOTES/ASSUMPTIONS: TODO
     * <br>
     * @param host
     */
    public static void setHost(String host) {
        m_Host = host;
    }

    /**
     * DESCRIPTION: Call a command such as createpool, showpool, etc.  This allows
     * 		external apps use this class as a client to do things to the arrays, using
     * 		CimClientProxy's command syntax.
     * <br>
     * <br>
     * NOTES/ASSUMPTIONS: The command much be valid
     * <br>
     *
     * @param command
     * @return String - Results of calling the command
     */
    public String callCommand(String command) {
        try {
            return parseInput(command);
        } catch (LogosException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * DESCRIPTION: Pre Processes the command name.  Take the command name and if the command is the
     *   unique prefix to one of the commands, then return the valid command name.  If it is not
     *   a unique prefix or not a prefix to any commands, just return the original string.</P>
     *
     * NOTES/ASSUMPTIONS: NONE
     * <P></P>
     *
     * @param c
     * @return Valid command name if it's a unique prefix to the command, otherwise return the
     *       input value.
     */
    private String preProcessCommandName(String c) {
        String fullCommandName = null;

        // If it is a valid command name, just return it back...no need to process it any further
        if (isValidCommand(c)) {
            return c;
        }

        // Look for a command that has the unique prefix c
        for (int i = 0; i < m_SortedCommands.length; i++) {
            // See if the command startswith the prefix c
            if (m_SortedCommands[i].startsWith(c)) {
                // Means there are more than one that start with this prefix
                if (fullCommandName != null) {
                    return c;
                }

                fullCommandName = m_SortedCommands[i];
            }
        }

        // None of the commands had prefix c
        if (fullCommandName == null) {
            return c;
        }

        return fullCommandName;
    }

    private void print(String s) {
        System.out.print(s);
    }

    private void println(String s) {
        print(s + "\r\n");
    }

    /**
     * DESCRIPTION:
     * <P>
     *
     * NOTES/ASSUMPTIONS: NONE
     * <BR>
     *
     * @param status
     */
    private void exit(int status) {
        m_LogosFrameMgr.shutdown(status);
    }

    private void clear(String frameName) {
        m_LogosFrameMgr.clear(frameName);

    }

    private void show(String frameName) {
        m_LogosFrameMgr.show(frameName);
    }

    private void hide(String frameName) {
        m_LogosFrameMgr.hide(frameName);
    }

    public synchronized void stop() {
        running = false;
    }
}
