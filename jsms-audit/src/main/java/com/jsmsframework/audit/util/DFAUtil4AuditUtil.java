package com.jsmsframework.audit.util;

import com.hankcs.hanlp.HanLP;
import com.jsmsframework.audit.dto.JsmsAuditKeywordCategoryDTO;
import com.jsmsframework.audit.dto.JsmsAuditKeywordListAndCategoryDTO;
import com.jsmsframework.audit.entity.JsmsAuditKeywordCategory;
import com.jsmsframework.audit.entity.JsmsAuditKeywordList;
import com.jsmsframework.common.enums.audit.AuditClientGroupIsDefault;
import com.jsmsframework.common.util.DFAUtil;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class DFAUtil4AuditUtil {


    private static Logger logger = LoggerFactory.getLogger(DFAUtil.class);


    /**
     * 根据List生成DFA树，可追加根节点
     * @param list
     * @param root
     * @return
     */
    @SuppressWarnings("Duplicates")
    public static DFANode4Audit<String> buildHierarchicalDFATreeParent(List<JsmsAuditKeywordListAndCategoryDTO> list, DFANode4Audit<String> root){
        long start = System.currentTimeMillis();
        DFANode4Audit<String> parent;
        DFANode4Audit<String> child;
        if(root == null)
            root = new DFANode4Audit<>(null,new ArrayList<DFAUtil.DFANode<String>>(),false,null);
        if(list==null||list.isEmpty()) {
            root.setEnd(true);
            return root;
        }else{
            root.setEnd(false);
        }
        parent = root;

        Collections.sort(list, new Comparator<JsmsAuditKeywordListAndCategoryDTO>() {
            @Override
            public int compare(JsmsAuditKeywordListAndCategoryDTO o1, JsmsAuditKeywordListAndCategoryDTO o2) {
                JsmsAuditKeywordCategoryDTO category1 = (JsmsAuditKeywordCategoryDTO) o1.getCategory();
                JsmsAuditKeywordCategoryDTO category2 = (JsmsAuditKeywordCategoryDTO) o2.getCategory();

                return category1.getSort().compareTo(category2.getSort());
            }
        });

        for(JsmsAuditKeywordListAndCategoryDTO dto:list) {
            Iterator<JsmsAuditKeywordList> iterator = dto.getKeywordLists().iterator();
            while (iterator.hasNext()) {
                JsmsAuditKeywordList jsmsAuditKeywordList = iterator.next();
                String keyword = jsmsAuditKeywordList.getKeyword();
                for (int pos = 0; pos < keyword.length(); pos++) {
                    char kwChar = keyword.charAt(pos);
                    DFANode4Audit<String> node = (DFANode4Audit<String>) parent.get(String.valueOf(kwChar));
                    if (node != null) {
                        parent = node;
                    } else {
                        child = new DFANode4Audit<>(String.valueOf(kwChar), new ArrayList<DFAUtil.DFANode<String>>(), false,dto.getCategory());
                        child.setAuditClientGroupIsDefault(((JsmsAuditKeywordCategoryDTO)dto.getCategory()).getAuditClientGroupIsDefault());
                        parent.addChild(child);
                        parent = child;
                    }
                }
                parent.setEnd(true);
                parent = root;
            }
        }

        if(root.getAuditClientGroupIsDefault()==null){
            if(root.getDfaNodes().size()>0){ //2.5需求定义了 一个用户只能在一个组别中，也就是只能有一个用户组
                DFANode4Audit<String> node = (DFANode4Audit<String>) root.getDfaNodes().get(0);
                root.setAuditClientGroupIsDefault(node.getAuditClientGroupIsDefault());
            }
        }
        logger.debug("DFAUtil.buildHierarchicalDFATreeParent cost {}ms", System.currentTimeMillis() - start);
        return root;
    }




    public static class DFANode4Audit<T> extends DFAUtil.DFANode<T>{
        private JsmsAuditKeywordCategory category;
        /**
         * 是否是默认用户组的分配
         */
        AuditClientGroupIsDefault auditClientGroupIsDefault;

        public DFANode4Audit(T value, List<DFAUtil.DFANode<T>> dfaNodes, boolean end,JsmsAuditKeywordCategory category) {
            super(value, dfaNodes, end);
            this.category = category;
        }

        public JsmsAuditKeywordCategory getCategory() {
            return category;
        }

        public void setCategory(JsmsAuditKeywordCategory category) {
            this.category = category;
        }

        public AuditClientGroupIsDefault getAuditClientGroupIsDefault() {
            return auditClientGroupIsDefault;
        }

        public void setAuditClientGroupIsDefault(AuditClientGroupIsDefault auditClientGroupIsDefault) {
            this.auditClientGroupIsDefault = auditClientGroupIsDefault;
        }
    }
}
