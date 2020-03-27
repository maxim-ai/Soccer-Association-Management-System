
import java.util.*;
import java.sql.Date;


public class Team
{

  private String name;
  private List<TeamManager> teamManagers;
  private Page page;
  private List<Coach> coachs;
  private List<Owner> owners;
  private List<Player> players;//11 players
  private League league;
  private List<Match> matchs;
  private Stadium stadium;


  public Team(String aName,Page aPage,League aLeague,Stadium aStadium)
  {
    if (aPage != null) {
      page = aPage;
    }
    if(aLeague != null) {
      setLeague(aLeague);
    }
    if(aStadium != null){
      setStadium(aStadium);
    }
    name = aName;
    teamManagers = new ArrayList<TeamManager>();
    coachs = new ArrayList<Coach>();
    owners = new ArrayList<Owner>();
    players = new ArrayList<Player>();
    matchs = new ArrayList<Match>();

  }

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

  public Page getPage()
  {
    return page;
  }

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

  public League getLeague()
  {
    return league;
  }

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

  public Stadium getStadium()
  {
    return stadium;
  }

  public static int minimumNumberOfTeamManagers()
  {
    return 1;
  }

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

  public static int minimumNumberOfCoachs()
  {
    return 0;
  }

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

  public static int minimumNumberOfOwners()
  {
    return 1;
  }

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

  public boolean isNumberOfPlayersValid()
  {
    boolean isValid = numberOfPlayers() >= minimumNumberOfPlayers() && numberOfPlayers() <= maximumNumberOfPlayers();
    return isValid;
  }
  public static int requiredNumberOfPlayers()
  {
    return 11;
  }
  public static int minimumNumberOfPlayers()
  {
    return 11;
  }
  public static int maximumNumberOfPlayers() { return 11; }

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
  public static int minimumNumberOfMatchs() { return 0;}

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

  public boolean setStadium(Stadium aStadium)
  {
    boolean wasSet = false;
    if (aStadium == null)
    {
      stadium=null;
      wasSet=true;
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
    //this.league = null;
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
    //this.stadium = null;
    if(placeholderStadium != null)
    {
      placeholderStadium.removeTeam(this);
    }
  }

  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "]" +
            "  " + "page = "+(getPage()) +
            "  " + "league = "+(getLeague()) +
            "  " + "stadium = "+(getStadium());
  }
}