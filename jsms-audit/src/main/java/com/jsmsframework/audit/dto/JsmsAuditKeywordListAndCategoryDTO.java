package com.jsmsframework.audit.dto;

import com.jsmsframework.audit.entity.JsmsAuditKeywordCategory;
import com.jsmsframework.audit.entity.JsmsAuditKeywordList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JsmsAuditKeywordListAndCategoryDTO extends HashMap<JsmsAuditKeywordCategory,List<JsmsAuditKeywordList>>{

    JsmsAuditKeywordCategory category;List<JsmsAuditKeywordList> keywordLists;

    public JsmsAuditKeywordListAndCategoryDTO(JsmsAuditKeywordCategory category, List<JsmsAuditKeywordList> keywordLists) {
        this.category = category;
        this.keywordLists = keywordLists;
    }


    public JsmsAuditKeywordCategory getCategory() {
        return category;
    }

    public void setCategory(JsmsAuditKeywordCategory category) {
        this.category = category;
    }

    public List<JsmsAuditKeywordList> getKeywordLists() {
        return keywordLists;
    }

    public void setKeywordLists(List<JsmsAuditKeywordList> keywordLists) {
        this.keywordLists = keywordLists;
    }
}
