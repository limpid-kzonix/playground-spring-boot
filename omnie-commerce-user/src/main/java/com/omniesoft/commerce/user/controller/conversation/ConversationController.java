package com.omniesoft.commerce.user.controller.conversation;

import com.omniesoft.commerce.common.request.PageableRequest;
import com.omniesoft.commerce.common.responce.ResponseMessage;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.projection.conversation.ConversationSummary;
import com.omniesoft.commerce.persistence.projection.conversation.MessageSummary;
import com.omniesoft.commerce.user.controller.conversation.payload.MessagePayload;
import com.omniesoft.commerce.user.service.conversation.ConversationService;
import com.omniesoft.commerce.user.service.conversation.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
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
@RequestMapping(value = "/conversations", consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@Api(value = "Convesations", tags = "Conversation / Messages Controller", description = "@limpid")
@AllArgsConstructor
public class ConversationController {

    private final ConversationService conversationService;
    private final MessageService messageService;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ConversationSummary> getUsersConversations(
            @Valid PageableRequest pageableRequest, Pageable pageable,
            @ApiIgnore UserEntity userEntity,
            @ApiParam(defaultValue = "_", required = true)
            @RequestParam(value = "search", defaultValue = "_") String searchPattern
    ) {
        return conversationService.findUsersConversations(searchPattern, userEntity, pageable);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseMessage.Created createConversation(
            @RequestParam("organization_id") UUID organization,
            @ApiIgnore UserEntity userEntity
    ) {
        UUID conversation = conversationService.createConversation(organization, userEntity);
        return new ResponseMessage.Created(conversation);
    }

    @GetMapping(path = "/{conversation-id}/messages")
    public Page<MessageSummary> getMessagesByConversation(
            @Valid PageableRequest pageableRequest, Pageable pageable,
            @ApiIgnore UserEntity userEntity,
            @PathVariable("id") UUID id,
            @ApiParam(defaultValue = "_", required = true) @RequestParam("search") String searchPattern
    ) {
        return messageService.findMessagesByConversation(id, searchPattern, pageable, userEntity);
    }

    @GetMapping(path = "/{conversation-id}/messages/{message-id}/previous")
    public Page<MessageSummary> getPreviousMessagesByConversation(
            @PathVariable("conversation-id") UUID conversation,
            @PathVariable("message-id") UUID message,
            @ApiIgnore UserEntity userEntity,
            @Valid PageableRequest pageableRequest, Pageable pageable

    ) {
        return messageService.findPreviousMessages(message, conversation, userEntity, pageable);
    }

    @GetMapping(path = "/{conversation-id}/messages/{message-id}/next")
    public Page<MessageSummary> getNextMessagesByConversation(
            @PathVariable("conversation-id") UUID conversation,
            @PathVariable("message-id") UUID message,
            @ApiIgnore UserEntity userEntity,
            @Valid PageableRequest pageableRequest, Pageable pageable

    ) {
        return messageService.findNextMessages(message, conversation, userEntity, pageable);
    }


    @GetMapping(path = "/{conversation-id}/messages/last")
    public MessageSummary getLastMessageByConversation(
            @PathVariable("conversation-id") UUID conversation,
            @ApiIgnore UserEntity userEntity

    ) {

        return conversationService.fetchLastMessageInConversation(conversation, userEntity);
    }

    @PostMapping("/{conversation-id}/messages")
    @ResponseStatus(HttpStatus.CREATED)
    public MessagePayload postMessageToOrganization(
            @PathVariable("conversation-id") UUID conversation,
            @ApiParam(required = true, defaultValue = "Type text here")
            @RequestParam(value = "message", defaultValue = "Message") String messagePayload,
            @ApiIgnore UserEntity userEntity
    ) {
        return messageService.postMessageToConversation(conversation, messagePayload, userEntity);
    }

    @PatchMapping("/{conversation-id}/messages")
    public void readConversation(
            @PathVariable("conversation-id") UUID conversationId,
            @ApiIgnore UserEntity userEntity
    ) {
        messageService.readMessagesInConversation(conversationId, userEntity);
    }
}
