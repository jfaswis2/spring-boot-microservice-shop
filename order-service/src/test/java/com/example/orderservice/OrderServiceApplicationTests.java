package com.example.orderservice;

import com.example.orderservice.dto.OrderLineItemsDto;
import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private OrderRepository orderRepository;

	@Test
	void shouldCreateOrder() throws Exception {

		OrderRequest orderRequest = getOrderRequest();

		String orderRequestString = objectMapper.writeValueAsString(orderRequest);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/order")
				.contentType(MediaType.APPLICATION_JSON)
				.content(orderRequestString))
				.andExpect(status().isCreated());
		Assertions.assertEquals(1, orderRepository.findAll().size());
	}

	private OrderRequest getOrderRequest() {

		OrderRequest orderRequest = new OrderRequest();
		List<OrderLineItemsDto> orderLineItemsDtoList = new ArrayList<>(List.of(new OrderLineItemsDto(1L,"Iphone 13", BigDecimal.valueOf(1200),1),
				new OrderLineItemsDto(1L,"Samsung 21", BigDecimal.valueOf(1300),1),
				new OrderLineItemsDto(1L,"Iphone 14", BigDecimal.valueOf(1400),1),
				new OrderLineItemsDto(1L,"Samsung 22", BigDecimal.valueOf(1400),1)));
		orderRequest.setOrderLineItemsDtoList(orderLineItemsDtoList);
		return orderRequest;
	}

}
