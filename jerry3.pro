#-------------------------------------------------
#
# Project created by QtCreator 2015-10-11T20:38:05
#
#-------------------------------------------------

QT       += core gui
greaterThan(QT_MAJOR_VERSION, 5): QT += widgets

QT       += printsupport
QT       += svg
QT       -= gui

TARGET = Jerry
CONFIG   -= console
CONFIG   -= app_bundle
CONFIG   += c++14



TEMPLATE = app


SOURCES += main.cpp \
    chess/board.cpp \
    chess/move.cpp \
    chess/game_node.cpp \
    chess/game.cpp \
    chess/pgn_reader.cpp \
    chess/pgn_printer.cpp \
    uci/uci_controller.cpp \
    uci/uci_worker.cpp \
    uci/engine_info.cpp \
    funct.cpp \
    main_window.cpp \
    viewController/boardviewcontroller.cpp \
    model/game_model.cpp \
    viewController/piece_images.cpp \
    dialogs/dialog_promotion.cpp \
    viewController/moveviewcontroller.cpp \
    chess/gui_printer.cpp \
    dialogs/dialog_nextmove.cpp \
    dialogs/dialog_plaintext.cpp \
    controller/mode_controller.cpp \
    dialogs/dialog_guioptions.cpp \
    dialogs/dialog_enterposition.cpp \
    viewController/colorstyle.cpp \
    viewController/engineview.cpp \
    model/engine.cpp \
    model/internalengine.cpp \
    dialogs/dialog_engines.cpp \
    dialogs/dialog_engineoptions.cpp \
    model/engine_option.cpp \
    dialogs/dialog_about.cpp \
    various/messagebox.cpp \
    controller/edit_controller.cpp \
    controller/file_controller.cpp \
    dialogs/dialog_newgame.cpp \
    dialogs/dialog_browseheaders.cpp \
    dialogs/dialog_editheaders.cpp \
    dialogs/dialog_gameanalysis.cpp \
    various/resource_finder.cpp \
    viewController/on_off_button.cpp \
    viewController/chessboard.cpp \
    viewController/enterposboard.cpp \
    viewController/pickcolorboard.cpp \
    chess/ecocode.cpp \
    chess/polyglot.cpp \
    chess/byteutil.cpp \
    chess/dcgdecoder.cpp \
    chess/dcgencoder.cpp \
    chess/database.cpp \
    chess/indexentry.cpp \
    dialogs/dialog_database.cpp \
    controller/database_controller.cpp \
    various/helper.cpp \
    dialogs/dialog_search.cpp \
    dialogs/tab_header_search.cpp \
    dialogs/tab_comment_search.cpp \
    dialogs/tab_search_pos.cpp \
    model/search_pattern.cpp \
    viewController/database_index_model.cpp

HEADERS += \
    chess/board.h \
    chess/move.h \
    chess/game_node.h \
    chess/game.h \
    chess/pgn_reader.h \
    chess/pgn_printer.h \
    uci/uci_worker.h \
    uci/uci_controller.h \
    uci/engine_info.h \
    funct.h \
    main_window.h \
    viewController/boardviewcontroller.h \
    model/game_model.h \
    viewController/piece_images.h \
    dialogs/dialog_promotion.h \
    viewController/moveviewcontroller.h \
    chess/gui_printer.h \
    dialogs/dialog_nextmove.h \
    dialogs/dialog_plaintext.h \
    controller/mode_controller.h \
    dialogs/dialog_guioptions.h \
    dialogs/dialog_enterposition.h \
    viewController/colorstyle.h \
    viewController/engineview.h \
    model/engine.h \
    model/internalengine.h \
    dialogs/dialog_engines.h \
    dialogs/dialog_engineoptions.h \
    model/engine_option.h \
    dialogs/dialog_about.h \
    various/messagebox.h \
    controller/edit_controller.h \
    controller/file_controller.h \
    dialogs/dialog_newgame.h \
    dialogs/dialog_browseheaders.h \
    dialogs/dialog_editheaders.h \
    dialogs/dialog_gameanalysis.h \
    various/resource_finder.h \
    viewController/on_off_button.h \
    viewController/chessboard.h \
    viewController/enterposboard.h \
    viewController/pickcolorboard.h \
    chess/ecocode.h \
    chess/polyglot.h \
    chess/dcgdecoder.h \
    chess/dcgencoder.h \
    chess/database.h \
    chess/byteutil.h \
    chess/indexentry.h \
    dialogs/dialog_database.h \
    controller/database_controller.h \
    various/helper.h \
    dialogs/dialog_search.h \
    dialogs/tab_header_search.h \
    dialogs/tab_comment_search.h \
    dialogs/tab_search_pos.h \
    model/search_pattern.h \
    viewController/database_index_model.h \
    chess/constants.h