package com.cplatform.mall.back.order.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cplatform.mall.back.item.entity.ItemSale;
import com.cplatform.order.ActOrderInfo;

/**
 * 订单表 Title. <br>
 * Description.
 * <p>
 * Copyright: Copyright (c) 2013-6-7 下午5:26:19
 * <p>
 * Company: 北京宽连十方数字技术有限公司
 * <p>
 * Author: LiuDong@c-platform.com
 * <p>
 * Version: 1.0
 * <p>
 */
//@Entity
//@Table(name = "t_act_order")
public class Order extends ActOrderInfo {

	private String userName;

	/** 用户手机号码 **/
	private String terminalId;

	/** 退款IDS（多个用逗号,隔开） **/
	private String refundIds;

	/** 第一个退款单ID **/
	private Long orderRefundFirstId;

	/** 第二个退款单ID **/
	private Long orderRefundSecondId;

	/** 第一个退款单状态 **/
	private Long refundFirstStatus;

	/** 退款单计数 **/
	private int refundCount;
	/**
	 * 成功退款单数量，最多2个
	 */
	private int successRefundCount;
	
	private static Map<String, String> payOnDeliveryMap = null;
	static {
		payOnDeliveryMap = new HashMap<String, String>();
		payOnDeliveryMap.put("1", "是");
		payOnDeliveryMap.put("0", "否");
	}
	
	
	public static Map<String, String> getPayOnDeliveryMap() {
		return payOnDeliveryMap;
	}


	@Transient
	public String getPayOnDeliveryName(){
		return payOnDeliveryMap.get(this.getPayOnDelivery()+"");
	}
	
	@Transient
	public Long getStatus() {
		return status;
	}



	public void setStatus(Long status) {
		this.status = status;
	}

	private Long status;
	
	@Transient
	public String getCommand() {
		return command;
	}

	
	
	public void setCommand(String command) {
		this.command = command;
	}

	private String command;
	

	@Transient
	public Long getActId() {
		return actId;
	}

	public void setActId(Long actId) {
		this.actId = actId;
	}

	private Long actId;
	
	/**商品信息**/
	
	private String spliteInfo;
	
	
	@Transient
	public String getSpliteInfo() {
		return spliteInfo;
	}

	public void setSpliteInfo(String spliteInfo) {
		this.spliteInfo = spliteInfo;
	}

	/**
	 * 订单物流状态
	 */
	private Long expressStatus;
	
	/**
	 * 订单状态（组合状态）
	 */
	private String orderStatus;
	/**
	 * 退款单集合
	 */
	private List<OrderRefund> orderRefunds;
	/**
	 * 是否有退款权限，如果有页面上会有“增加退款单”按钮
	 */
	private boolean canRefund;
	
	private Long itemMode;
	
	
	/**
	 * 订单商品
	 */
	private List<ItemSale> itemSales;

	@Transient
	 public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	private String currency;
	
	@Transient
	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	private String actName;
	
	@Transient
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	
	@Transient
	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public void setRefundIds(String refundIds) {
		this.refundIds = refundIds;
	}

	@Transient
	public String getRefundIds() {
		return refundIds;
	}

	public void setOrderRefundFirstId(Long orderRefundFirstId) {
		this.orderRefundFirstId = orderRefundFirstId;
	}

	@Transient
	public Long getOrderRefundFirstId() {
		return orderRefundFirstId;
	}

	public void setOrderRefundSecondId(Long orderRefundSecondId) {
		this.orderRefundSecondId = orderRefundSecondId;
	}

	@Transient
	public Long getOrderRefundSecondId() {
		return orderRefundSecondId;
	}

	public void setRefundFirstStatus(Long refundFirstStatus) {
		this.refundFirstStatus = refundFirstStatus;
	}

	@Transient
	public Long getRefundFirstStatus() {
		return refundFirstStatus;
	}

	public void setRefundCount(int refundCount) {
		this.refundCount = refundCount;
	}

