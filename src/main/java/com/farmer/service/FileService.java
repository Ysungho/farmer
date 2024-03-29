package com.farmer.service;
/* 파일을 처리하는 클래스
* 파일을 업로드하는 메소드와 삭제하는 메소드 작성 */

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
@Log
public class FileService {
    public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception {

        //↓UUID는 서로 다른 개체들을 구별하기 위해서 사용함.
        //↓실제 사용 시 중복도리 가능성이 거의 없기 때문에 파일의 이름으로 사용하면 파일명 중복 문제를 해결 할 수 있음
        UUID uuid = UUID.randomUUID();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String savedFileName = uuid.toString() + extension;//UUID로 받은 값과 원래 파일의 이름의 활장자를 조합해서 저장될 파일 이름을 만듬
        String fileUploadFullUrl = uploadPath + "/" + savedFileName;

        //↓FileOutputStrea 클래스는 바이트 단위의 출력을 내보내는 클래스.
        //↓생성자로 파일이 저장될 위치와 파일의 이름을 넘겨 파일에 쓸 파일 출력 스트림을 만듬
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        fos.write(fileData);//fileData를 파일 출력 스트림에 입력
        fos.close();
        return savedFileName;//업로드 된 파일의 이름을 반환
    }

    public void deleteFile(String filePath) throws Exception {
        File deleteFile = new File(filePath);//파일이 저장된 경로를 이용하여 파일 객체를 생성
        if (deleteFile.exists()) {//해당 파일이 존재하면 파일을 삭제
            deleteFile.delete();
            log.info("파일을 삭제하였습니다.");
        } else {
            log.info("파일이 존재하지 않습니다.");
        }
    }
}
