/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package com.zendesk.zccabdul.ticketviewer.model;
import java.util.*;

// line 6 "../../../../../ticketViewer.ump"
public class Ticket
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Ticket Attributes
  private long requesterId;
  private long assigneeId;
  private long id;
  private String subject;
  private String description;
  private String status;
  private String createdAt;

  //Ticket Associations
  private List<Tag> tags;
  private TicketViewer ticketViewer;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Ticket(long aRequesterId, long aAssigneeId, long aId, String aSubject, String aDescription, String aStatus, String aCreatedAt, TicketViewer aTicketViewer)
  {
    requesterId = aRequesterId;
    assigneeId = aAssigneeId;
    id = aId;
    subject = aSubject;
    description = aDescription;
    status = aStatus;
    createdAt = aCreatedAt;
    tags = new ArrayList<Tag>();
    boolean didAddTicketViewer = setTicketViewer(aTicketViewer);
    if (!didAddTicketViewer)
    {
      throw new RuntimeException("Unable to create ticket due to ticketViewer. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setRequesterId(int aRequesterId)
  {
    boolean wasSet = false;
    requesterId = aRequesterId;
    wasSet = true;
    return wasSet;
  }

  public boolean setAssigneeId(int aAssigneeId)
  {
    boolean wasSet = false;
    assigneeId = aAssigneeId;
    wasSet = true;
    return wasSet;
  }

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public boolean setSubject(String aSubject)
  {
    boolean wasSet = false;
    subject = aSubject;
    wasSet = true;
    return wasSet;
  }

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public boolean setStatus(String aStatus)
  {
    boolean wasSet = false;
    status = aStatus;
    wasSet = true;
    return wasSet;
  }

  public boolean setCreatedAt(String aCreatedAt)
  {
    boolean wasSet = false;
    createdAt = aCreatedAt;
    wasSet = true;
    return wasSet;
  }

  public long getRequesterId()
  {
    return requesterId;
  }

  public long getAssigneeId()
  {
    return assigneeId;
  }

  public long getId()
  {
    return id;
  }

  public String getSubject()
  {
    return subject;
  }

  public String getDescription()
  {
    return description;
  }

  public String getStatus()
  {
    return status;
  }

  public String getCreatedAt()
  {
    return createdAt;
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
  /* Code from template association_GetOne */
  public TicketViewer getTicketViewer()
  {
    return ticketViewer;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTags()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addTag(Tag aTag)
  {
    boolean wasAdded = false;
    if (tags.contains(aTag)) { return false; }
    tags.add(aTag);
    if (aTag.indexOfTicket(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aTag.addTicket(this);
      if (!wasAdded)
      {
        tags.remove(aTag);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeTag(Tag aTag)
  {
    boolean wasRemoved = false;
    if (!tags.contains(aTag))
    {
      return wasRemoved;
    }

    int oldIndex = tags.indexOf(aTag);
    tags.remove(oldIndex);
    if (aTag.indexOfTicket(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aTag.removeTicket(this);
      if (!wasRemoved)
      {
        tags.add(oldIndex,aTag);
      }
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
      existingTicketViewer.removeTicket(this);
    }
    ticketViewer.addTicket(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    ArrayList<Tag> copyOfTags = new ArrayList<Tag>(tags);
    tags.clear();
    for(Tag aTag : copyOfTags)
    {
      aTag.removeTicket(this);
    }
    TicketViewer placeholderTicketViewer = ticketViewer;
    this.ticketViewer = null;
    if(placeholderTicketViewer != null)
    {
      placeholderTicketViewer.removeTicket(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "requesterId" + ":" + getRequesterId()+ "," +
            "assigneeId" + ":" + getAssigneeId()+ "," +
            "id" + ":" + getId()+ "," +
            "subject" + ":" + getSubject()+ "," +
            "description" + ":" + getDescription()+ "," +
            "status" + ":" + getStatus()+ "," +
            "createdAt" + ":" + getCreatedAt()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "ticketViewer = "+(getTicketViewer()!=null?Integer.toHexString(System.identityHashCode(getTicketViewer())):"null");
  }
}