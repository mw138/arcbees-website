/*
 * Copyright 2010 ArcBees Inc.
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

package com.arcbees.hive.client.application.home;

import javax.inject.Inject;

import com.arcbees.hive.client.application.home.HomePresenter.MyView;
import com.arcbees.hive.client.resource.home.HomeResources;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class HomeView extends ViewWithUiHandlers<HomeUiHandlers> implements
        MyView {
    @UiField
    Anchor btGWTP;
    @UiField
    Anchor btJukito;
    @UiField
    Anchor btGAE;

    private final HomeResources homeResources;
    public Scheduler scheduler;

    private boolean isTimerOn;

    public interface Binder extends UiBinder<Widget, HomeView> {
    }

    @Inject
    public HomeView(final Binder uiBinder,
                    HomeResources homeResources) {
        this.homeResources = homeResources;

        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void startTimer() {
        isTimerOn = true;

        Scheduler.get().scheduleFixedPeriod(new Scheduler.RepeatingCommand() {

            public boolean execute() {
                Window.alert("lol");
                return isTimerOn;
            }
        }, 6000);
    }

    @Override
    public void stopTimer() {
        isTimerOn = false;
    }


    private void setEnabled(int index) {
        disableAll();

        Anchor selected = btGWTP;
¸
        switch (index) {
            case 0:
                selected = btGWTP;
                break;
            case 2:
                selected = btJukito;
                break;
            case 1:
                selected = btGAE;
                break;
            default:
                Window.alert("wrong index: " + index);
                break;
        }

        enable(selected);
    }

    private void disableAll() {
        disableAnchor(btGWTP);
        disableAnchor(btJukito);
        disableAnchor(btGAE);
    }

    private void disableAnchor(Anchor toDisable) {
        toDisable.setStyleName(homeResources.style().sliderProductsOff());
    }

    private void enable(Anchor selected) {
        selected.setStyleName(homeResources.style().sliderProductsOn());
    }
}
