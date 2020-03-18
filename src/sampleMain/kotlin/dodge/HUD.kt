package dodge

import godot.*
import godot.core.NodePath
import godot.core.VariantArray

class HUD : CanvasLayer() {

    lateinit var messageLabel: Label
    lateinit var scoreLabel: Label
    lateinit var startButton: Button
    lateinit var messageTimer: Timer
    lateinit var waitingTimer: Timer
    lateinit var gameOverTimer: Timer

    val signalStartGame by signal

    companion object: GodotClass<Node, HUD>(::HUD) {
        override fun init(registry: ClassMemberRegistry<HUD>) {
            with(registry) {
                registerMethod(HUD::_ready)
                registerMethod(HUD::showMessage)
                registerMethod(HUD::showMenu)
                registerMethod(HUD::showGameOver)
                registerMethod(HUD::updateScore)
                registerMethod(HUD::onMessageTimerTimeout)
                registerMethod(HUD::onGameOverTimerTimeout)
                registerMethod(HUD::onStartButtonPressed)
            }
        }
    }

    override fun _ready() {
        messageLabel = getNode(NodePath("MessageLabel")) as Label
        scoreLabel = getNode(NodePath("ScoreLabel")) as Label
        startButton = (getNode(NodePath("StartButton")) as Button).apply {
            connect("pressed", this@HUD, "onStartButtonPressed", VariantArray())
        }
        messageTimer = (getNode(NodePath("MessageTimer")) as Timer).apply {
            connect("timeout", this@HUD, "onMessageTimerTimeout", VariantArray())
        }
        waitingTimer = getNode(NodePath("WaitingTimer")) as Timer
        gameOverTimer = (getNode(NodePath("GameOverTimer")) as Timer).apply {
            connect("timeout", this@HUD, "onGameOverTimerTimeout", VariantArray())
        }

        addUserSignal(Signal::startGame.name)
        connect(Signal::startGame.name, getParent(), "newGame", VariantArray())
    }

    fun showMessage(text: String) {
        messageLabel.text = text
        messageLabel.show()
        messageTimer.start()
    }

    fun showMenu() {
        startButton.text = "Start"
        startButton.show()
        messageLabel.text = "Dodge the\nCreeps!"
        messageLabel.show()
    }

    fun showGameOver() {
        messageLabel.text = "Game over!"
        messageLabel.show()
        gameOverTimer.start()
    }

    fun updateScore(score: Int) {
        scoreLabel.text = score.toString()
    }

    fun onMessageTimerTimeout() {
        messageLabel.hide()
    }

    fun onGameOverTimerTimeout() {
        messageLabel.hide()
        showMenu()
    }

    fun onStartButtonPressed() {
        startButton.hide()
        signalStartGame.emit()
    }

}