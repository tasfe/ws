package com.cplatform.mall.back.item.entity;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 商品协议管理表. <br>
 * 类详细说明.
 * <p>
 * Copyright: Copyright (c) 2013-5-29 下午07:46:48
 * <p>
 * Company: 北京宽连十方数字技术有限公司
 * <p>
 * 
 * @author zhaowei@c-platform.com
 * @version 1.0.0
 */
@Entity
@Table(name = "T_HISUN_PRODUCTION_SETTLE")
public class HisunProductionSettle implements java.io.Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = -4187918790218781345L;

	// ******************bus method*************************
	/** 资金种类4-话费map */
	private static Map<String, String> capitaltype4Map = null;
	static {
		capitaltype4Map = new HashMap<String, String>();
		capitaltype4Map.put("0", "不支持");
		capitaltype4Map.put("1", "支持");
	}
	/** 资金种类3-积分map */
	private static Map<String, String> capitaltype3Map = null;
	static {
		capitaltype3Map = new HashMap<String, String>();
		capitaltype3Map.put("0", "不支持");
		capitaltype3Map.put("1", "支持");
	}

	/** 资金种类2-商城币map */
	private static Map<String, String> capitaltype2Map = null;
	static {
		capitaltype2Map = new HashMap<String, String>();
		capitaltype2Map.put("0", "不支持");
		capitaltype2Map.put("1", "支持");
	}

	/** 资金种类-现金map */
	private static Map<String, String> capitaltype1Map = null;
	static {
		capitaltype1Map = new HashMap<String, String>();
		capitaltype1Map.put("0", "不支持");
		capitaltype1Map.put("1", "支持");
	}

	/** 验证标识map */
	private static Map<String, String> verifyflagMap = null;
	static {
		verifyflagMap = new HashMap<String, String>();
		verifyflagMap.put("Y", "验证");
		verifyflagMap.put("N", "不验证");
	}

	/** 验证结算map */
	private static Map<String, String> verifysettleflagMap = null;
	static {
		verifysettleflagMap = new HashMap<String, String>();
		verifysettleflagMap.put("S1", "支付后结算");
		verifysettleflagMap.put("S2", "验证后结算");
	}

	/** 结算分期类型map */
	private static Map<String, String> settleperiodtypeMap = null;
	static {
		settleperiodtypeMap = new HashMap<String, String>();
		settleperiodtypeMap.put("1P2P", "一期支付二期支付");
		settleperiodtypeMap.put("1P2V", "一期支付二期验证");
		settleperiodtypeMap.put("1V2V", "一期验证二期验证");
	}

	/** 状态map */
	private static Map<String, String> statusMap = null;
	static {
		statusMap = new HashMap<String, String>();
		statusMap.put("0", "未审核");
		statusMap.put("1", "审核通过");
	}

	/** 高阳状态map */
	private static Map<String, String> syncGyStatusMap = null;
	static {
		syncGyStatusMap = new HashMap<String, String>();
		syncGyStatusMap.put("0", "未同步");
		syncGyStatusMap.put("1", "已同步");
		syncGyStatusMap.put("2", "待审核");// 对应结算status：3
		syncGyStatusMap.put("3", "生效");// 同步成功 对应结算status：1
		syncGyStatusMap.put("4", "审核驳回");// 对应结算status：4
		syncGyStatusMap.put("5", "删除");// 对应结算status：2
	}

	/** 协议类型map */
	private static Map<String, String> typeMap = null;
	static {
		typeMap = new HashMap<String, String>();
		typeMap.put("0", "商品协议");
		typeMap.put("1", "商品资料");
	}
	@Transient
	public static Map<String, String> getCapitaltype4Map() {
		return capitaltype4Map;
	}

	@Transient
	public String getCapitaltype4Name() {
		return capitaltype4Map.get(this.getCapitaltype4() + "");
	}

	@Transient
	public static Map<String, String> getCapitaltype3Map() {
		return capitaltype3Map;
	}

	@Transient
	public String getCapitaltype3Name() {
		return capitaltype3Map.get(this.getCapitaltype3() + "");
	}

	@Transient
	public static Map<String, String> getCapitaltype2Map() {
		return capitaltype2Map;
	}

	@Transient
	public String getCapitaltype2Name() {
		return capitaltype2Map.get(this.getCapitaltype2() + "");
	}

	@Transient
	public static Map<String, String> getCapitaltype1Map() {
		return capitaltype1Map;
	}

	@Transient
	public String getCapitaltype1Name() {
		return capitaltype1Map.get(this.getCapitaltype1() + "");
	}

	@Transient
	public static Map<String, String> getVerifyflagMap() {
		return verifyflagMap;
	}

	@Transient
	public String getVerifyflagName() {
		return verifyflagMap.get(this.getVerifyflag() + "");
	}

	@Transient
	public static Map<String, String> getVerifysettleflagMap() {
		return verifysettleflagMap;
	}

	@Transient
	public String getVerifysettleflagName() {
		return verifysettleflagMap.get(this.getVerifysettleflag() + "");
	}

	@Transient
	public static Map<String, String> getSettleperiodtypeMap() {
		return settleperiodtypeMap;
	}

	@Transient
	public String getSettleperiodtypeName() {
		return settleperiodtypeMap.get(this.getSettleperiodtype() + "");
	}

	@Transient
	public static Map<String, String> getStatusMap() {
		return statusMap;
	}

	@Transient
	public String getStatusName() {
		return statusMap.get(this.getStatus() + "");
	}

	@Transient
	public static Map<String, String> getSyncGyStatusMap() {
		return syncGyStatusMap;
	}

	@Transient
	public String getSyncGyStatusName() {
		return syncGyStatusMap.get(this.getSyncGyStatus() + "");
	}

	@Transient
	public String getSyncGyStatus1Name() {
		return syncGyStatusMap.get(this.getSyncGyStatus1() + "");
	}

	@Transient
	public String getSyncGyStatus2Name() {
		return syncGyStatusMap.get(this.getSyncGyStatus2() + "");
	}

	@Transient
	public String getSyncGyStatus3Name() {
		return syncGyStatusMap.get(this.getSyncGyStatus3() + "");
	}
	@Transient
	public String getSyncGyStatus4Name() {
		return syncGyStatusMap.get(this.getSyncGyStatus4() + "");
	}

	@Transient
	public static Map<String, String> getTypeMap() {
		return typeMap;
	}

	@Transient
	public String getTypeName() {
		return typeMap.get(this.getType() + "");
	}
	// 资金种类4-话费
		/**
		 * 支持
		 */
		public static final Long CAPITALTYPE4_1 = 1L;

		/**
		 * no
		 */
		public static final Long CAPITALTYPE4_0 = 0L;
	// 资金种类3-积分
	/**
	 * 支持
	 */
	public static final Long CAPITALTYPE3_1 = 1L;

	/**
	 * no
	 */
	public static final Long CAPITALTYPE3_0 = 0L;

	// 资金种类2-商城币
	/**
	 * 支持
	 */
	public static final Long CAPITALTYPE2_1 = 1L;

	/**
	 * no
	 */
	public static final Long CAPITALTYPE2_0 = 0L;

	// 资金种类-现金
	/**
	 * 支持
	 */
	public static final Long CAPITALTYPE1_1 = 1L;

	/**
	 * no
	 */
	public static final Long CAPITALTYPE1_0 = 0L;

	// 验证标识
	/**
	 * 验证
	 */
	public static final String VERIFYFLAG_Y = "Y";

	/**
	 * 不验证
	 */
	public static final String VERIFYFLAG_N = "N";

	// 验证结算
	/**
	 * 支付后结算
	 */
	public static final String VERIFYSETTLEFLAG_S1 = "S1";

	/**
	 * 验证后结算
	 */
	public static final String VERIFYSETTLEFLAG_S2 = "S2";

	// 结算分期类型
	/**
	 * 一期支付二期支付
	 */
	public static final String SETTLEPERIODTYPE_1P2P = "1P2P";

	/**
	 * 一期支付二期验证
	 */
	public static final String SETTLEPERIODTYPE_1P2V = "1P2V";

	/**
	 * 一期验证二期验证
	 */
	public static final String SETTLEPERIODTYPE_1V2V = "1V2V";

	// 状态
	/**
	 * 状态 － 未审核
	 */
	public static final Long STATUS_0 = 0L;

	/**
	 * 状态 － 审核通过
	 */
	public static final Long STATUS_1 = 1L;

	// 高阳状态
	/**
	 * 状态 － 未同步
	 */
	public static final Long SYNC_GY_STATUS_0 = 0L;

	/**
	 * 状态 － 同步成功
	 */
	public static final Long SYNC_GY_STATUS_1 = 1L;

	/**
	 * 状态 －同步失败
	 */
	public static final Long SYNC_GY_STATUS_2 = 2L;

	/**
	 * 状态 － 审核通过
	 */
	public static final Long SYNC_GY_STATUS_3 = 3L;

	/**
	 * 状态 － 审核驳回
	 */
	public static final Long SYNC_GY_STATUS_4 = 4L;

	// 协议类型
	/**
	 * 商品协议
	 */
	public static final Long TYPE_0 = 0L;

	/**
	 * 商品资料
	 */
	public static final Long TYPE_1 = 1L;

	public HisunProductionSettle() {

	}

	public HisunProductionSettle(ItemSale itemSale) {
		if (null != itemSale.getId()) {
			this.setContractid(itemSale.getId().toString());
			this.setProductionid(itemSale.getId().toString());
		}
		this.setProductionname(itemSale.getName());
		if (null != itemSale.getFeeType()) {
			this.setProductiontype(itemSale.getFeeType().toString());
		}
		if (null != itemSale.getSendCodeMode()) {
			this.setVerifyflag(itemSale.getSendCodeMode() == 0L ? "N" : "Y");
			if ("Y".equals(this.getVerifyflag())) {
				this.setVerifysettleflag("S1");
			}
		} else {
			this.setVerifyflag("N");
		}
		this.setProductionefftime(itemSale.getSaleStartTime());
		this.setProductionexptime(itemSale.getSaleStopTime());
		if (StringUtils.isNotBlank(itemSale.getVerifyStopTime())) {
			this.setVerifyexpdate(itemSale.getVerifyStopTime().substring(0, 8));
		}
		if (null != itemSale.getShopPrice()) {
			this.setPrice(itemSale.getShopPrice());
		}
		if (null != itemSale.getSettlePrice()) {
			this.setSettlementprice(itemSale.getSettlePrice());
		}
		if (3L == itemSale.getStatus()) {
			this.setStatus(this.STATUS_1);
		}
		this.setCapitaltype1(this.CAPITALTYPE1_1);
		this.setCapitaltype2(this.CAPITALTYPE2_1);
		this.setCapitaltype3(this.CAPITALTYPE3_1);
		this.setCapitaltype4(this.CAPITALTYPE4_1);
		this.setSyncGyStatus1(this.SYNC_GY_STATUS_0);
		this.setSyncGyStatus2(this.SYNC_GY_STATUS_0);
		this.setSyncGyStatus3(this.SYNC_GY_STATUS_0);
		this.setSyncGyStatus4(this.SYNC_GY_STATUS_0);
		this.setCreateTime(itemSale.getCreateTime());
		this.setCreateUser(itemSale.getCreateUserId());
		this.setType(this.TYPE_1);
	}

	/** 序列 **/
	private Long id;

	/** 结算商户编号 商户在统一清算平台中的编号 **/
	private String merchid;

	/** 商户编号 在业务平台中的商户编号 **/
	private String storeId;

	/** 业务编号 **/
	private String serviceid;

	/** 合同编号(商盟必输) **/
	private String contractid;

	/** 合同生效日期(YYYYMMDD，商盟必输) **/
	private String contracteffdate;

	/** 合同失效日期 **/
	private String contractexpdate;

	/** 商品编号 **/
	private String productionid;

	/** 商品名称 **/
	private String productionname;

	/** 商品类别 **/
	private String productiontype;
	
	/** 资金种类3-积分 1-支持 0-no **/
	private Long capitaltype4;

	/** 资金种类3-积分 1-支持 0-no **/
	private Long capitaltype3;

	/** 资金种类2-商城币 1-支持 0-no **/
	private Long capitaltype2;

	/** 资金种类-现金 1-支持 0-no **/
	private Long capitaltype1;

	/** 验证标识 Y-验证；N-不验证（默认） **/
	private String verifyflag;

	/**
	 * 验证结算 对于需要验证的商品，一种是支付验证都成功后才能做清结算，另一种是支付成功，但是没有验证的就要做清结算，通过此字段来区分，
	 * 这个字段当veriflag=Y时必输。 S1-支付后结算 S2-验证后结算
	 **/
	private String verifysettleflag;

	/** 产品上线时间YYYYMMDDHHMISS **/
	private String productionefftime;

	/** 产品下线时间YYYYMMDDHHMISS **/
	private String productionexptime;

	/** 验证截止日期(YYYYMMDD，商盟必输) **/
	private String verifyexpdate;

	/** 地市编码(商盟必输) **/
	private String cityid;

	/** 地市名称(商盟必输) **/
	private String cityname;

	/** 地市分成比例(商盟必输) **/
	private String cityprofitrate;

	/** 代理商编码(商盟必输) **/
	private String agentid;

	/** 代理商名称(商盟必输) **/
	private String agentname;

	/** 代理商分成比例(商盟必输) **/
	private String agentprofitrate;

	/** 商品单价 以分为单位 **/
	private Double price;

	/** 结算单价 以分为单位 **/
	private Double settlementprice;

	/** 结算分期数 商盟必输 **/
	private Long settleperiod;

	/** 结算分期类型 1P2P：一期支付二期支付 1P2V：一期支付二期验证 1V2V：一期验证二期验证 **/
	private String settleperiodtype;

	/** 结算时间 YYYYMMDD **/
	private String settledate1;

	/** 结算比例 **/
	private String settlerate1;

	/** 结算时间2 YYYYMMDD **/
	private String settledate2;

	/** 结算比例2 **/
	private String settlerate2;

	/** 结算时间3 YYYYMMDD **/
	private String settledate3;

	/** 结算比例3 **/
	private String settlerate3;

	/** 状态0-未审核 1-审核通过 **/
	private Long status;

	/** 高阳状态:现金（capitaltype1） 0-未同步 1-同步成功 2-同步失败 3-审核通过 4-审核驳回 **/
	private Long syncGyStatus1;

	/** 高阳状态:商城币 （capitaltype2） 0-未同步 1-同步成功 2-同步失败 3-审核通过 4-审核驳回 **/
	private Long syncGyStatus2;

	/** 高阳状态:积分（capitaltype3） 0-未同步 1-同步成功 2-同步失败 3-审核通过 4-审核驳回 **/
	private Long syncGyStatus3;
	
	/** 高阳状态:积分（capitaltype4） 0-未同步 1-同步成功 2-同步失败 3-审核通过 4-审核驳回 **/
	private Long syncGyStatus4;

	/** 创建时间 **/
	private String createTime;

	/** 创建人 **/
	private Long createUser;;

	/** 协议类型 0-商品协议 1-商品资料 **/
	private Long type;

	/** 协议文件路径 **/
	private String filePath;

	public void setId(Long id) {
		this.id = id;
	}

	@SequenceGenerator(name = "seq_item", sequenceName = "SEQ_SYS_ITEM_ID")
	@Id
	@GeneratedValue(generator = "seq_item")
	@JsonProperty
	public Long getId() {
		return id;
	}

	public void setMerchid(String merchid) {
		this.merchid = merchid;
	}

	@Column(name = "MERCHID")
	public String getMerchid() {
		return merchid;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	@Column(name = "STORE_ID")
	public String getStoreId() {
		return storeId;
	}

	public void setServiceid(String serviceid) {
		this.serviceid = serviceid;
	}

	@Column(name = "SERVICEID")
	public String getServiceid() {
		return serviceid;
	}

	public void setContractid(String contractid) {
		this.contractid = contractid;
	}

	@Column(name = "CONTRACTID")
	public String getContractid() {
		return contractid;
	}

	public void setContracteffdate(String contracteffdate) {
		this.contracteffdate = contracteffdate;
	}

	@Column(name = "CONTRACTEFFDATE")
	public String getContracteffdate() {
		return contracteffdate;
	}

	public void setContractexpdate(String contractexpdate) {
		this.contractexpdate = contractexpdate;
	}

	@Column(name = "CONTRACTEXPDATE")
	public String getContractexpdate() {
		return contractexpdate;
	}

	public void setProductionid(String productionid) {
		this.productionid = productionid;
	}

	@Column(name = "PRODUCTIONID")
	public String getProductionid() {
		return productionid;
	}

	public void setProductionname(String productionname) {
		this.productionname = productionname;
	}

	@Column(name = "PRODUCTIONNAME")
	public String getProductionname() {
		return productionname;
	}

	public void setProductiontype(String productiontype) {
		this.productiontype = productiontype;
	}

	@Column(name = "PRODUCTIONTYPE")
	public String getProductiontype() {
		return productiontype;
	}

	public void setCapitaltype3(Long capitaltype3) {
		this.capitaltype3 = capitaltype3;
	}

	@Column(name = "CAPITALTYPE3")
	public Long getCapitaltype3() {
		return capitaltype3;
	}
	
	public void setCapitaltype4(Long capitaltype4) {
		this.capitaltype4 = capitaltype4;
	}
	@Column(name = "CAPITALTYPE4")
	public Long getCapitaltype4() {
		return capitaltype4;
	}
	

	public void setCapitaltype2(Long capitaltype2) {
		this.capitaltype2 = capitaltype2;
	}

	@Column(name = "CAPITALTYPE2")
	public Long getCapitaltype2() {
		return capitaltype2;
	}

	public void setCapitaltype1(Long capitaltype1) {
		this.capitaltype1 = capitaltype1;
	}

	@Column(name = "CAPITALTYPE1")
	public Long getCapitaltype1() {
		return capitaltype1;
	}

	public void setVerifyflag(String verifyflag) {
		this.verifyflag = verifyflag;
	}

	@Column(name = "VERIFYFLAG")
	public String getVerifyflag() {
		return verifyflag;
	}

	public void setVerifysettleflag(String verifysettleflag) {
		this.verifysettleflag = verifysettleflag;
	}

	@Column(name = "VERIFYSETTLEFLAG")
	public String getVerifysettleflag() {
		return verifysettleflag;
	}

	public void setProductionefftime(String productionefftime) {
		this.productionefftime = productionefftime;
	}

	@Column(name = "PRODUCTIONEFFTIME")
	public String getProductionefftime() {
		return productionefftime;
	}

	public void setProductionexptime(String productionexptime) {
		this.productionexptime = productionexptime;
	}

	@Column(name = "PRODUCTIONEXPTIME")
	public String getProductionexptime() {
		return productionexptime;
	}

	public void setVerifyexpdate(String verifyexpdate) {
		this.verifyexpdate = verifyexpdate;
	}

	@Column(name = "VERIFYEXPDATE")
	public String getVerifyexpdate() {
		return verifyexpdate;
	}

	public void setCityid(String cityid) {
		this.cityid = cityid;
	}

	@Column(name = "CITYID")
	public String getCityid() {
		return cityid;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	@Column(name = "CITYNAME")
	public String getCityname() {
		return cityname;
	}

	public void setCityprofitrate(String cityprofitrate) {
		this.cityprofitrate = cityprofitrate;
	}

	@Column(name = "CITYPROFITRATE")
	public String getCityprofitrate() {
		return cityprofitrate;
	}

	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}

	@Column(name = "AGENTID")
	public String getAgentid() {
		return agentid;
	}

	public void setAgentname(String agentname) {
		this.agentname = agentname;
	}

	@Column(name = "AGENTNAME")
	public String getAgentname() {
		return agentname;
	}

	public void setAgentprofitrate(String agentprofitrate) {
		this.agentprofitrate = agentprofitrate;
	}

	@Column(name = "AGENTPROFITRATE")
	public String getAgentprofitrate() {
		return agentprofitrate;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Column(name = "PRICE")
	public Double getPrice() {
		return price;
	}

	public void setSettlementprice(Double settlementprice) {
		this.settlementprice = settlementprice;
	}

	@Column(name = "SETTLEMENTPRICE")
	public Double getSettlementprice() {
		return settlementprice;
	}

	public void setSettleperiod(Long settleperiod) {
		this.settleperiod = settleperiod;
	}

	@Column(name = "SETTLEPERIOD")
	public Long getSettleperiod() {
		return settleperiod;
	}

	public void setSettleperiodtype(String settleperiodtype) {
		this.settleperiodtype = settleperiodtype;
	}

	@Column(name = "SETTLEPERIODTYPE")
	public String getSettleperiodtype() {
		return settleperiodtype;
	}

	public void setSettledate1(String settledate1) {
		this.settledate1 = settledate1;
	}

	@Column(name = "SETTLEDATE1")
	public String getSettledate1() {
		return settledate1;
	}

	public void setSettlerate1(String settlerate1) {
		this.settlerate1 = settlerate1;
	}

	@Column(name = "SETTLERATE1")
	public String getSettlerate1() {
		return settlerate1;
	}

	public void setSettledate2(String settledate2) {
		this.settledate2 = settledate2;
	}

	@Column(name = "SETTLEDATE2")
	public String getSettledate2() {
		return settledate2;
	}

	public void setSettlerate2(String settlerate2) {
		this.settlerate2 = settlerate2;
	}

	@Column(name = "SETTLERATE2")
	public String getSettlerate2() {
		return settlerate2;
	}

	public void setSettledate3(String settledate3) {
		this.settledate3 = settledate3;
	}

	@Column(name = "SETTLEDATE3")
	public String getSettledate3() {
		return settledate3;
	}

	public void setSettlerate3(String settlerate3) {
		this.settlerate3 = settlerate3;
	}

	@Column(name = "SETTLERATE3")
	public String getSettlerate3() {
		return settlerate3;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Column(name = "STATUS")
	public Long getStatus() {
		return status;
	}

	public void setSyncGyStatus1(Long syncGyStatus1) {
		this.syncGyStatus1 = syncGyStatus1;
	}

	@Column(name = "SYNC_GY_STATUS1")
	public Long getSyncGyStatus1() {
		return syncGyStatus1;
	}

	public void setSyncGyStatus2(Long syncGyStatus2) {
		this.syncGyStatus2 = syncGyStatus2;
	}

	@Column(name = "SYNC_GY_STATUS2")
	public Long getSyncGyStatus2() {
		return syncGyStatus2;
	}

	public void setSyncGyStatus3(Long syncGyStatus3) {
		this.syncGyStatus3 = syncGyStatus3;
	}

	@Column(name = "SYNC_GY_STATUS3")
	public Long getSyncGyStatus3() {
		return syncGyStatus3;
	}
	
	@Column(name = "SYNC_GY_STATUS4")
	public Long getSyncGyStatus4() {
		return syncGyStatus4;
	}

	public void setSyncGyStatus4(Long syncGyStatus4) {
		this.syncGyStatus4 = syncGyStatus4;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Column(name = "CREATE_TIME")
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}

	@Column(name = "CREATE_USER")
	public Long getCreateUser() {
		return createUser;
	}

	public void setType(Long type) {
		this.type = type;
	}

	@Column(name = "TYPE")
	public Long getType() {
		return type;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Column(name = "FILE_PATH")
	public String getFilePath() {
		return filePath;
	}

	// **************bus method***********
	private String productiontypeName;

	private Long capitalType;

	/** 高阳状态 0-未同步 1-同步成功 2-同步失败 3-审核通过 4-审核驳回 **/
	private Long syncGyStatus;

	// 是否关联商品标识0-未关联 1-关联
	private Long isLink;

	private String storeName;

	public void setProductiontypeName(String productiontypeName) {
		this.productiontypeName = productiontypeName;
	}

	@Transient
	public String getProductiontypeName() {
		return productiontypeName;
	}

	@Transient
	public Long getCapitalType() {
		return capitalType;
	}

	public void setCapitalType(Long capitalType) {
		this.capitalType = capitalType;
	}

	public void setSyncGyStatus(Long syncGyStatus) {
		this.syncGyStatus = syncGyStatus;
	}

	@Transient
	public Long getSyncGyStatus() {
		return this.getSyncGyStatus1();
		// if (1L == capitaltype1 && 0 != syncGyStatus1) {
		// }
		// if (1L == this.getCapitaltype2() && 0 != this.getSyncGyStatus2()) {
		// return this.getSyncGyStatus2();
		// }
		// if (1L == this.getCapitaltype3() && 0 != this.getSyncGyStatus3()) {
		// return this.getSyncGyStatus3();
		// }
		// return 0L;
	}

	public void setIsLink(Long isLink) {
		this.isLink = isLink;
	}

	@Transient
	public Long getIsLink() {
		return isLink;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	@Transient
	public String getStoreName() {
		return storeName;
	}
}