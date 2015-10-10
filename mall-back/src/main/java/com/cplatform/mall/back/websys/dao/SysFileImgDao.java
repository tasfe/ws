package com.cplatform.mall.back.websys.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.cplatform.mall.back.websys.entity.SysFileImg;
import com.cplatform.mall.back.websys.entity.SysFileImgLink;

/**
 * Title. 通用文件存储<br>
 * Description.
 * <p>
 * Copyright: Copyright (c) 2013-5-29 下午5:52:31
 * <p>
 * Company: 北京宽连十方数字技术有限公司
 * <p>
 * Author: BaiJie
 * <p>
 * Version: 1.0
 * <p>
 */
public interface SysFileImgDao extends PagingAndSortingRepository<SysFileImg, Long> {

	/**
	 * 获得该业务对应的文件
	 * 
	 * @param bsKey
	 *            业务key
	 * @param bsId
	 *            业务id
	 * @return
	 */
	public List<SysFileImg> findByBsKeyAndBsId(String bsKey, Long bsId);
	public List<SysFileImg> findByBsId(Long bsId);
	public SysFileImg findByBsIdAndBsKey(Long bsId, String bsKey);
}