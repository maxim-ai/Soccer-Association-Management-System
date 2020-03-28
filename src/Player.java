
import java.sql.Date;

public class Player extends Role implements Pageable
{

  private Date birthday;
  private PositionEnum position;
  private Team team;
  private Page page;

  public Player(String aName, Date aBirthday, PositionEnum aPosition, Team aTeam, Page aPage)
  {
    super(aName);
    birthday = aBirthday;
    position = aPosition;
    if (aTeam != null) {
      setTeam(aTeam);
    }
    page = aPage;
  }

  public boolean setBirthday(Date aBirthday)
  {
    boolean wasSet = true;
    birthday = aBirthday;
    wasSet = true;
    return wasSet;
  }

  public boolean setPosition(PositionEnum aPosition)
  {
    boolean wasSet = true;
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

  public Team getTeam()
  {
    return team;
  }

  public Page getPage()
  {
    return page;
  }

  public boolean setTeam(Team aTeam)
  {
    boolean wasSet = true;
    //Must provide team to player
    if (aTeam == null)
    {
      team=null;
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
  }


  public String toString()
  {
    return super.toString() + "["+ "]" +
            "  " + "birthday" + "=" + (getBirthday())+
            "  " + "position" + "=" + (getPosition()) +
            "  " + "team = "+(getTeam())+
            "  " + "page = "+(getPage());
  }

  @Override
  public void removePage() {
    page=null;
  }
}