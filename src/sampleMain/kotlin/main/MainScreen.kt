package main

import godot.*
import godot.core.NodePath
import godot.core.VariantArray

class MainScreen: Node() {

    lateinit var chooseGameButton: Button
    lateinit var quitButton: Button

    companion object: GodotClass<Node, MainScreen>(::MainScreen) {
        override fun init(registry: ClassMemberRegistry<MainScreen>) {
            with(registry) {
                registerMethod(MainScreen::_ready)
                registerMethod(MainScreen::onChooseGameButtonPressed)
                registerMethod(MainScreen::onQuitButtonPressed)
            }
        }
    }

    override fun _ready() {
        chooseGameButton = getNode(NodePath("MenuButtons/ChooseGameButton")) as Button
        chooseGameButton.signalPressed.connect(this, this::onChooseGameButtonPressed)


        quitButton = getNode(NodePath(("MenuButtons/QuitButton"))) as Button
        quitButton.signalPressed.connect(this, this::onQuitButtonPressed)
    }

    fun onChooseGameButtonPressed() {
        getTree().changeScene("res://Games/Main/Scenes/ChooseGameScreen.tscn")
    }

    fun onQuitButtonPressed() {
        getTree().changeScene("res://Games/Main/Scenes/ChooseGameScreen.tscn")
    }
}