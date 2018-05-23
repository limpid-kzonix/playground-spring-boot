package com.omniesoft.commerce.persistence.repository.card;

import com.omniesoft.commerce.persistence.entity.cards.handbook.HandbookPhoneEntity;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.UUID;

@Transactional
public interface HandbookPhoneRepository extends CrudRepository<HandbookPhoneEntity, UUID> {
    void deleteByIdAndAndHandbook_Id(UUID id, UUID handbookId);
}
