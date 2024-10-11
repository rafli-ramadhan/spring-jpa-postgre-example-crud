package com.example.spring_app.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.spring_app.model.Data;
import com.example.spring_app.model.Request;
import com.example.spring_app.repository.DBRepo;
import com.example.spring_app.util.CommonUtil;
import com.example.spring_app.util.Logger;

@Service
public class DBServiceImpl implements DBService {

    long startTime = System.currentTimeMillis();

    private static final Logger LOG = Logger.getInstance();

    @Autowired
    private DBRepo repo;

    @Override
    public Data getContent(String contentId) {
        Data response = repo.getContent(contentId);
        LOG.logTrace("getContent", startTime, contentId, CommonUtil.convertJsonToString(response), HttpStatus.OK);
        return response;
    }

    @Override
    public boolean insertContent(Request request) {
        Data data = new Data(request.getContentID(), request.getContentMessage(), false, new Date(), null);
        try {
            Integer response = repo.insertContent(data);
            LOG.logTrace("insertContent", startTime, CommonUtil.convertJsonToString(request),
                    CommonUtil.convertJsonToString(response), HttpStatus.OK);
            return true;
        } catch (Exception ex) {
            LOG.logException("insertContent", startTime, CommonUtil.convertJsonToString(request),
                    CommonUtil.convertJsonToString(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR, ex);

            return false;
        }
    }

    @Override
    public boolean updateContent(String contentId, Request request) {
        Data data = new Data(contentId, request.getContentMessage(), false, null, new Date());

        try {
            Integer response = repo.updateContent(data);
            LOG.logTrace("updateContent", startTime, CommonUtil.convertJsonToString(request),
                    CommonUtil.convertJsonToString(response), HttpStatus.OK);
            return true;
        } catch (Exception ex) {
            LOG.logException("insertContent", startTime, CommonUtil.convertJsonToString(request),
                    CommonUtil.convertJsonToString(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR, ex);
            return false;
        }
    }

    @Override
    public boolean deleteContent(String contentId) {
        Data data = new Data(contentId, null, true, null, null);

        try {
            Integer response = repo.deleteContent(data);
            LOG.logTrace("deleteContent", startTime, contentId, CommonUtil.convertJsonToString(response),
                    HttpStatus.OK);
            return true;
        } catch (Exception ex) {
            LOG.logException("insertContent", startTime, contentId,
                    CommonUtil.convertJsonToString(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR, ex);
            return false;
        }
    }

}