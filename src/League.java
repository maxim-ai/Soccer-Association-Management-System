
import java.util.*;

public class League
{

  private String name;
  private List<Team> teams;
  private List<Season> seasons;
  private List<Referee> referees;
  private List<System> systems;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public League(String aName)
  {
    name = aName;
    teams = new ArrayList<Team>();
    seasons = new ArrayList<Season>();
    referees = new ArrayList<Referee>();
    systems = new ArrayList<System>();
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
  public Season getSeason(int index)
  {
    Season aSeason = seasons.get(index);
    return aSeason;
  }

  public List<Season> getSeasons()
  {
    List<Season> newSeasons = Collections.unmodifiableList(seasons);
    return newSeasons;
  }

  public int numberOfSeasons()
  {
    int number = seasons.size();
    return number;
  }

  public boolean hasSeasons()
  {
    boolean has = seasons.size() > 0;
    return has;
  }

  public int indexOfSeason(Season aSeason)
  {
    int index = seasons.indexOf(aSeason);
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
  /* Code from template association_GetMany */
  public System getSystem(int index)
  {
    System aSystem = systems.get(index);
    return aSystem;
  }

  public List<System> getSystems()
  {
    List<System> newSystems = Collections.unmodifiableList(systems);
    return newSystems;
  }

  public int numberOfSystems()
  {
    int number = systems.size();
    return number;
  }

  public boolean hasSystems()
  {
    boolean has = systems.size() > 0;
    return has;
  }

  public int indexOfSystem(System aSystem)
  {
    int index = systems.indexOf(aSystem);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTeams()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Team addTeam(String aName, System aSystem, Page aPage, Stadium aStadium)
  {
    return new Team(aName, aSystem, aPage, this, aStadium);
  }

  public boolean addTeam(Team aTeam)
  {
    boolean wasAdded = false;
    if (teams.contains(aTeam)) { return false; }
    League existingLeague = aTeam.getLeague();
    boolean isNewLeague = existingLeague != null && !this.equals(existingLeague);
    if (isNewLeague)
    {
      aTeam.setLeague(this);
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
    //Unable to remove aTeam, as it must always have a league
    if (!this.equals(aTeam.getLeague()))
    {
      teams.remove(aTeam);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addTeamAt(Team aTeam, int index)
  {  
    boolean wasAdded = false;
    if(addTeam(aTeam))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTeams()) { index = numberOfTeams() - 1; }
      teams.remove(aTeam);
      teams.add(index, aTeam);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTeamAt(Team aTeam, int index)
  {
    boolean wasAdded = false;
    if(teams.contains(aTeam))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTeams()) { index = numberOfTeams() - 1; }
      teams.remove(aTeam);
      teams.add(index, aTeam);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTeamAt(aTeam, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfSeasons()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addSeason(Season aSeason)
  {
    boolean wasAdded = false;
    if (seasons.contains(aSeason)) { return false; }
    seasons.add(aSeason);
    if (aSeason.indexOfLeague(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aSeason.addLeague(this);
      if (!wasAdded)
      {
        seasons.remove(aSeason);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeSeason(Season aSeason)
  {
    boolean wasRemoved = false;
    if (!seasons.contains(aSeason))
    {
      return wasRemoved;
    }

    int oldIndex = seasons.indexOf(aSeason);
    seasons.remove(oldIndex);
    if (aSeason.indexOfLeague(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aSeason.removeLeague(this);
      if (!wasRemoved)
      {
        seasons.add(oldIndex,aSeason);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addSeasonAt(Season aSeason, int index)
  {  
    boolean wasAdded = false;
    if(addSeason(aSeason))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSeasons()) { index = numberOfSeasons() - 1; }
      seasons.remove(aSeason);
      seasons.add(index, aSeason);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveSeasonAt(Season aSeason, int index)
  {
    boolean wasAdded = false;
    if(seasons.contains(aSeason))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSeasons()) { index = numberOfSeasons() - 1; }
      seasons.remove(aSeason);
      seasons.add(index, aSeason);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addSeasonAt(aSeason, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfReferees()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addReferee(Referee aReferee)
  {
    boolean wasAdded = false;
    if (referees.contains(aReferee)) { return false; }
    referees.add(aReferee);
    if (aReferee.indexOfLeague(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aReferee.addLeague(this);
      if (!wasAdded)
      {
        referees.remove(aReferee);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeReferee(Referee aReferee)
  {
    boolean wasRemoved = false;
    if (!referees.contains(aReferee))
    {
      return wasRemoved;
    }

    int oldIndex = referees.indexOf(aReferee);
    referees.remove(oldIndex);
    if (aReferee.indexOfLeague(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aReferee.removeLeague(this);
      if (!wasRemoved)
      {
        referees.add(oldIndex,aReferee);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addRefereeAt(Referee aReferee, int index)
  {  
    boolean wasAdded = false;
    if(addReferee(aReferee))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfReferees()) { index = numberOfReferees() - 1; }
      referees.remove(aReferee);
      referees.add(index, aReferee);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveRefereeAt(Referee aReferee, int index)
  {
    boolean wasAdded = false;
    if(referees.contains(aReferee))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfReferees()) { index = numberOfReferees() - 1; }
      referees.remove(aReferee);
      referees.add(index, aReferee);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addRefereeAt(aReferee, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfSystems()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addSystem(System aSystem)
  {
    boolean wasAdded = false;
    if (systems.contains(aSystem)) { return false; }
    systems.add(aSystem);
    if (aSystem.indexOfLeague(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aSystem.addLeague(this);
      if (!wasAdded)
      {
        systems.remove(aSystem);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeSystem(System aSystem)
  {
    boolean wasRemoved = false;
    if (!systems.contains(aSystem))
    {
      return wasRemoved;
    }

    int oldIndex = systems.indexOf(aSystem);
    systems.remove(oldIndex);
    if (aSystem.indexOfLeague(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aSystem.removeLeague(this);
      if (!wasRemoved)
      {
        systems.add(oldIndex,aSystem);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addSystemAt(System aSystem, int index)
  {  
    boolean wasAdded = false;
    if(addSystem(aSystem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSystems()) { index = numberOfSystems() - 1; }
      systems.remove(aSystem);
      systems.add(index, aSystem);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveSystemAt(System aSystem, int index)
  {
    boolean wasAdded = false;
    if(systems.contains(aSystem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSystems()) { index = numberOfSystems() - 1; }
      systems.remove(aSystem);
      systems.add(index, aSystem);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addSystemAt(aSystem, index);
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
    ArrayList<Season> copyOfSeasons = new ArrayList<Season>(seasons);
    seasons.clear();
    for(Season aSeason : copyOfSeasons)
    {
      aSeason.removeLeague(this);
    }
    ArrayList<Referee> copyOfReferees = new ArrayList<Referee>(referees);
    referees.clear();
    for(Referee aReferee : copyOfReferees)
    {
      aReferee.removeLeague(this);
    }
    ArrayList<System> copyOfSystems = new ArrayList<System>(systems);
    systems.clear();
    for(System aSystem : copyOfSystems)
    {
      aSystem.removeLeague(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "]";
  }
}