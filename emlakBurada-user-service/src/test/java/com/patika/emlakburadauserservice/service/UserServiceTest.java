package com.patika.emlakburadauserservice.service;

import com.patika.emlakburadauserservice.client.log.service.LogService;
import com.patika.emlakburadauserservice.converter.UserConverter;
import com.patika.emlakburadauserservice.dto.request.UserSaveRequest;
import com.patika.emlakburadauserservice.dto.response.UserResponse;
import com.patika.emlakburadauserservice.exception.EmlakBuradaException;
import com.patika.emlakburadauserservice.exception.ExceptionMessages;
import com.patika.emlakburadauserservice.model.Photo;
import com.patika.emlakburadauserservice.model.User;
import com.patika.emlakburadauserservice.repository.PhotoRepository;
import com.patika.emlakburadauserservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PhotoRepository photoRepository;

    @Mock
    private LogService logService;

    @Test
    void save_successfully() {
        // given
        UserSaveRequest request = UserSaveRequest.builder()
                .name("Test")
                .lastName("User")
                .email("test@example.com")
                .password("password")
                .phone("123456789")
                .address("Test Address")
                .build();

        User user = User.builder()
                .id(1L)
                .name(request.getName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(request.getPassword())
                .phone(request.getPhone())
                .address(request.getAddress())
                .createDate(LocalDateTime.now())
                .build();

        Mockito.when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        try (var mockedConverter = mockStatic(UserConverter.class)) {
            mockedConverter.when(() -> UserConverter.toUser(request)).thenReturn(user);
            Mockito.when(userRepository.save(user)).thenReturn(user);

            // when
            userService.save(request);

            // then
            verify(userRepository, times(1)).findByEmail(request.getEmail());
            verify(userRepository, times(1)).save(user);
            verify(logService, times(1)).log("DbLogStrategy", "user saved successfully.");
            verifyNoMoreInteractions(userRepository);
        }
    }

    @Test
    void save_shouldThrowException_whenEmailAlreadyExists() {
        // given
        UserSaveRequest request = UserSaveRequest.builder()
                .email("test@example.com")
                .build();

        User existingUser = new User();

        Mockito.when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(existingUser));

        // when
        EmlakBuradaException exception = assertThrows(EmlakBuradaException.class, () -> userService.save(request));

        // then
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessages.EMAIL_ALREADY_EXIST);
        verify(userRepository, times(1)).findByEmail(request.getEmail());
        verify(logService, times(1)).log("DbLogStrategy", ExceptionMessages.EMAIL_ALREADY_EXIST);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void getById_shouldReturnUserResponse_whenUserExists() {
        // given
        Long userId = 1L;
        User user = User.builder().id(userId).build();
        UserResponse userResponse = UserResponse.builder().id(userId).build();

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        try (var mockedConverter = mockStatic(UserConverter.class)) {
            mockedConverter.when(() -> UserConverter.toUserResponse(user)).thenReturn(userResponse);

            // when
            UserResponse result = userService.getById(userId);

            // then
            assertThat(result).isEqualTo(userResponse);
            verify(userRepository, times(1)).findById(userId);
            verifyNoMoreInteractions(userRepository);
        }
    }

    @Test
    void getById_shouldThrowException_whenUserNotFound() {
        // given
        Long userId = 1L;

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // when
        EmlakBuradaException exception = assertThrows(EmlakBuradaException.class, () -> userService.getById(userId));

        // then
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessages.USER_NOT_FOUND);
        verify(userRepository, times(1)).findById(userId);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void getByEmail_shouldReturnUserResponse_whenUserExists() {
        // given
        String email = "john.doe@example.com";
        User user = new User();
        UserResponse userResponse = new UserResponse();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        try (var mockedConverter = mockStatic(UserConverter.class)) {
            mockedConverter.when(() -> UserConverter.toUserResponse(user)).thenReturn(userResponse);

            // when
            UserResponse response = userService.getByEmail(email);

            // then
            assertThat(response).isNotNull();
            verify(userRepository, times(1)).findByEmail(email);
        }
    }

    @Test
    void getByEmail_shouldThrowException_whenUserNotFound() {
        // given
        String email = "john.doe@example.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // when
        EmlakBuradaException exception = assertThrows(EmlakBuradaException.class, () -> userService.getByEmail(email));

        // then
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessages.USER_NOT_FOUND);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void uploadImage_shouldUploadImageSuccessfully() throws IOException {
        // given
        Long userId = 1L;
        MultipartFile file = mock(MultipartFile.class);
        Photo existingPhoto = new Photo("oldFilename", "image/jpeg", new byte[0], null);
        User user = User.builder().id(userId).photo(existingPhoto).build();
        Photo newPhoto = new Photo("filename", "image/jpeg", new byte[0], user);

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(file.getOriginalFilename()).thenReturn("filename");
        Mockito.when(file.getContentType()).thenReturn("image/jpeg");
        Mockito.when(file.getBytes()).thenReturn(new byte[0]);

        // when
        String result = userService.uploadImage(file, userId);

        // then
        assertThat(result).isEqualTo(ExceptionMessages.UPLOAD_SUCCESS);
        verify(userRepository, times(1)).findById(userId);
        verify(photoRepository, times(1)).delete(existingPhoto);
        verify(userRepository, times(1)).save(user);
        verify(logService, times(1)).log("DbLogStrategy", ExceptionMessages.UPLOAD_SUCCESS);
    }

    @Test
    void getImageByUserId_shouldReturnImageData_whenPhotoExists() {
        // given
        Long userId = 1L;
        User user = User.builder().id(userId).build();
        Photo photo = new Photo();
        photo.setData(new byte[0]);
        user.setPhoto(photo);

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // when
        byte[] result = userService.getImageByUserId(userId);

        // then
        assertThat(result).isEqualTo(photo.getData());
        verify(userRepository, times(1)).findById(userId);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void getImageByUserId_shouldThrowException_whenPhotoNotFound() {
        // given
        Long userId = 1L;
        User user = User.builder().id(userId).build();

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // when
        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.getImageByUserId(userId));

        // then
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessages.PHOTO_NOT_FOUND);
        verify(logService, times(1)).log("DbLogStrategy", ExceptionMessages.PHOTO_NOT_FOUND);
        verify(userRepository, times(1)).findById(userId);
        verifyNoMoreInteractions(userRepository);
    }
}
