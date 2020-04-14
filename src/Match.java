import java.io.Serializable;
import java.util.Date;
import java.sql.Time;
import java.util.*;

public class Match implements Serializable
{

  private Date date;
  private Time time;
  private int awayScore;
  private int homeScore;
  private Team awayTeam;
  private Team homeTeam;
  private Referee mainReferee;
  private Referee lineRefereeOne;
  private Referee lineRefereeTwo;
  private Stadium stadium;
  private Season season;
  private EventCalender eventCalender;

  public Match(Date aDate, Time aTime, int aAwayScore, int aHomeScore, Stadium aStadium, Season aSeason, Team awayTeam,Team homeTeam, Referee mainReferee,Referee lineRefereeOne,Referee lineRefereeTwo)
  {
    date = aDate;
    time = aTime;
    awayScore = aAwayScore;
    homeScore = aHomeScore;
    setAwayTeam(awayTeam);
    setHomeTeam(homeTeam);
    this.homeTeam = homeTeam;
    this.mainReferee= mainReferee;
    this.lineRefereeOne = lineRefereeOne;
    this.lineRefereeTwo = lineRefereeTwo;
    setStadium(aStadium);
    this.season = aSeason;
    eventCalender = new EventCalender(this);

    Fan.notifyFansAboutMatch(this);
  }

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    Alert.notifyOtherRole("Game between "+awayTeam.getName()+" and "+homeTeam.getName()+" update to date: "+date.toString(),mainReferee);
    Alert.notifyOtherRole("Game between "+awayTeam.getName()+" and "+homeTeam.getName()+" update to date: "+date.toString(),lineRefereeOne);
    Alert.notifyOtherRole("Game between "+awayTeam.getName()+" and "+homeTeam.getName()+" update to date: "+date.toString(),lineRefereeTwo);
    wasSet = true;
    return wasSet;
  }

  public boolean setTime(Time aTime)
  {
    boolean wasSet = false;
    time = aTime;
    Alert.notifyOtherRole("Game between "+awayTeam.getName()+" and "+homeTeam.getName()+" update to time: "+time.toString(),mainReferee);
    Alert.notifyOtherRole("Game between "+awayTeam.getName()+" and "+homeTeam.getName()+" update to time: "+time.toString(),lineRefereeOne);
    Alert.notifyOtherRole("Game between "+awayTeam.getName()+" and "+homeTeam.getName()+" update to time: "+time.toString(),lineRefereeTwo);
    wasSet = true;
    return wasSet;
  }

  public boolean setAwayScore(int aAwayScore)
  {
    boolean wasSet = false;
    awayScore = aAwayScore;
    wasSet = true;
    return wasSet;
  }

  public boolean setHomeScore(int aHomeScore)
  {
    boolean wasSet = false;
    homeScore = aHomeScore;
    wasSet = true;
    return wasSet;
  }

  public Date getDate()
  {
    return date;
  }

  public Time getTime()
  {
    return time;
  }

  public int getAwayScore()
  {
    return awayScore;
  }

  public int getHomeScore()
  {
    return homeScore;
  }

  public Team getAwayTeam() {
    return awayTeam;
  }

  public void setAwayTeam(Team awayTeam) {
    this.awayTeam = awayTeam;
    if(homeTeam==null) return;
    if(awayTeam.indexOfMatch(this)==-1)
      awayTeam.addMatch(this,"away");

  }

  public Team getHomeTeam() {
    return homeTeam;
  }

  public void setHomeTeam(Team homeTeam) {
    this.homeTeam = homeTeam;
    if(homeTeam==null) return;
    if(homeTeam.indexOfMatch(this)==-1)
      homeTeam.addMatch(this,"home");
  }

  public Referee getMainReferee() {
    return mainReferee;
  }

  public void setMainReferee(Referee mainReferee) {
    this.mainReferee = mainReferee;
  }

  public Referee getLineRefereeOne() {
    return lineRefereeOne;
  }

  public void setLineRefereeOne(Referee lineRefereeOne) {
    this.lineRefereeOne = lineRefereeOne;
  }

  public Referee getLineRefereeTwo() {
    return lineRefereeTwo;
  }

  public void setLineRefereeTwo(Referee lineRefereeTwo) {
    this.lineRefereeTwo = lineRefereeTwo;
  }

  public Stadium getStadium()
  {
    return stadium;
  }

  public Season getSeason()
  {
    return season;
  }

  public EventCalender getEventCalender()
  {
    return eventCalender;
  }

  public boolean setStadium(Stadium aStadium)
  {
    boolean wasSet = false;
    if (aStadium == null)
    {
      stadium=null;
      wasSet=true;
      return wasSet;
    }

    Stadium existingStadium = stadium;
    if (existingStadium != null && !existingStadium.equals(aStadium))
    {
      existingStadium.removeMatch(this);
    }
    stadium = aStadium;
    stadium.addMatch(this);
    if(mainReferee!=null&&lineRefereeOne!=null&lineRefereeTwo!=null) {
      Alert.notifyOtherRole("Game between " + awayTeam.getName() + " and " + homeTeam.getName() + " update to Stadium: " + stadium.getName(), mainReferee);
      Alert.notifyOtherRole("Game between " + awayTeam.getName() + " and " + homeTeam.getName() + " update to Stadium: " + stadium.getName(), lineRefereeOne);
      Alert.notifyOtherRole("Game between " + awayTeam.getName() + " and " + homeTeam.getName() + " update to Stadium: " + stadium.getName(), lineRefereeTwo);
    }
    wasSet = true;
    return wasSet;
  }

  public boolean setSeason(Season aSeason)
  {
    boolean wasSet = false;
    if (aSeason == null)
    {
      return wasSet;
    }

    Season existingSeason = season;
    season = aSeason;
    if (existingSeason != null && !existingSeason.equals(aSeason))
    {
      existingSeason.removeMatch(this);
    }
    season.addMatch(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    homeTeam.removeMatch(this);
    awayTeam.removeMatch(this);
    if(lineRefereeTwo!=null) lineRefereeTwo.removeMatch(this);
    if(lineRefereeOne!=null) lineRefereeOne.removeMatch(this);
    if(mainReferee!=null) mainReferee.removeMatch(this);
    Stadium placeholderStadium = stadium;
    this.stadium = null;
    if(placeholderStadium != null)
    {
      placeholderStadium.removeMatch(this);
    }
    Season placeholderSeason = season;
    this.season = null;
    if(placeholderSeason != null)
    {
      placeholderSeason.removeMatch(this);
    }
    EventCalender existingEventCalender = eventCalender;
    eventCalender = null;
    if (existingEventCalender != null)
    {
      existingEventCalender.delete();
    }
  }

  public String toString()
  {
    return super.toString() + "["+
            "awayScore" + ":" + getAwayScore()+ "," +
            "homeScore" + ":" + getHomeScore() +
            "  " + "date" + "=" + (getDate())+
            "  " + "time" + "=" + (getTime()) +
            "  " + "stadium = "+(getStadium())+
            "  " + "season = "+(getSeason())+
            "  " + "eventCalender = "+(getEventCalender());
  }


  public void removeSeason() {
    season = null;
  }

  public void ShowMatch(){
    System.out.println("Date:");
    System.out.println(this.getDate());
    System.out.println();
    System.out.println("Time:");
    System.out.println(this.getTime());
    System.out.println();
    System.out.println("Score:");
    System.out.println(this.getHomeTeam().getName()+": "+this.getHomeScore()+", "+this.getAwayTeam().getName()+": "+this.getAwayScore());
    System.out.println();
    System.out.println("Referees:");
    System.out.println("Main: "+this.getMainReferee().getName()+", Line1: "+this.getLineRefereeOne().getName()+", Line2: "+this.getLineRefereeTwo().getName());
    System.out.println();
    System.out.println("Stadium:");
    System.out.println(this.getStadium().getName());
    System.out.println();
    System.out.println("Season:");
    System.out.println(season.getName());
    System.out.println();
    List<GameEvent> gameEvent=eventCalender.getGameEvents();
    System.out.println("Game Events:");
    for (GameEvent event: gameEvent){
      event.getType().name();
      System.out.println(", ");
    }
  }



}