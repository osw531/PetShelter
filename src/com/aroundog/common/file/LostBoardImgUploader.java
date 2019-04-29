package com.aroundog.common.file;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.aroundog.model.domain.LostBoard;

public class LostBoardImgUploader {
   private FileManager fileManager = new FileManager();
   
   public String[] returnFilename(MultipartFile[] myFile, LostBoard lostBoard, 
         String realPath) {
	   System.out.println("�ν�Ʈ�̹������δ� ����");
      String[] filenameList = new String[myFile.length];
      for (int i = 0; i < myFile.length; i++) {
         String filename = myFile[i].getOriginalFilename();
         File uploadFile = null;
         try {
        	 System.out.println(i+"��° �����̸�"+filename);
            uploadFile = new File(realPath + "/" + filename);
            System.out.println("���ε�������"+uploadFile);
            myFile[i].transferTo(new File(realPath + "/" + filename));
            filename = fileManager.reNameByHash(uploadFile, realPath);
           System.out.println(filename+"�� �ٲ� �̸��̱���");
            if (filename != null) {
               filenameList[i] = filename;
            }
         } catch (IllegalStateException | IOException e) {
            System.out.println("�� ����ڴ� ������ ������� �ʾҽ��ϴ�");
         }
      }
      return filenameList;
   }
}