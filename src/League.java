
import java.util.*;

public class League
{
  private String name;
  private List<Team> teams;
  private List<Referee> referees;
  private HashMap<Season,Policy> policies;

  public League(String aName) {
    name = aName;
    teams = new ArrayList<Team>();
    referees = new ArrayList<Referee>();
    policies = new HashMap<>();
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

  public Policy getPolicyBySeason(Season season)
  {
    Policy policy = policies.get(season);
    return policy;
  }


  public Referee getReferee(int index)
  {
    Referee aReferee = referees.get(index);
    return aReferee;
  }

  public List<Referee> getReferees()
  {
    List<Referee> newReferees = Collections.unmodifiableList(referees);
    return newReferees;
  }

  public int numberOfReferees()
  {
    int number = referees.size();
    return number;
  }

  public boolean hasReferees()
  {
    boolean has = referees.size() > 0;
    return has;
  }

  public int indexOfReferee(Referee aReferee)
  {
    int index = referees.indexOf(aReferee);
    return index;
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

  public boolean addPolicyToSeason(Season aSeason,Policy aPolicy)
  {
    policies.put(aSeason,aPolicy);
    if(!aSeason.hasPolicy(this,aPolicy)){
      aSeason.addPolicyToLeague(this, aPolicy);
    }
    return true;
  }

  /**
   * remove the policy of the season
   */
  public boolean removePolicyFromSeason(Season aSeason)
  {
    if (!policies.containsKey(aSeason)) {
      return true;
    }
    if(aSeason.hasPolicy(this,policies.get(aSeason))){
      aSeason.removePolicyFromLeague(this);
    }
    policies.remove(aSeason);
    return true;
  }

  /**
   * adds referee to team, adds the team to the referee
   * @param aReferee
   * @return
   */
  public boolean addReferee(Referee aReferee)
  {
    boolean wasAdded = false;
    if (referees.contains(aReferee)) { return false; }
    referees.add(aReferee);
    if (aReferee.indexOfLeague(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aReferee.addLeague(this);
      if (!wasAdded)
      {
        referees.remove(aReferee);
      }
    }
    return wasAdded;
  }

  /**
   * removes referee from the team, removes the team from the referee
   * @param aReferee
   * @return
   */
  public boolean removeReferee(Referee aReferee)
  {
    boolean wasRemoved = false;
    if (!referees.contains(aReferee))
    {
      return wasRemoved;
    }

    int oldIndex = referees.indexOf(aReferee);
    referees.remove(oldIndex);
    if (aReferee.indexOfLeague(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aReferee.removeLeague(this);
      if (!wasRemoved)
      {
        referees.add(oldIndex,aReferee);
      }
    }
    return wasRemoved;
  }

  public void delete()
  {
    for(int i=teams.size(); i > 0; i--)
    {
      Team aTeam = teams.get(i - 1);
      aTeam.removeLeauge(this);
    }

    for(Map.Entry <Season,Policy> seasonPolicyEntry : policies.entrySet())
    {
      seasonPolicyEntry.getKey().deleteLeague(this);
    }

    ArrayList<Referee> copyOfReferees = new ArrayList<Referee>(referees);
    referees.clear();
    for(Referee aReferee : copyOfReferees)
    {
      aReferee.removeLeague(this);
    }

  }

  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "]";
  }


  public boolean hasPolicy(Season season, Policy aPolicy) {
    if(policies.containsKey(season)){
      if (policies.get(season).equals(aPolicy)) {
        return true;
      }
    }
    return false;
  }


  public void deleteSeason(Season season) {
    if(policies.containsKey(season)){
      if(policies.get(season).equals(season)){
        policies.remove(season);
      }
    }
  }

}