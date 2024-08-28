import java.util.*;
import java.io.*;
import java.sql.*;

class Email {
    String subject;
    String content;
    String sender;
    String reciver;

    public Email(String subject, String content, String sender, String reciver) {
        this.subject = subject;
        this.content = content;
        this.sender = sender;
        this.reciver = reciver;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public String getSender() {
        return sender;
    }

    public String getReciver() {
        return reciver;
    }
}

class User {
    String username;
    String password;
    LinkedList<Email> inbox;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.inbox = new LinkedList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void addEmail(Email email) {
        inbox.add(email);
    }

    public void displayInbox() {
        if (inbox.isEmpty()) {
            System.out.println("Inbox is empty.");
            return;
        }
        for (int i = 0; i < inbox.size(); i++) {
            System.out.println("Email " + (i + 1));
            System.out.println("From: " + inbox.get(i).getReciver());
            System.out.println("To: " + inbox.get(i).getSender());
            System.out.println("Subject: " + inbox.get(i).getSubject());
            System.out.println("Content: " + inbox.get(i).getContent());
            System.out.println("========================");
        }
    }

    public void deleteEmail(int index) {
        if (index >= 0 && index < inbox.size()) {
            inbox.remove(index);
            System.out.println("Email deleted.");
        } else {
            System.out.println("Invalid email index.");
        }
    }

    public void searchEmails(String keyword) {
        System.out.println("Search results for keyword: " + keyword);
        for (Email email : inbox) {
            if (email.getSubject().toLowerCase().contains(keyword.toLowerCase()) ||
                    email.getContent().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println("From:" + email.getReciver());
                System.out.println("To: " + email.getSender());
                System.out.println("Subject: " + email.getSubject());
                System.out.println("Content: " + email.getContent());
                System.out.println("========================");
            } else {
                System.out.println("not found");
            }
        }
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
        System.out.println("Password changed successfully.");
    }

    public void viewProfile() {
        System.out.println("Username: " + username);
        System.out.println("Emails in inbox(List): " + inbox.size());
    }
}

class EmailSystem {
    private HashMap<String, User> users;
    private User currentUser;

    public EmailSystem() {
        this.users = new HashMap<>();
    }

    public void registerUser(String username, String password) {
        if (!users.containsKey(username)) {
            users.put(username, new User(username, password));
            System.out.println("User registered successfully.");
        } else {
            System.out.println("Username already exists.");
        }
    }

    public void login(String username, String password) {
        if (users.containsKey(username)) {
            User user = users.get(username);
            if (user.getPassword().equals(password)) {
                currentUser = user;
                System.out.println("Logged in as " + username);
            } else {
                System.out.println("Incorrect password");
            }
        } else {
            System.out.println("User not found");
        }
    }

