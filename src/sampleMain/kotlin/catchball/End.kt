package catchball

import godot.*

class End: Area() {

    companion object: GodotClass<Node, End>(::End) {
        override fun init(registry: ClassMemberRegistry<End>) {
            with(registry) {
                registerMethod(End::_process)
            }
        }
    }

    override fun _process(delta: Float) {
        if(getOverlappingBodies().size() == 1) gprint("END")

        setProcess(false)
    }
}