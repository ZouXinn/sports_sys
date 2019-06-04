package BLL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DAL.*;

public class SignUpHolder {//�����˺�ע����Ϣ
	private String teamName,userName,passWord,surePswd;
	public SignUpHolder(String teamName,String userName,String passWord,String surePswd) {
		this.teamName = teamName;
		this.userName = userName;
		this.passWord = passWord;
		this.surePswd = surePswd;		
	}
	public int signUp() {//0 �ɹ�  1 �û����ظ�  2ʧ��
		SqlDBHelper helper = new SqlDBHelper();
		String checkSql = "select * from team where t_id = '"+userName+"'";
		String insertSql = "insert into team (t_name,t_id,t_password) values(?,?,?)";//����Ӳ���
		ResultSet rs = helper.select(checkSql);
		try {
			if(rs != null && !rs.next()) {//�����Ϊ�գ����û���δ�ظ�������ʹ��
				Connection conn = DBConnect.getConnection();
				PreparedStatement ps = conn.prepareStatement(insertSql);
				ps.setString(1, teamName);
				ps.setString(2, userName);
				ps.setString(3, passWord);
				try{
				ps.execute();
				return 0;
				}catch(SQLException ex) {
					ex.printStackTrace();
					return 2;
				}									
			}else {//�û����Ѵ���
				return 1;
			}
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
			return -1;
		}		
		
	}
	
}
