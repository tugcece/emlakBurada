package com.patika.emlakburadauserbalanceservice.service;

import com.patika.emlakburadauserbalanceservice.client.log.service.LogService;
import com.patika.emlakburadauserbalanceservice.converter.UserBalanceConverter;
import com.patika.emlakburadauserbalanceservice.dto.request.UserBalanceUpdateRequest;
import com.patika.emlakburadauserbalanceservice.dto.response.UserBalanceResponse;
import com.patika.emlakburadauserbalanceservice.exception.EmlakBuradaException;
import com.patika.emlakburadauserbalanceservice.exception.ExceptionMessages;
import com.patika.emlakburadauserbalanceservice.model.UserBalance;
import com.patika.emlakburadauserbalanceservice.repository.UserBalanceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserBalanceServiceTest {

    @InjectMocks
    private UserBalanceService userBalanceService;

    @Mock
    private UserBalanceRepository userBalanceRepository;

    @Mock
    private LogService logService;

    @Mock
    private UserBalanceConverter userBalanceConverter;

    @Test
    void updateUserBalance_shouldUpdateBalanceSuccessfully() {
        // given
        Long userId = 1L;
        UserBalanceUpdateRequest updateRequest = new UserBalanceUpdateRequest(1L, userId, 20, 30);
        UserBalance userBalance = new UserBalance();
        userBalance.setUserId(userId);
        userBalance.setRemainingListings(10);
        userBalance.setValidUntil(LocalDateTime.now().plusDays(30));

        when(userBalanceRepository.findByUserId(userId)).thenReturn(Optional.of(userBalance));

        // when
        userBalanceService.updateUserBalance(updateRequest);

        // then
        verify(userBalanceRepository, times(2)).findByUserId(userId);
        verify(userBalanceRepository, times(1)).save(userBalance);
        verify(logService, times(1)).log("DbLogStrategy", "user balance updated");
    }

    @Test
    void updateUserBalance_shouldThrowException_whenUserNotFound() {
        // given
        Long userId = 1L;
        UserBalanceUpdateRequest updateRequest = new UserBalanceUpdateRequest(1L, userId, 20, 30);

        when(userBalanceRepository.findByUserId(userId)).thenReturn(Optional.empty());

        // when
        RuntimeException exception = assertThrows(RuntimeException.class, () -> userBalanceService.updateUserBalance(updateRequest));

        // then
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessages.USER_NOT_FOUND);
    }

    @Test
    void getUserBalanceByUserId_shouldReturnBalance_whenUserExists() {
        // given
        Long userId = 1L;
        UserBalance userBalance = new UserBalance();
        userBalance.setUserId(userId);
        UserBalanceResponse userBalanceResponse = new UserBalanceResponse();

        when(userBalanceRepository.findByUserId(userId)).thenReturn(Optional.of(userBalance));
        when(getUserBalanceResponse(userBalance)).thenReturn(userBalanceResponse);

        // when
        UserBalanceResponse response = userBalanceService.getUserBalanceByUserId(userId);

        // then
        assertThat(response).isNotNull();
        verify(userBalanceRepository, times(1)).findByUserId(userId);
    }

    private UserBalanceResponse getUserBalanceResponse(UserBalance userBalance) {
        UserBalanceResponse userResponse = new UserBalanceResponse();
        userResponse.setUserId(1L);
        userResponse.setValidUntil(LocalDateTime.now().plusDays(30));
        return userResponse;

    }
    @Test
    void getUserBalanceByUserId_shouldThrowException_whenBalanceNotFound() {
        // given
        Long userId = 1L;

        when(userBalanceRepository.findByUserId(userId)).thenReturn(Optional.empty());

        // when
        RuntimeException exception = assertThrows(RuntimeException.class, () -> userBalanceService.getUserBalanceByUserId(userId));

        // then
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessages.BALANCE_NOT_FOUND + userId);
    }

    @Test
    void reduceBalance_shouldReduceBalanceSuccessfully() {
        // given
        Long userId = 1L;
        UserBalance userBalance = new UserBalance();
        userBalance.setUserId(userId);
        userBalance.setRemainingListings(10);

        when(userBalanceRepository.findByUserId(userId)).thenReturn(Optional.of(userBalance));

        // when
        userBalanceService.reduceBalance(userId);

        // then
        verify(userBalanceRepository, times(1)).findByUserId(userId);
        verify(userBalanceRepository, times(1)).save(userBalance);
    }

    @Test
    void reduceBalance_shouldThrowException_whenBalanceNotFound() {
        // given
        Long userId = 1L;

        when(userBalanceRepository.findByUserId(userId)).thenReturn(Optional.empty());

        // when
        EmlakBuradaException exception = assertThrows(EmlakBuradaException.class, () -> userBalanceService.reduceBalance(userId));

        // then
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessages.USER_NOT_FOUND);
        verify(logService, times(1)).log("DbLogStrategy", "balance not found.");
    }

    @Test
    void reduceBalance_shouldThrowException_whenRemainingListingsIsZero() {
        // given
        Long userId = 1L;
        UserBalance userBalance = new UserBalance();
        userBalance.setUserId(userId);
        userBalance.setRemainingListings(0);

        when(userBalanceRepository.findByUserId(userId)).thenReturn(Optional.of(userBalance));

        // when
        EmlakBuradaException exception = assertThrows(EmlakBuradaException.class, () -> userBalanceService.reduceBalance(userId));

        // then
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessages.BALANCE_NOT_FOUND);
    }
}
