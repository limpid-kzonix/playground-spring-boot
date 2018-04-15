package com.omniesoft.commerce.user.controller.handbook;

import com.omniesoft.commerce.common.request.PageableRequest;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.cards.handbook.HandbookEntity;
import com.omniesoft.commerce.persistence.projection.card.handbook.HandbookSummary;
import com.omniesoft.commerce.user.controller.handbook.payload.HandbookPayload;
import com.omniesoft.commerce.user.service.handbook.HandbookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/handbook")
@Validated
@Api(value = "Handbook", tags = "Handbook Controller", description = "@limpid")
public class HandbookController {

    private final HandbookService handbookService;

    private final ModelMapper modelMapper;

    @Autowired
    public HandbookController(HandbookService handbookService, ModelMapper modelMapper) {

        this.handbookService = handbookService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(path = "/items", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<HandbookSummary> getHandbookItemsWithSearch(
            @ApiParam(example = "For example : 'name, asc' or just 'name', where name is field of model",
                    required = true) @Valid PageableRequest page, Pageable pageable,
            @ApiParam(defaultValue = "_", required = true) @RequestParam("search") String search
    ) {

        return handbookService.getHandbook(pageable, search);
    }

    @GetMapping(path = "/items/organizations", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<HandbookPayload> getHandbookOrgsItemsWithSearch(
            @ApiParam(example = "For example : 'name, asc' or just 'name', where name is field of model", required = true)
            @Valid PageableRequest page,
            Pageable pageable,
            @ApiParam(defaultValue = "_", required = true)
            @RequestParam("search") String search) {
        return handbookService.getHandbookOrganization(pageable, search);
    }

    @PostMapping(path = "/items", produces = MediaType.APPLICATION_JSON_VALUE)
    public HandbookPayload proposeHandbookItem(
            @Valid @RequestBody HandbookPayload payload,
            @ApiIgnore UserEntity userEntity
    ) {

        HandbookEntity handbookEntity = handbookService.proposeHandbook(payload, userEntity);
        return modelMapper.map(handbookEntity, HandbookPayload.class);
    }


    @GetMapping(path = "/items/my", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<HandbookSummary> getMyHandbookItem(
            @Valid PageableRequest page, Pageable pageable,
            @ApiIgnore UserEntity userEntity,
            @ApiParam(defaultValue = "_", required = true) @RequestParam("search") String search) {

        return handbookService.getMyHandbookItems(pageable, search, userEntity);
    }


    @DeleteMapping("/items/my/{id}")
    public String deleteMyHandbookItems(@PathVariable("id") UUID id, @ApiIgnore UserEntity userEntity) {

        handbookService.deleteHandbookItem(id, userEntity);
        return "OK";
    }

    @PutMapping(path = "/items/my/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public HandbookPayload updateHandbookItems(@PathVariable("id") UUID id, @Valid @RequestBody HandbookPayload
            payload, @ApiIgnore UserEntity userEntity) {

        HandbookEntity handbookEntity = handbookService.updateHandbook(id, payload, userEntity);
        return modelMapper.map(handbookEntity, HandbookPayload.class);
    }

}
