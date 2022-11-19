import java.util.Scanner;
import java.sql.*;


class user
{
    
    Connection connect()
    {
        Connection c = null;
        try
        {
            String url = "jdbc:sqlite:D:/Work/Java/Project/Database.sqlite";
            c = DriverManager.getConnection(url);
            //System.out.println("Connection to SQLite has been established.");
            c.setAutoCommit(false);
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return c;
    }

    String name, car, date;
    Long mobile, aadhar;
    
}

class userExist extends user
{
    Scanner sc = new Scanner(System.in);
    
    void display()
    {
        //String sql = "Select * From User Where Aadhar > ?";
    }

    void registered()
    {
        System.out.print("Enter Aadhar Number: ");
        aadhar = sc.nextLong();

        String sql = "Select * From User Where Aadhar = ?";
        try(Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(sql))
        {
            pstmt.setLong(1, aadhar);
            ResultSet r = pstmt.executeQuery();
            if(r.getLong("Aadhar") != 0)
                System.out.println("Name: " + r.getString("Name") + "\nAadhar Number: " + r.getLong("Aadhar") + "\nMobile Number: " + r.getInt("Mobile") + "\nCar currently rented: " + r.getString("Car") + "\nDue Date: " + r.getString("Date"));
            else
            {
                System.out.println("Customer not found. Want to register?(Y/N): ");
                char c = sc.next().charAt(0);
                if(c == 'y' || c == 'Y')
                    register();
                else
                {
                    System.out.println("Incorrrect Choice!");
                    ask();
                }
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    void register()
    {
        System.out.print("Enter Name: ");
        name  = sc.nextLine();
        
        try
        {
            System.out.print("\nEnter Aadhar Number: ");
            aadhar = sc.nextLong();
            
            String str = Long.toString(aadhar);
            if(str.length() != 12)
            {
                System.out.println("Aadhar Number must be of 12 digits");
                ask();
            }
            
            String sql1 = "Select * From User Where Aadhar = ?";
            try(Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(sql1))
            {
                pstmt.setLong(1, aadhar);
                ResultSet r = pstmt.executeQuery();
                if(r.getLong("Aadhar") != 0)
                {
                    System.out.println("User Already Exist!");
                    ask();
                }
            }
            catch(SQLException e)
            {
                System.out.println("Error: " + e.getMessage());
            }
        }
        catch(Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }

        try
        {
            System.out.print("\nEnter Mobile Number: ");
            mobile = sc.nextLong();
            
            String str1 = Long.toString(aadhar);
            if(str1.length() != 12)
            {
                System.out.println("Aadhar Number must be of 12 digits");
                ask();
            }
            
            String sql2 = "Select * From User Where Mobile = ?";
            try(Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(sql2))
            {
                pstmt.setLong(1, mobile);
                ResultSet r = pstmt.executeQuery();
                if(r.getLong("Mobile") != 0)
                {
                    System.out.println("User Already Exist!");
                    ask();
                }
            }
            catch(SQLException e)
            {
                System.out.println("Error: " + e.getMessage());
            }
        }
        catch(Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    void ask()
    {
        char c;
        System.out.print("Are you registered Costumer?(Y/N) ");
        c = sc.next().charAt(0);
        
        if(c == 'y' || c == 'Y')
            registered();
        else if(c == 'n' || c == 'N')
            register();
        else
        {
            System.out.println("Incorrect Choice!");
            ask();
        }

    }
}

class vehicle
{
    void check()
    {}

    void displayAvailable()
    {}
}

class borrow
{
    Scanner sc = new Scanner(System.in);
    
    void display(int a)
    {
        if(a == 1)
        {
            vehicle v = new vehicle();
            v.displayAvailable();
        }
        else
        {
            userExist u = new userExist();
            u.display();
        }
    }
    
    void rent()
    {}

    void unRent()
    {}



    void menu()
    {
        int c;
        System.out.println("Services Available:-\n1. Display Cars available.\n2. Rent a Car.\n3. Return Rented Car.\n4. Show Customer details.\n5. Exit.");
        c = sc.nextInt();
        switch(c)
        {
            case 1: display(1);break;
            case 2: rent();break;
            case 3: unRent();break;
            case 4: display(2);break;
            case 5: 
            {
                userExist u = new userExist();
                u.ask();
                break;
            }
            default: menu();
        }
    }
}

class Main
{
    public static void main(String[] args)
    {
        System.out.println("\t\t\t\t\t--------------------Welcome to Car Rents Pvt Limited--------------------");
        System.out.println("Price:\nRs.2000 per day if car is booked for less than or equal to 15 days + Rs.2000 Caution Money.\nRs.1800 per day if car is booked for more than 15 days + Rs.2000 Caution Money.");
        System.out.println("1. Caution Money includes damages to vehicle and late return of vehicle and will be refunded after complete inspection of rented vehicle.\n2. Only 1 car can be rented on each Aadhar Number.\n3. Press Ctrl + C to exit at any moment.");
        userExist u = new userExist();
        u.ask();
    }
}