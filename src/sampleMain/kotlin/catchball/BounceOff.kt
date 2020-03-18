package catchball

import godot.*

class BounceOff: Area() {

    companion object: GodotClass<Node, BounceOff>(::BounceOff) {
        override fun init(registry: ClassMemberRegistry<BounceOff>) {
            with(registry) {
                registerMethod(BounceOff::_process)
            }
        }
    }

    override fun _process(delta: Float) {
        if(getOverlappingBodies().size() > 0)
            (getOverlappingBodies()[0] as RigidBody).bounce = 0.0f
    }
}