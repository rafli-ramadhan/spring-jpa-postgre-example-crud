package com.example.spring_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring_app.model.Data;
import com.example.spring_app.model.Request;
import com.example.spring_app.service.DBService;

import jakarta.validation.Valid;

@RestController
public class Controller {

        @Autowired
        private DBService service;

        @GetMapping(value = "/v1/content")
        public ResponseEntity<String> getContent(@RequestParam(name = "content_id") String contentId) {
                Data response = service.getContent(contentId);

                if (!response.getContentMessage().isEmpty()) {
                        return ResponseEntity.status(HttpStatus.OK)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .body(response.getContentMessage());
                } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .body("Content not found");
                }
        }

        @PostMapping(value = "/v1/content")
        public ResponseEntity<String> insertContent(@RequestBody @Valid Request request) {
                service.insertContent(request);

                return ResponseEntity.status(HttpStatus.OK)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body("Success");
        }

        @PutMapping(value = "/v1/content/{id}")
        public ResponseEntity<String> updateContent(@PathVariable String id, @RequestBody @Valid Request request) {
                service.updateContent(id, request);
                return ResponseEntity.status(HttpStatus.OK)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body("Success");
        }

        @DeleteMapping(value = "/v1/content/{id}")
        public ResponseEntity<String> deleteContent(@PathVariable String id) {
                service.deleteContent(id);

                return ResponseEntity.status(HttpStatus.OK)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body("Success");
        }
}
