package fastfinish

import godot.*

class Player: Area2D() {

    var velocity = 0.0f
    var speed = 30.0f
    lateinit var path: PathFollow2D
    lateinit var sprite: Sprite

    companion object: GodotClass<Node, Player>(::Player) {
        override fun init(registry: ClassMemberRegistry<Player>) {
            with(registry) {
                registerMethod(Player::_ready)
                registerMethod(Player::_process)
            }
        }
    }

    init {
        setScript(ResourceLoader.load("res://Games/FastFinish/Scripts/Player.gdns") as Reference)
    }

    override fun _ready() {
        sprite = Sprite().apply {
            texture = ResourceLoader.load("res://Games/FastFinish/Sprites/Player.png") as Texture
        }
        addChild(sprite)
        path = getParent() as PathFollow2D
    }

    override fun _process(delta: Float) {
        if (Input.isActionJustPressed("ui_select"))
            velocity += 2

        if(velocity > 0) velocity -= 0.15f
        else velocity = 0.0f

        path.offset += velocity * delta * speed

        if (path.offset > 5100) {
            gprint("END")
            setProcess(false)
        }
    }
}