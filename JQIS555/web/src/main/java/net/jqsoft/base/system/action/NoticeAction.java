/**
 * Copyright (c) 2013-2015 jqsoft.net
 */
package net.jqsoft.base.system.action;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.zcframework.core.utils.date.UtilDateTime;
import org.zcframework.core.view.support.Result;
import org.zcframework.security.SpringSecurityUtils;
import org.zcframework.security.object.Loginer;

import net.jqsoft.base.system.domain.Notice;
import net.jqsoft.base.system.service.INoticeService;
import net.jqsoft.common.action.MyEntityAction;
import net.jqsoft.common.exception.ManagerException;

/**
 * 公告管理Action
 * 
 * @author guoxx
 * @createDate 2017年1月17日上午8:42:53
 */
public class NoticeAction extends MyEntityAction<Notice, INoticeService> {

	@Autowired
	private INoticeService noticeService;

	@Override
	protected INoticeService getEntityManager() {
		return noticeService;
	}

	/**
	 * 公告信息的编辑或新增的跳转页面
	 * 
	 * @author guoxx
	 * @createDate 2017年1月17日上午14:39:53
	 * @return 编辑或新增页面
	 */
	public String editPage() {
		if (StringUtils.isNotEmpty(entity.getId())) {
			entity = noticeService.get(entity.getId());
			// 获取登陆对象
			Loginer loginer = SpringSecurityUtils.getCurrentUser();
			if (!entity.getCreator().equalsIgnoreCase(loginer.getId().toString())) {
				result = new Result(false, "该公告不是您编辑的，您无权编辑");
				return RESULT;
			}
			if (entity.getStatus().equals("1")) {
				result = new Result(false, "公告已经发布，不能修改");
				return RESULT;
			}
		}
		return "edit";
	}

	/**
	 * 公告新增或编辑的保存操作
	 * 
	 * @author guoxx
	 * @createDate 2017年1月18日上午10:12:53
	 * @return 保存结果
	 */
	public String save() {
		try {
			int res = noticeService.saveOrUpdate(entity);
			if (res == 1) {
				result = new Result(true, "添加公告信息成功");
			} else {
				result = new Result(true, "更新公告信息成功");
			}
		} catch (ManagerException ex) {
			result = new Result(false, ex.getErrMsg(), "", false);
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(false, "保存公告信息失败！");
		} finally {

		}
		// 返回提示信息到页面
		return RESULT;
	}

	/**
	 * 公告信息查询
	 * 
	 * @author guoxx
	 * @createDate 2017年1月17日下午3:27:01
	 * @return 查询页面
	 */
	public String viewpage() {
		if (StringUtils.isNotEmpty(entity.getId())) {
			entity = noticeService.get(entity.getId());
		}
		return "view";
	}

	/**
	 * 删除一条公告
	 * 
	 * @author guoxx
	 * @createDate 2017年1月17日下午3:57:01
	 * @return 删除结果
	 */
	public String delete() {
		if (StringUtils.isNotEmpty(entity.getId())) {
			entity = noticeService.get(entity.getId());
			Loginer loginer = SpringSecurityUtils.getCurrentUser();
			if (!entity.getCreator().equalsIgnoreCase(loginer.getId().toString())) {
				result = new Result(false, "该公告不是您编辑的，您无权删除");
			} else {
				noticeService.removeById(entity.getId());
				result = new Result(true, "删除成功！");
			}
		}
		return RESULT;
	}

	/**
	 * 已发布的公告置顶
	 * 
	 * @author guoxx
	 * @createDate 2017年1月18日下午2:42:05
	 * @return 置顶结果
	 */
	public String toTop() {
		if (StringUtils.isNotEmpty(entity.getId())) {
			entity = noticeService.get(entity.getId());
			Loginer loginer = SpringSecurityUtils.getCurrentUser();
			if (!entity.getCreator().equalsIgnoreCase(loginer.getId().toString())) {
				result = new Result(false, "该公告不是您编辑的，您无权置顶");
			} else {
				// 未发布的不能置顶
				if (entity.getStatus().equals("0")) {
					result = new Result(false, "该公告还没有发布，不能置顶");
				} else if (entity.getIsTop().equals("1")) {
					entity.setUpdateTime(UtilDateTime.nowDate());
					noticeService.update(entity);
					result = new Result(true, "再次置顶成功");
				} else {
					entity.setIsTop("1");
					entity.setUpdator(loginer.getId().toString());
					entity.setUpdatorName(loginer.getUsername());
					entity.setUpdateTime(UtilDateTime.nowDate());
					noticeService.update(entity);
					result = new Result(true, "置顶成功");
				}
			}
		}

		return RESULT;
	}

	/**
	 * 公告发布
	 * 
	 * @author guoxx
	 * @createDate 2017年1月18日下午3:23:01
	 * @return 发布结果
	 */
	public String releaseNotice() {
		if (StringUtils.isNotEmpty(entity.getId())) {
			entity = noticeService.get(entity.getId());
			Loginer loginer = SpringSecurityUtils.getCurrentUser();
			if (!entity.getCreator().equalsIgnoreCase(loginer.getId().toString())) {
				result = new Result(false, "该公告不是您编辑的，您无权发布");
			} else {
				// 未发布的不能置顶
				if (entity.getStatus().equals("1")) {
					result = new Result(false, "该公告已经发布过了，请重新选择");
				} else {
					try {
						int res = noticeService.releaseNotice(entity);
						if (res == 2) {
							result = new Result(true, "公告信息发布成功");
						}
					} catch (Exception e) {
						e.printStackTrace();
						result = new Result(false, "公告信息发布失败，请重新发布");
					}

				}
			}
		}
		return RESULT;
	}

	/**
	 * 撤销已经发布的公告
	 * 
	 * @author guoxx
	 * @createDate 2017年1月18日下午5:05:11
	 * @return 撤销结果
	 */
	public String cancelRelease() {
		if (StringUtils.isNotEmpty(entity.getId())) {
			entity = noticeService.get(entity.getId());
			Loginer loginer = SpringSecurityUtils.getCurrentUser();
			if (!entity.getCreator().equalsIgnoreCase(loginer.getId().toString())) {
				result = new Result(false, "该公告不是你编辑的，你不能撤回");
			} else {
				if (entity.getStatus().equals("1")) {
					try {
						int res = noticeService.cancelRelease(entity);
						if (res == 2) {
							result = new Result(true, "已成功撤回");
						}
					} catch (Exception e) {
						e.printStackTrace();
						result = new Result(false, "撤回失败，请重新操作");
					}

				} else {
					result = new Result(false, "该公告没有发布，不能撤回");
				}
			}
		}
		return RESULT;
	}
}
