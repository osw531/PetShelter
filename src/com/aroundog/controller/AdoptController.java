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
   
   @Autowired //Ÿ�Ժҷ����� ���� ����
   private TypeService typeService;
   @Autowired //���� �̸��� �ٲٱ� ���� FileManager
   private FileManager fileManager;
   
   
   MultipartFile myFile;
   String img;
   String realPath;
   
   /*---------------------------------------������ ����(�Ծ� ���ε� ����)----------------------------------------------------------------*/
   /*
    * // ������: �Ծ� ���ε� �Խñ� ��� ����
    * 
    * @RequestMapping(value="/admin/adoptboardList", method=RequestMethod.GET)
    * public ModelAndView adoptboardselectAll() {
    * System.out.println("�����ڰ� �Ծ���ε� ��Ϻ���"); List adoptboardList=
    * adoptboardService.selectAll(); ModelAndView mav = new
    * ModelAndView("admin/adoptmanager/index"); mav.addObject("adoptboardList",
    * adoptboardList); return mav; }
    */
   
   // ������: �Ծ� ���ε�  �Խñ� 1��  ����
   @RequestMapping(value="/admin/adoptmanager/detail", method=RequestMethod.GET)
   public ModelAndView boardSelect(int adoptboard_id) {
      Adoptboard adoptboard= adoptboardService.select(adoptboard_id);
      System.out.println("adoptboard�� id"+adoptboard.getAdoptboard_id());
      System.out.println("adoptboard�� �������� �ֳ�"+adoptboard.getAdoptdog());
      System.out.println("adoptboard�� ������ type�� id"+adoptboard.getAdoptdog().getType());
      System.out.println("adoptboard�� ������ type�� �̸�"+adoptboard.getAdoptdog().getType().getInfo());
      
      List typeList= typeService.selectAll();
      ModelAndView mav = new ModelAndView("admin/adoptmanager/detail");
      mav.addObject("adoptboard", adoptboard);
      mav.addObject("typeList", typeList);
      return mav;
   }
   
