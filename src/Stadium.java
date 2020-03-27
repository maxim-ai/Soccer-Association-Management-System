/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4811.445d1d99b modeling language!*/


import java.util.*;
import java.sql.Date;
import java.sql.Time;

// line 132 "model.ump"
// line 286 "model.ump"
public class Stadium
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Stadium Attributes
  private String name;

  //Stadium Associations
  private List<Team> teams;
  private List<Match> matchs;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Stadium(String aName)
  {
    name = aName;
    teams = new ArrayList<Team>();
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
  /* Code from template association_GetMany */
  public Team getTeam(int index)
  {
    Team aTeam = teams.get(index);
    return aTeam;
  }

  public List<Team> getTeams()
  {
    List<Team> newTeams = Collections.unmodifiableList(teams);
    return newTeams;
  }

  public int numberOfTeams()
  {
    int number = teams.size();
    return number;
  }

  public boolean hasTeams()
  {
    boolean has = teams.size() > 0;
    return has;
  }

  public int indexOfTeam(Team aTeam)
  {
    int index = teams.indexOf(aTeam);
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
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfTeamsValid()
  {
    boolean isValid = numberOfTeams() >= minimumNumberOfTeams() && numberOfTeams() <= maximumNumberOfTeams();
    return isValid;
  }
  /* Code from template association_RequiredNumberOfMethod */
  public static int requiredNumberOfTeams()
  {
    return 2;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTeams()
  {
    return 2;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfTeams()
  {
    return 2;
  }
  /* Code from template association_AddMNToOnlyOne */
  public Team addTeam(String aName, System aSystem, Page aPage, League aLeague)
  {
    if (numberOfTeams() >= maximumNumberOfTeams())
    {
      return null;
    }
    else
    {
      return new Team(aName, aSystem, aPage, aLeague, this);
    }
  }

  public boolean addTeam(Team aTeam)
  {
    boolean wasAdded = false;
    if (teams.contains(aTeam)) { return false; }
    if (numberOfTeams() >= maximumNumberOfTeams())
    {
      return wasAdded;
    }

    Stadium existingStadium = aTeam.getStadium();
    boolean isNewStadium = existingStadium != null && !this.equals(existingStadium);

    if (isNewStadium && existingStadium.numberOfTeams() <= minimumNumberOfTeams())
    {
      return wasAdded;
    }

    if (isNewStadium)
    {
      aTeam.setStadium(this);
    }
    else
    {
      teams.add(aTeam);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTeam(Team aTeam)
  {
    boolean wasRemoved = false;
    //Unable to remove aTeam, as it must always have a stadium
    if (this.equals(aTeam.getStadium()))
    {
      return wasRemoved;
    }

    //stadium already at minimum (2)
    if (numberOfTeams() <= minimumNumberOfTeams())
    {
      return wasRemoved;
    }
    teams.remove(aTeam);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfMatchs()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Match addMatch(Date aDate, Time aTime, int aAwayScore, int aHomeScore, Season aSeason, EventCalender aEventCalender, Team[] allTeams, Referee[] allReferees)
  {
    return new Match(aDate, aTime, aAwayScore, aHomeScore, this, aSeason, aEventCalender, allTeams, allReferees);
  }

  public boolean addMatch(Match aMatch)
  {
    boolean wasAdded = false;
    if (matchs.contains(aMatch)) { return false; }
    Stadium existingStadium = aMatch.getStadium();
    boolean isNewStadium = existingStadium != null && !this.equals(existingStadium);
    if (isNewStadium)
    {
      aMatch.setStadium(this);
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
    //Unable to remove aMatch, as it must always have a stadium
    if (!this.equals(aMatch.getStadium()))
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
    for(int i=teams.size(); i > 0; i--)
    {
      Team aTeam = teams.get(i - 1);
      aTeam.delete();
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
            "name" + ":" + getName()+ "]";
  }
}