package com.jsmsframework.common.service;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.List;

import com.jsmsframework.common.exception.JsmsNoticeListException;
import com.ucpaas.sms.common.util.Collections3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsmsframework.common.mapper.JsmsNoticeListMapper;
import com.jsmsframework.common.entity.JsmsNoticeList;


import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 公告管理表
 * @author huangwenjie
 * @date 2017-12-06
 */
@Service
public class JsmsNoticeListServiceImpl implements JsmsNoticeListService{

    @Autowired
    private JsmsNoticeListMapper noticeListMapper;
    
    @Override
    public int insert(JsmsNoticeList model) {
        return this.noticeListMapper.insert(model);
    }

    @Override
    public int insertBatch(List<JsmsNoticeList> modelList) {
        return this.noticeListMapper.insertBatch(modelList);
    }

	@Override
    public int update(JsmsNoticeList model) {
		JsmsNoticeList old = this.noticeListMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.noticeListMapper.update(model); 
    }

    @Override
    public int updateSelective(JsmsNoticeList model) {
		JsmsNoticeList old = this.noticeListMapper.getById(model.getId());
		if(old != null)
        	return this.noticeListMapper.updateSelective(model);
		return 0;
    }

    @Override
    public JsmsNoticeList getById(Integer id) {
        JsmsNoticeList model = this.noticeListMapper.getById(id);
		return model;
    }

    @Override
    public JsmsNoticeList getContentById(Integer id) {
        JsmsNoticeList jsmsNoticeList = this.noticeListMapper.getContentById(id);

        if(jsmsNoticeList==null){
            throw new JsmsNoticeListException("此公告不存在!");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        //发布时间
        if(null!=(jsmsNoticeList.getReleaseTime())){
            jsmsNoticeList.setReleaseTimeToStr(sdf.format(jsmsNoticeList.getReleaseTime()));
        }
        //更新时间
        if(null!=(jsmsNoticeList.getUpdateTime())){
            jsmsNoticeList.setUpdateTimeToStr(sdf.format(jsmsNoticeList.getUpdateTime()));
        }
        return jsmsNoticeList;
    }

    @Override
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsNoticeList> list = this.noticeListMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
    public JsmsPage queryPageList(JsmsPage page) {
        List<JsmsNoticeList> list = this.noticeListMapper.queryList(page);
        if(!Collections3.isEmpty(list)){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
            for (int i=0;i<list.size();i++){
                JsmsNoticeList jsmsNoticeLis=list.get(i);
                //发布时间
                if(null!=(jsmsNoticeLis.getReleaseTime())){
                    list.get(i).setReleaseTimeToStr(sdf.format(jsmsNoticeLis.getReleaseTime()));
                }
                //更新时间
                if(null!=(jsmsNoticeLis.getUpdateTime())){
                    list.get(i).setUpdateTimeToStr(sdf.format(jsmsNoticeLis.getUpdateTime()));
                }
                String noticeContent = jsmsNoticeLis.getNoticeContent().replaceAll("</?[^>]+>", "");//去除html标签
                if(noticeContent.length()>12){
                    noticeContent = noticeContent.substring(0, 12);
                }
                list.get(i).setNoticeContent(noticeContent);
            }
        }
        page.setData(list);
        return page;
    }

    @Override
    public int count(Map<String,Object> params) {
		return this.noticeListMapper.count(params);
    }


    @Override
    public List<JsmsNoticeList> queryListAll(Map<String, Object> params) {
        List<JsmsNoticeList> modelList = this.noticeListMapper.queryListAll(params);
        if(!Collections3.isEmpty(modelList)){
            for (int i=0;i<modelList.size();i++){
                JsmsNoticeList jsmsNoticeLis=modelList.get(i);
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
                //发布时间
                if(null!=(jsmsNoticeLis.getReleaseTime())){
                    modelList.get(i).setReleaseTimeToStr(sdf.format(jsmsNoticeLis.getReleaseTime()));
                }
                //更新时间
                if(null!=(jsmsNoticeLis.getUpdateTime())){
                    modelList.get(i).setUpdateTimeToStr(sdf.format(jsmsNoticeLis.getUpdateTime()));
                }
            }
        }

        return modelList;
    }
    
}
