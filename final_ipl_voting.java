import java.io.*;
import java.sql.*;
import java.util.*;

class Captain {
    private String name;
    private int age;
    private String team;
    private int votes;

    public Captain(String name, int age, String team) {
        this.name = name;
        this.age = age;
        this.team = team;
        this.votes = 0;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getTeam() {
        return team;
    }

    public int getVotes() {
        return votes;
    }

    public void incrementVotes() {
        votes++;
    }

    @Override
    public String toString() {
        return "Captain{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", team='" + team + '\'' +
                ", votes=" + votes +
                '}';
    }
}

class IPLVotingSystem {
    private static final String A = null;

    // Method to print the winner and their rank based on the count of votes
    private static void printWinner(List<Captain> captainsList) {
        captainsList.sort(Comparator.comparingInt(Captain::getVotes).reversed());

        System.out.println("Voting Results:");
        for (int i = 0; i < captainsList.size(); i++) {
            Captain captain = captainsList.get(i);
            System.out.println("Rank " + (i + 1) + ": " + captain.getName() + " - " + captain.getVotes() + " votes");
            System.out.println("______________________________");
        }

        if (!captainsList.isEmpty()) {
            Captain winner = captainsList.get(0);
            System.out.println("--------------------");
            System.out.println("The winner is: " + winner.getName());
            System.out.println("--------------------");
            System.out.println("                                    ");
            System.out.println("*********************************");
            System.out.println("the new captain of team india :" + winner.getName());
            System.out.println("*********************************");
        } else {
            System.out.println("=>>>  No votes have been cast yet.");
        }

    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        Scanner scanner = new Scanner(System.in);

        Map<String, Captain> captainsMap = new HashMap<>();
        List<Captain> captainsList = new ArrayList<>();

        // Add captains to the map and list
        captainsMap.put("Rohit", new Captain("Rohit", 32, "MI"));
        captainsMap.put("Dhoni", new Captain("Dhoni", 39, "CSK"));
        captainsMap.put("Virat", new Captain("Virat", 30, "RCB"));
        captainsMap.put("Hardik", new Captain("Hardik", 31, "GT"));
        captainsMap.put("Rishabh", new Captain("Rishabh", 29, "DC"));

        captainsList.addAll(captainsMap.values());

        System.out.println("!WELCOME TO IPL VOTING SYSTEM!");
        System.out.println("this system for select new captain of ==>> INDIAN CRICKET TEAM");

        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. View Captains");
            System.out.println("2. Vote for Captain");
            System.out.println("3. View Voting Results");
            System.out.println("4. winner");
            System.out.println("5. using jdbc");

            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("List of Captains:");
                    for (Captain captain : captainsList) {
                        System.out.println(captain.getName() + " - " + captain.getAge() + " - " + captain.getTeam());
                    }
                    break;
                case 2:
                    System.out.print("Enter the name of the captain you want to vote for: ");
                    String voteForCaptain = scanner.next();
                    Captain selectedCaptain = captainsMap.get(voteForCaptain);
                    if (selectedCaptain != null) {
                        selectedCaptain.incrementVotes();
                        System.out.println("You voted for " + selectedCaptain.getName());
                    } else {
                        System.out.println("Invalid captain name");
                        System.out.println("// please try again //");
                    }
                    break;
                case 3:
                    System.out.println("Voting Results:");
                    for (Captain captain : captainsList) {

                        System.out.println(captain.getName() + " - " + captain.getVotes() + " votes");
                        System.out.println("<><><><><><><><><><><><><>");
                    }
                    break;
                case 4:
                    printWinner(captainsList);
                    break;
                case 5:
                    // STEP :1 load and resister driver
                    String driverName = "com.mysql.cj.jdbc.Driver";
                    Class.forName(driverName);
                    System.out.println("driver loaded successfully");
                    // STEP :2 create connection
                    String dburl = "jdbc:mysql://localhost:3306/ipl";
                    String dbuser = "root";
                    String dbpass = "";
                    Connection con = DriverManager.getConnection(dburl, dbuser, dbpass);
                    if (con != null)
                        System.out.println("conection successsful");
                    else
                        System.out.println(" not conected");

                    do {
                        System.out.println("<<<<<<<<<<==..opration using JDBC..==>>>>>>>>>>");
                        System.out.println("1. insert multiple  data");
                        System.out.println("2. delete any data in database");
                        System.out.println("3.update any data in database");
                        System.out.println("4. fetch data from database");
                        System.out.println("5. for exit");
                        System.out.println("[..ENTER YOUR CHOICE..]");
                        int cho = scanner.nextInt();
                        switch (cho) {
                            case 1:// insert data
                                System.out.println("enter how many data you want add in database:");
                                int x = scanner.nextInt();
                                for (int i = 1; i <= x; i++) {

                                    System.out.println("enter name of " + i + "player:");
                                    String name = scanner.next();
                                    System.out.println("enter an age of " + " " + i + "player:");
                                    int age = scanner.nextInt();
                                    System.out.println("enter a number of" + i + "player votes ");
                                    int vote = scanner.nextInt();
                                    System.out.println("enter a team of" + i + " player:");
                                    String team = scanner.next();
                                    Statement st = con.createStatement();
                                    String sql = "insert into ipl_voting(p_name,p_age,votes,p_team) values('" + name
                                            + "'," + age + "," + vote + ",'" + team + "')";
                                    // step 4: execute query
                                    int j = st.executeUpdate(sql);
                                    if (j > 0) {
                                        System.out.println(" insertion successfull");
                                    } else {
                                        System.out.println(" not insert");
                                    }

                                }
                                break;
                            case 2:// delete data
                                String sql = "delete from ipl_voting where p_name=?";
                                PreparedStatement pst = con.prepareStatement(sql);
                                System.out.println("enter player name");
                                String x1 = scanner.next();
                                pst.setString(1, x1);

                                int i = pst.executeUpdate();
                                if (i > 0) {
                                    System.out.println(" deleted successfull");
                                } else {
                                    System.out.println(" not delete, data missmatch");
                                }
                                break;
                            case 3:
                                String sql2 = "update ipl_voting set p_name =? where p_id=?";
                                PreparedStatement pst2 = con.prepareStatement(sql2);
                                System.out.println("enter a id which data you want change");
                                int id = scanner.nextInt();
                                System.out.println("enter new name");
                                String n = scanner.next();
                                pst2.setString(1, n);
                                pst2.setInt(2, id);

                                int j = pst2.executeUpdate();
                                if (j > 0) {
                                    System.out.println(" update successfull");
                                } else {
                                    System.out.println(" not update data missmatch");
                                }

                                break;
                            case 4:
                                String sql3 = "select * from ipl_voting where p_id=?";
                                PreparedStatement pst3 = con.prepareStatement(sql3);
                                System.out.println("enter player id whose data you want see");
                                int id2 = scanner.nextInt();
                                pst3.setInt(1, id2);

                                ResultSet rs = pst3.executeQuery();
                                while (rs.next()) {
                                    System.out.println("..........................");
                                    System.out.println("player id=" + rs.getInt(1));
                                    System.out.println("..........................");
                                    System.out.println("name of player=" + rs.getString(2));
                                    System.out.println("..........................");
                                    System.out.println("player age=" + rs.getInt(3));
                                    System.out.println("..........................");
                                    System.out.println("total votes of player=" + rs.getInt(4));
                                    System.out.println("..........................");
                                    System.out.println("team of player=" + rs.getString(5));
                                    System.out.println("..........................");
                                }

                                break;
                            case 5:
                                System.out.println("(*)(*)(*)(*) THANKS FOR VISIT (*)(*)(*)(*)");

                                break;
                            default:
                                System.out.println("invalid option");

                        }
                    } while (false);
                    break;
                case 6:
                    System.out.println("{{{{{{{{{ HAVE A NICE DAY }}}}}}}}}");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
