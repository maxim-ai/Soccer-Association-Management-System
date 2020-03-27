/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4811.445d1d99b modeling language!*/


import java.util.*;
import java.sql.Date;

// line 26 "model.ump"
// line 188 "model.ump"
public class Page
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Page Associations
  private System system;
  private Team team;
  private Player player;
  private Coach coach;
  private List<Fan> fans;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Page(System aSystem, Team aTeam, Player aPlayer, Coach aCoach)
  {
    boolean didAddSystem = setSystem(aSystem);
    if (!didAddSystem)
    {
      throw new RuntimeException("Unable to create page due to system. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (aTeam == null || aTeam.getPage() != null)
    {
      throw new RuntimeException("Unable to create Page due to aTeam. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    team = aTeam;
    if (aPlayer == null || aPlayer.getPage() != null)
    {
      throw new RuntimeException("Unable to create Page due to aPlayer. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    player = aPlayer;
    if (aCoach == null || aCoach.getPage() != null)
    {
      throw new RuntimeException("Unable to create Page due to aCoach. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    coach = aCoach;
    fans = new ArrayList<Fan>();
  }

  public Page(System aSystem, String aNameForTeam, System aSystemForTeam, League aLeagueForTeam, Stadium aStadiumForTeam, String aNameForPlayer, Date aBirthdayForPlayer, PositionEnum aPositionForPlayer, Team aTeamForPlayer, String aNameForCoach, String aTrainingForCoach, String aTeamRoleForCoach)
  {
    boolean didAddSystem = setSystem(aSystem);
    if (!didAddSystem)
    {
      throw new RuntimeException("Unable to create page due to system. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    team = new Team(aNameForTeam, aSystemForTeam, this, aLeagueForTeam, aStadiumForTeam);
    player = new Player(aNameForPlayer, aBirthdayForPlayer, aPositionForPlayer, aTeamForPlayer, this);
    coach = new Coach(aNameForCoach, aTrainingForCoach, aTeamRoleForCoach, this);
    fans = new ArrayList<Fan>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public System getSystem()
  {
    return system;
  }
  /* Code from template association_GetOne */
  public Team getTeam()
  {
    return team;
  }
  /* Code from template association_GetOne */
  public Player getPlayer()
  {
    return player;
  }
  /* Code from template association_GetOne */
  public Coach getCoach()
  {
    return coach;
  }
  /* Code from template association_GetOne_clear */
  protected void clear_coach()
  {
    coach = null;
  }
  /* Code from template association_GetOne_relatedSpecialization */
  public Coach getCoach_OneCoach()
  {
    return (Coach)coach;
  } 
  /* Code from template association_GetMany */
  public Fan getFan(int index)
  {
    Fan aFan = fans.get(index);
    return aFan;
  }

  public List<Fan> getFans()
  {
    List<Fan> newFans = Collections.unmodifiableList(fans);
    return newFans;
  }

  public int numberOfFans()
  {
    int number = fans.size();
    return number;
  }

  public boolean hasFans()
  {
    boolean has = fans.size() > 0;
    return has;
  }

  public int indexOfFan(Fan aFan)
  {
    int index = fans.indexOf(aFan);
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
      existingSystem.removePage(this);
    }
    system.addPage(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_set_specialization_reqCommonCode */  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfFans()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addFan(Fan aFan)
  {
    boolean wasAdded = false;
    if (fans.contains(aFan)) { return false; }
    fans.add(aFan);
    if (aFan.indexOfPage(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aFan.addPage(this);
      if (!wasAdded)
      {
        fans.remove(aFan);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeFan(Fan aFan)
  {
    boolean wasRemoved = false;
    if (!fans.contains(aFan))
    {
      return wasRemoved;
    }

    int oldIndex = fans.indexOf(aFan);
    fans.remove(oldIndex);
    if (aFan.indexOfPage(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aFan.removePage(this);
      if (!wasRemoved)
      {
        fans.add(oldIndex,aFan);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addFanAt(Fan aFan, int index)
  {  
    boolean wasAdded = false;
    if(addFan(aFan))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfFans()) { index = numberOfFans() - 1; }
      fans.remove(aFan);
      fans.add(index, aFan);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveFanAt(Fan aFan, int index)
  {
    boolean wasAdded = false;
    if(fans.contains(aFan))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfFans()) { index = numberOfFans() - 1; }
      fans.remove(aFan);
      fans.add(index, aFan);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addFanAt(aFan, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    System placeholderSystem = system;
    this.system = null;
    if(placeholderSystem != null)
    {
      placeholderSystem.removePage(this);
    }
    Team existingTeam = team;
    team = null;
    if (existingTeam != null)
    {
      existingTeam.delete();
    }
    Player existingPlayer = player;
    player = null;
    if (existingPlayer != null)
    {
      existingPlayer.delete();
    }
    Coach existingCoach = coach;
    coach = null;
    if (existingCoach != null)
    {
      existingCoach.delete();
    }
    ArrayList<Fan> copyOfFans = new ArrayList<Fan>(fans);
    fans.clear();
    for(Fan aFan : copyOfFans)
    {
      aFan.removePage(this);
    }
  }

}