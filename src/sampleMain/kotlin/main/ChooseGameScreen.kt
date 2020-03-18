package main

import godot.*
import godot.core.NodePath
import godot.core.VariantArray

class ChooseGameScreen: Node() {

    lateinit var playDodgeButton: Button
    lateinit var playPongButton: Button
    lateinit var playShmupButton: Button
    lateinit var backButton: Button
    lateinit var playCatchBallButton: Button
    lateinit var playFastFinishButton: Button

    companion object: GodotClass<Node, ChooseGameScreen>(::ChooseGameScreen) {
        override fun init(registry: ClassMemberRegistry<ChooseGameScreen>) {
            with(registry) {
                registerMethod(ChooseGameScreen::_ready)
                registerMethod(ChooseGameScreen::onPlayDodgeButtonPressed)
                registerMethod(ChooseGameScreen::onPlayShmupButtonPressed)
                registerMethod(ChooseGameScreen::onPlayCatchBallButtonPressed)
                registerMethod(ChooseGameScreen::onPlayFastFinishButtonPressed)
                registerMethod(ChooseGameScreen::onPlayPongButtonPressed)
                registerMethod(ChooseGameScreen::onBackButtonPressed)
            }
        }
    }

    override fun _ready() {
        playDodgeButton = (getNode(NodePath("MenuButtons/PlayDodgeButton")) as Button).apply {
            connect("pressed", this@ChooseGameScreen, "onPlayDodgeButtonPressed", VariantArray())
        }
        playPongButton = (getNode(NodePath("MenuButtons/PlayPongButton")) as Button).apply {
            connect("pressed", this@ChooseGameScreen, "onPlayPongButtonPressed", VariantArray())
        }
        playShmupButton = (getNode(NodePath("MenuButtons/PlayShmupButton")) as Button).apply {
            connect("pressed", this@ChooseGameScreen, "onPlayShmupButtonPressed", VariantArray())
        }
        playCatchBallButton = (getNode(NodePath("MenuButtons/PlayCatchBallButton")) as Button).apply {
            connect("pressed", this@ChooseGameScreen, "onPlayCatchBallButtonPressed", VariantArray())
        }
        playFastFinishButton = (getNode(NodePath("MenuButtons/PlayFastFinishButton")) as Button).apply {
            connect("pressed", this@ChooseGameScreen, "onPlayFastFinishButtonPressed", VariantArray())
        }
        backButton = (getNode(NodePath("MenuButtons/BackButton")) as Button).apply {
            connect("pressed", this@ChooseGameScreen, "onBackButtonPressed", VariantArray())
        }
    }

    fun onPlayDodgeButtonPressed() {
        getTree().changeScene("res://Games/Dodge/Scenes/Main.tscn")
    }

    fun onPlayShmupButtonPressed() {
        getTree().changeScene("res://Games/Shmup/Scenes/Stage.tscn")
    }

    fun onPlayCatchBallButtonPressed() {
        getTree().changeScene("res://Games/CatchBall/Scenes/Stage.tscn")
    }

    fun onPlayFastFinishButtonPressed() {
        getTree().changeScene("res://Games/FastFinish/Scenes/Stage.tscn")
    }

    fun onPlayPongButtonPressed() {
        getTree().changeScene("res://Games/Pong/Scenes/Main.tscn")
    }

    fun onBackButtonPressed() {
        getTree().changeScene("res://Games/Main/Scenes/MainScreen.tscn")
    }
}