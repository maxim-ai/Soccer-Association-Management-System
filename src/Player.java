/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4811.445d1d99b modeling language!*/


import java.sql.Date;
import java.util.*;

// line 70 "model.ump"
// line 231 "model.ump"
public class Player extends Role
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Player Attributes
  private Date birthday;
  private PositionEnum position;

  //Player Associations
  private Team team;
  private Page page;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Player(String aName, Date aBirthday, PositionEnum aPosition, Team aTeam, Page aPage)
  {
    super(aName);
    birthday = aBirthday;
    position = aPosition;
    boolean didAddTeam = setTeam(aTeam);
    if (!didAddTeam)
    {
      throw new RuntimeException("Unable to create player due to team. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (aPage == null || aPage.getPlayer() != null)
    {
      throw new RuntimeException("Unable to create Player due to aPage. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    page = aPage;
  }

  public Player(String aName, Date aBirthday, PositionEnum aPosition, Team aTeam, System aSystemForPage, Team aTeamForPage, Coach aCoachForPage)
  {
    super(aName);
    birthday = aBirthday;
    position = aPosition;
    boolean didAddTeam = setTeam(aTeam);
    if (!didAddTeam)
    {
      throw new RuntimeException("Unable to create player due to team. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    page = new Page(aSystemForPage, aTeamForPage, this, aCoachForPage);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setBirthday(Date aBirthday)
  {
    boolean wasSet = false;
    birthday = aBirthday;
    wasSet = true;
    return wasSet;
  }

  public boolean setPosition(PositionEnum aPosition)
  {
    boolean wasSet = false;
    position = aPosition;
    wasSet = true;
    return wasSet;
  }

  public Date getBirthday()
  {
    return birthday;
  }

  public PositionEnum getPosition()
  {
    return position;
  }
  /* Code from template association_GetOne */
  public Team getTeam()
  {
    return team;
  }
  /* Code from template association_GetOne */
  public Page getPage()
  {
    return page;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setTeam(Team aTeam)
  {
    boolean wasSet = false;
    //Must provide team to player
    if (aTeam == null)
    {
      return wasSet;
    }

    //team already at maximum (11)
    if (aTeam.numberOfPlayers() >= Team.maximumNumberOfPlayers())
    {
      return wasSet;
    }
    
    Team existingTeam = team;
    team = aTeam;
    if (existingTeam != null && !existingTeam.equals(aTeam))
    {
      boolean didRemove = existingTeam.removePlayer(this);
      if (!didRemove)
      {
        team = existingTeam;
        return wasSet;
      }
    }
    team.addPlayer(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Team placeholderTeam = team;
    this.team = null;
    if(placeholderTeam != null)
    {
      placeholderTeam.removePlayer(this);
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
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "birthday" + "=" + (getBirthday() != null ? !getBirthday().equals(this)  ? getBirthday().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "position" + "=" + (getPosition() != null ? !getPosition().equals(this)  ? getPosition().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "team = "+(getTeam()!=null?Integer.toHexString(System.identityHashCode(getTeam())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "page = "+(getPage()!=null?Integer.toHexString(System.identityHashCode(getPage())):"null");
  }
}