
import java.sql.Date;
import java.util.*;

public class Owner extends Role {
    private Team team;
    private Owner appointedBy;
    private HashMap<TeamManager,List<TeamManager.PermissionEnum>> managerPermissions;

    public Owner(String aName, Team team, Owner appointer)
    {
        super(aName);
        this.team = team;
        appointedBy = appointer;
        managerPermissions=new HashMap<>();
    }

    public Team getTeam()
    {
        return team;
    }

    public void setTeam(Team team)
    {
        this.team = team;
    }

    public Owner getAppointer()
    {
        return appointedBy;
    }

    public void setAppointer(Owner appointedBy)
    {
        this.appointedBy = appointedBy;
    }

    /**
     * add the entered asset to the owner's team
     * assets: Team manager, coach, player, stadium
     */
    public boolean addAssetToTeam(Object o) {
        if (this.team == null || !team.isActive() || o == null)
            return false;

        if (o instanceof TeamManager) {
            return team.addTeamManager((TeamManager) o);
        }
        else if (o instanceof Coach) {
            return team.addCoach((Coach) o);
        }
        else if (o instanceof Player) {
            return team.addPlayer((Player) o);
        }
        else if (o instanceof Stadium) {
            return team.setStadium((Stadium) o);
        }
        else
            return false;
    }

    /**
     * removes the entered asset from the owner's team
     * assets: Team manager, coach, player, stadium
     */
    public boolean removeAssetFromTeam(Object o)
    {
        boolean validRemoval = true;
        if (this.team == null || !team.isActive() || o == null)
            return false;

        if (o instanceof TeamManager) {
            if (team.indexOfTeamManager((TeamManager) o) == -1)
                return false;
            return team.removeTeamManager((TeamManager) o);
        } else if (o instanceof Coach) {
            if (team.indexOfCoach((Coach) o) == -1)
                return false;
            return team.removeCoach((Coach) o);
        } else if (o instanceof Player) {
            if (team.indexOfPlayer((Player) o) == -1)
                return false;
            return team.removePlayer((Player) o);
        } else if (o instanceof Stadium) {
            if (!team.getStadium().equals(o))
                return false;
            return team.setStadium(null);
        } else
            return false;
    }

    /**
     * edits a team asset, according to specified entry
     * assets: Team manager, coach, player, stadium
     */
    public boolean editTeamAsset(Object o, int action, String input)
    {
        if (this.team == null || !team.isActive() || o == null)
            return false;

        if (o instanceof TeamManager)
        {
            if(action==1)
            {
                return ((TeamManager)o).setName(input);
            }
            else
                return false;
        }
        else if (o instanceof Coach)
        {
            if(action==1)
            {
                return ((Coach)o).setTraining(input);
            }
            else if(action==2)
            {
                return ((Coach)o).setTeamRole(input);
            }
            else
                return false;
        }
        else if (o instanceof Player)
        {
            if(action==1)
            {
                return ((Player)o).setName(input);
            }
            else if(action==2)
            {
                String[] parsedDate=input.split(".");
                return ((Player)o).setBirthday(new Date(Integer.parseInt(parsedDate[2]),Integer.parseInt(parsedDate[1]),Integer.parseInt(parsedDate[0])));
            }
            else if(action==3)
            {
                try
                {
                    return ((Player)o).setPosition(PositionEnum.valueOf(input));
                }
                catch (Exception e)
                {
                    return false;
                }
            }
            else
                return false;
        }
        else if (o instanceof Stadium) {
            if(action==1)
            {
                return ((Stadium)o).setName(input);
            }
            else
                return false;
        }
        else
            return false;
    }

    /**
     * returns a list of editing options for the asset
     * assets: Team manager, coach, player, stadium
     * (for it to work, start the list at 1 in the UI)
     */
    public ArrayList<String> showEditingOptions(Object o)
    {
        ArrayList<String> options=new ArrayList();
        if (this.team == null || !team.isActive() || o == null)
            return options;

        if (o instanceof TeamManager) {
            options.add("name");
        }
        else if (o instanceof Coach) {
            options.add("name");
            options.add("training");
            options.add("teamRole");
        }
        else if (o instanceof Player) {
            options.add("name");
            options.add("birthday");
            options.add("position");
        }
        else if (o instanceof Stadium) {
            options.add("name");
        }
        return options;
    }

    /**
     * adds a new owner to a team
     */
    public boolean appointOwnerToTeam(Account account) {
        Owner checkOwner = account.checkIfOwner();
        if (checkOwner != null) {
            if (team.indexOfOwner(checkOwner) != -1)
                return false;
        } else {
            checkOwner = new Owner(account.getRole(0).getName(), this.team, this);
            account.addRole(checkOwner);
        }

        return team.addOwner(checkOwner);
    }

    /**
     * remove an owner from the team
     * @param owner
     * @return
     */
    public boolean removeOwnerFromTeam(Owner owner) {
        if (!owner.getTeam().equals(this.team) || !owner.appointedBy.equals(this))
            return false;
        if (owner.team.numberOfOwners() == 1)
            return false;

        owner.team.removeOwner(owner);

        for (Account account : Controller.getAccounts())
            for (Role role : account.getRoles())
                if (role instanceof Owner && role.equals(owner)) {
                    for (Role del : account.getRoles())
                        account.removeRole(del);
                    return true;
                }

        return true;
    }

    /**
     * adds a new team manager to the team
     * permissions: manageName, manageManagers, managePage, manageCoaches, managePlayers, manageLeague, manageMatches, manageStadium
     */
    public boolean appointTeamManagerToTeam(Account account,List<TeamManager.PermissionEnum> permissions) {
        TeamManager tempTeamManager=account.checkIfTeamManagr();
        Owner tempOwner=account.checkIfOwner();
        if((tempTeamManager!=null || tempOwner!=null) && (this.team.indexOfTeamManager(tempTeamManager)!=-1) || (tempTeamManager.getTeam().equals(this.team)))
            return false;

        if(tempTeamManager==null)
        {
            tempTeamManager=new TeamManager(account.getRole(0).getName(),this.team,this);
            account.addRole(tempTeamManager);
        }
        this.managerPermissions.put(tempTeamManager,permissions);

        return this.team.addTeamManager(tempTeamManager);
    }

    /**
     * removes a team manager from the team
     */
    public boolean removeTeamManagerFromTeam(TeamManager teamManager)
    {
        if (!teamManager.getTeam().equals(this.team) || !teamManager.getAppointer().equals(this))
            return false;

        teamManager.getTeam().removeTeamManager(teamManager);

        for (Account account : Controller.getAccounts())
            for (Role role : account.getRoles())
                if (role instanceof Owner && role.equals(teamManager))
                    account.removeRole(role);

        return true;
    }

    /**
     * checks if manager has a certain permission
     */
    public boolean hasPermission(TeamManager teamManager, TeamManager.PermissionEnum permission)
    {
        return managerPermissions.containsKey(teamManager) && managerPermissions.get(teamManager).contains(permission);
    }

    /**
     * returns all the permissions of a manager
     */
    public List<TeamManager.PermissionEnum> getAllPermitions(TeamManager teamManager)
    {
        return managerPermissions.get(teamManager);
    }

    /**
     * deactivates the owner's team
     */
    public boolean deactivateTeam()
    {
        this.team.setActive(false);
        return true;
    }

    /**
     * activates the owner's team
     */
    public boolean activateTeam()
    {
        this.team.setActive(true);
        return true;
    }

}