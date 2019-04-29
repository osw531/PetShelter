package com.aroundog.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aroundog.common.exception.DeleteFailException;
import com.aroundog.commons.Pager;
import com.aroundog.model.domain.Admin;
import com.aroundog.model.domain.FreeBoard;
import com.aroundog.model.domain.Report;
import com.aroundog.model.domain.ReportImg;
import com.aroundog.model.service.AdminService;
import com.aroundog.model.service.AdoptService;
import com.aroundog.model.service.AdoptboardService;
import com.aroundog.model.service.FreeBoardService;
import com.aroundog.model.service.ReportService;

@Controller
public class AdminController {

	@Autowired
	private AdminService adminService;
	@Autowired
	private FreeBoardService freeBoardService;
	@Autowired
	private ReportService reportService;
	@Autowired
	private AdoptboardService adoptboardService;
	@Autowired
    private AdoptService adoptService;
	private Pager pager=new Pager();
	

	//userservice-->���
	//������ �α��� ��û
	@RequestMapping(value="/admin/login", method=RequestMethod.GET)
	public String adminLogin(Admin admin, HttpServletRequest request) {
		Admin obj=adminService.loginCheck(admin);
		request.getSession().setAttribute("admin", obj);
		return "redirect:/admin/main";
	}
	
	//������ �α��� ������ �̵�
	@RequestMapping(value="/admin/main", method=RequestMethod.GET)
	public String goMain(HttpServletRequest request) {
		//�α��� ������ �ٷ� ȸ�������� ������������ DB���� ȸ�� ���� �ܾ�;���
		List freeBoardList=freeBoardService.selectAll();
		//System.out.println(freeBoardList);
		request.setAttribute("freeBoardList", freeBoardList);
		return "admin/main/main";
	}
	
	//������ ��忡�� ����Ʈ �ҷ�����
	@RequestMapping(value="/users",method=RequestMethod.GET)
	public String userList() {	
		
		return "admin/user/index";
	}
	
	//------------------------������ ��Ʈ------------------------------------------------------------
	//�α��� ������ ������ ��忡�� ��� ����Ʈ �ҷ�����
	@RequestMapping(value="/admin/members",method=RequestMethod.GET)
	public String goMemberList(HttpServletRequest request) {   
		return "redirect:/admin/member/list";
	}
	
	@RequestMapping(value="/admin/adoptmanagers",method=RequestMethod.GET)
	public String adoptManagerList(HttpServletRequest request) {   
		return "redirect:/admin/adoptboardList";
	}
	
	
	
	
	
	
	//--------------------------������ ��Ʈ ��----------------------------------------------------------
	//Report ���� ---------------------------------------------#   
	   
	   @RequestMapping(value="/reports",method=RequestMethod.GET)
	   public ModelAndView reportList(HttpServletRequest request) {   
	      List reportList=reportService.selectAll();//�𵨾غ�� ����Ʈ ��ȯ�ϰ�.. jsp���� ����Ʈ �޾Ƽ� ��� ���!!
	      pager.init(request, reportList.size());
	      ModelAndView mav = new ModelAndView("admin/report/index");
	      mav.addObject("pager",pager);
	      mav.addObject("reportList", reportList);
	      return mav;
	   } 
	   
	   @RequestMapping(value="/reports/{report_id}",method=RequestMethod.GET) 
	   public ModelAndView select(@PathVariable("report_id") int report_id) {   
	      ModelAndView mav = new ModelAndView("admin/report/detail"); 
	      Report report =  reportService.select(report_id); 
	      mav.addObject("report",report); 
	      return mav;
	   }
	   
	   @RequestMapping(value="/reportsimg/{report_id}",method=RequestMethod.GET)
	   @ResponseBody
	   public String selectImg(@PathVariable("report_id") int report_id) {
	      List<ReportImg> imgList = reportService.selectImg(report_id);
	      JSONArray jsonArray = new JSONArray();
	      for(int i = 0;i<imgList.size();i++) {
	         ReportImg ri = imgList.get(i);
	         JSONObject obj = new JSONObject();
	         obj.put("img", ri.getImg());         
	         jsonArray.add(obj);
	      }
	      return jsonArray.toString();
	      
	   }
	   
	   @RequestMapping(value="/reports/check",method=RequestMethod.POST)
	   public String update(@RequestParam("report_id") int report_id) {
	      reportService.update(report_id);
	      return "redirect:/reports";
	   }
	   
	   //#---------------------------------------------Report ���� ��
	   
	   
	   //#---------------------------------------------Report ���� ��
		@RequestMapping(value="/adopts",method=RequestMethod.GET)
			public ModelAndView adoptList() {   
			System.out.println("�����ڰ� �Ծ��û ��Ϻ��� ��û");
			List adoptList= adoptService.selectAll();
			ModelAndView mav = new ModelAndView("admin/adopt/index");
			mav.addObject("adoptList", adoptList);
			return mav;
		}
	//------------------------�����Խ��� ����--------------------------------------------------------------
	
	//�����Խ��� �Խù� 1�� ����
	@RequestMapping(value="/admin/freeboard/del/{freeboard_id}",method=RequestMethod.DELETE)
	@ResponseBody
	public String freeboardDel(@PathVariable("freeboard_id") int freeboard_id) {	
		freeBoardService.delete(freeboard_id);
		return "{\"result\":1,\"msg\":\"1\"}";
	}
	
	//������ ��������
	@RequestMapping(value="/admin/freeboard",method=RequestMethod.GET)
	public ModelAndView freeBoardChangePage(@RequestParam(value="currentPage", defaultValue="1" , required=false) int currentPage,HttpServletRequest request) {	
		ModelAndView mav = new ModelAndView("admin/freeboard/index");
		List freeboardList=freeBoardService.selectAll();
		pager.init(request, freeboardList.size());
		mav.addObject("freeBoardList", freeboardList);
		mav.addObject("pager", pager);	
		return mav;
	}
	
	//�����Խ��� ������ ����
	@RequestMapping(value="/admin/freeboard/detail/{freeboard_id}", method=RequestMethod.GET)
	public ModelAndView detail(@PathVariable("freeboard_id") int freeboard_id) {
		System.out.println("�����Խ��� �󼼺���");
		System.out.println(freeboard_id);
		FreeBoard freeboard=freeBoardService.select(freeboard_id);
		ModelAndView mav = new ModelAndView("admin/freeboard/detail");
		mav.addObject("freeboard", freeboard);
		return mav;
	}
	
	
	//---------------------------�����Խ��� ��--------------------------------------------------------	
	
	@RequestMapping(value="/adoptmanagers",method=RequestMethod.GET)
	public String adoptManagerList() {	
		return "admin/adoptmanager/index";
	}
	
	@ExceptionHandler(DeleteFailException.class)
	@ResponseBody
	public String deleteException(DeleteFailException e) {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"result\":0");
		sb.append("}");
		return sb.toString();
	}


	
}
