package com.free.test.firebase.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.free.test.firebase.dao.CommonDao;

@Service
public class CommonServiceImpl implements CommonService{
	@Autowired
	private SqlSession sqlSession;

	@Override
	public int getNextFileSeq() {
		int fileseq = sqlSession.getMapper(CommonDao.class).getNextFileSeq();
		return fileseq;
	}
	
	
}
