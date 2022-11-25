package com.ssafy.vue.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.vue.model.dto.MemberDto;

public interface MemberService {

	public MemberDto login(MemberDto memberDto) throws Exception;
	public MemberDto userInfo(String userid) throws Exception;
	public void saveRefreshToken(String userid, String refreshToken) throws Exception;
	public Object getRefreshToken(String userid) throws Exception;
	public void deleRefreshToken(String userid) throws Exception;
	public List<MemberDto> userList() throws Exception;
	public void deleteUser(String userid) throws Exception;
	public int idCheck(String userid) throws Exception;
	public void join(MemberDto memberDto) throws Exception;
	public void modify(MemberDto memberDto) throws Exception;
	public int confirmPwd(Map<String, String> current) throws Exception;
	public void changePwd(Map<String, String> current) throws Exception;
}
