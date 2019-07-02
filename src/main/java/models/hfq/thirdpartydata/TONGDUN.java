package models.hfq.thirdpartydata;

import java.io.Serializable;
import java.util.List;

public class TONGDUN implements Serializable {

    private static final long serialVersionUID = 1L;

    //风险决策的最终结果，Accept/Review/Reject
    private String finalDecision;//"Accept"
    //最终的风险分数，只有在权重模式下有效
    private String finalScore;//16
    private List<TONGDUN_Rule> hitRules;
//    private List<TONGDUN_Rule> hitRulesConvert;
    private String policyName;//"SimpleLoan_ios"
    //策略集内容-json
    private String policySet;
    //策略集名称
    private String policySetName;//"SimpleLoan_ios"
    //命中的风险类型的标识及风险结果的集合，只显示结果为Review和Reject的
    //格式为风险类型_评估结果，此处表示账户盗用低风险
    private String riskType;//"accountTakeOver_review"
    //请求的唯一序列号
    private String seqId;//"1561447090985747S427B68926167607"
    //借款事件生成的申请编号,32位
    private String applicationId;
    // 调用api耗时毫秒数
    private String spendTime;//63
    private String success;//true
    //设备指纹信息-JSON
    private String deviceInfo;
    //用户传入的地理位置信息-JSON
    private String geoipInfo;
    //用户传入的身份证和手机号-JSON
    private String attribution;
    //信用评分信息,如果用户传入手机号、身份证号字段，则会给出相应字段的信用分数-JSON
    private String creditScore;
    //决策结果自定义输出结果-JSON
    private String outputFields;
    //TONGDUN_DETAILS
    private String reasonCode;//"200"
    private String reasonDesc;//"获取成功"
//    private List<TONGDUN_Rule> rules;
//    private String successd;//true


    public String getFinalDecision() {
        return finalDecision;
    }

    public void setFinalDecision(String finalDecision) {
        this.finalDecision = finalDecision;
    }

    public String getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(String finalScore) {
        this.finalScore = finalScore;
    }

    public List<TONGDUN_Rule> getHitRules() {
        return hitRules;
    }

    public void setHitRules(List<TONGDUN_Rule> hitRules) {
        this.hitRules = hitRules;
    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public String getPolicySet() {
        return policySet;
    }

    public void setPolicySet(String policySet) {
        this.policySet = policySet;
    }

    public String getPolicySetName() {
        return policySetName;
    }

    public void setPolicySetName(String policySetName) {
        this.policySetName = policySetName;
    }

    public String getRiskType() {
        return riskType;
    }

    public void setRiskType(String riskType) {
        this.riskType = riskType;
    }

    public String getSeqId() {
        return seqId;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getSpendTime() {
        return spendTime;
    }

    public void setSpendTime(String spendTime) {
        this.spendTime = spendTime;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getGeoipInfo() {
        return geoipInfo;
    }

    public void setGeoipInfo(String geoipInfo) {
        this.geoipInfo = geoipInfo;
    }

    public String getAttribution() {
        return attribution;
    }

    public void setAttribution(String attribution) {
        this.attribution = attribution;
    }

    public String getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(String creditScore) {
        this.creditScore = creditScore;
    }

    public String getOutputFields() {
        return outputFields;
    }

    public void setOutputFields(String outputFields) {
        this.outputFields = outputFields;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getReasonDesc() {
        return reasonDesc;
    }

    public void setReasonDesc(String reasonDesc) {
        this.reasonDesc = reasonDesc;
    }

}



