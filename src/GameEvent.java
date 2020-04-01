
import java.sql.Date;
import java.sql.Time;

public class GameEvent
{

  private EventEnum type;
  private java.util.Date date;
  private Time hour;
  private String description;
  private int gameMinute;
  private EventCalender eventCalender;



  public GameEvent(EventEnum aType, java.util.Date aDate, Time aHour, String aDescription, int aGameMinute, EventCalender aEventCalender)
  {
    type = aType;
    date = aDate;
    hour = aHour;
    description = aDescription;
    gameMinute = aGameMinute;
    if(aEventCalender!=null){
      setEventCalender(aEventCalender);
    }
  }

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

  public java.util.Date getDate()
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

  public EventCalender getEventCalender()
  {
    return eventCalender;
  }

  public boolean setEventCalender(EventCalender aEventCalender)
  {
    boolean wasSet = false;
    if (aEventCalender == null)
    {
      eventCalender=null;
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
    if(placeholderEventCalender != null)
    {
      placeholderEventCalender.removeGameEvent(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "description" + ":" + getDescription()+ "," +
            "gameMinute" + ":" + getGameMinute()+
            "  " + "type" + "=" + (getType())+
            "  " + "date" + "=" + (getDate())+
            "  " + "hour" + "=" + (getHour())+
            "  " + "eventCalender = "+(getEventCalender());
  }
}