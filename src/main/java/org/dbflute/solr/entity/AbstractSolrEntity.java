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

import java.util.Arrays;

import org.apache.solr.client.solrj.beans.Field;
import org.dbflute.dbmeta.accessory.EntityModifiedProperties;
import org.dbflute.exception.NonSpecifiedColumnAccessException;
import org.dbflute.helper.message.ExceptionMessageBuilder;

/**
 * @author FreeGen
 */
public abstract class AbstractSolrEntity implements SolrEntity {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** The modified properties for this entity. (NotNull) */
    protected final EntityModifiedProperties __modifiedProperties = newModifiedProperties();

    /** The modified properties for this entity. (NullAllowed: normally null, created when specified-check) */
    protected EntityModifiedProperties __specifiedProperties;

    /** Is the entity created by DBFlute select process? */
    protected boolean __createdBySelect;

    /** score. */
    @Field("score")
    protected Float score;

    // ===================================================================================
    //                                                                           Solr Meta
    //                                                                           =========
    // -----------------------------------------------------
    //                                             Specified
    //                                             ---------
    @Override
    public void modifiedToSpecified(String[] specifyPropertys) {
        __specifiedProperties = newModifiedProperties();
        if (!__modifiedProperties.isEmpty()) {
            __specifiedProperties.accept(__modifiedProperties);
        }
        // because null is not reflected in solrj
        if (specifyPropertys != null) {
            Arrays.stream(specifyPropertys).forEach(specifyProperty -> {
                if (!__specifiedProperties.isModifiedProperty(specifyProperty)) {
                    __specifiedProperties.addPropertyName(specifyProperty);
                }
            });
        }
    }

    protected void registerModifiedProperty(String propertyName) {
        __modifiedProperties.addPropertyName(propertyName);
        registerSpecifiedProperty(propertyName); // synchronize if exists, basically for user's manual call
    }

    protected EntityModifiedProperties newModifiedProperties() {
        return new EntityModifiedProperties();
    }

    protected void checkSpecifiedProperty(String propertyName) {
        if (__specifiedProperties == null) { // means no need to check (e.g. all columns are selected)
            return;
        }
        if (!createdBySelect()) { // basically no way, selected when the properties exists but just in case
            return;
        }
        // SpecifyColumn is used for the entity
        if (__specifiedProperties.isModifiedProperty(propertyName)) { // yes, setter is called when select mapping
            return; // OK, OK
        }
        // no, you get the non-specified column, throws exception
        throwNonSpecifiedColumnAccessException(this, propertyName, __specifiedProperties);
    }

    protected void registerSpecifiedProperty(String propertyName) { // basically called by modified property registration
        if (__specifiedProperties != null) { // normally false, true if e.g. setting after selected
            __specifiedProperties.addPropertyName(propertyName);
        }
    }

    // XXX p1us2er0 例外メッセージを整理する。 (2015/05/31)
    protected static void throwNonSpecifiedColumnAccessException(SolrEntity entity, String propertyName,
            EntityModifiedProperties specifiedProperties) {
        final ExceptionMessageBuilder br = new ExceptionMessageBuilder();
        br.addNotice("Non-specified column was accessed.");
        br.addItem("Advice");
        br.addElement("To get non-specified column from entity is not allowd.");
        br.addElement("Non-specified means using SpecifyColumn but the column is non-specified.");
        br.addElement("Mistake? Or specify the column by your condition-bean if you need it.");
        br.addElement("For example:");
        br.addElement("  (x):");
        br.addElement("    memberBhv.selectPage(cb -> {");
        br.addElement("        cb.specify().columnMemberName();");
        br.addElement("        cb.query().set...");
        br.addElement("    }).alwaysPresent(member -> {");
        br.addElement("        ... = member.columnMemberName(); // OK");
        br.addElement("        ... = member.getDisplayOrder(); // *NG: exception");
        br.addElement("    });");
        br.addElement("  (o):");
        br.addElement("    memberBhv.selectPage(cb -> {");
        br.addElement("        cb.specify().columnMemberStatusName();");
        br.addElement("        cb.specify().columnDisplayOrder(); // *Point");
        br.addElement("        cb.query().set...");
        br.addElement("    }).alwaysPresent(member -> {");
        br.addElement("        ... = member.columnMemberName(); // OK");
        br.addElement("        ... = member.getDisplayOrder(); // OK");
        br.addElement("    });");
        br.addElement("");
        br.addElement("While, reluctantly you need to get the column without change conditions,");
        br.addElement("you can enable non-specified column access by the condition-bean option.");
        br.addElement("The method is cb.enable...()");
        buildExceptionTableInfo(br, entity);
        br.addItem("Non-Specified and Accessed");
        br.addElement(propertyName);
        br.addItem("Specified Column in the Table");
        br.addElement(specifiedProperties);
        final String msg = br.buildExceptionMessage();
        throw new NonSpecifiedColumnAccessException(msg);
    }

    protected static void buildExceptionTableInfo(ExceptionMessageBuilder br, SolrEntity entity) {
        br.addItem("Schema");
        br.addElement(entity.asSchemaName());
    }

    // ===================================================================================
    //                                                                     Birthplace Mark
    //                                                                     ===============
    @Override
    public void markAsSelect() {
        __createdBySelect = true;
    }

    @Override
    public boolean createdBySelect() {
        return __createdBySelect;
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * Get the value of score.
     * @return score. (NullAllowed)
     */
    public Float getScore() {
        checkSpecifiedProperty("score");
        return score;
    }

    /**
     * Set the value of score.
     * @param score score. (NullAllowed)
     */
    public void setScore(Float score) {
        registerModifiedProperty("score");
        this.score = score;
    }
}
