package com.free.kakao.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KakaoServiceImpl implements KakaoService {
	
	@Autowired
	private SqlSession sqlSession;
	
}
