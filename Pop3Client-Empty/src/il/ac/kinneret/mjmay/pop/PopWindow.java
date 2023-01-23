package il.ac.kinneret.mjmay.pop;

import il.ac.kinneret.mjmay.pop.model.IncomingListener;
import il.ac.kinneret.mjmay.pop.model.PopState;
import il.ac.kinneret.mjmay.pop.model.SharedData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class PopWindow implements Initializable {
    public static final int POP_PORT = 110;
    public static final int IMAP_PORT = 143;
    public TextField tfUserName;
    public PasswordField tfPassword;
    public TextField tfServer;
    public TextField tfList;
    public TextField tfRetr;
    public TextField tfDele;
    public TextField tfUidl;
    public TextField tfRaw;
    public TextArea taLog;
    public Button bConnect;
    PopState popState;
    private Thread listeningThread;

    /**
     * Initializes the GUI.
     * @param url Ignored
     * @param resourceBundle Ingored
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfUserName.setText("kin101");
        tfPassword.setText("kin101");
        tfServer.setText("12cm.yaweli.com");
        popState = null;
    }

    /**
     * Runs when the connect button is pressed
     * @param actionEvent Ignored
     */
    public void connect(ActionEvent actionEvent) {
        // see if we should connect or disconnect
        if (bConnect.getText().equals("Connect")) {
            // only try if there is a server here
            if (tfServer.getText().length() > 0) {
                // try to connect to the server
                popState = new PopState(tfServer.getText(), POP_PORT);
                // if we succeeded
                if (popState.isConnected()) {
                    taLog.appendText("Connected to " + tfServer.getText() + " on port " + POP_PORT + ".\n");
                    bConnect.setText("Disconnect");

                    //listen for incoming messages
                    InputStream isr = popState.getConnectionInputStream();
                    if (isr != null)
                    {
                        IncomingListener isl = new IncomingListener(isr);
                        listeningThread = new Thread(isl);
                        listeningThread.start();
                        isl.messageProperty().addListener((observableValue, s, t1) -> {
                            synchronized (SharedData.getSB()) {
                                taLog.appendText(SharedData.getSB().toString());
                                SharedData.clearSB();
                            }
                        });
                    }
                } else {
                    taLog.appendText("Error connecting to " + tfServer.getText() + " on port " + POP_PORT + ".\n");
                    bConnect.setText("Connect");
                }
            }
        } else
        {
            // disconnect
            if (popState != null){
                popState.close();
                listeningThread.interrupt();
                taLog.appendText("Disconnected from the server.\n");
                bConnect.setText("Connect");
            }
        }
    }

    /**
     * Runs when the user button is pressed
     * @param actionEvent Ignored
     */
    public void user(ActionEvent actionEvent) throws IOException, ExecutionException, InterruptedException {
        /// TODO: Do the USER command
        if (popState != null && popState.isConnected()) {
            popState.doUser(tfUserName.getText());
            taLog.appendText("user " + tfUserName.getText() + "\n");
        } else {
            taLog.appendText("Not connected to server.\n");
        }
    }


    /**
     * Runs when the password button is pushed
     * @param actionEvent Ignored
     */
    public void pass(ActionEvent actionEvent) {
        /// TODO: Do the PASS command
        if (popState != null && popState.isConnected()) {
            popState.doPass(tfPassword.getText());
            taLog.appendText("pass " + tfPassword.getText() + "\n");
        } else {
            taLog.appendText("Not connected to server.\n");
        }
        }


    /**
     * Runs when the stat button is pushed
     * @param actionEvent Ignored
     */
    public void stat(ActionEvent actionEvent) {
        /// TODO: Do the STAT command
        if(popState!=null && popState.isConnected())
        {
            popState.doStat();
            taLog.appendText("STAT\n" );
        }
        else {
            taLog.appendText("Not connected to server.\n");
        }
    }

    /**
     * Runs when the list button is pushed
     * @param actionEvent Ignored
     */
    public void list(ActionEvent actionEvent) {
        /// TODO: Do the LIST command
        if(popState!=null && popState.isConnected())
        {
            if(tfList.getText().isEmpty())
            {
                popState.doList(tfList.getText());
            }
            else{
                popState.doList(tfList.getText());

            }
            taLog.appendText("LIST "+tfList.getText() + "\n");
        }
    }

    /**
     * Runs when the retrieve button is pressed
     * @param actionEvent Ignored
     */
    public void retr (ActionEvent actionEvent) {
        /// TODO: Do the RETR command
        if(popState!=null && popState.isConnected())
        {
            if(tfRetr.getText().isEmpty())
            {
                System.err.println("PLEASE ENTER A NUMBER");
            }
            else{
                popState.doRetr(tfRetr.getText());
            }
            taLog.appendText("RETR "+tfRetr.getText() + "\n");
        }
    }

    /**
     * Runs when the delete button is pressed
     * @param actionEvent Ignored
     */
    public void dele(ActionEvent actionEvent) {
        /// TODO: Do the DELE command
        if(popState!=null && popState.isConnected()) {
            if(tfDele.getText().isEmpty())
            {
                System.err.println("PLEASE ENTER A NUMBER");
            }
            else{
                popState.doDele(tfDele.getText());
            }
            taLog.appendText("DELE "+tfDele.getText() + "\n");
        }
    }

    /**
     * Runs when the UIDL button is pressed
     * @param actionEvent Ignored
     */
    public void uidl(ActionEvent actionEvent) {
            /// TODO: Do the UIDL command
        if(popState!=null && popState.isConnected())
        {
            if(tfUidl.getText().isEmpty())
            {
                System.err.println("PLEASE ENTER A NUMBER HERE");
            }
            else{
                popState.doUidl(tfUidl.getText());
            }
            taLog.appendText("UIDL "+ tfUidl.getText() + "\n");
        }
    }

    /**
     * Runs when the reset button is pressed
     * @param actionEvent Ignored
     */
    public void rset(ActionEvent actionEvent) {
        /// TODO: Do the RSET command
        if (popState != null && popState.isConnected()) {
            popState.doRset();

            taLog.appendText("RSET \n");
        }
    }

    /**
     * Runs when the reset button is pressed
     * @param actionEvent Ignored
     */
    public void quit(ActionEvent actionEvent) {
        /// TODO: Do the QUIT command
        if (popState != null && popState.isConnected()) {
            popState.doRset();

            taLog.appendText("QUIT \n");
        }
    }

    /**
     * Runs when the raw command button is pressed.
     * @param actionEvent Ignored
     */
    public void raw(ActionEvent actionEvent) {
        /// TODO: Send a raw command
        if (popState != null && popState.isConnected()) {
            popState.doRaw(tfRaw.getText());

            taLog.appendText(tfRaw.getText()+"\n");
        }

    }
}
