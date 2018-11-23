/*
 * Copyright 2018 MovingBlocks
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
package org.destinationsol.menu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import org.destinationsol.GameOptions;
import org.destinationsol.SolApplication;
import org.destinationsol.assets.Assets;
import org.destinationsol.common.SolColor;
import org.destinationsol.game.SaveManager;
import org.destinationsol.ui.DisplayDimensions;
import org.destinationsol.ui.SolInputManager;
import org.destinationsol.ui.SolUiBaseScreen;
import org.destinationsol.ui.UiDrawer;
import org.destinationsol.ui.responsiveUi.UiRelativeLayout;
import org.destinationsol.ui.responsiveUi.UiTextButton;
import org.destinationsol.ui.responsiveUi.UiVerticalListLayout;
import static org.destinationsol.ui.UiDrawer.UI_POSITION_BOTTOM;
import static org.destinationsol.ui.responsiveUi.UiTextButton.DEFAULT_BUTTON_PADDING;

public class NewGameScreen extends SolUiBaseScreen {
    private DisplayDimensions displayDimensions;

    private final TextureAtlas.AtlasRegion backgroundTexture;

    private UiTextButton continueButton;

    NewGameScreen(GameOptions gameOptions) {
        displayDimensions = SolApplication.displayDimensions;

        UiVerticalListLayout buttonList = new UiVerticalListLayout();

        continueButton = new UiTextButton().setDisplayName("Continue")
                .setTriggerKey(gameOptions.getKeyShoot())
                .enableSound()
                .setOnReleaseAction(uiElement -> SolApplication.getInstance().loadGame(false, null, false));
        buttonList.addElement(continueButton);

        buttonList.addElement(new UiTextButton().setDisplayName("New game")
                .setTriggerKey(gameOptions.getKeyShoot())
                .enableSound()
                .setOnReleaseAction(uiElement -> SolApplication.changeScreen(SolApplication.getMenuScreens().newShipScreen)));

        buttonList.addElement(new UiTextButton().setDisplayName("Cancel")
                .setTriggerKey(gameOptions.getKeyEscape())
                .enableSound()
                .setOnReleaseAction(uiElement -> SolApplication.changeScreen(SolApplication.getMenuScreens().mainScreen)));

        rootUiElement = new UiRelativeLayout().addElement(buttonList, UI_POSITION_BOTTOM, 0, -buttonList.getHeight() / 2 - DEFAULT_BUTTON_PADDING);

        backgroundTexture = Assets.getAtlasRegion("engine:mainMenuBg", Texture.TextureFilter.Linear);
    }

    @Override
    public void onAdd(SolApplication solApplication) {
        continueButton.setEnabled(SaveManager.hasPrevShip("prevShip.ini"));
    }

    @Override
    public boolean isCursorOnBackground(SolInputManager.InputPointer inputPointer) {
        return true;
    }

    @Override
    public void drawBackground(UiDrawer uiDrawer, SolApplication solApplication) {
        uiDrawer.draw(backgroundTexture, displayDimensions.getRatio(), 1, displayDimensions.getRatio() / 2, 0.5f, displayDimensions.getRatio() / 2, 0.5f, 0, SolColor.WHITE);
    }
}
