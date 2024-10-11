package com.example.spring_app.repository;

import com.example.spring_app.config.AppConfig;
import com.example.spring_app.model.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class DBRepoImpl implements DBRepo {

    @Autowired
    AppConfig config;

    @Autowired
    @Qualifier("customJdbcTemplate")
    private NamedParameterJdbcTemplate template;

    @Override
    public Data getContent(String contentId) {
        String query = "select * from content where content_id = :content_id";

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("content_id", contentId);

        return template.queryForObject(query, parameters, new RowMapper<Data>() {
            @Override
            public Data mapRow(ResultSet rs, int rowNum) throws SQLException {
                Data data = new Data();
                data.setContentId(rs.getString("content_id"));
                data.setContentMessage(rs.getString("content_message"));
                data.setIsDeleted(rs.getBoolean("is_delete"));
                data.setCreatedDate(rs.getTimestamp("created_date"));
                data.setUpdatedDate(rs.getTimestamp("updated_date"));
                return data;
            }
        });
    }

    @Override
    public Integer insertContent(Data data) {
        String query = "insert into content(content_id, content_message, is_delete, created_date, updated_date) " +
                       "values (:content_id, :content_message, :is_delete, :created_date, :updated_date)";

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("content_id", data.getContentId())
                .addValue("content_message", data.getContentMessage())
                .addValue("is_delete", data.getIsDeleted())
                .addValue("created_date", data.getCreatedDate())
                .addValue("updated_date", data.getUpdatedDate());

        return template.update(query, parameters);
    }

    @Override
    public Integer updateContent(Data data) {
        String query = "update content set content_message = :content_message, updated_date = :updated_date " +
                       "where content_id = :content_id";

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("content_message", data.getContentMessage())
                .addValue("updated_date", data.getUpdatedDate())
                .addValue("content_id", data.getContentId());

        return template.update(query, parameters);
    }

    @Override
    public Integer deleteContent(Data data) {
        String query = "delete from content where content_id = :content_id";

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("content_id", data.getContentId());

        return template.update(query, parameters);
    }
}
