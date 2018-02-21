package com.omniesoft.commerce.owner.controller.conversation;

import com.omniesoft.commerce.common.request.PageableRequest;
import com.omniesoft.commerce.common.responce.ResponseMessage;
import com.omniesoft.commerce.owner.controller.AbstractOrganizationController;
import com.omniesoft.commerce.owner.controller.conversation.payload.MessagePayload;
import com.omniesoft.commerce.owner.service.conversation.ConversationService;
import com.omniesoft.commerce.owner.service.conversation.MessageService;
import com.omniesoft.commerce.owner.service.organization.OwnerAccessControlService;
import com.omniesoft.commerce.persistence.entity.account.UserEntity;
import com.omniesoft.commerce.persistence.entity.enums.AdminPermission;
import com.omniesoft.commerce.persistence.projection.conversation.ConversationSummary;
import com.omniesoft.commerce.persistence.projection.conversation.MessageSummary;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@AllArgsConstructor
@Api(value = "Conversation", tags = "Conversation API", description = "@limpid")
public class ConversationController extends AbstractOrganizationController {

    private final ConversationService conversationService;
    private final MessageService messageService;
    private final OwnerAccessControlService ownerAccessControlService;

    private final OwnerAccessControlService accessControl;

    @GetMapping(path = "/{organization-id}/conversations")
    public Page<ConversationSummary> getOrganizationConversationList(
            @Valid PageableRequest pageableRequest, Pageable pageable,
            @PathVariable("organization-id") UUID organizationId,
            @ApiParam(defaultValue = "_", required = true) @RequestParam("search") String searchPattern,
            @ApiIgnore UserEntity userEntity
    ) {

        return conversationService.findOrganizationConversations(organizationId, searchPattern, userEntity, pageable);
    }

    @PostMapping(path = "/{organization-id}/conversations")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseMessage createConversation(
            @RequestParam("user") UUID userId,
            @ApiIgnore UserEntity userEntity,
            @PathVariable("organization-id") UUID organizationId
    ) {
        ownerAccessControlService.hasPermissionByOrganization(userEntity, organizationId, AdminPermission.CONVERSATION_MANAGEMENT);
        UUID conversation = conversationService.createConversation(organizationId, userId, userEntity);
        return new ResponseMessage(conversation.toString());
    }

    @GetMapping(path = "/{organization-id}/conversations/{conversation-id}/messages")
    public Page<MessageSummary> getMessageByConversation(
            @PathVariable("organization-id") UUID organization,
            @PathVariable("conversation-id") UUID conversation,
            @ApiIgnore UserEntity userEntity,
            @ApiParam(defaultValue = "_", required = true) @RequestParam("search") String searchPattern,
            @Valid PageableRequest pageableRequest, Pageable pageable

    ) {

        return messageService
                .findMessageInConversation(searchPattern, conversation, organization, userEntity, pageable);
    }

    @GetMapping(path = "/{organization-id}/conversations/{conversation-id}/messages/last")
    public MessageSummary getLastMessageByConversation(
            @PathVariable("organization-id") UUID organization,
            @PathVariable("conversation-id") UUID conversation,
            @ApiIgnore UserEntity userEntity

    ) {

        return conversationService.fetchLastMessageInConversation(organization, conversation, userEntity);
    }


    @GetMapping(path = "/{organization-id}/conversations/{conversation-id}/messages/{message-id}/previous")
    public Page<MessageSummary> getPreviousMessagesByConversation(
            @PathVariable("organization-id") UUID organization,
            @PathVariable("conversation-id") UUID conversation,
            @PathVariable("message-id") UUID message,
            @ApiIgnore UserEntity userEntity,
            @Valid PageableRequest pageableRequest, Pageable pageable

    ) {

        return messageService.findPreviousMessages(organization, message, conversation, userEntity, pageable);
    }

    @GetMapping(path = "/{organization-id}/conversations/{conversation-id}/messages/{message-id}/next")
    public Page<MessageSummary> getNextMessagesByConversation(
            @PathVariable("organization-id") UUID organization,
            @PathVariable("conversation-id") UUID conversation,
            @PathVariable("message-id") UUID message,
            @ApiIgnore UserEntity userEntity,
            @Valid PageableRequest pageableRequest, Pageable pageable

    ) {

        return messageService.findNextMessages(organization, message, conversation, userEntity, pageable);
    }

    @PostMapping(path = "/{organization-id}/conversations/{conversation-id}/messages")
    @ResponseStatus(HttpStatus.CREATED)
    public MessagePayload postMessage(
            @PathVariable("organization-id") UUID organizationId,
            @PathVariable("conversation-id") UUID conversation,
            @ApiParam(required = true, defaultValue = "Type text here")
            @RequestParam(value = "message", defaultValue = "Message") String messagePayload,
            @ApiIgnore UserEntity userEntity
    ) {

        ownerAccessControlService.hasPermissionByOrganization(userEntity, organizationId, AdminPermission.CONVERSATION_MANAGEMENT);
        return messageService.postMessageToConversation(
                organizationId, conversation, messagePayload, userEntity
        );
    }

    @PatchMapping(path = "/{organization-id}/conversations/{conversation-id}/messages")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void readMessage(
            @PathVariable("organization-id") UUID organizationId,
            @PathVariable("conversation-id") UUID conversationId,
            @RequestParam(value = "last_message_id") UUID messageId,
            @ApiIgnore UserEntity userEntity
    ) {
        ownerAccessControlService.hasPermissionByOrganization(userEntity, organizationId, AdminPermission.CONVERSATION_MANAGEMENT);
        messageService.readMessage(organizationId, messageId, conversationId, userEntity);
    }


}
