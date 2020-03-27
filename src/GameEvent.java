/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4811.445d1d99b modeling language!*/


import java.sql.Date;
import java.sql.Time;

// line 123 "model.ump"
// line 281 "model.ump"
public class GameEvent
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GameEvent Attributes
  private EventEnum type;
  private Date date;
  private Time hour;
  private String description;
  private int gameMinute;

  //GameEvent Associations
  private EventCalender eventCalender;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public GameEvent(EventEnum aType, Date aDate, Time aHour, String aDescription, int aGameMinute, EventCalender aEventCalender)
  {
    type = aType;
    date = aDate;
    hour = aHour;
    description = aDescription;
    gameMinute = aGameMinute;
    boolean didAddEventCalender = setEventCalender(aEventCalender);
    if (!didAddEventCalender)
    {
      throw new RuntimeException("Unable to create gameEvent due to eventCalender. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setType(EventEnum aType)
  {
    boolean wasSet = false;
    type = aType;
    wasSet = true;
    return wasSet;
  }

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setHour(Time aHour)
  {
    boolean wasSet = false;
    hour = aHour;
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

  public boolean setGameMinute(int aGameMinute)
  {
    boolean wasSet = false;
    gameMinute = aGameMinute;
    wasSet = true;
    return wasSet;
  }

  public EventEnum getType()
  {
    return type;
  }

  public Date getDate()
  {
    return date;
  }

  public Time getHour()
  {
    return hour;
  }

  public String getDescription()
  {
    return description;
  }

  public int getGameMinute()
  {
    return gameMinute;
  }
  /* Code from template association_GetOne */
  public EventCalender getEventCalender()
  {
    return eventCalender;
  }
  /* Code from template association_SetOneToMany */
  public boolean setEventCalender(EventCalender aEventCalender)
  {
    boolean wasSet = false;
    if (aEventCalender == null)
    {
      return wasSet;
    }

    EventCalender existingEventCalender = eventCalender;
    eventCalender = aEventCalender;
    if (existingEventCalender != null && !existingEventCalender.equals(aEventCalender))
    {
      existingEventCalender.removeGameEvent(this);
    }
    eventCalender.addGameEvent(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    EventCalender placeholderEventCalender = eventCalender;
    this.eventCalender = null;
    if(placeholderEventCalender != null)
    {
      placeholderEventCalender.removeGameEvent(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "description" + ":" + getDescription()+ "," +
            "gameMinute" + ":" + getGameMinute()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "type" + "=" + (getType() != null ? !getType().equals(this)  ? getType().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "hour" + "=" + (getHour() != null ? !getHour().equals(this)  ? getHour().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "eventCalender = "+(getEventCalender()!=null?Integer.toHexString(System.identityHashCode(getEventCalender())):"null");
  }
}