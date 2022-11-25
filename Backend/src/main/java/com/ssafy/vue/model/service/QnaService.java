package com.ssafy.vue.model.service;

import java.util.List;

import com.ssafy.util.PageNavigation;
import com.ssafy.vue.model.dto.BoardParameterDto;
import com.ssafy.vue.model.dto.QnaDto;

public interface QnaService {
	public boolean writeArticle(QnaDto qnaDto) throws Exception;
	public List<QnaDto> listArticle() throws Exception;
	public PageNavigation makePageNavigation(BoardParameterDto boardParameterDto) throws Exception;
	
	public QnaDto getArticle(int articleno) throws Exception;
	public void updateHit(int articleno) throws Exception;
	public boolean modifyArticle(QnaDto qnaDto) throws Exception;
	public boolean deleteArticle(int articleno) throws Exception;
}
