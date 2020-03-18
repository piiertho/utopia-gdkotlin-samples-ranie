package pong

import godot.*
import godot.core.Vector2

class Player: KinematicBody2D() {

    var speed = 400

    companion object: GodotClass<Node, Player>(::Player) {
        override fun init(registry: ClassMemberRegistry<Player>) {
            with(registry) {
                registerMethod(Player::_physics_process)

                registerProperty(Player::speed, 400)
            }
        }
    }

    override fun _physics_process(delta: Float) {

        var velocity = Vector2()

        if (Input.isActionPressed("ui_right"))
            velocity.x += 1.0f
        if (Input.isActionPressed("ui_left"))
            velocity.x -= 1.0f

        if (velocity.length() > 0) {
            velocity *= speed.toFloat()
            moveAndCollide(velocity * delta)
            position.y = 1057.388062.toFloat()
        }
    }
}