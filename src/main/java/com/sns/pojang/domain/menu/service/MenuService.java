package com.sns.pojang.domain.menu.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.sns.pojang.domain.member.entity.Member;
import com.sns.pojang.domain.member.exception.MemberNotFoundException;
import com.sns.pojang.domain.member.repository.MemberRepository;
import com.sns.pojang.domain.menu.dto.request.CreateMenuOptionGroupRequest;
import com.sns.pojang.domain.menu.dto.request.CreateMenuOptionRequest;
import com.sns.pojang.domain.menu.dto.request.MenuRequest;
import com.sns.pojang.domain.menu.dto.response.*;
import com.sns.pojang.domain.menu.entity.Menu;
import com.sns.pojang.domain.menu.entity.MenuOption;
import com.sns.pojang.domain.menu.entity.MenuOptionGroup;
import com.sns.pojang.domain.menu.exception.MenuIdNotEqualException;
import com.sns.pojang.domain.menu.exception.MenuNotFoundException;
import com.sns.pojang.domain.menu.exception.MenuOptionGroupNotFoundException;
import com.sns.pojang.domain.menu.exception.MenuOptionNotFoundException;
import com.sns.pojang.domain.menu.repository.MenuOptionGroupRepository;
import com.sns.pojang.domain.menu.repository.MenuOptionRepository;
import com.sns.pojang.domain.menu.repository.MenuRepository;
import com.sns.pojang.domain.store.entity.Store;
import com.sns.pojang.domain.store.exception.StoreIdNotEqualException;
import com.sns.pojang.domain.store.exception.StoreNotFoundException;
import com.sns.pojang.domain.store.repository.StoreRepository;
import com.sns.pojang.global.error.ErrorCode;
import com.sns.pojang.global.error.exception.InvalidValueException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;
    private final MenuOptionGroupRepository menuOptionGroupRepository;
    private final MenuOptionRepository menuOptionRepository;
    private final MemberRepository memberRepository;
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // 메뉴 등록
    @Transactional
    public CreateMenuResponse createMenu(Long storeId, MenuRequest menuRequest) {
        Store findStore = findStore(storeId);
        validateOwner(findStore);
        String imagePath = null;
        if (menuRequest.getMenuImage() != null && !menuRequest.getMenuImage().isEmpty()){
            log.info("이미지 추가");
            imagePath = saveFile(menuRequest.getMenuImage());
        }
        Menu newMenu = menuRequest.toEntity(findStore, imagePath);

        return CreateMenuResponse.from(menuRepository.save(newMenu));
    }

    // 메뉴 옵션 그룹 등록
    @Transactional
    public List<CreateMenuOptionGroupResponse> createMenuOptionGroup(
            Long storeId, Long menuId, List<CreateMenuOptionGroupRequest> createMenuOptionGroupRequests) {
        Store findStore = findStore(storeId);
        validateOwner(findStore);
        Menu findMenu = findMenu(menuId);
        validateStore(findStore.getId(), findMenu);
        List<CreateMenuOptionGroupResponse> responses = new ArrayList<>();

        for (CreateMenuOptionGroupRequest request : createMenuOptionGroupRequests){
            MenuOptionGroup menuOptionGroup = request.toEntity(findMenu);

            // repository에 save 후,
            // 메뉴 Entity에 menuOptionGroup List에 생성된 menuOptionGroup add
            findMenu.getMenuOptionGroups().add(menuOptionGroupRepository.save(menuOptionGroup));
            responses.add(CreateMenuOptionGroupResponse.from(menuOptionGroup));
        }

        if (responses.isEmpty()){
            throw new InvalidValueException(ErrorCode.INVALID_INPUT_VALUE);
        }

        return responses;
    }

    // 메뉴 옵션 등록
    public List<CreateMenuOptionResponse> createMenuOption(
            Long storeId, Long menuId, Long menuOptionGroupId,
            List<CreateMenuOptionRequest> createMenuOptionRequests) {
        Store findStore = findStore(storeId);
        validateOwner(findStore);
        Menu findMenu = findMenu(menuId);
        validateStore(findStore.getId(), findMenu);
        MenuOptionGroup findMenuOptionGroup = findMenuOptionGroup(menuOptionGroupId);

        List<CreateMenuOptionResponse> responses = new ArrayList<>();

        for (CreateMenuOptionRequest request : createMenuOptionRequests){
            MenuOption menuOption = request.toEntity(findMenuOptionGroup);
            findMenuOptionGroup.getMenuOptions().add(menuOptionRepository.save(menuOption));
            responses.add(CreateMenuOptionResponse.from(menuOption));
        }

        return responses;
    }

    // 메뉴 수정
    @Transactional
    public CreateMenuResponse updateMenu(Long storeId, Long menuId, MenuRequest menuRequest)
            throws StoreIdNotEqualException{
        Store findStore = findStore(storeId);
        validateOwner(findStore);
        Menu findMenu = findMenu(menuId);

        // 입력된 storeId와 수정할 메뉴의 storeId의 일치 여부 확인
        validateStore(storeId, findMenu);
        String imagePath = null;
        if (menuRequest.getMenuImage() != null){
            // 기존 s3에 업로드 된 이미지 삭제
            if (findMenu.getImageUrl() != null){
                try {
                    amazonS3Client.deleteObject(bucket, findMenu.getImageUrl());
                } catch (SdkClientException e){
                    log.error("Not able to delete from S3: " + e.getMessage(), e);
                }
            }
            MultipartFile menuImage = menuRequest.getMenuImage();
            if (menuImage != null && !menuImage.isEmpty()){
                imagePath = saveFile(menuImage);
            }
        } else {
            // DB에 image url이 있다면 image 값 유지
            if (findMenu.getImageUrl() != null) {
                imagePath = findMenu.getImageUrl();
            }
        }

        Menu updatedMenu = findMenu.updateMenu(menuRequest.getName(), menuRequest.getMenuInfo(),
                menuRequest.getPrice(), imagePath, findStore);

        return CreateMenuResponse.from(updatedMenu);
    }

    // 메뉴 삭제
    @Transactional
    public void deleteMenu(Long storeId, Long menuId) {
        Store findStore = findStore(storeId);
        validateOwner(findStore);
        Menu findMenu = findMenu(menuId);
        validateStore(storeId, findMenu);
        findMenu.updateDeleteYn();
    }

    // 메뉴 이미지 조회
    @Transactional
    public Resource findImage(Long storeId, Long menuId) {
        Menu findMenu = findMenu(menuId);
        validateStore(storeId, findMenu);
        // 삭제된 메뉴는 조회 불가
        if (findMenu.getDeleteYn().equals("Y")){
            throw new MenuNotFoundException();
        }
        String imagePath = findMenu.getImageUrl();
        Path path = Paths.get(imagePath);
        Resource resource;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Url Form Is Not Valid");
        }
        return resource;
    }

    // 특정 가게의 메뉴 목록 조회
    @Transactional
    public List<MenuResponse> getMenus(Long storeId, Pageable pageable) throws StoreNotFoundException{
        Store findStore = findStore(storeId);
        // deleteYn이 N인 메뉴들만 조회
        Page<Menu> menus = menuRepository.findByDeleteYnAndStoreId("N", findStore.getId(), pageable);
        List<Menu> menuList = menus.getContent();

        List<MenuResponse> menuResponses = new ArrayList<>();

        for (Menu menu : menuList){
            URL url = amazonS3Client.getUrl(bucket, menu.getImageUrl());
            menuResponses.add(MenuResponse.from(menu, url.toString()));
        }

        return menuResponses;
    }

    // 메뉴 상세 조회
    @Transactional
    public MenuDetailResponse getMenuDetail(Long storeId, Long menuId){
        Store findStore = findStore(storeId);
        Menu findMenu = findMenu(menuId);
        validateStore(findStore.getId(), findMenu);

        return MenuDetailResponse.from(findMenu);
    }

    // 메뉴 옵션 조회
    @Transactional
    public MenuOptionResponse getMenuOption(Long storeId, Long optionId) {
        findStore(storeId);
        MenuOption findMenuOption = findMenuOption(optionId);

        return MenuOptionResponse.from(findMenuOption);
    }

    private Store findStore(Long storeId){
        return storeRepository.findById(storeId)
                .orElseThrow(StoreNotFoundException::new);
    }

    private Menu findMenu(Long menuId){
        return menuRepository.findById(menuId)
                .orElseThrow(MenuNotFoundException::new);
    }

    private MenuOptionGroup findMenuOptionGroup(Long menuOptionGroupId){
        return menuOptionGroupRepository.findById(menuOptionGroupId)
                .orElseThrow(MenuOptionGroupNotFoundException::new);
    }

    private MenuOption findMenuOption(Long menuOptionId){
        return menuOptionRepository.findById(menuOptionId)
                .orElseThrow(MenuOptionNotFoundException::new);
    }

    // 메뉴의 storeId와 입력 storeId 일치 여부 검증
    private void validateStore(Long storeId, Menu menu){
        Store store = menu.getStore();
        if (!storeId.equals(store.getId())){
            throw new StoreIdNotEqualException();
        }
    }

    // 메뉴의 storeId와 입력 storeId 일치 여부 검증
    private void validateMenu(Long menuId, MenuOptionGroup menuOptionGroup){
        Menu menu = menuOptionGroup.getMenu();
        if (!menuId.equals(menu.getId())){
            throw new MenuIdNotEqualException();
        }
    }

    // 가게 등록한 Owner인지 검증
    private void validateOwner(Store store){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member findMember = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(MemberNotFoundException::new);
        if (!store.getMember().equals(findMember)){
            throw new AccessDeniedException(store.getName() + "의 사장님이 아닙니다.");
        }
    }

    private String saveFile(MultipartFile file){
        String fileUrl;
        if (file.isEmpty()){
            return null;
        }
        try {
            fileUrl = UUID.randomUUID() + "_" + file.getOriginalFilename();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());
            amazonS3Client.putObject(bucket, fileUrl, file.getInputStream(), metadata);
        } catch (IOException e){
            throw new IllegalArgumentException("Image is not available");
        }
        return fileUrl;
    }


}
