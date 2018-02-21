package com.omniesoft.commerce.owner.controller.management;

import com.omniesoft.commerce.persistence.entity.enums.AdminPermission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Vitalii Martynovskyi
 * @since 27.09.17
 */
@RestController
@RequestMapping(path = "/permissions")
@Validated
@Api(value = "Permissions", tags = "Permissions API", description = "@omnie46")
public class PermissionController {

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Permissions", notes = "Return list of permissions for manage access for Owner API")
    public AdminPermission[] permissions() {
        return AdminPermission.values();
    }

    @GetMapping(path = "/my", produces = APPLICATION_JSON_VALUE)
    public AdminPermission[] myPermissions() {
        //TODO motokyi at 23.10.17: finish it
        return AdminPermission.values();
    }
}
