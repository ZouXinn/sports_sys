package BLL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import DAL.*;
public class SetProTypeHolder {
	private int datas[][];//0 ���ӵ���  1����˫�� 2���ӵ���  3����������� 4���Ӱ��� 5���ӱĴ� 6��������
	//7Ů������  8Ů�Ӹߵ͸� 9Ů��ƽ��ľ 10 Ů��������� 11Ů�ӱĴ�	
	public SetProTypeHolder(int datas[][]) {
		this.datas = datas;		
	}
	public boolean send() {
		Connection con = DBConnect.getConnection();
		String sql = "update project set p_type = ? where p_id = ?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			for(int i = 0 ; i < datas.length ; i++) {
				String type = String.valueOf(datas[i][0])+String.valueOf(datas[i][1])+
						String.valueOf(datas[i][2]);
				ps.setString(1, type);
				ps.setString(2, String.valueOf(i));
				ps.execute();
			}		
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
			return false;
		}		
		return true;
	}
}
