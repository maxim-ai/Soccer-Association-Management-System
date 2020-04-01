import java.sql.Date;
import java.sql.Time;
import java.util.*;

public class Match
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
    this.awayTeam = awayTeam;
    this.homeTeam = homeTeam;
    this.mainReferee= mainReferee;
    this.lineRefereeOne = lineRefereeOne;
    this.lineRefereeTwo = lineRefereeTwo;
    this.stadium = aStadium;
    this.season = aSeason;
    eventCalender = new EventCalender(this);

    Fan.notifyFansAboutMatch(this);
  }

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setTime(Time aTime)
  {
    boolean wasSet = false;
    time = aTime;
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
    stadium = aStadium;
    if (existingStadium != null && !existingStadium.equals(aStadium))
    {
      existingStadium.removeMatch(this);
    }
    stadium.addMatch(this);
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
    lineRefereeTwo.removeMatch(this);
    lineRefereeOne.removeMatch(this);
    mainReferee.removeMatch(this);
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
    System.out.println(this.getHomeTeam()+" against "+this.getAwayTeam());
  }



}