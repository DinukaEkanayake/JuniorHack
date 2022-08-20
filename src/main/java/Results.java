import javax.rmi.ssl.SslRMIClientSocketFactory;
import java.io.FileWriter;
import java.sql.*;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Scanner;

public class Results {

    public void register(){
        String url= "jdbc:mysql://localhost:3306/gpa_calculator";
        String username="root";
        String pwd="";

        Scanner input=new Scanner(System.in);

        try {
            Connection connection= DriverManager.getConnection(url,username,pwd);
            //inserting a row
            System.out.println("Enter your name : ");
            String name=input.nextLine();
            System.out.println("Course Duration(in years)");
            int duration=input.nextInt();
            String sql1="insert into registration(Stu_Name,Duration)values(?,?)";
            PreparedStatement preparedStatement= connection.prepareStatement(sql1);
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,duration);
            int rows=preparedStatement.executeUpdate();

            if(rows>0){
                System.out.println("Congratulations! Registration was completed.");
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  void AddResults(){
        System.out.println("---Add Result---");
        System.out.print("Year(1,2,3,4): ");
        Scanner input=new Scanner(System.in);
        int year=input.nextInt();
        System.out.print("Course Code: ");
        String code=input.next();
        System.out.print("Grade: ");
        String grade=input.next();
        System.out.print("Credits: ");
        int credits=input.nextInt();

        System.out.println("Confirm (y/n)");
        char option=input.next().charAt(0);

        if (option=='y'){
            String url= "jdbc:mysql://localhost:3306/gpa_calculator";
            String username="root";
            String pwd="";

            try {
                Connection connection= DriverManager.getConnection(url,username,pwd);
                //inserting a row
                String sql1="insert into result(Year,Code,Grade,Credits)values(?,?,?,?)";
                PreparedStatement preparedStatement= connection.prepareStatement(sql1);
                preparedStatement.setInt(1,year);
                preparedStatement.setString(2,code);
                preparedStatement.setString(3, grade);
                preparedStatement.setInt(4, credits);

                int rows=preparedStatement.executeUpdate();

                if(rows>0){
                    System.out.println("Added");
                }
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else{
            AddResults();
        }

    }

    public  void Viewresults(){
        System.out.println("---View Result");

        try {
            String url= "jdbc:mysql://localhost:3306/gpa_calculator";
            String username="root";
            String pwd="";

            Connection connection= DriverManager.getConnection(url,username,pwd);
            //displaying
            String sql2="select * from result";
            Statement statement=connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql2);

            //calculate GPA
            int total=0;
            String grade;
            int credit;
            double sum=0.0;
            int count=0;
            while(resultSet.next()){
                double gpv = 0.00;
                total=total+resultSet.getInt(4);
                grade=resultSet.getString(3);
                credit=resultSet.getInt(4);

                if (grade.equals ("A+"))
                    gpv= 4.00;
                else if (grade.equals("A"))
                    gpv= 4.00;
                else if (grade.equals("A-"))
                    gpv = 3.70;
                else if (grade.equals("B+"))
                    gpv = 3.30;
                else if (grade.equals ("B"))
                    gpv = 3.0;
                else if (grade.equals("B-"))
                    gpv = 2.70;
                else if (grade.equals("C+"))
                    gpv = 2.30;
                else if (grade.equals ("C"))
                    gpv = 2.00;
                else if (grade.equals ("C-"))
                    gpv = 1.70;
                else if (grade.equals ("D+"))
                    gpv = 1.30;
                else if (grade.equals ("D"))
                    gpv = 1.00;

                sum=sum+ (gpv*credit);
                count=count+1;
            }
            double gpa;
            gpa=sum/total;
            double rgpa=Math.round(gpa*100)/100;
            System.out.println("Current GPA : "+ rgpa);
            System.out.println("No:of subjects :"+ count);
            System.out.println("Total credits : "+total);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("To view records");
        System.out.println("1.Year 1");
        System.out.println("2.Year 2");
        System.out.println("3.Year 3");
        System.out.println("4.Year 4");

        System.out.println("\n0.Back");

        Scanner input=new Scanner(System.in);
        System.out.println("Enter ur choice : ");
        int option=input.nextInt();

        displayrecords(option);

        System.out.println("Do delete records press d ");
        System.out.println("To go back press 0");
        System.out.println();
        System.out.print("Enter your choice : ");
        char choice=input.next().charAt(0);
        if(choice=='d'){
            deleteRecords();
        }
        if(choice=='0'){

        }



    }
    public void displayrecords(int opt){
        if(opt==1 || opt==2 || opt==3 || opt==4){
            System.out.println("----Year"+opt+"----");
            System.out.println();
            try {
                String url= "jdbc:mysql://localhost:3306/gpa_calculator";
                String username="root";
                String pwd="";

                Connection connection= DriverManager.getConnection(url,username,pwd);
                //displaying
                String sql2="select * from result";
                Statement statement=connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql2);

                int id=1;
                int totalcredits=0;
                while(resultSet.next()){
                    if(resultSet.getInt(1)==opt) {
                        System.out.print(id+". ");
                        System.out.println("course code - "+ resultSet.getString(2));
                        System.out.println("   Grade - "+resultSet.getString(3));
                        System.out.println("   Credits - "+resultSet.getInt(4));
                        totalcredits=totalcredits+resultSet.getInt(4);

                        String sql="insert into result(id) values (?)";
                        PreparedStatement preparedStatement=connection.prepareStatement(sql);
                        preparedStatement.setInt(1,id);
                       // preparedStatement.executeUpdate();

                        System.out.println();
                        id++;
                    }
                }
                System.out.println("No of subjects : " + (id-1));
                System.out.println("Total credits : "+totalcredits);
                System.out.println("GPA for year 1 : ");
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if(opt==0){

        }
    }

    public  void deleteRecords(){
        Scanner input=new Scanner(System.in);
        System.out.println("Enter record id to delete record : ");
        int Cid=input.nextInt();
        System.out.println("Confirm (y/n)");
        char ans=input.next().charAt(0);

        if(ans=='y'){
            String url= "jdbc:mysql://localhost:3306/gpa_calculator";
            String username="root";
            String pwd="";

            try {
                Connection connection= DriverManager.getConnection(url,username,pwd);
                //deletion
                String sql4="delete from result where id=?";
                PreparedStatement preparedStatement2=connection.prepareStatement(sql4);
                preparedStatement2.setInt(1,Cid);
               int rows= preparedStatement2.executeUpdate();
               if(rows>0){
                   System.out.println("succesfully deleted");
               }
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
    public void exportRecords(){

        System.out.println("----Export Data---");
        System.out.println("Exporting your data...");


        try{
            String url= "jdbc:mysql://localhost:3306/gpa_calculator";
            String username="root";
            String pwd="";

            Connection connection= DriverManager.getConnection(url,username,pwd);
            String query =" SELECT * FROM Course_results WHERE id = xxx"; // take the user's course module details.

            // create the java statement
            Statement statement = connection.createStatement();

            // execute the query, and get a java resultset
            ResultSet resultSet = statement.executeQuery(query);

            FileWriter writter = new FileWriter("file.txt", true);

            // iterate through the java resultset
            while (resultSet.next()) {

                int year = resultSet.getInt("year");
                String course_code = resultSet.getString("course_code");
                String grade = resultSet.getString("grade");
                int credits = resultSet.getInt("credits");

                writter.append(String.valueOf(year));
                writter.append("   " +course_code);
                writter.append("   " +grade);
                writter.append("   "+String.valueOf(credits));
                writter.append("\n");
            }

            writter.close();

        }catch (Exception e){
            System.out.println(e);
        }

        System.out.println("Done!");

    }

    public  void clearrecords(){

        System.out.print("----Clear Data----");
        Scanner input=new Scanner(System.in);
        System.out.print("Clear all data (y/n) : ");
        char ans1=input.next().charAt(0);
        System.out.print("Confirm (y/n) : ");
        char ans2=input.next().charAt(0);

        if(ans2=='y'){
            String url= "jdbc:mysql://localhost:3306/gpa_calculator";
            String username="root";
            String pwd="";
            try{
                Connection connection = DriverManager.getConnection(url,username,pwd);
                try{
                    Statement statement = connection.createStatement();
                    String sql = "DELETE FROM result";
                    int deletedRows=statement.executeUpdate(sql);
                    if(deletedRows>0){
                        System.out.println("Deleted All Rows Successfully.");
                    }else{
                        System.out.println("Table is empty.");
                    }

                } catch(SQLException s){
                    s.printStackTrace();
                }
                connection.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

}
