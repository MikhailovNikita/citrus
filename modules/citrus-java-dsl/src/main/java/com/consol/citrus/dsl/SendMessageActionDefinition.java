/*
 * Copyright 2006-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.consol.citrus.dsl;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.XmlMappingException;
import org.springframework.xml.transform.StringResult;

import com.consol.citrus.actions.SendMessageAction;
import com.consol.citrus.exceptions.CitrusRuntimeException;
import com.consol.citrus.message.MessageSender;
import com.consol.citrus.validation.builder.AbstractMessageContentBuilder;
import com.consol.citrus.validation.builder.PayloadTemplateMessageBuilder;

/**
 *
 * @author Christoph Deppisch
 */
public class SendMessageActionDefinition extends AbstractActionDefinition<SendMessageAction> {

    /**
     * Default constructor with test action.
     * @param action
     */
    public SendMessageActionDefinition(SendMessageAction action) {
        super(action);
    }
    
    /**
     * Adds message sender reference to this definitions test action.
     * @param messageSender
     * @return
     */
    public SendMessageActionDefinition with(MessageSender messageSender) {
        action.setMessageSender(messageSender);
        return this;
    }
    
    /**
     * Adds message payload data to this definition.
     * @param payload
     * @return
     */
    public SendMessageActionDefinition payload(String payload) {
        if (action.getMessageBuilder() != null && action.getMessageBuilder() instanceof PayloadTemplateMessageBuilder) {
            ((PayloadTemplateMessageBuilder)action.getMessageBuilder()).setPayloadData(payload);
        } else {
            PayloadTemplateMessageBuilder messageBuilder = new PayloadTemplateMessageBuilder();
            messageBuilder.setPayloadData(payload);
            
            action.setMessageBuilder(messageBuilder);
        }
        
        return this;
    }
    
    /**
     * Adds message payload resource to this definition.
     * @param payload
     * @return
     */
    public SendMessageActionDefinition payload(Resource payloadResource) {
        if (action.getMessageBuilder() != null && action.getMessageBuilder() instanceof PayloadTemplateMessageBuilder) {
            ((PayloadTemplateMessageBuilder)action.getMessageBuilder()).setPayloadResource(payloadResource);
        } else {
            PayloadTemplateMessageBuilder messageBuilder = new PayloadTemplateMessageBuilder();
            messageBuilder.setPayloadResource(payloadResource);
            
            action.setMessageBuilder(messageBuilder);
        }
        
        return this;
    }
    
    public SendMessageActionDefinition payload(Object payload, Marshaller marshaller) {
        StringResult result = new StringResult();
        
        try {
            marshaller.marshal(payload, result);
        } catch (XmlMappingException e) {
            throw new CitrusRuntimeException("Failed to marshal object graph for message payload", e);
        } catch (IOException e) {
            throw new CitrusRuntimeException("Failed to marshal object graph for message payload", e);
        }
        
        if (action.getMessageBuilder() != null && action.getMessageBuilder() instanceof PayloadTemplateMessageBuilder) {
            ((PayloadTemplateMessageBuilder)action.getMessageBuilder()).setPayloadData(result.toString());
        } else {
            PayloadTemplateMessageBuilder messageBuilder = new PayloadTemplateMessageBuilder();
            messageBuilder.setPayloadData(result.toString());
            
            action.setMessageBuilder(messageBuilder);
        }
        
        return this;
    }

    /**
     * Adds message header to this definition's message sending action.
     * @param name
     * @param value
     */
    public SendMessageActionDefinition header(String name, Object value) {
        if (action.getMessageBuilder() != null && action.getMessageBuilder() instanceof AbstractMessageContentBuilder<?>) {
            ((AbstractMessageContentBuilder<?>)action.getMessageBuilder()).getMessageHeaders().put(name, value);
        } else {
            PayloadTemplateMessageBuilder messageBuilder = new PayloadTemplateMessageBuilder();
            messageBuilder.getMessageHeaders().put(name, value);
            
            action.setMessageBuilder(messageBuilder);
        }
        
        return this;
    }

}