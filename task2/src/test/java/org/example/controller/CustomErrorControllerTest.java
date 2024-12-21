package org.example.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import jakarta.servlet.RequestDispatcher;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomErrorControllerTest {

	@InjectMocks
	private CustomErrorController errorController;

	@Mock
	private MockHttpServletRequest request;

	@BeforeEach
	void setUp() {
		request = new MockHttpServletRequest();
	}

	@Test
	void handleError_NotFound() {
		request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.NOT_FOUND.value());

		ResponseEntity<Map<String, Object>> response = errorController.handleError(request);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(404, response.getBody().get("status"));
		assertEquals("Not Found", response.getBody().get("error"));
		assertEquals("The requested resource was not found", response.getBody().get("message"));
	}

	@Test
	void handleError_InternalServerError() {
		request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.INTERNAL_SERVER_ERROR.value());

		ResponseEntity<Map<String, Object>> response = errorController.handleError(request);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(500, response.getBody().get("status"));
		assertEquals("Internal Server Error", response.getBody().get("error"));
		assertEquals("An internal server error occurred", response.getBody().get("message"));
	}

	@Test
	void handleError_NoStatusCode() {
		ResponseEntity<Map<String, Object>> response = errorController.handleError(request);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(500, response.getBody().get("status"));
		assertEquals("Internal Server Error", response.getBody().get("error"));
		assertEquals("An unexpected error occurred", response.getBody().get("message"));
	}

	@Test
	void handleError_BadRequest() {
		request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.BAD_REQUEST.value());

		ResponseEntity<Map<String, Object>> response = errorController.handleError(request);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(400, response.getBody().get("status"));
		assertEquals("Bad Request", response.getBody().get("error"));
		assertEquals("An unexpected error occurred", response.getBody().get("message"));
	}

	@Test
	void handleError_Unauthorized() {
		request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.UNAUTHORIZED.value());

		ResponseEntity<Map<String, Object>> response = errorController.handleError(request);

		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(401, response.getBody().get("status"));
		assertEquals("Unauthorized", response.getBody().get("error"));
		assertEquals("An unexpected error occurred", response.getBody().get("message"));
	}

	@Test
	void handleError_ResponseContainsAllRequiredFields() {
		request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.NOT_FOUND.value());

		ResponseEntity<Map<String, Object>> response = errorController.handleError(request);

		assertNotNull(response.getBody());
		assertTrue(response.getBody().containsKey("status"));
		assertTrue(response.getBody().containsKey("error"));
		assertTrue(response.getBody().containsKey("message"));
	}
}