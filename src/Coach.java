/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4811.445d1d99b modeling language!*/


import java.util.*;

// line 50 "model.ump"
// line 213 "model.ump"
public class Coach extends Role
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Coach Attributes
  private String training;
  private String teamRole;

  //Coach Associations
  private List<Team> teams;
  private Page page;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Coach(String aName, String aTraining, String aTeamRole, Page aPage)
  {
    super(aName);
    training = aTraining;
    teamRole = aTeamRole;
    teams = new ArrayList<Team>();
    if (aPage == null || aPage.getCoach() != null)
    {
      throw new RuntimeException("Unable to create Coach due to aPage. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    page = aPage;
  }

  public Coach(String aName, String aTraining, String aTeamRole, System aSystemForPage, Team aTeamForPage, Player aPlayerForPage)
  {
    super(aName);
    training = aTraining;
    teamRole = aTeamRole;
    teams = new ArrayList<Team>();
    page = new Page(aSystemForPage, aTeamForPage, aPlayerForPage, this);
  }

  //------------------------
  // INTERFACE
  //------------------------

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
  /* Code from template association_GetOne_relatedSpecialization */
  public Page getPage_OnePage()
  {
    return (Page)page;
  } 
  /* Code from template association_GetOne */
  public Page getPage()
  {
    return page;
  }
  /* Code from template association_GetOne_clear */
  protected void clear_page()
  {
    page = null;
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
  /* Code from template association_set_specialization_reqCommonCode */
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
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "training" + ":" + getTraining()+ "," +
            "teamRole" + ":" + getTeamRole()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "page = "+(getPage()!=null?Integer.toHexString(System.identityHashCode(getPage())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "page = "+(getPage()!=null?Integer.toHexString(System.identityHashCode(getPage())):"null");
  }
}