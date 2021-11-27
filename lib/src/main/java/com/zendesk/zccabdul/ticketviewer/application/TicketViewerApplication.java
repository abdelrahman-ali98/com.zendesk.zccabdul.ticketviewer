package com.zendesk.zccabdul.ticketviewer.application;

import com.zendesk.zccabdul.ticketviewer.javafx.fxml.TicketsViewerFxmlView;
import com.zendesk.zccabdul.ticketviewer.model.TicketViewer;

import javafx.application.Application;

public class TicketViewerApplication {

	private static TicketViewer ticketViewer;

	public static void main(String[] args) {
		// launch UI
		Application.launch(TicketsViewerFxmlView.class, args);
	}

	/**
	 * @author AbdelrahmanAli
	 * @return
	 */
	public static TicketViewer getTicketViewer() {
		if (ticketViewer == null) {
			ticketViewer = new TicketViewer();
		}
		return ticketViewer;
	}
}
