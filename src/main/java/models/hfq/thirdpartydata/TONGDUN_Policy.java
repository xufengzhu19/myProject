package models.hfq.thirdpartydata;

import java.io.Serializable;
import java.util.List;

public class TONGDUN_Policy implements Serializable {
    private static final long serialVersionUID = 1L;

    //命中的规则列表
    private List<TONGDUN_Rule> hitRules;
//    private List<TONGDUN_Rule> hitRulesConvert;
    // 策略结果，Review是审核
    private String policyDecision;//"Accept"/"Review"
    // 策略模式，共有三种：1.首次匹配：取第一条命中的规则作为返回结果；
    // 2.最坏匹配：取命中规则中决策结果最坏的作为返回结果；
    // 3.权重模式：对所有命中规则进行加权处理作为最后的返回结果。
    // 该条策略的策略模式，FirstMatch/WorstMatch/Weighted,此处为权重模式
    private String policyMode;//"Weighted"
    private String policyName;//"异常借款_ios"
    //策略分数，根据命中规则分数而来，不同模式计算方式不同。
    //该条策略的风险分数，只有在权重模式下有效
    private String policyScore;//16
    private String policyUuid;//"81d94324f88d48aeb5bb482812bbf2b3"
    // 本策略要识别的风险类型的标识，accountTakeOver表示账户盗用，其它标识详见资源中心-风险决策服务-附录2
    private String riskType;//"suspiciousLoan"


    public List<TONGDUN_Rule> getHitRules() {
        return hitRules;
    }

    public void setHitRules(List<TONGDUN_Rule> hitRules) {
        this.hitRules = hitRules;
    }

    public String getPolicyDecision() {
        return policyDecision;
    }

    public void setPolicyDecision(String policyDecision) {
        this.policyDecision = policyDecision;
    }

    public String getPolicyMode() {
        return policyMode;
    }

    public void setPolicyMode(String policyMode) {
        this.policyMode = policyMode;
    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public String getPolicyScore() {
        return policyScore;
    }

    public void setPolicyScore(String policyScore) {
        this.policyScore = policyScore;
    }

    public String getPolicyUuid() {
        return policyUuid;
    }

    public void setPolicyUuid(String policyUuid) {
        this.policyUuid = policyUuid;
    }

    public String getRiskType() {
        return riskType;
    }

    public void setRiskType(String riskType) {
        this.riskType = riskType;
    }
}
