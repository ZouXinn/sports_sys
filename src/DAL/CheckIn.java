package DAL;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CheckIn {
	private static Connection connection = DBConnect.getConnection();
	public static int check(String account,String pswd,int identify) throws IOException
	{// 0 ������ȷ    1 �˺Ų�����       2 �������     
		//0 admin  1 team  2 judge
		int result = -1;
		if(identify == 0)
		{
			if(account.equals("admin")&&pswd.equals("admin"))
			{
				result = 0;		//
			}
			else {
				result = 2;   //
			}
		}
		if(identify == 1)
		{
			String password = null;
			Statement stmt;
			ResultSet rs;
			
			try {
				stmt = connection.createStatement();
				rs = stmt.executeQuery("SELECT * FROM team WHERE t_id='" + account+"'");
				while (rs.next()) {
					password = rs.getString("t_password");
				}
			} catch (SQLException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
			if(pswd.equals(password)) {
				System.out.println("team succsses!");
				result = 0;
			}					
			else {
				System.out.println("team sorry!");
				if(password == null) {System.out.println("You have to register first!"); result = 1;}
				else {System.out.println("Your password is wrong");result = 2;}
			}
		}
		else if(identify == 2)
		{
			String password = null;
			Statement stmt;
			ResultSet rs;
			
			try {
				stmt = connection.createStatement();
				rs = stmt.executeQuery("select r_phone from referee where r_id=" + "'"+account+"'");
				while (rs.next()) {
					password = rs.getString("r_phone");//idΪ�˺ţ��绰Ϊ����
				}
			} catch (SQLException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
			if(pswd.equals(password)) {
				System.out.println("referee succsses!");
				result = 0;
			}		
			else {
				System.out.println("referee sorry!");
				if(password == null) {System.out.println("You have to register first!"); result = 1;}
				else {System.out.println("Your password is wrong");result = 2;}
			}
		}
		return result;
	}
}

