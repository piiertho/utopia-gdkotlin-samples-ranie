package pong

import godot.*
import godot.core.NodePath
import godot.core.VariantArray
import godot.core.Vector2

class Ball: KinematicBody2D() {

    var xVel = 300
    var yVel = 300
    lateinit var velocity: Vector2
    lateinit var visibilityNotifier2D: VisibilityNotifier2D

    companion object: GodotClass<Node, Ball>(::Ball) {
        override fun init(registry: ClassMemberRegistry<Ball>) {
            with(registry) {
                registerMethod(Ball::_ready)
                registerMethod(Ball::_physics_process)

                registerProperty(Ball::xVel, 300)
                registerProperty(Ball::yVel, 300)
            }
        }
    }

    override fun _ready() {
        velocity = Vector2(xVel, yVel)

        visibilityNotifier2D = getNode(NodePath("VisibilityNotifier2D")) as VisibilityNotifier2D
        val parent = getParent() as Main
        visibilityNotifier2D.signalScreenExited.connect(parent, parent::on_VisibilityNotifier2D_screen_exited)
    }

    override fun _physics_process(delta: Float) {
        val collisionInfo = moveAndCollide(velocity * delta)
        if (!collisionInfo.isNull()) {
            val normal = collisionInfo.normal
            velocity = velocity.bounce(normal)
            gprint("Velocity: $velocity, normal: $normal")
        }
    }
}