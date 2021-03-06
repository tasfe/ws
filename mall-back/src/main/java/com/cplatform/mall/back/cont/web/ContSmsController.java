package com.cplatform.mall.back.cont.web;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
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
import com.cplatform.mall.back.cont.entity.ContSms;
import com.cplatform.mall.back.cont.entity.ContentCode;
import com.cplatform.mall.back.cont.service.ContSmsService;
import com.cplatform.mall.back.cont.service.ContentCodeService;
import com.cplatform.mall.back.item.web.ItemManageController;
import com.cplatform.mall.back.model.SessionUser;
import com.cplatform.mall.back.sys.entity.SysUnit;
import com.cplatform.mall.back.sys.service.SysUnitService;
import com.cplatform.mall.back.utils.JsonRespWrapper;
import com.cplatform.mall.back.utils.LogUtils;
import com.cplatform.util2.TimeStamp;

/**
 * 短信内容管理控制器. <br>
 * 类详细说明.
 * <p>
 * Copyright: Copyright (c) 2013-5-25 下午02:59:26
 * <p>
 * Company: 北京宽连十方数字技术有限公司
 * <p>
 * 
 * @author zhaowei@c-platform.com
 * @version 1.0.0
 */
@Controller
@RequestMapping(value = "/cont/sms")
public class ContSmsController {

	private static final Logger log = Logger.getLogger(ItemManageController.class);

	@Autowired
	private LogUtils logUtils;

	@Autowired
	private ContSmsService contSmsService;

	@Autowired
	private SysUnitService sysUnitService;

	@Autowired
	private ContentCodeService contentCodeService;

	/**
	 * 短信内容查询
	 * 
	 * @param contSms
	 *            查询条件
	 * @param page
	 *            当前页
	 * @param model
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping(value = "/list")
	public String list(ContSms contSms, @RequestParam(value = "page", required = false, defaultValue = "1") int page, Model model)
	        throws SQLException {
		Page<ContSms> contSmsPage = contSmsService.findContSms(contSms, page);
		model.addAttribute("contSmsPage", contSmsPage);
		model.addAttribute("statusMap", ContSms.getStatusMap());
		return "cont/sms/sms_list";
	}

	/**
	 * 跳转录入短信内容
	 * 
	 * @param model
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) throws SQLException {
		ContSms contSms = new ContSms();
		List<ContentCode> contentCodeList = contentCodeService.getContentCodeList(0L);
		model.addAttribute("contentCodeList", contentCodeList);
		model.addAttribute("method", "add");
		model.addAttribute("contSms", contSms);
		return "cont/sms/sms_add";
	}

	/**
	 * 录入短信内容
	 * 
	 * @param online
	 *            短信内容务实体类
	 * @param request
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Object createPost(@ModelAttribute("online") ContSms contSms, HttpServletRequest request, BindingResult result) {
		SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(SessionUser.SESSION_USER_KEY);
		contSms.setUpdateTime(TimeStamp.getTime(14));
		contSms.setAuditorId(sessionUser.getId());
		contSms.setUnitId(sessionUser.getUnitId().toString());
		contSms.setStartTime(contSms.getStartTime().replaceAll("-", "").replaceAll(" ", "").replaceAll(":", ""));
		contSms.setEndTime(contSms.getEndTime().replaceAll("-", "").replaceAll(" ", "").replaceAll(":", ""));
		contSms.setStatus(ContSms.STATUS_0);
		try {
			// 执行业务逻辑
			contSmsService.saveContSms(contSms);

			// 提示并跳转
			logUtils.logAdd("短信内容管理", "新增操作，内容id："+contSms.getId());
			return JsonRespWrapper.success("录入成功", "/cont/sms/list");

		}
		catch (Exception ex) {
			// 未知的错误消息，一般是exception catch后通知用户
			logUtils.logAdd("短信内容管理", "新增操作失败，内容id："+contSms.getId());
			log.error(ex.getMessage());
			return JsonRespWrapper.error(ex.getMessage());
		}
	}

	/**
	 * 跳转短信内容编辑
	 * 
	 * @param id
	 *            待编辑短信内容ID
	 * @param model
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false) Long id, Model model) throws SQLException {
		ContSms contSms = contSmsService.findOneContSms(id);
		List<ContentCode> contentCodeList = contentCodeService.getContentCodeList(0L);
		model.addAttribute("contentCodeList", contentCodeList);
		model.addAttribute("method", "edit");
		model.addAttribute("contSms", contSms);
		return "cont/sms/sms_add";
	}

	/**
	 * 短信内容编辑
	 * 
	 * @param contSms
	 *            短信内容实体类
	 * @param request
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public Object updatePost(@ModelAttribute("contSms") ContSms contSms, HttpServletRequest request, BindingResult result) {
		SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(SessionUser.SESSION_USER_KEY);
		// 将状态置为未审核
		contSms.setStatus(ContSms.STATUS_0);
		contSms.setUpdateTime(TimeStamp.getTime(14));
		contSms.setUpdateUserId(sessionUser.getId());
		contSms.setStartTime(contSms.getStartTime().replaceAll("-", "").replaceAll(" ", "").replaceAll(":", ""));
		contSms.setEndTime(contSms.getEndTime().replaceAll("-", "").replaceAll(" ", "").replaceAll(":", ""));
		try {
			// 执行业务逻辑
			contSmsService.saveContSms(contSms);
			// 返回来源地址
			String backUrl = request.getParameter("backUrl");
			logUtils.logAdd("短信内容管理", "修改操作，内容id："+contSms.getId());
			return JsonRespWrapper.success("修改成功", backUrl);
		}
		catch (Exception ex) {
			// 未知的错误消息，一般是exception catch后通知用户
			logUtils.logAdd("短信内容管理", "修改操作失败，内容id："+contSms.getId());
			log.error(ex.getMessage());
			return JsonRespWrapper.error(ex.getMessage());
		}
	}

	/**
	 * 查看短信内容
	 * 
	 * @param id
	 *            短信内容ID
	 * @param model
	 * @return
	 * @throws SQLException 
	 * @throws NumberFormatException 
	 */
	@RequestMapping(value = "/view")
	public String view(@RequestParam(required = false) Long id, Model model) throws NumberFormatException, SQLException {
		ContSms contSms = contSmsService.findOneContSms(id);
		SysUnit sysUnit = sysUnitService.findUnitById(Long.valueOf(contSms.getUnitId()));
		if (null != sysUnit) {
			contSms.setUnitName(sysUnit.getName());
		}
		ContentCode contentCode = contSmsService.findCodeBySrc(contSms.getContentSrc());
		if (null != contentCode) {
			contSms.setSrcName(contentCode.getName());
		}
		model.addAttribute("contSms", contSms);
		return "cont/sms/sms_view";
	}

