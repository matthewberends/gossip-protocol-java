package gossip_protocol.src;

import java.util.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

public class WorkerThread implements Runnable {

	private String _command;
	private String _nodeAddress;
	private Socket _outgoingSocket;

    public WorkerThread(String command) {
    	_command = command;
    }

    public void run() {
    	String[] parsedCommand = parseCommand();
		dispatchCommand(parsedCommand);
    }

    private String[] parseCommand() {
    	//remove extra white spaces
        _command = _command.replaceAll(" +", " ");
    	return _command.split(" ");
    }

    private void dispatchCommand(String[] parsedCommand) {
    	switch(parsedCommand[0]) {
    		case "connect":
                if (parsedCommand.length > 1) {
    			    connect(parsedCommand[1]);
                } else {
                    System.out.println("Please specify IP address to connect to.");
                }
    			break;
    		case "fail":
    			fail();
    			break;
    		case "gracefulShutdown":
    			gracefulShutdown();
    			break;
    		default:
    			break;
    	}
    }

    private void connect(String address) {
        String[]addressAndPort = address.split(":");
        if (addressAndPort.length != 2) {
            System.out.println("Please specify address and port in the proper format \"<address>:<port>\"");
            return;
        }
        
        address = addressAndPort[0];
        int port = Integer.valueOf(addressAndPort[1]);
        
        System.out.println("Connecting to " + address + ":" + port);
    	try {
    		_outgoingSocket = new Socket(address, port);
    		_outgoingSocket.close();
    	} catch (IOException e) {

    	} 
    }

    private void fail() {
    	System.out.println("Fail");
    }

    private void gracefulShutdown() {
    	System.out.println("Graceful shutdown");
    }

}