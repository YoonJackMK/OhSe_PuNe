package server.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserDto {
	int record_v, record_d,score;
	String id,name, pw, pw_q, pw_a, tel, email;
	Date birth;

	SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");


	public String getBirthStr() {
		return date.format(birth);
	}

	public void setBirthStr(String birthStr) {
		try {
			birth = date.parse(birthStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public int getRecord_v() {
		return record_v;
	}
	public void setRecord_v(int record_v) {
		this.record_v = record_v;
	}
	public int getRecord_d() {
		return record_d;
	}
	public void setRecord_d(int record_d) {
		this.record_d = record_d;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getPw_q() {
		return pw_q;
	}
	public void setPw_q(String pw_q) {
		this.pw_q = pw_q;
	}
	public String getPw_a() {
		return pw_a;
	}
	public void setPw_a(String pw_a) {
		this.pw_a = pw_a;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	@Override
	public String toString() {
		return "UserDto [id=" + id + ", pw=" + pw + ", name=" + name + ", pw_q=" + pw_q + ", pw_a=" + pw_a + ", birth="
				+ birth + ", tel=" + tel + ", email=" + email + ", record_v=" + record_v + ", record_d=" + record_d
				+ ", score=" + score + "]";
	}

}
