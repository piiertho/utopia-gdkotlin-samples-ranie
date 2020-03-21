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

    val signalStartGame by signal0()

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

        val startButton = getNode(NodePath("StartButton")) as Button
        startButton.signalPressed.connect(this, this::onStartButtonPressed)


        val timer = getNode(NodePath("MessageTimer")) as Timer
        timer.signalTimeout.connect(this, this::onMessageTimerTimeout)

        waitingTimer = getNode(NodePath("WaitingTimer")) as Timer

        val gameOverTimer = getNode(NodePath("GameOverTimer")) as Timer
        gameOverTimer.signalTimeout.connect(this, this::onGameOverTimerTimeout)

        addUserSignal(signalStartGame.name, VariantArray())
        val parent = getParent() as Main
        signalStartGame.connect(parent, parent::newGame)
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