package au.edu.unsw.soacourse.hrSystem.dao;
import java.sql.*;  

import au.edu.unsw.soacourse.hrSystem.model.UserProfile;
public class UserProfileDao {
	 public String getName(String userID)  {
	        try  
	        {  
	         //连接SQLite的JDBC  
	         Class.forName("org.sqlite.JDBC");  
	         //建立一个数据库名employees.db的连接，如果不存在就在当前目录下创建之  
	         Connection conn = DriverManager.getConnection("jdbc:sqlite:db/foundITServer.db");  
	         Statement statement = conn.createStatement();  
	         String sql = "SELECT * FROM tb_userProfile where userID='"+userID+"';";
	         System.out.println("sql is "+sql+"\n");
	         ResultSet rsExist = statement.executeQuery(sql);  
	         if(!rsExist.next())  
	         {
	        	 System.out.print("name = " + rsExist.getString("name") + " "); 
	        	 return rsExist.getString("name");
	         }
	         rsExist.close();  
	         statement.close();  
	         conn.close(); //结束数据库的连接   
	        }  
	        catch( Exception e )  
	        {  
	         e.printStackTrace ( );  
	        }  
		return null;
	 }
	 public void setName(String userID,String name ){
		 try  
	        {  
	         //连接SQLite的JDBC  
	         Class.forName("org.sqlite.JDBC");  
	         //建立一个数据库名employees.db的连接，如果不存在就在当前目录下创建之  
	         Connection conn = DriverManager.getConnection("jdbc:sqlite:db/foundITServer.db");  
	         Statement statement = conn.createStatement();  
	         String sql = "UPDATE  tb_userProfile SET name ='"+name+"WHERE userID = '"+userID+"';";
	         System.out.println("sql is "+sql+"\n");
	         ResultSet rsExist = statement.executeQuery(sql);  

	         rsExist.close();  
	         statement.close();  
	         conn.close(); //结束数据库的连接   
	        }  
	        catch( Exception e )  
	        {  
	         e.printStackTrace ( );  
	        }  

		 
	 }


}
