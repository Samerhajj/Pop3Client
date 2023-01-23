package il.ac.kinneret.mjmay.pop.model;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutionException;

public class PopState {

    private Socket clientSocket;
    PrintWriter pwOut;
    BufferedReader brIn;

    public PopState (String serverName, int port)
    {
        try {
            clientSocket = new Socket(serverName, port);
            pwOut = new PrintWriter(clientSocket.getOutputStream());
            brIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            clientSocket = null;
            pwOut = null;
            brIn = null;
            return;
        }
    }

    /**
     * Returns the input stream associated with the connection to enable the input to be printed to the log.  NULL if the connection is not available.
     * @return The input stream associated with the connection to the server
     */
    public InputStream getConnectionInputStream() {
        if (isConnected())
        {
            try {
                return clientSocket.getInputStream();
            } catch (IOException e) {
                return null;
            }
        }
        else
        {
            return null;
        }
    }
    public boolean isConnected()
    {
        return (clientSocket != null && !clientSocket.isClosed());
    }

    public boolean close()
    {
        /// TODO: Send the CLOSE command
        return false;
    }

    /**
     * Do the USER command
     * @param username The user name to send
     * @return The command sent and the result from the server
     */
    public String doUser (String username) throws IOException, ExecutionException, InterruptedException {
        if(isConnected()) {
            // send the USER command
            pwOut.println("USER " + username);
            pwOut.flush();
            // check if command sent correctly
            if (pwOut.checkError()) {
                return "Error sending USER command";
            }
          String response;
            synchronized (SharedData.getSB())
            {
                response=SharedData.getSB().toString();
            }
            System.out.println(response);
            if (response.startsWith("+OK")) {
                return "USER command sent successfully. Server response: " + response;
            } else {
                return "USER command failed. Server response: " + response;
            }
        } else {
            return "Not connected to server";
        }
    }


    /**
     * Do the PASS command
     * @param password The user password to send
     * @return The command sent and the result from the server
     */
    public String doPass (String password)
    {
        /// TODO: Send the PASS command
        if(isConnected())
        {
            pwOut.println("PASS "+ password);
            pwOut.flush();
            //check if error occurs
            if (pwOut.checkError()) {
                return "Error sending PASS command";
            }

        String response;
        synchronized (SharedData.getSB())
        {
            response=SharedData.getSB().toString();
        }
        System.out.println(response);
        if (response.startsWith("+OK")) {
            return "PASS command sent successfully. Server response: " + response;
        } else {
            return "PASS command failed. Server response: " + response;
        }
    } else {
        return "Not connected to server";
    }
}



    public String doStat() {
        /// TODO: Send the STAT command
        if (isConnected()) {
            pwOut.println("STAT");
            pwOut.flush();
            if (pwOut.checkError()) {
                return "Error sending STAT command";
            }
            String response;
            synchronized (SharedData.getSB()) {
                response = SharedData.getSB().toString();
            }
            System.out.println(response);
            if (response.startsWith("+OK")) {
                return "STAT command sent successfully. Server response: " + response;
            } else {
                return "STAT command failed. Server response: " + response;
            }

        }
        else {
            return "Not connected to server";
        }
    }

    public String doList(String listNumber) {
        /// TODO: Send the LIST command
       // return listNumber;
        //if(PopState)
        if(isConnected())
        {
            String response;
            if(listNumber.isEmpty())
            {
                pwOut.println("LIST");
                        pwOut.flush();
            }
            else{
                pwOut.println("LIST " + listNumber);
                pwOut.flush();
            }
            synchronized (SharedData.getSB()) {
                response = SharedData.getSB().toString();
            }
            System.out.println(response);
            if (response.startsWith("+OK")) {
                return "LIST command sent successfully. Server response: " + response;
            } else {
                return "LIST command failed. Server response: " + response;
            }
        }
        else {
            return "Not connected to server";
        }
    }

    public String doRetr(String retrNumber) {
        /// TODO: Send the RETR command
if(isConnected())
{
    String response;
    pwOut.println("RETR " + retrNumber);
    pwOut.flush();
    synchronized (SharedData.getSB()) {
        response = SharedData.getSB().toString();
    }

        if (response.startsWith("+OK")) {
            return "RETR command sent successfully. Server response: " + response;
        } else {
            return "RETR command failed. Server response: " + response;
        }

    }
        else {
        return "Not connected to server";
    }
    }

    public String doDele(String deleNumber) {
        /// TODO: Send the DELE command
    if(isConnected())
    {
        pwOut.println("DELE " + deleNumber);
        pwOut.flush();
        String response;
        synchronized (SharedData.getSB()) {
            response = SharedData.getSB().toString();
        }
        if (response.startsWith("+OK")) {
            return "DELE command sent successfully. Server response: " + response;
        } else {
            return "DELE command failed. Server response: " + response;
        }

    }
    else {
        return "Not connected to server";
    }
    }

    public String doUidl(String uidlNumber) {
        /// TODO: Send the UIDL command
    if(isConnected())
    {
        pwOut.println("UIDL "+ uidlNumber);
        pwOut.flush();
        String response;
        synchronized (SharedData.getSB()) {
            response = SharedData.getSB().toString();
        }
        if (response.startsWith("+OK")) {
            return "UIDL command sent successfully. Server response: " + response;
        } else {
            return "UIDL command failed. Server response: " + response;
        }
    }
    else {
        return "Not connected to server";
    }
    }

    public String doRset() {
        /// TODO: Send the RSET command
        if(isConnected())
        {   pwOut.println("RSET");
            pwOut.flush();
            String response;
            synchronized (SharedData.getSB()) {
                response = SharedData.getSB().toString();
            }
            if (response.startsWith("+OK")) {
                return "RSET command sent successfully. Server response: " + response;
            } else {
                return "RSET command failed. Server response: " + response;
            }
        }
        else {
            return "Not connected to server";
        }
    }

    public String doQuit() {
        /// TODO: Send the QUIT command
        if(isConnected())
        {   pwOut.println("QUIT");
            pwOut.flush();
            String response;
            synchronized (SharedData.getSB()) {
                response = SharedData.getSB().toString();
            }
            if (response.startsWith("+OK")) {
                return "QUIT command sent successfully. Server response: " + response;
            } else {
                return "QUIT command failed. Server response: " + response;
            }
        }
        else {
            return "Not connected to server";
        }
    }

    public String doRaw(String rawCommand) {
        /// TODO: Send the raw command
       if(isConnected())
       {
           pwOut.println(rawCommand);
           pwOut.flush();
           System.out.println(rawCommand);
           String response;
           synchronized (SharedData.getSB()) {
               response = SharedData.getSB().toString();
           }
          return response;
       }
       else {
           return "Not connected to server";
       }
       }
    }
