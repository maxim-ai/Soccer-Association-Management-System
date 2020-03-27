/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4811.445d1d99b modeling language!*/


import java.util.*;
import java.sql.Date;
import java.sql.Time;

// line 99 "model.ump"
// line 259 "model.ump"
public class Season
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Season Attributes
  private String name;

  //Season Associations
  private System system;
  private List<League> leagues;
  private List<Match> matchs;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Season(String aName, System aSystem)
  {
    name = aName;
    boolean didAddSystem = setSystem(aSystem);
    if (!didAddSystem)
    {
      throw new RuntimeException("Unable to create season due to system. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    leagues = new ArrayList<League>();
    matchs = new ArrayList<Match>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }
  /* Code from template association_GetOne */
  public System getSystem()
  {
    return system;
  }
  /* Code from template association_GetMany */
  public League getLeague(int index)
  {
    League aLeague = leagues.get(index);
    return aLeague;
  }

  public List<League> getLeagues()
  {
    List<League> newLeagues = Collections.unmodifiableList(leagues);
    return newLeagues;
  }

  public int numberOfLeagues()
  {
    int number = leagues.size();
    return number;
  }

  public boolean hasLeagues()
  {
    boolean has = leagues.size() > 0;
    return has;
  }

  public int indexOfLeague(League aLeague)
  {
    int index = leagues.indexOf(aLeague);
    return index;
  }
  /* Code from template association_GetMany */
  public Match getMatch(int index)
  {
    Match aMatch = matchs.get(index);
    return aMatch;
  }

  public List<Match> getMatchs()
  {
    List<Match> newMatchs = Collections.unmodifiableList(matchs);
    return newMatchs;
  }

  public int numberOfMatchs()
  {
    int number = matchs.size();
    return number;
  }

  public boolean hasMatchs()
  {
    boolean has = matchs.size() > 0;
    return has;
  }

  public int indexOfMatch(Match aMatch)
  {
    int index = matchs.indexOf(aMatch);
    return index;
  }
  /* Code from template association_SetOneToMany */
  public boolean setSystem(System aSystem)
  {
    boolean wasSet = false;
    if (aSystem == null)
    {
      return wasSet;
    }

    System existingSystem = system;
    system = aSystem;
    if (existingSystem != null && !existingSystem.equals(aSystem))
    {
      existingSystem.removeSeason(this);
    }
    system.addSeason(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfLeagues()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addLeague(League aLeague)
  {
    boolean wasAdded = false;
    if (leagues.contains(aLeague)) { return false; }
    leagues.add(aLeague);
    if (aLeague.indexOfSeason(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aLeague.addSeason(this);
      if (!wasAdded)
      {
        leagues.remove(aLeague);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeLeague(League aLeague)
  {
    boolean wasRemoved = false;
    if (!leagues.contains(aLeague))
    {
      return wasRemoved;
    }

    int oldIndex = leagues.indexOf(aLeague);
    leagues.remove(oldIndex);
    if (aLeague.indexOfSeason(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aLeague.removeSeason(this);
      if (!wasRemoved)
      {
        leagues.add(oldIndex,aLeague);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addLeagueAt(League aLeague, int index)
  {  
    boolean wasAdded = false;
    if(addLeague(aLeague))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLeagues()) { index = numberOfLeagues() - 1; }
      leagues.remove(aLeague);
      leagues.add(index, aLeague);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveLeagueAt(League aLeague, int index)
  {
    boolean wasAdded = false;
    if(leagues.contains(aLeague))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLeagues()) { index = numberOfLeagues() - 1; }
      leagues.remove(aLeague);
      leagues.add(index, aLeague);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addLeagueAt(aLeague, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfMatchs()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Match addMatch(Date aDate, Time aTime, int aAwayScore, int aHomeScore, Stadium aStadium, EventCalender aEventCalender, Team[] allTeams, Referee[] allReferees)
  {
    return new Match(aDate, aTime, aAwayScore, aHomeScore, aStadium, this, aEventCalender, allTeams, allReferees);
  }

  public boolean addMatch(Match aMatch)
  {
    boolean wasAdded = false;
    if (matchs.contains(aMatch)) { return false; }
    Season existingSeason = aMatch.getSeason();
    boolean isNewSeason = existingSeason != null && !this.equals(existingSeason);
    if (isNewSeason)
    {
      aMatch.setSeason(this);
    }
    else
    {
      matchs.add(aMatch);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeMatch(Match aMatch)
  {
    boolean wasRemoved = false;
    //Unable to remove aMatch, as it must always have a season
    if (!this.equals(aMatch.getSeason()))
    {
      matchs.remove(aMatch);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addMatchAt(Match aMatch, int index)
  {  
    boolean wasAdded = false;
    if(addMatch(aMatch))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMatchs()) { index = numberOfMatchs() - 1; }
      matchs.remove(aMatch);
      matchs.add(index, aMatch);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveMatchAt(Match aMatch, int index)
  {
    boolean wasAdded = false;
    if(matchs.contains(aMatch))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMatchs()) { index = numberOfMatchs() - 1; }
      matchs.remove(aMatch);
      matchs.add(index, aMatch);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addMatchAt(aMatch, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    System placeholderSystem = system;
    this.system = null;
    if(placeholderSystem != null)
    {
      placeholderSystem.removeSeason(this);
    }
    ArrayList<League> copyOfLeagues = new ArrayList<League>(leagues);
    leagues.clear();
    for(League aLeague : copyOfLeagues)
    {
      aLeague.removeSeason(this);
    }
    for(int i=matchs.size(); i > 0; i--)
    {
      Match aMatch = matchs.get(i - 1);
      aMatch.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "system = "+(getSystem()!=null?Integer.toHexString(System.identityHashCode(getSystem())):"null");
  }
}