package com.cplatform.mall.back.store.entity;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cplatform.mall.back.entity.IdEntity;

/**
 * 
 * 商户自审. <br>
 * 类详细说明.
 * <p>
 * Copyright: Copyright (c) 2013-5-31 下午03:58:28
 * <p>
 * Company: 北京宽连十方数字技术有限公司
 * <p>
 * @author zhaowei@c-platform.com
 * @version 1.0.0
 */
@Entity
@Table(name = "t_shop_settings")
public class ShopSettings extends IdEntity {
	

	 /**首页文字介绍**/
	private String homePageWord;
	
	 /**1--业务门店  2--商户 3--渠道商**/
    private Long shopClass;
    


	/**商户招牌图片路径**/
    private Long shopId ;


	/**图片名称**/
    private String picName;
    
    private String auditAdvice;			//审核意见
    
    /**发布状态。 0未发布 1已发布 修改任意网店设置后此字段为 0 审核后为1**/
    private Long pubTag;
    
    @Column(name = "OPERATE_END_TIME")
    public String getOperateEndTime() {
		return operateEndTime;
	}

	public void setOperateEndTime(String operateEndTime) {
		this.operateEndTime = operateEndTime;
	}

	private String  operateEndTime;
    

	@Column(name = "PUB_TAG")
	public Long getPubTag() {
		return pubTag;
	}
     
	public void setPubTag(Long pubTag) {
		this.pubTag = pubTag;
	}

	/**商户招牌图片路径**/
	private String shopPicUrl;
	
	/** 商户名称 **/
	private String name;
	
	@Transient
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static Map<Long, String> shopClassMap = null;
	public static Map<Long, String> pubTagMap =null;
	
	
	
	
	static {
		
		
		shopClassMap = new HashMap<Long, String>();
		shopClassMap.put(1L, "业务门店");
		shopClassMap.put(2L, "商店");
		shopClassMap.put(3L, "渠道商");
		
		
		
		pubTagMap = new HashMap<Long, String>();
		pubTagMap.put(0L, "未发布 ");
		pubTagMap.put(1L, "已发布");
		pubTagMap.put(2L, "审核驳回");
		
		
	}

	@Column(name = "SHOP_PIC_URL")
    public String getShopPicUrl() {
		return shopPicUrl;
	}

	public void setShopPicUrl(String shopPicUrl) {
		this.shopPicUrl = shopPicUrl;
	}
	@Column(name = "HOMEPAGE_WORD")
	public String getHomePageWord() {
		return homePageWord;
	}

	public void setHomePageWord(String homePageWord) {
		this.homePageWord = homePageWord;
	}
	@Column(name = "shop_class")
	    public Long getShopClass() {
		return shopClass;
	}

	public void setShopClass(Long shopClass) {
		this.shopClass = shopClass;
	}
	
	@Column(name = "SHOP_ID")
	    public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	@Column(name = "PIC_NAME")
	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	
	
	@Transient
	public String getShopClassName() {
		return shopClassMap.get(this.getShopClass());
	}
	
	@Transient
	public String getPubTagName() {
		return pubTagMap.get(this.getPubTag());
	}

	@Column(name="AUDIT_ADVICE")
	public String getAuditAdvice() {
		return auditAdvice;
	}

	public void setAuditAdvice(String auditAdvice) {
		this.auditAdvice = auditAdvice;
	}

}