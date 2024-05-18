package cn.com.dhcc.credit.approval.constants;

import org.apache.commons.lang.StringUtils;

/**
 * 审批功能：和动作对应的枚举类
 *
 * @author wanghaowei
 * @date 2020年6月22日
 */
public enum Operation {

    UPDATE("修改","U"),
    INSERT("新增","C"),
    DELETE("删除","D");

    public String operationName;
    public String operationMark;

    Operation(String operationName, String operationMark) {
        this.operationName = operationName;
        this.operationMark = operationMark;
    }

    public static String mark(String operationName){
        if (StringUtils.isEmpty(operationName)) {
            return null;
        }
        Operation[] operations = Operation.values();
        for (Operation operation : operations) {
            if(operation.operationName.equals(operationName)){
                return operation.operationMark;
            }
        }
        return null;
    }

}
