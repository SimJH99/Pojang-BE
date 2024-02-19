package com.sns.pojang.domain.menu.service;

import com.sns.pojang.domain.menu.dto.request.MenuRequest;
import com.sns.pojang.domain.menu.dto.response.MenuResponse;
import com.sns.pojang.domain.menu.entity.Menu;
import com.sns.pojang.domain.menu.repository.MenuRepository;
import com.sns.pojang.domain.store.entity.Store;
import com.sns.pojang.domain.store.exception.StoreNotFoundException;
import com.sns.pojang.domain.store.repository.StoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Service
@Slf4j
public class MenuService {
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;
    @Value("${image.path}")
    private String imagePath;

    @Autowired
    public MenuService(MenuRepository menuRepository, StoreRepository storeRepository) {
        this.menuRepository = menuRepository;
        this.storeRepository = storeRepository;
    }

    @Transactional
    public MenuResponse createMenu(Long storeId, MenuRequest menuRequest) {
        Store findStore = findStore(storeId);
        Path path;
        if (menuRequest.getMenuImage() != null){
            log.info("이미지 추가");
            MultipartFile menuImage = menuRequest.getMenuImage();
            String fileName = menuImage.getOriginalFilename(); // 확장자 포함한 파일명 추출
            path = Paths.get(imagePath,fileName);
            try {
                byte[] bytes = menuImage.getBytes(); // 이미지 파일을 바이트로 변환
                // 해당 경로의 폴더에 이미지 파일 추가. 이미 동일 파일이 있으면 덮어 쓰기(Write), 없으면 Create
                Files.write(path, bytes, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            } catch (IOException e) {
                throw new IllegalArgumentException("Image Not Available");
            }
        } else {
            // 첨부된 이미지가 없을 경우, 기본 이미지로 세팅해주기
            path = Paths.get(imagePath, "no_image.jpg");
        }
        Menu newMenu = menuRequest.toEntity(findStore, path);

        return MenuResponse.from(menuRepository.save(newMenu));
    }

    private Store findStore(Long storeId){
        return storeRepository.findById(storeId)
                .orElseThrow(StoreNotFoundException::new);
    }
}
