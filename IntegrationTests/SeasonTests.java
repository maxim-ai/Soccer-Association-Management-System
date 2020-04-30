
import BusinessLayer.OtherCrudOperations.League;
import BusinessLayer.OtherCrudOperations.Policy;
import BusinessLayer.OtherCrudOperations.SLsettings;
import BusinessLayer.OtherCrudOperations.Season;
import BusinessLayer.DataController;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class SeasonTests {
    Season season = new Season("2019");

    @Before
    public void setUp(){
        DataController.clearDataBase();
    }

    @Test
    public void addSLsettingsToLeague() {
        League league = new League("first league");
        SLsettings sLsetting = new SLsettings(new Policy("point","game"));
        HashMap<League, SLsettings> sLsettings = season.getsLsettings();
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
