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
package org.dbflute.kvs.cache.bhv.writable;

/**
 * @author FreeGen
 */
public class InsertOrUpdateOption implements WritableOption {

    protected boolean _disableCommonColumnAutoSetup;

    protected boolean _kvsCacheAsyncReflectionEnabled = true;

    // ===============================================================================
    //                                                                   Common Column
    //                                                                   =============
    /**
     * Disable auto-setup for common columns. <br>
     * You can insert by your values for common columns.
     * <pre>
     * Member member = new Member();
     * member.setOthers...(value);
     * member.setRegisterDatetime(registerDatetime);
     * member.setRegisterUser(registerUser);
     * member.setUpdateDatetime(updateDatetime);
     * member.setUpdateUser(updateUser);
     * InsertOption&lt;MemberCB&gt; option = new InsertOption&lt;MemberCB&gt;();
     * option.<span style="color: #CC4747">disableCommonColumnAutoSetup</span>();
     * memberBhv.varyingInsert(member, option);
     * </pre>
     * @return The option of insert. (NotNull: returns this)
     */
    public InsertOrUpdateOption disableCommonColumnAutoSetup() {
        _disableCommonColumnAutoSetup = true;
        return this;
    }

    public boolean isCommonColumnAutoSetupDisabled() {
        return _disableCommonColumnAutoSetup;
    }

    public void enableKvsCacheAsyncReflection() {
        _kvsCacheAsyncReflectionEnabled = true;
    }

    public void disableKvsCacheAsyncReflection() {
        _kvsCacheAsyncReflectionEnabled = false;
    }

    public boolean isKvsCacheAsyncReflectionEnabled() {
        return _kvsCacheAsyncReflectionEnabled;
    }
}
