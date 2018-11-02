import java.io.IOException;
import java.net.UnknownHostException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/*
 * File intended to serve as the client for any testing purposes. This should
 * be used to simulate any interactions that would otherwise occur with phone
 * clients.
 */

public class Client {
	public static final String ENCODING = "UTF-16BE";

	public static Socket connectToServer (InetAddress IPAddr, int port)
       		throws IOException {
		return new Socket (IPAddr, port);	
	}

	public static void processConnection (Socket s) throws IOException {
		Scanner standardInput = null;
		BufferedReader serverOutput = null;
		PrintWriter clientOutput = null;
		standardInput = new Scanner (System.in, ENCODING);
		try {
			serverOutput = new BufferedReader (
					new InputStreamReader (
						s.getInputStream (), ENCODING));
		} catch (IOException e) {
			System.err.println ("Unable to create input stream.");
			return;
		}
		try {
			clientOutput = new PrintWriter (
					new OutputStreamWriter (
					s.getOutputStream (), ENCODING), true);
		} catch (IOException e) {
			System.err.println ("Unable to create output stream.");
			return;
		}
		String nextLine = "";
		while (true) {
			System.out.print ("Me: ");
			if (!standardInput.hasNextLine ()) {
				throw new IOException ();
			}
			nextLine = standardInput.nextLine ();
			System.out.print (nextLine);
			clientOutput.print (nextLine);
			nextLine = serverOutput.readLine ();
			if (nextLine == null) {
				throw new IOException ();
			}
			serverOutput.skip (1);
			System.out.println (nextLine);
		}
	}

	public static void main (String []args) {
		if (args.length != 2) {
			System.err.println ("Usage: java Client <Host"
					+ "IP address> <Port>");
			System.exit (1);
		}
		InetAddress IPAddr = null;
		try {
			IPAddr = InetAddress.getByName (args[0]);	
		} catch (UnknownHostException e) {
			System.err.println ("Unable to find the specified" 
					+ " host.");
		}
		int port = -1;
		try {
			port = Integer.parseInt (args[1]);
		} catch (NumberFormatException e) {
			System.err.println ("Port specified is not a valid"
					+ " integer.");
		}
		Socket s = null;
		try {
			s = connectToServer (IPAddr, port);
		} catch (IOException e) {
			System.err.println ("Unable to connect to the server.");
		}
		try {
			processConnection (s);
		} catch (IOException e) {
		}
	}
}
