
import java.util.*;

public class TeamManager extends Role
{


  enum PermissionEnum{
    manageName,
    manageManagers,
    managePage,
    manageCoaches,
    managePlayers,
    manageLeague,
    manageMatches,
    manageStadium
  }

  private Team team;
  private Owner appointedBy;

  public TeamManager(String aName, Team team, Owner appointer) {
    super(aName);
    this.team = team;
    appointedBy = appointer;
  }

  public Team getTeam() {
    return team;
  }

  public void setTeam(Team team) {
    this.team = team;
    if(team==null) return;
    if(!team.getTeamManagers().contains(this))
      team.addTeamManager(this);
  }

  public Owner getAppointer() {
    return appointedBy;
  }

  /**
   * allows the team manager to change the name of the team
   */
  public boolean changeTeamName(String name)
  {
    if(team==null)
      return false;
    if(!appointedBy.hasPermission(this,PermissionEnum.manageName))
      return false;

    Logger.getInstance().writeNewLine(this.getName()+" changed the name of the team "+team.getName() +" to be:" + name);

    return team.setName(name);
  }

  /**
   * allows the team manager to add a team manager
   */
  public boolean addTeamManager(TeamManager aTeamManager)
  {
    if(team==null)
      return false;
    if(!appointedBy.hasPermission(this,PermissionEnum.manageManagers))
      return false;

    Logger.getInstance().writeNewLine(this.getName()+" set "+aTeamManager.getName() +" as a team manager for the team:" + team.getName());

    return team.addTeamManager(aTeamManager);
  }

  /**
   * allows the team manager to remove a team manager
   */
  public boolean removeTeamManager(TeamManager aTeamManager)
  {
    if(team==null)
      return false;
    if(!appointedBy.hasPermission(this,PermissionEnum.manageManagers))
      return false;

    Logger.getInstance().writeNewLine(this.getName()+" removed the team manager"+(aTeamManager.getName() +" from the team:" + team.getName()));

    return team.removeTeamManager(aTeamManager);
  }

  /**
   * allows the team manager to add a coach
   */
  public boolean addCoach(Coach aCoach)
  {
    if(team==null)
      return false;
    if(!appointedBy.hasPermission(this,PermissionEnum.manageCoaches))
      return false;

    Logger.getInstance().writeNewLine(this.getName()+" set "+(aCoach.getName() +" as a coach for the team:" + team.getName()));

    return team.addCoach(aCoach);
  }

  /**
   * allows the team manager to remove a coach
   */
  public boolean removeCoach(Coach aCoach)
  {
    if(team==null)
      return false;
    if(!appointedBy.hasPermission(this,PermissionEnum.manageCoaches))
      return false;

    Logger.getInstance().writeNewLine(this.getName()+" removed the Coach"+(aCoach.getName() +" from the team:" + team.getName()));

    return team.removeCoach(aCoach);
  }

  /**
   * allows the team manager to add a player
   */
  public boolean addPlayer(Player aPlayer)
  {
    if(team==null)
      return false;
    if(!appointedBy.hasPermission(this,PermissionEnum.managePlayers))
      return false;

    Logger.getInstance().writeNewLine(this.getName()+" set "+aPlayer.getName() +" as a player for the team:" + team.getName());

    return team.addPlayer(aPlayer);
  }

  /**
   * allows the team manager to remove a player
   */
  public boolean removePlayer(Player aPlayer)
  {
    if(team==null)
      return false;
    if(!appointedBy.hasPermission(this,PermissionEnum.managePlayers))
      return false;

    Logger.getInstance().writeNewLine(this.getName()+" removed the Player"+(aPlayer.getName() +" from the team:" + team.getName()));

    return team.removePlayer(aPlayer);
  }

  /**
   * allows the team manager to change the league
   */
  public boolean setLeague(League aLeague)
  {
    if(team==null)
      return false;
    if(!appointedBy.hasPermission(this,PermissionEnum.manageLeague))
      return false;
    Logger.getInstance().writeNewLine(this.getName()+" set the League of the team "+team.getName()+" to be "+aLeague.getName());

    return team.setLeague(aLeague);
  }

  /**
   * allows the team manager to remove a match
   */
  public boolean removeMatch(Match aMatch)
  {
    if(team==null)
    return false;
    if(!appointedBy.hasPermission(this,PermissionEnum.manageMatches))
      return false;

    Logger.getInstance().writeNewLine(this.getName()+"removed a match on "+aMatch.getDate()+" for the team "+team.getName());

    return team.removeMatch(aMatch);
  }


  /**
   * allows the team manager to change the stadium
   */
  public boolean setStadium(Stadium aStadium)
  {
    if(team==null)
      return false;
    if(!appointedBy.hasPermission(this,PermissionEnum.manageStadium))
      return false;

    Logger.getInstance().writeNewLine(this.getName()+" set "+(aStadium.getName() +" as the stadium for the team:" + team.getName()));

    return team.setStadium(aStadium);
  }

  public void delete()
  {
      team.removeTeamManager(this);
      setTeam(null);
  }

  public void ShowTeamManager(){
    System.out.println("Name:");
    System.out.println(this.getName());
    System.out.println("Team managed:");
    System.out.println(this.getTeam().getName());

  }

}