package com.ssafy.vue.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.vue.model.dto.MemberDto;

@Mapper
public interface MemberMapper {

	public MemberDto login(MemberDto memberDto) throws SQLException;
	public MemberDto userInfo(String userid) throws SQLException;
	public void saveRefreshToken(Map<String, String> map) throws SQLException;
	public Object getRefreshToken(String userid) throws SQLException;
	public void deleteRefreshToken(Map<String, String> map) throws SQLException;
	public List<MemberDto> userList() throws SQLException;
	public void deleteUser(String userid) throws SQLException;
	public int idCheck(String userid) throws SQLException;
	public void join(MemberDto memberDto) throws SQLException;
	public void modify(MemberDto memberDto) throws SQLException;
	public int confirmPwd(Map<String, String> current) throws SQLException; 
	public void changePwd(Map<String, String> current) throws SQLException;
}
