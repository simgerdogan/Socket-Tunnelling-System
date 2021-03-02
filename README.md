# Socket-Tunnelling-System

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

![ ](screenshots/socket%20tunnel%20architecture.jpg)

![ ](screenshots/socket%20tunnel%20process%20comunication.jpg)





### [Back To The Top](#Socket-Tunnelling-System)

---

## Process

It is a process on the client side that listens on one or more TCP / UDP ports according to the settings given by the user.
The incoming connection can be encrypted or plain data.This process (socket tunnel client) must accept incoming communication and its data is encrypted using SSL / TLS. - even if they are already encrypted as a result of the protocol requirements.To achieve this, it generated your own private key and certificate pair using OpenSSL, which is available for free for Linux, Windows and Macs.

In addition, a PEM (Privacy Enhanced Mail) file has been created, combining the newly created private key and certificate.
The client application should then be able to encrypt any incoming data stream and pass it to the listening port of the socket tunnel server.

Socket tunnel client / server pair can bypass any deep packet inspection routers by acting as an HTTPS connection.
Therefore, if the socket tunnel server is set to listen on the HTTPS port 443 on an open Internet, the socket tunnel client should be able to transmit data and communicate with the server without any problems and firewall interruptions.On the server side, the socket tunnel server must listen on a specific port and transmit incoming data to a predefined local port or any third party service.


### [Back To The Top](#Socket-Tunnelling-System)

---

## Techonologies & Languages

- Java ( programming language )


### [Back To The Top](#Socket-Tunnelling-System)

---



