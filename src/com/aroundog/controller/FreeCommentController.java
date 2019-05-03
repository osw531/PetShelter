package com.aroundog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aroundog.model.domain.FreeComment;
import com.aroundog.model.service.FreeCommentService;

@Controller
public class FreeCommentController {
	@Autowired
	private FreeCommentService freeCommentService;
	
	
	//자유게시판  댓글  등록하기~
	@RequestMapping(value="/user/freecomment/regist", method=RequestMethod.POST)
	public String freeBoardCommentRegist(FreeComment freeComment) {
		int freeboard_id2=freeComment.getFreeboard_id();
		List<FreeComment> free=freeCommentService.select(freeboard_id2);
		System.out.println(free);
		if(free==null) {	
			System.out.println("첫댓글");
			freeCommentService.insertFirst(freeComment);
		}else {
			System.out.println("다음댓글");
			freeCommentService.insert(freeComment);
		}
		int freeboard_id=freeComment.getFreeboard_id();
		return "redirect:/user/freeboard/detail/regist/"+freeboard_id;
	}
	
	//자유게시판  댓글의 댓글  등록하기~
	@RequestMapping(value="/user/freecomment/add", method=RequestMethod.POST)
	public String freeBoardCommentAdd(FreeComment freeComment) {
		freeCommentService.commentAdd(freeComment);
		int freeboard_id=freeComment.getFreeboard_id();
		return "redirect:/user/freeboard/detail/regist/"+freeboard_id;
	}
	
	//댓글 team으로 삭제
	@RequestMapping(value="/user/freecomment/del/{team}", method=RequestMethod.GET)
	public String freeCommentDelByTeam(FreeComment freeComment,@PathVariable("team") int team) {
		freeCommentService.deleteByTeam(freeComment);
		return "redirect:/user/freeboard/detail/"+freeComment.getFreeboard_id();//해당 게시판으로 이동해야함/user/freeboard/detail/{freeboard_id}
	}
	
	//댓글의 댓글 한건 삭제
	@RequestMapping(value="/user/freecommentreply/del/{freecomment_id}", method=RequestMethod.GET)
	public String freeCommentDelByCommentId(int freeboard_id,@PathVariable("freecomment_id") int freecomment_id) {
		freeCommentService.deleteByCommentId(freecomment_id);
		return "redirect:/user/freeboard/detail/"+freeboard_id;
	}
}
