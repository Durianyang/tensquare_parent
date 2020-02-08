package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import com.tensquare.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author: Durian
 * Date: 2020/2/8 22:24
 * Description:
 */
@Service
public class LabelService
{
    private final LabelDao labelDao;
    private final IdWorker idWorker;
    @Autowired
    public LabelService(LabelDao labelDao, IdWorker idWorker)
    {
        this.labelDao = labelDao;
        this.idWorker = idWorker;
    }

    public List<Label> findAll()
    {
        return labelDao.findAll();
    }

    public Label findById(String labelId)
    {
        return labelDao.findById(labelId).orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(Label label)
    {
        label.setId(idWorker.nextId() + "");
        labelDao.save(label);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void update(Label label)
    {
        labelDao.save(label);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(String labelId)
    {
        labelDao.deleteById(labelId);
    }
}
