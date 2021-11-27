package com.zendesk.zccabdul.ticketviewer.javafx.fxml.controller;

import java.util.List;

import com.zendesk.zccabdul.ticketviewer.controller.TOTicket;
import com.zendesk.zccabdul.ticketviewer.controller.TicketViewerController;
import com.zendesk.zccabdul.ticketviewer.javafx.fxml.TicketsViewerFxmlView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;



public class ViewTicketsController {

	ObservableList<String> myList = FXCollections.observableArrayList();

	@FXML
	private TableView<TOTicket> overviewTable;

	@FXML
	private Text descriptionText;
	
	@FXML
	private Button nextButton;
	
	@FXML
	private Button previousButton;
	@FXML
	private Label totalTickets;
	
	@FXML
	private Label errorLabel;
	/**
	 * init UI elements
	 * 
	 * @author AbdelrahmanAli
	 */
	@FXML
	public void initialize() {
		
		// initialize the overview table by adding new columns
		overviewTable.getColumns().add(createTableColumn("ID", "id"));
		overviewTable.getColumns().add(createTableColumn("Requester", "requester"));		
		overviewTable.getColumns().add(createTableColumn("Subject", "subject"));		
		overviewTable.getColumns().add(createTableColumn("Status", "status"));
		totalTickets.setText(" Tickets");
		errorLabel.setTextFill(Color.RED);
		// overview table if a refreshable element
		overviewTable.addEventHandler(TicketsViewerFxmlView.REFRESH_EVENT, event -> {
			try {
				overviewTable.setItems(getNextTickets());
			} catch (Exception e) {
				errorLabel.setText(e.getMessage());
			}
		});
		previousButton.setDisable(true);
				
		overviewTable.setOnMouseClicked( event -> {
			System.out.println(overviewTable.getSelectionModel().getSelectedItem().getId());
		
			if (descriptionText != null)
			descriptionText.setText(TicketViewerController.getTicketDescription(overviewTable.getSelectionModel().getSelectedItem().getId()));
		});
		// register refreshable nodes
		TicketsViewerFxmlView.getInstance().registerRefreshEvent(overviewTable);

				
	}
	/**
	 * @author AbdelrahmanAli
	 */
	private ObservableList<TOTicket> getNextTickets() throws Exception{
		List<TOTicket> assignments = null;
		try {
			assignments = TicketViewerController.getNextTickets();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		totalTickets.setText(TicketViewerController.getTotalNumOfTickets() + " Tickets");
		return FXCollections.observableList(assignments);
	}


	/**
	 * This method will create table column
	 * 
	 * @author AbdelrahmanAli
	 * @return column of requested TOAssignment
	 */
	public static TableColumn<TOTicket, String> createTableColumn(String header, String propertyName) {
		TableColumn<TOTicket, String> column = new TableColumn<TOTicket, String>(header);
		column.setCellValueFactory(new PropertyValueFactory<>(propertyName));
		return column;
	}
	@FXML
	public void loadNextPage(ActionEvent event) {
		errorLabel.setText("");
		ObservableList<TOTicket> tickets= null;
		try {
			tickets = getNextTickets();
		} catch (Exception e) {
			System.out.println("should change label for error");
			errorLabel.setText(e.getMessage());
		}
		if (tickets == null || tickets.size() == 0) {
			nextButton.setText("That is it buddy");
			previousButton.setDisable(false);
			nextButton.setDisable(true);
			return;
		}
		if (tickets.size() < 25)
			nextButton.setDisable(true);
		overviewTable.setItems(tickets);
		previousButton.setDisable(false);
		System.out.println("load next clicked");
		totalTickets.setText(TicketViewerController.getTotalNumOfTickets() + " Tickets");
	}
	// Event Listener on Button.onAction
	@FXML
	public void loadPreviousPage(ActionEvent event) {
		ObservableList<TOTicket> tickets= null;
		try {
			tickets = getPreviousTickets();
		} catch (Exception e) {
			totalTickets.setText(e.getMessage());
			System.out.println("should change label for error");
		}
		if (tickets == null || tickets.size() == 0) {
			System.out.println("This is the very begining");
			nextButton.setText("next");
			previousButton.setDisable(true);
			nextButton.setDisable(false);
			return;
		}
		overviewTable.setItems(tickets);
		nextButton.setDisable(false);
		System.out.println("load previous clicked");
		totalTickets.setText(TicketViewerController.getTotalNumOfTickets() + " Tickets");
	}
	private ObservableList<TOTicket> getPreviousTickets() throws Exception {
		List<TOTicket> assignments = null;
		try {
		assignments = TicketViewerController.getPreviousTickets();
		System.out.println("should change label for error");
		}catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		return FXCollections.observableList(assignments);
	}
}
