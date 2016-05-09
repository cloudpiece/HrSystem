package au.edu.unsw.soacourse.books.dao;
import java.sql.*;  
//import org.sqlite.JDBC;  
/** 
 * 这是个非常简单的SQLite的Java程序, 
 * 程序中创建数据库、创建表、然后插入数据， 
 * 最后读出数据显示出来 
 * @author licoolxue (http://licoolxue.iteye.com/) 
 */  
public class TestSQLite {  
  
    public static void main(String[] args)   
    {  
        try  
        {  
         //连接SQLite的JDBC  
         Class.forName("org.sqlite.JDBC");  
         //建立一个数据库名employees.db的连接，如果不存在就在当前目录下创建之  
         Connection conn = DriverManager.getConnection("jdbc:sqlite:db/foundITServer.db");  
         Statement statement = conn.createStatement();  
         ResultSet rsExist = statement.executeQuery("SELECT * FROM sqlite_master where type='table' and name ='employee';"); //查询employee表是否存在   
         if(!rsExist.next())  
         {  
             statement.executeUpdate( "create table employee(name varchar(20), salary int);" );//创建一个表，两列  
             statement.executeUpdate( "insert into employee values('张三',8000);" ); //插入数据  
             statement.executeUpdate( "insert into employee values('李四',7800);" );  
             statement.executeUpdate( "insert into employee values('王五',5800);" );  
             statement.executeUpdate( "insert into employee values('赵六',9100);" );  
         }  
         ResultSet rs = statement.executeQuery("select * from employee;"); //查询数据   
         while (rs.next()) { //将查询到的数据打印出来  
             System.out.print("name = " + rs.getString("name") + " "); //列属性一  
             System.out.println("salary = " + rs.getString("salary")); //列属性二  
         }  
         rs.close();  
         statement.close();  
         conn.close(); //结束数据库的连接   
        }  
        catch( Exception e )  
        {  
         e.printStackTrace ( );  
        }  
    }  
}  