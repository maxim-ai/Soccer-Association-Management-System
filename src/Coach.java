
import java.util.*;

public class Coach extends Role
{


  private String training;
  private String teamRole;
  private List<Team> teams;
  private Page page;

  public Coach(String aName, String aTraining, String aTeamRole, Page aPage)
  {
    super(aName);
    training = aTraining;
    teamRole = aTeamRole;
    teams = new ArrayList<Team>();
    page = aPage;
  }

  public boolean setTraining(String aTraining)
  {
    boolean wasSet = false;
    training = aTraining;
    wasSet = true;
    return wasSet;
  }

  public boolean setTeamRole(String aTeamRole)
  {
    boolean wasSet = false;
    teamRole = aTeamRole;
    wasSet = true;
    return wasSet;
  }

  public String getTraining()
  {
    return training;
  }

  public String getTeamRole()
  {
    return teamRole;
  }

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

  public Page getPage()
  {
    return page;
  }
  protected void clear_page()
  {
    page = null;
  }
  public static int minimumNumberOfTeams()
  {
    return 0;
  }
  public boolean addTeam(Team aTeam)
  {
    boolean wasAdded = false;
    if (teams.contains(aTeam)) { return false; }
    teams.add(aTeam);
    if (aTeam.indexOfCoach(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aTeam.addCoach(this);
      if (!wasAdded)
      {
        teams.remove(aTeam);
      }
    }
    return wasAdded;
  }

  public boolean removeTeam(Team aTeam)
  {
    boolean wasRemoved = false;
    if (!teams.contains(aTeam))
    {
      return wasRemoved;
    }

    int oldIndex = teams.indexOf(aTeam);
    teams.remove(oldIndex);
    if (aTeam.indexOfCoach(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aTeam.removeCoach(this);
      if (!wasRemoved)
      {
        teams.add(oldIndex,aTeam);
      }
    }
    return wasRemoved;
  }

  public void delete()
  {
    ArrayList<Team> copyOfTeams = new ArrayList<Team>(teams);
    teams.clear();
    for(Team aTeam : copyOfTeams)
    {
      aTeam.removeCoach(this);
    }
    Page existingPage = page;
    page = null;
    if (existingPage != null)
    {
      existingPage.delete();
    }
  }

  public String toString()
  {
    return super.toString() + "["+
            "training" + ":" + getTraining()+ "," +
            "teamRole" + ":" + getTeamRole() +
            "  " + "page = "+(getPage())+
            "  " + "page = "+(getPage());
  }
}