/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package com.zendesk.zccabdul.ticketviewer.controller;
import java.util.*;

// line 13 "../../../../../TicketViewerTransferObjects.ump"
public class TOTag
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOTag Attributes
  private String tag;

  //TOTag Associations
  private List<TOTicket> tOTickets;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOTag(String aTag)
  {
    tag = aTag;
    tOTickets = new ArrayList<TOTicket>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setTag(String aTag)
  {
    boolean wasSet = false;
    tag = aTag;
    wasSet = true;
    return wasSet;
  }

  public String getTag()
  {
    return tag;
  }
  /* Code from template association_GetMany */
  public TOTicket getTOTicket(int index)
  {
    TOTicket aTOTicket = tOTickets.get(index);
    return aTOTicket;
  }

  public List<TOTicket> getTOTickets()
  {
    List<TOTicket> newTOTickets = Collections.unmodifiableList(tOTickets);
    return newTOTickets;
  }

  public int numberOfTOTickets()
  {
    int number = tOTickets.size();
    return number;
  }

  public boolean hasTOTickets()
  {
    boolean has = tOTickets.size() > 0;
    return has;
  }

  public int indexOfTOTicket(TOTicket aTOTicket)
  {
    int index = tOTickets.indexOf(aTOTicket);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTOTickets()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addTOTicket(TOTicket aTOTicket)
  {
    boolean wasAdded = false;
    if (tOTickets.contains(aTOTicket)) { return false; }
    tOTickets.add(aTOTicket);
    if (aTOTicket.indexOfTag(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aTOTicket.addTag(this);
      if (!wasAdded)
      {
        tOTickets.remove(aTOTicket);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeTOTicket(TOTicket aTOTicket)
  {
    boolean wasRemoved = false;
    if (!tOTickets.contains(aTOTicket))
    {
      return wasRemoved;
    }

    int oldIndex = tOTickets.indexOf(aTOTicket);
    tOTickets.remove(oldIndex);
    if (aTOTicket.indexOfTag(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aTOTicket.removeTag(this);
      if (!wasRemoved)
      {
        tOTickets.add(oldIndex,aTOTicket);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addTOTicketAt(TOTicket aTOTicket, int index)
  {  
    boolean wasAdded = false;
    if(addTOTicket(aTOTicket))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTOTickets()) { index = numberOfTOTickets() - 1; }
      tOTickets.remove(aTOTicket);
      tOTickets.add(index, aTOTicket);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTOTicketAt(TOTicket aTOTicket, int index)
  {
    boolean wasAdded = false;
    if(tOTickets.contains(aTOTicket))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTOTickets()) { index = numberOfTOTickets() - 1; }
      tOTickets.remove(aTOTicket);
      tOTickets.add(index, aTOTicket);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTOTicketAt(aTOTicket, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    ArrayList<TOTicket> copyOfTOTickets = new ArrayList<TOTicket>(tOTickets);
    tOTickets.clear();
    for(TOTicket aTOTicket : copyOfTOTickets)
    {
      aTOTicket.removeTag(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "tag" + ":" + getTag()+ "]";
  }
}