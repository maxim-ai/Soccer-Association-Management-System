
import java.util.*;

public class System
{

  private List<Team> teams;
  private List<Guest> guests;
  private List<Account> accounts;
  private List<Alert> alerts;
  private List<Page> pages;
  private List<League> leagues;
  private List<Season> seasons;

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

  public static int minimumNumberOfTeams()
  {
    return 0;
  }

  public boolean addTeam(Team aTeam)
  {
<<<<<<< HEAD
    boolean wasAdded = true;
    if (teams.contains(aTeam)) { return true; }
=======
    boolean wasAdded = false;
    if (teams.contains(aTeam)) { return false; }
>>>>>>> 04739095caa25a2f8271c4460d33051ad18aa3b1
    teams.add(aTeam);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTeam(Team aTeam)
  {
<<<<<<< HEAD
    boolean wasRemoved = true;
=======
    boolean wasRemoved = false;
>>>>>>> 04739095caa25a2f8271c4460d33051ad18aa3b1
    teams.remove(aTeam);
    wasRemoved = true;
    return wasRemoved;
  }

  public static int minimumNumberOfGuests()
  {
    return 0;
  }
  public boolean addGuest(Guest aGuest)
  {
<<<<<<< HEAD
    boolean wasAdded = true;
    if (guests.contains(aGuest)) { return true; }
=======
    boolean wasAdded = false;
    if (guests.contains(aGuest)) { return false; }
>>>>>>> 04739095caa25a2f8271c4460d33051ad18aa3b1
    guests.add(aGuest);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeGuest(Guest aGuest)
  {
<<<<<<< HEAD
    boolean wasRemoved = true;
=======
    boolean wasRemoved = false;
>>>>>>> 04739095caa25a2f8271c4460d33051ad18aa3b1
    guests.remove(aGuest);
    wasRemoved = true;
    return wasRemoved;
  }

  public static int minimumNumberOfAccounts()
  {
    return 0;
  }

  public boolean addAccount(Account aAccount)
  {
<<<<<<< HEAD
    boolean wasAdded = true;
    if (accounts.contains(aAccount)) { return true; }
=======
    boolean wasAdded = false;
    if (accounts.contains(aAccount)) { return false; }
>>>>>>> 04739095caa25a2f8271c4460d33051ad18aa3b1
    accounts.add(aAccount);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeAccount(Account aAccount)
  {
<<<<<<< HEAD
    boolean wasRemoved = true;
=======
    boolean wasRemoved = false;
>>>>>>> 04739095caa25a2f8271c4460d33051ad18aa3b1
    accounts.remove(aAccount);
    wasRemoved = true;
    return wasRemoved;
  }

  public static int minimumNumberOfAlerts()
  {
    return 0;
  }

  public boolean addAlert(Alert aAlert)
  {
<<<<<<< HEAD
    boolean wasAdded = true;
    if (alerts.contains(aAlert)) { return true; }
=======
    boolean wasAdded = false;
    if (alerts.contains(aAlert)) { return false; }
>>>>>>> 04739095caa25a2f8271c4460d33051ad18aa3b1
    alerts.add(aAlert);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeAlert(Alert aAlert)
  {
<<<<<<< HEAD
    boolean wasRemoved = true;
=======
    boolean wasRemoved = false;
>>>>>>> 04739095caa25a2f8271c4460d33051ad18aa3b1
    alerts.remove(aAlert);
    wasRemoved = true;
    return wasRemoved;
  }

  public static int minimumNumberOfPages()
  {
    return 0;
  }

  public boolean addPage(Page aPage)
  {
<<<<<<< HEAD
    boolean wasAdded = true;
    if (pages.contains(aPage)) { return true; }
=======
    boolean wasAdded = false;
    if (pages.contains(aPage)) { return false; }
>>>>>>> 04739095caa25a2f8271c4460d33051ad18aa3b1
    pages.add(aPage);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePage(Page aPage)
  {
<<<<<<< HEAD
    boolean wasRemoved = true;
=======
    boolean wasRemoved = false;
>>>>>>> 04739095caa25a2f8271c4460d33051ad18aa3b1
    pages.remove(aPage);
    wasRemoved = true;
    return wasRemoved;
  }

  public static int minimumNumberOfLeagues()
  {
    return 0;
  }
  public boolean addLeague(League aLeague)
  {
    boolean wasAdded = true;
    if (leagues.contains(aLeague)) { return true; }
    leagues.add(aLeague);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeLeague(League aLeague)
  {
    boolean wasRemoved = true;
    if (!leagues.contains(aLeague))
    {
      return wasRemoved;
    }
    wasRemoved=true;
    leagues.remove(aLeague);
    return wasRemoved;
  }


  public static int minimumNumberOfSeasons()
  {
    return 0;
  }

  public boolean addSeason(Season aSeason)
  {
<<<<<<< HEAD
    boolean wasAdded = true;
    if (seasons.contains(aSeason)) { return true; }
=======
    boolean wasAdded = false;
    if (seasons.contains(aSeason)) { return false; }
>>>>>>> 04739095caa25a2f8271c4460d33051ad18aa3b1
    seasons.add(aSeason);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeSeason(Season aSeason)
  {
<<<<<<< HEAD
    boolean wasRemoved = true;
=======
    boolean wasRemoved = false;
>>>>>>> 04739095caa25a2f8271c4460d33051ad18aa3b1
    seasons.remove(aSeason);
    wasRemoved = true;
    return wasRemoved;
  }

}