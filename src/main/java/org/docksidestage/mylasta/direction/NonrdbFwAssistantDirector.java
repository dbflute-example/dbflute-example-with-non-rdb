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
package org.docksidestage.mylasta.direction;

import javax.annotation.Resource;

import org.docksidestage.mylasta.direction.sponsor.NonrdbActionAdjustmentProvider;
import org.docksidestage.mylasta.direction.sponsor.NonrdbApiFailureHook;
import org.docksidestage.mylasta.direction.sponsor.NonrdbCookieResourceProvider;
import org.docksidestage.mylasta.direction.sponsor.NonrdbCurtainBeforeHook;
import org.docksidestage.mylasta.direction.sponsor.NonrdbJsonResourceProvider;
import org.docksidestage.mylasta.direction.sponsor.NonrdbListedClassificationProvider;
import org.docksidestage.mylasta.direction.sponsor.NonrdbMailDeliveryDepartmentCreator;
import org.docksidestage.mylasta.direction.sponsor.NonrdbSecurityResourceProvider;
import org.docksidestage.mylasta.direction.sponsor.NonrdbTimeResourceProvider;
import org.docksidestage.mylasta.direction.sponsor.NonrdbUserLocaleProcessProvider;
import org.docksidestage.mylasta.direction.sponsor.NonrdbUserTimeZoneProcessProvider;
import org.lastaflute.core.direction.CachedFwAssistantDirector;
import org.lastaflute.core.direction.FwAssistDirection;
import org.lastaflute.core.direction.FwCoreDirection;
import org.lastaflute.core.security.InvertibleCryptographer;
import org.lastaflute.core.security.OneWayCryptographer;
import org.lastaflute.db.dbflute.classification.ListedClassificationProvider;
import org.lastaflute.db.direction.FwDbDirection;
import org.lastaflute.thymeleaf.ThymeleafRenderingProvider;
import org.lastaflute.web.direction.FwWebDirection;
import org.lastaflute.web.ruts.renderer.HtmlRenderingProvider;
import org.lastaflute.web.servlet.filter.cors.CorsHook;

/**
 * @author jflute
 */
public class NonrdbFwAssistantDirector extends CachedFwAssistantDirector {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Resource
    private NonrdbConfig config;

    // ===================================================================================
    //                                                                              Assist
    //                                                                              ======
    @Override
    protected void prepareAssistDirection(FwAssistDirection direction) {
        direction.directConfig(nameList -> nameList.add("nonrdb_config.properties"), "nonrdb_env.properties");
    }

    // ===================================================================================
    //                                                                               Core
    //                                                                              ======
    @Override
    protected void prepareCoreDirection(FwCoreDirection direction) {
        // this configuration is on nonrdb_env.properties because this is true only when development
        direction.directDevelopmentHere(config.isDevelopmentHere());

        // titles of the application for logging are from configurations
        direction.directLoggingTitle(config.getDomainTitle(), config.getEnvironmentTitle());

        // this configuration is on sea_env.properties because it has no influence to production
        // even if you set true manually and forget to set false back
        direction.directFrameworkDebug(config.isFrameworkDebug()); // basically false

        // you can add your own process when your application is booting
        direction.directCurtainBefore(createCurtainBeforeHook());

        direction.directSecurity(createSecurityResourceProvider());
        direction.directTime(createTimeResourceProvider());
        direction.directJson(createJsonResourceProvider());
        direction.directMail(createMailDeliveryDepartmentCreator().create());
    }

    protected NonrdbCurtainBeforeHook createCurtainBeforeHook() {
        return new NonrdbCurtainBeforeHook();
    }

    protected NonrdbSecurityResourceProvider createSecurityResourceProvider() { // #change_it_first
        final InvertibleCryptographer inver = InvertibleCryptographer.createAesCipher("nonrdb:dockside:");
        final OneWayCryptographer oneWay = OneWayCryptographer.createSha256Cryptographer();
        return new NonrdbSecurityResourceProvider(inver, oneWay);
    }

    protected NonrdbTimeResourceProvider createTimeResourceProvider() {
        return new NonrdbTimeResourceProvider(config);
    }

    protected NonrdbJsonResourceProvider createJsonResourceProvider() {
        return new NonrdbJsonResourceProvider();
    }

    protected NonrdbMailDeliveryDepartmentCreator createMailDeliveryDepartmentCreator() {
        return new NonrdbMailDeliveryDepartmentCreator(config);
    }

    // ===================================================================================
    //                                                                                 DB
    //                                                                                ====
    @Override
    protected void prepareDbDirection(FwDbDirection direction) {
        direction.directClassification(createListedClassificationProvider());
    }

    protected ListedClassificationProvider createListedClassificationProvider() {
        return new NonrdbListedClassificationProvider();
    }

    // ===================================================================================
    //                                                                                Web
    //                                                                               =====
    @Override
    protected void prepareWebDirection(FwWebDirection direction) {
        direction.directRequest(createUserLocaleProcessProvider(), createUserTimeZoneProcessProvider());
        direction.directCookie(createCookieResourceProvider());
        direction.directAdjustment(createActionAdjustmentProvider());
        direction.directMessage(nameList -> nameList.add("nonrdb_message"), "nonrdb_label");
        direction.directApiCall(createApiFailureHook());
        direction.directCors(new CorsHook("http://localhost:5000")); // #change_it
        direction.directHtmlRendering(createHtmlRenderingProvider());
    }

    protected NonrdbUserLocaleProcessProvider createUserLocaleProcessProvider() {
        return new NonrdbUserLocaleProcessProvider();
    }

    protected NonrdbUserTimeZoneProcessProvider createUserTimeZoneProcessProvider() {
        return new NonrdbUserTimeZoneProcessProvider();
    }

    protected NonrdbCookieResourceProvider createCookieResourceProvider() { // #change_it_first
        final InvertibleCryptographer cr = InvertibleCryptographer.createAesCipher("dockside:nonrdb:");
        return new NonrdbCookieResourceProvider(config, cr);
    }

    protected NonrdbActionAdjustmentProvider createActionAdjustmentProvider() {
        return new NonrdbActionAdjustmentProvider();
    }

    protected NonrdbApiFailureHook createApiFailureHook() {
        return new NonrdbApiFailureHook();
    }

    protected HtmlRenderingProvider createHtmlRenderingProvider() {
        return new ThymeleafRenderingProvider().asDevelopment(config.isDevelopmentHere());
    }
}
