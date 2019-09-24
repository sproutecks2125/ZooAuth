// import all necessary classes and exceptions
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Author: Brandon Rickman
 * Southern New Hampshire University
 * IT145 - Application Development
 * Final Project: Zoo Authentication (Option 1)
 * @Brandon.Rickman@snhu.edu
 */

// class variable creation
public class HashCompare {  
    private FileInputStream credFS;
    private Scanner credScnr;
    private String credUserName;
    private String credPassHash;
    private String credRole;
    private String userPassHash;
    private String fullLine;
    private String[] credParts;
    
    // class constructor
    public HashCompare() throws FileNotFoundException {
        credUserName = "none";
        credPassHash = "none";
        credRole = "none";
        userPassHash = "none";
    }
    
    // method to create a string array from credentials file: no return and takes in one param
    public void setCredParts(String userName) throws IOException {
        credParts = new String [3]; // creates array with 3 elements
        credUserName = "none";
        credFS = new FileInputStream("credentials.txt"); // new instance of Credentials file reader
        credScnr = new Scanner(credFS); // new Scanner for file information
        
        // while loop stating: if user name does not equal cred user name and file has more lines
        while ( !userName.equals(credUserName) && (credScnr.hasNextLine()) ){
            fullLine = credScnr.nextLine(); // variable for next full line in file
            Scanner inFullLine = new Scanner(fullLine); // scanner for the string of the full line
            String firstPart = inFullLine.next(); // This will get the next string up to the first space and put it in the variable
            
            // if user name equals the first element in the full line of credentials file (the creds user name)
            if (userName.equals(firstPart))
            {
                credParts[0] = firstPart; // sets first element of array to creds username
                credParts[1] = inFullLine.next(); // sets second element of array to cred password hash (next block in full line)
                // creates a loop that checks to see if the full line has any other elements in it
                while (inFullLine.hasNext())
                {
                    credParts[2] = inFullLine.next(); // sets the third element in the array to the last element in full line
                    // this will be the Role of the user information used to find the correct file to print out in Main
                }
            }
        }
    }
    
    // method creates a true/false return if the user password hash and the credentials password hash match
    public boolean comparePassword(String userPassHash, String credPassHash){
        return userPassHash.equals(credPassHash); // returns true if match found and false if not
    }
    
    // method to create a MD5 hash from the users input password
    public String hashPassword(String userPassWord){
        MD5Digest md5digest = new MD5Digest(); // new instance of MD5Digest class
        /* this block of code is a solution for an error I continued to receieve.
         * this sets up a try/catch senario to see if MD5Digest can be called and convert the password
         * if it cannot the catch is a "NoSuchAlgorithmException" and prints an error message to the user.
        */
        try 
        {              
            userPassHash = md5digest.Hash(userPassWord);
        }
        catch (NoSuchAlgorithmException ex)
        {
            System.err.println("Caught No Such Algorithm Exception: ");
        }
        return userPassHash;// return hashed password
    }
    
    // get method from credParts array first element
    public String getCredUserName() {
        return credParts[0];
    }
    
    // get method from credParts array second element
    public String getCredPassHash() {
        return credParts[1];
    }
    
    // get method from credParts array third element
    public String getCredRole() {
        return credParts[2];
    }
}
