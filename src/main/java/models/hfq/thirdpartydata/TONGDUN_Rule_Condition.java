package models.hfq.thirdpartydata;

import java.io.Serializable;

public class TONGDUN_Rule_Condition implements Serializable {

    private static final long serialVersionUID = 1L;

    //主属性的字段名。
    private String dimType;//"id_number"
    //主属性的字段值。
    private String dimValue;//"130102199005122436"
    private String result;//"5.0"
    /**
     * 当type为frequency_one_dim时，表示命中了频度规则单维度,包含以上三个字段（基础字段）
     * 当type为active_days_one时表示命中了活跃天数规则。以上
     * 当type为active_days_two时表示命中了活跃天数规则。
     * 当type为last_match时，表示命中了最近连续次数规则。
     * 当type为count时，表示命中了次数统计规则。
     * 当type为four_calculation时表示命中了四则运算规则
     * 当type为function_kit时，表示命中了函数工具箱规则
     */
    private String type;//"grey_list"/"cross_partner"/"frequency_distinct"/fuzzy_black_list
    //当type为frequency_count时，表示命中了频度规则双维度出现次数，它包含的字段有：
    //从属性的字段名
    private String subDimType;
    /**
     * 当type为frequency_distinct时，表示命中了频度规则关联次数，它包含的字段有：
     * 当type为custom_list时，表命中了自定义列表规则。其字段定义如下：
     * 当type为cross_velocity_distinct时，表示命中了跨事件频度统计规则，双维度出现次数。其字段定义如下：
     * 主属性关联从属性的列表-List<String>
     */
    private String list;
    /**
     * 当type为black_list时，表示命中了风险名单规则，它包含的字段有-JSON：
     * 当type为grey_list时，表示命中了关注名单规则，字段如下：-JSON
     * 当type为fuzzy_black_list时，表示命中了模糊证据库规则，其字段定义如下：
     * 当type为creditList_index_detail时，表示命中了信贷名单库指标，它包含的字段有：-JSON
     * 命中的风险名单数据-JSON
     */
    private String hits;
    //当type为fp_exception时，表示命中了设备指纹异常规则，字段如下：
    //异常代码
    private String code;
    //异常代码的显示名
    private String codeDisplayName;
    //当type为geo_ip_distance时，表示命中了移动距离规则，其字段定义如下：
    //距离单位，比如km表示千米，m表示米。
    private String unit;
    //当type为proxy_ip时，表示命中了代理IP规则，其字段定义如下：
    //代理类型。比如VPN表示VPN类型的代理
    private String proxyIpType;
    //当type为match_address时，表示命中了地址匹配规则。其字段定义如下：
    //地址A的字段名，比如home_address表示家庭地址。
    private String addressA;
    //客户传的地址A的具体值。
    private String addressAValue;
    //地址B的字段名，比如home_address表示家庭地址。
    private String addressB;
    //客户传的地址B的具体值。
    private String addressBValue;
    //当type为gps_distance时，表示命中了GPS坐标距离规则。其字段定义如下：
    private String gpsA;
    private String gpsB;
    //当type为regex时表示命中了正则表达式规则，其字段定义如下：
    private String value;
    /**
     * 当type为event_time_diff时表示命中了事件时间差规则。其字段定义如下：
     * 当type为time_diff时表示命中了时间差规则。其字段定义如下：
     * 相差时间展示。比如1.1小时。
     */
    private String diffDisplay;
    /**
     * 当type为cross_event时表示命中了跨事件规则。其字段定义如下：
     * 当type为cross_velocity_one_dim时，表示命中了跨事件频度统计规则，单维度的情况。其字段定义如下：
     * 当type为cross_velocity_count时，表示命中了跨事件频度统计规则，双维度出现次数。其字段定义如下：
     * 匹配字段的字段名。
     */
    private String matchDimType;
    /**
     * 当type为calculate时，表示命中了平均值规则、求和规则或方差规则。其字段定义如下：
     * 当type为min_max时，表示命中了最大最小值规则。其字段定义如下：
     * 计算类型。sum表示求和，average表示平均值，variance表示方差，standard_variance表示标准差。
     */
    private String calcType;
    private String eventType;//"Loan"
    //当type为association_partner时，表示命中了关联合作方规则。其字段定义如下：
    //如果配置了多个主属性，展示各个主属性的关联合作方个数-JSON
    private String resultsForDim;
    //保存了各个主属性的行业类型分布情况-JSON
    private String hitsForDim;
    /**
     * 当type为association_industry时，表示命中了关联行业类型规则。-JSON
     * 当type为discredit_count时，表示命中了信贷逾期统计规则。其字段定义如下：-JSON
     * 当type为cross_partner时，表示命中了跨合作方规则。其字段定义如下：-JSON
     * 当type为usual_browser时，表示命中了常用浏览器规则.其字段定义如下：
     * 当type为usual_device时，表示命中了常用设备规则。其字段定义如下：
     * 当type为usual_location时，表示命中了常用地理位置规则。其字段定义如下：
     */
    private String unitDisplayName;
    //当type为keyword时，表示命中了关键词规则。其字段定义如下：-List<String>
    //命中的关键词列表
    private String data;
    //关键词替换为*后的原始文本
    private String replaceText;
    //当type为android_cheat_app时，表示命中了android作弊工具识别规则。其字段定义如下：
    //当type为ios_cheat_app时，表示命中了IOS作弊工具识别规则。其字段定义如下：
    private String hookMethod;
    private String hookInline;
    private String hookAddress;
    private String hookIMP;
    //当type为android_emulator时，表示命中了安卓模拟器识别规则。其字段定义如下：
    private String emulatorType;
    //device_status_abnormal，表示命中了设备状态异常规则。其字段定义如下：-List<String>
    private String abnormalTags;
    //当type为suspected_team时，表示命中了风险群体规则。其字段定义如下：-JSON


