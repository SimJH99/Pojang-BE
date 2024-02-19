    package com.sns.pojang.domain.menu.dto.response;

    import com.sns.pojang.domain.menu.entity.MenuOption;
    import lombok.Builder;
    import lombok.Getter;

    @Builder
    @Getter
    public class CreateMenuOptionResponse {
        private Long menuOptionId;
        private String name;
        private int price;

        public static CreateMenuOptionResponse from(MenuOption menuOption){

            return CreateMenuOptionResponse.builder()
                    .menuOptionId(menuOption.getId())
                    .name(menuOption.getName())
                    .price(menuOption.getPrice())
                    .build();
        }
    }
