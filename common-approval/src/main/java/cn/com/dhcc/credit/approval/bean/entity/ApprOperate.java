package cn.com.dhcc.credit.approval.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * @author 
 * 操作记录表
 */
@Entity
@Table(name="APPR_OPERATE")
public class ApprOperate implements Serializable {
    /**
     * ID
     */
    private String id;

    /**
     * 操作员
     */
    private String operator;

    /**
     * 操作方式
     */
    private String operatorMode;

    /**
     * 业务类型
     */
    private String bussType;

    /**
     * 具体信息段
     */
    private String infoSegment;

    /**
     * 业务发生日期
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date bussDate;

    /**
     * 操作时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date operateDate;

    /**
     * 操作字段
     */
    private String operateField;

    /**
     * 操作字段旧值
     */
    private String fieldNewvalue;

    /**
     * 操作字段新值
     */
    private String fieldOldvalue;

    /**
     * 审批任务表ID
     */
    private String taskId;

    /**
     * 业务表ID
     */
    private String dataId;

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @Column(name = "OPERATOR")
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
    @Column(name = "OPERATOR_MODE")
    public String getOperatorMode() {
        return operatorMode;
    }

    public void setOperatorMode(String operatorMode) {
        this.operatorMode = operatorMode;
    }
    @Column(name = "BUSS_TYPE")
    public String getBussType() {
        return bussType;
    }

    public void setBussType(String bussType) {
        this.bussType = bussType;
    }
    @Column(name = "INFO_SEGMENT")
    public String getInfoSegment() {
        return infoSegment;
    }

    public void setInfoSegment(String infoSegment) {
        this.infoSegment = infoSegment;
    }
    @Column(name = "BUSS_DATE")
    public Date getBussDate() {
        return bussDate;
    }

    public void setBussDate(Date bussDate) {
        this.bussDate = bussDate;
    }
    @Column(name = "OPERATE_DATE")
    public Date getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }
    @Column(name = "OPERATE_FIELD")
    public String getOperateField() {
        return operateField;
    }

    public void setOperateField(String operateField) {
        this.operateField = operateField;
    }
    @Column(name = "FIELD_NEWVALUE")
    public String getFieldNewvalue() {
        return fieldNewvalue;
    }

    public void setFieldNewvalue(String fieldNewvalue) {
        this.fieldNewvalue = fieldNewvalue;
    }
    @Column(name = "FIELD_OLDVALUE")
    public String getFieldOldvalue() {
        return fieldOldvalue;
    }

    public void setFieldOldvalue(String fieldOldvalue) {
        this.fieldOldvalue = fieldOldvalue;
    }
    @Column(name = "TASK_ID")
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    @Column(name = "DATA_ID")
    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ApprOperate other = (ApprOperate) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOperator() == null ? other.getOperator() == null : this.getOperator().equals(other.getOperator()))
            && (this.getOperatorMode() == null ? other.getOperatorMode() == null : this.getOperatorMode().equals(other.getOperatorMode()))
            && (this.getBussType() == null ? other.getBussType() == null : this.getBussType().equals(other.getBussType()))
            && (this.getInfoSegment() == null ? other.getInfoSegment() == null : this.getInfoSegment().equals(other.getInfoSegment()))
            && (this.getBussDate() == null ? other.getBussDate() == null : this.getBussDate().equals(other.getBussDate()))
            && (this.getOperateDate() == null ? other.getOperateDate() == null : this.getOperateDate().equals(other.getOperateDate()))
            && (this.getOperateField() == null ? other.getOperateField() == null : this.getOperateField().equals(other.getOperateField()))
            && (this.getFieldNewvalue() == null ? other.getFieldNewvalue() == null : this.getFieldNewvalue().equals(other.getFieldNewvalue()))
            && (this.getFieldOldvalue() == null ? other.getFieldOldvalue() == null : this.getFieldOldvalue().equals(other.getFieldOldvalue()))
            && (this.getTaskId() == null ? other.getTaskId() == null : this.getTaskId().equals(other.getTaskId()))
            && (this.getDataId() == null ? other.getDataId() == null : this.getDataId().equals(other.getDataId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOperator() == null) ? 0 : getOperator().hashCode());
        result = prime * result + ((getOperatorMode() == null) ? 0 : getOperatorMode().hashCode());
        result = prime * result + ((getBussType() == null) ? 0 : getBussType().hashCode());
        result = prime * result + ((getInfoSegment() == null) ? 0 : getInfoSegment().hashCode());
        result = prime * result + ((getBussDate() == null) ? 0 : getBussDate().hashCode());
        result = prime * result + ((getOperateDate() == null) ? 0 : getOperateDate().hashCode());
        result = prime * result + ((getOperateField() == null) ? 0 : getOperateField().hashCode());
        result = prime * result + ((getFieldNewvalue() == null) ? 0 : getFieldNewvalue().hashCode());
        result = prime * result + ((getFieldOldvalue() == null) ? 0 : getFieldOldvalue().hashCode());
        result = prime * result + ((getTaskId() == null) ? 0 : getTaskId().hashCode());
        result = prime * result + ((getDataId() == null) ? 0 : getDataId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", operator=").append(operator);
        sb.append(", operatorMode=").append(operatorMode);
        sb.append(", bussType=").append(bussType);
        sb.append(", infoSegment=").append(infoSegment);
        sb.append(", bussDate=").append(bussDate);
        sb.append(", operateDate=").append(operateDate);
        sb.append(", operateField=").append(operateField);
        sb.append(", fieldNewvalue=").append(fieldNewvalue);
        sb.append(", fieldOldvalue=").append(fieldOldvalue);
        sb.append(", taskId=").append(taskId);
        sb.append(", dataId=").append(dataId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}