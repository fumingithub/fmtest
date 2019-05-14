/**
 * Copyright (c) 2006-2016 jqsoft.net
 */
package net.jqsoft.common.action;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.util.ReflectionUtils;
import org.zcframework.core.dao.support.ModelSetup;
import org.zcframework.core.dao.support.ORMType;
import org.zcframework.core.dao.support.Page;
import org.zcframework.core.dao.support.ibatis.IBatisModelSetup;
import org.zcframework.core.domain.support.Entity;
import org.zcframework.core.exception.CoreRuntimeException;
import org.zcframework.core.service.IBaseEntityService;
import org.zcframework.core.utils.clazz.BeanUtils;
import org.zcframework.core.utils.clazz.GenericsUtils;
import org.zcframework.core.view.support.BaseAction;
import org.zcframework.core.view.support.Result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 项目所有Action的父类
 *
 * @author jinliang
 * @create 2016-12-01 下午 3:49
 **/
abstract public class MyEntityAction<T extends Entity, M extends IBaseEntityService<T>> extends
        BaseAction implements Preparable, ModelDriven {
	

    /** 初始化日志器 */
	protected static Logger LOGGER = Logger.getLogger("operationLog");

    protected static final String DELETED = "deleted";

    /**
     * 操作状态:已新增
     */
    protected static final String STATE_SAVEED_NEW = "saved.new";

    /**
     * 操作状态:已修改
     */
    protected static final String STATE_SAVEED_UPDATE = "saved.update";

    /**
     * 操作状态:已删除2
     */
    protected static final String STATE_DELETED = "deleted";

    protected Class<T> entityClass; // Action所管理的Entity类型.

    protected Class idClass; // Action所管理的Entity的主键类型.

    protected String idName; // Action所管理的Entity的主键名.

    protected int start = 1;
    protected int limit;
    protected long totalCount;


    //protected Page page;


    /**
     * 操作后的状态
     */
    protected String state;

    public Result result;
    //新增结束后操作的
    public static final String RESULT="result";
    public static final String VIEW="view";

    /**
     * 取得entityClass的函数. JDK1.4不支持泛型的子类可以抛开Class<T> entityClass,重载此函数达到相同效果。
     */
    @JSON(serialize = false)
    protected Class<T> getEntityClass() {
        return entityClass;
    }

    /**
     * 获得EntityManager类进行CRUD操作,可以在子类重载.
     */
    @JSON(serialize = false)
    protected abstract M getEntityManager();

    /**
     * 业务对象
     */
    protected T entity;

    protected List entitys = new ArrayList();


    @JSON(serialize = true)
    public List<T> getEntitys() {
        return entitys;
    }

    public void setEntitys(List<T> entitys) {
        this.entitys = entitys;
    }

    public T getEntity() {
        return this.entity;
    }

    /**
     * 是否需要生成UUID
     * @return boolean
     */
    public boolean needGenerateUUID(){
        return false;
    }

    private ModelSetup filter;

    /**
     * 从request中获得Entity的id，并判断其有效性.
     */
    @JSON(serialize = false)
    protected Object getEntityId() {
        String idString = getHttpServletRequest().getParameter(idName);
        if (StringUtils.isEmpty(idString)) {
            return null;
        }
        try {
            return (Serializable) ConvertUtils.convert(idString, idClass);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Wrong when get id from request");
        }
    }

    /**
     * 设置request中Entity的id.
     */
    protected void setEntityId(Object id) {
        getHttpServletRequest().setAttribute(idName, id);
    }

    public void prepare() throws Exception {
        // 根据T,反射获得entityClass
        entityClass = GenericsUtils.getSuperClassGenricType(getClass());
        // 反射获得entity的主键类型
        try {
            idName = getEntityManager().getIdName(entityClass);
            idClass = BeanUtils.getPropertyType(entityClass, idName);
        } catch (Exception e) {
            ReflectionUtils.handleReflectionException(e);
        }
        entity = entityClass.newInstance();
    }

    @JSON(serialize = false)
    public T getModel() {
        return entity;
    }

    /**
     * 删除用的action
     */
    public String delete() {
        try {
            doDeleteEntity();
            this.setEntityId(null);
            this.setState(STATE_DELETED);
            result = new Result(true, getText("entity.deleted"));
            this.addActionMessage(getText("entity.deleted"));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            this.addActionError(getText("entity.deleted.failed"));
            result = new Result(false, getText("entity.deleted.failed"));
        }
        return DELETED;
    }

    public String view(){
        entity=doGetEntity();
        if(entity==null) {
            this.addActionError(getText("entity.missing"));
            result = new Result(false, getText("entity.missing"));
        }
        return VIEW;

    }

    /**
     * 编辑业务对象的Action函数.
     */
    public String edit() {
        T object = null;
        if (this.getEntityId() != null) {// 修改
            object = doGetEntity();
            if (object == null) {
                this.addActionError(getText("entity.missing"));
                result = new Result(false, getText("entity.missing"));
                return list();
            }
        } else {// 新增
            try {
                object = entityClass.newInstance();
            } catch (InstantiationException e) {
                logger.error(e);
            } catch (IllegalAccessException e) {
                logger.error(e);
            }
        }
        this.entity = object;
        return INPUT;
    }

    /**
     * 保存使用的action
     */
    public String save() {
        boolean isNew = false;
        T object;
        try {
            // 如果是修改操作，id is not blank
            if (getEntityId() != null) {
                object = doGetEntity();
                if (object == null) {
                    this.addActionError(getText("entity.missing"));
                    result = new Result(true, getText("entity.missing"));
                    return INPUT;
                }
            } else { // 否则为新增操作
                object = doNewEntity();
                isNew = true;
                // BeanUtils.forceSetProperty(entity, idName, null);
                //如果需要生成UUID则重写这个方法，返回true
                if(needGenerateUUID()) {
                    String uuid= UUID.randomUUID().toString();
                    entity.setId(uuid);
                    object.setId(uuid);
                }
            }
            bindEntity(entity, object);
            doSaveEntity(object);
            entity=object;
            if (isNew) {// 为了防止在新增界面点击保存时新增多条，第二次点击保存则为修改
                bindEntity(object, entity);
                this.setState(STATE_SAVEED_NEW);
                this.setEntityId(this.getEntityId());
                this.addActionMessage(getText("entity.saved.new"));
                String forward=getHttpServletRequest().getRequestURI();
                String queryString=getHttpServletRequest().getQueryString();
                if(queryString!=null&&!queryString.equals(""))
                    forward=forward+"?"+queryString+"&id="+entity.getId();
                else
                    forward=forward+"?id="+entity.getId();
                result = new Result(true, getText("entity.saved.new"),forward,false);
            } else {
                this.setState(STATE_SAVEED_UPDATE);
                this.addActionMessage(getText("entity.saved.update"));
                result = new Result(true, getText("entity.saved.update"));
            }

        } catch (CoreRuntimeException e) {
            logger.error(e.getMessage(), e);
            // String key = "entity.saved.failed";
            this.addActionError(e.getMessage());
            result = new Result(false, e.getMessage());
        }
        return RESULT;
    }

    /**
     * 默认首页，列表页面
     *
     * @return
     */
    public String execute() {
        return list();
    }

    /**
     * 列表使用的action
     * 请 在 子类 中 组装ModelSetup
     */
    public String list() {
        // 去除前台传来的参数的首尾空格 addBy jinliang at 2016-12-01
        Map<String, Object> params = getActionContext().getParameters();
        for (Map.Entry entry : params.entrySet()) {
            if (entry.getValue() instanceof String[]) {
                String[] vals = (String[]) entry.getValue();
                entry.setValue(new String[]{vals[0].toString().trim()});
            }
        }
        filter = setupModel();
        doPageEntity(filter);
        return SUCCESS;
    }

    /**
     * 钩子,让继承类实现modelSetup的组装
     *
     * @return
     */
    protected ModelSetup setupModel() {
        IBatisModelSetup model = (IBatisModelSetup) getModelSetupFromRequest(ORMType.IBATIS);
        model.setCountName(entityClass.getSimpleName() + ".count");
        model.setSqlName(entityClass.getSimpleName() + ".select");
        return model;

    }

    protected void doPageEntity(ModelSetup modelSetup) {
        if (limit == 0)
            limit = Page.DEFAULT_PAGE_SIZE;
        if (start < 1)
            start = 1;
        //int pageNo=start/limit+1;
        Page page = getEntityManager().pagedQuery(modelSetup, start, limit);
        this.setEntitys(page.getResult());

        this.setTotalCount(page.getTotalCount());
    }

    /**
     * 新建业务对象的函数.
     *
     * @return T
     */
    protected T doNewEntity() {
        T object = null;
        try {
            object = getEntityClass().newInstance();
        } catch (Exception e) {
            logger.error("Can't new Instance of entity.", e);
        }
        return object;
    }

    /**
     * 从数据库获取业务对象的函数.
     *
     * @return
     */
    protected T doGetEntity() {
        Object id = getEntityId();
        return getEntityManager().get(id);
    }

    /**
     * 保存业务对象的函数.
     *
     * @return
     */
    protected void doSaveEntity(T object) {
        if (getEntityId() != null)
            getEntityManager().update(object);
        else
            getEntityManager().create(object);
    }

    /**
     * 删除业务对象的函数.
     */
    protected void doDeleteEntity() {
        Object id = getEntityId();
        getEntityManager().removeById(id);
    }

    @JSON(serialize = false)
    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    /**
     * 将request中的entity内容通过BeanUtils的copyProperties()绑定到Object中.
     * 因为BeanUtils中两个参数的顺序很容易搞错，因此封装此函数.
     *
     * @param entity
     * @param object
     */
    protected void bindEntity(T entity, Object object) {
        if (entity != null) {
            try {
                BeanUtils.updateObject(entity, object);
            } catch (Exception e) {
                ReflectionUtils.handleReflectionException(e);
            }
        }
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public Result getResult() {
        return result;
    }

    public ModelSetup getFilter(){return filter;}

    public void setFilter(ModelSetup filter) {
        this.filter = filter;
    }
}
