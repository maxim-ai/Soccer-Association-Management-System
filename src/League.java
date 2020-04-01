import java.util.*;

public class League
{

  private String name;
  private List<Team> teams;
  private HashMap<Season,SLsettings> sLsetting;

  public League(String aName) {
    name = aName;
    teams = new ArrayList<Team>();
    sLsetting = new HashMap<>();
  }


  public HashMap<Season, SLsettings> getsLsetting() {
    return sLsetting;
  }

  public void setsLsetting(HashMap<Season, SLsettings> sLsetting) {
    this.sLsetting = sLsetting;
  }


  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
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

  public SLsettings getSLsettingsBySeason(Season season)
  {
    SLsettings asLsettings = sLsetting.get(season);
    return asLsettings;
  }

  public static int minimumNumberOfTeams() { return 2;}

  public boolean addTeam(Team aTeam)
  {
    boolean wasAdded = true;
    if (teams.contains(aTeam)) { return true; }
    League existingLeague = aTeam.getLeague();
    boolean isNewLeague = existingLeague != null && !this.equals(existingLeague);
    if (isNewLeague)
    {
      aTeam.setLeague(this);
    }
    else
    {
      teams.add(aTeam);
    }
    wasAdded = true;
    return wasAdded;
  }

  /**
   * removes team from list, makes league field in that team null
   */
  public boolean removeTeam(Team aTeam)
  {
    boolean wasRemoved = false;
    //Unable to remove aTeam, as it must always have a league
    if (!this.equals(aTeam.getLeague()))
    {
      teams.remove(aTeam);
      aTeam.setLeague(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addSLsettingsToSeason(Season aSeason,SLsettings aSlSLsettings)
  {
    sLsetting.put(aSeason,aSlSLsettings);
    if(!aSeason.hasPolicy(this,aSlSLsettings)){
      aSeason.addSLsettingsToLeague(this,aSlSLsettings);
    }
    return true;
  }

  /**
   * remove the policy of the season
   */
  public boolean removeSLsettingsFromSeason(Season aSeason)
  {
    if (!sLsetting.containsKey(aSeason)) {
      return true;
    }
    if(aSeason.hasPolicy(this,sLsetting.get(aSeason))){
      aSeason.removeSLsettingsFromLeague(this);
    }
    sLsetting.remove(aSeason);
    return true;
  }


  public void delete()
  {
    for(int i=teams.size(); i > 0; i--)
    {
      Team aTeam = teams.get(i - 1);
      aTeam.removeLeauge(this);
    }

    for(Map.Entry <Season,SLsettings> seasonPolicyEntry : sLsetting.entrySet())
    {
      seasonPolicyEntry.getKey().deleteLeague(this);
    }

  }

  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "]";
  }


  public boolean hasPolicy(Season season, SLsettings AslSLsettings) {
    if(sLsetting.containsKey(season)){
      if (sLsetting.get(season).equals(AslSLsettings)) {
        return true;
      }
    }
    return false;
  }


  public void deleteSeason(Season season) {
    if(sLsetting.containsKey(season)){
      if(sLsetting.get(season).equals(season)){
        sLsetting.remove(season);
      }
    }
  }

  public void ShowLeague() {

    System.out.println("Name:");
    System.out.println(this.getName());
    System.out.println();
    System.out.println("Teams in league:");
    for(Team team:this.getTeams())
      System.out.println(team.getName());
    System.out.println();
  }


}