package com.jsmsframework.common.enums;

/**
 * 关键字转换类型
 * @author yeshiyuan
 * @create 2018/1/25
 */
public enum  KeywordConvertEnum {
    无(0,"无"),
    繁体转简体(1,"繁体转简体"),
    大写转小写(2,"大写转小写"),
    全角转半角(4,"全角转半角"),
    去空格(8,"去空格"),
    中文符号转英文符号(16,"中文符号转英文符号"),
    删除其他字符(32,"删除其他字符");

    private Integer value;
    private String desc;

    KeywordConvertEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * 通过smsp参数值获取设置的关键字转换类型
     * @param value
     * @return
     */
    public static String[] getValueArray(long value)
    {
        StringBuilder bf = new StringBuilder("");
        if ((value & 0) == 0){
            if (bf.length() > 0)
            {
                bf.append(",");
            }
            bf.append("0");
        }
        if ((value & 1) == 1){
            if (bf.length() > 0)
            {
                bf.append(",");
            }
            bf.append("1");
        }
        if ((value & 2) == 2){
            if (bf.length() > 0)
            {
                bf.append(",");
            }
            bf.append("2");
        }
        if ((value & 4) == 4){
            if (bf.length() > 0)
            {
                bf.append(",");
            }
            bf.append("4");
        }
        if ((value & 8) == 8){
            if (bf.length() > 0)
            {
                bf.append(",");
            }
            bf.append("8");
        }
        if ((value & 16) == 16){
            if (bf.length() > 0)
            {
                bf.append(",");
            }
            bf.append("16");
        }
        if ((value & 32) == 32){
            if (bf.length() > 0)
            {
                bf.append(",");
            }
            bf.append("32");
        }
        String[] array = bf.toString().split(",");
        return array;
    }
}