    public void composeEmail(String subject, String content, String reciver) {
        if (currentUser != null) {
            Email newEmail = new Email(subject, content, reciver, currentUser.getUsername());
            users.values().forEach(user -> user.addEmail(newEmail));

            System.out.println("Email sent successfully ");
        } else {
            System.out.println("Please log in first");
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }
}

class EmailClass {
    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        EmailSystem emailSystem = new EmailSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Compose Email");
            System.out.println("4. Display Inbox");
            System.out.println("5. Delete Email");
            System.out.println("6. Search Email");
            System.out.println("7. Change Password");
            System.out.println("8. View Profile");
            System.out.println("9.opration in email table data(using JDBC)");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    System.out.print("Enter new username: ");
                    String newUsername = scanner.nextLine();
                    System.out.print("Enter new password: ");
                    String newPassword = scanner.nextLine();
                    emailSystem.registerUser(newUsername, newPassword);
                    break;
                case 2:
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    emailSystem.login(username, password);
                    break;
                case 3:
                    if (emailSystem.getCurrentUser() != null) {
                        System.out.print("Enter email subject: ");
                        String subject = scanner.nextLine();
                        System.out.print("Enter email content: ");
                        String content = scanner.nextLine();
                        System.out.println("enter reciver name: ");
                        String rec = scanner.nextLine();
                        emailSystem.composeEmail(subject, content, rec);
                        FileWriter fr = new FileWriter("email.txt");
                        BufferedWriter bw = new BufferedWriter(fr);
                        bw.write("To:=");
                        bw.write(rec);
                        bw.newLine();
                        ;
                        bw.write("subject= ");
                        bw.write(subject);
                        bw.newLine();
                        bw.write("content=  ");
                        bw.write(content);
                        bw.newLine();
                        bw.flush();
                        bw.close();
                    } else {
                        System.out.println("Please log in first");
                    }
                    break;
                case 4:
                    if (emailSystem.getCurrentUser() != null) {
                        emailSystem.getCurrentUser().displayInbox();
                    } else {
                        System.out.println("Please log in first");
                    }
                    break;
                case 5:
                    if (emailSystem.getCurrentUser() != null) {
                        System.out.print("Enter the index of the email to delete: ");
                        int indexToDelete = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline
                        emailSystem.getCurrentUser().deleteEmail(indexToDelete - 1);
                    } else {
                        System.out.println("Please log in first");
                    }
                    break;
                case 6:
                    if (emailSystem.getCurrentUser() != null) {
                        System.out.print("Enter keyword to search: "); // enter subject
                        String keyword = scanner.nextLine();
                        emailSystem.getCurrentUser().searchEmails(keyword);
                    } else {
                        System.out.println("Please log in first");
                    }
                    break;
                case 7:
                    if (emailSystem.getCurrentUser() != null) {
                        System.out.print("Enter new password: ");
                        newPassword = scanner.nextLine();
                        emailSystem.getCurrentUser().changePassword(newPassword);
                    } else {
                        System.out.println("Please log in first");
                    }
                    break;
                case 8:
                    if (emailSystem.getCurrentUser() != null) {
                        emailSystem.getCurrentUser().viewProfile();
                    } else {
                        System.out.println("Please log in first");
                    }
                    break;
                case 9:
                    // STEP :1 load and resister driver
                    String driverName = "com.mysql.cj.jdbc.Driver";
                    Class.forName(driverName);
                    System.out.println("driver loaded successfully");
                    // STEP :2 create connection
                    String dburl = "jdbc:mysql://localhost:3306/email";
                    String dbuser = "root";
                    String dbpass = "";
                    Connection con = DriverManager.getConnection(dburl, dbuser, dbpass);
                    if (con != null)
                        System.out.println("conection successsful");
                    else
                        System.out.println(" not conected");

