
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class SeasonTests {
    Season season = new Season("2019");

    @Test
    public void addSLsettingsToLeague() {
        League league = new League("first league");
        SLsettings sLsetting = new SLsettings(new Policy("point","game"));
        HashMap<League,SLsettings> sLsettings = season.getsLsettings();
        season.addSLsettingsToLeague(league,sLsetting);
        assertEquals(sLsetting,season.getSLsettingsByLeague(league));
        assertEquals(sLsetting,league.getSLsettingsBySeason(season));

    }

    @Test
    public void removeSLsettingsFromLeague() {

        League league = new League("first league");
        SLsettings sLsetting = new SLsettings(new Policy("point","game"));
        HashMap<League,SLsettings> SLsettings = season.getsLsettings();
        season.addSLsettingsToLeague(league,sLsetting);
        season.removeSLsettingsFromLeague(league,true);
        assertEquals(0,season.getsLsettings().size());
    }

}
