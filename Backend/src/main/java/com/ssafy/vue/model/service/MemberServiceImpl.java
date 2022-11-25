package com.ssafy.vue.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.vue.model.dto.MemberDto;
import com.ssafy.vue.model.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public MemberDto login(MemberDto memberDto) throws Exception {
		if (memberDto.getUserid() == null || memberDto.getUserpwd() == null)
			return null;
		return sqlSession.getMapper(MemberMapper.class).login(memberDto);
	}

	@Override
	public MemberDto userInfo(String userid) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).userInfo(userid);
	}

	@Override
	public void saveRefreshToken(String userid, String refreshToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userid", userid);
		map.put("token", refreshToken);
		sqlSession.getMapper(MemberMapper.class).saveRefreshToken(map);
	}

	@Override
	public Object getRefreshToken(String userid) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).getRefreshToken(userid);
	}

	@Override
	public void deleRefreshToken(String userid) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userid", userid);
		map.put("token", null);
		sqlSession.getMapper(MemberMapper.class).deleteRefreshToken(map);
	}

	@Override
	public List<MemberDto> userList() throws Exception {
		return sqlSession.getMapper(MemberMapper.class).userList();
	}

	@Override
	public void deleteUser(String userid) throws Exception {
		sqlSession.getMapper(MemberMapper.class).deleteUser(userid);
	}

	@Override
	public void join(MemberDto memberDto) throws Exception {
		sqlSession.getMapper(MemberMapper.class).join(memberDto);
	}

	@Override
	public int idCheck(String userid) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).idCheck(userid);
	}

	@Override
	public void modify(MemberDto memberDto) throws Exception {
		sqlSession.getMapper(MemberMapper.class).modify(memberDto);
	}

	@Override
	public int confirmPwd(Map<String, String> current) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).confirmPwd(current);
	}

	@Override
	public void changePwd(Map<String, String> current) throws Exception {
		sqlSession.getMapper(MemberMapper.class).changePwd(current);
	}

}
