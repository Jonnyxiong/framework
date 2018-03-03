package com.jsmsframework.statistics.service;


import com.jsmsframework.common.dto.R;
import com.jsmsframework.record.dto.RecordDTO;

/**
 *投诉率
 */
public interface JsmsSignComplaintService {


    /**
     * 标记单条短信投诉
     * @return
     */
    R signSingleComplaint(RecordDTO record);

}
