# Pop3Client
JavaFX POP3 Client
# POP3-CLient
![image](https://user-images.githubusercontent.com/75926858/213996401-a27a1ef0-176d-4340-8d84-1581d9ce4ccc.png)
When the user clicks on “Connect”, the tool attempts to connect to the server identified in the POP Server
field on port 110 (the default POP3 port).

Once connected, the tool enables the user to perform the following actions using the buttons shown and
changes the text on the Connect button to Disconnect. Pushing it again causes the session to be closed
immediately and disables the other buttons.

All commands entered, whether via buttons or the raw entry TextField will be echoed in the middle “log”
TextArea.

![image](https://user-images.githubusercontent.com/75926858/213996491-3a2d20f7-ce9a-4592-bc1e-a41e65b314d0.png)
**Protocol Command Buttons**
**USER** sends the USER command with the value shown in the User Name field above. It is used to define
which user name is trying to log in.

**PASS** sends the PASS command with the value shown in the Password field above. It is used for plaintext
(unencrypted and unsecured) authentication.
STAT sends the STAT command that shows the status of the user’s inbox (number of messages and number
of bytes total).

**LIST** sends the LIST command with the value shown in the TextField below it. When sent with no
parameter (i.e. the TextField is blank), it gives a list of all the email messages in the user’s inbox
along with the size of the message in bytes. If a parameter is provided, the server shows the number
and bytes of just the message indicated.

**RETR** sends the RETR command that retrieves a single message. The message to retrieve is indicated in
the TextField below the button. The message is sent back in ASCII format. The end of the message
is indicated by a line with a single period (.) on it.

**DELE** sends the DELE command that flags a message in the user’s inbox for deletion. The message to delete
is indicated in the TextField below the button. The message is flagged for deletion, but will only be
deleted for real (expunged) when the user logs out using the QUIT button.

**UIDL** sends the UIDL command that retrieves the unique identifier for a single message. The message is
indicated in the TextField below the button. The UIDL is often the MD5 hash digest of the message
and its contents, but can be any unique identifier that the system generates.

**RSET** sends the RSET command that resets the user’s session. Importantly, it clears the deletion flags set
using the DELE button.

**QUIT** send the QUIT command that closes the session successfully. The command causes the server to close
its TCP connection with the client and expunge any messages flagged for deletion.

![image](https://user-images.githubusercontent.com/75926858/213996948-4699ebe3-e659-49cf-8830-096eec798192.png)
# RAW COMMAND
In addition to the buttons, the bottom TextField in the Raw Command Entry lets the user send raw
commands to the server, not just using the buttons.

# Server Responses
The POP3 protocol specifies that all commands result in either single or multiline responses. Multiline
responses end with a line with a single period (.). The client could therefore use a state-based algorithm to
listen for either a single line response or a multiline response after each command.
Despite this option, to make the client simpler (and not get into a state-machine like algorithm for listening
for responses), we will implement a separate listening thread which will display any lines received from the
server in the central “log” TextArea on the bottom.

# NOTE
you might need to figure your javafx here is a guide on how to do it https://openjfx.io/openjfx-docs/
