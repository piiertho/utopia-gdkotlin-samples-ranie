package fastfinish

import godot.*
import godot.core.NodePath
import godot.core.Vector2

class Stage: Node() {

    lateinit var path: Path2D
    lateinit var player: Player
    lateinit var background: Sprite

    companion object: GodotClass<Node, Stage>(::Stage) {
        override fun init(registry: ClassMemberRegistry<Stage>) {
            with(registry) {
                registerMethod(Stage::_ready)
                registerMethod(Stage::_unhandled_input)
            }
        }
    }

    override fun _ready() {
        background = Sprite().apply {
            texture = ResourceLoader.load("res://Games/FastFinish/Sprites/Background.png") as Texture
            position = Vector2(968, 537)
        }
        addChild(background)
        path = (ResourceLoader.load("res://Games/FastFinish/Scenes/PlayerPath.tscn") as PackedScene).instance() as Path2D
        player = Player()
        path.getNode(NodePath("PathFollow2D"))?.addChild(player)
        addChild(path)
    }

    override fun _unhandled_input(event: InputEvent) {
        if (event.isActionPressed("ui_cancel")) {
            getTree().changeScene("res://Games/Main/Scenes/ChooseGameScreen.tscn")
        }
    }
}