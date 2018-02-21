package com.omniesoft.commerce.user.controller.card.business;

import com.omniesoft.commerce.common.request.PageableRequest;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.cards.BusinessCardEntity;
import com.omniesoft.commerce.persistence.projection.card.BusinessCardSummary;
import com.omniesoft.commerce.user.controller.card.payload.BusinessCardPayload;
import com.omniesoft.commerce.user.service.card.BusinessCardService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(path = "/cards/business")
@AllArgsConstructor
@Validated
@Api(value = "Card", tags = "Card Controller", description = "@limpid")
public class BusinessCardController {

    private final BusinessCardService businessCardService;
    private final ModelMapper modelMapper;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<BusinessCardSummary> getBusinessCards(
            @ApiIgnore UserEntity userEntity,
            @Valid PageableRequest pageableRequest, Pageable pageable) {
        return businessCardService.getBusinessCards(userEntity, pageable);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public BusinessCardPayload createBusinessCard(@Valid @RequestBody BusinessCardPayload businessCardPayload,
                                                  @ApiIgnore UserEntity
                                                          userEntity) {

        BusinessCardEntity businessCardEntity = businessCardService.createDiscountCard(businessCardPayload, userEntity);
        return modelMapper.map(businessCardEntity, BusinessCardPayload.class);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public BusinessCardPayload updateBusinessCard(@Valid @RequestBody BusinessCardPayload businessCardPayload,
                                                  @ApiIgnore UserEntity userEntity) {

        BusinessCardEntity businessCardEntity = businessCardService.updateDiscountCard(businessCardPayload, userEntity);
        return modelMapper.map(businessCardEntity, BusinessCardPayload.class);
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public void deleteBusinessCard(@ApiIgnore UserEntity userEntity, @PathVariable("id") UUID id) {

        businessCardService.deleteDiscountCard(id, userEntity);
    }
}
