package com.patika.emlakburadapaymentservice.controller;

import com.patika.emlakburadapaymentservice.dto.request.PaymentSaveRequest;
import com.patika.emlakburadapaymentservice.dto.response.PaymentResponse;
import com.patika.emlakburadapaymentservice.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PaymentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();
    }

    @Test
    public void testMakePayment() throws Exception {
        PaymentSaveRequest request = new PaymentSaveRequest();
        PaymentResponse response = new PaymentResponse();

        when(paymentService.processPayment(request)).thenReturn(response);

        mockMvc.perform(post("/api/v1/payments")
                        .contentType("application/json")
                        .content("{\"amount\":100, \"orderId\":1}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetPaymentById() throws Exception {
        PaymentResponse response = new PaymentResponse();
        response.setId(1L);

        when(paymentService.findPaymentById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/v1/payments/id/1"))
                .andExpect(status().isOk());
    }
}
