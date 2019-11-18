package com.windsoft.my.shop.web.admin.service.impl;

import com.windsoft.my.shop.commons.dto.BaseResult;
import com.windsoft.my.shop.commons.dto.PageInfo;
import com.windsoft.my.shop.commons.validator.BeanValidator;
import com.windsoft.my.shop.domain.TbContent;
import com.windsoft.my.shop.web.admin.abstracts.AbstractBaseServiceImpl;
import com.windsoft.my.shop.web.admin.dao.TbContentDao;
import com.windsoft.my.shop.web.admin.service.TbContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TbContentServiceImpl extends AbstractBaseServiceImpl<TbContent, TbContentDao> implements TbContentService {

    @Override
    public BaseResult save(TbContent tbContent) {
        String validator = BeanValidator.validator(tbContent);
        if(validator != null){
            return BaseResult.fail(validator);
        }
        //通过验证
        else{
            tbContent.setUpdated(new Date());
            //增加用户
            if(tbContent.getId() == null){
                tbContent.setCreated(new Date());
                getDao().insert(tbContent);
            }
            //编辑用户
            else {
                update(tbContent);
            }

            return BaseResult.success("保存内容信息成功");
        }
    }

    @Override
    public List<TbContent> search(String keyword) {
        return null;
    }

    @Override
    public PageInfo<TbContent> page(int start, int length, int draw, TbContent tbContent) {
        Map<String, Object> params = new HashMap<>();
        params.put("start",start);
        params.put("length",length);
        params.put("tbContent",tbContent);

        int count = count(tbContent);
        PageInfo<TbContent> pageInfo = new PageInfo<>();
        pageInfo.setDraw(draw);
        pageInfo.setRecordsTotal(count);
        pageInfo.setRecordsFiltered(count);
        pageInfo.setData(getDao().page(params));

        return pageInfo;
    }
}
