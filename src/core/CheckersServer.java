package core;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import javafx.application.Platform;

/**
 * Class: CheckersServer A server to host a game of checkers for two players.
 * 
 * @author Ian Skelskey
 * @version 1.0
 * @since 9-22-2021
 */
public class CheckersServer {
	public static void main(String[] args) {
		ServerThread thread = new ServerThread();
		thread.start();
	}
}

class ServerThread extends Thread {
	String input;
	DataOutputStream blueOutput;
	DataOutputStream redOutput;
	DataInputStream blueInput;
	DataInputStream redInput;

	@Override
	public void run() {
		try {
			System.out.println("ServerThread is running...");
			ServerSocket serverSocket = new ServerSocket(8000);
			System.out.println("Sever started at " + new Date() + '\n');
			System.out.println("Waiting for blue player...");
			Socket blueSocket = serverSocket.accept();
			System.out.println("Blue player found");
			System.out.println("Waiting for red player...");
			Socket redSocket = serverSocket.accept();
			System.out.println("Red player found");
			blueOutput = new DataOutputStream(blueSocket.getOutputStream());
			System.out.println("blue output initialized.");
			blueOutput.writeChar('x');
			blueOutput.flush();
			redOutput = new DataOutputStream(redSocket.getOutputStream());
			System.out.println("red output initialized.");
			redOutput.writeChar('o');
			blueInput = new DataInputStream(blueSocket.getInputStream());
			System.out.println("blue input initialized.");

			redOutput.flush();
			redInput = new DataInputStream(redSocket.getInputStream());
			System.out.println("red input initialized.");
		} catch (IOException e) {
			System.out.println("Client/Server Connection Failed");
			System.err.println(e);
		}
				char player = 'x';
		do { 
			if (player == 'x') {
				try {
					System.out.println("attempting to receive input from BLUE");
					input = blueInput.readLine();
					System.out.println("input received from BLUE: " + input);
				} catch (Exception e) {
					System.err.println(e);
				}
				try {
					redOutput.writeBytes(input + "\n");
					redOutput.flush();
					System.out.println("Instruction passed back to RED");
					input = "";
					player = 'o';
				} catch (Exception e) {
					System.err.println(e);
				}
			}else {
				try {
					System.out.println("attempting to receive input from RED");
					input = redInput.readLine();
					System.out.println("input received from RED: " + input);
				} catch (Exception e) {
					System.err.println(e);
				}
				try {
					blueOutput.writeBytes(input + "\n");
					System.out.println(input +" from blue");
					blueOutput.flush();
					System.out.println("Instruction passed back to BLUE");
					input = "";
					player = 'x';
				} catch (Exception e) {
					System.err.println(e);
				}
			}
		} while (true);
	}
}
