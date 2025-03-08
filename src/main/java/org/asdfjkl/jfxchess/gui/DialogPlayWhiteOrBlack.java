/* JerryFX - A Chess Graphical User Interface
 * Copyright (C) 2020 Dominik Klein
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package org.asdfjkl.jfxchess.gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;

import java.util.function.DoubleFunction;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class DialogPlayWhiteOrBlack {

    Stage stage;
    boolean accepted = false;

    final Slider sliderStrength = new Slider();
    final Slider sliderThinkTime = new Slider();

    int thinkTime = 3;
    int strength = 0;

    ToggleButton tglLimitStrength = new ToggleButton("Off");

    public boolean show(Engine activeEngine,
                        int currThinkTime,
                        int colorTheme) {

        int minElo = 1200;
        int maxElo = 3000;
        if(activeEngine.supportsUciLimitStrength()) {
            strength = activeEngine.getUciElo();
            minElo = activeEngine.getMinUciElo();
            maxElo = activeEngine.getMaxUciElo();
        }


        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        Button btnOk = new Button();
        btnOk.setText("OK");

        Button btnCancel = new Button();
        btnCancel.setText("Cancel");
        Region spacer = new Region();

        HBox hbButtons = new HBox();
        hbButtons.getChildren().addAll(spacer, btnOk, btnCancel);
        hbButtons.setHgrow(spacer, Priority.ALWAYS);
        hbButtons.setSpacing(10);

        sliderStrength.setMin(minElo);
        sliderStrength.setMax(maxElo);

        sliderStrength.setBlockIncrement(1);
        sliderStrength.setMajorTickUnit(1);
        sliderStrength.setMinorTickCount(0);
        sliderStrength.setSnapToTicks(true);

        Label txtStrength = new Label();
        HBox hboxStrength = new HBox();
        hboxStrength.getChildren().addAll(sliderStrength, txtStrength);
        hboxStrength.setSpacing(20);

        int tmpStrength = strength;
        sliderStrength.valueProperty().addListener(
                ((observableValue, number, t1) -> {
                    strength = t1.intValue();;
                    txtStrength.setText("Elo "+ strength);
                })
        );
        
        sliderStrength.setValue(3000);
        sliderStrength.setValue(tmpStrength);
        sliderStrength.setStyle("-show-value-on-interaction: false;");
        
        sliderThinkTime.setMin(1);
        sliderThinkTime.setMax(7);

        sliderThinkTime.setBlockIncrement(1);
        sliderThinkTime.setMajorTickUnit(1);
        sliderThinkTime.setMinorTickCount(0);
        sliderThinkTime.setSnapToTicks(true);

        Label txtThinkTime = new Label();
        HBox hboxthinkTime = new HBox();
        hboxthinkTime.getChildren().addAll(sliderThinkTime, txtThinkTime);
        hboxthinkTime.setSpacing(20);

        sliderThinkTime.valueProperty().addListener(
                ((observableValue, number, t1) -> {
                    int i = t1.intValue();
                    switch(i) {
                        case 1:
                            thinkTime = 1;
                            break;
                        case 2:
                            thinkTime = 2;
                            break;
                        case 3:
                            thinkTime = 3;
                            break;
                        case 4:
                            thinkTime = 5;
                            break;
                        case 5:
                            thinkTime = 10;
                            break;
                        case 6:
                            thinkTime = 15;
                            break;
                        case 7:
                            thinkTime = 30;
                            break;
                    }
                    txtThinkTime.setText(thinkTime + " sec(s)");
                })
        );

        sliderThinkTime.setStyle("-show-value-on-interaction: false;");
        if(currThinkTime <= 3) {
            sliderThinkTime.setValue(3);
            sliderThinkTime.setValue(currThinkTime);
        }
        if(currThinkTime == 5) {
            sliderThinkTime.setValue(4);
        }
        if(currThinkTime == 10) {
            sliderThinkTime.setValue(5);
        }
        if(currThinkTime == 15) {
            sliderThinkTime.setValue(6);
        }
        if(currThinkTime == 30) {
            sliderThinkTime.setValue(7);
        }
        
	//Font arialFontBold36  = Font.font("Arial", FontWeight.BOLD, 10);
        //tglLimitStrength.setFont(arialFontBold10);
        
        HBox hbStrength = new HBox();
        hbStrength.getChildren().addAll(new Label("Limit Computer Strength: "), tglLimitStrength);
        hbStrength.setSpacing(5);
        VBox vbox = new VBox();
        vbox.getChildren().addAll(
                 hbStrength,
                hboxStrength,
                new Label("Computer's Time per Move"),
                hboxthinkTime,
                hbButtons);
        vbox.setSpacing(10);
        vbox.setPadding( new Insets(15, 25, 10, 25));

        tglLimitStrength.setOnAction(actionEvent -> {
            tglLimitStrength.setText(tglLimitStrength.isSelected()? "On" : "Off");
            hboxStrength.setDisable(!tglLimitStrength.isSelected());
        });
        
        hboxStrength.setDisable(!activeEngine.supportsUciLimitStrength());
        tglLimitStrength.setDisable(!activeEngine.supportsUciLimitStrength());
        tglLimitStrength.setSelected(activeEngine.getUciLimitStrength());
        tglLimitStrength.setText(activeEngine.getUciLimitStrength()? "On" : "Off");
        if (!activeEngine.supportsUciLimitStrength()) {
            txtStrength.setText("N.A.");
        }

        btnOk.setOnAction(e -> {
            btnOkClicked();
        });

        btnCancel.setOnAction(e -> {
            btnCancelClicked();
        });

        vbox.getStyleClass().add(JMetroStyleClass.BACKGROUND);
        Scene scene = new Scene(vbox);
        JMetro jMetro;
        if(colorTheme == GameModel.STYLE_LIGHT) {
            jMetro = new JMetro();
        } else {
            jMetro = new JMetro(Style.DARK);
        }
        jMetro.setScene(scene);

        stage.setScene(scene);
        stage.getIcons().add(new Image("icons/app_icon.png"));

        stage.showAndWait();

        return accepted;
    }

    private void btnOkClicked() {
        accepted = true;
        stage.close();
    }

    private void btnCancelClicked() {
        accepted = false;
        stage.close();
    }
    
    public boolean limitStrength() {
        return tglLimitStrength.isSelected();
    }

}


