
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
    setPage(aPage);
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
    pageUpdated();
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

    
    Team existingTeam = team;
    if (existingTeam != null && !existingTeam.equals(aTeam))
    {
      boolean didRemove = existingTeam.removePlayer(this);
      if (!didRemove)
      {
        return wasSet;
      }
    }

      team = aTeam;

    team.addPlayer(this);
    wasSet = true;
    pageUpdated();
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

  /*
  UC-5.1 update player details
   */
  public void updateDetails(Date birthday, PositionEnum position,Team team)
  {
    this.birthday=birthday;
    this.position=position;
    this.team=team;
    pageUpdated();
  }

  public void ShowPlayer() {
    System.out.println("Name:");
    System.out.println(this.getName());
    System.out.println();
    System.out.println("Age:");
    int age=2020-this.getBirthday().getYear();
    System.out.println(age);
    System.out.println();
    System.out.println("Position:");
    System.out.println(this.getPosition());
    System.out.println();
    System.out.println("Team:");
    System.out.println(this.getTeam().getName());
  }

  public void pageUpdated()
  {
    if(page!=null)
      page.notifyTrackingFans(new Alert(getName()+" page updated"));
  }

  @Override
  public void setPage(Page page)
  {
    this.page = page;
    if(page==null) return;
    if(!page.getType().equals(this))
      page.setType(this);
  }
}