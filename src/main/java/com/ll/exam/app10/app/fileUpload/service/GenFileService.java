package com.ll.exam.app10.app.fileUpload.service;

import com.ll.exam.app10.app.article.entity.Article;
import com.ll.exam.app10.app.fileUpload.entity.GenFile;
import com.ll.exam.app10.app.fileUpload.repository.GenFileRepository;
import com.ll.exam.app10.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class GenFileService {
    private final GenFileRepository genFileRepository;

    public void saveFiles(Article article, Map<String, MultipartFile> fileMap) {
        String relTypeCode = "article";
        long relId = article.getId();
        
        //  keySet() 메소드는 맵에 포함된 키의 세트 보기를 리턴한다. 
        //  따라서 다음을 사용하여 맵을 반복할 수 있다. 
        //  keySet() 각 키 호출에 대해 map.get(key) 값을 가져온다.

        for (String inputName : fileMap.keySet()) {
            MultipartFile multipartFile = fileMap.get(inputName);

            String[] inputNameBits = inputName.split("__");

            String typeCode = inputNameBits[0];
            String type2Code = inputNameBits[1];
            String originFileName = multipartFile.getOriginalFilename();
            String fileExt = Util.file.getExt(originFileName);
            String fileExtTypeCode = Util.file.getFileExtTypeCodeFromFileExt(fileExt);
            String fileExtType2Code = Util.file.getFileExtType2CodeFromFileExt(fileExt);
            int fileNo = Integer.parseInt(inputNameBits[2]);
            int fileSize = (int) multipartFile.getSize();
            String fileDir = relTypeCode + "/" + Util.date.getCurrentDateFormatted("yyyy_MM_dd");

            GenFile genFile = GenFile
                    .builder()
                    .relTypeCode(relTypeCode)
                    .relId(relId)
                    .typeCode(typeCode)
                    .type2Code(type2Code)
                    .fileExtTypeCode(fileExtTypeCode)
                    .fileExtType2Code(fileExtType2Code)
                    .fileNo(fileNo)
                    .fileSize(fileSize)
                    .fileDir(fileDir)
                    .fileExt(fileExt)
                    .originFileName(originFileName)
                    .build();

            genFileRepository.save(genFile);
        }
    }
}