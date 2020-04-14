import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class OwnerController
{
    Owner owner;

    public OwnerController(Owner owner)
    {
        this.owner = owner;
    }

    public Team getTeam()
    {
        return owner.getTeam();
    }

    public void setTeam(Team team)
    {
        owner.setTeam(team);
    }

    public Owner getAppointer()
    {
        return owner.getAppointer();
    }

    public void setAppointer(Owner appointedBy)
    {
        owner.setAppointer(appointedBy);
    }

    /**
     * add the entered asset to the owner's team
     * assets: coach, player, stadium
     */
    public boolean addAssetToTeam(Object o)
    {
        if(o==null || (!(o instanceof Coach) && !(o instanceof Player) && !(o instanceof Stadium)))
            return false;
        return owner.addAssetToTeam(o);
    }

    /**
     * removes the entered asset from the owner's team
     * assets: coach, player, stadium
     */
    public boolean removeAssetFromTeam(Object o)
    {
        if(o==null || (!(o instanceof Coach) && !(o instanceof Player) && !(o instanceof Stadium)))
            return false;
        return owner.removeAssetFromTeam(o);
    }

    /**
     * edits a team asset, according to specified entry
     * assets: Team manager, coach, player, stadium
     */
    public boolean editTeamAsset(Object o, int action, String input)
    {
        if(o==null || (!(o instanceof Coach) && (!(o instanceof TeamManager) && !(o instanceof Player) && !(o instanceof Stadium))))
            return false;
        return owner.editTeamAsset(o,action,input);
    }

    /**
     * returns a list of editing options for the asset
     * assets: Team manager, coach, player, stadium
     * (for it to work, start the list at 1 in the UI)
     */
    public ArrayList<String> showEditingOptions(Object o)
    {
        if(o==null || (!(o instanceof Coach) && (!(o instanceof TeamManager) && !(o instanceof Player) && !(o instanceof Stadium))))
            return null;
        return owner.showEditingOptions(o);
    }

    /**
     * adds a new owner to a team
     */
    public boolean appointOwnerToTeam(Account account)
    {
        if(account==null)
            return false;
        return owner.appointOwnerToTeam(account);
    }

    /**
     * remove an owner from the team
     */
    public boolean removeOwnerFromTeam(Owner owner)
    {
        if(owner==null)
            return false;
        return owner.removeOwnerFromTeam(owner);
    }

    /**
     * adds a new team manager to the team
     * permissions: manageName, manageManagers, managePage, manageCoaches, managePlayers, manageLeague, manageMatches, manageStadium
     */
    public boolean appointTeamManagerToTeam(Account account, List<TeamManager.PermissionEnum> permissions)
    {
        if(account==null || permissions==null)
            return false;
        return owner.appointTeamManagerToTeam(account,permissions);
    }

    /**
     * removes a team manager from the team
     */
    public boolean removeTeamManagerFromTeam(TeamManager teamManager)
    {
        if(teamManager==null)
            return false;
        return owner.removeTeamManagerFromTeam(teamManager);
    }

    /**
     * checks if manager has a certain permission
     */
    public boolean hasPermission(TeamManager teamManager, TeamManager.PermissionEnum permission)
    {
        if(teamManager==null || permission==null)
            return false;
        return owner.hasPermission(teamManager,permission);
    }

    /**
     * returns all the permissions of a manager
     */
    public List<TeamManager.PermissionEnum> getAllPermitions(TeamManager teamManager)
    {
        if(teamManager==null)
            return null;
        return owner.getAllPermitions(teamManager);
    }

    /**
     * add a permission to a team manager
     */
    public boolean addPermissionToManager(TeamManager teamManager,TeamManager.PermissionEnum permissionEnum)
    {
        if(teamManager==null || permissionEnum==null)
        {
            return false;
        }
        return owner.addPermissionToManager(teamManager,permissionEnum);
    }

    /**
     * remove a permission from a team manager
     */
    public boolean removePermissionFromManager(TeamManager teamManager,TeamManager.PermissionEnum permissionEnum)
    {
        if(teamManager==null || permissionEnum==null)
        {
            return false;
        }
        return owner.removePermissionFromManager(teamManager,permissionEnum);
    }

    /**
     * deactivates the owner's team
     */
    public boolean deactivateTeam()
    {
        return owner.deactivateTeam();
    }

    /**
     * activates the owner's team
     */
    public Team activateTeam(String teamName)
    {
        if(teamName==null)
            return null;
        return owner.activateTeam(teamName);
    }

    /**
     * return the chosen non active team
     */
    public Team getNonActiveTeam(String teamName)
    {
        if(teamName==null)
            return null;
       return owner.getNonActiveTeam(teamName);
    }

    public void delete()
    {
        owner.delete();
    }

    /**
     * creates a new team, provided there is an authorisation from the Association
     */
    public String createTeam(String teamName,League league, Stadium stadium)
    {
        if(teamName==null)
            return "Wrong input, team name is null";

        return owner.createTeam(teamName,league,stadium);
    }

    /**
     * creates a new player in the team
     */
    public Account createPlayer(String aName,int age, Date aBirthday, PositionEnum aPosition,String userName, String password)
    {
        if(aName==null || userName==null || password==null)
            return null;
        return owner.createPlayer(userName,age,aBirthday,aPosition,userName,password);
    }

    /**
     * creates a new team manager in the team
     */
    public Account createTeamManager(String aName,int age,List<TeamManager.PermissionEnum> permissions,String userName, String password)
    {
        if(aName==null || userName==null || password==null)
            return null;
        return owner.createTeamManager(aName,age,permissions,userName,password);
    }

    /**
     * creates a new coach in the team
     */
    public Account createCoach(String aName,int age, String aTraining, String aTeamRole,String userName, String password)
    {
        if(aName==null || userName==null || password==null)
            return null;
        return owner.createCoach(aName,age,aTraining,aTeamRole,userName,password);
    }


    public void ShowOwner(){
        owner.ShowOwner();
    }
}
