/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package com.zendesk.zccabdul.ticketviewer.controller;
import java.util.*;

// line 3 "../../../../../TicketViewerTransferObjects.ump"
public class TOTicket
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOTicket Attributes
  private long requester;
  private long id;
  private String subject;
  private String description;
  private String status;
  private String createdAt;

  //TOTicket Associations
  private List<TOTag> tags;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOTicket(long aRequester, long aId, String aSubject, String aDescription, String aStatus, String aCreatedAt)
  {
    requester = aRequester;
    id = aId;
    subject = aSubject;
    description = aDescription;
    status = aStatus;
    createdAt = aCreatedAt;
    tags = new ArrayList<TOTag>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setRequester(int aRequester)
  {
    boolean wasSet = false;
    requester = aRequester;
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

  public long getRequester()
  {
    return requester;
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
  public TOTag getTag(int index)
  {
    TOTag aTag = tags.get(index);
    return aTag;
  }

  public List<TOTag> getTags()
  {
    List<TOTag> newTags = Collections.unmodifiableList(tags);
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

  public int indexOfTag(TOTag aTag)
  {
    int index = tags.indexOf(aTag);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTags()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addTag(TOTag aTag)
  {
    boolean wasAdded = false;
    if (tags.contains(aTag)) { return false; }
    tags.add(aTag);
    if (aTag.indexOfTOTicket(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aTag.addTOTicket(this);
      if (!wasAdded)
      {
        tags.remove(aTag);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeTag(TOTag aTag)
  {
    boolean wasRemoved = false;
    if (!tags.contains(aTag))
    {
      return wasRemoved;
    }

    int oldIndex = tags.indexOf(aTag);
    tags.remove(oldIndex);
    if (aTag.indexOfTOTicket(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aTag.removeTOTicket(this);
      if (!wasRemoved)
      {
        tags.add(oldIndex,aTag);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addTagAt(TOTag aTag, int index)
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

  public boolean addOrMoveTagAt(TOTag aTag, int index)
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
    ArrayList<TOTag> copyOfTags = new ArrayList<TOTag>(tags);
    tags.clear();
    for(TOTag aTag : copyOfTags)
    {
      aTag.removeTOTicket(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "requester" + ":" + getRequester()+ "," +
            "id" + ":" + getId()+ "," +
            "subject" + ":" + getSubject()+ "," +
            "description" + ":" + getDescription()+ "," +
            "status" + ":" + getStatus()+ "," +
            "createdAt" + ":" + getCreatedAt()+ "]";
  }
}