	/**
	 * 跳转短信内容审核
	 * 
	 * @param id
	 *            待审核短信内容ID
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/sms_auditing", method = RequestMethod.GET)
	public String auditing(@RequestParam(required = false) Long id, Model model) {
		ContSms contSms = contSmsService.findOneContSms(id);
		model.addAttribute("id", contSms.getId());
		model.addAttribute("content", contSms.getContent());
		model.addAttribute("contSms", contSms);
		return "cont/sms/sms_auditing";
	}

	/**
	 * 短信内容审核
	 * 
	 * @param id
	 *            待审核短信内容ID
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/sms_auditing", method = RequestMethod.POST)
	@ResponseBody
	public Object createPostAuditing(@RequestParam(required = false) Long id, @RequestParam(required = false) Long status,
	        @RequestParam(required = false) String advice, HttpServletRequest request) {
		if (2L == status && advice.isEmpty()) {
			return JsonRespWrapper.successAlert("审核失败，请填写驳回原因！");
		}
		// else if(4L == status && advice.isEmpty()){
		// return JsonRespWrapper.successAlert("请填写内部驳回原因！");
		// }
		ContSms contSms = contSmsService.findOneContSms(id);
		contSms.setStatus(status);
		if (null != advice) {
			contSms.setAdvice(advice);
		}
		try {
			// 执行业务逻辑
			contSmsService.saveContSms(contSms);

			// 返回来源地址
			String backUrl = request.getParameter("backUrl");
			logUtils.logAudit("短信内容管理", "审核成功，内容id："+contSms.getId());
			return JsonRespWrapper.success("审核成功", backUrl);
		}
		catch (Exception ex) {
			// 未知的错误消息，一般是exception catch后通知用户
			logUtils.logAudit("短信内容管理", "审核失败，内容id："+contSms.getId());
			log.error(ex.getMessage());
			return JsonRespWrapper.error(ex.getMessage());
		}
	}

	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public Object deleteContSms(@PathVariable Long id) {
		ContSms contSms = contSmsService.findOneContSms(id);
		if (1L == contSms.getStatus()) {
			return JsonRespWrapper.successAlert("该短信内容已经审核通过，不能被删除！");
		}
		// if(3L == contSms.getStatus()) {
		// return JsonRespWrapper.successAlert("该短信内容已经内部审核通过，不能被删除！");
		// }
		contSmsService.deleteContSms(id);
		logUtils.logAudit("短信内容管理", "删除操作，内容id："+contSms.getId());
		return JsonRespWrapper.successReload("删除成功！");
	}
}
