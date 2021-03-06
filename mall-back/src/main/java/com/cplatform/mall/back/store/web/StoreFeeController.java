package com.cplatform.mall.back.store.web;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cplatform.dbhelp.page.Page;
import com.cplatform.mall.back.model.SessionUser;
import com.cplatform.mall.back.model.SyncGYResponseBean;
import com.cplatform.mall.back.store.entity.Store;
import com.cplatform.mall.back.store.entity.StoreFee;
import com.cplatform.mall.back.store.service.StoreFeeService;
import com.cplatform.mall.back.store.service.StoreService;
import com.cplatform.mall.back.store.web.validator.StoreFeeValidator;
import com.cplatform.mall.back.sys.entity.SysFee;
import com.cplatform.mall.back.utils.JsonRespWrapper;
import com.cplatform.mall.back.utils.LogUtils;
import com.cplatform.mall.back.utils.MallUtils;
import com.cplatform.mall.back.utils.SyncInterface;
import com.cplatform.settle_interface.bean.MerchantFeeInfo;
import com.cplatform.util2.TimeStamp;

/**
 * @Title 费率管理
 * @Description
 * @Copyright: Copyright (c) 2013-7-9
 * @Company: 北京宽连十方数字技术有限公司
 * @Author liudong
 * @Version: 1.0
 */
@Controller
@RequestMapping(value = "/store/storeFee")
public class StoreFeeController {

	private static final Logger log = Logger.getLogger(StoreFeeController.class);

	@Autowired
	private LogUtils logUtils;

	@Autowired
	StoreFeeService storeFeeService;

	@Autowired
	StoreService storeService;

	@Autowired
	StoreFeeValidator storeFeeValidator;

	@Autowired
	private SyncInterface syncInterface;

	/**
	 * 结算信息列表
	 * 
	 * @param store
	 * @param page
	 * @param model
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping(value = "/auditlist")
	public String auditlist(StoreFee storeFee, @RequestParam(value = "page", required = false, defaultValue = "1") int page, Model model)
	        throws SQLException {
		Page<StoreFee> pageData = storeFeeService.listStoreFee(storeFee, page, Page.DEFAULT_PAGESIZE);
		model.addAttribute("pageData", pageData);
		model.addAttribute("syncGyFlagMap", StoreFee.syncGyFlagMap);
		model.addAttribute("statusMap", StoreFee.statusMap);
		return "/store/fee/fee-audit-list";
	}

	/**
	 * 费率列表页面
	 * 
	 * @param storeId
	 * @param model
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping(value = "/feeList/{storeId}")
	public Object feeList(StoreFee storeFee, @PathVariable Long storeId,
	        @RequestParam(value = "page", required = false, defaultValue = "1") int page, Model model) throws SQLException {
		storeFee.setStoreId(storeId);
		Store store = this.storeService.findStoreById(storeId);
		Page<StoreFee> storeFeeList = this.storeFeeService.listStoreFee(storeFee, page, Page.DEFAULT_PAGESIZE);
		model.addAttribute("store", store);
		model.addAttribute("storeFeeList", storeFeeList);
		// model.addAttribute("syncGyFlagMsg", Store.syncGyFlagMsg);
		// /
		/**
		 * request 对象多余，应该用下面的
		 * 
		 * @author liujun
		 */
		// /model.addAttribute("storeFeeList", storeFeeList);

