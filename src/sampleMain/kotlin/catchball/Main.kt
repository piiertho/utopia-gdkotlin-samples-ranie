package catchball

import godot.*

class Main: Node() {

    companion object: GodotClass<Node, Main>(::Main) {
        override fun init(registry: ClassMemberRegistry<Main>) {
            with(registry) {
                registerMethod(Main::_unhandled_input)
            }
        }
    }

    override fun _unhandled_input(event: InputEvent) {
        if (event.isActionPressed("ui_cancel")) {
            getTree().changeScene("res://Games/Main/Scenes/ChooseGameScreen.tscn")
        }
    }
}