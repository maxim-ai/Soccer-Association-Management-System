import java.io.IOException;
import java.sql.Time;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class Referee extends Role
{

  private String training;
  private HashMap<League,Season> leagues;
  private List<Match> matchs;
  private SLsettings sLsettings;



  public Referee(String aTraining, String aName)
  {
    super(aName);
    training = aTraining;
    //leagues = new ArrayList<League>();
    leagues = new HashMap<>();
    matchs = new ArrayList<Match>();
  }

  public SLsettings getsLsettings() {
    return sLsettings;
  }

  public void setsLsettings(SLsettings sLsettings) {
    this.sLsettings = sLsettings;
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

  public void setMatchs(List<Match> matchs) {
    this.matchs = matchs;
  }

  public List<Match> getMatchs() {
    return matchs;
  }

  public HashMap<League, Season> getLeagues() {
    return leagues;
  }

  public void setLeagues(HashMap<League, Season> leagues) {
    this.leagues = leagues;
  }

  public static int minimumNumberOfLeagues()
  {
    return 0;
  }

  public boolean addLeague(League aLeague,Season aSeason)
  {
    leagues.put(aLeague,aSeason);
    if(!aSeason.hasLeague(aLeague)){
      aSeason.addSLsettingsToLeague(aLeague, sLsettings);
    }
    return true;
  }

  /**
   * remove the policy of the season
   */
  public boolean removeLeague(League league, Season aSeason)
  {
    if (!leagues.containsKey(aSeason)) {
      return true;
    }
    leagues.remove(aSeason);
    return true;
  }

  public static int minimumNumberOfMatchs()
  {
    return 0;
  }

  /**
   * add match to referee, if the match is full return false
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
      if(aMatch.getMainReferee()==null) {
        aMatch.setMainReferee(this);
        wasAdded=true;
      }
      else
        return false;
    }
    else if(mainORline.equalsIgnoreCase("line"))
    {

      if(aMatch.getLineRefereeOne()==null) {
        aMatch.setLineRefereeOne(this);
        wasAdded=true;
      }
      else if(aMatch.getLineRefereeTwo()==null) {
        aMatch.setLineRefereeTwo(this);
        wasAdded=true;
      }
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
  /*
  `UC-10.1 update details
   */
  public void updateDetails(String name){
    String before=super.getName();
    super.setName(name);
    try {
      Logger.getInstance().writeNewLine("Referee "+before+" update name to: "+super.getName());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  /*
  UC-10.2 display all matches()
   */
  public void displayAllMatches() {
    if (!matchs.isEmpty()) {
      for (Match m:matchs
      ) {
        m.ShowMatch();
        System.out.println();
      }
      try {
        Logger.getInstance().writeNewLine("Referee "+super.getName()+" watch all his Matches");
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else System.out.println("No matches!");
  }
  /*
  UC-10.3 update event during match
   */
  public void updateEventDuringMatch(Match match,EventEnum aType, String aDescription)
  {

    if(matchs.contains(match)){
      Date currDate=new Date(System.currentTimeMillis());
      long diff=getDateDiff(match.getDate(),currDate,TimeUnit.MINUTES);
      if (getDateDiff(match.getDate(),currDate,TimeUnit.MINUTES)<90)
      {
        Time currTime=new Time(Calendar.getInstance().getTimeInMillis());
        GameEvent event=new GameEvent(aType,currDate,currTime,aDescription,(int)(currTime.getTime()-match.getTime().getTime()),match.getEventCalender());
        match.getEventCalender().addGameEvent(event);
        try {
          Logger.getInstance().writeNewLine("Referee "+super.getName()+" update event during the match between: "+match.getHomeTeam().getName()+","+match.getAwayTeam().getName()+" to "+event.getType());
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      System.out.println("This match already end!");
    }
    else System.out.println("The referee is not associated with the game");
  }

  public void ShowReferee() {
    System.out.println("Name:");
    System.out.println(this.getName());
    System.out.println();
    System.out.println("Training:");
    System.out.println(this.getTraining());
    System.out.println();
    System.out.println("Matches judged:");
    for(Match match:this.getMatchs())
      match.ShowMatch();
  }


  public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
    long diffInMillies = date2.getTime() - date1.getTime();
    return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
  }

}