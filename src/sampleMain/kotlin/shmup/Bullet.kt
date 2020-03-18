package shmup

import godot.*
import godot.core.Vector2

class Bullet: Area2D() {

    var speed = 1000

    companion object: GodotClass<Node, Bullet>(::Bullet) {
        override fun init(registry: ClassMemberRegistry<Bullet>) {
            with(registry) {
                registerMethod(Bullet::_process)
            }
        }
    }

    override fun _process(delta: Float) {
        position -= Vector2(0f, speed * delta)
    }
}