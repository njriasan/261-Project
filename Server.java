/*
 * File intended to serve a simulated server for any testing purposes.
 * We will initially assume 1 server but if we decide to use N servers then
 * we can simply start up more processes.
 */
public class Server {

public static void startServer (int port) {
	ServerSocket serverSock = new ServerSocket (port);
	connectionSock = serverSock.accept ();
}

public static void main (String[] args) {
	if (args.length != 1) {
		System.Error.println ("Must specify a port");
		System.exit (1);
	}
	try {
		int port = Integer.parseInt (args[0]);
	} catch (NumberFormatException e) {
		System.Error.println ("Port is not a valid integer.");
		System.exit (1);
	}
	try {
		startServer (port);
	} catch 
}

}
