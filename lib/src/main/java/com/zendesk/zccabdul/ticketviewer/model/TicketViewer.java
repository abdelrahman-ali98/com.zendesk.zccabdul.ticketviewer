/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package com.zendesk.zccabdul.ticketviewer.model;
import java.util.*;

// line 2 "../../../../../ticketViewer.ump"
public class TicketViewer
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TicketViewer Associations
  private List<Ticket> tickets;
  private List<Tag> tags;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TicketViewer()
  {
    tickets = new ArrayList<Ticket>();
    tags = new ArrayList<Tag>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Ticket getTicket(int index)
  {
    Ticket aTicket = tickets.get(index);
    return aTicket;
  }

  public List<Ticket> getTickets()
  {
    List<Ticket> newTickets = Collections.unmodifiableList(tickets);
    return newTickets;
  }

  public int numberOfTickets()
  {
    int number = tickets.size();
    return number;
  }

  public boolean hasTickets()
  {
    boolean has = tickets.size() > 0;
    return has;
  }

  public int indexOfTicket(Ticket aTicket)
  {
    int index = tickets.indexOf(aTicket);
    return index;
  }
  /* Code from template association_GetMany */
  public Tag getTag(int index)
  {
    Tag aTag = tags.get(index);
    return aTag;
  }

  public List<Tag> getTags()
  {
    List<Tag> newTags = Collections.unmodifiableList(tags);
    return newTags;
  }

  public int numberOfTags()
  {
    int number = tags.size();
    return number;
  }

  public boolean hasTags()
  {
    boolean has = tags.size() > 0;
    return has;
  }

  public int indexOfTag(Tag aTag)
  {
    int index = tags.indexOf(aTag);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTickets()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Ticket addTicket(int aRequesterId, int aAssigneeId, int aId, String aSubject, String aDescription, String aStatus, String aCreatedAt)
  {
    return new Ticket(aRequesterId, aAssigneeId, aId, aSubject, aDescription, aStatus, aCreatedAt, this);
  }

  public boolean addTicket(Ticket aTicket)
  {
    boolean wasAdded = false;
    if (tickets.contains(aTicket)) { return false; }
    TicketViewer existingTicketViewer = aTicket.getTicketViewer();
    boolean isNewTicketViewer = existingTicketViewer != null && !this.equals(existingTicketViewer);
    if (isNewTicketViewer)
    {
      aTicket.setTicketViewer(this);
    }
    else
    {
      tickets.add(aTicket);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTicket(Ticket aTicket)
  {
    boolean wasRemoved = false;
    //Unable to remove aTicket, as it must always have a ticketViewer
    if (!this.equals(aTicket.getTicketViewer()))
    {
      tickets.remove(aTicket);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addTicketAt(Ticket aTicket, int index)
  {  
    boolean wasAdded = false;
    if(addTicket(aTicket))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTickets()) { index = numberOfTickets() - 1; }
      tickets.remove(aTicket);
      tickets.add(index, aTicket);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTicketAt(Ticket aTicket, int index)
  {
    boolean wasAdded = false;
    if(tickets.contains(aTicket))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTickets()) { index = numberOfTickets() - 1; }
      tickets.remove(aTicket);
      tickets.add(index, aTicket);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTicketAt(aTicket, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTags()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Tag addTag(String aName)
  {
    return new Tag(aName, this);
  }

  public boolean addTag(Tag aTag)
  {
    boolean wasAdded = false;
    if (tags.contains(aTag)) { return false; }
    TicketViewer existingTicketViewer = aTag.getTicketViewer();
    boolean isNewTicketViewer = existingTicketViewer != null && !this.equals(existingTicketViewer);
    if (isNewTicketViewer)
    {
      aTag.setTicketViewer(this);
    }
    else
    {
      tags.add(aTag);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTag(Tag aTag)
  {
    boolean wasRemoved = false;
    //Unable to remove aTag, as it must always have a ticketViewer
    if (!this.equals(aTag.getTicketViewer()))
    {
      tags.remove(aTag);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addTagAt(Tag aTag, int index)
  {  
    boolean wasAdded = false;
    if(addTag(aTag))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTags()) { index = numberOfTags() - 1; }
      tags.remove(aTag);
      tags.add(index, aTag);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTagAt(Tag aTag, int index)
  {
    boolean wasAdded = false;
    if(tags.contains(aTag))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTags()) { index = numberOfTags() - 1; }
      tags.remove(aTag);
      tags.add(index, aTag);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTagAt(aTag, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    while (tickets.size() > 0)
    {
      Ticket aTicket = tickets.get(tickets.size() - 1);
      aTicket.delete();
      tickets.remove(aTicket);
    }
    
    while (tags.size() > 0)
    {
      Tag aTag = tags.get(tags.size() - 1);
      aTag.delete();
      tags.remove(aTag);
    }
    
  }

}