    public String getDimType() {
        return dimType;
    }

    public void setDimType(String dimType) {
        this.dimType = dimType;
    }

    public String getDimValue() {
        return dimValue;
    }

    public void setDimValue(String dimValue) {
        this.dimValue = dimValue;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubDimType() {
        return subDimType;
    }

    public void setSubDimType(String subDimType) {
        this.subDimType = subDimType;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    public String getHits() {
        return hits;
    }

    public void setHits(String hits) {
        this.hits = hits;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeDisplayName() {
        return codeDisplayName;
    }

    public void setCodeDisplayName(String codeDisplayName) {
        this.codeDisplayName = codeDisplayName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getProxyIpType() {
        return proxyIpType;
    }

    public void setProxyIpType(String proxyIpType) {
        this.proxyIpType = proxyIpType;
    }

    public String getAddressA() {
        return addressA;
    }

    public void setAddressA(String addressA) {
        this.addressA = addressA;
    }

    public String getAddressAValue() {
        return addressAValue;
    }

    public void setAddressAValue(String addressAValue) {
        this.addressAValue = addressAValue;
    }

    public String getAddressB() {
        return addressB;
    }

    public void setAddressB(String addressB) {
        this.addressB = addressB;
    }

    public String getAddressBValue() {
        return addressBValue;
    }

    public void setAddressBValue(String addressBValue) {
        this.addressBValue = addressBValue;
    }

    public String getGpsA() {
        return gpsA;
    }

    public void setGpsA(String gpsA) {
        this.gpsA = gpsA;
    }

    public String getGpsB() {
        return gpsB;
    }

    public void setGpsB(String gpsB) {
        this.gpsB = gpsB;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDiffDisplay() {
        return diffDisplay;
    }

    public void setDiffDisplay(String diffDisplay) {
        this.diffDisplay = diffDisplay;
    }

    public String getMatchDimType() {
        return matchDimType;
    }

    public void setMatchDimType(String matchDimType) {
        this.matchDimType = matchDimType;
    }

    public String getCalcType() {
        return calcType;
    }

    public void setCalcType(String calcType) {
        this.calcType = calcType;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getResultsForDim() {
        return resultsForDim;
    }

    public void setResultsForDim(String resultsForDim) {
        this.resultsForDim = resultsForDim;
    }

    public String getHitsForDim() {
        return hitsForDim;
    }

    public void setHitsForDim(String hitsForDim) {
        this.hitsForDim = hitsForDim;
    }

    public String getUnitDisplayName() {
        return unitDisplayName;
    }

    public void setUnitDisplayName(String unitDisplayName) {
        this.unitDisplayName = unitDisplayName;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getReplaceText() {
        return replaceText;
    }

    public void setReplaceText(String replaceText) {
        this.replaceText = replaceText;
    }

    public String getHookMethod() {
        return hookMethod;
    }

    public void setHookMethod(String hookMethod) {
        this.hookMethod = hookMethod;
    }

    public String getHookInline() {
        return hookInline;
    }

    public void setHookInline(String hookInline) {
        this.hookInline = hookInline;
    }

    public String getHookAddress() {
        return hookAddress;
    }

    public void setHookAddress(String hookAddress) {
        this.hookAddress = hookAddress;
    }

    public String getHookIMP() {
        return hookIMP;
    }

    public void setHookIMP(String hookIMP) {
        this.hookIMP = hookIMP;
    }

    public String getEmulatorType() {
        return emulatorType;
    }

    public void setEmulatorType(String emulatorType) {
        this.emulatorType = emulatorType;
    }

    public String getAbnormalTags() {
        return abnormalTags;
    }

    public void setAbnormalTags(String abnormalTags) {
        this.abnormalTags = abnormalTags;
    }
}
