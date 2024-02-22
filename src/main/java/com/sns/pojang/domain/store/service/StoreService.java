package com.sns.pojang.domain.store.service;

import com.sns.pojang.domain.favorite.entity.Favorite;
import com.sns.pojang.domain.favorite.repository.FavoriteRepository;
import com.sns.pojang.domain.favorite.service.FavoriteService;
import com.sns.pojang.domain.member.entity.Member;
import com.sns.pojang.domain.member.exception.MemberNotFoundException;
import com.sns.pojang.domain.member.repository.MemberRepository;
import com.sns.pojang.domain.order.entity.Order;
import com.sns.pojang.domain.order.entity.OrderStatus;
import com.sns.pojang.domain.order.repository.OrderRepository;
import com.sns.pojang.domain.review.dto.response.RatingResponse;
import com.sns.pojang.domain.review.dto.response.ReviewResponse;
import com.sns.pojang.domain.review.entity.Review;
import com.sns.pojang.domain.review.exception.ReviewNotFoundException;
import com.sns.pojang.domain.review.repository.ReviewRepository;
import com.sns.pojang.domain.order.repository.OrderRepository;
import com.sns.pojang.domain.store.dto.request.CreateStoreRequest;
import com.sns.pojang.domain.store.dto.request.RegisterBusinessNumberRequest;
import com.sns.pojang.domain.store.dto.request.SearchStoreRequest;
import com.sns.pojang.domain.store.dto.request.UpdateStoreRequest;
import com.sns.pojang.domain.store.dto.response.*;
import com.sns.pojang.domain.store.entity.BusinessNumber;
import com.sns.pojang.domain.store.entity.Status;
import com.sns.pojang.domain.store.entity.Store;
import com.sns.pojang.domain.store.exception.*;
import com.sns.pojang.domain.store.repository.BusinessNumberRepository;
import com.sns.pojang.domain.store.repository.StoreRepository;
import com.sns.pojang.global.error.exception.EntityNotFoundException;
import com.sns.pojang.global.error.exception.InvalidValueException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.sns.pojang.global.error.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;
    private final BusinessNumberRepository businessNumberRepository;
    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final FavoriteRepository favoriteRepository;

    @Value("${image.path}")
    private String imagePath;

    public BusinessNumber registerBusinessNumber(RegisterBusinessNumberRequest registerBusinessNumberRequest){
        BusinessNumber businessNumber = new BusinessNumber(registerBusinessNumberRequest.getBusinessNumber());
        return businessNumberRepository.save(businessNumber);
    }

    @Transactional
    public CreateStoreResponse createStore(CreateStoreRequest createStoreRequest) throws BusinessNumberDuplicateException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member findMember = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(MemberNotFoundException::new);

        //사업자 번호 대조
        if (businessNumberRepository.findByBusinessNumber(createStoreRequest.getBusinessNumber()).isEmpty()) {
            throw new BusinessNumberNotFoundException();
        }
        //사업자 번호 등록 여부 대조
        if (storeRepository.findByBusinessNumber(createStoreRequest.getBusinessNumber()).isPresent()) {
            throw new BusinessNumberDuplicateException();
        }

        Path path;
        if (createStoreRequest.getStoreImage() != null){
            MultipartFile storeImage = createStoreRequest.getStoreImage();
            String fileName = storeImage.getOriginalFilename(); // 확장자 포함한 파일명 추출
            path = Paths.get(imagePath, fileName);
            try {
                byte[] bytes = storeImage.getBytes(); // 이미지 파일을 바이트로 변환
                // 해당 경로의 폴더에 이미지 파일 추가. 이미 동일 파일이 있으면 덮어 쓰기(Write), 없으면 Create
                Files.write(path, bytes, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            } catch (IOException e) {
                throw new IllegalArgumentException("Image Not Available");
            }
        } else {
            // 첨부된 이미지가 없을 경우, 기본 이미지로 세팅해주기
            path = Paths.get(imagePath, "no_image.jpg");
        }

        Store newStore = createStoreRequest.toEntity(path, findMember);

        // Member List<Store>에 생성된 store 추가
        newStore.attachMember(findMember);

        return CreateStoreResponse.from(storeRepository.save(newStore));
    }

    @Transactional
    public UpdateStoreResponse updateStore(Long id, UpdateStoreRequest updateStoreRequest) {
        Store findStore = findStore(id);
        validateOwner(findStore);

        findStore.updateStore(updateStoreRequest.getName(),
                updateStoreRequest.getCategory(),
                updateStoreRequest.getStoreNumber(),
                updateStoreRequest.getIntroduction(),
                updateStoreRequest.getOperationTime());

        return UpdateStoreResponse.from(storeRepository.save(findStore));
    }