// ������: �Ծ� ���ε� �Խñ� ���� /Ʈ����� ó�� ��
@RequestMapping(value="/admin/adoptmanager/regist", method=RequestMethod.POST)
public String insert(Adoptboard adoptboard, HttpServletRequest request) {
      //���� ó��
   myFile=adoptboard.getAdoptdog().getMyFile();
   img=myFile.getOriginalFilename();
   realPath=request.getServletContext().getRealPath("/data/dogs");
   File uploadFile=null;
   
   try {
      uploadFile=new File(realPath+"/"+img); //���ε� �� ������ ���!!�� �̸�
      myFile.transferTo(new File(realPath+"/"+img)); //���ε�!
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

// ������: �Ծ� ���ε� �Խñ� ���� /Ʈ����� ó�� ��
@RequestMapping(value="/admin/adoptmanager/update", method=RequestMethod.POST)
public String update(Adoptboard adoptboard, HttpServletRequest request) {
   //���� ��亸���� ����� �������� _ID�� �̾Ƴ���
   int adoptboard_id=adoptboard.getAdoptboard_id();
   Adoptboard board=adoptboardService.select(adoptboard_id);
   //�̾Ƴ� board�� ������ id �̱�
   int adoptdog_id=board.getAdoptdog().getAdoptdog_id();
   String dbImg=board.getAdoptdog().getImg();
      
      //������ ����
      MultipartFile myFile=adoptboard.getAdoptdog().getMyFile();
      //��ü�� ������ �ִ� ��ġ
      String realPath=request.getServletContext().getRealPath("/data/dogs");
      System.out.println("realPath"+realPath);
      try {
            adoptboard.getAdoptdog().setImg(dbImg); //db�� ����� �̸��� pojo�� �־��ֱ�
            adoptboard.getAdoptdog().setAdoptdog_id(adoptdog_id);             //���� id �� �������� �־��ֱ�
            
            adoptboardService.update(adoptboard);
               myFile.transferTo(new File(realPath+"/"+dbImg)); // �̹��� ��ü! �̰� ���񽺿��� ���ֱ�!!!!!!
               
            
      } catch (IllegalStateException | IOException e) {
         e.printStackTrace();
      }
      
      return "redirect:/admin/adoptboardList";
}

// ������: �Ծ� �Խñ� ���� /Ʈ����� ó�� ��
@RequestMapping(value="/admin/adoptmanager/delete", method=RequestMethod.GET)
public String delete(int adoptboard_id) {
   adoptboardService.delete(adoptboard_id);
   return "redirect:/admin/adoptboardList";
}
   /*------------------------------------------������ ����(�Ծ� ��û ����)----------------------------------------------------*/
   // ������: �Ծ��û �Խñ� ��� ����
//   @RequestMapping(value="/admin/adoptList", method=RequestMethod.GET)
//   public ModelAndView selectAll() {
//      System.out.println("�����ڰ� �Ծ��û ��Ϻ��� ��û");
//      List adoptList= adoptService.selectAll();
//      ModelAndView mav = new ModelAndView("admin/adopt/index");
//      mav.addObject("adoptList", adoptList);
//      return mav;
//   }
   
   // ������: �Ծ��û �Խñ� 1�� �󼼺���
   @RequestMapping(value="/admin/adopt/detail", method=RequestMethod.GET)
   public ModelAndView adoptSelect(int adopt_id) {
      System.out.println("adoptSelect���� �Ѱܹ��� adopt_id: "+adopt_id);
      Adopt adopt= adoptService.select(adopt_id);
      
      ModelAndView mav= new ModelAndView("admin/adopt/detail");
      mav.addObject("adopt", adopt);
      return mav;
   }
   
   // ������: �Ծ��û �Խñ� ���� & Ȯ�οϷ�
   @RequestMapping(value="/admin/adopt/update", method=RequestMethod.POST)
   public String adoptUpdate(Adopt adopt) {
      adoptService.update(adopt);
      return "redirect:/adopts";
   }
   /*------------------------------------------������ ����(type)----------------------------------------------------*/
   // ������: type ��� �ҷ�����
   @RequestMapping(value="/admin/adoptmanager/type", method=RequestMethod.GET)
   public ModelAndView typeSelectAll() {
      List typeList= typeService.selectAll();
      ModelAndView mav = new ModelAndView("admin/adoptmanager/regist");
      mav.addObject("typeList", typeList);
      return mav;
   }
   
   /*------------------------------------------���� ����(�Ծ�)----------------------------------------------------*/
   // ���� : �Ծ�Խù� ����Ʈ ��������
      @RequestMapping(value="/user/adopt/adoptboardList", method=RequestMethod.GET)
      public ModelAndView adoptboardSelectAll() {
         List adoptboardList= adoptboardService.selectAll();
         ModelAndView mav= new ModelAndView("user/adopt/adoptboardList");
         mav.addObject("adoptboardList", adoptboardList);
         return mav;
      } 
   
   // ���� : �Ծ��û ����ϱ�
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

   // ����:������ �Ѹ����� �󼼺���
   @RequestMapping(value="/user/adopt/adoptboardDetail", method=RequestMethod.GET)
   public ModelAndView adoptdogSelect(int adoptboard_id) {
      Adoptboard adoptboard= adoptboardService.select(adoptboard_id);
      List adoptboardList= adoptboardService.selectAll();
      ModelAndView mav= new ModelAndView("user/adopt/adoptboardDetail");
      mav.addObject("adoptboard", adoptboard);
      mav.addObject("adoptboardList", adoptboardList);
      return mav;
   }

   /*-------------------------------------------------����ó��-----------------------------------------------------------*/
   
   // ���� : �Ծ� ��û ��� ���� ó��
   @ExceptionHandler(AdoptRegistFailException.class)
   public ModelAndView adoptRegistFail(AdoptRegistFailException e) {
      ModelAndView mav = new ModelAndView("user/error/adoptError");
      mav.addObject("err", e.getMessage());
      return mav;
   }
   // ������ : �Ծ� �Խñ� ��� ���� ó��
   @ExceptionHandler(AdoptboardRegistFailException.class)
   public ModelAndView adoptboardRegistFail(AdoptboardRegistFailException e) {
      ModelAndView mav = new ModelAndView("user/error/adoptError");
      mav.addObject("err", e.getMessage());
      return mav;
   }
   // ������ :  �Ծ� �Խñ� ���� ���� ó��
   @ExceptionHandler(DeleteFailException.class)
   public ModelAndView adoptboardDeleteFail(DeleteFailException e) {
      ModelAndView mav = new ModelAndView("user/error/adoptError");
      mav.addObject("err", e.getMessage());
      return mav;
   }
   // ������ :  �Ծ� �Խñ� ���� ���� ó��
   @ExceptionHandler(EditFailException.class)
   public ModelAndView adoptboardEditFail(EditFailException e) {
      ModelAndView mav = new ModelAndView("admin/error/adoptboardEditError"); //��������������.. �ٽ� ���ε带 �ϴ� �ɷ�!!!!
      mav.addObject("err", e.getMessage());
      return mav;
   }
   
}