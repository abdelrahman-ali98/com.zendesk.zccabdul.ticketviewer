/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package com.zendesk.zccabdul.ticketviewer.model;
import java.util.*;

// line 17 "../../../../../ticketViewer.ump"
public class Tag
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, Tag> tagsByName = new HashMap<String, Tag>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Tag Attributes
  private String name;

  //Tag Associations
  private TicketViewer ticketViewer;
  private List<Ticket> tickets;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Tag(String aName, TicketViewer aTicketViewer)
  {
    if (!setName(aName))
    {
      throw new RuntimeException("Cannot create due to duplicate name. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    boolean didAddTicketViewer = setTicketViewer(aTicketViewer);
    if (!didAddTicketViewer)
    {
      throw new RuntimeException("Unable to create tag due to ticketViewer. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    tickets = new ArrayList<Ticket>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    String anOldName = getName();
    if (anOldName != null && anOldName.equals(aName)) {
      return true;
    }
    if (hasWithName(aName)) {
      return wasSet;
    }
    name = aName;
    wasSet = true;
    if (anOldName != null) {
      tagsByName.remove(anOldName);
    }
    tagsByName.put(aName, this);
    return wasSet;
  }

  public String getName()
  {
    return name;
  }
  /* Code from template attribute_GetUnique */
  public static Tag getWithName(String aName)
  {
    return tagsByName.get(aName);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithName(String aName)
  {
    return getWithName(aName) != null;
  }
  /* Code from template association_GetOne */
  public TicketViewer getTicketViewer()
  {
    return ticketViewer;
  }
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
  /* Code from template association_SetOneToMany */
  public boolean setTicketViewer(TicketViewer aTicketViewer)
  {
    boolean wasSet = false;
    if (aTicketViewer == null)
    {
      return wasSet;
    }

    TicketViewer existingTicketViewer = ticketViewer;
    ticketViewer = aTicketViewer;
    if (existingTicketViewer != null && !existingTicketViewer.equals(aTicketViewer))
    {
      existingTicketViewer.removeTag(this);
    }
    ticketViewer.addTag(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTickets()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addTicket(Ticket aTicket)
  {
    boolean wasAdded = false;
    if (tickets.contains(aTicket)) { return false; }
    tickets.add(aTicket);
    if (aTicket.indexOfTag(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aTicket.addTag(this);
      if (!wasAdded)
      {
        tickets.remove(aTicket);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeTicket(Ticket aTicket)
  {
    boolean wasRemoved = false;
    if (!tickets.contains(aTicket))
    {
      return wasRemoved;
    }

    int oldIndex = tickets.indexOf(aTicket);
    tickets.remove(oldIndex);
    if (aTicket.indexOfTag(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aTicket.removeTag(this);
      if (!wasRemoved)
      {
        tickets.add(oldIndex,aTicket);
      }
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

  public void delete()
  {
    tagsByName.remove(getName());
    TicketViewer placeholderTicketViewer = ticketViewer;
    this.ticketViewer = null;
    if(placeholderTicketViewer != null)
    {
      placeholderTicketViewer.removeTag(this);
    }
    ArrayList<Ticket> copyOfTickets = new ArrayList<Ticket>(tickets);
    tickets.clear();
    for(Ticket aTicket : copyOfTickets)
    {
      aTicket.removeTag(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "ticketViewer = "+(getTicketViewer()!=null?Integer.toHexString(System.identityHashCode(getTicketViewer())):"null");
  }
}