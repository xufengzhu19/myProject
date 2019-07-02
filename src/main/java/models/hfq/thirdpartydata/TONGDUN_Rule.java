package models.hfq.thirdpartydata;

import java.io.Serializable;

public class TONGDUN_Rule implements Serializable {

    private static final long serialVersionUID = 1L;

    private String backId;//"2794451"
    // 规则决策结果，Accept/Review/Reject，只有在首次匹配和最坏匹配模式下有效
    private String decision;//"Accept"
    private String name;//"近60个月以上申请人在多个平台申请借款"
    // 规则风险权重，只有在权重模式下有效
    private String score;//6
    /**
     * 当type为frequency_one_dim时，表示命中了频度规则单维度
     * 当type为frequency_count时，表示命中了频度规则双维度出现次数
     * 当type为frequency_distinct时，表示命中了频度规则关联次数
     */
    private String type;//"other"/"mobile"/"identityno"
    private String uuid;//"30878fd7425648e992c7d473fe8d86b3"
    private String ruleId;//"2794451"
    // 该规则的父规则uuid
    private String parentUuid;//"2794451"
    private String conditions;//json


    public String getBackId() {
        return backId;
    }

    public void setBackId(String backId) {
        this.backId = backId;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getParentUuid() {
        return parentUuid;
    }

    public void setParentUuid(String parentUuid) {
        this.parentUuid = parentUuid;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }
}
