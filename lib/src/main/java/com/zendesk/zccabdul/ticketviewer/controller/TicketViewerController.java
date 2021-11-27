package com.zendesk.zccabdul.ticketviewer.controller;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

import com.zendesk.zccabdul.ticketviewer.application.TicketViewerApplication;
import com.zendesk.zccabdul.ticketviewer.model.Tag;
import com.zendesk.zccabdul.ticketviewer.model.Ticket;
import com.zendesk.zccabdul.ticketviewer.model.TicketViewer;

import org.json.JSONArray;

/**
 * @author AbdelrahmanAli
 *
 */
public class TicketViewerController {

	// TODO add your token here
	private static final String API_KEY = "YWJkZWxyYWhtYW4uYWxpMkBtYWlsLm1jZ2lsbC5jYS90b2tlbjpSQUtuMk1URTNUNDJGbFh2VVdiZm9lQmQ4U1FuNU90OTc2U2hEcm81";
	// TODO add your Domain here
	private static final String DOMAIN = "zccabdul";
	private static final String NUMBER_TICKETS = "https://" + DOMAIN + ".zendesk.com/api/v2/tickets/count.json";
	private static String nextPage = "https://" + DOMAIN + ".zendesk.com/api/v2/tickets.json";

	private static int numOfTotalTicketsDowloaded = 0;
	private static int numOfTotalTickets = 0; 				// Total num on the cloud
	private static int nextTicket = 0; 						// next ticket to be displayed
	private static int previousTicket = 0; 					// last ticket in last page

	/**
	 * This method will download some tickets from the server
	 * 
	 * @author AbdelrahmanAli
	 * @throws Exception
	 */
	private static void loadTickets() throws Exception {

		Runnable r = new Runnable() {
			public void run() {

				try {
					backgroudTask(NUMBER_TICKETS);
					backgroudTask(nextPage);
				} catch (Exception e) {
					System.out.println("Error while downloading, Can't load tickets");
				}

			}
		};
		Thread thread = new Thread(r);
		thread.start();

		try { 								// I started it in background thread since it should be
			thread.join();					// but I joined it right after since this app does not do anything else
		} catch (InterruptedException e) {
		}
	}

	/**
	 * This method will parse json object and 
	 * add tickets to model layer if any
	 * 
	 * @author AbdelrahmanAli
	 * @param rawTickets json object as string
	 * @throws Exception
	 */
	public static void setTickets(String rawTickets) throws Exception {

		if (rawTickets != null && !(rawTickets.equals(""))) {

			List<String> tickets = new ArrayList<>();

			try {
				JSONObject jsonObject = new JSONObject(rawTickets);

				nextPage = jsonObject.get("next_page") == null ? "" : jsonObject.get("next_page").toString();

				JSONArray result = new JSONArray(jsonObject.get("tickets").toString());
				for (int i = 0; i < result.length(); i++) {
					JSONObject jsonPart = result.getJSONObject(i);
					tickets.add(jsonPart.toString());

				}
				parseTickets(tickets);
			} catch (Exception e) {
				throw new Exception("Failed fetching tickets");
			}

		} else {
			// Handle input data error
		}
		numOfTotalTicketsDowloaded = TicketViewerApplication.getTicketViewer().getTickets().size();
	}

	/**
	 * helper method to parsr tickets 
	 * 
	 * @author AbdelrahmanAli
	 * @param tickets
	 * @throws Exception
	 */
	private static void parseTickets(List<String> tickets) throws Exception {

		TicketViewer ticketViewer = TicketViewerApplication.getTicketViewer();

		for (String toParseTicket : tickets) {

			try {
				JSONObject jsonObject = new JSONObject(toParseTicket);

				long requester_id = jsonObject.getLong("requester_id");
				long assignee_id = jsonObject.getLong("assignee_id");
				long id = jsonObject.getLong("id");
				String subject = jsonObject.getString("subject");
				String description = jsonObject.getString("description");
				String status = jsonObject.getString("status");
				String created_at = jsonObject.getString("created_at");
				Ticket ticket = new Ticket(requester_id, assignee_id, id, subject, description, status, created_at,
						ticketViewer);

				String[] tags = jsonObject.getNames(jsonObject);

				for (String tag : tags) {
					Tag toAddTag = Tag.getWithName(tag);
					if (toAddTag == null)
						toAddTag = new Tag(tag, ticketViewer);

					ticket.addTag(toAddTag);
				}
			} catch (Exception e) {
				throw new Exception("Failed parsing tickets");
			}
		}

	}

