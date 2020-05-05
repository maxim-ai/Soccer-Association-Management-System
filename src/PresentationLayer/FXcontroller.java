package PresentationLayer;

import BusinessLayer.DataController;
import BusinessLayer.OtherCrudOperations.*;
import BusinessLayer.Pages.Page;
import BusinessLayer.RoleCrudOperations.*;
import ServiceLayer.GuestController.GuestController;
import ServiceLayer.OurSystem;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.util.Pair;

//import java.awt.*;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.Time;
import java.util.*;
import java.util.List;
import java.util.ResourceBundle;


public class FXcontroller implements Initializable {

    @FXML
    public AnchorPane starter_pane;
    public AnchorPane login_pane;
    public AnchorPane options_pane;

    public TableView<String> options_table;
    public TextField username_input;
    public TextField password_input;
    public Button login_button;
    public Button submit_button;
    public TableColumn<String,String> col_options;
    public Button runMethod_button;
    public TableView<String> alerts_table;
    public TableColumn<String,String> col_alerts;


    OurSystem ourSystem;
    List controllers=new ArrayList();
    HashMap<String, Pair<Method,List<String>>> functionsToUser=new HashMap<>(); //key: name of option for user
                                                                                //value: pairKey:actual method
                                                                                //       pairValue: list of args for method

    public void chooseLogin()
    {
        starter_pane.setVisible(false);
        login_pane.setVisible(true);
    }

