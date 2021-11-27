package com.zendesk.zccabdul.ticketviewer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import com.zendesk.zccabdul.ticketviewer.application.TicketViewerApplication;
import com.zendesk.zccabdul.ticketviewer.controller.TicketViewerController;
import com.zendesk.zccabdul.ticketviewer.model.Ticket;
import com.zendesk.zccabdul.ticketviewer.model.TicketViewer;


public class TicketViewerControllerTest {

	private static TicketViewer ticketViewer = TicketViewerApplication.getTicketViewer();

	@BeforeAll
	  public static void setUpOnce() {
		//Nothing to be setup for all methods
	  }

	  @BeforeEach
	  public void setUp() {
		  ticketViewer.delete();		//delete all data in the application
	  }

	/**
	 * @author AbdelrahmanAli
	 */
	@Test 
    public void testParseTickets() {
        String tickets = "{\"tickets\":[{\"url\":\"https://zccabdul.zendesk.com/api/v2/tickets/1.json\","
        		+ "\"id\":1,\"external_id\":null,\"via\":{\"channel\":\"sample_ticket\",\"source\":{"
        		+ "\"from\":{},\"to\":{},\"rel\":null}},\"created_at\":\"2021-11-25T01:05:42Z\",\"update"
        		+ "d_at\":\"2021-11-25T01:05:42Z\",\"type\":\"incident\",\"subject\":\"Sample ticket: "
        		+ "Meet the ticket\",\"raw_subject\":\"Sample ticket: Meet the ticket\",\"description\":\"Hi"
        		+ " there,\\n\\nI’m sending an email because I’m having a problem setting up your new product. "
        		+ "Can you help me troubleshoot?\\n\\nThanks,\\n The Customer\\n\\n\",\"priority\":\"normal\",\""
        		+ "status\":\"open\",\"recipient\":null,\"requester_id\":1524260708921,\"submitter_id\":1524260675"
        		+ "581,\"assignee_id\":1524260675581,\"organization_id\":null,\"group_id\":4414083846039,\"collab"
        		+ "orator_ids\":[],\"follower_ids\":[],\"email_cc_ids\":[],\"forum_topic_id\":null,\"problem_id\":nu"
        		+ "ll,\"has_incidents\":false,\"is_public\":true,\"due_at\":null,\"tags\":[\"sample\",\"support\",\"z"
        		+ "endesk\"],\"custom_fields\":[],\"satisfaction_rating\":null,\"sharing_agreement_ids\":[],\"fields\""
        		+ ":[],\"followup_ids\":[],\"ticket_form_id\":1500003311121,\"brand_id\":1500002345381,\"allow_channelba"
        		+ "ck\":false,\"allow_attachments\":true},{\"url\":\"https://zccabdul.zendesk.com/api/v2/tickets/2.json\""
        		+ ",\"id\":2,\"external_id\":null,\"via\":{\"channel\":\"api\",\"source\":{\"from\":{},\"to\":{},\"rel\":"
        		+ "null}},\"created_at\":\"2021-11-25T01:59:37Z\",\"updated_at\":\"2021-11-25T01:59:37Z\",\"type\":null,\"s"
        		+ "ubject\":\"velit eiusmod reprehenderit officia cupidatat\",\"raw_subject\":\"velit eiusmod reprehenderi"
        		+ "t officia cupidatat\",\"description\":\"Aute ex sunt culpa ex ea esse sint cupidatat aliqua ex consequ"
        		+ "at sit reprehenderit. Velit labore proident quis culpa ad duis adipisicing laboris voluptate velit inc"
        		+ "ididunt minim consequat nulla. Laboris adipisicing reprehenderit minim tempor officia ullamco occaeca"
        		+ "t ut laborum.\\n\\nAliquip velit adipisicing exercitation irure aliqua qui. Commodo eu laborum cillu"
        		+ "m nostrud eu. Mollit duis qui non ea deserunt est est et officia ut excepteur Lorem pariatur deseru"
        		+ "nt.\",\"priority\":null,\"status\":\"open\",\"recipient\":null,\"requester_id\":1524260675581,\"sub"
        		+ "mitter_id\":1524260675581,\"assignee_id\":1524260675581,\"organization_id\":1500634607901,\"group_i"
        		+ "d\":4414083846039,\"collaborator_ids\":[],\"follower_ids\":[],\"email_cc_ids\":[],\"forum_topic_id"
        		+ "\":null,\"problem_id\":null,\"has_incidents\":false,\"is_public\":true,\"due_at\":null,\"tags\":[\"e"
        		+ "st\",\"incididunt\",\"nisi\"],\"custom_fields\":[],\"satisfaction_rating\":null,\"sharing_agreement_i"
        		+ "ds\":[],\"fields\":[],\"followup_ids\":[],\"ticket_form_id\":1500003311121,\"brand_id\":150000234538"
        		+ "1,\"allow_channelback\":false,\"allow_attachments\":true}],\"next_page\":\"https://zccabdul.zendesk.c"
        		+ "om/api/v2/tickets.json?page=2\",\"previous_page\":null,\"count\":101}";
        try {
			TicketViewerController.setTickets(tickets);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
        assertEquals(2, ticketViewer.getTickets().size());
        Ticket ticket = ticketViewer.getTicket(0);
        assertEquals( 1, ticket.getId());
        assertEquals("open", ticket.getStatus());
        assertEquals("Sample ticket: Meet the ticket", ticket.getSubject());

    }
}
