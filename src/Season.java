import java.util.*;
import java.sql.Date;
import java.sql.Time;


public class Season
{
  private String name;
  private List<Match> matchs;
  private HashMap<League,SLsettings> sLsettings;

  public Season(String aName)
  {
    name = aName;
    sLsettings = new HashMap<>();
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

  public SLsettings getSLsettingsByLeague(League aLeague)
  {
    SLsettings sLsetting = sLsettings.get(aLeague);
    return sLsetting;
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

  public boolean addSLsettingsToLeague(League aLeague,SLsettings asLsettings)
  {
    sLsettings.put(aLeague,asLsettings);
    if(!aLeague.hasPolicy(this,asLsettings)){
      aLeague.addSLsettingsToSeason(this, asLsettings);
    }
    return true;
  }

  public boolean removeSLsettingsFromLeague(League aLeague)
  {
    if (!sLsettings.containsKey(aLeague)) {
      return true;
    }
    if(aLeague.hasPolicy(this,sLsettings.get(aLeague))){
      aLeague.removeSLsettingsFromSeason(this);
    }
    sLsettings.remove(aLeague);
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
    for(Map.Entry <League,SLsettings> leaguePolicyEntry : sLsettings.entrySet())
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

  public boolean hasPolicy(League league, SLsettings AslSLsettings) {
    if(sLsettings.containsKey(league)){
      if (sLsettings.get(league).equals(AslSLsettings)) {
        return true;
      }
    }
    return false;
  }

  public void deleteLeague(League league) {

    if(sLsettings.containsKey(league)){
      if(sLsettings.get(league).equals(league)){
        sLsettings.remove(league);
      }
    }

  }

  public boolean hasLeague(League league){
    if(sLsettings.containsKey(league)){
      return true;
    }
    return false;
  }
}