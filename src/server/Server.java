package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {

		try (ServerSocket server = new ServerSocket(2000)) {
			System.out.println("Listen port 2000");

			while (true) {

				Socket socket = server.accept();
				Action action = new Action(socket);
				action.start();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

class Action extends Thread {

	private InputStream is;
	private OutputStream os;

	public Action(Socket socket) {
		try {

			is = socket.getInputStream();
			os = socket.getOutputStream();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int generateRandomNumber() {
		return (int) (Math.random() * 100);
	}

	@Override
	public void run() {

		int secretNum = generateRandomNumber();
		int numClt, tries = 0;

		System.out.println("Client connected");
		
		while (tries < 7) {
			tries++;
			try {
				numClt = is.read();
				System.out.println("\tClient write: " + numClt);
				if (numClt == secretNum) {
					os.write(("You put the correct number, congratulations!! " + tries +" tries").getBytes());
					break;
				} else if (numClt < secretNum) {
					os.write(("You wrote a smaller number, try again - tries:" + tries).getBytes());
				} else {
					os.write(("You wrote a greater number, try again - tries:" + tries).getBytes());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			is.close();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Client disconnected");

	}

}