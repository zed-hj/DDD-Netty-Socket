package com.zed.application.in.event;

import lombok.Data;

import java.util.List;

/**
 * @author Zed
 */
@Data
public class NotifyClientsCommand {

    private List<String> clientIds;

    private String value;

}
