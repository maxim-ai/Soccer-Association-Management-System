public class TeamManagerController
{
    TeamManager teamManager;

    public TeamManagerController(TeamManager teamManager) {
        this.teamManager = teamManager;
    }

    public Team getTeam() {
        return teamManager.getTeam();
    }

    public void setTeam(Team team) {
        teamManager.setTeam(team);
    }

    public Owner getAppointer() {
        return teamManager.getAppointer();
    }


    /**
     * allows the team manager to change the name of the team
     */
    public boolean changeTeamName(String name)
    {
        if(name==null)
            return false;
        return teamManager.changeTeamName(name);
    }

    /**
     * allows the team manager to add a team manager
     */
    public boolean addTeamManager(TeamManager aTeamManager)
    {
        if(aTeamManager==null)
            return false;
        return teamManager.addTeamManager(aTeamManager);
    }

    /**
     * allows the team manager to remove a team manager
     */
    public boolean removeTeamManager(TeamManager aTeamManager)
    {
        if(aTeamManager==null)
            return false;
        return teamManager.removeTeamManager(aTeamManager);
    }

    /**
     * allows the team manager to add a coach
     */
    public boolean addCoach(Coach aCoach)
    {
        if(aCoach==null)
            return false;
        return teamManager.addCoach(aCoach);
    }

    /**
     * allows the team manager to remove a coach
     */
    public boolean removeCoach(Coach aCoach)
    {
        if(aCoach==null)
            return false;
        return teamManager.removeCoach(aCoach);
    }

    /**
     * allows the team manager to add a player
     */
    public boolean addPlayer(Player aPlayer)
    {
        if(aPlayer==null)
            return false;
        return teamManager.addPlayer(aPlayer);
    }

    /**
     * allows the team manager to remove a player
     */
    public boolean removePlayer(Player aPlayer)
    {
        if(aPlayer==null)
            return false;
        return teamManager.removePlayer(aPlayer);
    }

    /**
     * allows the team manager to change the league
     */
    public boolean setLeague(League aLeague)
    {
        if(aLeague==null)
            return false;
        return teamManager.setLeague(aLeague);
    }

    /**
     * allows the team manager to remove a match
     */
    public boolean removeMatch(Match aMatch)
    {
        if(aMatch==null)
            return false;
        return teamManager.removeMatch(aMatch);
    }


    /**
     * allows the team manager to change the stadium
     */
    public boolean setStadium(Stadium aStadium)
    {
        if(aStadium==null)
            return false;
        return teamManager.setStadium(aStadium);
    }

    public void ShowTeamManager(){
        teamManager.ShowTeamManager();

    }

    public void delete()
    {
        teamManager.delete();
    }
}
