package catchball

import godot.*
import godot.core.Vector3

class Player: KinematicBody() {

    var speed = 6.0f

    companion object: GodotClass<Node, Player>(::Player) {
        override fun init(registry: ClassMemberRegistry<Player>) {
            with(registry) {
                registerMethod(Player::_process)
                registerProperty(Player::speed, 6f)
            }
        }
    }

    override fun _process(delta: Float){
        var velocity = Vector3()

        if (Input.isActionPressed("ui_right"))
            velocity.z -= 1.0f
        if (Input.isActionPressed("ui_left"))
            velocity.z += 1.0f

        if (Input.isActionPressed("ui_down"))
            velocity.x += 1.0f
        if (Input.isActionPressed("ui_up"))
            velocity.x -= 1.0f

        if (velocity.length() > 0) {
            velocity = velocity.normalized() * speed

            if(translation.x + velocity.x * delta < 4.0 &&
                    translation.x + velocity.x * delta > -4.0)
                translation { x += velocity.x * delta }

            if (translation.z + velocity.z * delta < 4.0 &&
                    translation.z + velocity.z * delta > -4.0)
                translation { z += velocity.z * delta }
        }
    }

}