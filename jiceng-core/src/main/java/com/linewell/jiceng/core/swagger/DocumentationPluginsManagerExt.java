package com.linewell.jiceng.core.swagger;

import springfox.documentation.service.Operation;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spring.web.plugins.DocumentationPluginsManager;

/**
 * @author tanghc
 */
public class DocumentationPluginsManagerExt extends DocumentationPluginsManager {


    @Override
    public Operation operation(OperationContext operationContext) {
        Operation operation = super.operation(operationContext);
        return operation;
    }


}
