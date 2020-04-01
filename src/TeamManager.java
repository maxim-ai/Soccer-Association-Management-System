
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
  }

  public Owner getAppointer() {
    return appointedBy;
  }

  public void setAppointer(Owner appointedBy) {
    this.appointedBy = appointedBy;
  }

  /**
   * allows the team manager to change the name of the team
   */
  public boolean changeTeamName(String name)
  {
    if(team==null || name==null)
      return false;
    if(appointedBy.hasPermission(this,PermissionEnum.manageName))
      return false;
    return team.setName(name);
  }

  /**
   * allows the team manager to add a team manager
   */
  public boolean addTeamManager(TeamManager aTeamManager)
  {
    if(team==null || aTeamManager==null)
      return false;
    if(appointedBy.hasPermission(this,PermissionEnum.manageManagers))
      return false;
    return team.addTeamManager(aTeamManager);
  }

  /**
   * allows the team manager to remove a team manager
   */
  public boolean removeTeamManager(TeamManager aTeamManager)
  {
    if(team==null || aTeamManager==null)
      return false;
    if(appointedBy.hasPermission(this,PermissionEnum.manageManagers))
      return false;
    return team.removeTeamManager(aTeamManager);
  }

  /**
   * allows the team manager to add a coach
   */
  public boolean addCoach(Coach aCoach)
  {
    if(team==null || aCoach==null)
      return false;
    if(appointedBy.hasPermission(this,PermissionEnum.manageCoaches))
      return false;
    return team.addCoach(aCoach);
  }

  /**
   * allows the team manager to remove a coach
   */
  public boolean removeCoach(Coach aCoach)
  {
    if(team==null || aCoach==null)
      return false;
    if(appointedBy.hasPermission(this,PermissionEnum.manageCoaches))
      return false;
    return team.removeCoach(aCoach);
  }

  /**
   * allows the team manager to add a player
   */
  public boolean addPlayer(Player aPlayer)
  {
    if(team==null || aPlayer==null)
      return false;
    if(appointedBy.hasPermission(this,PermissionEnum.managePlayers))
      return false;
    return team.addPlayer(aPlayer);
  }

  /**
   * allows the team manager to remove a player
   */
  public boolean removePlayer(Player aPlayer)
  {
    if(team==null || aPlayer==null)
      return false;
    if(appointedBy.hasPermission(this,PermissionEnum.managePlayers))
      return false;
    return team.removePlayer(aPlayer);
  }

  /**
   * allows the team manager to change the league
   */
  public boolean setLeague(League aLeague)
  {
    if(team==null || aLeague==null)
      return false;
    if(appointedBy.hasPermission(this,PermissionEnum.manageLeague))
      return false;
    return team.setLeague(aLeague);
  }

  /**
   * allows the team manager to add a match
   */
  public boolean addMatch(Match aMatch, String homeOrAway)
  {
    if(team == null || !team.isActive() || aMatch==null || homeOrAway==null)
      return false;
    if(appointedBy.hasPermission(this,PermissionEnum.manageMatches))
      return false;
    return team.addMatch(aMatch,homeOrAway);
  }

  /**
   * allows the team manager to remove a match
   */
  public boolean removeMatch(Match aMatch)
  {
    if(team == null || !team.isActive() || aMatch==null)
    return false;
    if(appointedBy.hasPermission(this,PermissionEnum.manageMatches))
      return false;
    return team.removeMatch(aMatch);
  }


  /**
   * allows the team manager to change the stadium
   */
  public boolean setStadium(Stadium aStadium)
  {
    if(team == null || !team.isActive() || aStadium==null)
      return false;
    if(appointedBy.hasPermission(this,PermissionEnum.manageStadium))
      return false;
    return team.setStadium(aStadium);
  }

}