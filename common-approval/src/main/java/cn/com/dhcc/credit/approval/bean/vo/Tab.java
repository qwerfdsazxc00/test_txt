package cn.com.dhcc.credit.approval.bean.vo;

import lombok.Data;

import java.util.List;

/**
 * 显示审批等级对应的对象
 * @author wanghaowei
 * @date 2020年6月28日
 */
@Data
public class Tab {


    private String checked;

    private List<Tab> children;

    private String id;

    private String name;

    private String parentId;

}