	@Transient
	public int getRefundCount() {
		return refundCount;
	}

	/** 业务类型map */
	private static Map<String, String> actTypeMap = null;
	static {
		actTypeMap = new HashMap<String, String>();
		actTypeMap.put("20", "短信购");
		// actTypeMap.put("50", "短信购(测试)");
		actTypeMap.put("40", "普通购买");
		// actTypeMap.put("41", "普通购买(测试)");
	}

	@Transient
	public static Map<String, String> getActTypeMap() {
		return actTypeMap;
	}

	@Transient
	public String getActTypeName() {
		String actTypeName = actTypeMap.get(this.getActType() + "");
		return actTypeName == null ? this.getActType() + "" : actTypeName;
	}

	/** 关闭状态map */
	private static Map<String, String> closeStatusMap = null;
	static {
		closeStatusMap = new HashMap<String, String>();
		closeStatusMap.put("1", "已关闭");
		closeStatusMap.put("0", "未关闭");
	}

	@Transient
	public static Map<String, String> getCloseStatusMap() {
		return closeStatusMap;
	}

	@Transient
	public String getCloseStatusName() {
		return closeStatusMap.get(this.getCloseStatus() + "");
	}

	/** 支付状态map */
	private static Map<String, String> payStatusMap = null;
	static {
		payStatusMap = new HashMap<String, String>();
		payStatusMap.put("2", "已支付");
		payStatusMap.put("1", "支付中");
		payStatusMap.put("0", "未支付");
	}

	/** 删除状态map */
	private static Map<String, String> deleteStatusMap = null;
	static {
		deleteStatusMap = new HashMap<String, String>();
		deleteStatusMap.put("1", "已删除");
		deleteStatusMap.put("0", "未删除");
	}
	
	@Transient
	public static Map<String, String> getPayStatusMap() {
		return payStatusMap;
	}

	@Transient
	public String getPayStatusName() {
		return payStatusMap.get(this.getPayStatus() + "");
	}
	
	@Transient
	public String getDeleteStatusName() {
		return deleteStatusMap.get(this.getDeleteStatus() + "");
	}
	@Transient
	public int getSuccessRefundCount() {
		return successRefundCount;
	}
	@Transient
	public List<OrderRefund> getOrderRefunds() {
		return orderRefunds;
	}
	@Transient
	public boolean isCanRefund() {
		return canRefund;
	}
	public void setSuccessRefundCount(int successRefundCount) {
		this.successRefundCount = successRefundCount;
	}

	public void setOrderRefunds(List<OrderRefund> orderRefunds) {
		this.orderRefunds = orderRefunds;
	}
	

	public void setCanRefund(boolean canRefund) {
		this.canRefund = canRefund;
	}
	@Transient
	public Long getExpressStatus() {
		return expressStatus;
	}

	public void setExpressStatus(Long expressStatus) {
		this.expressStatus = expressStatus;
	}
	@Transient
	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	@Transient
	public Long getItemMode() {
		return itemMode;
	}
	@Transient
	public void setItemMode(Long itemMode) {
		this.itemMode = itemMode;
	}
	/** 查询用创建开始时间 **/
	private String beginTime;

	/** 查询用创建结束时间 **/
	private String endTime;
	

	@Transient
	public String getBeginTime() {
		return beginTime;
	}
	@Transient
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	@Transient
	public String getEndTime() {
		return endTime;
	}
	@Transient
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Transient
	public List<ItemSale> getItemSales() {
		return itemSales;
	}

	public void setItemSales(List<ItemSale> itemSales) {
		this.itemSales = itemSales;
	}
	
	private static Map<String, String> itemModeMap = null;
	static {
		itemModeMap = new HashMap<String, String>();
		itemModeMap.put("0", "实物");
		itemModeMap.put("1", "虚拟物");
	}
	
	public String getItemModeName(){
		return itemModeMap.get(this.getItemMode()+"");
	}
	
	
}
