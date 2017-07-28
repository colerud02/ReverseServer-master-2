
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * A server program which accepts requests from clients to capitalize strings.
 * When clients connect, a new thread is started to handle an interactive dialog
 * in which the client sends in a string and the server thread sends back the
 * capitalized version of the string.
 *
 * The program is runs in an infinite loop, so shutdown in platform dependent.
 * If you ran it from a console window with the "java" interpreter, Ctrl+C
 * generally will shut it down.
 */
public class ReverserServer extends Thread {
	/**
	 * A private thread to handle capitalization requests on a particular
	 * socket. The client terminates the dialogue by sending a single line
	 * containing only a period.
	 * 
	 * Services this thread's client by first sending the client a welcome
	 * message then repeatedly reading strings and sending back the reversed
	 * version of the string.
	 */

	// **** 1. Create member variables for your socket and client number;
	Socket sock;
	int clientNum;

	/**
	 * Application method to run the server runs in an infinite loop listening
	 * on port 9898. When a connection is requested, it spawns a new thread to
	 * do the servicing and immediately returns to listening. The server keeps a
	 * unique client number for each client that connects just to show
	 * interesting logging messages. It is certainly not necessary to do this.
	 */

	// 2 ****. create a constructor for your ReverserServer that takes in a
	// Socket an int for your client number
	public ReverserServer(Socket Sock, int ClientNum) {
		sock = Sock;
		clientNum = ClientNum;
	}

	public void run() {
		try {
		// Decorate the streams so we can send characters
		// and not just bytes. Ensure output is flushed
		// after every newline.

		// 3. Create a Scanner and pass in your socket's inputStream. (hint:
		// it's a getter)
		Scanner scan = new Scanner(sock.getInputStream());

		// 4. Create a PrintWriter 'out', and pass it your socket's output
		// stream.
		PrintWriter out = new PrintWriter(sock.getOutputStream(), true);

		// 5. Use your PrintWriter Send a welcome message to the client.
		out.println("");

		while (true) {
			
				// 6. read each line using your BufferedReader. This is the user
				// input
				String input = scan.nextLine();

				// 7. break out of the loop if the user enters a certain word or
				// it returns null.
				if (input == null || input.equals(".")) {
					break;
				}
				// 8. Use your Printwriter to set back a capitalized word
				out.println(input.toUpperCase());

			// 9. if any exceptions occur, close your socket.

		}
		} catch (Exception e) {
			try {
				sock.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Logs a simple message. In this case we just write the message to the
	 * server applications standard output.
	 */
	public static void main(String[] args) {
		try {
		System.out.println("The capitalization server is running.");

		// 10. Create a variable to hold a client number
		int clientNumber = 0;

		// 11. Create a SeverSocket on port 9898
		ServerSocket serverSock = new ServerSocket();

		try {
			while (true) {
				// 12. call the accept method on your ServerSocket and store it
				// in a Socket Variable
				Socket socket = serverSock.accept();
				
				// 14. create an instance of a ReverserServer and pass it your
				// listener
				ReverserServer reverserServer = new ReverserServer(socket, clientNumber);
				// and client number.
				

				// 15. Start your Reverser.
				reverserServer.start();
			}
		} finally {
			// 16. Don't forget to close your ServerSocket!
			serverSock.close();

		}
	}
		catch(Exception e){
			
		}
	}
	
}
