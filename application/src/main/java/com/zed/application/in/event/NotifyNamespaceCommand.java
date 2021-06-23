package com.zed.application.in.event;

import lombok.Data;

/**
 * @author Zed
 */
@Data
public class NotifyNamespaceCommand {

    private String namespace;

    private String value;

}
