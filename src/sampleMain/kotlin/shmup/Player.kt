package shmup

import godot.*
import godot.core.NodePath
import godot.core.VariantArray
import godot.core.Vector2

class Player: KinematicBody2D() {

    var shootTime = 0
    var shooting = false
    var speed = 600.0f
    lateinit var Bullet: PackedScene

    companion object: GodotClass<Node, Player>(::Player) {
        override fun init(registry: ClassMemberRegistry<Player>) {
            with(registry) {
                registerMethod(Player::_ready)
                registerMethod(Player::_process)
                registerMethod(Player::move)
                registerMethod(Player::shootin)
                registerProperty(Player::speed, 600f)
            }
        }
    }

    override fun _ready() {
        Bullet = ResourceLoader.load("res://Games/Shmup/Scenes/Bullet.tscn") as PackedScene
    }

    override fun _process(delta: Float) {
        move(delta)
        shootin()
    }

    fun move(delta: Float) {
        var velocity = Vector2()

        if (Input.isActionPressed("ui_right"))
            velocity.x += 1f
        if (Input.isActionPressed("ui_left"))
            velocity.x -= 1f
        if (Input.isActionPressed("ui_down"))
            velocity.y += 1f
        if (Input.isActionPressed("ui_up"))
            velocity.y -= 1f

        if (velocity.length() > 0) {
            velocity = velocity.normalized() * speed
            moveAndCollide(velocity * delta)
        }
    }

    fun shootin() {
        if (Input.isActionPressed("ui_accept") && !shooting){
            shooting = true
            shootTime = 0
            val bul = (Bullet.instance() as Area2D).apply {
                position = this@Player.position
            }
            val notifier = VisibilityNotifier2D().apply {
                connect("screen_exited", bul, "queue_free", VariantArray())
            }
            (getNode(NodePath("Shoot")) as AudioStreamPlayer2D).play()
            bul.addChild(notifier)
            getParent()?.addChild(bul)
        }
        else
            shootTime++

        if (shootTime > 8) shooting = false
    }
}