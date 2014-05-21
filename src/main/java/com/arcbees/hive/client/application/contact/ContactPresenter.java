/*
 * Copyright 2014 ArcBees Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.arcbees.hive.client.application.contact;

import javax.inject.Inject;

import com.arcbees.hive.client.application.common.AppPresenter;
import com.arcbees.hive.client.application.common.event.ResizeEvent;
import com.arcbees.hive.client.dispatch.AsyncCallbackImpl;
import com.arcbees.hive.client.place.NameTokens;
import com.arcbees.hive.shared.NoResult;
import com.arcbees.hive.shared.dispatch.SendMail;
import com.google.gwt.user.client.Window;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

public class ContactPresenter extends
        Presenter<ContactPresenter.MyView, ContactPresenter.MyProxy> implements ContactUiHandlers {
    @ProxyStandard
    @NameToken(NameTokens.contact)
    interface MyProxy extends ProxyPlace<ContactPresenter> {
    }

    interface MyView extends View, HasUiHandlers<ContactUiHandlers> {
    }

    private final DispatchAsync dispatcher;

    @Inject
    public ContactPresenter(EventBus eventBus, MyView view, MyProxy proxy, DispatchAsync dispatcher) {
        super(eventBus, view, proxy, AppPresenter.SLOT_SetMainContent);

        this.dispatcher = dispatcher;

        getView().setUiHandlers(this);
    }

    @Override
    protected void onReveal() {
        super.onReveal();

        ResizeEvent.fire(this, AppPresenter.SLOT_SetMainContent,
                getView().asWidget().getOffsetHeight());
    }

    @Override
    public void sendMail(String senderEmail, String senderName, String contents) {
        dispatcher.execute(new SendMail(senderEmail, senderName, contents), new AsyncCallbackImpl<NoResult>() {
            @Override
            public void onSuccess(NoResult result) {
                Window.alert("Message sent successfully!");
            }
        });
    }
}
