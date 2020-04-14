import java.util.*;

public class Fan extends Role
{
  private List<Page> pages;
  private boolean trackPersonalPages;
  private boolean getMatchNotifications;
  private List<String[]> searchHistory;


  public Fan(String aName)
  {
    super(aName);
    pages = new ArrayList<Page>();
    trackPersonalPages=false;
    getMatchNotifications=false;
    searchHistory=new LinkedList<>();
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
  public static int minimumNumberOfPages()
  {
    return 0;
  }
  public boolean addPage(Page aPage)
  {
    boolean wasAdded = false;
    if (pages.contains(aPage)) { return false; }
    pages.add(aPage);
    if (aPage.indexOfFan(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aPage.addFan(this);
      if (!wasAdded)
      {
        pages.remove(aPage);
      }
    }
    return wasAdded;
  }
  public boolean removePage(Page aPage)
  {
    boolean wasRemoved = false;
    if (!pages.contains(aPage))
    {
      return wasRemoved;
    }

    int oldIndex = pages.indexOf(aPage);
    pages.remove(oldIndex);
    if (aPage.indexOfFan(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aPage.removeFan(this);
      if (!wasRemoved)
      {
        pages.add(oldIndex,aPage);
      }
    }
    return wasRemoved;
  }

  public void delete()
  {
    ArrayList<Page> copyOfPages = new ArrayList<Page>(pages);
    pages.clear();
    for(Page aPage : copyOfPages)
    {
      aPage.removeFan(this);
    }
  }

  public boolean isTrackPersonalPages() {
    return trackPersonalPages;
  }
  public void setTrackPersonalPages(boolean trackPersonalPages) {
    this.trackPersonalPages = trackPersonalPages;
  }

  public boolean isGetMatchNotifications() {
    return getMatchNotifications;
  }

  public void setGetGameNotifications(boolean getGameNotifications) {
    this.getMatchNotifications = getGameNotifications;
  }

  //endregion

  //region My Methods
  public void ShowInfo(String InfoAbout){ new Guest("1").ShowInfo(InfoAbout); }

  public void Search(String criterion, String query){
    new Guest("1").Search(criterion,query);
    String[] savedSearch={criterion,query};
    searchHistory.add(savedSearch);
  }

  public void Filter(String category,String roleFilter){
    new Guest("1").Filter(category,roleFilter);
  }

  public void Logout(){
    System.out.println("Logged out");
  }

  public void SubscribeTrackPersonalPages(){ trackPersonalPages=true; }

  public static void notifyFansAboutMatch(Match match){
    List<Fan> fans= DataManager.getFansFromAccounts();
    if(fans==null)return;
    for(Fan fan:fans){
      if(fan.isGetMatchNotifications())
        fan.addAlert(new Alert(match.getAwayTeam().getName()+" against "+match.getHomeTeam().getName()));
    }
  }

  //Need to be Changed//
  public void Report(String report){
    OurSystem.getSM().addComplain(report);
  }


  public void ShowSearchHistory(){
    Guest guestDummy=new Guest("1");
    for(String[] savedSearch:searchHistory){
      guestDummy.Search(savedSearch[0],savedSearch[1]);
    }
  }

  public void ShowPersonalInfo(){
    System.out.println("Name:");
    System.out.println(getName());
    List<Team> teams=new LinkedList<>();
    List<Player> players=new LinkedList<>();
    List<Coach> coaches=new LinkedList<>();
    for(Page page:pages){
      if(page.getType() instanceof Team)
        teams.add((Team)page.getType());
      if(page.getType() instanceof Player)
        players.add((Player)page.getType());
      if(page.getType() instanceof Coach)
        coaches.add((Coach)page.getType());
    }
    Guest guestDummy=new Guest("1");
    System.out.println("Teams Tracked:");
    for(Team team:teams)
      team.ShowTeam();
    System.out.println("Players Tracked:");
    for(Player player:players)
      player.ShowPlayer();
    System.out.println("Coaches Tracked:");
    for(Coach coach:coaches)
      coach.ShowCoach();
  }

  public void EditPersonalInfo(String newName){
    setName(newName);
  }

}