		model.addAttribute("store", store);
		return "/store/fee/fee-list";

	}

	/**
	 * 跳转到添加渠道商结算信息页面
	 * 
	 * @param storeId
	 * @param session
	 * @param model
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping(value = "/feeAdd", method = RequestMethod.GET)
	public Object feeAdd(@RequestParam(value = "storeId") Long storeId, HttpSession session, Model model) throws SQLException {

		Store store = this.storeService.findStoreById(storeId);

		model.addAttribute("store", store);
		StoreFee vo = new StoreFee();
		vo.setCapitalType1(1L);
		vo.setCapitalType2(1L);
		vo.setCapitalType3(1L);
		//vo.setCapitalType4(1L);
		vo.setStoreId(store.getId());
		// 设置默认值开始
		//vo.setClearType("P");
		vo.setTradeMode(0L);
		// vo.setProductionTypeName("商品");
		vo.setEffortdate(TimeStamp.getTime(TimeStamp.YYYYMMDD));
		vo.setFeedrtflag("D");
		vo.setFeeperiodunit("2");
		vo.setFeemothod(1L);
		vo.setFeeType("01");
		vo.setFcName("1");
		vo.setFeemode(1L);
		vo.setFeelevelflag("1");
		vo.setBeganamount("0");
		vo.setMinfeeamount("0");
		vo.setMaxfeeamount("999999999.99");
		// 设置默认值结束
		List<SysFee> feeList = storeService.getSysFeeList(storeId);
		model.addAttribute("feeList", feeList);
		model.addAttribute("storeFee", vo);
		return "/store/fee/fee-add";

	}

	/**
	 * 添加费率
	 * 
	 * @param po
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@RequestMapping(value = "/feeAdd", method = RequestMethod.POST)
	@ResponseBody
	public Object feeSave(@ModelAttribute("storeFee") StoreFee po, HttpServletRequest request, Model model, BindingResult result) throws Exception {
		if (!"flag".equals(po.getProductionType())) {
			storeFeeValidator.validate(po, result);
		} else {
			/***
			 * 该代码屏蔽，在新增修改费率的时候，不可以修改t_sys_fee中的数据
			 * 
			 * @author liujun 2013-08-21 10：40
			 **/
			// List<Object> lists = storeService.saveSysFee(po);
			// String msg = (String)lists.get(0);
			// if(!"同步成功".equals(msg)){
			// return JsonRespWrapper.successAlert("费率同步时发生异常");
			// }
			// String productionType = (Long)lists.get(1) + "";
			// po.setProductionType(productionType);
		}
		if (result.hasErrors()) {
			return JsonRespWrapper.error(result.getFieldErrors());
		}
		if(null != po.getCapitalType4() && 1L == po.getCapitalType4()){
			po.setPhoneEffortdate(TimeStamp.getTime(TimeStamp.YYYYMMDD));
		}
		po.setCreateUser(SessionUser.getSessionUser().getId());
		po.setCreateTime(TimeStamp.getTime(TimeStamp.YYYYMMDDhhmmss));
		po.setStatus(0L);
		po.setSyncGyFlag1(0L);
		po.setSyncGyFlag2(0L);
		po.setSyncGyFlag3(0L);
		po.setSyncGyFlag4(0L);
		po.setEffortdate(po.getEffortdate().replaceAll("-", ""));
		po.setExpirydate(po.getExpirydate().replaceAll("-", ""));
		if (po.getCapitalType1() == null) {
			po.setCapitalType1(0L);
		}
		if (po.getCapitalType2() == null) {
			po.setCapitalType2(0L);
		}
		if (po.getCapitalType3() == null) {
			po.setCapitalType3(0L);
		}
		if (po.getCapitalType4() == null) {
			po.setCapitalType4(0L);
		}
		// if ("P".equals(po.getClearType())) {
		// po.setProductionType("01");
		// }
		// po.setTradeMode(0L);
		po.setFeebasicflag(Long.valueOf(po.getFeelvlbasicflag()));
		if (po.getFixfeeamount5().equals("9999999.99")) {

			po.setFixfeeamount5("-1000");
		}

		po = MallUtils.dealMultiStoreFeeName(po);

		this.storeService.addOrUpdateStoreFee(po);
		logUtils.logAdd("商户费率管理", "商户费率添加, 商户费率编号：" + po.getId());
		return JsonRespWrapper.success("操作成功", "/store/storeFee/feeList/" + po.getStoreId());
	}

	@RequestMapping(value = "/feeEdit", method = RequestMethod.GET)
	public String feeEdit(@RequestParam(value = "id") Long id, HttpSession session, Model model) throws SQLException {
		StoreFee vo = this.storeService.findStoreFeeById(id);
		vo = MallUtils.dealDiviStoreFeeName(vo);
		model.addAttribute("storeFee", vo);
		// if ("01".equals(vo.getProductionType())) {
		// vo.setProductionTypeName("商品");
		// } else {
		SysFee fee = storeService.findSysFeeByid(Long.parseLong(vo.getProductionType()));

		model.addAttribute("productionTypeName", fee.getName());
		// }

		List<SysFee> feeList = storeService.getSysFeeList(vo.getStoreId());
		model.addAttribute("feeList", feeList);

		return "/store/fee/fee-add";

	}

	@RequestMapping(value = "/feeEdit", method = RequestMethod.POST)
	@ResponseBody
	public Object feeUpdate(@ModelAttribute("storeFee") StoreFee po, HttpSession session, Model model, BindingResult result) throws Exception {
		// po.setEffortdate(po.getEffortdate().replaceAll("-", ""));
		// po.setExpirydate(po.getExpirydate().replaceAll("-", ""));
		if (!"flag".equals(po.getProductionType())) {
			storeFeeValidator.validate(po, result);
		} else {
			/***
			 * 该代码屏蔽，在新增修改费率的时候，不可以修改t_sys_fee中的数据
			 * 
			 * @author liujun 2013-08-21 10：40
			 **/
			// List<Object> lists = storeService.saveSysFee(po);
			// String msg = (String)lists.get(0);
			// if(!"同步成功".equals(msg)){
			// return JsonRespWrapper.successAlert("费率同步时发生异常");
			// }
			// String productionType = (Long)lists.get(1) + "";
			// po.setProductionType(productionType);
		}
		if (result.hasErrors()) {
			return JsonRespWrapper.error(result.getFieldErrors());
		}
		if(null != po.getCapitalType4() && 1L == po.getCapitalType4()){
			po.setPhoneEffortdate(TimeStamp.getTime(TimeStamp.YYYYMMDD));
		}
		// if ("P".equals(po.getClearType())) {
		// po.setProductionType("01");
		// }
		if (po.getCapitalType1() == null) {
			po.setCapitalType1(0L);
		}
		if (po.getCapitalType2() == null) {
			po.setCapitalType2(0L);
		}
		if (po.getCapitalType3() == null) {
			po.setCapitalType3(0L);
		}
		if (po.getCapitalType4() == null) {
			po.setCapitalType4(0L);
		}
		StoreFee oldpo = this.storeService.findStoreFeeById(po.getId());
		po.setCreateUser(oldpo.getCreateUser());
		po.setCreateTime(oldpo.getCreateTime());
		po.setSyncGyFlag1(oldpo.getSyncGyFlag1());
		po.setSyncGyFlag2(oldpo.getSyncGyFlag2());
		po.setSyncGyFlag3(oldpo.getSyncGyFlag3());
		po.setSyncGyFlag4(oldpo.getSyncGyFlag4());
		po.setMerchid(oldpo.getMerchid());
		po.setSyncTime(oldpo.getSyncTime());
		po.setStatus(0L);
		// po.setTradeMode(oldpo.getTradeMode());
		po.setFeebasicflag(oldpo.getFeebasicflag());
		po.setFeebasicflag(Long.valueOf(po.getFeelvlbasicflag()));

		po = MallUtils.dealMultiStoreFeeName(po);
		this.storeService.addOrUpdateStoreFee(po);
		logUtils.logModify("商户费率管理", "商户费率修改, 商户费率编号：" + po.getId());
		return JsonRespWrapper.success("操作成功", "/store/storeFee/feeList/" + po.getStoreId());

	}

	@RequestMapping(value = "/feeView/{id}")
	public String feeView(@PathVariable(value = "id") Long id, Model model) {
		StoreFee vo = this.storeService.findStoreFeeById(id);
		vo = MallUtils.dealDiviStoreFeeName(vo);
		model.addAttribute("storeFee", vo);
		// if ("01".equals(vo.getProductionType())) {
		// model.addAttribute("productionTypeName", "商品");
		// } else {
		SysFee fee = storeService.findSysFeeByid(Long.parseLong(vo.getProductionType()));
		model.addAttribute("productionTypeName", fee.getName());
		// }

		Store store = this.storeService.findStoreById(vo.getStoreId());
		model.addAttribute("store", store);
		model.addAttribute("syncGyFlagMsg", Store.syncGyFlagMsg);
		return "/store/fee/fee-view";

	}

	@RequestMapping(value = "/feeDel/{id}")
	@ResponseBody
	public Object feeDel(@PathVariable Long id, Model model) {
		StoreFee po = storeService.findStoreFeeById(id);
		if (po.getStatus() == 3L) {
			return JsonRespWrapper.successAlert("当前费率信息已经审核通过，不能被删除");
		}

		if (po.getSyncGyFlag1() != 0L || po.getSyncGyFlag2() != 0L || po.getSyncGyFlag3() != 0L || po.getSyncGyFlag4() != 0L) {
			return JsonRespWrapper.successAlert("当前费率信息已经同步过清结算系统，不能被删除");
		}
		storeService.delStoreFeeById(id);
		logUtils.logDelete("商户费率管理", "商户费率删除, 商户费率编号：" + po.getId());
		return JsonRespWrapper.success("操作成功", "/store/storeFee/feeList/" + po.getStoreId());
	}

	@RequestMapping(value = "/auditPage/{id}")
	public String toAuditPage(@PathVariable Long id, Model model) {
		model.addAttribute("id", id);
		return "/store/fee/fee_audit";
	}

	@RequestMapping(value = "/feeAudit")
	@ResponseBody
	public Object settleAudit(@RequestParam(value = "id") Long id, @RequestParam(value = "status") Long status, Model model,
	        HttpServletRequest request) {
		StoreFee po = this.storeService.findStoreFeeById(id);
		po.setStatus(status);
		this.storeService.addOrUpdateStoreFee(po);
		logUtils.logAudit("商户费率管理", "商户费率审核, 商户费率编号：" + po.getId());
		/*
		// 审核通过后同步清算接口
		String msg = "";
		if (po.getStatus() == 3) {
			Store store = this.storeService.findStoreById(po.getStoreId());
			if (store != null) {
				if (store.getMerchid() == null) {
					msg = "该商户基本信息未同步成功，暂不能同步。";
				} else {
					// if (store.getSyncGyFlag() != 3L) {
					// msg += vo.getId() + "该商户基本信息清结算平台未审核通过，暂不能同步。";
					// continue;
					// }
					if (po.getMerchid() == null) {
						po.setMerchid(store.getMerchid());
					}
					SyncGYResponseBean bean = null;
					if (po.getCapitalType1() == 1L) {// 现金
						po.setCapitalType(1L);
						if (po.getSyncGyFlag1() == 0L) {
							bean = this.syncInterface.syncAddFee(po);
						} else {
							bean = this.syncInterface.syncUpdateFee(po);
						}
						if (bean != null) {
							if (bean.isSyncSuccess()) {
								po.setSyncGyFlag1(1L);
								po.setMerchid(bean.getMerchid());
								po.setSyncTime(TimeStamp.getTime(TimeStamp.YYYYMMDDhhmmss));
								storeService.addOrUpdateStoreFee(po);
							}
							msg += "现金类型:" + bean.getMsg()+"。";
						} else {
							msg += "现金类型同步失败。";
						}
					}
					if (po.getCapitalType2() == 1L) {// 商城币
						po.setCapitalType(2L);
						if (po.getSyncGyFlag2() == 0L) {
							bean = this.syncInterface.syncAddFee(po);
						} else {
							bean = this.syncInterface.syncUpdateFee(po);
						}
						if (bean != null) {
							if (bean.isSyncSuccess()) {
								po.setSyncGyFlag2(1L);
								po.setMerchid(bean.getMerchid());
								po.setSyncTime(TimeStamp.getTime(TimeStamp.YYYYMMDDhhmmss));
								this.storeService.addOrUpdateStoreFee(po);
							}
							msg += "商城币类型:" + bean.getMsg()+"。";
						} else {
							msg += "商城币类型同步失败。";
						}
					}

					if (po.getCapitalType3() == 1L) {// 积分
						po.setCapitalType(3L);
						if (po.getSyncGyFlag3() == 0L) {
							bean = this.syncInterface.syncAddFee(po);
						} else {
							bean = this.syncInterface.syncUpdateFee(po);
						}
						if (bean != null) {
							if (bean.isSyncSuccess()) {
								po.setSyncGyFlag3(1L);
								po.setMerchid(bean.getMerchid());
								po.setSyncTime(TimeStamp.getTime(TimeStamp.YYYYMMDDhhmmss));
								this.storeService.addOrUpdateStoreFee(po);
							}
							msg += "积分类型:" + bean.getMsg()+"。";
						} else {
							msg += "积分类型同步失败。";
						}
					}
					
					if (po.getCapitalType4() == 1L) {// 话费
						po.setCapitalType(4L);
						if (po.getSyncGyFlag4() == 0L) {
							bean = this.syncInterface.syncAddFee(po);
						} else {
							bean = this.syncInterface.syncUpdateFee(po);
						}
						if (bean != null) {
							if (bean.isSyncSuccess()) {
								po.setSyncGyFlag4(1L);
								po.setMerchid(bean.getMerchid());
								po.setSyncTime(TimeStamp.getTime(TimeStamp.YYYYMMDDhhmmss));
								this.storeService.addOrUpdateStoreFee(po);
							}
							msg += "话费类型:" + bean.getMsg()+"。";
						} else {
							msg += "话费类型同步失败。";
						}
					}
					
				}
				log.info(po.getId() + msg);
			}
		}
		*/
		// 返回到来源页面
		String backUrl = request.getParameter("backUrl");
		return JsonRespWrapper.success("审核成功，请同步相应费率", backUrl);
	}

	@RequestMapping(value = "/sync")
	@ResponseBody
	public Object sync(@RequestParam(value = "ids") String ids, Model model, HttpServletRequest request) {
		String msg = "操作完成。";
		String message = "";
		SyncGYResponseBean bean = null;
		String[] idarray = ids.split(",");
		if (idarray != null) {
			// 获取当前的登录用户
			SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(SessionUser.SESSION_USER_KEY);
			for (String id : idarray) {
				if ("".equals(id)) {
					continue;
				}
				StoreFee vo = this.storeService.findStoreFeeById(Long.valueOf(id));
				if (vo != null) {
					Store store = this.storeService.findStoreById(vo.getStoreId());
					if (store == null) {
						continue;
					}
					if (store.getMerchid() == null) {
						message = vo.getId() + "该商户基本信息未同步成功，暂不能同步。";
						msg += message;
						continue;
					}
					// if (store.getSyncGyFlag() != 3L) {
					// msg += vo.getId() + "该商户基本信息清结算平台未审核通过，暂不能同步。";
					// continue;
					// }
					if (vo.getStatus() != 3) {
						message = vo.getId() + "状态：未审核通过，暂不能同步。";
						msg += message;
						continue;
					}
					if (vo.getMerchid() == null) {
						vo.setMerchid(store.getMerchid());
					}
					if (vo.getCapitalType1() == 1L) {// 现金
						vo.setCapitalType(1L);
						if (vo.getSyncGyFlag1() == 0L) {
							bean = this.syncInterface.syncAddFee(vo);
						} else {
							bean = this.syncInterface.syncUpdateFee(vo);
						}
						if (bean != null) {
							if (bean.isSyncSuccess()) {
								vo.setSyncGyFlag1(1L);
								vo.setMerchid(bean.getMerchid());
								vo.setSyncTime(TimeStamp.getTime(TimeStamp.YYYYMMDDhhmmss));
								storeService.addOrUpdateStoreFee(vo);
							}
							message = vo.getId() + "现金类型:" + bean.getMsg();
							msg += message;
						} else {
							message = vo.getId() + "现金类型同步失败。";
							msg += message;
						}
					}
					if (vo.getCapitalType2() == 1L) {// 商城币
						vo.setCapitalType(2L);
						if (vo.getSyncGyFlag2() == 0L) {
							bean = this.syncInterface.syncAddFee(vo);
						} else {
							bean = this.syncInterface.syncUpdateFee(vo);
						}
						if (bean != null) {
							if (bean.isSyncSuccess()) {
								vo.setSyncGyFlag2(1L);
								vo.setMerchid(bean.getMerchid());
								vo.setSyncTime(TimeStamp.getTime(TimeStamp.YYYYMMDDhhmmss));
							}
							this.storeService.addOrUpdateStoreFee(vo);
							message = vo.getId() + "商城币类型:" + bean.getMsg();
							msg += message;
						} else {
							message = vo.getId() + "商城币类型同步失败。";
							msg += message;
						}
					}

					if (vo.getCapitalType3() == 1L) {// 积分
						vo.setCapitalType(3L);
						if (vo.getSyncGyFlag3() == 0L) {
							bean = this.syncInterface.syncAddFee(vo);
						} else {
							bean = this.syncInterface.syncUpdateFee(vo);
						}
						if (bean != null) {
							if (bean.isSyncSuccess()) {
								vo.setSyncGyFlag3(1L);
								vo.setMerchid(bean.getMerchid());
								vo.setSyncTime(TimeStamp.getTime(TimeStamp.YYYYMMDDhhmmss));
								this.storeService.addOrUpdateStoreFee(vo);
							}
							message = vo.getId() + "积分类型:" + bean.getMsg();
							msg += message;
						} else {
							message = vo.getId() + "积分类型同步失败。";
							msg += message;
						}
					}
					/*
					if (vo.getCapitalType4() == 1L) {// 话费
						vo.setCapitalType(4L);
						if (vo.getSyncGyFlag4() == 0L) {
							bean = this.syncInterface.syncAddFee(vo);
						} else {
							bean = this.syncInterface.syncUpdateFee(vo);
						}
						if (bean != null) {
							if (bean.isSyncSuccess()) {
								vo.setSyncGyFlag4(1L);
								vo.setMerchid(bean.getMerchid());
								vo.setSyncTime(TimeStamp.getTime(TimeStamp.YYYYMMDDhhmmss));
								storeService.addOrUpdateStoreFee(vo);
							}
							message = vo.getId() + "话费类型:" + bean.getMsg();
							msg += message;
						} else {
							message = vo.getId() + "话费类型同步失败。";
							msg += message;
						}
					}
					*/
					
				}
				logUtils.logOther("商户费率审核", "同步清算系统：" + message, sessionUser.getId());
			}
		}
		return JsonRespWrapper.successReload(msg);
	}

	@RequestMapping(value = "/syncQuery")
	@ResponseBody
	public Object syncQuery(@RequestParam(value = "ids") String ids, Model model, HttpServletRequest request) throws SQLException {
		String msg = "操作完成。";
		String message = "";
		Long backStoreId = null;
		SyncGYResponseBean bean = null;
		String[] idarray = ids.split(",");
		if (idarray != null) {
			// 获取当前的登录用户
			SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(SessionUser.SESSION_USER_KEY);
			for (String id : idarray) {
				if ("".equals(id)) {
					continue;
				}
				StoreFee vo = this.storeService.findStoreFeeById(Long.valueOf(id));
				if (vo != null) {
					// if (vo.getStoreId().equals(backStoreId)) { //
					// 同一商户只需要一次查询，返回的list是该商户下的所有费率
					// continue;
					// }
					backStoreId = vo.getStoreId();
					if (vo.getMerchid() == null) {
						message = vo.getId() + "该费率未同步成功，暂不能查询。";
						msg += message;
						continue;
					}
					if (vo.getStatus() != 3) {
						message = vo.getId() + "状态：未审核通过，暂不能查询。";
						msg += message;
						continue;
					}
					bean = this.syncInterface.queryStoreFee(vo);
					if (bean != null) {
						List<MerchantFeeInfo> list = bean.getMerchantFeeInfoList();
						if (list != null && list.size() > 0) {
							String filtersql = "";
							for (MerchantFeeInfo info : list) {
								System.out.println(info.getEffortdate());
								System.out.println(info.getExpirydate());
								bean.setStatus(info.getStatus());
								if ("1".equals(info.getCapitaltype())) {
									if (bean.isSyncSuccess()) {
										filtersql = " and t.capital_type1 = 1 ";
										StoreFee fee = this.storeFeeService.getStoreFee(vo.getId(), info.getProductiontype(), info.getEffortdate(),
										                                                info.getExpirydate(), info.getCleartype(), filtersql,"");
										if (fee == null) {
											continue;
										}
										fee.setSyncGyFlag1(bean.getFeeInfoStatus());
										fee.setMerchid(bean.getMerchid());
										fee.setSyncTime(TimeStamp.getTime(TimeStamp.YYYYMMDDhhmmss));
										storeService.addOrUpdateStoreFee(fee);
										message = fee.getId() + "现金状态：" + StoreFee.syncGyFlagMap.get(fee.getSyncGyFlag1()) + "。";
										msg += message;
									} else {
										message = vo.getId() + bean.getMsg();
										msg += message;
									}
								}
								if ("2".equals(info.getCapitaltype())) {
									if (bean.isSyncSuccess()) {
										filtersql = " and t.capital_type2 = 1 ";
										StoreFee fee = this.storeFeeService.getStoreFee(vo.getId(), info.getProductiontype(), info.getEffortdate(),
										                                                info.getExpirydate(), info.getCleartype(), filtersql,"");
										if (fee == null) {
											continue;
										}
										fee.setSyncGyFlag2(bean.getFeeInfoStatus());
										fee.setMerchid(bean.getMerchid());
										fee.setSyncTime(TimeStamp.getTime(TimeStamp.YYYYMMDDhhmmss));
										storeService.addOrUpdateStoreFee(fee);
										message = fee.getId() + "商城币状态：" + StoreFee.syncGyFlagMap.get(fee.getSyncGyFlag2()) + "。";
										msg += message;
									} else {
										message = vo.getId() + bean.getMsg();
										msg += message;
									}
								}
								if ("3".equals(info.getCapitaltype())) {
									if (bean.isSyncSuccess()) {
										filtersql = " and t.capital_type3 = 1 ";
										StoreFee fee = this.storeFeeService.getStoreFee(vo.getId(), info.getProductiontype(), info.getEffortdate(),
										                                                info.getExpirydate(), info.getCleartype(), filtersql,"");
										if (fee == null) {
											continue;
										}
										fee.setSyncGyFlag3(bean.getFeeInfoStatus());
										fee.setMerchid(bean.getMerchid());
										fee.setSyncTime(TimeStamp.getTime(TimeStamp.YYYYMMDDhhmmss));
										storeService.addOrUpdateStoreFee(fee);
										message = fee.getId() + "积分状态：" + StoreFee.syncGyFlagMap.get(fee.getSyncGyFlag3()) + "。";
										msg += message;
									} else {
										message = vo.getId() + bean.getMsg();
										msg += message;
									}
								}
								if ("4".equals(info.getCapitaltype())) {
									if (bean.isSyncSuccess()) {
										filtersql = " and t.capital_type4 = 1 ";
										StoreFee fee = this.storeFeeService.getStoreFee(vo.getId(), info.getProductiontype(), info.getEffortdate(),
										                                                info.getExpirydate(), info.getCleartype(), filtersql,"true");
										if (fee == null) {
											continue;
										}
										fee.setSyncGyFlag4(bean.getFeeInfoStatus());
										fee.setMerchid(bean.getMerchid());
										fee.setSyncTime(TimeStamp.getTime(TimeStamp.YYYYMMDDhhmmss));
										storeService.addOrUpdateStoreFee(fee);
										message = fee.getId() + "话费状态：" + StoreFee.syncGyFlagMap.get(fee.getSyncGyFlag4()) + "。";
										msg += message;
									} else {
										message = "";
										msg += vo.getId() + bean.getMsg();
									}
								}
							}
						} else {
							message = vo.getId() + "费率不存在。";
							msg += message;
						}
					} else {
						message = vo.getId() + "费率查询失败。";
						msg += message;
					}
					//
					// if (vo.getCapitalType1() == 1L) {// 现金
					// vo.setCapitalType(1L);
					// bean = this.syncInterface.queryStoreFee(vo);
					// if (bean != null) {
					// vo.setSyncGyFlag1(Long.valueOf(bean.getCode()));
					// vo.setMerchid(bean.getMerchid());
					// vo.setSyncTime(TimeStamp.getTime(TimeStamp.YYYYMMDDhhmmss));
					// storeService.addOrUpdateStoreFee(vo);
					// msg += vo.getId() + "现金类型查询成功。";
					// } else {
					// msg += vo.getId() + "现金类型查询成功。";
					// }
					// }
					// if (vo.getCapitalType2() == 1L) {// 商城币
					// vo.setCapitalType(2L);
					// bean = this.syncInterface.queryStoreFee(vo);
					// if (bean != null) {
					// vo.setSyncGyFlag2(Long.valueOf(bean.getCode()));
					// vo.setMerchid(bean.getMerchid());
					// vo.setSyncTime(TimeStamp.getTime(TimeStamp.YYYYMMDDhhmmss));
					// this.storeService.addOrUpdateStoreFee(vo);
					// msg += vo.getId() + "商城币类型查询成功。";
					// } else {
					// msg += vo.getId() + "商城币类型查询成功。";
					// }
					// }
					//
					// if (vo.getCapitalType3() == 1L) {// 积分
					// vo.setCapitalType(3L);
					// bean = this.syncInterface.queryStoreFee(vo);
					// if (bean != null) {
					// vo.setSyncGyFlag3(Long.valueOf(bean.getCode()));
					// vo.setMerchid(bean.getMerchid());
					// vo.setSyncTime(TimeStamp.getTime(TimeStamp.YYYYMMDDhhmmss));
					// this.storeService.addOrUpdateStoreFee(vo);
					// msg += vo.getId() + " 积分类型查询成功。";
					// } else {
					// msg += vo.getId() + " 积分类型查询成功。";
					// }
					// }

				}
				if (sessionUser != null) {
					logUtils.logOther("商户费率审核", "查询清算系统：" + message, sessionUser.getId());
				} else {
					// 监控程序调用时sessionUser为空
					log.info("业务监控程序商户费率查询清算系统:" + message);
				}
			}
		}
		// logUtils.logOther("商户费率审核", "查询清算系统");
		// return JsonRespWrapper.success(msg, "/store/storeFee/auditlist");
		return JsonRespWrapper.successReload(msg);

	}
	@RequestMapping(value = "/syncPhoneCharge/{id}")
	@ResponseBody
	public Object syncPhoneCharge(@PathVariable Long id, Model model,HttpServletRequest request) throws SQLException {
		String msg = "操作完成。";
		String message = "";
		SyncGYResponseBean bean = null;
			// 获取当前的登录用户
			SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(SessionUser.SESSION_USER_KEY);
				StoreFee vo = this.storeService.findStoreFeeById(Long.valueOf(id));
				if (vo != null) {
					Store store = this.storeService.findStoreById(vo.getStoreId());
					if (store == null) {
						message = "该商户不存在。";
						msg += message;
						return JsonRespWrapper.successReload(msg);
					}
					if (store.getMerchid() == null) {
						message = vo.getId() + "该商户基本信息未同步成功，暂不能同步。";
						msg += message;
						return JsonRespWrapper.successReload(msg);
					}
					if (vo.getStatus() != 3) {
						message = vo.getId() + "状态：未审核通过，暂不能同步。";
						msg += message;
						return JsonRespWrapper.successReload(msg);
					}
					if (vo.getMerchid() == null) {
						vo.setMerchid(store.getMerchid());
					}
					if (vo.getCapitalType4() == 1L && StringUtils.isNotEmpty(vo.getPhoneEffortdate())) {// 话费
						vo.setCapitalType(4L);
						if (vo.getSyncGyFlag4() == 0L && StringUtils.isNotEmpty(vo.getPhoneEffortdate())) {
							vo.setTempDate(vo.getEffortdate());
							vo.setEffortdate(vo.getPhoneEffortdate());
							bean = this.syncInterface.syncAddFee(vo);
						} else {
							vo.setTempDate(vo.getEffortdate());
							vo.setEffortdate(vo.getPhoneEffortdate());
							bean = this.syncInterface.syncUpdateFee(vo);
						}
						if (bean != null) {
							if (bean.isSyncSuccess()) {
								vo.setSyncGyFlag4(1L);
								vo.setMerchid(bean.getMerchid());
								vo.setSyncTime(TimeStamp.getTime(TimeStamp.YYYYMMDDhhmmss));
								vo.setEffortdate(vo.getTempDate());
								storeService.addOrUpdateStoreFee(vo);
							}
							message = vo.getId() + "话费类型:" + bean.getMsg();
							msg += message;
						} else {
							message = vo.getId() + "话费类型同步失败。";
							msg += message;
						}
					}
				}
				logUtils.logOther("商户费率审核", "同步清算系统：" + message, sessionUser.getId());
				return JsonRespWrapper.successReload(msg);
	}
}
