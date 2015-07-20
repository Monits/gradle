/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.platform.base.internal.registry;

import com.google.common.base.Function;
import org.gradle.model.internal.core.NodeInitializer;
import org.gradle.model.internal.manage.schema.ModelProperty;
import org.gradle.model.internal.manage.schema.ModelSchema;
import org.gradle.model.internal.manage.schema.ModelSchemaStore;
import org.gradle.model.internal.manage.schema.ModelStructSchema;
import org.gradle.model.internal.manage.schema.extract.ManagedImplTypeSchemaExtractionStrategySupport;
import org.gradle.model.internal.manage.schema.extract.ModelSchemaExtractionContext;
import org.gradle.model.internal.type.ModelType;
import org.gradle.platform.base.BinarySpec;
import org.gradle.platform.base.internal.BinarySpecInternal;

import java.lang.reflect.Method;

// Needed as a separate Java class because Groovy compiler won't recognize type parameter <R>
public class BinarySpecSpecializationSchemaExtractionStrategy extends ManagedImplTypeSchemaExtractionStrategySupport {
    @Override
    protected boolean ignoreMethod(Method method) {
        try {
            BinarySpec.class.getMethod(method.getName(), method.getParameterTypes());
            return true;
        } catch (NoSuchMethodException ignore) {
            return false;
        }
    }

    @Override
    protected <R> ModelSchema<R> createSchema(ModelSchemaExtractionContext<R> extractionContext, ModelSchemaStore store, ModelType<R> type, Iterable<ModelProperty<?>> properties, Class<R> concreteClass) {
        return ModelSchema.struct(type, properties, type.getConcreteClass(), BinarySpecInternal.class, new Function<ModelStructSchema<R>, NodeInitializer>() {
            @Override
            public NodeInitializer apply(ModelStructSchema<R> schema) {
                return null;
            }
        });
    }
}
