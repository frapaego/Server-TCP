package com.telefonica.and.seneca.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ServerSideSocket {

	private final Log logger = LogFactory.getLog(getClass());

	public void run(String... args) throws Exception {
		try {
			final int serverPort = 4020;
			final ServerSocket serverSocket = new ServerSocket(serverPort);

			while (!serverSocket.isClosed()) {
				logger.info("Waiting for client on port " + serverSocket.getLocalPort() + "...");

				final Socket server = serverSocket.accept();
				logger.info("Just connected to " + server.getRemoteSocketAddress());
				final ServerSideSocketThread serverThread = new ServerSideSocketThread(server);
				serverThread.start();

				serverSocket.close();

			}
		} catch (final UnknownHostException ex) {
			logger.error(ex.getMessage(), ex);
		} catch (final IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static void main(String[] args) {
		final ServerSideSocket sss = new ServerSideSocket();
		try {
			sss.run(args);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

}