package com.aroundog.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.aroundog.common.exception.AdoptRegistFailException;
import com.aroundog.common.exception.AdoptboardRegistFailException;
import com.aroundog.common.exception.DeleteFailException;
import com.aroundog.common.exception.EditFailException;
import com.aroundog.common.file.FileManager;
import com.aroundog.model.domain.Adopt;
import com.aroundog.model.domain.Adoptboard;
import com.aroundog.model.domain.Member;
import com.aroundog.model.service.AdoptService;
import com.aroundog.model.service.AdoptboardService;
import com.aroundog.model.service.AdoptdogService;
import com.aroundog.model.service.TypeService;

@Controller
public class AdoptController {

   @Autowired
   private AdoptService adoptService;
   @Autowired
   private AdoptdogService adoptdogService;
   @Autowired
   private AdoptboardService adoptboardService;
   
   @Autowired //타입불러오기 위한 서비스
   private TypeService typeService;
   @Autowired //파일 이름명 바꾸기 위한 FileManager
   private FileManager fileManager;
   
   
   MultipartFile myFile;
   String img;
   String realPath;
   
   /*---------------------------------------관리자 관련(입양 업로드 관련)----------------------------------------------------------------*/
   /*
    * // 관리자: 입양 업로드 게시글 목록 보기
    * 
    * @RequestMapping(value="/admin/adoptboardList", method=RequestMethod.GET)
    * public ModelAndView adoptboardselectAll() {
    * System.out.println("관리자가 입양업로드 목록보기"); List adoptboardList=
    * adoptboardService.selectAll(); ModelAndView mav = new
    * ModelAndView("admin/adoptmanager/index"); mav.addObject("adoptboardList",
    * adoptboardList); return mav; }
    */
   
   // 관리자: 입양 업로드  게시글 1건  보기
   @RequestMapping(value="/admin/adoptmanager/detail", method=RequestMethod.GET)
   public ModelAndView boardSelect(int adoptboard_id) {
      Adoptboard adoptboard= adoptboardService.select(adoptboard_id);
      System.out.println("adoptboard의 id"+adoptboard.getAdoptboard_id());
      System.out.println("adoptboard의 강아지가 있나"+adoptboard.getAdoptdog());
      System.out.println("adoptboard의 강아지 type의 id"+adoptboard.getAdoptdog().getType());
      System.out.println("adoptboard의 강아지 type의 이름"+adoptboard.getAdoptdog().getType().getInfo());
      
      List typeList= typeService.selectAll();
      ModelAndView mav = new ModelAndView("admin/adoptmanager/detail");
      mav.addObject("adoptboard", adoptboard);
      mav.addObject("typeList", typeList);
      return mav;
   }
   
// 관리자: 입양 업로드 게시글 쓰기 /트랜잭션 처리 함
@RequestMapping(value="/admin/adoptmanager/regist", method=RequestMethod.POST)
public String insert(Adoptboard adoptboard, HttpServletRequest request) {
      //파일 처리
   myFile=adoptboard.getAdoptdog().getMyFile();
   img=myFile.getOriginalFilename();
   realPath=request.getServletContext().getRealPath("/data/dogs");
   File uploadFile=null;
   
   try {
      uploadFile=new File(realPath+"/"+img); //업로드 될 파일의 경로!!와 이름
      myFile.transferTo(new File(realPath+"/"+img)); //업로드!
      img=fileManager.reNameByDate(uploadFile, realPath);
      
      if(img!=null) {
         adoptboard.getAdoptdog().setImg(img);
         adoptboardService.insert(adoptboard);
      }
   } catch (IllegalStateException | IOException e) {
      e.printStackTrace();
   }
   return "redirect:/admin/adoptboardList";
}

// 관리자: 입양 업로드 게시글 수정 /트랜잭션 처리 함
@RequestMapping(value="/admin/adoptmanager/update", method=RequestMethod.POST)
public String update(Adoptboard adoptboard, HttpServletRequest request) {
   //받은 어답보드의 연결된 강아지의 _ID를 뽑아내기
   int adoptboard_id=adoptboard.getAdoptboard_id();
   Adoptboard board=adoptboardService.select(adoptboard_id);
   //뽑아낸 board로 강아지 id 뽑기
   int adoptdog_id=board.getAdoptdog().getAdoptdog_id();
   String dbImg=board.getAdoptdog().getImg();
      
      //수정할 파일
      MultipartFile myFile=adoptboard.getAdoptdog().getMyFile();
      //교체할 파일이 있는 위치
      String realPath=request.getServletContext().getRealPath("/data/dogs");
      System.out.println("realPath"+realPath);
      try {
            adoptboard.getAdoptdog().setImg(dbImg); //db에 저장된 이름을 pojo에 넣어주기
            adoptboard.getAdoptdog().setAdoptdog_id(adoptdog_id);             //뽑은 id 빈 강아지에 넣어주기
            
            adoptboardService.update(adoptboard);
               myFile.transferTo(new File(realPath+"/"+dbImg)); // 이미지 교체! 이거 서비스에서 해주기!!!!!!
               
            
      } catch (IllegalStateException | IOException e) {
         e.printStackTrace();
      }
      
      return "redirect:/admin/adoptboardList";
}

// 관리자: 입양 게시글 삭제 /트랜잭션 처리 함
@RequestMapping(value="/admin/adoptmanager/delete", method=RequestMethod.GET)
public String delete(int adoptboard_id) {
   adoptboardService.delete(adoptboard_id);
   return "redirect:/admin/adoptboardList";
}
   /*------------------------------------------관리자 관련(입양 신청 관련)----------------------------------------------------*/
   // 관리자: 입양요청 게시글 목록 보기
//   @RequestMapping(value="/admin/adoptList", method=RequestMethod.GET)
//   public ModelAndView selectAll() {
//      System.out.println("관리자가 입양신청 목록보기 요청");
//      List adoptList= adoptService.selectAll();
//      ModelAndView mav = new ModelAndView("admin/adopt/index");
//      mav.addObject("adoptList", adoptList);
//      return mav;
//   }
   
