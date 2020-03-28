import java.util.*;
import java.sql.Date;
import java.sql.Time;


public class Season
{


  private String name;
  private List<Match> matchs;
  private HashMap<League,Policy> policies;

  public Season(String aName)
  {
    name = aName;
    policies = new HashMap<>();
    matchs = new ArrayList<Match>();
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

  public Policy getPolicyByLeague(League aLeague)
  {
    Policy policy = policies.get(aLeague);
    return policy;
  }

  public Match getMatch(int index)
  {
    Match aMatch = matchs.get(index);
    return aMatch;
  }

  public List<Match> getMatchs()
  {
    List<Match> newMatchs = Collections.unmodifiableList(matchs);
    return newMatchs;
  }

  public int numberOfMatchs()
  {
    int number = matchs.size();
    return number;
  }

  public boolean hasMatchs()
  {
    boolean has = matchs.size() > 0;
    return has;
  }

  public int indexOfMatch(Match aMatch)
  {
    int index = matchs.indexOf(aMatch);
    return index;
  }

  public boolean addPolicyToLeague(League aLeague,Policy aPolicy)
  {
    policies.put(aLeague,aPolicy);
    if(!aLeague.hasPolicy(this,aPolicy)){
      aLeague.addPolicyToSeason(this, aPolicy);
    }
    return true;
  }

  public boolean removePolicyFromLeague(League aLeague)
  {
    if (!policies.containsKey(aLeague)) {
      return true;
    }
    if(aLeague.hasPolicy(this,policies.get(aLeague))){
      aLeague.removePolicyFromSeason(this);
    }
    policies.remove(aLeague);
    return true;
  }

  public boolean addMatch(Match aMatch)
  {
    boolean wasAdded = false;
    if (matchs.contains(aMatch)) { return false; }
    Season existingSeason = aMatch.getSeason();
    boolean isNewSeason = existingSeason != null && !this.equals(existingSeason);
    if (isNewSeason)
    {
      aMatch.setSeason(this);
    }
    else
    {
      matchs.add(aMatch);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeMatch(Match aMatch)
  {
    boolean wasRemoved = true;
    //Unable to remove aMatch, as it must always have a season
    if (!this.equals(aMatch.getSeason()))
    {
      matchs.remove(aMatch);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public void delete()
  {
    for(Map.Entry <League,Policy> leaguePolicyEntry : policies.entrySet())
    {
      leaguePolicyEntry.getKey().deleteSeason(this);
    }
    for(int i=matchs.size(); i > 0; i--)
    {
      Match aMatch = matchs.get(i - 1);
      aMatch.removeSeason();
    }
  }

  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName();
  }

  public boolean hasPolicy(League league, Policy aPolicy) {
    if(policies.containsKey(league)){
      if (policies.get(league).equals(aPolicy)) {
        return true;
      }
    }
    return false;
  }

  public void deleteLeague(League league) {

    if(policies.containsKey(league)){
      if(policies.get(league).equals(league)){
        policies.remove(league);
      }
    }

  }
}