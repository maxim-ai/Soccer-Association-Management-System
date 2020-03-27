/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4811.445d1d99b modeling language!*/


import java.util.*;

// line 59 "model.ump"
// line 220 "model.ump"
public class TeamManager extends Role
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TeamManager Associations
  private List<Team> teams;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TeamManager(String aName)
  {
    super(aName);
    teams = new ArrayList<Team>();
  }

  //------------------------
  // INTERFACE
  //------------------------
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTeams()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addTeam(Team aTeam)
  {
    boolean wasAdded = false;
    if (teams.contains(aTeam)) { return false; }
    teams.add(aTeam);
    if (aTeam.indexOfTeamManager(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aTeam.addTeamManager(this);
      if (!wasAdded)
      {
        teams.remove(aTeam);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeTeam(Team aTeam)
  {
    boolean wasRemoved = false;
    if (!teams.contains(aTeam))
    {
      return wasRemoved;
    }

    int oldIndex = teams.indexOf(aTeam);
    teams.remove(oldIndex);
    if (aTeam.indexOfTeamManager(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aTeam.removeTeamManager(this);
      if (!wasRemoved)
      {
        teams.add(oldIndex,aTeam);
      }
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

  public void delete()
  {
    ArrayList<Team> copyOfTeams = new ArrayList<Team>(teams);
    teams.clear();
    for(Team aTeam : copyOfTeams)
    {
      aTeam.removeTeamManager(this);
    }
    super.delete();
  }

}