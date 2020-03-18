package pong

import godot.*
import godot.core.NodePath
import godot.core.Vector2

class Enemy: KinematicBody2D() {

    @RegisterProperty(true, "400")
    var speed = 400

    companion object: GodotClass<Node, Enemy>(::Enemy) {
        override fun init(registry: ClassMemberRegistry<Enemy>) {
            with(registry) {
                registerMethod(Enemy::_physics_process)

                registerProperty(Enemy::speed, 400)
            }
        }
    }

    override fun _physics_process(delta: Float) {
        var velocity = Vector2()
        val ball = getParent()?.getNode(NodePath("Ball")) as KinematicBody2D
        val ballPos = ball.getPosition()


        if (ballPos.x > this.position.x)
            velocity.x += 1.0f
        else
            velocity.x -= 1.0f

        if (velocity.length() > 0) {
            velocity *= speed.toFloat()
            moveAndCollide(velocity * delta)
            position.y = 12.263565.toFloat()
        }
    }
}