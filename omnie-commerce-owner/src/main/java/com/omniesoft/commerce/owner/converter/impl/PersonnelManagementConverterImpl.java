package com.omniesoft.commerce.owner.converter.impl;

import com.omniesoft.commerce.owner.controller.management.payload.AdminRoleDto;
import com.omniesoft.commerce.owner.converter.PersonnelManagementConverter;
import com.omniesoft.commerce.persistence.entity.admin.AdminRoleEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author Vitalii Martynovskyi
 * @since 17.10.17
 */
@Service
@AllArgsConstructor
public class PersonnelManagementConverterImpl implements PersonnelManagementConverter {
    private final ModelMapper mapper;

    @Override
    public Set<AdminRoleDto> roles(Set<AdminRoleEntity> roles) {
        if (roles != null) {

            java.lang.reflect.Type targetListType = new TypeToken<Set<AdminRoleDto>>() {
            }.getType();
            return mapper.map(roles, targetListType);
        }
        return null;
    }

}
