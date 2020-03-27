/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4811.445d1d99b modeling language!*/


import java.util.*;
import java.sql.Date;

// line 84 "model.ump"
// line 243 "model.ump"
public class Team
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Team Attributes
  private String name;

  //Team Associations
  private List<TeamManager> teamManagers;
  private System system;
  private Page page;
  private List<Coach> coachs;
  private List<Owner> owners;
  private List<Player> players;
  private League league;
  private List<Match> matchs;
  private Stadium stadium;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Team(String aName, System aSystem, Page aPage, League aLeague, Stadium aStadium)
  {
    name = aName;
    teamManagers = new ArrayList<TeamManager>();
    boolean didAddSystem = setSystem(aSystem);
    if (!didAddSystem)
    {
      throw new RuntimeException("Unable to create team due to system. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (aPage == null || aPage.getTeam() != null)
    {
      throw new RuntimeException("Unable to create Team due to aPage. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    page = aPage;
    coachs = new ArrayList<Coach>();
    owners = new ArrayList<Owner>();
    players = new ArrayList<Player>();
    boolean didAddLeague = setLeague(aLeague);
    if (!didAddLeague)
    {
      throw new RuntimeException("Unable to create team due to league. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    matchs = new ArrayList<Match>();
    boolean didAddStadium = setStadium(aStadium);
    if (!didAddStadium)
    {
      throw new RuntimeException("Unable to create team due to stadium. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public Team(String aName, System aSystem, System aSystemForPage, Player aPlayerForPage, Coach aCoachForPage, League aLeague, Stadium aStadium)
  {
    name = aName;
    teamManagers = new ArrayList<TeamManager>();
    boolean didAddSystem = setSystem(aSystem);
    if (!didAddSystem)
    {
      throw new RuntimeException("Unable to create team due to system. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    page = new Page(aSystemForPage, this, aPlayerForPage, aCoachForPage);
    coachs = new ArrayList<Coach>();
    owners = new ArrayList<Owner>();
    players = new ArrayList<Player>();
    boolean didAddLeague = setLeague(aLeague);
    if (!didAddLeague)
    {
      throw new RuntimeException("Unable to create team due to league. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    matchs = new ArrayList<Match>();
    boolean didAddStadium = setStadium(aStadium);
    if (!didAddStadium)
    {
      throw new RuntimeException("Unable to create team due to stadium. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
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
  public TeamManager getTeamManager(int index)
  {
    TeamManager aTeamManager = teamManagers.get(index);
    return aTeamManager;
  }

  public List<TeamManager> getTeamManagers()
  {
    List<TeamManager> newTeamManagers = Collections.unmodifiableList(teamManagers);
    return newTeamManagers;
  }

  public int numberOfTeamManagers()
  {
    int number = teamManagers.size();
    return number;
  }

  public boolean hasTeamManagers()
  {
    boolean has = teamManagers.size() > 0;
    return has;
  }

  public int indexOfTeamManager(TeamManager aTeamManager)
  {
    int index = teamManagers.indexOf(aTeamManager);
    return index;
  }
  /* Code from template association_GetOne */
  public System getSystem()
  {
    return system;
  }
  /* Code from template association_GetOne */
  public Page getPage()
  {
    return page;
  }
  /* Code from template association_GetMany */
  public Coach getCoach(int index)
  {
    Coach aCoach = coachs.get(index);
    return aCoach;
  }

  public List<Coach> getCoachs()
  {
    List<Coach> newCoachs = Collections.unmodifiableList(coachs);
    return newCoachs;
  }

  public int numberOfCoachs()
  {
    int number = coachs.size();
    return number;
  }

  public boolean hasCoachs()
  {
    boolean has = coachs.size() > 0;
    return has;
  }

  public int indexOfCoach(Coach aCoach)
  {
    int index = coachs.indexOf(aCoach);
    return index;
  }
  /* Code from template association_GetMany */
  public Owner getOwner(int index)
  {
    Owner aOwner = owners.get(index);
    return aOwner;
  }

  public List<Owner> getOwners()
  {
    List<Owner> newOwners = Collections.unmodifiableList(owners);
    return newOwners;
  }

  public int numberOfOwners()
  {
    int number = owners.size();
    return number;
  }

  public boolean hasOwners()
  {
    boolean has = owners.size() > 0;
    return has;
  }

  public int indexOfOwner(Owner aOwner)
  {
    int index = owners.indexOf(aOwner);
    return index;
  }
  /* Code from template association_GetMany */
  public Player getPlayer(int index)
  {
    Player aPlayer = players.get(index);
    return aPlayer;
  }

  public List<Player> getPlayers()
  {
    List<Player> newPlayers = Collections.unmodifiableList(players);
    return newPlayers;
  }

  public int numberOfPlayers()
  {
    int number = players.size();
    return number;
  }

  public boolean hasPlayers()
  {
    boolean has = players.size() > 0;
    return has;
  }

  public int indexOfPlayer(Player aPlayer)
  {
    int index = players.indexOf(aPlayer);
    return index;
  }
  /* Code from template association_GetOne */
  public League getLeague()
  {
    return league;
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
  /* Code from template association_GetOne */
  public Stadium getStadium()
  {
    return stadium;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTeamManagers()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addTeamManager(TeamManager aTeamManager)
  {
    boolean wasAdded = false;
    if (teamManagers.contains(aTeamManager)) { return false; }
    teamManagers.add(aTeamManager);
    if (aTeamManager.indexOfTeam(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aTeamManager.addTeam(this);
      if (!wasAdded)
      {
        teamManagers.remove(aTeamManager);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeTeamManager(TeamManager aTeamManager)
  {
    boolean wasRemoved = false;
    if (!teamManagers.contains(aTeamManager))
    {
      return wasRemoved;
    }

    int oldIndex = teamManagers.indexOf(aTeamManager);
    teamManagers.remove(oldIndex);
    if (aTeamManager.indexOfTeam(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aTeamManager.removeTeam(this);
      if (!wasRemoved)
      {
        teamManagers.add(oldIndex,aTeamManager);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addTeamManagerAt(TeamManager aTeamManager, int index)
  {  
    boolean wasAdded = false;
    if(addTeamManager(aTeamManager))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTeamManagers()) { index = numberOfTeamManagers() - 1; }
      teamManagers.remove(aTeamManager);
      teamManagers.add(index, aTeamManager);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTeamManagerAt(TeamManager aTeamManager, int index)
  {
    boolean wasAdded = false;
    if(teamManagers.contains(aTeamManager))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTeamManagers()) { index = numberOfTeamManagers() - 1; }
      teamManagers.remove(aTeamManager);
      teamManagers.add(index, aTeamManager);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTeamManagerAt(aTeamManager, index);
    }
    return wasAdded;
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
      existingSystem.removeTeam(this);
    }
    system.addTeam(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCoachs()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addCoach(Coach aCoach)
  {
    boolean wasAdded = false;
    if (coachs.contains(aCoach)) { return false; }
    coachs.add(aCoach);
    if (aCoach.indexOfTeam(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aCoach.addTeam(this);
      if (!wasAdded)
      {
        coachs.remove(aCoach);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeCoach(Coach aCoach)
  {
    boolean wasRemoved = false;
    if (!coachs.contains(aCoach))
    {
      return wasRemoved;
    }

    int oldIndex = coachs.indexOf(aCoach);
    coachs.remove(oldIndex);
    if (aCoach.indexOfTeam(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aCoach.removeTeam(this);
      if (!wasRemoved)
      {
        coachs.add(oldIndex,aCoach);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addCoachAt(Coach aCoach, int index)
  {  
    boolean wasAdded = false;
    if(addCoach(aCoach))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCoachs()) { index = numberOfCoachs() - 1; }
      coachs.remove(aCoach);
      coachs.add(index, aCoach);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCoachAt(Coach aCoach, int index)
  {
    boolean wasAdded = false;
    if(coachs.contains(aCoach))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCoachs()) { index = numberOfCoachs() - 1; }
      coachs.remove(aCoach);
      coachs.add(index, aCoach);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCoachAt(aCoach, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfOwners()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addOwner(Owner aOwner)
  {
    boolean wasAdded = false;
    if (owners.contains(aOwner)) { return false; }
    owners.add(aOwner);
    if (aOwner.indexOfTeam(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aOwner.addTeam(this);
      if (!wasAdded)
      {
        owners.remove(aOwner);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeOwner(Owner aOwner)
  {
    boolean wasRemoved = false;
    if (!owners.contains(aOwner))
    {
      return wasRemoved;
    }

    int oldIndex = owners.indexOf(aOwner);
    owners.remove(oldIndex);
    if (aOwner.indexOfTeam(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aOwner.removeTeam(this);
      if (!wasRemoved)
      {
        owners.add(oldIndex,aOwner);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addOwnerAt(Owner aOwner, int index)
  {  
    boolean wasAdded = false;
    if(addOwner(aOwner))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOwners()) { index = numberOfOwners() - 1; }
      owners.remove(aOwner);
      owners.add(index, aOwner);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveOwnerAt(Owner aOwner, int index)
  {
    boolean wasAdded = false;
    if(owners.contains(aOwner))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOwners()) { index = numberOfOwners() - 1; }
      owners.remove(aOwner);
      owners.add(index, aOwner);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addOwnerAt(aOwner, index);
    }
    return wasAdded;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfPlayersValid()
  {
    boolean isValid = numberOfPlayers() >= minimumNumberOfPlayers() && numberOfPlayers() <= maximumNumberOfPlayers();
    return isValid;
  }
  /* Code from template association_RequiredNumberOfMethod */
  public static int requiredNumberOfPlayers()
  {
    return 11;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPlayers()
  {
    return 11;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfPlayers()
  {
    return 11;
  }
  /* Code from template association_AddMNToOnlyOne */
  public Player addPlayer(String aName, Date aBirthday, PositionEnum aPosition, Page aPage)
  {
    if (numberOfPlayers() >= maximumNumberOfPlayers())
    {
      return null;
    }
    else
    {
      return new Player(aName, aBirthday, aPosition, this, aPage);
    }
  }

  public boolean addPlayer(Player aPlayer)
  {
    boolean wasAdded = false;
    if (players.contains(aPlayer)) { return false; }
    if (numberOfPlayers() >= maximumNumberOfPlayers())
    {
      return wasAdded;
    }

    Team existingTeam = aPlayer.getTeam();
    boolean isNewTeam = existingTeam != null && !this.equals(existingTeam);

    if (isNewTeam && existingTeam.numberOfPlayers() <= minimumNumberOfPlayers())
    {
      return wasAdded;
    }

    if (isNewTeam)
    {
      aPlayer.setTeam(this);
    }
    else
    {
      players.add(aPlayer);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePlayer(Player aPlayer)
  {
    boolean wasRemoved = false;
    //Unable to remove aPlayer, as it must always have a team
    if (this.equals(aPlayer.getTeam()))
    {
      return wasRemoved;
    }

    //team already at minimum (11)
    if (numberOfPlayers() <= minimumNumberOfPlayers())
    {
      return wasRemoved;
    }
    players.remove(aPlayer);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_SetOneToMany */
  public boolean setLeague(League aLeague)
  {
    boolean wasSet = false;
    if (aLeague == null)
    {
      return wasSet;
    }

    League existingLeague = league;
    league = aLeague;
    if (existingLeague != null && !existingLeague.equals(aLeague))
    {
      existingLeague.removeTeam(this);
    }
    league.addTeam(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfMatchs()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addMatch(Match aMatch)
  {
    boolean wasAdded = false;
    if (matchs.contains(aMatch)) { return false; }
    matchs.add(aMatch);
    if (aMatch.indexOfTeam(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aMatch.addTeam(this);
      if (!wasAdded)
      {
        matchs.remove(aMatch);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeMatch(Match aMatch)
  {
    boolean wasRemoved = false;
    if (!matchs.contains(aMatch))
    {
      return wasRemoved;
    }

    int oldIndex = matchs.indexOf(aMatch);
    matchs.remove(oldIndex);
    if (aMatch.indexOfTeam(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aMatch.removeTeam(this);
      if (!wasRemoved)
      {
        matchs.add(oldIndex,aMatch);
      }
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
  /* Code from template association_SetOneToAtMostN */
  public boolean setStadium(Stadium aStadium)
  {
    boolean wasSet = false;
    //Must provide stadium to team
    if (aStadium == null)
    {
      return wasSet;
    }

    //stadium already at maximum (2)
    if (aStadium.numberOfTeams() >= Stadium.maximumNumberOfTeams())
    {
      return wasSet;
    }
    
    Stadium existingStadium = stadium;
    stadium = aStadium;
    if (existingStadium != null && !existingStadium.equals(aStadium))
    {
      boolean didRemove = existingStadium.removeTeam(this);
      if (!didRemove)
      {
        stadium = existingStadium;
        return wasSet;
      }
    }
    stadium.addTeam(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    ArrayList<TeamManager> copyOfTeamManagers = new ArrayList<TeamManager>(teamManagers);
    teamManagers.clear();
    for(TeamManager aTeamManager : copyOfTeamManagers)
    {
      aTeamManager.removeTeam(this);
    }
    System placeholderSystem = system;
    this.system = null;
    if(placeholderSystem != null)
    {
      placeholderSystem.removeTeam(this);
    }
    Page existingPage = page;
    page = null;
    if (existingPage != null)
    {
      existingPage.delete();
    }
    ArrayList<Coach> copyOfCoachs = new ArrayList<Coach>(coachs);
    coachs.clear();
    for(Coach aCoach : copyOfCoachs)
    {
      aCoach.removeTeam(this);
    }
    ArrayList<Owner> copyOfOwners = new ArrayList<Owner>(owners);
    owners.clear();
    for(Owner aOwner : copyOfOwners)
    {
      aOwner.removeTeam(this);
    }
    for(int i=players.size(); i > 0; i--)
    {
      Player aPlayer = players.get(i - 1);
      aPlayer.delete();
    }
    League placeholderLeague = league;
    this.league = null;
    if(placeholderLeague != null)
    {
      placeholderLeague.removeTeam(this);
    }
    ArrayList<Match> copyOfMatchs = new ArrayList<Match>(matchs);
    matchs.clear();
    for(Match aMatch : copyOfMatchs)
    {
      if (aMatch.numberOfTeams() <= Match.minimumNumberOfTeams())
      {
        aMatch.delete();
      }
      else
      {
        aMatch.removeTeam(this);
      }
    }
    Stadium placeholderStadium = stadium;
    this.stadium = null;
    if(placeholderStadium != null)
    {
      placeholderStadium.removeTeam(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "system = "+(getSystem()!=null?Integer.toHexString(System.identityHashCode(getSystem())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "page = "+(getPage()!=null?Integer.toHexString(System.identityHashCode(getPage())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "league = "+(getLeague()!=null?Integer.toHexString(System.identityHashCode(getLeague())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "stadium = "+(getStadium()!=null?Integer.toHexString(System.identityHashCode(getStadium())):"null");
  }
}