    public void logIn() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String userName=username_input.getText();
        String password=password_input.getText();
        GuestController guestController= OurSystem.makeGuestController();
        ArrayList<Object> controllerList= (ArrayList<Object>) guestController.LogIn(userName,password);
        if(controllerList.size()==1 && controllerList.get(0) instanceof String)
        {
            showAlert((String)controllerList.get(0), Alert.AlertType.WARNING);
            return;
        }
        controllers=controllerList;
        login_pane.setVisible(false);
        options_pane.setVisible(true);
        createOptionsForUser();
        setAlerts();
    }

    public void createOptionsForUser() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List options=new ArrayList();
        for(Object o:controllers)
        {
            Method method=o.getClass().getDeclaredMethod("showUserMethods");
            HashMap<String, Pair<Method,List<String>>> returnedMap= (HashMap<String, Pair<Method, List<String>>>) method.invoke(o);
            for(Map.Entry<String, Pair<Method,List<String>>> entry:returnedMap.entrySet())
            {
                functionsToUser.put(entry.getKey(),entry.getValue());
                options.add(entry.getKey());
            }
            //options= (List) ((HashMap<String, Pair<Method,List<String>>>)method.invoke(o)).keySet();
        }

        ObservableList<String> details = FXCollections.observableArrayList(options);
        col_options.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        options_table.setItems(details);

    }

    public void executeOption() throws InvocationTargetException, IllegalAccessException {
        String choice=options_table.getSelectionModel().getSelectedItem();
        List<String> arguments=new ArrayList<>();
        Pair<Method, List<String>> methodPair=functionsToUser.get(choice);
        for(String arg:methodPair.getValue())
        {
            Dialog dialog;
            if(arg.contains("@"))
            {
                List<String> choices = new ArrayList<>();
                List<String> dropDownFunc = OurSystem.getDropList(arg.substring(arg.indexOf("@")+1),controllers,arguments);
                for(String pick:dropDownFunc)
                {
                    choices.add(pick);
                }
                dialog=new ChoiceDialog<String>("", choices);
                dialog.setHeaderText("list Input");
                dialog.setContentText(arg.substring(0,arg.indexOf("@"))+":");
            }
            else
            {
                dialog = new TextInputDialog("");
                dialog.setHeaderText("Text Input");
                dialog.setContentText(arg+":");
            }
            dialog.setTitle("");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                arguments.addAll(Collections.singleton(result.get()));
            }
            else
            {
                showAlert("Operation Cancelled", Alert.AlertType.INFORMATION);
                return;
            }
        }
        String answer= (String) methodPair.getKey().invoke(getControllerByMethod(methodPair.getKey()),arguments.toArray());
        showAlert(answer, Alert.AlertType.INFORMATION);
    }





    /**
     * The function shows an alert with the given text
     * @param context
     */
    public void showAlert(String context, Alert.AlertType type)
    {
        Alert a=new Alert(type);
        a.setContentText(context);
        a.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            if(!(new File("firstInitCheck")).exists())
                InitDatabase();
        } catch (Exception e) {
        }
        ourSystem=new OurSystem();
        ourSystem.Initialize();

    }

    private Object getControllerByMethod(Method argM){
        for(Object o:controllers){
            for(Method M:o.getClass().getDeclaredMethods()){
                if(argM.getName().equals(M.getName()))
                    return o;
            }
        }
        return null;
    }

    public void showRunQueryButton()
    {
        runMethod_button.setVisible(true);
    }


    public void setAlerts() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<String> alerts=new ArrayList();
        for(Object o:controllers)
        {
            Method method= null;
            try {
                method = o.getClass().getDeclaredMethod("getAlerts");
            }
            catch (NoSuchMethodException e) {
                continue;
            }
            alerts.addAll((List<String>) method.invoke(o));
        }
        if(alerts.size()>0)
        {
            alerts_table.setVisible(true);
            ObservableList<String> details = FXCollections.observableArrayList(alerts);
            col_alerts.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
            alerts_table.setItems(details);
        }
        else
        {
            alerts_table.setVisible(false);
        }

    }

    public void logout()
    {
        options_pane.setVisible(false);
        login_pane.setVisible(true);
        username_input.setText("");
        password_input.setText("");
    }



    public  void InitDatabase() throws Exception {
        Account arAccount1, arAccount2;
        AssociationRepresentative ar1, ar2;
        League league1, league2;
        Season season;
        Policy policy1, policy2;
        Account refAccount1, refAccount2, refAccount3, refAccount4, refAccount5, refAccount6;
        Referee referee1, referee2, referee3, referee4, referee5, referee6;
        SLsettings sLsettings1, sLsettings2;
        Account ownerAccount1, ownerAccount2, ownerAccount3, ownerAccount4;
        Owner owner1, owner2, owner3, owner4;
        Account tmAccount1, tmAccount2, tmAccount3, tmAccount4;
        TeamManager tm1, tm2, tm3, tm4;
        Account playerAccount1, playerAccount2, playerAccount3, playerAccount4, playerAccount5, playerAccount6, playerAccount7, playerAccount8;
        Player player1, player2, player3, player4, player5, player6, player7, player8;
        Match match1, match2;
        Team team1, team2, team3, team4;
        Stadium stadium1, stadium2, stadium3, stadium4;
        Account coachAccount1, coachAccount2, coachAccount3, coachAccount4;
        Coach coach1, coach2, coach3, coach4;
        Page playerPage1, playerPage2, playerPage3, playerPage4, playerPage5, playerPage6, playerPage7, playerPage8,
                coachPage1, coachPage2, coachPage3, coachPage4, teamPage1, teamPage2, teamPage3, teamPage4;
        Account fanAccount1, fanAccount2, fanAccount3, fanAccount4, fanAccount5, fanAccount6, fanAccount7, fanAccount8;
        Fan fan1, fan2, fan3, fan4, fan5, fan6, fan7, fan8;

        //region AssociationRepresantives creations
        arAccount1 = new Account("AR1", 99, "AR1X", "Password");
        ar1 = new AssociationRepresentative("AR1");
        arAccount1.addRole(ar1);

        arAccount2 = new Account("AR2", 99, "AR2X", "Password");
        ar2 = new AssociationRepresentative("AR2");
        arAccount2.addRole(ar2);
        //endregion

        //region Referees creation
        refAccount1 = new Account("Referee1", 99, "Referee1X", "Password");
        referee1 = ar1.createNewReferee(refAccount1, "Complete", "Referee1");

        refAccount2 = new Account("Referee2", 99, "Referee2X", "Password");
        referee2 = ar1.createNewReferee(refAccount2, "Complete", "Referee2");

        refAccount3 = new Account("Referee3", 99, "Referee3X", "Password");
        referee3 = ar1.createNewReferee(refAccount3, "Complete", "Referee3");

        refAccount4 = new Account("Referee4", 99, "Referee4X", "Password");
        referee4 = ar1.createNewReferee(refAccount4, "Complete", "Referee4");

        refAccount5 = new Account("Referee5", 99, "Referee5X", "Password");
        referee5 = ar1.createNewReferee(refAccount5, "Complete", "Referee5");

        refAccount6 = new Account("Referee6", 99, "Referee6X", "Password");
        referee6 = ar1.createNewReferee(refAccount6, "Complete", "Referee6");

        //endregion

        //region Leagues and seasons creation
        league1 = new League("League1");
        league2 = new League("League2");
        season = new Season("BusinessLayer.OtherCrudOperations.Season");
        policy1 = new Policy("Standart", "aaa");
        policy2 = new Policy("Double", "bbb");
        sLsettings1 = new SLsettings(policy1);
        sLsettings1.addReferee(referee1);
        sLsettings1.addReferee(referee2);
        sLsettings1.addReferee(referee3);
        sLsettings2 = new SLsettings(policy2);
        sLsettings2.addReferee(referee4);
        sLsettings2.addReferee(referee5);
        sLsettings2.addReferee(referee6);
        league1.addSLsettingsToSeason(season, sLsettings1);
        league2.addSLsettingsToSeason(season, sLsettings2);
        season.addSLsettingsToLeague(league1, sLsettings1);
        season.addSLsettingsToLeague(league2, sLsettings2);
        referee1.setsLsettings(sLsettings1);
        referee2.setsLsettings(sLsettings1);
        referee3.setsLsettings(sLsettings1);
        referee4.setsLsettings(sLsettings2);
        referee5.setsLsettings(sLsettings2);
        referee6.setsLsettings(sLsettings2);
        referee1.addLeague(league1, season);
        referee2.addLeague(league1, season);
        referee3.addLeague(league1, season);
        referee4.addLeague(league2, season);
        referee5.addLeague(league2, season);
        referee6.addLeague(league2, season);
        policy1.setsLsettings(sLsettings1);
        policy2.setsLsettings(sLsettings2);

        //endregion  //

        //region BusinessLayer.OtherCrudOperations.Stadium creation
        stadium1 = new Stadium("Stadium1");
        stadium2 = new Stadium("Stadium2");
        stadium3 = new Stadium("Stadium3");
        stadium4 = new Stadium("Stadium4");
        //endregion

        //region Teams creation
        team1 = new Team("Team1", league1, stadium1);
        team2 = new Team("Team2", league1, stadium2);
        team3 = new Team("Team3", league2, stadium3);
        team4 = new Team("Team4", league2, stadium4);
        //endregion

        //region BusinessLayer.RoleCrudOperations.Owner creation
        ownerAccount1 = new Account("Owner1", 99, "Owner1X", "Password");
        owner1 = new Owner("Owner1", team1, null);
        ownerAccount1.addRole(owner1);

        ownerAccount2 = new Account("Owner2", 99, "Owner2X", "Password");
        owner2 = new Owner("Owner2", team2, null);
        ownerAccount2.addRole(owner2);

        ownerAccount3 = new Account("Owner3", 99, "Owner3X", "Password");
        owner3 = new Owner("Owner3", team3, null);
        ownerAccount3.addRole(owner3);

        ownerAccount4 = new Account("Owner4", 99, "Owner4X", "Password");
        owner4 = new Owner("Owner4", team4, null);
        ownerAccount4.addRole(owner4);
        //endregion

        //region TeamManagers creation
        tmAccount1 = new Account("TM1", 99, "TM1X", "Password");
        List<TeamManager.PermissionEnum> tm1Permissions = new ArrayList<>();
        tm1Permissions.add(TeamManager.PermissionEnum.managePlayers);
        tm1Permissions.add(TeamManager.PermissionEnum.manageMatches);
        owner1.appointTeamManagerToTeam(tmAccount1, tm1Permissions);
        tm1 = tmAccount1.checkIfTeamManagr();

        tmAccount2 = new Account("TM2", 99, "TM2X", "Password");
        List<TeamManager.PermissionEnum> tm2Permissions = new ArrayList<>();
        tm2Permissions.add(TeamManager.PermissionEnum.managePlayers);
        tm2Permissions.add(TeamManager.PermissionEnum.manageMatches);
        owner2.appointTeamManagerToTeam(tmAccount2, tm2Permissions);
        tm2 = tmAccount2.checkIfTeamManagr();

        tmAccount3 = new Account("TM3", 99, "TM3X", "Password");
        List<TeamManager.PermissionEnum> tm3Permissions = new ArrayList<>();
        tm3Permissions.add(TeamManager.PermissionEnum.managePlayers);
        tm3Permissions.add(TeamManager.PermissionEnum.manageMatches);
        owner3.appointTeamManagerToTeam(tmAccount3, tm3Permissions);
        tm3 = tmAccount3.checkIfTeamManagr();

        tmAccount4 = new Account("TM4", 99, "TM4X", "Password");
        List<TeamManager.PermissionEnum> tm4Permissions = new ArrayList<>();
        tm4Permissions.add(TeamManager.PermissionEnum.managePlayers);
        tm4Permissions.add(TeamManager.PermissionEnum.manageMatches);
        owner4.appointTeamManagerToTeam(tmAccount4, tm4Permissions);
        tm4 = tmAccount4.checkIfTeamManagr();

        //endregion

        //region Players creation
        playerAccount1 = new Account("Player1", 99, "Player1X", "Password");
        player1 = new Player("Player1", new Date(), PositionEnum.Striker, team1, null);
        playerAccount1.addRole(player1);

        playerAccount2 = new Account("Player2", 99, "Player2X", "Password");
        player2 = new Player("Player2", new Date(), PositionEnum.Striker, team1, null);
        playerAccount2.addRole(player2);

        playerAccount3 = new Account("Player3", 99, "Player3X", "Password");
        player3 = new Player("Player3", new Date(), PositionEnum.Striker, team2, null);
        playerAccount3.addRole(player3);

        playerAccount4 = new Account("Player4", 99, "Player4X", "Password");
        player4 = new Player("Player4", new Date(), PositionEnum.Striker, team2, null);
        playerAccount4.addRole(player4);

        playerAccount5 = new Account("Player5", 99, "Player5X", "Password");
        player5 = new Player("Player5", new Date(), PositionEnum.Striker, team3, null);
        playerAccount5.addRole(player5);

        playerAccount6 = new Account("Player6", 99, "Player6X", "Password");
        player6 = new Player("Player6", new Date(), PositionEnum.Striker, team3, null);
        playerAccount6.addRole(player6);

        playerAccount7 = new Account("Player7", 99, "Player7X", "Password");
        player7 = new Player("Player7", new Date(), PositionEnum.Striker, team4, null);
        playerAccount7.addRole(player7);

        playerAccount8 = new Account("Player8", 99, "Player8X", "Password");
        player8 = new Player("Player8", new Date(), PositionEnum.Striker, team4, null);
        playerAccount8.addRole(player8);
        //endregion

        //region BusinessLayer.RoleCrudOperations.Coach creation
        coachAccount1 = new Account("Coach1", 99, "Coach1X", "Password");
        coach1 = new Coach("Coach1", "Full", "Main", null);
        coachAccount1.addRole(coach1);

        coachAccount2 = new Account("Coach2", 99, "Coach2X", "Password");
        coach2 = new Coach("Coach2", "Full", "Main", null);
        coachAccount2.addRole(coach2);

        coachAccount3 = new Account("Coach3", 99, "Coach3X", "Password");
        coach3 = new Coach("Coach3", "Full", "Main", null);
        coachAccount3.addRole(coach3);

        coachAccount4 = new Account("Coach4", 99, "Coach4X", "Password");
        coach4 = new Coach("Coach4", "Full", "Main", null);
        coachAccount4.addRole(coach4);
        //endregion

        //region Owners, TeamManagers and Coaches teams setting
        owner1.setTeam(team1);
        owner2.setTeam(team2);
        owner3.setTeam(team3);
        owner4.setTeam(team4);
        tm1.setTeam(team1);
        tm2.setTeam(team2);
        tm3.setTeam(team3);
        tm4.setTeam(team4);
        coach1.addTeam(team1);
        coach2.addTeam(team2);
        coach3.addTeam(team3);
        coach4.addTeam(team4);
        //endregion

        //region BusinessLayer.OtherCrudOperations.Match creation
        match1 = new Match(new Date(), new Time(22, 0, 0), 0, 0, stadium1, season
                , team2, team1, null, null, null);

        match2 = new Match(new Date(), new Time(19, 0, 0), 0, 0, stadium4, season
                , team3, team4, null, null, null);
        //endregion

        //region Referess match setting
        referee1.addMatch(match1, "main");
        referee2.addMatch(match1, "line");
        referee3.addMatch(match1, "line");
        referee4.addMatch(match2, "main");
        referee5.addMatch(match2, "line");
        referee6.addMatch(match2, "line");
        //endregion

        //region Seasons matches setting
        season.addMatch(match1);
        season.addMatch(match2);
        //endregion

        //region Players teams setting
        player1.setTeam(team1);
        player2.setTeam(team1);
        player3.setTeam(team2);
        player4.setTeam(team2);
        player5.setTeam(team3);
        player6.setTeam(team3);
        player7.setTeam(team4);
        player8.setTeam(team4);
        //endregion

        //region BusinessLayer.Pages.Page creation
        playerPage1 = new Page(player1);
        playerPage2 = new Page(player2);
        playerPage3 = new Page(player3);
        playerPage4 = new Page(player4);
        playerPage5 = new Page(player5);
        playerPage6 = new Page(player6);
        playerPage7 = new Page(player7);
        playerPage8 = new Page(player8);
        coachPage1 = new Page(coach1);
        coachPage2 = new Page(coach2);
        coachPage3 = new Page(coach3);
        coachPage4 = new Page(coach4);
        teamPage1 = new Page(team1);
        teamPage2 = new Page(team2);
        teamPage3 = new Page(team3);
        teamPage4 = new Page(team4);
        //endregion

        //region BusinessLayer.RoleCrudOperations.Fan creation
        fanAccount1 = new Account("Fan1", 99, "Fan1X", "Password");
        fan1 = new Fan("Fan1");
        fanAccount1.addRole(fan1);

        fanAccount2 = new Account("Fan2", 99, "Fan2X", "Password");
        fan2 = new Fan("Fan2");
        fanAccount2.addRole(fan2);

        fanAccount3 = new Account("Fan3", 99, "Fan3X", "Password");
        fan3 = new Fan("Fan3");
        fanAccount3.addRole(fan3);

        fanAccount4 = new Account("Fan4", 99, "Fan4X", "Password");
        fan4 = new Fan("Fan4");
        fanAccount4.addRole(fan4);

        fanAccount5 = new Account("Fan5", 99, "Fan5X", "Password");
        fan5 = new Fan("Fan5");
        fanAccount5.addRole(fan5);

        fanAccount6 = new Account("Fan6", 99, "Fan6X", "Password");
        fan6 = new Fan("Fan6");
        fanAccount6.addRole(fan6);

        fanAccount7 = new Account("Fan7", 99, "Fan7X", "Password");
        fan7 = new Fan("Fan7");
        fanAccount7.addRole(fan7);

        fanAccount8 = new Account("Fan8", 99, "Fan8X", "Password");
        fan8 = new Fan("Fan8");
        fanAccount8.addRole(fan8);
        //endregion

        //region BusinessLayer.RoleCrudOperations.Fan page setting
        fan1.addPage(playerPage1);
        fan1.addPage(playerPage2);
        fan2.addPage(coachPage3);
        fan3.addPage(coachPage4);
        fan3.addPage(teamPage3);
        fan2.addPage(playerPage3);
        fan5.addPage(teamPage4);
        fan4.addPage(coachPage1);
        fan5.addPage(playerPage8);
        fan7.addPage(playerPage2);
        fan8.addPage(playerPage2);
        fan1.addPage(teamPage4);
        //endregion

        //region Datamanager adding
        DataController.addAccount(arAccount1);
        DataController.addAccount(arAccount2);
        DataController.addAccount(refAccount1);
        DataController.addAccount(refAccount2);
        DataController.addAccount(refAccount3);
        DataController.addAccount(refAccount4);
        DataController.addAccount(refAccount5);
        DataController.addAccount(refAccount6);
        DataController.addAccount(ownerAccount1);
        DataController.addAccount(ownerAccount2);
        DataController.addAccount(ownerAccount3);
        DataController.addAccount(ownerAccount4);
        DataController.addAccount(tmAccount1);
        DataController.addAccount(tmAccount2);
        DataController.addAccount(tmAccount3);
        DataController.addAccount(tmAccount4);
        DataController.addAccount(playerAccount1);
        DataController.addAccount(playerAccount2);
        DataController.addAccount(playerAccount3);
        DataController.addAccount(playerAccount4);
        DataController.addAccount(playerAccount5);
        DataController.addAccount(playerAccount6);
        DataController.addAccount(playerAccount7);
        DataController.addAccount(playerAccount8);
        DataController.addAccount(coachAccount1);
        DataController.addAccount(coachAccount2);
        DataController.addAccount(coachAccount3);
        DataController.addAccount(coachAccount4);
        DataController.addAccount(fanAccount1);
        DataController.addAccount(fanAccount2);
        DataController.addAccount(fanAccount3);
        DataController.addAccount(fanAccount4);
        DataController.addAccount(fanAccount5);
        DataController.addAccount(fanAccount6);
        DataController.addAccount(fanAccount7);
        DataController.addAccount(fanAccount8);
        DataController.addLeague(league1);
        DataController.addLeague(league2);
        DataController.addSeason(season);
        DataController.addStadium(stadium1);
        DataController.addStadium(stadium2);
        DataController.addStadium(stadium3);
        DataController.addStadium(stadium4);
        DataController.addTeam(team1);
        DataController.addTeam(team2);
        DataController.addTeam(team3);
        DataController.addTeam(team4);
        match1.getEventCalender().addGameEvent(new GameEvent(EventEnum.goal,new Date(),new Time(1,1,1),"aaa",80,match1.getEventCalender()));
        owner1.addAlert(new BusinessLayer.OtherCrudOperations.Alert("bla bla bla bla"));
        owner1.addAlert(new BusinessLayer.OtherCrudOperations.Alert("tralalalala"));
        //end
    }
}
