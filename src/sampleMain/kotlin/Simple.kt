import godot.*

class Simple: Spatial() {
    override fun _ready() {
        gprint("test")
    }

    companion object: GodotClass<Spatial, Simple>(::Simple) {
        override fun init(registry: ClassMemberRegistry<Simple>) {
            with(registry) {
                registerMethod(Simple::_ready)
            }
        }
    }
}