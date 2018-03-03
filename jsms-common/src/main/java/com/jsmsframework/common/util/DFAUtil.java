package com.jsmsframework.common.util;

import com.hankcs.hanlp.HanLP;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 敏感词汇查询工具类（基于DFA实现）
 * 可用于关键字定位
 *
 */
public class DFAUtil {

    private static Logger logger = LoggerFactory.getLogger(DFAUtil.class);

    /**
     * 构建关键字（敏感词）树
     * @param keywordList
     * @return
     */
    public static DFANode<String> buildDFATree(List<String> keywordList){
        long start = System.currentTimeMillis();
        DFANode<String> root = new DFANode<>(null,new ArrayList<DFANode<String>>(),false);
        DFANode<String> parent;
        DFANode<String> child;

        Iterator<String> iterator = keywordList.iterator();
        parent = root;
        while (iterator.hasNext()) {
            String keyword = iterator.next();
            for (int pos = 0; pos < keyword.length(); pos ++) {
                char kwChar = keyword.charAt(pos);
                DFANode<String> node = root.get(String.valueOf(kwChar));
                if(node != null) {
                    parent = node;
                } else {
                    child = new DFANode<>(String.valueOf(kwChar),new ArrayList<DFANode<String>>(),false);
                    parent.addChild(child);
                    parent = child;
                }
            }
            parent.setEnd(true);
            parent = root;
        }

        logger.debug("DFAUtil.buildDFATree cost {}ms", System.currentTimeMillis() - start);
        return root;
    }

    /**
     * 可追加根节点
     * @param keywordList
     * @return
     */
    @SuppressWarnings("Duplicates")
    public static DFANode<String> buildHierarchicalDFATreeParent(List<String> keywordList, DFANode<String> root){
        long start = System.currentTimeMillis();
        DFANode<String> parent;
        DFANode<String> child;

        parent = root;
        Iterator<String> iterator = keywordList.iterator();
        while (iterator.hasNext()) {
            String keyword = iterator.next();
            keyword = HanLP.convertToSimplifiedChinese(keyword);
            for (int pos = 0; pos < keyword.length(); pos ++) {
                char kwChar = keyword.charAt(pos);
                DFANode<String> node = root.get(String.valueOf(kwChar));
                if(node != null) {
                    parent = node;
                } else {
                    child = new DFANode<>(String.valueOf(kwChar),new ArrayList<DFANode<String>>(),false);
                    parent.addChild(child);
                    parent = child;
                }
            }
            parent.setEnd(true);
            parent = root;
        }

        logger.debug("DFAUtil.buildHierarchicalDFATreeParent cost {}ms", System.currentTimeMillis() - start);
        return root;
    }



    public static class DFANode<T>{
        private T value;
        private List<DFANode<T>> dfaNodes;
        private HashMap<T,DFANode> traverser; //用于快速遍历dfaNodes里的值
        private boolean end;

        public DFANode(T value, List<DFANode<T>> dfaNodes, boolean end) {
            this.value = value;
            this.dfaNodes = dfaNodes;
            this.end = end;
            this.traverser = new HashMap<>();
            for(DFANode<T> node:dfaNodes){
                this.traverser.put(node.getValue(),node);
            }
        }




        public DFANode<String> get(T nodeT) {
            return traverser.get(nodeT);
        }


        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public List<DFANode<T>> getDfaNodes() {
            return dfaNodes;
        }

        public void setDfaNodes(List<DFANode<T>> dfaNodes) {
            this.dfaNodes = dfaNodes;
        }

        public boolean isEnd() {
            return end;
        }

        public void setEnd(boolean end) {
            this.end = end;
        }

        public void addChild(DFANode<T> child) {
            this.dfaNodes.add(child);
            this.traverser.put(child.getValue(),child);
        }
    }
}
