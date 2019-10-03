import java.io.*;
import java.net.*;
import java.util.*;

/**
 *  LightSystem connects to the clients which are sockets for LightPanels
 *  on the default port. It will notify all clients to turn on or off when 
 *  it is turned on or off.
 */
public class LightSystem extends Thread {
	public static final int DEFAULT_PORT = 9223;
	public static final String HIGH = "H";
	public static final String LOW = "L";

	private static Random random = new Random();

	public static Random getRandom() {
		return random;
	}

	private Set<PrintWriter> clients = new HashSet<>();
	private boolean isHigh = false;
	private int port;

	/**
	 *  LightSystem is the default constructor for a LightSystem
	 */
	public LightSystem() {
		this(DEFAULT_PORT);
	}

	/**
	 * LightSystem(port) is an explicit constructor for a LightSystem
	 * initializes the system to port and starts the thread
	 * @param port  type int, the port to run the LightSystem socket
	 */
	public LightSystem(int port) {
		this.port = port;
		start();
	}

	/**
	 * run() is the process for the thread initialized in the constructor.
	 * It adds the clients and notifies them
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			while (true) {
				Socket clientSocket = serverSocket.accept();

				System.out.println(clientSocket + " connected");

				PrintWriter clientOut = new PrintWriter(clientSocket.getOutputStream(), true);
				clients.add(clientOut);
				BufferedReader clientSocketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				LightSystemThread thread = new LightSystemThread(this, clientSocketIn);
				thread.start();
				notifyClient(clientOut);
			}
		} catch (BindException e) {
			throw new RuntimeException("LightSystem/other already running on port");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * switchOn() turns on all the clients in the LightSystem
	 */
	public void switchOn() {
		if (!isHigh) {
			isHigh = true;
			notifyClients();
		}
	}

	/**
	 * switchOff() turns off all the clients in the LightSystem
	 */
	public void switchOff() {
		if (isHigh) {
			isHigh = false;
			notifyClients();
		}
	}

	/**
	 * notifyClients() notifies all the clients in the LightSystem
	 */
	private void notifyClients() {
		Iterator<PrintWriter> it = clients.iterator();
		while (it.hasNext()) {
			PrintWriter clientOut = (PrintWriter) it.next();
			notifyClient(clientOut);
		}
	}

	/**
	 * notifyClient() sends a high or low signal to the client
	 * @param clientOut  type PrintWriter, the client to be notified
	 */
	private void notifyClient(PrintWriter clientOut) {
		if (isHigh)
			clientOut.println(HIGH);
		else
			clientOut.println(LOW);
	}
}