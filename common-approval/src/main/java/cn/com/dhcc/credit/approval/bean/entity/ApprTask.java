package cn.com.dhcc.credit.approval.bean.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author 
 * 审批任务表
 */
@Entity
@Table(name="APPR_TASK")
public class ApprTask implements Serializable {
    /**
     * ID
     */
    private String taskId;

    /**
     * 姓名
     */
    private String operator;

    /**
     * 证件类型
     */
    private String idType;

    /**
     * 证件号码
     */
    private String idNum;

    /**
     * 业务编号
     */
    private String bussNum;

    /**
     * 业务类型
     */
    private String bussType;

    /**
     * 所属机构
     */
    private String deptCode;

    /**
     * 区分个人还是企业
     */
    private String bussCategory;

    /**
     * 操作时间
     */
    private Date operateDate;

    /**
     * 审批员
     */
    private String approver;

    /**
     * 审批时间
     */
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
     * 业务表ID
     */
    private String dataId;
    /**
     * 客户号
     */
    private String custId;

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "TASK_ID")
//    @GenericGenerator(name = "system-uuid", strategy = "uuid")
//    @GeneratedValue(generator = "system-uuid")
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    @Column(name = "OPERATOR")
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
    @Column(name = "ID_TYPE")
    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }
    @Column(name = "ID_NUM")
    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }
    @Column(name = "BUSS_NUM")
    public String getBussNum() {
        return bussNum;
    }

    public void setBussNum(String bussNum) {
        this.bussNum = bussNum;
    }
    @Column(name = "BUSS_TYPE")
    public String getBussType() {
        return bussType;
    }

    public void setBussType(String bussType) {
        this.bussType = bussType;
    }
    @Column(name = "DEPT_CODE")
    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }
    @Column(name = "BUSS_CATEGORY")
    public String getBussCategory() {
        return bussCategory;
    }

    public void setBussCategory(String bussCategory) {
        this.bussCategory = bussCategory;
    }
    @Column(name = "OPERATE_DATE")
    public Date getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
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

    public String getApproveState() {
        return approveState;
    }

    public void setApproveState(String approveState) {
        this.approveState = approveState;
    }

    public String getApproverLevel() {
        return approverLevel;
    }

    public void setApproverLevel(String approverLevel) {
        this.approverLevel = approverLevel;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    @Column(name = "CUST_ID")
    public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((approveDate == null) ? 0 : approveDate.hashCode());
		result = prime * result + ((approveState == null) ? 0 : approveState.hashCode());
		result = prime * result + ((approver == null) ? 0 : approver.hashCode());
		result = prime * result + ((approverLevel == null) ? 0 : approverLevel.hashCode());
		result = prime * result + ((bussCategory == null) ? 0 : bussCategory.hashCode());
		result = prime * result + ((bussNum == null) ? 0 : bussNum.hashCode());
		result = prime * result + ((bussType == null) ? 0 : bussType.hashCode());
		result = prime * result + ((custId == null) ? 0 : custId.hashCode());
		result = prime * result + ((dataId == null) ? 0 : dataId.hashCode());
		result = prime * result + ((deptCode == null) ? 0 : deptCode.hashCode());
		result = prime * result + ((idNum == null) ? 0 : idNum.hashCode());
		result = prime * result + ((idType == null) ? 0 : idType.hashCode());
		result = prime * result + ((operateDate == null) ? 0 : operateDate.hashCode());
		result = prime * result + ((operator == null) ? 0 : operator.hashCode());
		result = prime * result + ((taskId == null) ? 0 : taskId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ApprTask other = (ApprTask) obj;
		if (approveDate == null) {
			if (other.approveDate != null)
				return false;
		} else if (!approveDate.equals(other.approveDate))
			return false;
		if (approveState == null) {
			if (other.approveState != null)
				return false;
		} else if (!approveState.equals(other.approveState))
			return false;
		if (approver == null) {
			if (other.approver != null)
				return false;
		} else if (!approver.equals(other.approver))
			return false;
		if (approverLevel == null) {
			if (other.approverLevel != null)
				return false;
		} else if (!approverLevel.equals(other.approverLevel))
			return false;
		if (bussCategory == null) {
			if (other.bussCategory != null)
				return false;
		} else if (!bussCategory.equals(other.bussCategory))
			return false;
		if (bussNum == null) {
			if (other.bussNum != null)
				return false;
		} else if (!bussNum.equals(other.bussNum))
			return false;
		if (bussType == null) {
			if (other.bussType != null)
				return false;
		} else if (!bussType.equals(other.bussType))
			return false;
		if (custId == null) {
			if (other.custId != null)
				return false;
		} else if (!custId.equals(other.custId))
			return false;
		if (dataId == null) {
			if (other.dataId != null)
				return false;
		} else if (!dataId.equals(other.dataId))
			return false;
		if (deptCode == null) {
			if (other.deptCode != null)
				return false;
		} else if (!deptCode.equals(other.deptCode))
			return false;
		if (idNum == null) {
			if (other.idNum != null)
				return false;
		} else if (!idNum.equals(other.idNum))
			return false;
		if (idType == null) {
			if (other.idType != null)
				return false;
		} else if (!idType.equals(other.idType))
			return false;
		if (operateDate == null) {
			if (other.operateDate != null)
				return false;
		} else if (!operateDate.equals(other.operateDate))
			return false;
		if (operator == null) {
			if (other.operator != null)
				return false;
		} else if (!operator.equals(other.operator))
			return false;
		if (taskId == null) {
			if (other.taskId != null)
				return false;
		} else if (!taskId.equals(other.taskId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ApprTask [taskId=" + taskId + ", operator=" + operator + ", idType=" + idType + ", idNum=" + idNum
				+ ", bussNum=" + bussNum + ", bussType=" + bussType + ", deptCode=" + deptCode + ", bussCategory="
				+ bussCategory + ", operateDate=" + operateDate + ", approver=" + approver + ", approveDate="
				+ approveDate + ", approveState=" + approveState + ", approverLevel=" + approverLevel + ", dataId="
				+ dataId + ", custId=" + custId + "]";
	}

	
}