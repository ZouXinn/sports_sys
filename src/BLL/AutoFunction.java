package BLL;
import DAL.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import Model.RankCell;
import com.mysql.cj.conf.ConnectionUrl.Type;
import com.mysql.cj.conf.RuntimeProperty.RuntimePropertyListener;
import com.mysql.cj.jdbc.interceptors.ServerStatusDiffInterceptor;

public class AutoFunction {
	private static Connection con = DBConnect.getConnection();
	//private static RankCell[] Results;
	private static int referreNum = 4;
	//���˶�Ա�������
	public static boolean autoSorta_id() throws SQLException
	{
		int man0 = 1,woman0 = 0,man1 = 1,woman1 = 0;
		String sql01 = "SELECT count(athlete.a_id) FROM athlete,team_athlete WHERE athlete.a_id = team_athlete.a_id and t_id <> 'WHU' and a_sex = 1";
		PreparedStatement ptmt01 = con.prepareStatement(sql01);
		
		String sql0 = "SELECT count(athlete.a_id) FROM athlete,team_athlete WHERE athlete.a_id = team_athlete.a_id and t_id <> 'WHU' and a_sex = 0";
		PreparedStatement ptmt0 = con.prepareStatement(sql0);
		
		String sql1 = "select * from athlete,team_athlete where athlete.a_id = team_athlete.a_id and a_sex = 1 and t_id <> 2";
		PreparedStatement ptmt1 = con.prepareStatement(sql1);
		
		String sql2 = "select * from athlete,team_athlete where athlete.a_id = team_athlete.a_id and a_sex = 0 and t_id <> 2";
		PreparedStatement ptmt2 = con.prepareStatement(sql2);
		
		String sql3 = "select * from athlete,team_athlete where athlete.a_id = team_athlete.a_id and a_sex = 1 and t_id = 2";
		PreparedStatement ptmt3 = con.prepareStatement(sql3);
		
		String sql4 = "select * from athlete,team_athlete where athlete.a_id = team_athlete.a_id and a_sex = 0 and t_id = 2";
		PreparedStatement ptmt4 = con.prepareStatement(sql4);
		//��ѯ���ݿ��зǶ�������Ů�Բ��������������ΪcountOfUnhost0
		ResultSet rSet0 = ptmt0.executeQuery();
		int countOfUnhost0 = -1;
		if(rSet0.next()) {
			countOfUnhost0 = rSet0.getInt(1);	
			System.out.println(countOfUnhost0);
		}
		//��ѯ���ݿ��зǶ����������Բ��������������ΪcountOfUnhost0
		ResultSet rSet01 = ptmt01.executeQuery();
		int countOfUnhost01 = -1;
		if(rSet01.next()) {
			countOfUnhost01 = rSet01.getInt(1);	
			System.out.println(countOfUnhost01);
		}
		//System.out.print(countOfUnhost);
		//���Ƕ����������Ա��
		ResultSet rSet1 = ptmt1.executeQuery();
		while(rSet1.next()) {
			String a_id = rSet1.getString("a_id");
			String sqlTemp = "update athlete set a_gameID = ? where  a_id = ?";
			PreparedStatement ptmtemp = con.prepareStatement(sqlTemp);
			ptmtemp.setString(1, ""+man0);
			ptmtemp.setString(2, ""+a_id);
			ptmtemp.execute();
			man0 = man0 + 2;
		}
		//���Ƕ�������Ů�Ա��
		ResultSet rSet2 = ptmt2.executeQuery();
		while(rSet2.next()) {
			String a_id = rSet2.getString("a_id");
			String sqlTemp = "update athlete set a_gameID = ? where  a_id = ?";
			PreparedStatement ptmtemp = con.prepareStatement(sqlTemp);
			ptmtemp.setString(1, ""+woman0);
			ptmtemp.setString(2, ""+a_id);
			ptmtemp.execute();
			woman0 = woman0 + 2;
		}
		//�������������Ա��
		ResultSet rSet3 = ptmt3.executeQuery();
		while(rSet3.next()) {
			String a_id = rSet3.getString("a_id");
			String sqlTemp = "update athlete set a_gameID = ? where  a_id = ?";
			PreparedStatement ptmtemp = con.prepareStatement(sqlTemp);
			ptmtemp.setString(1, ""+(man1+countOfUnhost01));
			ptmtemp.setString(2, ""+a_id);
			ptmtemp.execute();
			man1 = man1 + 2;
		}
		//����������Ů�Ա��
		ResultSet rSet4 = ptmt4.executeQuery();
		while(rSet4.next()) {
			String a_id = rSet4.getString("a_id");
			String sqlTemp = "update athlete set a_gameID = ? where  a_id = ?";
			PreparedStatement ptmtemp = con.prepareStatement(sqlTemp);
			ptmtemp.setString(1, ""+(woman1+countOfUnhost0));
			ptmtemp.setString(2, ""+a_id);
			ptmtemp.execute();
			woman1 = woman1 + 2;
		}		
		return true;
	}
	//��ÿһ�����з�����к�
	public static boolean allocateRefereeGameID() throws SQLException {
		int tempNum = 0;
		String sql5 = "select * from referee";
		PreparedStatement ptmt5 = con.prepareStatement(sql5);
		ResultSet rSet5 = ptmt5.executeQuery();
		while(rSet5.next()) {
			String r_id = rSet5.getString("r_id");
			String sqlTemp = "update referee set r_gameID = ? where  r_id = ?";
			PreparedStatement ptmtemp = con.prepareStatement(sqlTemp);
			ptmtemp.setString(1,"J"+tempNum);
			ptmtemp.setString(2,r_id);
			ptmtemp.execute();
			tempNum++;
		}
		return true;
	}
	//�����з����ÿ��С�飬һ�������к������ֲ���
	public static boolean allocateRefereeToGtoup() throws SQLException {
		
		
		String sql6 = "SELECT count(r_id) FROM referee";
		PreparedStatement ptmt6 = con.prepareStatement(sql6);
		ResultSet rSet6 = ptmt6.executeQuery();
		int cout_referee = 0;
		while(rSet6.next()) {
			cout_referee = rSet6.getInt(1);
		}
		
		String sql7 = "Select DISTINCT p_id,groupNum,ageGroup from athlete_project";
		PreparedStatement ptmt7 = con.prepareStatement(sql7);
		ResultSet rSet7 = ptmt7.executeQuery();
		String sqlTem = "";
		int i = 0;
		while(rSet7.next()) {
			String p_id = rSet7.getString("p_id");
			String groupNum = rSet7.getString("groupNum");
			String ageGroup = rSet7.getString("ageGroup");
			sqlTem = "insert into referee_project set r_gameID = ? , p_id = ? , groupNum = ? , ageGroup = ? , role = ?";
			for(int j = 0;j < referreNum;j++) {
				PreparedStatement ptmtemp = con.prepareStatement(sqlTem);
				ptmtemp.setString(1, "J"+i);
				ptmtemp.setString(2,p_id);
				ptmtemp.setString(3,groupNum);
				ptmtemp.setString(4,ageGroup);
				ptmtemp.setInt(5, j);
				ptmtemp.execute();
				i++;
				if(i >= cout_referee) {i = 0;}
			}
		}
		return true;
	}
	//�˶�Ա����
	public static boolean allocateAthleteToGroup() throws SQLException {
		String sql8 = "SELECT DISTINCT p_id FROM athlete_project";
		PreparedStatement ptmt8 = con.prepareStatement(sql8);
		ResultSet rSet8 = ptmt8.executeQuery();
		int sequenceNum = 1;
		int groupSize = 6;//����һ����Ĵ�С
		while(rSet8.next()) {
			String tempp_id = rSet8.getString("p_id");
			String sqlTemp = "SELECT DISTINCT ageGroup FROM athlete_project WHERE p_id = ?";
			PreparedStatement ptmtemp = con.prepareStatement(sqlTemp);
			ptmtemp.setString(1,tempp_id);
			ResultSet rSetTemp = ptmtemp.executeQuery();
			while(rSetTemp.next()) {
				int groupNum = 1;
				String ageGroup = rSetTemp.getString("ageGroup");
				String sqlTemp3 = "SELECT * FROM athlete_project WHERE p_id = ? and ageGroup = ?";
				PreparedStatement ptmtemp2 = con.prepareStatement(sqlTemp3);
				ptmtemp2.setString(1, tempp_id);
				ptmtemp2.setString(2, ageGroup);
				ResultSet rSetTemp2 = ptmtemp2.executeQuery();
				while(rSetTemp2.next()) {
					String tempa_id = rSetTemp2.getString("a_id");
					String sqlTemp2 = "update athlete_project set groupNum = ? , sequenceNum = ? where p_id = ? and a_id = ?";
					PreparedStatement ptmtemp1 = con.prepareStatement(sqlTemp2);
					ptmtemp1.setString(1,groupNum+"");
					ptmtemp1.setString(2,sequenceNum+"");
					ptmtemp1.setString(3,tempp_id);
					ptmtemp1.setString(4,tempa_id);
					sequenceNum++;
					if(sequenceNum % groupSize == 0) {
						sequenceNum = 1;
						groupNum++;
					}
				}
			}
		}
		return true;
	}
	//�˶�Ա����ɼ��Զ�����
	//��������Ŀ������������
	//����ֵ��RankCell[]���飬����Ϊ����С˳���źõ��˶�Ա�ɼ��б�
	public static RankCell[] getRankCellof(int proType,int ageGroup) throws SQLException {
		String sql10 = "SELECT count(a_id) FROM athlete_project where p_id = ? and ageGroup = ?";
		PreparedStatement ptmt10 = con.prepareStatement(sql10);
		ptmt10.setString(1, proType+"");
		ptmt10.setString(2, ageGroup+"");
		ResultSet rSet10 = ptmt10.executeQuery();
		int count_score = 0;
		while(rSet10.next()) {
			count_score = rSet10.getInt(1);
		}
		
		RankCell[] Results = new RankCell[count_score];
		
		String sql9 = "SELECT a_gameID FROM athlete_project nature join athlete where p_id = ? and ageGroup = ?";
		PreparedStatement ptmt9 = con.prepareStatement(sql9);
		ptmt9.setString(1, proType+"");
		ptmt9.setString(2, ageGroup+"");
		ResultSet rSet9 = ptmt9.executeQuery();
		int i = 0;
		while(rSet9.next()){
			Results[i] = new RankCell(1, rSet9.getString("a_gameID"),getTotalScore(rSet9.getString("a_gameID"),proType+""));
			i++;
		}
		
		for(int m = 0;m < count_score;m++) {
			for(int n = 0;n < count_score - 1 - m;n++) {
				if(Results[n].getScore() < Results[n+1].getScore()) {
					RankCell temp = Results[n];
					Results[n] = Results[n+1];
					Results[n+1] = temp;
				}
			}
		}
		int temp = 1;
		for(int m = 1;m < count_score;m++) {
			if(Results[m].getScore() == Results[m-1].getScore()) {
				Results[m].setRank(temp);
			}
			else {
				temp = m;
				Results[m].setRank(temp);
			}
		}
		return Results;
	}
	//����ָ���˶�Ա��ָ����Ŀ���յ÷�
	public static double getTotalScore(String a_gameID,String p_id) throws SQLException {
			String sqlTemp = "SELECT score,role from athlete_score nature join referee_project where p_id = ? and a_gameID = ?";
			PreparedStatement pStatementTemp = con.prepareStatement(sqlTemp);
			pStatementTemp.setString(1, p_id);
			pStatementTemp.setString(2, a_gameID);
			ResultSet resultSetTemp = pStatementTemp.executeQuery();
			double[] scores = new double[referreNum - 1];
			int i = 0;
			double dp = 0;
			while(resultSetTemp.next()) {
				int roleTemp = resultSetTemp.getInt("role");
				if(roleTemp == 0) {
					dp = Double.parseDouble(resultSetTemp.getString("score"));
				}
				else {
					scores[i] = Double.parseDouble(resultSetTemp.getString("score"));
				}
				i++;
			}
			//ȥ����߷���ͷ�
			for(int m = 0;m < referreNum - 1;m++) {
				for(int n = 0;n < referreNum - 2 - m;n++) {
					if(scores[n] < scores[n+1]) {
						double temp = scores[n];
						scores[n] = scores[n+1];
						scores[n+1] = temp;
					}
				}
			}
			int totalScore = 0;
			for(int m = 2;m < referreNum - 1;m++) {
				totalScore+=scores[m];
			}
			totalScore = totalScore/(referreNum - 1);
			totalScore+=dp;  //�õ����ճɼ�
			return totalScore;
	}
	//����ָ�����˵�ȫ�ܳɼ������������гɼ�������ӣ�
	public static double getGPA(String a_gameID) throws SQLException {
		
			String sqlTemp = "SELECT p_id FROM athlete_project nature join athlete where a_gameID = ?";
			PreparedStatement pStatement = con.prepareStatement(sqlTemp);
			pStatement.setString(1, a_gameID);
			ResultSet resultSet2 = pStatement.executeQuery();
			int GPA = 0;
			while(resultSet2.next()) {
				GPA += getTotalScore(a_gameID, resultSet2.getString("p_id"));
			}
		return GPA;
	}
	//�����˶�Աȫ�ܳɼ�����
	public static RankCell[] getRankCellofAllAthlete() throws SQLException {
		String sql10 = "SELECT count(a_id) FROM athlete";
		PreparedStatement ptmt10 = con.prepareStatement(sql10);
		ResultSet rSet10 = ptmt10.executeQuery();
		int count_athlete = 0;
		while(rSet10.next()) {
			count_athlete = rSet10.getInt(1);
		}
		
		RankCell[] Results = new RankCell[count_athlete];
		
		String sql = "Select a_gameID from athlete";
		PreparedStatement ptmt = con.prepareStatement(sql);
		ResultSet rSet = ptmt.executeQuery();
		int i = 0;
		while(rSet.next()) {
			Results[i] = new RankCell(1, rSet.getString("a_gameID"), getGPA(rSet.getString("a_gameID")));
			i++;
		}
		for(int m = 0;m < count_athlete;m++) {
			for(int n = 0;n < count_athlete - 1 - m;n++) {
				if(Results[n].getScore() < Results[n+1].getScore()) {
					RankCell temp = Results[n];
					Results[n] = Results[n+1];
					Results[n+1] = temp;
				}
			}
		}
		int temp = 1;
		for(int m = 1;m < count_athlete;m++) {
			if(Results[m].getScore() == Results[m-1].getScore()) {
				Results[m].setRank(temp);
			}
			else {
				temp = m;
				Results[m].setRank(temp);
			}
		}
		return Results;
	}
	//����ָ����Ŀָ��������ܳɼ�
	public static double getScoreof(int proType,String t_name) throws SQLException {
		String sql = "select a_gameID from athlete_project nature join team_athlete nature join team where t_name = ? and p_id = ?";
		PreparedStatement ptmt = con.prepareStatement(sql);
		ptmt.setString(1, t_name);
		ptmt.setString(2, proType+"");
		ResultSet rSet = ptmt.executeQuery();
		Vector<Double> scoresOfTeam = new Vector<Double>();
		while(rSet.next()) {
			scoresOfTeam.add(getTotalScore(rSet.getString("a_gameID"), proType+""));
		}
		int num = scoresOfTeam.size();
		Double scores[] = new Double[num];
		scores = scoresOfTeam.toArray(new Double[num]);
		for(int m = 0;m < num;m++) {
			for(int n = 0;n < num - 1 - m;n++) {
				if(scores[n] < scores[n+1]) {
					Double temp = scores[n+1];
					scores[n+1] = scores[n];
					scores[n]= temp; 
				}
			}
		}
		
		String sql2 = "select p_type from project where p_id = ?";
		PreparedStatement ptmt2 = con.prepareStatement(sql2);
		ptmt2.setString(1, proType+"");
		ResultSet rSet2 = ptmt.executeQuery();
		String type = "222";
		while(rSet2.next()) {
			type = rSet2.getString("p_type");
		}
		int number = type.charAt(2);
		double totalScoreOfTeam = 0;
		if(number <= num) {
			for(int i = 0;i < number;i++) {
				totalScoreOfTeam += scores[i];
			}
		}
		
		return totalScoreOfTeam;
	}
	//�õ�ָ����Ŀ����������
	public static RankCell[] getRankCellofTeam(int proType) throws SQLException {
		String sql = "select DISTINCT t_name from athlete_project nature join team_athlete nature join team where p_id = ?";
		PreparedStatement ptmt = con.prepareStatement(sql);
		ptmt.setString(1, proType+"");
		ResultSet rSet = ptmt.executeQuery();
		int count_team = 0;
		while(rSet.next()) {
			count_team++;
		}
		RankCell[] Results = new RankCell[count_team];
		
		rSet = ptmt.executeQuery();
		int i = 0;
		while(rSet.next()) {
			Results[i] = new RankCell(1, rSet.getString("t_name"), getScoreof(proType, rSet.getString("t_name")));
			i++;
		}
		for(int m = 0;m < count_team;m++) {
			for(int n = 0;n < count_team - 1 - m;n++) {
				if(Results[n].getScore() < Results[n+1].getScore()) {
					RankCell temp = Results[n];
					Results[n] = Results[n+1];
					Results[n+1] = temp;
				}
			}
		}
		int temp = 1;
		for(int m = 1;m < count_team;m++) {
			if(Results[m].getScore() == Results[m-1].getScore()) {
				Results[m].setRank(temp);
			}
			else {
				temp = m;
				Results[m].setRank(temp);
			}
		}
		return Results;
	}
}
