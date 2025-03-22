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

import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

// EngineListView which allows ordering by drag and drop.
// The fisrt listcell (index 0) is reserved for the internal
// chess engine which can't be moved around.
public class EngineListView extends ListView<Engine> {
    private static final Engine PLACEHOLDER = new Engine();
    private Engine draggingItem = null;
    private int originalIndex = -1;
    
    private Engine savedSelectedEngine;

    public EngineListView(ObservableList<Engine> items) {
        super(items);
        getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        setCellFactory( lv -> {
            return createListCell();
        });
    }

    private ListCell<Engine> createListCell() {
        ListCell<Engine> cell = new ListCell<>() {
            @Override
            protected void updateItem(Engine item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null|| item.getName() == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    if (PLACEHOLDER.equals(item)) {
                        setText("");
                    } else {
                        setText(item.getName());
                    }
                }
            }
        };

        // Set up drag detected event
        cell.setOnDragDetected(event -> {
            savedSelectedEngine = getSelectionModel().getSelectedItem();
            // only start drag operation if the cell represents one of the
            // movable engines
            if (!cell.isEmpty() && getItems().indexOf(cell.getItem()) > 0) {
                draggingItem = cell.getItem();
                Dragboard db = cell.startDragAndDrop(TransferMode.MOVE);
                System.out.println(event.getSource().getClass());
                ClipboardContent cc = new ClipboardContent();
                cc.putString(draggingItem.getName());
                db.setContent(cc);
    
                // Create a snapshot of the text and set it as the drag view
                //Text dragText = new Text(draggingItem.getName());
                SnapshotParameters params = new SnapshotParameters();
                WritableImage snapshot = cell.snapshot(params, null);

//                // Experimenting with the dragView image.
//                // I don't really like the Orange colored dragView.
//                // But this had other sideeffects.
//
//                // Setting an image view 
//                ImageView imageView = new ImageView(snapshot); 
//      
//                // Instantiating the ColorAdjust class 
//                ColorAdjust colorAdjust = new ColorAdjust(); 
//      
//                // Setting the contrast value 
//                //colorAdjust.setContrast(0.4);     
//      
//                // Setting the hue value
                  // This gave a lightblue drawImage
//                colorAdjust.setHue(-0.96);    
//      
//                // Setting the brightness value 
//                //colorAdjust.setBrightness(0.9);  
//     
//                // Setting the saturation value 
//                //colorAdjust.setSaturation(0.8);   
//      
//
//                //Applying coloradjust effect to the ImageView node 
//                imageView.setEffect(colorAdjust);
//
//                snapshot = imageView.snapshot(params, snapshot);
                 
                db.setDragView(snapshot,30, 0);

                // Insert the placeholder
                originalIndex = getItems().indexOf(cell.getItem());
                getItems().set(originalIndex, PLACEHOLDER);
                event.consume();
            }
        });
        


        // Set up drag over event.
        cell.setOnDragOver(event -> {
            // Here we also accept transfer if we are in an empty
            // cell below the valid engines (indexOf == -1), but the 
            // dragged engine will end up as last in the list if 
            // dropped there.
            if (getItems().indexOf(cell.getItem()) != 0) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
        });

        // Set up drag entered event.
        cell.setOnDragEntered(event -> {
            if (event.getDragboard().hasString()) {
                int index = getItems().indexOf(cell.getItem());
                if (index > 0) {
                    // We must remove draggingItem if it exists, 
                    // in case the dragging slipped into the internal
                    // engine and now enters back into the first
                    // moveable engine.
                    if (draggingItem != null) {
                        getItems().remove(draggingItem);
                        getSelectionModel().clearSelection();
                        refresh();
                    }
                    // Remove old PLACEHOLDER if it exists.
                    getItems().remove(PLACEHOLDER);
                    // Insert PLACEHOLDER in new position.
                    getItems().add(index, PLACEHOLDER);
                }
                if (index == 0) {
                    // Internal engine, just remove PLACEHOLDER if it
                    // exists and insert draggingItem in its original place
                    // if it doesn't already exist.
                    getItems().remove(PLACEHOLDER);
                    if (draggingItem != null && !getItems().contains(draggingItem)) {
                        getItems().add(originalIndex, draggingItem);
                        getSelectionModel().clearSelection();
                        refresh();
                    }
                }
                if (index == -1) {
                    // cell is empty, put PLACEHOLDER at the end of the list.
                    getItems().remove(PLACEHOLDER);
                    getItems().add(PLACEHOLDER);
                    getSelectionModel().clearSelection();
                }
            }
            event.consume();
        });

        // Set up drag dropped event
        cell.setOnDragDropped(event -> {
            System.out.println("DragDropped");
            Dragboard db = event.getDragboard();
            if (db.hasString()) {
                int placeholderIndex = getItems().indexOf(PLACEHOLDER);
                if (placeholderIndex != -1) {
                    getItems().set(placeholderIndex, draggingItem);
                    event.setDropCompleted(true);
                } else {
                    event.setDropCompleted(false);
                }
            } else {
                event.setDropCompleted(false);
            }
            System.out.println(savedSelectedEngine.getName());
            System.out.println(getItems().indexOf(savedSelectedEngine));
            System.out.println(getItems().indexOf(draggingItem));
            int idx = getItems().indexOf(savedSelectedEngine);
            System.out.println("index: " + idx);
            if (idx != -1) {
                selectIdx(getItems().indexOf(idx));
            }
            event.consume();
        });

        // Set up drag done event.
        cell.setOnDragDone(event -> {
            System.out.println("DragDone");
            getItems().remove(PLACEHOLDER);
            // Restore the list if draggingItem is missing.
            if (draggingItem != null && !getItems().contains(draggingItem)) {
                getItems().add(originalIndex, draggingItem);
            }
            System.out.println(getItems().indexOf(savedSelectedEngine));
            System.out.println(getItems().indexOf(draggingItem));
            int idx = getItems().indexOf(savedSelectedEngine);
            System.out.println("index: " + idx);
            if (idx != -1) {
                this.refresh();
                getSelectionModel().clearSelection();
                selectIdx(getItems().indexOf(savedSelectedEngine));
                this.refresh();
            }
            draggingItem = null;
            originalIndex = -1;
            event.consume();
        });

        // If the dragging leaves the listview entirely:
        setOnDragExited(event -> {
            // Restore the list
            getItems().remove(PLACEHOLDER);
            if (draggingItem != null && !getItems().contains(draggingItem)) {
                getItems().add(originalIndex, draggingItem);
            }
            //selectEngine(savedSelectedEngine);
            event.consume();
        });

        // If the dragging reenters the ListView:
        setOnDragEntered(event -> {
            // Remove the draggingItem again.
            getItems().remove(draggingItem);
            //selectEngine(savedSelectedEngine);
            event.consume();
        });

        return cell;
    }
    
    public void selectIdx(int idx) {
        getSelectionModel().clearAndSelect(idx);
    }
    
    //public void selectEngine(Engine engine) {
    //    getSelectionModel().select(engine);
    //}
    
    public Engine getSelectedEngine() {
        return getSelectionModel().getSelectedItem();
    }
    
    public void addSelectedItemPropertyListener(ChangeListener<Engine> listener) {
        getSelectionModel().selectedItemProperty().addListener(listener);
    }
}
