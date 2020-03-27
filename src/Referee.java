/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4811.445d1d99b modeling language!*/


import java.util.*;

// line 138 "model.ump"
// line 292 "model.ump"
public class Referee
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Referee Attributes
  private String training;

  //Referee Associations
  private List<League> leagues;
  private List<Match> matchs;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Referee(String aTraining)
  {
    training = aTraining;
    leagues = new ArrayList<League>();
    matchs = new ArrayList<Match>();
  }

  //------------------------
  // INTERFACE
  //------------------------

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
  public static int minimumNumberOfMatchs()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
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
  /* Code from template association_RemoveMany */
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
  /* Code from template association_AddIndexControlFunctions */
  public boolean addMatchAt(Match aMatch, int index)
  {  
    boolean wasAdded = false;
    if(addMatch(aMatch))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMatchs()) { index = numberOfMatchs() - 1; }
      matchs.remove(aMatch);
      matchs.add(index, aMatch);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveMatchAt(Match aMatch, int index)
  {
    boolean wasAdded = false;
    if(matchs.contains(aMatch))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMatchs()) { index = numberOfMatchs() - 1; }
      matchs.remove(aMatch);
      matchs.add(index, aMatch);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addMatchAt(aMatch, index);
    }
    return wasAdded;
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
      if (aMatch.numberOfReferees() <= Match.minimumNumberOfReferees())
      {
        aMatch.delete();
      }
      else
      {
        aMatch.removeReferee(this);
      }
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "training" + ":" + getTraining()+ "]";
  }
}