package dodge

import gdnative.godot_array
import godot.*
import godot.core.NodePath
import godot.core.VariantArray
import godot.core.Vector2
import kotlin.math.PI
import kotlin.random.Random

class Main: Node() {

    lateinit var mobTimer: Timer
    lateinit var scoreTimer: Timer
    lateinit var startTimer: Timer
    lateinit var startPosition: Position2D
    lateinit var mobPath: Path2D
    lateinit var mobSpawnLocation: PathFollow2D
    lateinit var mobScene: PackedScene
    var score = 0

    companion object: GodotClass<Node, Main>(::Main) {
        override fun init(registry: ClassMemberRegistry<Main>) {
            with(registry) {
                registerMethod(Main::_ready)
                registerMethod(Main::gameOver)
                registerMethod(Main::newGame)
                registerMethod(Main::onStartTimerTimeout)
                registerMethod(Main::onScoreTimerTimeout)
                registerMethod(Main::onMobTimerTimeout)
                registerMethod(Main::_unhandled_input)
            }
        }
    }

    override fun _ready() {
        mobScene = ResourceLoader.load("res://Games/Dodge/Scenes/Mob.tscn") as PackedScene

        mobTimer = (getNode(NodePath("MobTimer")) as Timer)
        mobTimer.signalTimeout.connect(this, this::onMobTimerTimeout)

        scoreTimer = (getNode(NodePath("ScoreTimer")) as Timer)
        scoreTimer.signalTimeout.connect(this, this::onScoreTimerTimeout)

        startTimer = (getNode(NodePath("StartTimer")) as Timer)
        startTimer.signalTimeout.connect(this, this::onStartTimerTimeout)

        startPosition = getNode(NodePath("StartPosition")) as Position2D
        mobPath = getNode(NodePath("MobPath")) as Path2D
        mobSpawnLocation = getNode(NodePath("MobPath/MobSpawnLocation")) as PathFollow2D

        getNode(NodePath("HUD"))?.callv("showMenu", VariantArray())
    }

    fun gameOver() {
        scoreTimer.stop()
        mobTimer.stop()
        getNode(NodePath("HUD"))?.callv("showGameOver", VariantArray())
    }

    fun newGame() {
        getTree().callGroup("enemies", "free")
        score = 0

        var param = VariantArray()
        param.append(startPosition.position)
        getNode(NodePath("Player"))?.callv("start", param)
        startTimer.start()

        param = VariantArray()
        param.append(score)
        getNode(NodePath("HUD"))?.callv("updateScore", param)

        param = VariantArray()
        param.append("Get ready!")
        getNode(NodePath("HUD"))?.callv("showMessage", param)
    }

    fun onStartTimerTimeout() {
        mobTimer.start()
        scoreTimer.start()
    }

    fun onScoreTimerTimeout() {
        score += 1
        val param = VariantArray()
        param.append(score)
        getNode(NodePath("HUD"))?.callv("updateScore", param)
    }

    fun onMobTimerTimeout() {
        mobSpawnLocation.setOffset(Random.nextInt().toFloat())
        val mob = mobScene.instance() as RigidBody2D
        addChild(mob)
        var direction = (mobSpawnLocation.rotation + PI/2).toFloat()
        mob.position = mobSpawnLocation.position

        fun ClosedRange<Int>.random() =
                Random.nextInt(endInclusive - start) + start
        direction += ((-PI/4 * 100000).toInt()..(PI/4  * 100000).toInt()).random() / 100000
        mob.rotation = direction
        mob.setLinearVelocity(Vector2((mob.get("minSpeed").asInt()..mob.get("maxSpeed").asInt()).random(), 0).rotated(direction))
    }

    override fun _unhandled_input(event: InputEvent) {
        if (event.isActionPressed("ui_cancel")) {
            getTree().changeScene("res://Games/Main/Scenes/ChooseGameScreen.tscn")
        }
    }
}