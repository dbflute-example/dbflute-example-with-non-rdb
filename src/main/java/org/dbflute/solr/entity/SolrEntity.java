/*
 * Copyright 2015-2022 the original author or authors.
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
package org.dbflute.solr.entity;

/**
 * @author FreeGen
 */
public interface SolrEntity {

    // ===================================================================================
    //                                                                           Solr Meta
    //                                                                           =========
    /**
     * Handle the meta as schema name, that can be identity of schema.
     * @return The (fixed) schema name of the solr. (NotNull)
     */
    String asSchemaName(); // not use 'get' for quiet

    // -----------------------------------------------------
    //                                             Specified
    //                                             ---------
    /**
     * Copy to modified properties to specified properties. <br>
     * It means non-specified columns are checked
     * @param specifyFields specifyFields
     */
    void modifiedToSpecified(String[] specifyFields);

    // ===================================================================================
    //                                                                     Birthplace Mark
    //                                                                     ===============
    /**
     * Mark as select that means the entity is created by DBFlute select process. (basically for Framework) <br>
     * e.g. determine columns of batch insert
     */
    void markAsSelect();

    /**
     * Is the entity created by DBFlute select process? (basically for Framework)
     * @return The determination, true or false.
     */
    boolean createdBySelect();
}