   // 관리자: 입양요청 게시글 1건 상세보기
   @RequestMapping(value="/admin/adopt/detail", method=RequestMethod.GET)
   public ModelAndView adoptSelect(int adopt_id) {
      System.out.println("adoptSelect에서 넘겨받은 adopt_id: "+adopt_id);
      Adopt adopt= adoptService.select(adopt_id);
      
      ModelAndView mav= new ModelAndView("admin/adopt/detail");
      mav.addObject("adopt", adopt);
      return mav;
   }
   
   // 관리자: 입양요청 게시글 수정 & 확인완료
   @RequestMapping(value="/admin/adopt/update", method=RequestMethod.POST)
   public String adoptUpdate(Adopt adopt) {
      adoptService.update(adopt);
      return "redirect:/adopts";
   }
   /*------------------------------------------관리자 관련(type)----------------------------------------------------*/
   // 관리자: type 목록 불러오기
   @RequestMapping(value="/admin/adoptmanager/type", method=RequestMethod.GET)
   public ModelAndView typeSelectAll() {
      List typeList= typeService.selectAll();
      ModelAndView mav = new ModelAndView("admin/adoptmanager/regist");
      mav.addObject("typeList", typeList);
      return mav;
   }
   
   /*------------------------------------------유저 관련(입양)----------------------------------------------------*/
   // 유저 : 입양게시물 리스트 가져오기
      @RequestMapping(value="/user/adopt/adoptboardList", method=RequestMethod.GET)
      public ModelAndView adoptboardSelectAll() {
         List adoptboardList= adoptboardService.selectAll();
         ModelAndView mav= new ModelAndView("user/adopt/adoptboardList");
         mav.addObject("adoptboardList", adoptboardList);
         return mav;
      } 
   
   // 유저 : 입양신청 등록하기
   @RequestMapping(value="/user/adopt/adoption", method=RequestMethod.POST)
   public String adoptRegist(Adopt adopt, HttpServletRequest request) {
      Member member=(Member)request.getSession().getAttribute("member");
      int adoptboard_id=adopt.getAdoptboard_id();
      Adoptboard adoptboard=adoptboardService.select(adoptboard_id);
      adopt.setAdoptboard(adoptboard);
      adopt.setMember(member);
      adoptService.insert(adopt);
      return "redirect:/user/adopt/adoptboardList";
   }

   // 유저:강아지 한마리의 상세보기
   @RequestMapping(value="/user/adopt/adoptboardDetail", method=RequestMethod.GET)
   public ModelAndView adoptdogSelect(int adoptboard_id) {
      Adoptboard adoptboard= adoptboardService.select(adoptboard_id);
      List adoptboardList= adoptboardService.selectAll();
      ModelAndView mav= new ModelAndView("user/adopt/adoptboardDetail");
      mav.addObject("adoptboard", adoptboard);
      mav.addObject("adoptboardList", adoptboardList);
      return mav;
   }

   /*-------------------------------------------------예외처리-----------------------------------------------------------*/
   
   // 유저 : 입양 신청 등록 예외 처리
   @ExceptionHandler(AdoptRegistFailException.class)
   public ModelAndView adoptRegistFail(AdoptRegistFailException e) {
      ModelAndView mav = new ModelAndView("user/error/adoptError");
      mav.addObject("err", e.getMessage());
      return mav;
   }
   // 관리자 : 입양 게시글 등록 예외 처리
   @ExceptionHandler(AdoptboardRegistFailException.class)
   public ModelAndView adoptboardRegistFail(AdoptboardRegistFailException e) {
      ModelAndView mav = new ModelAndView("user/error/adoptError");
      mav.addObject("err", e.getMessage());
      return mav;
   }
   // 관리자 :  입양 게시글 삭제 예외 처리
   @ExceptionHandler(DeleteFailException.class)
   public ModelAndView adoptboardDeleteFail(DeleteFailException e) {
      ModelAndView mav = new ModelAndView("user/error/adoptError");
      mav.addObject("err", e.getMessage());
      return mav;
   }
   // 관리자 :  입양 게시글 수정 예외 처리
   @ExceptionHandler(EditFailException.class)
   public ModelAndView adoptboardEditFail(EditFailException e) {
      ModelAndView mav = new ModelAndView("admin/error/adoptboardEditError"); //에러페이지에서.. 다시 업로드를 하는 걸로!!!!
      mav.addObject("err", e.getMessage());
      return mav;
   }
   
}