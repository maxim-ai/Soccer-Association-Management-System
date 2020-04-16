
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class LeagueTests {
    League league = new League("first league");


    @Test
    public void addSLsettingsToSeason() {
        Season season = new Season("2019");
        SLsettings sLsetting = new SLsettings(new Policy("point","game"));
        HashMap<Season,SLsettings> sLsettings = league.getsLsetting();
        league.addSLsettingsToSeason(season,sLsetting);
        assertEquals(sLsetting,league.getSLsettingsBySeason(season));
        assertEquals(sLsetting,season.getSLsettingsByLeague(league));
    }

    @Test
    public void removeSLsettingsFromSeason() {
        Season season = new Season("2019");
        SLsettings sLsetting = new SLsettings(new Policy("point","game"));
        HashMap<Season,SLsettings> sLsettings = league.getsLsetting();
        league.addSLsettingsToSeason(season,sLsetting);
        league.removeSLsettingsFromSeason(season,true);
        assertEquals(0,league.getsLsetting().size());
    }

}
