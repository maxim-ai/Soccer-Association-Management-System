/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4811.445d1d99b modeling language!*/


import java.util.*;
import java.sql.Date;
import java.sql.Time;

// line 117 "model.ump"
// line 274 "model.ump"
public class EventCalender
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //EventCalender Associations
  private Match match;
  private List<GameEvent> gameEvents;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public EventCalender(Match aMatch)
  {
    if (aMatch == null || aMatch.getEventCalender() != null)
    {
      throw new RuntimeException("Unable to create EventCalender due to aMatch. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    match = aMatch;
    gameEvents = new ArrayList<GameEvent>();
  }

  public EventCalender(Date aDateForMatch, Time aTimeForMatch, int aAwayScoreForMatch, int aHomeScoreForMatch, Stadium aStadiumForMatch, Season aSeasonForMatch, Team[] allTeamsForMatch, Referee[] allRefereesForMatch)
  {
    match = new Match(aDateForMatch, aTimeForMatch, aAwayScoreForMatch, aHomeScoreForMatch, aStadiumForMatch, aSeasonForMatch, this, allTeamsForMatch, allRefereesForMatch);
    gameEvents = new ArrayList<GameEvent>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Match getMatch()
  {
    return match;
  }
  /* Code from template association_GetMany */
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGameEvents()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public GameEvent addGameEvent(EventEnum aType, Date aDate, Time aHour, String aDescription, int aGameMinute)
  {
    return new GameEvent(aType, aDate, aHour, aDescription, aGameMinute, this);
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
  /* Code from template association_AddIndexControlFunctions */
  public boolean addGameEventAt(GameEvent aGameEvent, int index)
  {  
    boolean wasAdded = false;
    if(addGameEvent(aGameEvent))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGameEvents()) { index = numberOfGameEvents() - 1; }
      gameEvents.remove(aGameEvent);
      gameEvents.add(index, aGameEvent);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveGameEventAt(GameEvent aGameEvent, int index)
  {
    boolean wasAdded = false;
    if(gameEvents.contains(aGameEvent))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGameEvents()) { index = numberOfGameEvents() - 1; }
      gameEvents.remove(aGameEvent);
      gameEvents.add(index, aGameEvent);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addGameEventAt(aGameEvent, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    Match existingMatch = match;
    match = null;
    if (existingMatch != null)
    {
      existingMatch.delete();
    }
    for(int i=gameEvents.size(); i > 0; i--)
    {
      GameEvent aGameEvent = gameEvents.get(i - 1);
      aGameEvent.delete();
    }
  }

}