package com.stars.travel.model.base;

import java.util.Date;

public class User implements BaseBean {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String email;

    private String password;

    private Date createtime;

    private String source;

    private String oauthid;

    private String sinatoken;

    private String qqoauthid;

    private String weixin;

    private Short state;

    private Short sex;

    private String signature;

    private String strategyInfo;

    private String score;

    private String summary;

    private String phone;

    private String personalemail;

    private String weibo;

    private Short activated;

    private String portrait;

    private Short type;

    private String introduceImage;

    private String introduction;

    private String address;

    private String profession;

    private String namespy;

    private String idmd5;

    private String fromsite;

    private String utmSource;

    private Integer isactiveemailsend;

    private String nikename;

    private String qqnikename;

    private Boolean islogintokenoverdue;

    private String accessversion;

    private Date lastlogintime;

    private Boolean isEnable;

    private String price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getOauthid() {
        return oauthid;
    }

    public void setOauthid(String oauthid) {
        this.oauthid = oauthid == null ? null : oauthid.trim();
    }

    public String getSinatoken() {
        return sinatoken;
    }

    public void setSinatoken(String sinatoken) {
        this.sinatoken = sinatoken == null ? null : sinatoken.trim();
    }

    public String getQqoauthid() {
        return qqoauthid;
    }

    public void setQqoauthid(String qqoauthid) {
        this.qqoauthid = qqoauthid == null ? null : qqoauthid.trim();
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin == null ? null : weixin.trim();
    }

    public Short getState() {
        return state;
    }

    public void setState(Short state) {
        this.state = state;
    }

    public Short getSex() {
        return sex;
    }

    public void setSex(Short sex) {
        this.sex = sex;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature == null ? null : signature.trim();
    }

    public String getStrategyInfo() {
        return strategyInfo;
    }