	/**
	 * @author AbdelrahmanAli
	 * @return
	 * @throws Exception
	 */
	public static List<TOTicket> getNextTickets() throws Exception {

		List<Ticket> tickets = TicketViewerApplication.getTicketViewer().getTickets();

		if (tickets.size() < 1)				// Check if there are any tickets in the system
			if (!checkInternet())			
				throw new Exception("No Internet Connection");
			else
				try {
					loadTickets();			//Download some if we do not have any
				} catch (Exception e) {
					throw new Exception(e.getMessage());
				}

		List<TOTicket> toTickets = new ArrayList<>();
		if (nextTicket > numOfTotalTicketsDowloaded)		
			return toTickets;								// return empty set if no tickets to be showed
		
		tickets = TicketViewerApplication.getTicketViewer().getTickets();
		int added = 0;
		for (int i = nextTicket; i < tickets.size() && added < 25; i++) {
			Ticket ticket = tickets.get(i);
			TOTicket toTicket = new TOTicket(ticket.getRequesterId(), ticket.getId(), ticket.getSubject(),
					ticket.getDescription(), ticket.getStatus(), ticket.getCreatedAt());
			for (Tag tag : ticket.getTags()) {
				toTicket.addTag(new TOTag(tag.getName()));
			}
			toTickets.add(toTicket);
			added++;
		}
		
		nextTicket += added;
		previousTicket = nextTicket - added;

		// load tickets in background if we have a buffer less than the page size which is 25
		if (nextTicket + 25 > TicketViewerApplication.getTicketViewer().getTickets().size() && !nextPage.equals("")
				&& numOfTotalTicketsDowloaded < numOfTotalTickets)
			try {
				loadTickets();
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		
		return toTickets;
	}

	/**
	 * This method will return the previous tickets 
	 * from last page if any
	 * 
	 * @author AbdelrahmanAli
	 * @return list of tickets as transfer objects 
	 * @throws Exception
	 */
	public static List<TOTicket> getPreviousTickets() throws Exception {

		List<TOTicket> toTickets = new ArrayList<>();
		if (previousTicket == 0)						// Check if there were tickets shown before
			return toTickets;							// return empty list if no previous tickets were shown
		
		List<Ticket> tickets = TicketViewerApplication.getTicketViewer().getTickets();

		int added = 0;
		for (int i = previousTicket - 1; i < tickets.size() && added < 25 && i >= 0; i--) {
			Ticket ticket = tickets.get(i);
			TOTicket toTicket = new TOTicket(ticket.getRequesterId(), ticket.getId(), ticket.getSubject(),
					ticket.getDescription(), ticket.getStatus(), ticket.getCreatedAt());
			for (Tag tag : ticket.getTags()) {
				toTicket.addTag(new TOTag(tag.getName()));
			}
			toTickets.add(toTicket);
			added++;
		}
		
		previousTicket -= added;
		nextTicket = previousTicket + added;

		if (nextTicket < 0)				//make sure we still in boundary
			nextTicket = 0;
		
		if (nextTicket + 25 > TicketViewerApplication.getTicketViewer().getTickets().size() && !nextPage.equals("")
				&& numOfTotalTicketsDowloaded < numOfTotalTickets)
			try {
				loadTickets();
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		Collections.reverse(toTickets);
		return toTickets;
	}

	/**
	 * 
	 * @author AbdelrahmanAli
	 * @param id ticket's id
	 * @return ticket's description of provided id if any
	 */
	public static String getTicketDescription(long id) {
		// This code be optamised by setting the id in ticket class to be unique, then
		// get the ticket in O(1) instead of O(n)
		for (Ticket ticket : TicketViewerApplication.getTicketViewer().getTickets())
			if (ticket.getId() == id)
				return ticket.getDescription();
		return null;
	}

	/**
	 * This methods
	 * 
	 * @author AbdelrahmanAli
	 * @param url to request data from
	 * @throws Exception
	 */
	private static void backgroudTask(String url) throws Exception {

		try {
			String result = loadTickets(url);
			JSONObject jsonObject = new JSONObject(result);

			if (url.equals(NUMBER_TICKETS)) { // if URL was fetching Num of Tickets
				numOfTotalTickets = jsonObject.getJSONObject("count").getInt("value");
				System.out.println("Num of tickets on Zendesk: " + numOfTotalTickets);
			} else // if url was to get tickets, add tickets to the model layer
				setTickets(result);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}

	/**
	 * This method uses Http to request data
	 * 
	 * @author AbdelrahmanAli
	 * @param rawUrl to request data from
	 * @return json string
	 * @throws Exception
	 */
	private static String loadTickets(String rawUrl) throws Exception {
		String resultJson = "";
		try {
			URL url = new URL(rawUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("Authorization", "Basic " + API_KEY);
			InputStream inputStream = connection.getInputStream();
			InputStreamReader reader = new InputStreamReader(inputStream);
			int data = reader.read();
			while (data != -1) {
				char current = (char) data;
				resultJson += current;
				data = reader.read();

			}
		} catch (Exception e) {
			throw new Exception("Failed parsing tickets");
		}
		return resultJson;
	}

	/**
	 * 
	 * @author AbdelrahmanAli
	 * @return total num of tickets on the server
	 */
	public static int getTotalNumOfTickets() {
		return numOfTotalTickets;
	}

	/**
	 * This method to check Internet connectivity
	 * 
	 * @author AbdelrahmanAli
	 * @return true if there is Internet connection
	 */
	private static boolean checkInternet() {
		try {
			URL url = new URL("http://www.google.com");
			URLConnection connection = url.openConnection();
			connection.connect();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
