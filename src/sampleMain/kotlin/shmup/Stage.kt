package shmup

import godot.*
import godot.core.NodePath

class Stage: Node() {

    lateinit var Enemy: PackedScene
    lateinit var EnemyPath: PackedScene
    lateinit var Player: PackedScene
    lateinit var player: Area2D
    var respawnTime = 0

    companion object: GodotClass<Node, Stage>(::Stage) {
        override fun init(registry: ClassMemberRegistry<Stage>) {
            with(registry) {
                registerMethod(Stage::_ready)
                registerMethod(Stage::_process)
                registerMethod(Stage::_unhandled_input)
            }
        }
    }

    override fun _ready() {
        Enemy = ResourceLoader.load("res://Games/Shmup/Scenes/Enemy.tscn") as PackedScene
        EnemyPath = ResourceLoader.load("res://Games/Shmup/Scenes/EnemyPath.tscn") as PackedScene
        Player = ResourceLoader.load("res://Games/Shmup/Scenes/Player.tscn") as PackedScene
        player = Player.instance() as Area2D
        addChild(player)
    }

    override fun _process(delta: Float) {
        respawnTime++
        val count = getTree().getNodesInGroup("enemies").size()
        if(respawnTime > 30 && count < 4) {
            loadEnemy()
            respawnTime = 0
        }
    }

    fun loadEnemy() {
        val newEnemy = Enemy.instance()
        val newPath = EnemyPath.instance().apply {
            getNode(NodePath("PathFollow2D"))?.addChild(newEnemy)
        }
        addChild(newPath)
    }

    override fun _unhandled_input(event: InputEvent) {
        if (event.isActionPressed("ui_cancel")) {
            getTree().changeScene("res://Games/Main/Scenes/ChooseGameScreen.tscn")
        }
    }
}