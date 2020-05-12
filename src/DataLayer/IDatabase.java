package DataLayer;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;
import java.util.List;

public interface IDatabase {

    /**
     * @return a connection to the database
     */
    public Connection connectToDB();

    /**
     * get all fans
     * returns: username,name,trackPersonalPages,GetMatchNotifications
     */
    public List<String[]> getFansFromAccounts();

    /**
     * get all owners
     * @return username,name
     */
    public List<String[]> getOwnersFromAccounts();

    /**
     * get all referees
     * @return username,name,training
     */
    public List<String[]> getRefereesFromAccounts();

    /**
     * get all coaches
     * @return username,name,training,teamrole,age
     */
    public List<String[]> getCoachesFromAccounts();

    /**
     * get all players
     * @return username,name,birthday,position
     */
    public List<String[]> getPlayersFromAccounts();

    /**
     * get all system managers
     * @return username,name
     */
    public List<String[]> getSMsFromAccounts();
    /**
     * get all association representatives
     * @return username,name
     */
    public List<String[]> getARsFromAccounts();

    /**
     * get all team managers
     * @return username,name
     */
    public List<String[]> getTMsFromAccounts();

    /**
     * get all teams
     * @return name,points
     */
    public List<String[]> getTeams();

    /**
     * get all leagues
     * @return name
     */
    public List<String[]> getLeagues();

    /**
     * get all stadiums
     * @return name
     */
    public List<String[]> getStadiums();

    /**
     * get all accounts
     * @return username, password, name, age
     */
    public List<String[]> getAccounts();

    /**
     * gets the account info from username
     * @return username,password,name,age
     */
    public String[] getAccount(String username);

    /**
     * get all roles the account has
     * @return the name of the roles
     */
    public List<String> getAccountRoles(String userName);

    /**
     *  adds an account to the database
     */
    public void addAccount(String userName, String password, String name, int age);

    /**
     *  adds an owner to the database
     */
    public void addOwnerRole(String userName, String name, String teamName, String appointedUserName);

    public void addPlayerRole(String userName, String name, Date birthday, String position, String teamName, int pageID);

    public void addFanRole(String userName, String name, boolean trackPersonalPages, boolean getMatchNotifications, List<Integer> pagesIDs);

    public void addRefereeRole(String userName, String name, String training, HashMap<String, String> refLeaguesAndSeasons);

    public void addTeamManagerRole(String userName, String name, String teamName, String appointerUserName, List<String> permissions);

    public void addSystemManagerRole(String userName, String name, HashMap<String, String> complaintAndComments);

    public void addAssociationRepresentativeRole(String userName, String name);

    public void addCoachRole(String userName, String name, String training,String teamRole, int pageID, String teamName);

    public void addLeague(String name, List<String> seasonList, List<String[]> policyList);

    public void addSeason(String name, List<String> leagueList, List<String[]> policyList);

    public void addStadium(String name);

    public void addTeam(String name, int pageID, String leagueName, String stadiumName,int points);

    public void addMatch(String date, Time time, int awayScore, int homeScore, String awayTeamName, String homeTeamName,
                         String mainRefUN, String lineRefUN1, String lineRefUN2, String stadiumName, String seasonName);

    public void addAlert(String userName, List<String> alerts);

    public void addPage(int pageID);

    public void addRefsToMatch(String date, Time time, String awayTeamName, String homeTeamName, String username1,String username2, String username3);

    public void addGameEvent(String eventType, Time hour, String description, int gameMinute, String date, String awayTeamName, String homeTeamName);
}
