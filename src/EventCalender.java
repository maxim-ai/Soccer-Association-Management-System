import java.util.*;
import java.sql.Date;
import java.sql.Time;

public class EventCalender
{

  private Match match;
  private List<GameEvent> gameEvents;


  public EventCalender(Match aMatch)
  {
    match = aMatch;
    gameEvents = new ArrayList<GameEvent>();
  }


  public Match getMatch()
  {
    return match;
  }

  public GameEvent getGameEvent(int index)
  {
    GameEvent aGameEvent = gameEvents.get(index);
    return aGameEvent;
  }

  public List<GameEvent> getGameEvents()
  {
    List<GameEvent> newGameEvents = Collections.unmodifiableList(gameEvents);
    return newGameEvents;
  }

  public int numberOfGameEvents()
  {
    int number = gameEvents.size();
    return number;
  }

  public boolean hasGameEvents()
  {
    boolean has = gameEvents.size() > 0;
    return has;
  }

  public int indexOfGameEvent(GameEvent aGameEvent)
  {
    int index = gameEvents.indexOf(aGameEvent);
    return index;
  }

  public static int minimumNumberOfGameEvents()
  {
    return 0;
  }

  public boolean addGameEvent(GameEvent aGameEvent)
  {
    boolean wasAdded = false;
    if (gameEvents.contains(aGameEvent)) { return false; }
    EventCalender existingEventCalender = aGameEvent.getEventCalender();
    boolean isNewEventCalender = existingEventCalender != null && !this.equals(existingEventCalender);
    if (isNewEventCalender)
    {
      aGameEvent.setEventCalender(this);
    }
    else
    {
      gameEvents.add(aGameEvent);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeGameEvent(GameEvent aGameEvent)
  {
    boolean wasRemoved = false;
    //Unable to remove aGameEvent, as it must always have a eventCalender
    if (!this.equals(aGameEvent.getEventCalender()))
    {
      gameEvents.remove(aGameEvent);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public void delete()
  {
    for(int i=gameEvents.size(); i > 0; i--)
    {
      GameEvent aGameEvent = gameEvents.get(i - 1);
      aGameEvent.delete();
    }
  }

}