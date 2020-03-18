package catchball

import godot.*
import godot.core.Vector3
import kotlin.random.Random
import kotlin.system.getTimeNanos

class Ball: RigidBody() {

    companion object: GodotClass<Node, Ball>(::Ball) {
        override fun init(registry: ClassMemberRegistry<Ball>) {
            with(registry) {
                registerMethod(Ball::_ready)
            }
        }
    }

    override fun _ready() {
        val random = Random(getTimeNanos())
        fun ClosedRange<Int>.random() =
                random.nextInt(endInclusive - start) + start

        linearVelocity = Vector3((-70..70).random().toFloat() / 10 , 0f, (-70..70).random().toFloat() / 10)
    }
}