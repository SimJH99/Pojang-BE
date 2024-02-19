package com.sns.pojang.domain.order.service;

import com.sns.pojang.domain.member.entity.Member;
import com.sns.pojang.domain.member.exception.MemberNotFoundException;
import com.sns.pojang.domain.member.repository.MemberRepository;
import com.sns.pojang.domain.menu.entity.Menu;
import com.sns.pojang.domain.menu.entity.MenuOption;
import com.sns.pojang.domain.menu.exception.MenuNotFoundException;
import com.sns.pojang.domain.menu.exception.MenuOptionNotFoundException;
import com.sns.pojang.domain.menu.repository.MenuOptionRepository;
import com.sns.pojang.domain.menu.repository.MenuRepository;
import com.sns.pojang.domain.order.dto.request.OrderRequest;
import com.sns.pojang.domain.order.dto.request.SelectedMenuOptionRequest;
import com.sns.pojang.domain.order.dto.request.SelectedMenuRequest;
import com.sns.pojang.domain.order.dto.response.CreateOrderResponse;
import com.sns.pojang.domain.order.entity.Order;
import com.sns.pojang.domain.order.entity.OrderMenu;
import com.sns.pojang.domain.order.exception.InvalidTotalPriceException;
import com.sns.pojang.domain.order.repository.OrderRepository;
import com.sns.pojang.domain.store.entity.Store;
import com.sns.pojang.domain.store.exception.StoreNotFoundException;
import com.sns.pojang.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final MenuOptionRepository menuOptionRepository;

    // 메뉴 주문
    @Transactional
    public CreateOrderResponse createOrder(OrderRequest orderRequest) {
        Member findMember = findMember();
        Store findStore = findStore(orderRequest.getStoreId());
        // 총 주문 금액 검증
        validateTotalPrice(orderRequest.getSelectedMenus(), orderRequest.getTotalPrice());
        Order order = orderRequest.toEntity(findMember, findStore);

        for (SelectedMenuRequest selectedMenu : orderRequest.getSelectedMenus()){
            Menu findMenu = findMenu(selectedMenu.getMenuId());
            OrderMenu orderMenu = OrderMenu.builder()
                    .quantity(selectedMenu.getQuantity())
                    .menu(findMenu)
                    .order(order)
                    .build();
            order.getOrderMenus().add(orderMenu);
        }
        return CreateOrderResponse.from(orderRepository.save(order));
    }

    // 프론트에서 받아온 총 주문 금액 검증 (프론트는 중간에 연산이 조작 되기 쉬움)
    private void validateTotalPrice(List<SelectedMenuRequest> selectedMenus, int totalPrice){
        int calculatedTotalPrice = 0;
        for (SelectedMenuRequest selectedMenuRequest : selectedMenus){
            Menu menu = findMenu(selectedMenuRequest.getMenuId());
            int menuOptionTotal = 0;
            for (SelectedMenuOptionRequest selectedMenuOptionRequest : selectedMenuRequest.getSelectedMenuOptions()){
                for (Long menuOptionId : selectedMenuOptionRequest.getSelectedMenuOptions()){
                    MenuOption menuOption = findMenuOption(menuOptionId);
                    menuOptionTotal += menuOption.getAdditionalPrice();
                }
            }
            calculatedTotalPrice += menu.getPrice() + menuOptionTotal;
        }
        if (calculatedTotalPrice != totalPrice){
            throw new InvalidTotalPriceException();
        }
    }

    private Member findMember(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return memberRepository.findByEmail(authentication.getName())
                .orElseThrow(MemberNotFoundException::new);
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
}
