package server;

import java.util.ArrayList;

import server.model.UserDao;
import server.model.UserDto;


public class User_info {

	public static void main(String[] args) {
		// DataMain���� �����ڸ� ���� ���⼭ ���� �� ���� , �߰��� �Ѵ�

		ArrayList<UserDto> list = new UserDao().list();
		
		for (UserDto dto : list) {
			System.out.println(dto);
		}
		
	/*	UserDto dto = new UserDto();
		dto.setId("admin");
		dto.setPw("admin");
		dto.setName("admin");
		
		new UserDao().insert(dto);*/
	
	}

}
