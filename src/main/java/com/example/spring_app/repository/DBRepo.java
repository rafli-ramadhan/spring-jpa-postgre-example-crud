package com.example.spring_app.repository;

import com.example.spring_app.model.Data;

public interface DBRepo {

    Data getContent(String content);

    Integer insertContent(Data data);

    Integer updateContent(Data data);

    Integer deleteContent(Data data);

}
