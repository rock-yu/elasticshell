/*
 * Licensed to Luca Cavanna (the "Author") under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. Elastic Search licenses this
 * file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.elasticsearch.shell.client.builders.indices;

import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.admin.indices.status.IndicesStatusRequest;
import org.elasticsearch.action.admin.indices.status.IndicesStatusResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.ToXContent;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.shell.client.builders.AbstractRequestBuilderJsonOutput;
import org.elasticsearch.shell.json.JsonToString;
import org.elasticsearch.shell.json.StringToJson;

import java.io.IOException;

import static org.elasticsearch.rest.action.support.RestActions.buildBroadcastShardsHeader;

/**
 * @author Luca Cavanna
 *
 * Request builder for status API
 */
@SuppressWarnings("unused")
public class StatusRequestBuilder<JsonInput, JsonOutput> extends AbstractRequestBuilderJsonOutput<IndicesStatusRequest, IndicesStatusResponse, JsonInput, JsonOutput> {

    public StatusRequestBuilder(Client client, JsonToString<JsonInput> jsonToString, StringToJson<JsonOutput> stringToJson) {
        super(client, new IndicesStatusRequest(), jsonToString, stringToJson);
    }

    public StatusRequestBuilder<JsonInput, JsonOutput> indices(String... indices) {
        request.indices(indices);
        return this;
    }

    public StatusRequestBuilder<JsonInput, JsonOutput> recovery(boolean recovery) {
        request.recovery(recovery);
        return this;
    }

    public StatusRequestBuilder<JsonInput, JsonOutput> snapshot(boolean snapshot) {
        request.snapshot(snapshot);
        return this;
    }

    @Override
    protected ActionFuture<IndicesStatusResponse> doExecute(IndicesStatusRequest request) {
        return client.admin().indices().status(request);
    }

    @Override
    protected XContentBuilder toXContent(IndicesStatusRequest request, IndicesStatusResponse response, XContentBuilder builder) throws IOException {
        builder.startObject();
        builder.field(Fields.OK, true);
        buildBroadcastShardsHeader(builder, response);
        response.toXContent(builder, ToXContent.EMPTY_PARAMS);
        builder.endObject();
        return builder;
    }
}
