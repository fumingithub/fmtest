/**
 * Copyright (c) 2006-2015 jqsoft.net
 */
package net.jqsoft.base.system.view;

import net.jqsoft.base.system.domain.Attachment;
import net.jqsoft.base.system.service.IAttachmentService;


import org.springframework.beans.factory.annotation.Autowired;
import org.zcframework.core.view.support.entity.BaseEntityAction;

/**
 * AttachmentAction
 * @author xiaohf@jqsoft.net
 * @version 1.0
 */
public class AttachmentAction extends BaseEntityAction<Attachment, IAttachmentService> {
    @Autowired
    private IAttachmentService attachmentService;

    @Override
    protected IAttachmentService getEntityManager() {
        return attachmentService;
    }

    @Override
    public boolean needGenerateUUID() {
        return false;
    }
}