                    while (true) {
                        System.out.println(".....JDBC.....");
                        System.out.println("1.insert data for send email to other user:");
                        System.out.println("2.update user/rceiver detail:");
                        System.out.println("3.delete user from table:");
                        System.out.println("4.display user details");
                        System.out.println("5.dispaly all details of table");
                        System.out.println("6.exit");
                        System.out.println("****** ENTER YOUR CHOIE******");
                        int ch = scanner.nextInt();
                        switch (ch) {
                            case 1:// insert data in table
                                System.out.println("how many user you want add in table ? ");
                                int n = scanner.nextInt();
                                for (int i = 1; i <= n; i++) {

                                    System.out.println("enter a user name");
                                    String name = scanner.next();
                                    System.out.println("enter mobile number of user");
                                    String m = scanner.next();
                                    System.out.println("enter the subject of email");
                                    String sub = scanner.next();
                                    System.out.println("enter the name of reciver");
                                    String rec = scanner.next();
                                    System.out.println("enter the name of file");
                                    String file = scanner.next();// "D:\\file_name.txt"

                                    String sql = "insert into email_table(u_name,u_mobile,subject,reciver,massage)values(?,?,?,?,?)";
                                    PreparedStatement pst = con.prepareStatement(sql);

                                    pst.setString(1, name);
                                    pst.setString(2, m);
                                    pst.setString(3, sub);
                                    pst.setString(4, rec);
                                    File f = new File(file);
                                    FileReader fr = new FileReader(f);
                                    pst.setCharacterStream(5, fr);

                                    int j = pst.executeUpdate();
                                    if (j > 0) {
                                        System.out.println("email send sucssesfully");
                                    } else {
                                        System.out.println(" not send");
                                    }

                                }
                                break;
                            case 2:// update name if user and reciver
                                String sql2 = "update email_table set u_name =? where u_id=?";
                                PreparedStatement pst2 = con.prepareStatement(sql2);
                                System.out.println("enter a u_id which data you want change");
                                int id = scanner.nextInt();
                                System.out.println("enter new name of user");
                                String n1 = scanner.next();

                                pst2.setString(1, n1);

                                pst2.setInt(2, id);
                                String sql3 = "update email_table set reciver =? where u_id=?";
                                PreparedStatement pst3 = con.prepareStatement(sql3);

                                System.out.println("enter a new name of reciver:");
                                String rec_new = scanner.next();
                                pst3.setString(1, rec_new);

                                pst3.setInt(2, id);

                                int j = pst2.executeUpdate();
                                int x = pst3.executeUpdate();
                                if (j > 0 && x > 0) {
                                    System.out.println(" update successfull");
                                } else {
                                    System.out.println(" not update ");
                                }
                                break;
                            case 3: // delete data from table
                                String sql4 = "delete from email_table where u_name=?";
                                PreparedStatement pst = con.prepareStatement(sql4);
                                System.out.println("enter a name of user ,whose name you want delete:");
                                String d_n = scanner.next();
                                pst.setString(1, d_n);

                                int i = pst.executeUpdate();
                                if (i > 0) {
                                    System.out.println(" data delete succusesfull");
                                } else {
                                    System.out.println("not delete");
                                }

                                break;
                            case 4:// display user data
                                String sql5 = "select u_id,u_name,u_mobile,reciver from email_table where u_id=?";
                                PreparedStatement pst4 = con.prepareStatement(sql5);
                                System.out.println("enter usre id ");
                                int id2 = scanner.nextInt();
                                pst4.setInt(1, id2);
                                ResultSet rs = pst4.executeQuery();
                                while (rs.next()) {
                                    System.out.println("user id: " + rs.getInt(1));
                                    System.out.println("name of user: " + rs.getString("u_name"));
                                    System.out.println("mobile number of user: " + rs.getString("u_mobile"));
                                    System.out.println("user send email to: " + rs.getString("reciver"));
                                }
                                break;
                            case 5:
                                String sql6 = "select * from email_table where u_id=? ";
                                PreparedStatement pst5 = con.prepareStatement(sql6);
                                System.out.println("enter an user id: ");
                                int id3 = scanner.nextInt();
                                pst5.setInt(1, id3);
                                ResultSet rs1 = pst5.executeQuery();
                                while (rs1.next()) {
                                    System.out.println("user id: " + rs1.getInt("u_id"));
                                    System.out.println("user mobile number: " + rs1.getString("u_mobile"));
                                    System.out.println("From: " + rs1.getString("u_name"));
                                    System.out.println("To: " + rs1.getString("reciver"));
                                    System.out.println("Subject: " + rs1.getString("subject"));
                                    Clob c = rs1.getClob(6);
                                    Reader r = c.getCharacterStream();
                                    int z = r.read();
                                    System.out.print("Massage: ");
                                    while (z != -1) {
                                        System.out.print((char) z);
                                        z = r.read();
                                    }
                                    System.out.println();

                                }
                                break;
                            case 6:
                                System.out.println("*******THANKS FOR VISIT********");
                                System.exit(0);
                                break;
                            default:
                                System.out.println("Invalid choice");
                        }
                    }

                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}
