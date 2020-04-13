
import java.io.IOException;
import java.util.Date;

public class Player extends Role implements Pageable
{

  private Date birthday;
  private PositionEnum position;
  private Team team;
  private Page page;

  public Player(String aName, Date aBirthday, PositionEnum aPosition, Team aTeam)
  {
    super(aName);
    birthday = aBirthday;
    position = aPosition;
    if (aTeam != null) {
      setTeam(aTeam);
    }
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

  public void setPage(Page page) {
    this.page = page;
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
    try {
      Logger.getInstance().writeNewLine("Player "+super.getName()+"update details to "+birthday.toString()+","+position.name()+","+team.getName());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void ShowPlayer() {
    System.out.println("Name:");
    System.out.println(this.getName());
    System.out.println();
    System.out.println("Age:");
    int age=0;
    for(Account account:DataManager.getAccounts()){
      for(Role role:account.getRoles()){
        if(role.equals(this))
          age=account.getAge();
      }
    }
    System.out.println(age);
    System.out.println();
    System.out.println("Position:");
    System.out.println(this.getPosition());
    System.out.println();
    System.out.println("Team:");
    System.out.println(this.getTeam().getName());
  }

  public void pageUpdated(){
    page.notifyTrackingFans(new Alert(getName()+" page updated"));
  }

}