//    가게 조회 및 검색기능
    @Transactional
    public List<SearchStoreResponse> findStores(SearchStoreRequest searchStoreRequest, Pageable pageable) {
//        검색을 위해 Specification 객체 사용
        Specification<Store> spec = new Specification<Store>() {
            @Override
            public Predicate toPredicate(Root<Store> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (searchStoreRequest.getName() != null) {
                    predicates.add(criteriaBuilder.like(root.get("name"), "%" + searchStoreRequest.getName() + "%"));
                }
                if (searchStoreRequest.getCategory() != null) {
                    predicates.add(criteriaBuilder.like(root.get("category"), "%" + searchStoreRequest.getCategory() + "%"));
                }
//                predicates.add(criteriaBuilder.equal(root.get("delYn"), "N"));
                Predicate[] predicatesArr = new Predicate[predicates.size()];
                for (int i = 0; i < predicates.size(); i++) {
                    predicatesArr[i] = predicates.get(i);
                }
                Predicate predicate = criteriaBuilder.and(predicatesArr);
                return predicate;
            }
        };

        Page<Store> stores = storeRepository.findAll(spec , pageable);
        List<Store> storeList = stores.getContent();
        List<SearchStoreResponse> searchStoreResponses = new ArrayList<>();
        int totalRating = 0;
        double avgRating = 0;
        for(Store store : storeList) {
            int countOrder = orderRepository.findByStoreAndOrderStatus(store, OrderStatus.CONFIRM).size();
            int likes = favoriteRepository.findByStoreAndFavoriteYn(store, "Y").size();
            List<Review> reviews = reviewRepository.findByStoreAndDeleteYn(store, "N");
            for(Review review : reviews) {
                totalRating += review.getRating();
            }
            avgRating = (double) totalRating /reviews.size();
            totalRating = 0;
            SearchStoreResponse searchStoreResponse = SearchStoreResponse.from(store, countOrder, avgRating, likes);
            searchStoreResponses.add(searchStoreResponse);
        }
//        return RatingResponse.builder()
//                .avgRating(df.format(avgRating))
//        List<SearchStoreResponse> searchStoreResponses = new ArrayList<>();
//        searchStoreResponses = storeList.stream().map(i -> SearchStoreResponse.builder()
//                .id(i.getId())
//                .name(i.getName())
//                .category(i.getCategory())
//                .imageUrl(i.getImageUrl())
//                .build()
//        ).collect(Collectors.toList());
        return searchStoreResponses;
    }

    @Transactional
    public void deleteStore(Long id) {
        Store findStore = findStore(id);
        validateOwner(findStore);
        findStore.isDelete();
    }

    @Transactional
    public List<SearchMyStoreResponse> getMyStore() {
        Member findMember = findMember();
        //본인이 아닌 다른 owner회원이 조회하지 못하게 분기처리

//                .status(i.getStatus())
        List<Store> stores = storeRepository.findByMember(findMember);
//        if (stores.isEmpty()){
//            throw new EntityNotFoundException(MY_STORE_NOT_FOUND);
//        }
        return stores.stream().map(SearchMyStoreResponse::from).collect(Collectors.toList());
    }

    // 가게 이미지 조회
    @Transactional
    public Resource findImage(Long storeId) {
        Store findStore = findStore(storeId);
        // 삭제된 매장은 조회 불가
        if (findStore.getDeleteYn().equals("Y")){
            throw new StoreNotFoundException();
        }
        String imageUrl = findStore.getImageUrl();
        Path path = Paths.get(imageUrl);
        Resource resource;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Url Form Is Not Valid");
        }
        return resource;
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

    // 가게 등록한 Owner인지 검증
    private void validateOwner(Store store){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member findMember = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(MemberNotFoundException::new);
        if (!store.getMember().equals(findMember)){
            throw new AccessDeniedException(store.getName() + "의 사장님이 아닙니다.");
        }
    }

    public List<ReviewResponse> findReviews(Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(StoreNotFoundException::new);
        if(store.getDeleteYn().equals("Y")) {
            throw new StoreNotFoundException();
        }
        List<Review> reviews = reviewRepository.findByStoreAndDeleteYn(store, "N");
        if(reviews.isEmpty()) {
            throw new ReviewNotFoundException();
        }
        List<ReviewResponse> reviewResponses = new ArrayList<>();
        for(Review review : reviews) {
            ReviewResponse reviewResponse = ReviewResponse.from(review);
            reviewResponses.add(reviewResponse);
        }
        return reviewResponses;
    }

    public RatingResponse findRating(Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(StoreNotFoundException::new);
        if(store.getDeleteYn().equals("Y")) {
            throw new StoreNotFoundException();
        }
        List<Review> reviews = reviewRepository.findByStoreAndDeleteYn(store, "N");
        int totalRating = 0;
        for(Review review : reviews) {
            totalRating += review.getRating();
        }
        double avgRating = (double) totalRating /reviews.size();
        return RatingResponse.from(avgRating);
    }

    public SearchStoreInfoResponse getStoreDetail(Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(StoreNotFoundException::new);
        if(store.getDeleteYn().equals("Y")) {
            throw new StoreNotFoundException();
        }
        List<Favorite> favorites = favoriteRepository.findByStoreAndFavoriteYn(store, "Y");
        int count = 0;
        for(Favorite favorite : favorites) {
            // 회원 탈퇴 후 30일 전까지 회원 정보가 삭제되지 않으므로 걸러줘야 함
            if(favorite.getMember().getDeleteYn().equals("N")) {
                count++;
            }
        }
        List<Review> reviews = reviewRepository.findByStoreAndDeleteYn(store, "N");
        int totalRating = 0;
        for(Review review : reviews) {
            totalRating += review.getRating();
        }
        double avgRating = (double) totalRating /reviews.size();
        return SearchStoreInfoResponse.from(store, count, avgRating);
    }

    @Transactional
    public void open(Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(StoreNotFoundException::new);
        if(store.getStatus() == Status.OPEN) {
            throw new AlreadyOpenedException();
        }
        store.open();
    }

    @Transactional
    public void close(Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(StoreNotFoundException::new);
        if(store.getStatus() == Status.CLOSED) {
            throw new AlreadyClosedException();
        }
        store.close();
    }
}
