package com.zed.application.out;

import lombok.Data;

import java.util.List;

@Data
public class NamespaceDTO {

    private String name;

    private List<ClientDTO> clients;

}
