
import Server.BusinessLayer.DataController;
import Server.BusinessLayer.OtherCrudOperations.*;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class LeagueTest {
    League league = new League("first league");

    @Before
    public void setUp(){
        DataController.getInstance().clearDataBase();
    }

    @Test
    public void getsLsetting() {
        HashMap<Season,SLsettings> sLsetting = new HashMap<>();
        league.setsLsetting(sLsetting);
        assertEquals(sLsetting,league.getsLsetting());
    }

    @Test
    public void setsLsetting() {
        HashMap<Season, SLsettings> sLsetting = new HashMap<>();
        league.setsLsetting(sLsetting);
        assertEquals(sLsetting,league.getsLsetting());
    }

    @Test
    public void setName() {
        league.setName("first league");
        assertEquals("first league",league.getName());
    }

    @Test
    public void getName() {
        assertEquals("first league",league.getName());
    }

    @Test
    public void setTeam() {
        List<Team> teams = new LinkedList<>();
        league.setTeams(teams);
        assertEquals(teams,league.getTeams());
    }

    @Test
    public void getTeams() {
        List<Team> teams = new LinkedList<>();
        league.setTeams(teams);
        assertEquals(teams,league.getTeams());
    }

    @Test
    public void getTeam() {
        Team team = new Team("Maccbi",league,new Stadium("bloomfield"));
        league.addTeam(team);
        assertEquals(team,league.getTeam(0));

    }

        @Test
    public void numberOfTeams() {
        Team team = new Team("Maccbi",league,new Stadium("bloomfield"));
        league.addTeam(team);
        assertEquals(1,league.numberOfTeams());
    }

    @Test
    public void hasTeams() {
        Team team = new Team("Maccbi",league,new Stadium("bloomfield"));
        league.addTeam(team);
        assertEquals(true,league.hasTeams());
    }

    @Test
    public void getSLsettingsBySeason() {
        Season season = new Season("2019");
        SLsettings sLsetting = new SLsettings(new Policy("point","game"));
        HashMap<Season,SLsettings> sLsettings = league.getsLsetting();
        sLsettings.put(season,sLsetting);
        assertEquals(sLsetting,league.getSLsettingsBySeason(season));
    }

    @Test
    public void minimumNumberOfTeams() {
        assertEquals(2,league.minimumNumberOfTeams());
    }

    @Test
    public void addTeam() {
        Team team = new Team("Maccbi",league,new Stadium("Bloomfield"));
        league.addTeam(team);
        assertEquals(team,league.getTeam(0));
    }

    @Test
    public void removeTeam() {
        Team team = new Team("Maccbi",league,new Stadium("Bloomfield"));
        league.addTeam(team);
        league.removeTeam(team);
        assertEquals(0,league.getTeams().size());
    }

    @Test
    public void addSLsettingsToSeason() {
        Season season = new Season("2019");
        SLsettings sLsetting = new SLsettings(new Policy("point","game"));
        HashMap<Season,SLsettings> sLsettings = league.getsLsetting();
        addSLsettingsToSeason(season,sLsetting);
        assertEquals(sLsetting,league.getSLsettingsBySeason(season));
        assertEquals(sLsetting,season.getSLsettingsByLeague(league));
    }

    public boolean addSLsettingsToSeason(Season aSeason,SLsettings aSlSLsettings)
    {
        league.getsLsetting().put(aSeason,aSlSLsettings);
        if(!aSeason.hasPolicy(league,aSlSLsettings)){
            addSLsettingsToLeagueStub(league,aSlSLsettings,aSeason);
        }
        return true;
    }

    public boolean addSLsettingsToLeagueStub(League aLeague, SLsettings asLsettings, Season season)
    {
        season.getsLsettings().put(aLeague,asLsettings);
        if(!aLeague.hasPolicy(season,asLsettings)){
            addSLsettingsToSeason(season, asLsettings);
        }
        return true;
    }


    @Test
    public void removeSLsettingsFromSeason() {
        Season season = new Season("2019");
        SLsettings sLsetting = new SLsettings(new Policy("point","game"));
        HashMap<Season,SLsettings> sLsettings = league.getsLsetting();
        league.addSLsettingsToSeason(season,sLsetting);
        removeSLsettingsFromSeason(season,true);
        assertEquals(0,league.getsLsetting().size());
    }

    public boolean removeSLsettingsFromSeason(Season aSeason, boolean bool)
    {
        if (!league.getsLsetting().containsKey(aSeason)) {
            return true;
        }
        if(aSeason.hasPolicy(league,league.getsLsetting().get(aSeason)) && bool){
            removeSLsettingsFromLeagueStub(league,false,aSeason);
        }
        league.getsLsetting().remove(aSeason);
        return true;
    }

    public boolean removeSLsettingsFromLeagueStub(League aLeague,Boolean bool,Season season)
    {
        if (!season.getsLsettings().containsKey(aLeague)) {
            return true;
        }
        if(aLeague.hasPolicy(season,season.getsLsettings().get(aLeague)) && bool){
            aLeague.removeSLsettingsFromSeason(season,false);
        }
        season.getsLsettings().remove(aLeague);
        return true;
    }

    @Test
    public void deleteSeason() {
        Season season = new Season("2019");
        SLsettings sLsetting = new SLsettings(new Policy("point","game"));
        HashMap<Season,SLsettings> sLsettings = league.getsLsetting();
        sLsettings.put(season,sLsetting);
        league.deleteSeason(season,sLsetting);
        assertEquals(null,league.getSLsettingsBySeason(season));

    }

    @Test
    public void hasPolicy() {
        Season season = new Season("2019");
        SLsettings sLsetting = new SLsettings(new Policy("point","game"));
        HashMap<Season,SLsettings> sLsettings = league.getsLsetting();
        sLsettings.put(season,sLsetting);
        assertEquals(true, league.hasPolicy(season,sLsetting));

    }

    @Test
    public void showLeague() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        league.ShowLeague();
        String expectedOutput  = "Name:\r\nfirst league\r\n\r\nTeams in league:\r\n\r\n";
        assertEquals(expectedOutput, outContent.toString());;

    }
}
