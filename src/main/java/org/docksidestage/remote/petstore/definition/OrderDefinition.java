/*
 * Copyright 2015-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.docksidestage.remote.petstore.definition;

import javax.xml.bind.annotation.XmlElement;

import org.lastaflute.core.util.Lato;

/**
 * The bean class as Order for remote API of definition.
 * @author FreeGen
 */
public class OrderDefinition {

    /** The property of id. (NullAllowed) */
    @XmlElement(name = "id")
    public Long id;

    /** The property of petId. (NullAllowed) */
    @XmlElement(name = "petId")
    public Long petId;

    /** The property of quantity. (NullAllowed) */
    @XmlElement(name = "quantity")
    public Integer quantity;

    /** The property of shipDate. (NullAllowed) */
    @XmlElement(name = "shipDate")
    public java.time.LocalDateTime shipDate;

    /** The property of status. (enumValue=[placed, approved, delivered]) (Order Status) (NullAllowed) */
    @XmlElement(name = "status")
    public String status;

    /** The property of complete. (NullAllowed) */
    @XmlElement(name = "complete")
    public Boolean complete;

    @Override
    public String toString() {
        return Lato.string(this);
    }
}
