package client;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) {

		Scanner keyboard = new Scanner(System.in);
		int number;
		byte[] buffer;
		String message;

		try (Socket socket = new Socket("10.13.0.5", 2000)) {
			
			System.out.println("You have 7 tries");

			OutputStream os = socket.getOutputStream();
			InputStream is = socket.getInputStream();

			while (true) {
				System.out.print("Write a number: ");
				number = keyboard.nextInt();
				keyboard.nextLine();

				os.write(number);

				buffer = new byte[256];
				is.read(buffer);

				message = new String(buffer);
				System.out.println(message);
				if (message.contains("congratulations") || message.contains("7"))
					break;
			}

			is.close();
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		keyboard.close();
	}

}
