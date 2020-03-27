/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4811.445d1d99b modeling language!*/


import java.util.*;

// line 8 "model.ump"
// line 170 "model.ump"
public class System
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //System Associations
  private List<Team> teams;
  private List<Guest> guests;
  private List<Account> accounts;
  private List<Alert> alerts;
  private List<Page> pages;
  private List<League> leagues;
  private List<Season> seasons;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public System()
  {
    teams = new ArrayList<Team>();
    guests = new ArrayList<Guest>();
    accounts = new ArrayList<Account>();
    alerts = new ArrayList<Alert>();
    pages = new ArrayList<Page>();
    leagues = new ArrayList<League>();
    seasons = new ArrayList<Season>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Team getTeam(int index)
  {
    Team aTeam = teams.get(index);
    return aTeam;
  }

  public List<Team> getTeams()
  {
    List<Team> newTeams = Collections.unmodifiableList(teams);
    return newTeams;
  }

  public int numberOfTeams()
  {
    int number = teams.size();
    return number;
  }

  public boolean hasTeams()
  {
    boolean has = teams.size() > 0;
    return has;
  }

  public int indexOfTeam(Team aTeam)
  {
    int index = teams.indexOf(aTeam);
    return index;
  }
  /* Code from template association_GetMany */
  public Guest getGuest(int index)
  {
    Guest aGuest = guests.get(index);
    return aGuest;
  }

  public List<Guest> getGuests()
  {
    List<Guest> newGuests = Collections.unmodifiableList(guests);
    return newGuests;
  }

  public int numberOfGuests()
  {
    int number = guests.size();
    return number;
  }

  public boolean hasGuests()
  {
    boolean has = guests.size() > 0;
    return has;
  }

  public int indexOfGuest(Guest aGuest)
  {
    int index = guests.indexOf(aGuest);
    return index;
  }
  /* Code from template association_GetMany */
  public Account getAccount(int index)
  {
    Account aAccount = accounts.get(index);
    return aAccount;
  }

  public List<Account> getAccounts()
  {
    List<Account> newAccounts = Collections.unmodifiableList(accounts);
    return newAccounts;
  }

  public int numberOfAccounts()
  {
    int number = accounts.size();
    return number;
  }

  public boolean hasAccounts()
  {
    boolean has = accounts.size() > 0;
    return has;
  }

  public int indexOfAccount(Account aAccount)
  {
    int index = accounts.indexOf(aAccount);
    return index;
  }
  /* Code from template association_GetMany */
  public Alert getAlert(int index)
  {
    Alert aAlert = alerts.get(index);
    return aAlert;
  }

  public List<Alert> getAlerts()
  {
    List<Alert> newAlerts = Collections.unmodifiableList(alerts);
    return newAlerts;
  }

  public int numberOfAlerts()
  {
    int number = alerts.size();
    return number;
  }

  public boolean hasAlerts()
  {
    boolean has = alerts.size() > 0;
    return has;
  }

  public int indexOfAlert(Alert aAlert)
  {
    int index = alerts.indexOf(aAlert);
    return index;
  }
  /* Code from template association_GetMany */
  public Page getPage(int index)
  {
    Page aPage = pages.get(index);
    return aPage;
  }

  public List<Page> getPages()
  {
    List<Page> newPages = Collections.unmodifiableList(pages);
    return newPages;
  }

  public int numberOfPages()
  {
    int number = pages.size();
    return number;
  }

  public boolean hasPages()
  {
    boolean has = pages.size() > 0;
    return has;
  }

  public int indexOfPage(Page aPage)
  {
    int index = pages.indexOf(aPage);
    return index;
  }
  /* Code from template association_GetMany */
  public League getLeague(int index)
  {
    League aLeague = leagues.get(index);
    return aLeague;
  }

  public List<League> getLeagues()
  {
    List<League> newLeagues = Collections.unmodifiableList(leagues);
    return newLeagues;
  }

  public int numberOfLeagues()
  {
    int number = leagues.size();
    return number;
  }

  public boolean hasLeagues()
  {
    boolean has = leagues.size() > 0;
    return has;
  }

  public int indexOfLeague(League aLeague)
  {
    int index = leagues.indexOf(aLeague);
    return index;
  }
  /* Code from template association_GetMany */
  public Season getSeason(int index)
  {
    Season aSeason = seasons.get(index);
    return aSeason;
  }

  public List<Season> getSeasons()
  {
    List<Season> newSeasons = Collections.unmodifiableList(seasons);
    return newSeasons;
  }

  public int numberOfSeasons()
  {
    int number = seasons.size();
    return number;
  }

  public boolean hasSeasons()
  {
    boolean has = seasons.size() > 0;
    return has;
  }

  public int indexOfSeason(Season aSeason)
  {
    int index = seasons.indexOf(aSeason);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTeams()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Team addTeam(String aName, Page aPage, League aLeague, Stadium aStadium)
  {
    return new Team(aName, this, aPage, aLeague, aStadium);
  }

  public boolean addTeam(Team aTeam)
  {
    boolean wasAdded = false;
    if (teams.contains(aTeam)) { return false; }
    System existingSystem = aTeam.getSystem();
    boolean isNewSystem = existingSystem != null && !this.equals(existingSystem);
    if (isNewSystem)
    {
      aTeam.setSystem(this);
    }
    else
    {
      teams.add(aTeam);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTeam(Team aTeam)
  {
    boolean wasRemoved = false;
    //Unable to remove aTeam, as it must always have a system
    if (!this.equals(aTeam.getSystem()))
    {
      teams.remove(aTeam);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addTeamAt(Team aTeam, int index)
  {  
    boolean wasAdded = false;
    if(addTeam(aTeam))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTeams()) { index = numberOfTeams() - 1; }
      teams.remove(aTeam);
      teams.add(index, aTeam);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTeamAt(Team aTeam, int index)
  {
    boolean wasAdded = false;
    if(teams.contains(aTeam))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTeams()) { index = numberOfTeams() - 1; }
      teams.remove(aTeam);
      teams.add(index, aTeam);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTeamAt(aTeam, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGuests()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Guest addGuest(String aId)
  {
    return new Guest(aId, this);
  }

  public boolean addGuest(Guest aGuest)
  {
    boolean wasAdded = false;
    if (guests.contains(aGuest)) { return false; }
    System existingSystem = aGuest.getSystem();
    boolean isNewSystem = existingSystem != null && !this.equals(existingSystem);
    if (isNewSystem)
    {
      aGuest.setSystem(this);
    }
    else
    {
      guests.add(aGuest);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeGuest(Guest aGuest)
  {
    boolean wasRemoved = false;
    //Unable to remove aGuest, as it must always have a system
    if (!this.equals(aGuest.getSystem()))
    {
      guests.remove(aGuest);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addGuestAt(Guest aGuest, int index)
  {  
    boolean wasAdded = false;
    if(addGuest(aGuest))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGuests()) { index = numberOfGuests() - 1; }
      guests.remove(aGuest);
      guests.add(index, aGuest);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveGuestAt(Guest aGuest, int index)
  {
    boolean wasAdded = false;
    if(guests.contains(aGuest))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGuests()) { index = numberOfGuests() - 1; }
      guests.remove(aGuest);
      guests.add(index, aGuest);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addGuestAt(aGuest, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAccounts()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Account addAccount(String aUserName, String aPassword)
  {
    return new Account(aUserName, aPassword, this);
  }

  public boolean addAccount(Account aAccount)
  {
    boolean wasAdded = false;
    if (accounts.contains(aAccount)) { return false; }
    System existingSystem = aAccount.getSystem();
    boolean isNewSystem = existingSystem != null && !this.equals(existingSystem);
    if (isNewSystem)
    {
      aAccount.setSystem(this);
    }
    else
    {
      accounts.add(aAccount);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeAccount(Account aAccount)
  {
    boolean wasRemoved = false;
    //Unable to remove aAccount, as it must always have a system
    if (!this.equals(aAccount.getSystem()))
    {
      accounts.remove(aAccount);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addAccountAt(Account aAccount, int index)
  {  
    boolean wasAdded = false;
    if(addAccount(aAccount))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAccounts()) { index = numberOfAccounts() - 1; }
      accounts.remove(aAccount);
      accounts.add(index, aAccount);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveAccountAt(Account aAccount, int index)
  {
    boolean wasAdded = false;
    if(accounts.contains(aAccount))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAccounts()) { index = numberOfAccounts() - 1; }
      accounts.remove(aAccount);
      accounts.add(index, aAccount);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addAccountAt(aAccount, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAlerts()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Alert addAlert(String aDescription)
  {
    return new Alert(aDescription, this);
  }

  public boolean addAlert(Alert aAlert)
  {
    boolean wasAdded = false;
    if (alerts.contains(aAlert)) { return false; }
    System existingSystem = aAlert.getSystem();
    boolean isNewSystem = existingSystem != null && !this.equals(existingSystem);
    if (isNewSystem)
    {
      aAlert.setSystem(this);
    }
    else
    {
      alerts.add(aAlert);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeAlert(Alert aAlert)
  {
    boolean wasRemoved = false;
    //Unable to remove aAlert, as it must always have a system
    if (!this.equals(aAlert.getSystem()))
    {
      alerts.remove(aAlert);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addAlertAt(Alert aAlert, int index)
  {  
    boolean wasAdded = false;
    if(addAlert(aAlert))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAlerts()) { index = numberOfAlerts() - 1; }
      alerts.remove(aAlert);
      alerts.add(index, aAlert);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveAlertAt(Alert aAlert, int index)
  {
    boolean wasAdded = false;
    if(alerts.contains(aAlert))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAlerts()) { index = numberOfAlerts() - 1; }
      alerts.remove(aAlert);
      alerts.add(index, aAlert);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addAlertAt(aAlert, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPages()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Page addPage(Team aTeam, Player aPlayer, Coach aCoach)
  {
    return new Page(this, aTeam, aPlayer, aCoach);
  }

  public boolean addPage(Page aPage)
  {
    boolean wasAdded = false;
    if (pages.contains(aPage)) { return false; }
    System existingSystem = aPage.getSystem();
    boolean isNewSystem = existingSystem != null && !this.equals(existingSystem);
    if (isNewSystem)
    {
      aPage.setSystem(this);
    }
    else
    {
      pages.add(aPage);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePage(Page aPage)
  {
    boolean wasRemoved = false;
    //Unable to remove aPage, as it must always have a system
    if (!this.equals(aPage.getSystem()))
    {
      pages.remove(aPage);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPageAt(Page aPage, int index)
  {  
    boolean wasAdded = false;
    if(addPage(aPage))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPages()) { index = numberOfPages() - 1; }
      pages.remove(aPage);
      pages.add(index, aPage);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePageAt(Page aPage, int index)
  {
    boolean wasAdded = false;
    if(pages.contains(aPage))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPages()) { index = numberOfPages() - 1; }
      pages.remove(aPage);
      pages.add(index, aPage);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPageAt(aPage, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfLeagues()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addLeague(League aLeague)
  {
    boolean wasAdded = false;
    if (leagues.contains(aLeague)) { return false; }
    leagues.add(aLeague);
    if (aLeague.indexOfSystem(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aLeague.addSystem(this);
      if (!wasAdded)
      {
        leagues.remove(aLeague);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeLeague(League aLeague)
  {
    boolean wasRemoved = false;
    if (!leagues.contains(aLeague))
    {
      return wasRemoved;
    }

    int oldIndex = leagues.indexOf(aLeague);
    leagues.remove(oldIndex);
    if (aLeague.indexOfSystem(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aLeague.removeSystem(this);
      if (!wasRemoved)
      {
        leagues.add(oldIndex,aLeague);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addLeagueAt(League aLeague, int index)
  {  
    boolean wasAdded = false;
    if(addLeague(aLeague))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLeagues()) { index = numberOfLeagues() - 1; }
      leagues.remove(aLeague);
      leagues.add(index, aLeague);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveLeagueAt(League aLeague, int index)
  {
    boolean wasAdded = false;
    if(leagues.contains(aLeague))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLeagues()) { index = numberOfLeagues() - 1; }
      leagues.remove(aLeague);
      leagues.add(index, aLeague);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addLeagueAt(aLeague, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfSeasons()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Season addSeason(String aName)
  {
    return new Season(aName, this);
  }

  public boolean addSeason(Season aSeason)
  {
    boolean wasAdded = false;
    if (seasons.contains(aSeason)) { return false; }
    System existingSystem = aSeason.getSystem();
    boolean isNewSystem = existingSystem != null && !this.equals(existingSystem);
    if (isNewSystem)
    {
      aSeason.setSystem(this);
    }
    else
    {
      seasons.add(aSeason);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeSeason(Season aSeason)
  {
    boolean wasRemoved = false;
    //Unable to remove aSeason, as it must always have a system
    if (!this.equals(aSeason.getSystem()))
    {
      seasons.remove(aSeason);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addSeasonAt(Season aSeason, int index)
  {  
    boolean wasAdded = false;
    if(addSeason(aSeason))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSeasons()) { index = numberOfSeasons() - 1; }
      seasons.remove(aSeason);
      seasons.add(index, aSeason);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveSeasonAt(Season aSeason, int index)
  {
    boolean wasAdded = false;
    if(seasons.contains(aSeason))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSeasons()) { index = numberOfSeasons() - 1; }
      seasons.remove(aSeason);
      seasons.add(index, aSeason);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addSeasonAt(aSeason, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=teams.size(); i > 0; i--)
    {
      Team aTeam = teams.get(i - 1);
      aTeam.delete();
    }
    for(int i=guests.size(); i > 0; i--)
    {
      Guest aGuest = guests.get(i - 1);
      aGuest.delete();
    }
    for(int i=accounts.size(); i > 0; i--)
    {
      Account aAccount = accounts.get(i - 1);
      aAccount.delete();
    }
    for(int i=alerts.size(); i > 0; i--)
    {
      Alert aAlert = alerts.get(i - 1);
      aAlert.delete();
    }
    for(int i=pages.size(); i > 0; i--)
    {
      Page aPage = pages.get(i - 1);
      aPage.delete();
    }
    ArrayList<League> copyOfLeagues = new ArrayList<League>(leagues);
    leagues.clear();
    for(League aLeague : copyOfLeagues)
    {
      aLeague.removeSystem(this);
    }
    for(int i=seasons.size(); i > 0; i--)
    {
      Season aSeason = seasons.get(i - 1);
      aSeason.delete();
    }
  }

}