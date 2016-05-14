package com.vg.module.dao;

import com.vg.exception.BaseException;

/**
 * @author  SunJianwei<327021593@qq.com>
 * @date    16-5-7 11:46
 */
public class TblInvertedIndexDao extends BaseDao {

    public TblInvertedIndexDao() {
        db      = "news";
        table   = "";       // 数据根据hash值分布在多个表中，采用改进的一致性hash算法找到表名
    }

    // 虚拟节点数量，hash取模的出所在虚拟节点
    private static int nVNode = 100;
    private static String[][] vNode2TableName = {
            // {表名， 起始虚拟节点， 终止虚拟节点},
            {"tblInvertedIndex0", "0", "9"},
            {"tblInvertedIndex1", "10", "19"},
            {"tblInvertedIndex2", "20", "29"},
            {"tblInvertedIndex3", "30", "39"},
            {"tblInvertedIndex4", "40", "49"},
            {"tblInvertedIndex5", "50", "59"},
            {"tblInvertedIndex6", "60", "69"},
            {"tblInvertedIndex7", "70", "79"},
            {"tblInvertedIndex8", "80", "89"},
            {"tblInvertedIndex9", "90", "99"},
    };

    /**
     * 根据hash串，计算对应的表名
     *
     * @param hash
     * @return
     * @throws BaseException 找不到对应配置的表明时抛异常
     */
    public static String getTable(String hash) throws BaseException {
        //System.out.println(hash);
        if (null == hash || "".equals(hash)) {
            throw new NullPointerException("hash str is empty at TblInvertedIndexDao.getTable()");
        }
        byte[] bytes = hash.getBytes();
        int sum = 0;
        for (byte b : bytes) {
            sum += b;
        }
        //System.out.println(sum);
        int vNodeIndex = sum % nVNode;
        //System.out.println(vNodeIndex);
        for (String[] tableItem : vNode2TableName) {
            int startIndex = Integer.parseInt(tableItem[1]);
            int endIndex   = Integer.parseInt(tableItem[2]);
            if (vNodeIndex >= startIndex && vNodeIndex <= endIndex) {
                return tableItem[0];
            }
        }
        throw new BaseException("can't find tableName");
    }


}
