/** @author Adam Gale
 * 251326327
 * CS2210 Assignment 4
 * Interface.java
 * 
 * The Interface class allows the user to load in a text file containing a library of labels and data
 * The text file is parsed into a binary search tree
 * The user is provided multiple ways to interact with the content as well as manipulating the search tree
 * 
 * I have tried my best to have robust error catching, but I am sure something has slipped through the cracks
 * Tried to handle as many edge cases as I could consider, and have provided several non-terminating warning messages to the user when unexpected behavior may occur
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Interface {

    private static BSTDictionary BSTDict;  // our BSTDictionary that will be used to store the records generated from the source file
    
    /**
     * Out main method allows the user to load in a text file and create a binary search tree dictionary out of the created records 
     * 
     * @param args should only include one argument, the filename containing the records to be added to the dictionary
     */
    public static void main(String[] args) throws IOException {

        if (args.length > 1 || args.length == 0) {  // catching incorrect argument usage
            System.out.println("Warning: please launch using the format \"Interface <filename>\"");
            return;
        }
        
        BSTDict = new BSTDictionary(); // initializing our dictionary
        String userCommand = ""; // initializing user input
        String fileName = args[0]; // loading the filename from launch options

        try (FileReader inputReader = new FileReader(fileName); BufferedReader inputBuffer = new BufferedReader(inputReader)) { // just learned about "try-with-resources", so let's try to use it!
            
            String inputLine;  // string we use to load lines from input file into
            boolean duplicate = false;  // detecting duplicates in the input file - why not?
            
            while ((inputLine = inputBuffer.readLine()) != null) {  // iterate through the input file line by line

                String label = inputLine;  // upon second thought, i started loading in two lines at a time to build records
                inputLine = inputBuffer.readLine();
                String data = inputLine;

                try{
                    BSTDict.put(convertToRecord(label,data));  // try to build a record with the parsed lines (using helper method), and then try to put that record in the dictionary
                }
                catch (DictionaryException e) {  // if the record already exists, we'll let the user know there were some issues once we're finished with the file
                    duplicate = true;
                }
                catch (NullPointerException e) {  // trying to catch the case where there is a straggler label line without a proper matching data line
                    System.out.println("Warning: input file contains invalid dictionary entires.");  
                }
            }
            if (duplicate == true) {
                System.out.println("Warning: input file contained one or more duplicate entries that have not been stored.");  // yo, your dictionary is kinda bad
            }
            if (BSTDict.smallest() == null) {  // testing to see if we actually made a dictionary at all (kinda goofy way to do it but i can't really check root lol)
                System.out.println("Warning: input file generated empty dictionary.");  // yo, your dictionary is really, really bad
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found. Please double check the filename or path.");  // yo, your dictionary doesn't even exist
            return;
        }

        while (!userCommand.equals("exit")) {  // reading user commands

            StringReader keyboard = new StringReader();
            userCommand = keyboard.read("Enter next command: ");  // easier to deal with stuff if it's all lowercase!
            processCommand(userCommand);  // created a helper method to assist in dealing with user commands
        }
    }

    /** 
     * Private helper method
     * Executes user commands to interact with the content/manipulate the dictionary
     * This is truly a mess, please have mercy
     * I don't know why I decided to split the commands by word length and then deal with them case by case but... it is what it is and it works
     * It was the first thing that came to mind to deal with commands like "add" that have a variable amount of string splits, sorry!
     * 
     * @param command the user's command to be executed
     */    
    private static void processCommand(String command) {

        Key key = null;  // we initialize some variables to use for executing user commands
        Record record = null;
        String file = null;

        String[] commandSplit = command.split(" ");  // chop up the user's command into words
        String operation = commandSplit[0].toLowerCase();  // the first word is the "operation" to exectue: this is what we'll be checking in our switches

        if (commandSplit.length == 1) {  // shamefully checking if the operation length is 1 to handle 1-word operations

            switch(operation) {

                case "exit":  // handling the exit operation
                    return;

                case "first":  // handling the first operation, catching the case where the dictionary is empty (no more null pointer errors, hah!)
                    record = BSTDict.smallest();
                    if (record == null) {
                        System.out.println("Tree is empty.");
                    }
                    else {
                        System.out.println(record.getKey().getLabel()+","+record.getKey().getType()+","+record.getDataItem());  // returning smallest record in the dictionary
                    }
                    break;

                case "last":  // handling the last operation, catching the case where the dictionary is empty (no more null pointer errors, hah!)
                    record = BSTDict.largest();
                    if (record == null) {
                        System.out.println("Tree is empty.");
                    }    
                    else {                
                        System.out.println(record.getKey().getLabel()+","+record.getKey().getType()+","+record.getDataItem());  // returning largest record in dictionary
                    }
                    break;

                default:
                    System.out.println("Invalid command.");  // handling bad commands
            }
        }

        else if (commandSplit.length == 2) {  // handling the 2-word class of operations

            file = commandSplit[1];  // 2nd word is generally used as a filename

            switch(operation) {

                case "define":  // handling define operation
                    key = new Key(file,1);
                    record = BSTDict.get(key);
                    if (record == null) {
                        System.out.println("The word "+file+" is not in the ordered dictionary.");  // definition not found
                    }
                    else {
                        System.out.println(record.getDataItem());  // printing definition if found
                    }
                    break;

                case "translate":  // handling translate operation
                    key = new Key(file,2);
                    record = BSTDict.get(key);
                    if (record == null) {
                        System.out.println("There is no definition for the word "+file);  // translation not found
                    }
                    else {
                        System.out.println(record.getDataItem());  // printing translation if found
                    }
                    break;

                case "sound":  // handling sound operation
                    key = new Key(file,3);
                    record = BSTDict.get(key);
                    if (record == null) {
                        System.out.println("There is no sound file for "+file);  // sound not found
                    }
                    else {
                        try {
                            SoundPlayer Player = new SoundPlayer();  // attempt to play the sound if found
                            Player.play(record.getDataItem());
                        }
                        catch (MultimediaException e) {
                            System.out.println(e.getMessage());  // catch errors thrown from the sound player
                        }
                    }
                    break;

                case "play":  // handling play operation
                    key = new Key(file,4);
                    record = BSTDict.get(key);
                    if (record == null) {
                        System.out.println("There is no music file for "+file);  // music not found
                    }
                    else {
                        try {
                            SoundPlayer Player = new SoundPlayer();  // attempt to play the music if found
                            Player.play(record.getDataItem());
                        }
                        catch(MultimediaException e) {
                            System.out.println(e.getMessage());  // catch errors thrown from the sound player
                        }
                    }
                    break;  

                case "say":  // handling say operation
                    key = new Key(file,5);
                    record = BSTDict.get(key);
                    if (record == null) {
                        System.out.println("There is no voice file for "+file);  // voice file not found
                    }
                    else {
                        try {
                            SoundPlayer Player = new SoundPlayer();  // attempt to play the voice if found
                            Player.play(record.getDataItem());
                        }
                        catch (MultimediaException e) {
                            System.out.println(e.getMessage());  // catch errors thrown from the sound player
                        }
                    }
                    break;   

                case "show":  // handling show operation
                    key = new Key(file,6);
                    record = BSTDict.get(key);
                    if (record == null) {
                        System.out.println("There is no image file for "+file);  // image not found
                    }
                    else {
                        try {
                            PictureViewer viewer = new PictureViewer();  // attempt to display the image
                            viewer.show(record.getDataItem());
                        }
                        catch (MultimediaException e) {
                            System.out.println(e.getMessage());  // catch display errors
                        }
                    }
                    break;     

                case "animate":  // handling animate operation
                    key = new Key(file,7);
                    record = BSTDict.get(key);
                    if (record == null) {
                        System.out.println("There is no animated image file for "+file);  // image not found
                    }
                    else {
                        try {
                            PictureViewer viewer = new PictureViewer();  // attempt to display the image
                            viewer.show(record.getDataItem());
                        }
                        catch (MultimediaException e) {
                            System.out.println(e.getMessage());  // catch display errors
                        }
                    }
                    break;    

                case "browse":  // handling browse operation
                    key = new Key(file,8);
                    record = BSTDict.get(key);
                    if (record == null) {
                        System.out.println("There is no webpage called "+file);  //  html file not found
                    }
                    else {
                        try {
                            ShowHTML browser = new ShowHTML();  // attempt to diplay the site
                            browser.show(record.getDataItem());
                        }
                        catch (Exception e) {
                            System.out.println(e.getMessage());  // i don't know what kind of errors can be thrown, so we caught them all!
                        }
                    }
                    break;

                case "list":  // handling list operation
                    String prefix = file;
                    file = "";
                    key = new Key(prefix,0);  // generating a key that matches the prefix with type=0
                    record = BSTDict.successor(key);  // if something matching the prefix is stored in the dictionary, it would occur immediately after our generated key!
                    if (record == null) {  // there are some cases in which the successor would not exist, we catch these and report no matches
                        System.out.println("No matches found.");
                        break;
                    }
                    while (record != null && record.getKey().getLabel().startsWith(prefix)) {  // we check to make sure the record matches the prefix string
                        file = file.concat(record.getKey().getLabel()+", ");  // keep adding our matches to the eventual output string
                        record = BSTDict.successor(record.getKey());  // get the next successor! keep trying until it doesn't match the prefix!
                    }  
                    System.out.println(file.substring(0, file.length()-2));  // print the list out and trim the straggler comma
                    break;                
                    
                default:
                    System.out.println("Invalid command.");  // handling bad commands  
            }
        }

        else if (commandSplit.length == 3) {  // nearing the end of the insanity, handling 3-word commands

            file = commandSplit[1];
            
            switch(operation) {

                case "delete":  // handling the delete operation
                    try {
                        key = new Key(file,Integer.parseInt(commandSplit[2]));  // building the key out of the user input
                        BSTDict.remove(key);  // removing the node matching user input
                        System.out.println("The record with label "+file+" and type "+Integer.parseInt(commandSplit[2])+" has been removed.");  // i think it's nice to get confirmation :)
                    }
                    catch (NumberFormatException e) {
                        System.out.println("Invalid command syntax. [delete <label> <type(int)>]");  // bad input, catch the exception and let user know how to properly use command
                    }
                    catch(DictionaryException e) {
                        System.out.println("No record in the ordered dictionary has key ("+file+","+Integer.parseInt(commandSplit[2])+").");  // key not in dictionary, nothing to remove
                    }
                    break;

                default:
                    System.out.println("Invalid command.");  // handling bad commands

            }
        }

        else if (commandSplit.length >= 4) {  // AKA handling add

            file = commandSplit[1];  // we do a little bit of string manipulation here to condense everything after the 3rd word into one "data" string
            String data = commandSplit[3];
            for (int i = 4; i<commandSplit.length;i++) {
                data = data.concat(" "+commandSplit[i]);
            }

            switch(operation) {

                case "add":  // handling the add operation
                try {
                    key = new Key(file,Integer.parseInt(commandSplit[2]));  // creating the key 
                    record = new Record(key, data);  // creating the record with the key and data string
                    BSTDict.put(record);
                    System.out.println("Record with key ("+file+","+Integer.parseInt(commandSplit[2])+") and data ("+data+") added.");  // i think it's nice to get confirmation :)
                }
                catch (NumberFormatException e) {
                    System.out.println("Invalid command syntax. [add <label> <type(int)> <data>]");  // bad input, catch the exception and let user know how to properly use command
                }
                catch (DictionaryException e) {
                    System.out.println("A record with the given key ("+file+","+Integer.parseInt(commandSplit[2])+") is already in the ordered dictionary.");  // key already in dictionary, nothing to add
                }
                break;

                default:
                    System.out.println("Invalid command.");  // handling bad commands

            }

        }

        else {
            System.out.println("Invalid command.");  // handling bad commands, the final boss of bad command handlers
        }
    }

    /** 
     * Private helper method
     * Used to convert two strings into a Record object
     * Assesses the type based on the first character and file extension of the input
     * We check the prefixes first as I feel this makes more sense 
     * what if a translation ended in ".jpg"? the translation classification should still take priority
     * 
     * @param label the label to be used in the Key
     * @param input the input used to determine the type, stored as data in the record
     * @return a Record containing the label, data, and type as determined by the method
     */        
    private static Record convertToRecord(String label, String input) {

        Key key;
        Record record;

        if (input.startsWith("/")) {  // leading / means translation
            key = new Key(label,2);
            record = new Record(key,input.substring(1));
        }

        else if (input.startsWith("-")) {  // leading - means sound file
            key = new Key(label,3);
            record = new Record(key,input.substring(1));
        }

        else if (input.startsWith("+")) {  // leading + means music file
            key = new Key(label,4);
            record = new Record(key,input.substring(1));
        }

        else if (input.startsWith("*")) {  // leading * means voice file
            key = new Key(label,5);
            record = new Record(key,input.substring(1));
        }

        else if (input.endsWith(".jpg")) {  // .jpg suffix means image file
            key = new Key(label,6);
            record = new Record(key,input);
        }

        else if (input.endsWith(".gif")) {  // . gif suffix means animated image file
            key = new Key(label,7);
            record = new Record(key,input);
        }

        else if (input.endsWith(".html")) {
            key = new Key(label,8);
            record = new Record(key,input);
        }

        else {  // default case, set type to 1
            key = new Key(label,1);
            record = new Record(key,input);
        }

        return record;

    }
}