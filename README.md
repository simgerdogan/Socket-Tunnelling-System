# Socket-Tunnelling-System
# File-Server-with-Retransmission-Security

>This is a ReadMe to understand project info.

---

### Table Of Contents

- [Description](#description)
- [Process](#process)
- [Techonologies and Languages](#languages)

---

## Description

As a project, a socket tunneling system has been developed to support both UDP and TCP connections.The project can use a client-server model where the node behind the firewall acts as a client and the node in the open Internet acts as a server.

This system should be useful for:
(a) Encrypt unsecured protocols such as POP3, HTTP, and IMAP
(b) bypassing firewalls and routers with deep packet inspection enabled.





### [Back To The Top](#Software-Architecture-for-Natural-Disaster)

---

## Process

- The server has a predefined IP address and port number and is waiting for a client to connect.Basically clients are authenticated.
- Control protocol includes: Starting a session, Generating Session key and IV (counter for counter mode), Getting file data (block count, etc.), Requesting a missing block, Ending a session
- The transmission protocol is used to send and receive a particular block.
- Each block must be encrypted separately using the session key.
- A command line interface is provided for sending commands for the command protocol.
- It can be assumed that the sender has a public key and is known to the clients. 


### [Back To The Top](#Software-Architecture-for-Natural-Disaster)

---

## Techonologies & Languages

- Jupyter Notebook ( which is a web-based interactive development environment for Jupyter notebooks, code, and data)
- Python ( programming language )


### [Back To The Top](#Software-Architecture-for-Natural-Disaster)

---



