
import java.io.Serializable;

import java.util.*;

public class Owner extends Role implements Serializable {
    private Team team;
    private Owner appointedBy;
    private HashMap<TeamManager,List<TeamManager.PermissionEnum>> managerPermissions;
    private List<Team> nonActiveTeams;

    public Owner(String aName, Team team, Owner appointer)
    {
        super(aName);
        setTeam(team);
        if(appointer==null)
            appointedBy=this;
        else
            appointedBy = appointer;
        managerPermissions=new HashMap<>();
        nonActiveTeams=new ArrayList<>();
    }

    public Team getTeam()
    {
        return team;
    }

    public void setTeam(Team team)
    {
        this.team = team;
        if(team!=null && team.indexOfOwner(this)==-1)
            team.addOwner(this);
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
     * assets: coach, player, stadium
     */
    public boolean addAssetToTeam(Object o)
    {
        if (this.team == null)
            return false;

        else if (o instanceof Coach)
        {
            Logger.getInstance().writeNewLine(this.getName()+" set "+((Coach) o).getName() +" as a coach for the team:" + team.getName());
            return team.addCoach((Coach) o);
        }
        else if (o instanceof Player) {
            Logger.getInstance().writeNewLine(this.getName()+" set "+((Player) o).getName() +" as a player for the team:" + team.getName());
            return team.addPlayer((Player) o);
        }
        else if (o instanceof Stadium) {
            Logger.getInstance().writeNewLine(this.getName()+" set "+((Stadium) o).getName() +" as the stadium for the team:" + team.getName());
            return team.setStadium((Stadium) o);
        }
        else
            return false;
    }

    /**
     * removes the entered asset from the owner's team
     * assets: coach, player, stadium
     */
    public boolean removeAssetFromTeam(Object o)
    {
        boolean validRemoval = true;
        if (this.team == null)
            return false;

        else if (o instanceof Coach) {
            if (team.getCoachs()==null || team.indexOfCoach((Coach) o) == -1)
                return false;
            Logger.getInstance().writeNewLine(this.getName()+" removed the Coach"+((Coach) o).getName() +" from the team:" + team.getName());
            return team.removeCoach((Coach) o);
        }
        else if (o instanceof Player) {
            if (team.getPlayers()==null || team.indexOfPlayer((Player) o) == -1)
                return false;
            Logger.getInstance().writeNewLine(this.getName()+" removed the Player"+((Player) o).getName() +" from the team:" + team.getName());
            return team.removePlayer((Player) o);
        }
        else if (o instanceof Stadium) {
            if (team.getStadium()==null || !team.getStadium().equals(o))
                return false;
            Logger.getInstance().writeNewLine(this.getName()+" removed the Stadium"+((Stadium) o).getName() +" from the team:" + team.getName());
            return team.setStadium(null);
        }
        else
            return false;
    }

    /**
     * edits a team asset, according to specified entry
     * assets: Team manager, coach, player, stadium
     * 1:  change name, 2: training for coach, birthday fr player (dd-mm-yyyy), 3:team role for coach, position for player
     */
    public boolean editTeamAsset(Object o, int action, String input)
    {
        if (this.team == null)
            return false;

        if (o instanceof TeamManager)
        {
            if(action==1)
            {
                Logger.getInstance().writeNewLine(this.getName()+" changed the name of the Team manager "+((TeamManager) o).getName() +" to be:" + input);
                return ((TeamManager)o).setName(input);
            }
            else
                return false;
        }
        else if (o instanceof Coach)
        {
            if(action==1)
            {
                Logger.getInstance().writeNewLine(this.getName()+" changed the name of the Coach "+((Coach) o).getName() +" to be:" + input);
                return ((Coach)o).setName(input);
            }
            else if(action==2)
            {
                Logger.getInstance().writeNewLine(this.getName()+" changed the training of the Coach "+((Coach) o).getName() +" from "+((Coach) o).getTraining()+" to be:" + input);
                return ((Coach)o).setTraining(input);
            }
            else if(action==3)
            {
                Logger.getInstance().writeNewLine(this.getName()+" changed the team role of the Coach "+((Coach) o).getName() +" from "+((Coach) o).getTeamRole()+" to be:" + input);
                return ((Coach)o).setTeamRole(input);
            }
            else
                return false;
        }
        else if (o instanceof Player)
        {
            if(action==1)
            {
                Logger.getInstance().writeNewLine(this.getName()+" changed the name of the Player "+((Player) o).getName() +" to be:" + input);
                return ((Player)o).setName(input);
            }
            else if(action==2)
            {
                Date newDate= null;
                try {
                    String[] parsedDate=input.split("-");
                    Logger.getInstance().writeNewLine(this.getName()+" changed the birthday of the Player "+((Player) o).getName() +" from "+((Player) o).getBirthday()+" to be:" + input);
                    newDate = new Date(Integer.parseInt(parsedDate[2]),Integer.parseInt(parsedDate[1]),Integer.parseInt(parsedDate[0]));
                }
                catch (Exception e) {
                    return false;
                }
                return ((Player)o).setBirthday(newDate);
            }
            else if(action==3)
            {
                try
                {
                    Logger.getInstance().writeNewLine(this.getName()+" changed the position of the Player "+((Player) o).getName() +" from "+((Player) o).getPosition()+" to be:" + input);
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
                Logger.getInstance().writeNewLine(this.getName()+" changed the name of the Stadium "+((Stadium) o).getName() +" to be:" + input);
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
        if (this.team == null)
            return null;

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
        else
            return null;
        return options;
    }

    /**
     * adds a new owner to a team
     */
    public boolean appointOwnerToTeam(Account account)
    {

        if(account.hasRoles() && account.checkIfOwner()==null &&account.checkIfTeamManagr()==null && account.checkIfCoach()==null && account.checkIfPlayer()==null)
            return false;
        Owner checkOwner = account.checkIfOwner();
        if (checkOwner != null) {
            if (team.indexOfOwner(checkOwner) != -1)
                return false;
        }
        else
        {
            checkOwner = new Owner(account.getRole(0).getName(), this.team, this);
            account.addRole(checkOwner);
        }
        Logger.getInstance().writeNewLine(this.getName()+" appointed "+account.getName()+" to be an owner on team "+ team.getName());
        return team.addOwner(checkOwner);
    }

    /**
     * remove an owner from the team
     */
    public boolean removeOwnerFromTeam(Owner owner) {
        if (owner.team.numberOfOwners() == 1)
            return false;

        if (!owner.getTeam().equals(this.team) || !owner.appointedBy.equals(this))
            return false;

        owner.team.removeOwner(owner);

        for (Account account : DataManager.getAccounts())
            for (Role role : account.getRoles())
                if (role instanceof Owner && role.equals(owner))
                {
                    account.removeRole(role);
                    account.removeRole(account.checkIfPlayer());
                    account.removeRole(account.checkIfCoach());
                    account.removeRole(account.checkIfTeamManagr());

                    if(account.checkIfPlayer()!=null) account.checkIfPlayer().delete();
                    if(account.checkIfCoach()!=null) account.checkIfCoach().delete();
                    if(account.checkIfTeamManagr()!=null) account.checkIfTeamManagr().delete();
                    ((Owner) role).delete();

                    Logger.getInstance().writeNewLine(this.getName()+" removed "+account.getName()+"'s entire permissions");
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
        if(account.hasRoles())
        {
            if((account.checkIfOwner()!=null && team.getOwners().contains(account.checkIfOwner())) || ((account.checkIfTeamManagr()!=null && team.getTeamManagers().contains(account.checkIfTeamManagr()))))
                return false;
            if(!(account.checkIfTeamManagr()!=null || account.checkIfOwner()!=null))
                return false;
        }

        if(tempTeamManager==null)
        {
            tempTeamManager=new TeamManager(account.getName(),this.team,this);
            account.addRole(tempTeamManager);
        }
        this.managerPermissions.put(tempTeamManager,permissions);

        Logger.getInstance().writeNewLine(this.getName()+" appointed "+account.getName()+" to be an Team manager on team "+ team.getName());

        return this.team.addTeamManager(tempTeamManager);
    }

    /**
     * removes a team manager from the team
     */
    public boolean removeTeamManagerFromTeam(TeamManager teamManager)
    {
        if (!teamManager.getTeam().equals(this.team) || !teamManager.getAppointer().equals(this))
            return false;

        teamManager.delete();

        List<Account> tamp=DataManager.getAccounts();

        for (Account account : DataManager.getAccounts())
        {
            if(account.checkIfTeamManagr()!=null && account.checkIfTeamManagr().equals(teamManager))
            {
                account.removeRole(teamManager);
                break;
            }
        }

        Logger.getInstance().writeNewLine(this.getName()+" removed "+teamManager.getName()+"'s permission as a Team manager on team "+team.getName());

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
     * add a permission to a team manager
     */
    public boolean addPermissionToManager(TeamManager teamManager,TeamManager.PermissionEnum permissionEnum)
    {

        if(!teamManager.getAppointer().equals(this))
            return false;
        List<TeamManager.PermissionEnum> permissions=managerPermissions.get(teamManager);
        if(!permissions.contains(permissionEnum))
            permissions.add(permissionEnum);
        managerPermissions.put(teamManager,permissions);
        return true;
    }

    /**
     * remove a permission from a team manager
     */
    public boolean removePermissionFromManager(TeamManager teamManager,TeamManager.PermissionEnum permissionEnum)
    {

        if(!teamManager.getAppointer().equals(this))
            return false;
        List<TeamManager.PermissionEnum> permissions=managerPermissions.get(teamManager);
        permissions.remove(permissionEnum);
        managerPermissions.put(teamManager,permissions);
        return true;
    }

    /**
     * deactivates the owner's team
     */
    public boolean deactivateTeam()
    {
        String notification=this.getName()+" has deactivated team: "+team.getName();
        for(Owner owner:team.getOwners())
            Alert.notifyOtherRole(notification,owner);
        for(TeamManager teamManager:team.getTeamManagers())
            Alert.notifyOtherRole(notification,teamManager);
        for(SystemManager systemManager:DataManager.getSystemManagersFromAccounts())
            Alert.notifyOtherRole(notification,systemManager);

        nonActiveTeams.add(team);
        Logger.getInstance().writeNewLine(this.getName()+" has deactivated "+team.getName());
        team.delete();

        return true;
    }

    /**
     * activates the chosen team
     */
    public Team activateTeam(String teamName)
    {
        Team teamToActivate=getNonActiveTeam(teamName);
        if(teamToActivate==null)
            return null;
        nonActiveTeams.remove(teamToActivate);

        String notification=this.getName()+" has activated team: "+teamName;

        for(SystemManager systemManager:DataManager.getSystemManagersFromAccounts())
            Alert.notifyOtherRole(notification,systemManager);

        Logger.getInstance().writeNewLine(this.getName()+" has activated "+teamName);

        return teamToActivate;
    }

    /**
     * return the chosen non active team
     */
    public Team getNonActiveTeam(String teamName)
    {
        for(Team team:nonActiveTeams)
        {
            if(team.getName().equals(teamName))
                return team;
        }
        return null;
    }

    public void ShowOwner(){
        System.out.println("Name:");
        System.out.println(this.getName());
        System.out.println("Team owned:");
        System.out.println(this.getTeam().getName());
    }

    public void delete()
    {
        team.removeOwner(this);
    }

    /**
     * creates a new team, provided there is an authorisation from the Association
     */
    public String createTeam(String teamName,League league, Stadium stadium)
    {
        boolean teamExists=false;
        for(Team t: DataManager.getTeams())
        {
            if(t.getName().equals(teamName))
            {
                teamExists=true;
                break;
            }
        }

        if(teamExists)
            return "Wrong input, team already exists";

        else if(!AssociationRepresentative.checkIfRequestExists(this,teamName))
        {
            for(AssociationRepresentative ar:DataManager.getAssiciationRepresentivesFromAccounts())
            {
                Alert.notifyOtherRole(getName()+" is requesting to create a new team, teamName: "+teamName,ar);
                AssociationRepresentative.addOpenTeamRequest(this,teamName);
            }
            return "Request sent, waiting for approval";
        }
        else
        {
            if(!AssociationRepresentative.getRequestStatus(this,teamName))
                return "waiting for approval";
            else
            {
                Team team=new Team(teamName,league,stadium);
                team.addOwner(this);
                DataManager.addTeam(team);
                AssociationRepresentative.removeOpenTeamRequest(this,teamName);
                Logger.getInstance().writeNewLine(getName()+" just opened the team: "+teamName);
                return "Team successfully added";
            }
        }

    }

    /**
     * creates a new player in the team
     */
    public Account createPlayer(String aName,int age, Date aBirthday, PositionEnum aPosition,String userName, String password)
    {
        if(getTeam()==null)
            return null;
        Player player=new Player(aName,aBirthday,aPosition,getTeam(),null);
        new Page(player);
        Account account=new Account(aName,age,userName,password);
        account.addRole(player);
        SystemManager.createAccount(account);
        Logger.getInstance().writeNewLine(getName()+" just a new player: "+aName+" to team: "+getTeam());
        return account;
    }

    /**
     * creates a new team manager in the team
     */
    public Account createTeamManager(String aName,int age,List<TeamManager.PermissionEnum> permissions,String userName, String password)
    {
        if(getTeam()==null)
            return null;
        Account account=new Account(aName,age,userName,password);
        appointTeamManagerToTeam(account,permissions);
        SystemManager.createAccount(account);
        Logger.getInstance().writeNewLine(getName()+" just a new team manager: "+aName+" to team: "+getTeam());
        return account;
    }

    /**
     * creates a new coach in the team
     */
    public Account createCoach(String aName,int age, String aTraining, String aTeamRole,String userName, String password)
    {
        if(getTeam()==null)
            return null;
        Coach coach=new Coach(aName,aTraining,aTeamRole,null);
        coach.addTeam(getTeam());
        new Page(coach);
        Account account=new Account(aName,age,userName,password);
        account.addRole(coach);
        SystemManager.createAccount(account);
        Logger.getInstance().writeNewLine(getName()+" just a new coach: "+aName+" to team: "+getTeam());
        return account;
    }





}