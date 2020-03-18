package pong

import godot.*
import godot.core.NodePath
import godot.core.Variant
import godot.core.Vector2

class Main: Node() {

    lateinit var ball: KinematicBody2D
    lateinit var ballStartPos: Vector2
    lateinit var ballStartVel: Vector2

    var yourScore = 0
    var enemyScore = 0

    companion object: GodotClass<Node, Main>(::Main) {
        override fun init(registry: ClassMemberRegistry<Main>) {
            with(registry) {
                registerMethod(Main::_ready)
                registerMethod(Main::startGame)
                registerMethod(Main::on_VisibilityNotifier2D_screen_exited)
                registerMethod(Main::_unhandled_input)
            }
        }
    }

    override fun _ready() {
        ball = getNode(NodePath("Ball")) as KinematicBody2D
        ballStartPos = ball.getPosition()
        ballStartVel = Vector2(ball.get("xVel").asFloat(), ball.get("yVel").asFloat())
        startGame()
    }

    fun startGame() {
        ball.position = ballStartPos
        ball.set("xVel", Variant(ballStartVel.x))
        ball.set("yVel", Variant(ballStartVel.y))
        val yourScoreLabel = getNode(NodePath("YourScore")) as Label
        val enemyScoreLabel = getNode(NodePath("EnemyScore")) as Label
        yourScoreLabel.setText("YOU:\n$yourScore")
        enemyScoreLabel.setText("ENEMY:\n$enemyScore")
    }

    fun on_VisibilityNotifier2D_screen_exited() {
        if (ball.position.y < 0)
            yourScore += 1
        else
            enemyScore += 1
        startGame()
    }

    override fun _unhandled_input(event: InputEvent) {
        if (event.isActionPressed("ui_cancel")) {
            getTree().changeScene("res://Games/Main/Scenes/ChooseGameScreen.tscn")
        }
    }
}