package cn.com.dhcc.credit.approval.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * @author 
 * 审批记录表
 */
@Entity
@Table(name="APPR_RECORD")
public class ApprRecord implements Serializable {
    /**
     * ID
     */
    private String id;

    /**
     * 审批员
     */
    private String approver;

    /**
     * 审批时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date approveDate;

    /**
     * 审批状态
     */
    private String approveState;

    /**
     * 审批员级别
     */
    private String approverLevel;

    /**
     * 审批备注
     */
    private String remark;

    /**
     * 审批任务表ID
     */
    private String taskId;

    /**
     * 0有效，1无效
     */
    private String dataFlag;

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
    @Column(name = "APPROVER")
    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }
    @Column(name = "APPROVE_DATE")
    public Date getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }
    @Column(name = "APPROVE_STATE")
    public String getApproveState() {
        return approveState;
    }

    public void setApproveState(String approveState) {
        this.approveState = approveState;
    }
    @Column(name = "APPROVER_LEVEL")
    public String getApproverLevel() {
        return approverLevel;
    }

    public void setApproverLevel(String approverLevel) {
        this.approverLevel = approverLevel;
    }
    @Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    @Column(name = "TASK_ID")
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    @Column(name = "DATA_FLAG")
    public String getDataFlag() {
        return dataFlag;
    }

    public void setDataFlag(String dataFlag) {
        this.dataFlag = dataFlag;
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
        ApprRecord other = (ApprRecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getApprover() == null ? other.getApprover() == null : this.getApprover().equals(other.getApprover()))
            && (this.getApproveDate() == null ? other.getApproveDate() == null : this.getApproveDate().equals(other.getApproveDate()))
            && (this.getApproveState() == null ? other.getApproveState() == null : this.getApproveState().equals(other.getApproveState()))
            && (this.getApproverLevel() == null ? other.getApproverLevel() == null : this.getApproverLevel().equals(other.getApproverLevel()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getTaskId() == null ? other.getTaskId() == null : this.getTaskId().equals(other.getTaskId()))
            && (this.getDataFlag() == null ? other.getDataFlag() == null : this.getDataFlag().equals(other.getDataFlag()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getApprover() == null) ? 0 : getApprover().hashCode());
        result = prime * result + ((getApproveDate() == null) ? 0 : getApproveDate().hashCode());
        result = prime * result + ((getApproveState() == null) ? 0 : getApproveState().hashCode());
        result = prime * result + ((getApproverLevel() == null) ? 0 : getApproverLevel().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getTaskId() == null) ? 0 : getTaskId().hashCode());
        result = prime * result + ((getDataFlag() == null) ? 0 : getDataFlag().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", approver=").append(approver);
        sb.append(", approveDate=").append(approveDate);
        sb.append(", approveState=").append(approveState);
        sb.append(", approverLevel=").append(approverLevel);
        sb.append(", remark=").append(remark);
        sb.append(", taskId=").append(taskId);
        sb.append(", dataFlag=").append(dataFlag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}