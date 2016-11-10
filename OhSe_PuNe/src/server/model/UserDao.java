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
	/////리스트에서 가지고온 데이터 확인 
	public ArrayList<UserDto> list()
	{
		ArrayList<UserDto> res = new ArrayList<>();

		try {
			sql ="select * from user_info";
			rs = stmt.executeQuery(sql);

			while(rs.next())
			{
				UserDto dto = new UserDto();

				dto.id = rs.getString("id");
				dto.name = rs.getString("name");
				dto.birth = rs.getDate("birth");
				dto.record_v = rs.getInt("record_v");
				dto.record_d = rs.getInt("record_d");
				dto.score = rs.getInt("score");

				res.add(dto);
			}

		} catch (Exception e) {e.printStackTrace();} finally{close();}
		
		return res;
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

		} catch (Exception e) {e.printStackTrace();} finally{close();}

	}

	public ArrayList id_chk()
	{
		ArrayList res = new ArrayList<>();

		try {

			sql ="select id from user_info";
			rs = stmt.executeQuery(sql);

			while(rs.next())
			{
				res.add(rs.getString("id"));
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			close();
		}

		return res;
	}
	public HashMap login_chk()
	{
		HashMap res = new HashMap<>();

		try {

			sql ="select id, pw from user_info";
			rs = stmt.executeQuery(sql);

			while(rs.next())
			{
				res.put(rs.getString("id"), rs.getString("pw"));
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			close();
		}

		return res;
	}
	public HashMap find_id()
	{
		HashMap res = new HashMap<>();

		try {

			sql ="select name, email from user_info";
			rs = stmt.executeQuery(sql);

			while(rs.next())
			{
				res.put(rs.getString("name"), rs.getString("email"));
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			close();
		}

		return res;
		
	}
	public HashMap find_pw()
	{
		HashMap res = new HashMap<>();

		try {

			sql ="select id, email from user_info";
			rs = stmt.executeQuery(sql);

			while(rs.next())
			{
				res.put(rs.getString("id"), rs.getString("email"));
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			close();
		}

		return res;
	}
	public ArrayList mail_chk()
	{
		ArrayList res = new ArrayList<>();

		try {

			sql ="select email from user_info";
			rs = stmt.executeQuery(sql);

			while(rs.next())
			{
				res.add(rs.getString("email"));
			}

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
