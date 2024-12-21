package org.example.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CustomErrorController implements ErrorController {

	@RequestMapping("/error")
	public ResponseEntity<Map<String, Object>> handleError(HttpServletRequest request) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		Map<String, Object> errorDetails = new HashMap<>();

		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		String message = "An unexpected error occurred";

		if (status != null) {
			int statusCode = Integer.parseInt(status.toString());
			httpStatus = HttpStatus.valueOf(statusCode);

			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				message = "The requested resource was not found";
			} else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				message = "An internal server error occurred";
			}
		}

		errorDetails.put("status", httpStatus.value());
		errorDetails.put("error", httpStatus.getReasonPhrase());
		errorDetails.put("message", message);

		return new ResponseEntity<>(errorDetails, httpStatus);
	}
}