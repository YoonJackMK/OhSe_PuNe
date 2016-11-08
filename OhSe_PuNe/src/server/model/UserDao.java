package server.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
					"hr","hr"
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

				dto.id = rs.getString("admin");
				dto.pw = rs.getString("admin");
				dto.name = rs.getString("admin");

				res.add(dto);
			}

		} catch (Exception e) {e.printStackTrace();} finally{close();}
		
		return res;
	}
	///삽입
	public void insert(UserDto dto)
	{
		try {
			sql = "insert into user_info (id,pw,name) "
					+" values ('"+dto.id
					+"','"+dto.pw+
					",'"+dto.name+
					"')";
			System.out.println(sql);
			//stmt.executeUpdate(sql);

		} catch (Exception e) {e.printStackTrace();} finally{close();}

	}

	public void close()
	{
		if(rs!=null) try {rs.close();} catch (SQLException e) {}
		if(stmt!=null) try {stmt.close();} catch (SQLException e) {}
		if(con!=null) try {con.close();} catch (SQLException e) {}

	}

}
