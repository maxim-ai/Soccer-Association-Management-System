import java.util.*;


public class Referee extends Role
{

  private String training;
  private List<League> leagues;
  private List<Match> matchs;

  public Referee(String aTraining, String aName)
  {
    super(aName);
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
    boolean wasAdded = true;
    if (leagues.contains(aLeague)) { return true; }
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
    boolean wasRemoved = true;
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

  /**
   * add match to referee, is the match is full return false
   * @param aMatch
   * @param mainORline
   * @return
   */
  public boolean addMatch(Match aMatch, String mainORline)
  {
    boolean wasAdded = false;
    if (matchs.contains(aMatch)) { return false; }

    if(mainORline.equalsIgnoreCase("main"))
    {
      if(aMatch.getMainReferee()==null)
        aMatch.setMainReferee(this);
      else
        return false;
    }
    else if(mainORline.equalsIgnoreCase("line"))
    {

      if(aMatch.getLineRefereeOne()==null)
        aMatch.setLineRefereeOne(this);
      else if(aMatch.getLineRefereeTwo()==null)
        aMatch.setLineRefereeTwo(this);
      else
        return false;
    }
    matchs.add(aMatch);
    return wasAdded;
  }

  /**
   * remove match from referee, remove referee from match
   * @param aMatch
   * @return
   */
  public boolean removeMatch(Match aMatch)
  {
    boolean wasRemoved = true;
    if (!matchs.contains(aMatch))
    {
      return wasRemoved;
    }

    int oldIndex = matchs.indexOf(aMatch);
    matchs.remove(oldIndex);

    if(aMatch.getMainReferee().equals(this))
      aMatch.setMainReferee(null);
    else if(aMatch.getLineRefereeOne().equals(this))
      aMatch.setLineRefereeOne(null);
    else if(aMatch.getLineRefereeTwo().equals(this))
      aMatch.setLineRefereeTwo(null);

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
      if(aMatch.getMainReferee().equals(this))
        aMatch.setMainReferee(null);
      else if(aMatch.getLineRefereeOne().equals(this))
        aMatch.setLineRefereeOne(null);
      else if(aMatch.getLineRefereeTwo().equals(this))
        aMatch.setLineRefereeTwo(null);
    }

  }
  public String toString()
  {
    return super.toString() + "["+
            "training" + ":" + getTraining()+ "]";
  }
}