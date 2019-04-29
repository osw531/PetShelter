package com.aroundog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aroundog.model.domain.FreeComment;
import com.aroundog.model.domain.LostComment;
import com.aroundog.model.service.LostCommentService;

@Controller
public class LostCommentController {
	@Autowired
	private LostCommentService lostCommentService;

	@RequestMapping(value="/user/lostboardcomment/write", method=RequestMethod.POST)
	public String lostBoardCommentRegist(LostComment lostComment) {
		lostCommentService.insert(lostComment);
		int lostboard_id=lostComment.getLostboard_id();
		System.out.println(lostboard_id+"로봇아이디");
		return "redirect:/user/lostboard/lostboardlist/"+lostboard_id;
	}
	
	//자유게시판  댓글의 댓글  등록하기~
	@RequestMapping(value="/user/lostcomment/add", method=RequestMethod.POST)
	public String lostCommentAdd(LostComment lostComment) {
		lostCommentService.commentAdd(lostComment);
		int lostboard_id=lostComment.getLostboard_id();
		return "redirect:/user/lostboard/detail/regist/"+lostboard_id;
	}
}
