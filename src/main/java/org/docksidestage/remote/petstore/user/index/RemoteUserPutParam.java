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
package org.docksidestage.remote.petstore.user.index;

import org.lastaflute.core.util.Lato;

/**
 * The bean class as param for remote API of PUT /user/{username}.
 * @author FreeGen
 */
public class RemoteUserPutParam {

    /** The property of id. (NullAllowed) */
    public Long id;

    /** The property of username. (NullAllowed) */
    public String username;

    /** The property of firstName. (NullAllowed) */
    public String firstName;

    /** The property of lastName. (NullAllowed) */
    public String lastName;

    /** The property of email. (NullAllowed) */
    public String email;

    /** The property of password. (NullAllowed) */
    public String password;

    /** The property of phone. (NullAllowed) */
    public String phone;

    /** The property of userStatus. (User Status) (NullAllowed) */
    public Integer userStatus;

    @Override
    public String toString() {
        return Lato.string(this);
    }
}
