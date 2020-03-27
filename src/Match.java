/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4811.445d1d99b modeling language!*/


import java.sql.Date;
import java.sql.Time;
import java.util.*;

// line 105 "model.ump"
// line 265 "model.ump"
public class Match
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Match Attributes
  private Date date;
  private Time time;
  private int awayScore;
  private int homeScore;

  //Match Associations
  private List<Team> teams;
  private List<Referee> referees;
  private Stadium stadium;
  private Season season;
  private EventCalender eventCalender;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Match(Date aDate, Time aTime, int aAwayScore, int aHomeScore, Stadium aStadium, Season aSeason, EventCalender aEventCalender, Team[] allTeams, Referee[] allReferees)
  {
    date = aDate;
    time = aTime;
    awayScore = aAwayScore;
    homeScore = aHomeScore;
    teams = new ArrayList<Team>();
    boolean didAddTeams = setTeams(allTeams);
    if (!didAddTeams)
    {
      throw new RuntimeException("Unable to create Match, must have 2 teams. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    referees = new ArrayList<Referee>();
    boolean didAddReferees = setReferees(allReferees);
    if (!didAddReferees)
    {
      throw new RuntimeException("Unable to create Match, must have 3 referees. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddStadium = setStadium(aStadium);
    if (!didAddStadium)
    {
      throw new RuntimeException("Unable to create match due to stadium. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddSeason = setSeason(aSeason);
    if (!didAddSeason)
    {
      throw new RuntimeException("Unable to create match due to season. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (aEventCalender == null || aEventCalender.getMatch() != null)
    {
      throw new RuntimeException("Unable to create Match due to aEventCalender. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    eventCalender = aEventCalender;
  }

  public Match(Date aDate, Time aTime, int aAwayScore, int aHomeScore, Stadium aStadium, Season aSeason, Team[] allTeams, Referee[] allReferees)
  {
    date = aDate;
    time = aTime;
    awayScore = aAwayScore;
    homeScore = aHomeScore;
    teams = new ArrayList<Team>();
    boolean didAddTeams = setTeams(allTeams);
    if (!didAddTeams)
    {
      throw new RuntimeException("Unable to create Match, must have 2 teams. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    referees = new ArrayList<Referee>();
    boolean didAddReferees = setReferees(allReferees);
    if (!didAddReferees)
    {
      throw new RuntimeException("Unable to create Match, must have 3 referees. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddStadium = setStadium(aStadium);
    if (!didAddStadium)
    {
      throw new RuntimeException("Unable to create match due to stadium. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddSeason = setSeason(aSeason);
    if (!didAddSeason)
    {
      throw new RuntimeException("Unable to create match due to season. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    eventCalender = new EventCalender(this);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setTime(Time aTime)
  {
    boolean wasSet = false;
    time = aTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setAwayScore(int aAwayScore)
  {
    boolean wasSet = false;
    awayScore = aAwayScore;
    wasSet = true;
    return wasSet;
  }

  public boolean setHomeScore(int aHomeScore)
  {
    boolean wasSet = false;
    homeScore = aHomeScore;
    wasSet = true;
    return wasSet;
  }

  public Date getDate()
  {
    return date;
  }

  public Time getTime()
  {
    return time;
  }

  public int getAwayScore()
  {
    return awayScore;
  }

  public int getHomeScore()
  {
    return homeScore;
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
  public Referee getReferee(int index)
  {
    Referee aReferee = referees.get(index);
    return aReferee;
  }

  public List<Referee> getReferees()
  {
    List<Referee> newReferees = Collections.unmodifiableList(referees);
    return newReferees;
  }

  public int numberOfReferees()
  {
    int number = referees.size();
    return number;
  }

  public boolean hasReferees()
  {
    boolean has = referees.size() > 0;
    return has;
  }

  public int indexOfReferee(Referee aReferee)
  {
    int index = referees.indexOf(aReferee);
    return index;
  }
  /* Code from template association_GetOne */
  public Stadium getStadium()
  {
    return stadium;
  }
  /* Code from template association_GetOne */
  public Season getSeason()
  {
    return season;
  }
  /* Code from template association_GetOne */
  public EventCalender getEventCalender()
  {
    return eventCalender;
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
  /* Code from template association_AddManyToManyMethod */
  public boolean addTeam(Team aTeam)
  {
    boolean wasAdded = false;
    if (teams.contains(aTeam)) { return false; }
    if (numberOfTeams() >= maximumNumberOfTeams())
    {
      return wasAdded;
    }

    teams.add(aTeam);
    if (aTeam.indexOfMatch(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aTeam.addMatch(this);
      if (!wasAdded)
      {
        teams.remove(aTeam);
      }
    }
    return wasAdded;
  }
  /* Code from template association_AddMNToMany */
  public boolean removeTeam(Team aTeam)
  {
    boolean wasRemoved = false;
    if (!teams.contains(aTeam))
    {
      return wasRemoved;
    }

    if (numberOfTeams() <= minimumNumberOfTeams())
    {
      return wasRemoved;
    }

    int oldIndex = teams.indexOf(aTeam);
    teams.remove(oldIndex);
    if (aTeam.indexOfMatch(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aTeam.removeMatch(this);
      if (!wasRemoved)
      {
        teams.add(oldIndex,aTeam);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_SetMNToMany */
  public boolean setTeams(Team... newTeams)
  {
    boolean wasSet = false;
    ArrayList<Team> verifiedTeams = new ArrayList<Team>();
    for (Team aTeam : newTeams)
    {
      if (verifiedTeams.contains(aTeam))
      {
        continue;
      }
      verifiedTeams.add(aTeam);
    }

    if (verifiedTeams.size() != newTeams.length || verifiedTeams.size() < minimumNumberOfTeams() || verifiedTeams.size() > maximumNumberOfTeams())
    {
      return wasSet;
    }

    ArrayList<Team> oldTeams = new ArrayList<Team>(teams);
    teams.clear();
    for (Team aNewTeam : verifiedTeams)
    {
      teams.add(aNewTeam);
      if (oldTeams.contains(aNewTeam))
      {
        oldTeams.remove(aNewTeam);
      }
      else
      {
        aNewTeam.addMatch(this);
      }
    }

    for (Team anOldTeam : oldTeams)
    {
      anOldTeam.removeMatch(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfRefereesValid()
  {
    boolean isValid = numberOfReferees() >= minimumNumberOfReferees() && numberOfReferees() <= maximumNumberOfReferees();
    return isValid;
  }
  /* Code from template association_RequiredNumberOfMethod */
  public static int requiredNumberOfReferees()
  {
    return 3;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfReferees()
  {
    return 3;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfReferees()
  {
    return 3;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addReferee(Referee aReferee)
  {
    boolean wasAdded = false;
    if (referees.contains(aReferee)) { return false; }
    if (numberOfReferees() >= maximumNumberOfReferees())
    {
      return wasAdded;
    }

    referees.add(aReferee);
    if (aReferee.indexOfMatch(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aReferee.addMatch(this);
      if (!wasAdded)
      {
        referees.remove(aReferee);
      }
    }
    return wasAdded;
  }
  /* Code from template association_AddMNToMany */
  public boolean removeReferee(Referee aReferee)
  {
    boolean wasRemoved = false;
    if (!referees.contains(aReferee))
    {
      return wasRemoved;
    }

    if (numberOfReferees() <= minimumNumberOfReferees())
    {
      return wasRemoved;
    }

    int oldIndex = referees.indexOf(aReferee);
    referees.remove(oldIndex);
    if (aReferee.indexOfMatch(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aReferee.removeMatch(this);
      if (!wasRemoved)
      {
        referees.add(oldIndex,aReferee);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_SetMNToMany */
  public boolean setReferees(Referee... newReferees)
  {
    boolean wasSet = false;
    ArrayList<Referee> verifiedReferees = new ArrayList<Referee>();
    for (Referee aReferee : newReferees)
    {
      if (verifiedReferees.contains(aReferee))
      {
        continue;
      }
      verifiedReferees.add(aReferee);
    }

    if (verifiedReferees.size() != newReferees.length || verifiedReferees.size() < minimumNumberOfReferees() || verifiedReferees.size() > maximumNumberOfReferees())
    {
      return wasSet;
    }

    ArrayList<Referee> oldReferees = new ArrayList<Referee>(referees);
    referees.clear();
    for (Referee aNewReferee : verifiedReferees)
    {
      referees.add(aNewReferee);
      if (oldReferees.contains(aNewReferee))
      {
        oldReferees.remove(aNewReferee);
      }
      else
      {
        aNewReferee.addMatch(this);
      }
    }

    for (Referee anOldReferee : oldReferees)
    {
      anOldReferee.removeMatch(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setStadium(Stadium aStadium)
  {
    boolean wasSet = false;
    if (aStadium == null)
    {
      return wasSet;
    }

    Stadium existingStadium = stadium;
    stadium = aStadium;
    if (existingStadium != null && !existingStadium.equals(aStadium))
    {
      existingStadium.removeMatch(this);
    }
    stadium.addMatch(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setSeason(Season aSeason)
  {
    boolean wasSet = false;
    if (aSeason == null)
    {
      return wasSet;
    }

    Season existingSeason = season;
    season = aSeason;
    if (existingSeason != null && !existingSeason.equals(aSeason))
    {
      existingSeason.removeMatch(this);
    }
    season.addMatch(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    ArrayList<Team> copyOfTeams = new ArrayList<Team>(teams);
    teams.clear();
    for(Team aTeam : copyOfTeams)
    {
      aTeam.removeMatch(this);
    }
    ArrayList<Referee> copyOfReferees = new ArrayList<Referee>(referees);
    referees.clear();
    for(Referee aReferee : copyOfReferees)
    {
      aReferee.removeMatch(this);
    }
    Stadium placeholderStadium = stadium;
    this.stadium = null;
    if(placeholderStadium != null)
    {
      placeholderStadium.removeMatch(this);
    }
    Season placeholderSeason = season;
    this.season = null;
    if(placeholderSeason != null)
    {
      placeholderSeason.removeMatch(this);
    }
    EventCalender existingEventCalender = eventCalender;
    eventCalender = null;
    if (existingEventCalender != null)
    {
      existingEventCalender.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "awayScore" + ":" + getAwayScore()+ "," +
            "homeScore" + ":" + getHomeScore()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "time" + "=" + (getTime() != null ? !getTime().equals(this)  ? getTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "stadium = "+(getStadium()!=null?Integer.toHexString(System.identityHashCode(getStadium())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "season = "+(getSeason()!=null?Integer.toHexString(System.identityHashCode(getSeason())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "eventCalender = "+(getEventCalender()!=null?Integer.toHexString(System.identityHashCode(getEventCalender())):"null");
  }
}