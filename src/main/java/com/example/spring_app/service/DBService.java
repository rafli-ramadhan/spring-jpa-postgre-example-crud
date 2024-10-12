package com.example.spring_app.service;

import com.example.spring_app.model.Data;
import com.example.spring_app.model.Request;

public interface DBService {

    Data getContent(String contentId);

    boolean insertContent(Request request);

    boolean updateContent(String contentId, Request request);

    boolean softDeleteContent(String contentId);

    boolean deleteContent(String contentId);
}
