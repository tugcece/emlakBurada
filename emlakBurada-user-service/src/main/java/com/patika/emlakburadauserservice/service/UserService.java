package com.patika.emlakburadauserservice.service;

import com.patika.emlakburadauserservice.client.log.service.LogService;
import com.patika.emlakburadauserservice.client.userBalance.service.UserBalanceService;
import com.patika.emlakburadauserservice.converter.UserConverter;
import com.patika.emlakburadauserservice.dto.request.UserBalanceSaveRequest;
import com.patika.emlakburadauserservice.dto.request.UserSaveRequest;
import com.patika.emlakburadauserservice.dto.response.UserResponse;
import com.patika.emlakburadauserservice.exception.EmlakBuradaException;
import com.patika.emlakburadauserservice.exception.ExceptionMessages;
import com.patika.emlakburadauserservice.model.Photo;
import com.patika.emlakburadauserservice.model.User;
import com.patika.emlakburadauserservice.repository.PhotoRepository;
import com.patika.emlakburadauserservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;


@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PhotoRepository photoRepository;
    private final LogService logService;
    private final UserBalanceService userBalanceService;

    /* For the save method, firstly user registration is checked from the repository.
     * Then, if there is a user, it is converted to User with this converter.
     *  For this, @Builder is used. Finally, the user is saved to the db.
     */
    @Transactional
    public void save(UserSaveRequest request) {

        userRepository.findByEmail(request.getEmail()).ifPresent(existingUser -> {
            logService.log("DbLogStrategy",ExceptionMessages.EMAIL_ALREADY_EXIST);
            log.error(ExceptionMessages.EMAIL_ALREADY_EXIST);
            throw new EmlakBuradaException(ExceptionMessages.EMAIL_ALREADY_EXIST);
        });

        User user = UserConverter.toUser(request);
        userRepository.save(user);
        UserBalanceSaveRequest userBalanceSaveRequest = UserBalanceSaveRequest.builder()
                                                .userId(user.getId())
                                                .remainingListings(0)
                                                .validUntil(LocalDateTime.now().plusMonths(1).toString())
                                                .build();
        userBalanceService.createUserBalance(userBalanceSaveRequest);
        logService.log("DbLogStrategy","user saved successfully.");
        log.info("user saved. user id:{} : createdate:{}",user.getId(),user.getCreateDate());
    }
    @Transactional(readOnly = true)
    public UserResponse getById(Long userId) {
       User user =  userRepository.findById(userId)
               .orElseThrow(() -> new EmlakBuradaException(ExceptionMessages.USER_NOT_FOUND));

        return UserConverter.toUserResponse(user);
    }
    @Transactional(readOnly = true)
    public UserResponse getByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EmlakBuradaException(ExceptionMessages.USER_NOT_FOUND));

        return UserConverter.toUserResponse(user);
    }

    @Transactional
    public String uploadImage(MultipartFile file, Long userId) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException(ExceptionMessages.USER_NOT_FOUND));

        if (user.getPhoto() != null) {
            photoRepository.delete(user.getPhoto());
        }

        Photo photo = new Photo(file.getOriginalFilename(), file.getContentType(), file.getBytes(), user);
        user.setPhoto(photo);
        userRepository.save(user);
        logService.log("DbLogStrategy",ExceptionMessages.UPLOAD_SUCCESS);
        return (ExceptionMessages.UPLOAD_SUCCESS);
    }

    @Transactional(readOnly = true)
    public byte[] getImageByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException(ExceptionMessages.USER_NOT_FOUND));
        Photo photo = user.getPhoto();
        if (photo == null) {
            log.error(ExceptionMessages.PHOTO_NOT_FOUND);
            logService.log("DbLogStrategy",ExceptionMessages.PHOTO_NOT_FOUND);
            throw new RuntimeException(ExceptionMessages.PHOTO_NOT_FOUND);
        }
        return photo.getData();
    }
}
