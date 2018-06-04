package com.omniesoft.commerce.notification;

import com.omniesoft.commerce.persistence.repository.organization.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class SearchAdminService implements ISearchAdminService {

    private final OrganizationRepository organizationRepository;

    @Override
    public String getOwner(UUID organizationId) {
        return organizationRepository.findOne(organizationId).getOwner().getUser().getLogin();
    }
}
