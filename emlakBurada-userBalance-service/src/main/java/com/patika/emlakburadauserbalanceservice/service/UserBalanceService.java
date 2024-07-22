package com.patika.emlakburadauserbalanceservice.service;

import com.patika.emlakburadauserbalanceservice.client.log.service.LogService;
import com.patika.emlakburadauserbalanceservice.converter.UserBalanceConverter;
import com.patika.emlakburadauserbalanceservice.dto.request.UserBalanceSaveRequest;
import com.patika.emlakburadauserbalanceservice.dto.request.UserBalanceUpdateRequest;
import com.patika.emlakburadauserbalanceservice.dto.response.UserBalanceResponse;
import com.patika.emlakburadauserbalanceservice.exception.EmlakBuradaException;
import com.patika.emlakburadauserbalanceservice.exception.ExceptionMessages;
import com.patika.emlakburadauserbalanceservice.model.UserBalance;
import com.patika.emlakburadauserbalanceservice.repository.UserBalanceRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserBalanceService {

    private final UserBalanceRepository userBalanceRepository;
    private final LogService logService;
    public void updateUserBalance(UserBalanceUpdateRequest updateRequest) {
        UserBalance userBalance = userBalanceRepository.findByUserId(updateRequest.getUserId())
                .orElseThrow(() -> new RuntimeException(ExceptionMessages.USER_NOT_FOUND));

        LocalDateTime validityTime = calculateExpirationDate(updateRequest.getPeriodOfValidity(), updateRequest.getUserId());
        userBalance.setRemainingListings(userBalance.getRemainingListings() + updateRequest.getRemainingListings());
        userBalance.setUpdatedAt(LocalDateTime.now());
        userBalance.setValidUntil(validityTime);
        userBalanceRepository.save(userBalance);
        logService.log("DbLogStrategy","user balance updated");
    }

    private LocalDateTime calculateExpirationDate(Integer periodOfValidity, Long userId) {
        LocalDateTime now = LocalDateTime.now();
        Optional<UserBalance> existingBalance = userBalanceRepository.findByUserId(userId);

        if (existingBalance.isEmpty()) {
            logService.log("DbLogStrategy","balance not found.");
            throw new RuntimeException(ExceptionMessages.BALANCE_NOT_FOUND);
        }
        LocalDateTime currentExpirationDate = existingBalance.get().getValidUntil();

        LocalDateTime newExpirationDate = currentExpirationDate.isAfter(now)
                ? currentExpirationDate.plusDays(periodOfValidity)
                : now.plusDays(periodOfValidity);

        return newExpirationDate;
    }


    public UserBalanceResponse getUserBalanceByUserId(Long userId) {
        UserBalance userBalance = userBalanceRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException(ExceptionMessages.BALANCE_NOT_FOUND + userId));
        return UserBalanceConverter.toResponse(userBalance);
    }

    @Transactional
    public void reduceBalance(Long userId) {
        UserBalance userBalance = userBalanceRepository.findByUserId(userId)
                .orElseThrow(() -> new EmlakBuradaException(ExceptionMessages.USER_NOT_FOUND));

        if (userBalance.getRemainingListings() <= 0) {
            throw new EmlakBuradaException(ExceptionMessages.BALANCE_NOT_FOUND);
        }

        userBalance.setRemainingListings(userBalance.getRemainingListings() - 1);
        userBalanceRepository.save(userBalance);
    }

    @Transactional
    public void createUserBalance(UserBalanceSaveRequest request) {
        UserBalance userBalance = new UserBalance(
                request.getUserId(),
                LocalDateTime.parse(request.getValidUntil()),

                request.getRemainingListings()
        );
        userBalance.setCreatedAt(LocalDateTime.now());
        userBalanceRepository.save(userBalance);
    }

}
