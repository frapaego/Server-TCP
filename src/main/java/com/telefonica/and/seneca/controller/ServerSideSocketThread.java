package com.telefonica.and.seneca.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ServerSideSocketThread extends Thread {

	private final Log logger = LogFactory.getLog(getClass());

	private Socket server = null;

	public ServerSideSocketThread(Socket socket) {
		super("ServerSideSocketThread");
		this.server = socket;
	}

	@Override
	public void run() {
		try {
			while (server.isConnected()) {
				final PrintWriter toClient = new PrintWriter(server.getOutputStream(), true);
				final BufferedReader fromClient = new BufferedReader(new InputStreamReader(server.getInputStream()));
				final String line = fromClient.readLine();
				logger.info("Server received: " + line);

				boolean lc = false;
				if (line.contains("$1#")) {
					lc = true;
					toClient.println("[$2#|LC]");

					logger.info("Server send: " + "[$2#|LC]");
				}
				while (lc) {
					for (int i = 1; i <= 3; i++) {
						toClient.println("[$3#|600400" + i
								+ "|22/10/2006 14:15:11|39,863872|N|3,958706|W||||254||67,5|COORD. GEOGRAFICAS: WGS-84; COORD. UTM: ED-50(HUSO 30)|85|418112,25998334|4413295,4490385|0]");
						logger.info("Server send: " + "[$3#|600400" + i
								+ "|22/10/2006 14:15:11|39,863872|N|3,958706|W||||254||67,5|COORD. GEOGRAFICAS: WGS-84; COORD. UTM: ED-50(HUSO 30)|85|418112,25998334|4413295,4490385|0]");
					}

					for (int i = 1; i <= 3; i++) {
						toClient.println(
								"[$4#|600400" + i + "|22/10/2006 14:15:11|32769|418210,16475565|441874,1574774|0]");
						logger.info("Server send: " + "[$4#|600400" + i
								+ "|22/10/2006 14:15:11|32769|418210,16475565|441874,1574774|0]");
					}
					sleep(10000);
				}

				toClient.close();
				fromClient.close();
				// server.close();
			}

		} catch (final IOException | InterruptedException e) {
			logger.error(e.getMessage(), e);
		}
	}

}