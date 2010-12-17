/*
 * Copyright 2006-2010 the original author or authors.
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

package com.consol.citrus.test.demo.ws;

import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.endpoint.MessageEndpoint;

import com.consol.citrus.exceptions.CitrusRuntimeException;

/**
 * @author Christoph Deppisch
 */
public class SoapFaultGeneratingEndpoint implements MessageEndpoint {

	public void invoke(MessageContext messageContext) throws Exception {
		throw new CitrusRuntimeException();
	}
}