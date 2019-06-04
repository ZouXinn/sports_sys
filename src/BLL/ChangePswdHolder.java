package BLL;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import DAL.*;

public class ChangePswdHolder {
	private String userName,passWord,newPassWord,againNewPassWord;
	private int identify;
	public ChangePswdHolder(String userName,String passWord,String newPassWord,String againNewPassWord,
			int identify) {
		this.userName = userName;
		this.passWord = passWord;
		this.newPassWord = newPassWord;
		this.againNewPassWord = againNewPassWord;
		this.identify = identify;//0  ����Ա  1 ����  2����
	}
	public int check() {//0 �ɹ�   1  ��Ϊ��  2���������벻һ��   
		if(passWord.equals("")||newPassWord.equals("")||againNewPassWord.equals("")) {
			return 1;
		}else if(!newPassWord.equals(againNewPassWord)) {
			System.out.println("new1:"+newPassWord+"   new2:"+againNewPassWord);
			return 2;
		}else {
			return 0;
		}
	}
	public int send() {//0�޸ĳɹ�   1 �˺Ų�����       2 �������     -1����
		try {
			int ret = CheckIn.check(userName, passWord, identify);
			if(ret == 0) {//������ȷ
				//String sql = "update team set password = ? where account = ? and password = ?";
				String sql = "update team set t_password = ? where t_id = ? and t_password = ?";
				Connection con = DBConnect.getConnection();
				try {
					PreparedStatement ps = con.prepareStatement(sql);
					ps.setString(1, newPassWord);
					ps.setString(2, userName);
					ps.setString(3, passWord);
					ps.execute();
					return 0;
				} catch (SQLException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
					return -1;
				}
			}else {
				return ret;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}		
	}
}
