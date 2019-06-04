package BLL;
import DAL.*;
import java.net.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;

import com.mysql.cj.protocol.x.SyncMessageReader;

import Model.IpAndRole;
public class JudgeIPHolder {
	private String username;
	private int proType,ageGroup,groupNum;
	private String ip;
	//private 
	public JudgeIPHolder(String username,int proType,int ageGroup,int groupNum) {
		this.username = username;
		this.proType = proType;
		this.ageGroup = ageGroup;
		this.groupNum = groupNum;
		InetAddress ia=null;
        try {
            ia=InetAddress.getLocalHost();
            String localname=ia.getHostName();
            //ip=ia.getHostAddress();
            ip = GetRealLocalIP.getRealIP();
            System.out.println("���������ǣ�"+ localname);
            System.out.println("������ip�� ��"+ip);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
	public void insertIP() {
		Connection con = DBConnect.getConnection();				
		String sql = "update referee_project set ip = ? where p_id = ? and ageGroup = ? " + 
				"and groupNum = ? and r_gameID in (select r_gameID form from referee where r_id = ?)";
		try {
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, ip);
			preparedStatement.setString(2, String.valueOf(proType));
			preparedStatement.setString(3, String.valueOf(ageGroup));
			preparedStatement.setString(4, String.valueOf(groupNum));
			preparedStatement.setString(5, username);
			if(preparedStatement.execute()) {
				System.out.println("ip Set!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	public String getMainJudgeIP() {
		String sql = "select ip from referee_project where p_id = ? and ageGroup = ? and "+
	"groupNum = ? and role = ?";
		Connection connection = DBConnect.getConnection();
		String ret = null;
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, String.valueOf(proType));
			preparedStatement.setString(2, String.valueOf(ageGroup));
			preparedStatement.setString(3, String.valueOf(groupNum));
			preparedStatement.setInt(4, 0);
			ResultSet rSet = preparedStatement.executeQuery();
			if(rSet!= null && rSet.next()) {
				ret = rSet.getString("ip");
			}
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		return ret;
	}
	public IpAndRole[] getOtherJudgesIPAndRole() {
		IpAndRole[] ret = new IpAndRole[3];
		int index = 0;
		String sql = "select ip,role from referee_project where p_id = ? and "+
		"ageGroup = ? and groupNum = ? and role <> ? order by role";
		Connection connection = DBConnect.getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, String.valueOf(proType));
			preparedStatement.setString(2, String.valueOf(ageGroup));
			preparedStatement.setString(3, String.valueOf(groupNum));
			preparedStatement.setInt(4, 0);
			ResultSet rSet = preparedStatement.executeQuery();
			while(rSet!=null && rSet.next()) {
				if(index<3) {
					ret[index] = new IpAndRole(rSet.getString("ip"), rSet.getInt("role"));
					index++;
				}
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return ret;
	}
	public int getMyRole() {
		String sql = "select role from referee_project where p_id = ? and " + 
				"ageGroup = ? and groupNum = ? and r_gameID in (select r_gameID from referee where r_id = ?)";
		Connection connection = DBConnect.getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, String.valueOf(proType));
			preparedStatement.setString(2, String.valueOf(ageGroup));
			preparedStatement.setString(3, String.valueOf(groupNum));
			preparedStatement.setString(4, username);
			ResultSet rSet = preparedStatement.executeQuery();
			if(rSet!=null && rSet.next()) {
				return rSet.getInt("role");
			}
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}	
		return -1;
	}
	public void deleteIP() {
		Connection con = DBConnect.getConnection();
		String sql = "update referee_project set ip = ? where p_id = ? and ageGroup = ? " + 
				"and groupNum = ? and r_gameID in (select r_gameID form from referee where r_id = ?)";
		try {
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, null);//��֪���ܲ�����null   ���Թ���Ӧ�ÿ���
			preparedStatement.setString(2, String.valueOf(proType));
			preparedStatement.setString(3, String.valueOf(ageGroup));
			preparedStatement.setString(4, String.valueOf(groupNum));
			preparedStatement.setString(5, username);
			if(preparedStatement.execute()) {
				System.out.println("ip Set Null!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
