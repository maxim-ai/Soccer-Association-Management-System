import java.util.*;


public class Referee
{

  private String training;
  private List<League> leagues;
  private List<Match> matchs;

  public Referee(String aTraining)
  {
    training = aTraining;
    leagues = new ArrayList<League>();
    matchs = new ArrayList<Match>();
  }

  public boolean setTraining(String aTraining)
  {
    boolean wasSet = false;
    training = aTraining;
    wasSet = true;
    return wasSet;
  }

  public String getTraining()
  {
    return training;
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

  public static int minimumNumberOfLeagues()
  {
    return 0;
  }

  public boolean addLeague(League aLeague)
  {
    boolean wasAdded = false;
    if (leagues.contains(aLeague)) { return false; }
    leagues.add(aLeague);
    if (aLeague.indexOfReferee(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aLeague.addReferee(this);
      if (!wasAdded)
      {
        leagues.remove(aLeague);
      }
    }
    return wasAdded;
  }

  public boolean removeLeague(League aLeague)
  {
    boolean wasRemoved = false;
    if (!leagues.contains(aLeague))
    {
      return wasRemoved;
    }

    int oldIndex = leagues.indexOf(aLeague);
    leagues.remove(oldIndex);
    if (aLeague.indexOfReferee(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aLeague.removeReferee(this);
      if (!wasRemoved)
      {
        leagues.add(oldIndex,aLeague);
      }
    }
    return wasRemoved;
  }

  public static int minimumNumberOfMatchs()
  {
    return 0;
  }

  public boolean addMatch(Match aMatch)
  {
    boolean wasAdded = false;
    if (matchs.contains(aMatch)) { return false; }
    matchs.add(aMatch);
    if (aMatch.indexOfReferee(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aMatch.addReferee(this);
      if (!wasAdded)
      {
        matchs.remove(aMatch);
      }
    }
    return wasAdded;
  }

  public boolean removeMatch(Match aMatch)
  {
    boolean wasRemoved = false;
    if (!matchs.contains(aMatch))
    {
      return wasRemoved;
    }

    int oldIndex = matchs.indexOf(aMatch);
    matchs.remove(oldIndex);
    if (aMatch.indexOfReferee(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aMatch.removeReferee(this);
      if (!wasRemoved)
      {
        matchs.add(oldIndex,aMatch);
      }
    }
    return wasRemoved;
  }

  public void delete()
  {
    ArrayList<League> copyOfLeagues = new ArrayList<League>(leagues);
    leagues.clear();
    for(League aLeague : copyOfLeagues)
    {
      aLeague.removeReferee(this);
    }
    ArrayList<Match> copyOfMatchs = new ArrayList<Match>(matchs);
    matchs.clear();
    for(Match aMatch : copyOfMatchs)
    {
      aMatch.removeReferee(this);
    }

  }
  public String toString()
  {
    return super.toString() + "["+
            "training" + ":" + getTraining()+ "]";
  }
}