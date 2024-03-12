package com.sns.pojang.domain.menu.service;

import com.sns.pojang.domain.member.entity.Member;
import com.sns.pojang.domain.member.exception.MemberNotFoundException;
import com.sns.pojang.domain.member.repository.MemberRepository;
import com.sns.pojang.domain.menu.dto.request.FindMenuOptionsRequest;
import com.sns.pojang.domain.menu.dto.request.MenuRequest;
import com.sns.pojang.domain.menu.dto.request.OptionGroupRequest;
import com.sns.pojang.domain.menu.dto.request.OptionRequest;
import com.sns.pojang.domain.menu.dto.response.CreateMenuResponse;
import com.sns.pojang.domain.menu.dto.response.MenuOptionGroupResponse;
import com.sns.pojang.domain.menu.dto.response.MenuOptionInfoResponse;
import com.sns.pojang.domain.menu.dto.response.MenuResponse;
import com.sns.pojang.domain.menu.entity.Menu;
import com.sns.pojang.domain.menu.entity.MenuOption;
import com.sns.pojang.domain.menu.entity.MenuOptionGroup;
import com.sns.pojang.domain.menu.exception.MenuIdNotEqualException;
import com.sns.pojang.domain.menu.exception.MenuNotFoundException;
import com.sns.pojang.domain.menu.exception.MenuOptionNotFoundException;
import com.sns.pojang.domain.menu.repository.MenuOptionGroupRepository;
import com.sns.pojang.domain.menu.repository.MenuOptionRepository;
import com.sns.pojang.domain.menu.repository.MenuRepository;
import com.sns.pojang.domain.store.entity.Store;
import com.sns.pojang.domain.store.exception.StoreIdNotEqualException;
import com.sns.pojang.domain.store.exception.StoreNotFoundException;
import com.sns.pojang.domain.store.repository.StoreRepository;
import com.sns.pojang.global.config.s3.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MenuService {
    private static final String FILE_TYPE = "menus";

    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;
    private final MenuOptionGroupRepository menuOptionGroupRepository;
    private final MenuOptionRepository menuOptionRepository;
    private final MemberRepository memberRepository;
    private final S3Service s3Service;

    // 메뉴 등록
    @Transactional
    public CreateMenuResponse createMenu(Long storeId, MenuRequest menuRequest) {
        Store findStore = findStore(storeId);
        validateOwner(findStore);
        String imagePath = null;
        if (menuRequest.getImage() != null && !menuRequest.getImage().isEmpty()){
            imagePath = s3Service.uploadFile(FILE_TYPE, menuRequest.getImage());
        }
        Menu newMenu = menuRequest.toEntity(findStore, imagePath);

        return CreateMenuResponse.from(menuRepository.save(newMenu));
    }

    // 메뉴 옵션 등록
    @Transactional
    public MenuOptionGroupResponse createOptions(
            Long storeId, Long menuId, OptionGroupRequest optionGroupRequest) {
        log.info("옵션 생성 Service Start");
        Store findStore = findStore(storeId);
        validateOwner(findStore);
        Menu findMenu = findMenu(menuId);
        validateStore(findStore.getId(), findMenu);

        MenuOptionGroup newOptionGroup = optionGroupRequest.toEntity();

        for (OptionRequest optionRequest : optionGroupRequest.getOptions()){
            MenuOption newMenuOption = optionRequest.toEntity();
            newMenuOption.attachOptionGroup(newOptionGroup);
            log.info("옵션: " + newMenuOption);
        }
        newOptionGroup.attachMenu(findMenu);
        log.info("옵션 그룹: " + newOptionGroup);
        menuOptionGroupRepository.save(newOptionGroup);
        log.info("DB 저장 완료");

        return MenuOptionGroupResponse.from(newOptionGroup);
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
        if (menuRequest.getImage() != null){
            // 추후 기존 s3에 업로드 된 이미지 삭제 로직 구현

            //
            MultipartFile menuImage = menuRequest.getImage();
            if (menuImage != null && !menuImage.isEmpty()){
                imagePath = s3Service.uploadFile(FILE_TYPE, menuImage);
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
            menuResponses.add(MenuResponse.from(menu, menu.getImageUrl()));
        }

        return menuResponses;
    }

    // 메뉴 상세 조회
    @Transactional
    public MenuResponse getMenuDetail(Long storeId, Long menuId){
        Store findStore = findStore(storeId);
        Menu findMenu = findMenu(menuId);
        validateStore(findStore.getId(), findMenu);

        return MenuResponse.from(findMenu, findMenu.getImageUrl());
    }

    // 메뉴 옵션 조회
    @Transactional
    public List<MenuOptionInfoResponse> findOptionObjects(Long storeId, FindMenuOptionsRequest findMenuOptionsRequest) {
        findStore(storeId);
        List<MenuOptionInfoResponse> menuOptionInfoResponses = new ArrayList<>();
        if (findMenuOptionsRequest.getOptionIds() != null){
            for (Long optionId : findMenuOptionsRequest.getOptionIds()){
                MenuOption menuOption = findMenuOption(optionId);
                menuOptionInfoResponses.add(MenuOptionInfoResponse.from(menuOption));
            }
        }
        return menuOptionInfoResponses;
    }

    private Store findStore(Long storeId){
        return storeRepository.findById(storeId)
                .orElseThrow(StoreNotFoundException::new);
    }

    private Menu findMenu(Long menuId){
        return menuRepository.findById(menuId)
                .orElseThrow(MenuNotFoundException::new);
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
}