    public void setStrategyInfo(String strategyInfo) {
        this.strategyInfo = strategyInfo == null ? null : strategyInfo.trim();
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score == null ? null : score.trim();
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getPersonalemail() {
        return personalemail;
    }

    public void setPersonalemail(String personalemail) {
        this.personalemail = personalemail == null ? null : personalemail.trim();
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo == null ? null : weibo.trim();
    }

    public Short getActivated() {
        return activated;
    }

    public void setActivated(Short activated) {
        this.activated = activated;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait == null ? null : portrait.trim();
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public String getIntroduceImage() {
        return introduceImage;
    }

    public void setIntroduceImage(String introduceImage) {
        this.introduceImage = introduceImage == null ? null : introduceImage.trim();
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession == null ? null : profession.trim();
    }

    public String getNamespy() {
        return namespy;
    }

    public void setNamespy(String namespy) {
        this.namespy = namespy == null ? null : namespy.trim();
    }

    public String getIdmd5() {
        return idmd5;
    }

    public void setIdmd5(String idmd5) {
        this.idmd5 = idmd5 == null ? null : idmd5.trim();
    }

    public String getFromsite() {
        return fromsite;
    }

    public void setFromsite(String fromsite) {
        this.fromsite = fromsite == null ? null : fromsite.trim();
    }

    public String getUtmSource() {
        return utmSource;
    }

    public void setUtmSource(String utmSource) {
        this.utmSource = utmSource == null ? null : utmSource.trim();
    }

    public Integer getIsactiveemailsend() {
        return isactiveemailsend;
    }

    public void setIsactiveemailsend(Integer isactiveemailsend) {
        this.isactiveemailsend = isactiveemailsend;
    }

    public String getNikename() {
        return nikename;
    }

    public void setNikename(String nikename) {
        this.nikename = nikename == null ? null : nikename.trim();
    }

    public String getQqnikename() {
        return qqnikename;
    }

    public void setQqnikename(String qqnikename) {
        this.qqnikename = qqnikename == null ? null : qqnikename.trim();
    }

    public Boolean getIslogintokenoverdue() {
        return islogintokenoverdue;
    }

    public void setIslogintokenoverdue(Boolean islogintokenoverdue) {
        this.islogintokenoverdue = islogintokenoverdue;
    }

    public String getAccessversion() {
        return accessversion;
    }

    public void setAccessversion(String accessversion) {
        this.accessversion = accessversion == null ? null : accessversion.trim();
    }

    public Date getLastlogintime() {
        return lastlogintime;
    }

    public void setLastlogintime(Date lastlogintime) {
        this.lastlogintime = lastlogintime;
    }

    public Boolean getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price == null ? null : price.trim();
    }

    /** 
     * 拷贝，将对象中的字段全部拷贝到子对象中
     * @param bean 接收对象的子类
     * @return 拷贝完成后的子类
     */ 
    public  <T extends User> T copy(T bean) {
        bean.setId(getId());
        bean.setName(getName());
        bean.setEmail(getEmail());
        bean.setPassword(getPassword());
        bean.setCreatetime(getCreatetime());
        bean.setSource(getSource());
        bean.setOauthid(getOauthid());
        bean.setSinatoken(getSinatoken());
        bean.setQqoauthid(getQqoauthid());
        bean.setWeixin(getWeixin());
        bean.setState(getState());
        bean.setSex(getSex());
        bean.setSignature(getSignature());
        bean.setStrategyInfo(getStrategyInfo());
        bean.setScore(getScore());
        bean.setSummary(getSummary());
        bean.setPhone(getPhone());
        bean.setPersonalemail(getPersonalemail());
        bean.setWeibo(getWeibo());
        bean.setActivated(getActivated());
        bean.setPortrait(getPortrait());
        bean.setType(getType());
        bean.setIntroduceImage(getIntroduceImage());
        bean.setIntroduction(getIntroduction());
        bean.setAddress(getAddress());
        bean.setProfession(getProfession());
        bean.setNamespy(getNamespy());
        bean.setIdmd5(getIdmd5());
        bean.setFromsite(getFromsite());
        bean.setUtmSource(getUtmSource());
        bean.setIsactiveemailsend(getIsactiveemailsend());
        bean.setNikename(getNikename());
        bean.setQqnikename(getQqnikename());
        bean.setIslogintokenoverdue(getIslogintokenoverdue());
        bean.setAccessversion(getAccessversion());
        bean.setLastlogintime(getLastlogintime());
        bean.setIsEnable(getIsEnable());
        bean.setPrice(getPrice());
        return bean;
    }

    /** 
     * 格式化显示
     */ 
    @Override
    public String toString() {
        return "{" + 
        	"id:" + getId() + 
        	", name:" + getName() + 
        	", email:" + getEmail() + 
        	", password:" + getPassword() + 
        	", createtime:" + getCreatetime() + 
        	", source:" + getSource() + 
        	", oauthid:" + getOauthid() + 
        	", sinatoken:" + getSinatoken() + 
        	", qqoauthid:" + getQqoauthid() + 
        	", weixin:" + getWeixin() + 
        	", state:" + getState() + 
        	", sex:" + getSex() + 
        	", signature:" + getSignature() + 
        	", strategyInfo:" + getStrategyInfo() + 
        	", score:" + getScore() + 
        	", summary:" + getSummary() + 
        	", phone:" + getPhone() + 
        	", personalemail:" + getPersonalemail() + 
        	", weibo:" + getWeibo() + 
        	", activated:" + getActivated() + 
        	", portrait:" + getPortrait() + 
        	", type:" + getType() + 
        	", introduceImage:" + getIntroduceImage() + 
        	", introduction:" + getIntroduction() + 
        	", address:" + getAddress() + 
        	", profession:" + getProfession() + 
        	", namespy:" + getNamespy() + 
        	", idmd5:" + getIdmd5() + 
        	", fromsite:" + getFromsite() + 
        	", utmSource:" + getUtmSource() + 
        	", isactiveemailsend:" + getIsactiveemailsend() + 
        	", nikename:" + getNikename() + 
        	", qqnikename:" + getQqnikename() + 
        	", islogintokenoverdue:" + getIslogintokenoverdue() + 
        	", accessversion:" + getAccessversion() + 
        	", lastlogintime:" + getLastlogintime() + 
        	", isEnable:" + getIsEnable() + 
        	", price:" + getPrice() + 
        "}";
    }
}