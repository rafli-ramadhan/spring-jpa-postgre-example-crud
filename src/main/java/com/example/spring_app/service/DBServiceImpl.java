package com.example.spring_app.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.spring_app.model.Data;
import com.example.spring_app.model.Request;
import com.example.spring_app.repository.DBRepo;
import com.example.spring_app.util.CommonUtil;
import com.example.spring_app.util.Logger;

@Service
public class DBServiceImpl implements DBService {

    private static final Logger LOG = Logger.getInstance();
    private long startTime;

    @Autowired
    private DBRepo repo;

    @Override
    public Data getContent(String contentId) {
        startTime = System.currentTimeMillis();

        Optional<Data> optionalData = repo.findByContentId(contentId);
        if (optionalData.isPresent()) {
            Data response = optionalData.get();
            LOG.logTrace("getContent", startTime, contentId, CommonUtil.convertJsonToString(response), HttpStatus.OK);
            return response;
        } else {
            LOG.logTrace("getContent", startTime, contentId, "Content not found", HttpStatus.NOT_FOUND);
            return null;
        }
    }

    @Override
    public boolean insertContent(Request request) {
        startTime = System.currentTimeMillis();

        Data data = new Data();
        data.setContentId(request.getContentID());
        data.setContentMessage(request.getContentMessage());
        data.setIsDeleted(false);
        data.setCreatedDate(new Date());
        data.setUpdatedDate(null);

        try {
            Data savedData = repo.save(data);
            LOG.logTrace("insertContent", startTime, CommonUtil.convertJsonToString(request),
                    CommonUtil.convertJsonToString(savedData), HttpStatus.OK);
            return true;
        } catch (Exception ex) {
            LOG.logException("insertContent", startTime, CommonUtil.convertJsonToString(request),
                    CommonUtil.convertJsonToString(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR, ex);
            return false;
        }
    }

    @Override
    public boolean updateContent(String contentId, Request request) {
        startTime = System.currentTimeMillis();

        Optional<Data> optionalData = repo.findByContentId(contentId);
        if (optionalData.isPresent()) {
            Data existingData = optionalData.get();
            existingData.setContentMessage(request.getContentMessage());
            existingData.setUpdatedDate(new Date());

            try {
                Data updatedData = repo.save(existingData);
                LOG.logTrace("updateContent", startTime, CommonUtil.convertJsonToString(request),
                        CommonUtil.convertJsonToString(updatedData), HttpStatus.OK);
                return true;
            } catch (Exception ex) {
                LOG.logException("updateContent", startTime, CommonUtil.convertJsonToString(request),
                        CommonUtil.convertJsonToString(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR, ex);
                return false;
            }
        } else {
            LOG.logTrace("updateContent", startTime, contentId, "Content not found", HttpStatus.NOT_FOUND);
            return false;
        }
    }

    @Override
    public boolean softDeleteContent(String contentId) {
        startTime = System.currentTimeMillis();

        Optional<Data> optionalData = repo.findByContentId(contentId);
        if (optionalData.isPresent()) {
            Data existingData = optionalData.get();
            existingData.setIsDeleted(true);
            existingData.setUpdatedDate(new Date());

            try {
                Data updatedData = repo.save(existingData);
                LOG.logTrace("softDeleteContent", startTime, CommonUtil.convertJsonToString(contentId),
                        CommonUtil.convertJsonToString(updatedData), HttpStatus.OK);
                return true;
            } catch (Exception ex) {
                LOG.logException("softDeleteContent", startTime, CommonUtil.convertJsonToString(contentId),
                        CommonUtil.convertJsonToString(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR, ex);
                return false;
            }
        } else {
            LOG.logTrace("updateContent", startTime, contentId, "Content not found", HttpStatus.NOT_FOUND);
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deleteContent(String contentId) {
        startTime = System.currentTimeMillis();

        try {
            repo.deleteByContentId(contentId);
            LOG.logTrace("deleteContent", startTime, contentId, "Content deleted", HttpStatus.OK);
            return true;
        } catch (Exception ex) {
            LOG.logException("deleteContent", startTime, contentId,
                    CommonUtil.convertJsonToString(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR, ex);
            return false;
        }
    }
}
