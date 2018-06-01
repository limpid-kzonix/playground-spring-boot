package com.omniesoft.commerce.user.controller.temp;

import com.omniesoft.commerce.common.request.PageableRequest;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.projection.card.handbook.HandbookSummary;
import com.omniesoft.commerce.user.service.handbook.HandbookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(path = "/temp/handbooks")
@Validated
@Api(value = "Handbook", tags = "Handbook Temporary Controller", description = "@omnie46")
@RequiredArgsConstructor
public class TemporaryController {

    private final HandbookService handbookService;
    private final ITemporaryHandbookService tempHandbookService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<HandbookSummary> getHandbookItemsWithSearch(
            @ApiParam(example = "For example : 'name, asc' or just 'name', where name is field of model",
                    required = true) @Valid PageableRequest page, Pageable pageable,
            @ApiParam(defaultValue = "_", required = true) @RequestParam("search") String search) {

        return handbookService.getHandbook(pageable, search);
    }


    @GetMapping(path = "/{handbook-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public HandbookSummary getHandbook(
            @PathVariable(name = "handbook-id") UUID id,
            @ApiIgnore UserEntity userEntity) {
        return tempHandbookService.getHandbook(id);
    }

    @PostMapping
    public HandbookSummary addHandbook(
            @Valid @RequestBody SaveHandbookPl payload,
            @ApiIgnore UserEntity userEntity) {

        return tempHandbookService.save(payload);
    }

    @PutMapping(path = "/{handbook-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public HandbookSummary addHandbook(
            @PathVariable(name = "handbook-id") UUID id,
            @Valid @RequestBody UpdateHandbookPl payload,
            @ApiIgnore UserEntity userEntity) {

        return tempHandbookService.update(id, payload);
    }

    @DeleteMapping(path = "/{handbook-id}")
    public void deleteHandbook(
            @PathVariable(name = "handbook-id") UUID id,
            @ApiIgnore UserEntity userEntity) {

        tempHandbookService.deleteHandbook(id);
    }

    @PostMapping(path = "/{handbook-id}/phones/{phone}", produces = MediaType.APPLICATION_JSON_VALUE)
    public HandbookSummary addPhone(
            @PathVariable(name = "handbook-id") UUID handbookId,
            @PathVariable(name = "phone") String phone,
            @ApiIgnore UserEntity userEntity) {

        return tempHandbookService.addPhone(handbookId, phone);
    }

    @DeleteMapping(path = "/{handbook-id}/phones/{phone-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public HandbookSummary deletePhone(
            @PathVariable(name = "handbook-id") UUID handbookId,
            @PathVariable(name = "phone-id") UUID phoneId,
            @ApiIgnore UserEntity userEntity) {

        return tempHandbookService.deletePhone(handbookId, phoneId);
    }

    @PostMapping(path = "/{handbook-id}/tags/{tag}", produces = MediaType.APPLICATION_JSON_VALUE)
    public HandbookSummary addTag(
            @PathVariable(name = "handbook-id") UUID handbookId,
            @PathVariable(name = "tag") String tag,
            @ApiIgnore UserEntity userEntity) {

        return tempHandbookService.addTag(handbookId, tag);
    }

    @DeleteMapping(path = "/{handbook-id}/tags/{tag-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public HandbookSummary deleteTag(
            @PathVariable(name = "handbook-id") UUID handbookId,
            @PathVariable(name = "tag-id") UUID tagId,
            @ApiIgnore UserEntity userEntity) {

        return tempHandbookService.deleteTag(handbookId, tagId);
    }


}
