package server.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;



public class UserDao {

	Connection con = null; // 데이터 베이스에 연동할 변수
	Statement stmt = null; // sql 실행에 필요한 변수
	ResultSet rs = null;   // select 구문을 실행하였을 때 결과 값을 저장할 변수
	String sql = null;
	public String joinres="complete";
	public UserDao() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); // sql과 연동할 플러그를 만들어 준다.
			con = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:orcl",
					"puyo","puyo"
					);

			stmt = con.createStatement();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	///삽입
	public void insert(UserDto dto)
	{
		try {
			sql = "insert into user_info (id,pw,name,birth,tel,email,pw_q,pw_a) "
					+" values ('"+dto.id
					+"','"+dto.pw+
					"','"+dto.name+
					"','"+dto.getBirthStr()+
					"','"+dto.tel+
					"','"+dto.email+
					"','"+dto.pw_q+
					"','"+dto.pw_a+
					"')";
			stmt.executeUpdate(sql);

		} catch (Exception e) {
			if(id_chk(dto.id)) joinres="notok";
			else if(mail_chk(dto.email)) joinres="mailnotok";
		} 
		finally
		{
			close();
		}

	}
	public ArrayList login_chk(String str,String str2)
	{
		ArrayList res = new ArrayList<>();
		boolean chk=false;
		try {

			sql ="select * from user_info where id = '"+str+"' and pw = '"+str2+"'";
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				chk=true;
				res.add(chk);
				res.add(rs.getString("id"));
				res.add(rs.getString("name"));
				res.add(rs.getDate("birth"));
				res.add(rs.getInt("record_v"));
				res.add(rs.getInt("record_d"));
				res.add(rs.getInt("score"));
			}
			else {
				res.add(chk);
			}

		} catch (Exception e) {

		} finally{
			close();
		}
		return res;

	}
	public boolean id_chk(String str)
	{
		boolean res=false;
		try {

			sql ="select * from user_info where id like '"+str+"'";
			rs = stmt.executeQuery(sql);

			if(rs.next())
				res = true;

		} 
		catch (Exception e) {

		} 
		finally{
			//close();
		}
		return res;


	}
	public boolean name_chk(String str)
	{
		boolean res=false;
		try {

			sql ="select * from user_info where name like '"+str+"'";
			rs = stmt.executeQuery(sql);

			if(rs.next())
				res = true;

		} 
		catch (Exception e) {

		} 
		finally{
			//close();
		}
		return res;


	}
	public boolean mail_chk(String str)
	{
		boolean res = false;

		try {

			sql ="select * from user_info where email = '"+str+"'";
			rs = stmt.executeQuery(sql);

			if(rs.next())

				res=true;


		}catch (Exception e) {


		}finally{

		}

		return res;
	}
	public boolean find_idchk(String str,String str2)
	{
		boolean res = false;

		try {

			sql ="select * from user_info where name = '"+str+"' and email = '"+str2+"'";
			rs = stmt.executeQuery(sql);

			if(rs.next())
				res = true;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{

		}

		return res;

	}
	public String Result_findid(String nn){
		String res = null;
		try {

			sql ="select id from user_info where email like '"+nn+"'";
			rs = stmt.executeQuery(sql);

			while(rs.next())
			{
				res = rs.getString("id");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			close();
		}

		return res;
	}
	public boolean find_pwchk(String str,String str2)
	{
		boolean res = false;

		try {

			sql ="select * from user_info where id = '"+str+"' and email = '"+str2+"'";
			rs = stmt.executeQuery(sql);

			if(rs.next())
				res = true;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{

		}

		return res;

	}
	public boolean Pw_QnA(String str,String str2)
	{
		boolean res = false;

		try {

			sql ="select * from user_info where pw_q = '"+str+"' and pw_a = '"+str2+"'";
			rs = stmt.executeQuery(sql);

			if(rs.next())
				res = true;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{

		}

		return res;

	}
	public String Change_pw(String id, String pw){
		String res = "비밀번호 변경완료";
		try {

			sql ="update user_info set pw ="+"'"+pw+"'"+" where id like '"+id+"'";
			stmt.executeUpdate(sql);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			close();
		}

		return res;
	}


	public void close()
	{
		if(rs!=null) try {rs.close();} catch (SQLException e) {}
		if(stmt!=null) try {stmt.close();} catch (SQLException e) {}
		if(con!=null) try {con.close();} catch (SQLException e) {}
	}

}
