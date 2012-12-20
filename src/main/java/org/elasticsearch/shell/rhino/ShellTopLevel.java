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
package org.elasticsearch.shell.rhino;

import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.Singleton;
import org.elasticsearch.common.inject.name.Named;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ImporterTopLevel;

import java.io.PrintStream;

@Singleton
public class ShellTopLevel extends ImporterTopLevel {

    private final PrintStream out;

    @Inject
    ShellTopLevel(@Named("shellOutput") PrintStream out){

        this.out = out;

        //TODO default imports (check mvel)

        Context context = Context.enter();
        try {
            initStandardObjects(context, true);
        } finally {
            Context.exit();
        }

        /*context = Context.enter();
        try {
            //TODO is there a better way to deal with dynamic properties without making everything unsealed?
            NativeJavaObject nativeJavaObject = new NativeJavaObject(this, new Test(), Test.class);
            nativeJavaObject.setPrototype(context.newObject(this));
            nativeJavaObject.getPrototype().put("property1", nativeJavaObject.getPrototype(), nativeJavaObject);
            defineProperty("test", nativeJavaObject, ScriptableObject.DONTENUM);

        } finally {
            Context.exit();
        }*/

    }
}
