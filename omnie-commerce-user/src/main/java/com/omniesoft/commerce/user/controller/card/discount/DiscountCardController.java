package com.omniesoft.commerce.user.controller.card.discount;

import com.omniesoft.commerce.common.request.PageableRequest;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.cards.DiscountCardEntity;
import com.omniesoft.commerce.persistence.projection.card.DiscountCardSummary;
import com.omniesoft.commerce.user.controller.card.payload.DiscountCardPayload;
import com.omniesoft.commerce.user.service.card.DiscountCardService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(path = "/cards/discount")
@Validated
@Api(value = "Card", tags = "Card Controller", description = "@limpid")
public class DiscountCardController {

    private final DiscountCardService discountCardService;
    private final ModelMapper modelMapper;

    @Autowired
    public DiscountCardController(DiscountCardService discountCardService, ModelMapper modelMapper) {
        this.discountCardService = discountCardService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<DiscountCardSummary> getDiscountCards(
            @Valid PageableRequest page, Pageable pageable,
            @ApiIgnore UserEntity userEntity) {
        return discountCardService.getDiscountCard(userEntity, pageable);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public DiscountCardPayload createDiscountCard(
            @ApiIgnore UserEntity userEntity,
            @Valid @RequestBody DiscountCardPayload discountCardPayload) {
        DiscountCardEntity discountCard = discountCardService.createDiscountCard(discountCardPayload, userEntity);
        return modelMapper.map(discountCard, DiscountCardPayload.class);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public DiscountCardPayload updateDiscountCard(
            @ApiIgnore UserEntity userEntity,
            @Valid @RequestBody DiscountCardPayload discountCardPayload
    ) {
        DiscountCardEntity discountCard = discountCardService.updateDiscountCard(discountCardPayload, userEntity);
        return modelMapper.map(discountCard, DiscountCardPayload.class);
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public void deleteDiscountCard(@ApiIgnore UserEntity userEntity, @PathVariable("id") UUID id) {
        discountCardService.deleteDiscountCard(id, userEntity);
    }
}
