package DataLayer;

import BusinessLayer.OtherCrudOperations.PositionEnum;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBAdapter {

    private static Connection connectToDB(){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionUrl = "jdbc:sqlserver://localhost;databaseName=DB2020;integratedSecurity=true";
            Connection con = DriverManager.getConnection(connectionUrl);
            return con;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            if(!e.getMessage().contains("Violation of PRIMARY KEY"))
                e.printStackTrace();
        }
        return null;
    }

    public static void addAccount(String userName, String password, String name, int age) {
        try {
            Connection con=connectToDB();
            PreparedStatement st=con.prepareStatement("insert into Account values(?,?,?,?)");
            st.setString(1,userName);
            st.setString(2,password);
            st.setString(3,name);
            st.setInt(4,age);
            st.executeUpdate();
            st.close();
            con.close();
        } catch (SQLException e) {
            if(!e.getMessage().contains("Violation of PRIMARY KEY"))
                e.printStackTrace();
        }
    }

    public static List<String> getTeamNames() {
        try {
            Connection con = connectToDB();
            PreparedStatement st=con.prepareStatement("select * from Team");
            ResultSet resultSet = st.executeQuery();
            List <String> list=new ArrayList<>();
            while (resultSet.next()) {
                list.add(resultSet.getString("Name"));
            }
            con.close();
            return list;
        } catch (SQLException e) {
            if(!e.getMessage().contains("Violation of PRIMARY KEY"))
                e.printStackTrace();


        }
        return null;
    }

    public static void addOwnerRole(String userName, String name, String teamName, String appointedUserName) {
        try {
            Connection con=connectToDB();
            PreparedStatement ps=con.prepareStatement("insert into Owner values(?,?,?,?) ");
            ps.setString(1,userName);
            ps.setString(2,name);
            ps.setString(3,teamName);
            ps.setString(4,appointedUserName);
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            if(!e.getMessage().contains("Violation of PRIMARY KEY"))
                e.printStackTrace();

        }
    }

    public static void addPlayerRole(String userName, String name, Date birthday, String position, String teamName, int pageID) {
        try {
            Connection con=connectToDB();
            PreparedStatement ps=con.prepareStatement("insert into Player values(?,?,?,?,?,?) ");
            ps.setString(1,userName);
            ps.setString(2,name);
            ps.setDate(3,birthday);
            ps.setString(4,position);
            ps.setString(5,teamName);
            ps.setInt(6,pageID);
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            if(!e.getMessage().contains("Violation of PRIMARY KEY"))
                e.printStackTrace();

        }

    }

    public static void addFanRole(String userName, String name, boolean trackPersonalPages, boolean getMatchNotifications, List<Integer> pagesIDs) {
        try {
            Connection con=connectToDB();
            PreparedStatement ps1=con.prepareStatement("insert into Fan values(?,?,?,?) ");
            ps1.setString(1,userName);
            ps1.setString(2,name);
            ps1.setBoolean(3,trackPersonalPages);
            ps1.setBoolean(4,getMatchNotifications);
            ps1.executeUpdate();

            for(Integer id:pagesIDs){
                PreparedStatement ps2=con.prepareStatement("insert into FansAndPages values(?,?)");
                ps2.setInt(1,id);
                ps2.setString(2,userName);
                ps2.executeUpdate();
            }
            con.close();
        } catch (SQLException e) {
            if(!e.getMessage().contains("Violation of PRIMARY KEY"))
                e.printStackTrace();
        }

    }

    public static void addRefereeRole(String userName, String name, String training, HashMap<String, String> refLeaguesAndSeasons) {
        try {
            Connection con=connectToDB();
            PreparedStatement ps1=con.prepareStatement("insert into Referee values(?,?,?) ");
            ps1.setString(1,userName);
            ps1.setString(2,name);
            ps1.setString(3,training);
            ps1.executeUpdate();

            for(Map.Entry<String,String> entry:refLeaguesAndSeasons.entrySet()){
                PreparedStatement pr2=con.prepareStatement("insert into LeaguesForReferee values (?,?,?)");
                pr2.setString(1,userName);
                pr2.setString(2,entry.getKey());
                pr2.setString(3,entry.getValue());
                pr2.executeUpdate();
            }
            con.close();
        } catch (SQLException e) {
            if(!e.getMessage().contains("Violation of PRIMARY KEY"))
                e.printStackTrace();

        }
    }

    public static void addTeamManagerRole(String userName, String name, String teamName, String appointerUserName, List<String> permissions) {
        try {
            Connection con=connectToDB();
            PreparedStatement ps1=con.prepareStatement("insert into TeamManager values(?,?,?,?) ");
            ps1.setString(1,userName);
            ps1.setString(2,name);
            ps1.setString(3,teamName);
            ps1.setString(4,appointerUserName);
            ps1.executeUpdate();

            for(String permission:permissions){
                PreparedStatement pr2=con.prepareStatement("insert into TeamManagerPermissions values(?,?)");
                pr2.setString(1,userName);
                pr2.setString(2,permission);
                pr2.executeUpdate();
            }
            con.close();
        } catch (SQLException e) {
            if(!e.getMessage().contains("Violation of PRIMARY KEY"))
                e.printStackTrace();
        }
    }

    public static void addSystemManagerRole(String userName, String name, HashMap<String, String> complaintAndComments) {
        try {
            Connection con=connectToDB();
            PreparedStatement ps1=con.prepareStatement("insert into SystemManager values(?,?,?,?) ");
            ps1.setString(1,userName);
            ps1.setString(2,name);
            ps1.executeUpdate();

            for(Map.Entry<String,String> entry:complaintAndComments.entrySet()){
                PreparedStatement pr2=con.prepareStatement("insert into ComplainAndComments values(?,?,?)");
                pr2.setString(1,userName);
                pr2.setString(2,entry.getKey());
                pr2.setString(3,entry.getValue());
                pr2.executeUpdate();
            }
            con.close();
        } catch (SQLException e) {
            if(!e.getMessage().contains("Violation of PRIMARY KEY"))
                e.printStackTrace();
        }
    }

    public static void addAssociationRepresentativeRole(String userName, String name) {
        try {
            Connection con=connectToDB();
            PreparedStatement ps=con.prepareStatement("insert into AssociationRepresentative values(?,?)");
            ps.setString(1,userName);
            ps.setString(2,name);
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            if(!e.getMessage().contains("Violation of PRIMARY KEY"))
                e.printStackTrace();
        }
    }

    public static void addCoachRole(String userName, String name, String training,String teamRole, int pageID, String teamName) {
        try {
            Connection con=connectToDB();
            PreparedStatement ps=con.prepareStatement("insert into Coach values(?,?,?,?,?,?) ");
            ps.setString(1,userName);
            ps.setString(2,name);
            ps.setString(3,training);
            ps.setString(4,teamRole);
            ps.setInt(5,pageID);
            ps.setString(6,teamName);
            ps.executeUpdate();

            con.close();
        } catch (SQLException e) {
            if(!e.getMessage().contains("Violation of PRIMARY KEY"))
                e.printStackTrace();
        }
    }

    public static void addLeague(String name, List<String> seasonList, List<String[]> policyList) {
        try {
            Connection con=connectToDB();
            PreparedStatement ps1=con.prepareStatement("insert into League values(?) ");
            ps1.setString(1,name);
            ps1.executeUpdate();

            for(int i=0;i<seasonList.size();i++){

                String[] policy=policyList.get(i);

                PreparedStatement ps2=con.prepareStatement("insert into Season values(?)");
                ps2.setString(1,seasonList.get(i));
                ps2.executeUpdate();


                PreparedStatement ps3=con.prepareStatement("insert into Policy values (?,?,?,?,?)");
                ps3.setString(1,policy[2]);
                ps3.setString(2,policy[0]);
                ps3.setString(3,policy[1]);
                ps3.setString(4,name);
                ps3.setString(5,seasonList.get(i));
                ps3.executeUpdate();





                PreparedStatement ps4=con.prepareStatement("insert into SLsettings values(?,?,?)");
                ps4.setString(1,name);
                ps4.setString(2,seasonList.get(i));
                ps4.setString(3,policy[2]);
                ps4.executeUpdate();


            }
            con.close();
        } catch (SQLException e) {
            if(!e.getMessage().contains("Violation of PRIMARY KEY"))
                e.printStackTrace(); }
    }

    public static void addSeason(String name, List<String> leagueList, List<String[]> policyList) {
        try {
            Connection con=connectToDB();
            PreparedStatement ps1=con.prepareStatement("insert into Season values(?) ");
            ps1.setString(1,name);
            ps1.executeUpdate();

            for(int i=0;i<leagueList.size();i++){
                String[] policy=policyList.get(i);
                PreparedStatement ps2=con.prepareStatement("insert into SLsettings values(?,?,?)");
                ps2.setString(1,leagueList.get(i));
                ps2.setString(2,name);
                ps2.setString(3,policy[2]);
                ps2.executeUpdate();

                PreparedStatement ps3=con.prepareStatement("inserto into Policy values (?,?,?,?,?)");
                ps3.setString(1,policy[2]);
                ps3.setString(2,policy[0]);
                ps3.setString(3,policy[1]);
                ps3.setString(4,leagueList.get(i));
                ps3.setString(5,name);
                ps3.executeUpdate();
            }
            con.close();
        } catch (SQLException e) {
            if(!e.getMessage().contains("Violation of PRIMARY KEY"))
                e.printStackTrace(); }
    }

    public static void addStadium(String name) {
        try {
            Connection con=connectToDB();
            PreparedStatement ps=con.prepareStatement("insert into Stadium values(?) ");
            ps.setString(1,name);
            ps.executeUpdate();
        } catch (SQLException e) {
            if(!e.getMessage().contains("Violation of PRIMARY KEY"))
                e.printStackTrace();
        }
    }

    public static void addTeam(String name, int pageID, String leagueName, String stadiumName,int points) {
        try {
            Connection con=connectToDB();
            PreparedStatement ps=con.prepareStatement("insert into Team values(?,?,?,?,?)");
            ps.setString(1,name);
            ps.setInt(2,pageID);
            ps.setString(3,leagueName);
            ps.setString(4,stadiumName);
            ps.setInt(5,points);
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            if(!e.getMessage().contains("Violation of PRIMARY KEY"))
                e.printStackTrace();
        }
    }

    public static void addMatch(String date, Time time, int awayScore, int homeScore, String awayTeamName, String homeTeamName,
                                String mainRefUN, String lineRefUN1, String lineRefUN2, String stadiumName,String seasonName) {
        try {
            Connection con=connectToDB();
            PreparedStatement ps=con.prepareStatement("insert into Match values(?,?,?,?,?,?,?,?,?,?,?)");
            ps.setString(1,date);
            ps.setTime(2,time);
            ps.setInt(3,awayScore);
            ps.setInt(4,homeScore);
            ps.setString(5,awayTeamName);
            ps.setString(6,homeTeamName);
            ps.setString(7,mainRefUN);
            ps.setString(8,lineRefUN1);
            ps.setString(9,lineRefUN2);
            ps.setString(10,stadiumName);
            ps.setString(11,seasonName);
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            if(!e.getMessage().contains("Violation of PRIMARY KEY"))
                e.printStackTrace();
        }
    }

    public static void addAlert(String userName, List<String> alerts) {
        try {
            Connection con=connectToDB();
            for(String alert:alerts){
                PreparedStatement ps= con.prepareStatement("insert into Alert values(?,?)");
                ps.setString(1,alert);
                ps.setString(2,userName);
                ps.executeUpdate();
            }
            con.close();
        } catch (SQLException e) {
            if(!e.getMessage().contains("Violation of PRIMARY KEY"))
                e.printStackTrace();
        }
    }

    public static void addPage(int pageID){
        try {
            Connection con=connectToDB();
            PreparedStatement ps=con.prepareStatement("insert into Page values(?)");
            ps.setInt(1,pageID);
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            if(!e.getMessage().contains("Violation of PRIMARY KEY"))
                e.printStackTrace();
        }
    }

    public static void addRefsToMatch(String date, Time time, String awayTeamName, String homeTeamName, String username1,String username2, String username3) {
        try {
            Connection con=connectToDB();
            PreparedStatement pr=con.prepareStatement("update Match set MainReferee=?, LineRefereeOne=?, LineRefereeTwo=? where Date=? and AwayTeam=? and homeTeam=?");
            pr.setString(1,username1);
            pr.setString(2,username2);
            pr.setString(3,username3);
            pr.setString(4,date);
            pr.setString(5,awayTeamName);
            pr.setString(6,homeTeamName);
            pr.executeUpdate();
            con.close();
        } catch (SQLException e) {
            if(!e.getMessage().contains("Violation of PRIMARY KEY"))
                e.printStackTrace();
        }
    }

    public static void addGameEvent(String eventType, Time hour, String description, int gameMinute, String date, String awayTeamName, String homeTeamName) {
        try {
            Connection con=connectToDB();
            PreparedStatement ps=con.prepareStatement("insert into GameEvent values (?,?,?,?,?,?,?)");
            ps.setString(1,eventType);
            ps.setTime(2,hour);
            ps.setString(3,description);
            ps.setInt(4,gameMinute);
            ps.setString(5,date);
            ps.setString(6,awayTeamName);
            ps.setString(7,homeTeamName);
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            if(!e.getMessage().contains("Violation of PRIMARY KEY"))
                e.printStackTrace();
        }
    }

    public static String[] getUserNamePasswordDC(String userName) {
        String[] arr=new String[4];
        try {
            Connection con=connectToDB();
            PreparedStatement ps=con.prepareStatement("select * from Account where UserName=?");
            ps.setString(1,userName);
            ResultSet RS=ps.executeQuery();
            boolean sizeZero=true;
            while(RS.next()){
                sizeZero=false;
                arr[0]=RS.getString(1);
                arr[1]=RS.getString(2);
                arr[2]=RS.getString(3);
                arr[3]=RS.getInt(4)+"";
            }
            if(sizeZero) return null;
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arr;
    }

    public static List<String> getAccountRoles(String userName) {
        List<String> roleList=new ArrayList<>();
        try {
            Connection con=connectToDB();
            String[] roleClasses={"AssociationRepresentative","Coach","Fan","Owner","Player","Referee","SystemManager","TeamManager"};
            for(String role:roleClasses){
                PreparedStatement ps=con.prepareStatement("select * from "+role+" where UserName=?");
                ps.setString(1,userName);
                ResultSet RS=ps.executeQuery();
                while(RS.next())
                    roleList.add(role);
            }

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roleList;
    }
}
