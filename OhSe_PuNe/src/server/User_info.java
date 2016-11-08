package server;

import java.util.ArrayList;

import server.model.UserDao;
import server.model.UserDto;


public class User_info {

	public static void main(String[] args) {
		// DataMain에서 생성자를 통해 여기서 수정 및 변경 , 추가를 한다

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
