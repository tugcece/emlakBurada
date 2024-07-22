package com.patika.emlakburadapurchaseservice.controller;

import com.patika.emlakburadapurchaseservice.dto.request.PurchaseSaveRequest;
import com.patika.emlakburadapurchaseservice.service.PurchaseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping
    public ResponseEntity<Void> purchase(@RequestBody PurchaseSaveRequest saveRequest) {
         purchaseService.purchasePackage(saveRequest);
        return  new ResponseEntity<>(HttpStatus.CREATED);
    }

}

