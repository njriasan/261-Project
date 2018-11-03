import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.ServerSocket;

/*
 * File intended to serve a simulated server for any testing purposes.
 * We will initially assume 1 server but if we decide to use N servers then
 * we can simply start up more processes.
 */
public class Server {
	public static final int NUMCONNECTIONS = 100;

	public static ServerSocket startServer (int port) throws IOException {
		return new ServerSocket (port, NUMCONNECTIONS);
	}

	public static void receiveInput (Socket s) throws IOException {
		/* stream to read from the client. */
		Scanner serverInput = null;
		PrintWriter serverOutput = null;
		try {
			serverInput = new Scanner (
						s.getInputStream ());
		} catch (IOException e) {
			System.err.println ("Unable to create input stream.");
			return;
		}
		try {
			serverOutput = new PrintWriter (
					new OutputStreamWriter (
					s.getOutputStream ()), true);
		} catch (IOException e) {
			System.err.println ("Unable to create output stream.");
			return;
		}
		String nextLine = "";
		while (true) {
			if (!serverInput.hasNextLine ()) {
				throw new IOException ();
			}
			nextLine = serverInput.nextLine ();
			System.out.println (nextLine);
			serverOutput.println ("Message successfully received."); 
		}
	}

	public static void socketListen (ServerSocket sock) {
		while (true) {
			Socket s = null;
			try {
				s = sock.accept ();
			} catch (IOException e) {
				System.err.println ("Failed to accept new" +
						" connection.");
				continue;
			}
			try {
				receiveInput (s);
			} catch (IOException e) {
				System.out.println ("Closed connection with"
						+ " client.");
			}
			try {
				s.close ();	
			} catch (IOException e) {
				System.err.println ("Unable to close socket.");
			}
		}
	}	

	public static void main (String[] args) {
		if (args.length != 1) {
			System.err.println ("Usage: java Server <Port>");
			System.exit (1);
		}
		int port = -1;
		try {
			port = Integer.parseInt (args[0]);
		} catch (NumberFormatException e) {
			System.err.println ("Port is not a valid integer.");
			System.exit (1);
		}
		ServerSocket serverSock = null;
		try {
		 	serverSock = startServer (port);
		} catch (IOException e) {
			System.err.println ("Unable to create server at the"
				       + " specified port.");
			System.exit (1);	
		}
		socketListen (serverSock);
	}
}
