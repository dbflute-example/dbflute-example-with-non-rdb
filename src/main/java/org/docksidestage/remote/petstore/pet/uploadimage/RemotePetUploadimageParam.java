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
package org.docksidestage.remote.petstore.pet.uploadimage;

import org.lastaflute.core.util.Lato;

/**
 * The bean class as param for remote API of POST /pet/{petId}/uploadImage.
 * @author FreeGen
 */
public class RemotePetUploadimageParam {

    /** The property of additionalMetadata. (Additional data to pass to server) (NullAllowed) */
    public String additionalMetadata;

    /** The property of file. (file to upload) (NullAllowed) */
    public org.lastaflute.web.ruts.multipart.MultipartFormFile file;

    @Override
    public String toString() {
        return Lato.string(this);
    }
}
