import java.sql.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("---GPA Calculator---");

        Results s1 = new Results();
        try {
            String url = "jdbc:mysql://localhost:3306/gpa_calculator";
            String username = "root";
            String pwd = "";

            Connection connection = DriverManager.getConnection(url, username, pwd);
            //displaying
            String sql2 = "select * from registration";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql2);

            if (resultSet.next()) {
                String name = resultSet.getString(1);
                System.out.println("Hello" + " " + name + ", " + "Welcome back!");
            } else {
                s1.register();
            }
            connection.close();
            while (true){
                System.out.println("---Menu---");
                System.out.println("1.View results");
                System.out.println("2.Add results");
                System.out.println("3.Export Data");
                System.out.println("4.Clear Data");
                System.out.println("5.Exit");

                Scanner input=new Scanner(System.in);
                System.out.print("Enter your choice: ");
                int option=input.nextInt();

                if (option==1){
                    s1.Viewresults();
                }
                if (option==2){
                    s1.AddResults();
                }
                if(option==3){
                    s1.exportRecords();
                }
                if(option==4){
                    s1.clearrecords();
                }
                if (option==5){
                    System.exit(0);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
