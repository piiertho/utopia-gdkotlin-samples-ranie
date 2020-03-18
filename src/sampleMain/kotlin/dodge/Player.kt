package dodge

import godot.*
import godot.core.NodePath
import godot.core.VariantArray
import godot.core.Vector2

class Player: Area2D() {

    var speed: Int = 400
    lateinit var screensize: Vector2

    lateinit var playerSprite: AnimatedSprite
    lateinit var collisionShape: CollisionShape2D

    val signalHit by signal0()

    companion object: GodotClass<Node, Player>(::Player) {
        override fun init(registry: ClassMemberRegistry<Player>) {
            with(registry) {
                registerMethod(Player::_ready)
                registerMethod(Player::_process)
                registerMethod(Player::on_Player_body_entered)
                registerMethod(Player::start)

                registerProperty(Player::speed, 400)
            }
        }
    }

    override fun _ready() {
        addUserSignal(Signal::hit.name)
        playerSprite = getNode(NodePath("AnimatedSprite")) as AnimatedSprite
        collisionShape = getNode(NodePath("CollisionShape2D")) as CollisionShape2D
        screensize = getViewportRect().size
        hide()

        this.connect("body_entered", this, "_on_Player_body_entered", VariantArray())
        this.connect(Signal::hit.name, getParent(), "gameOver", VariantArray())
    }

    override fun _process(delta: Float) {
        var velocity = Vector2()

        if (Input.isActionPressed("ui_right"))
            velocity.x += 1
        if (Input.isActionPressed("ui_left"))
            velocity.x -= 1
        if (Input.isActionPressed("ui_up"))
            velocity.y -= 1
        if (Input.isActionPressed("ui_down"))
            velocity.y += 1

        if (velocity.length() != 0.0f) {
            velocity = velocity.normalized() * speed.toFloat()
            playerSprite.play()
        } else {
            playerSprite.stop()
        }

        position += velocity * delta

        if (position.x < 0) {
            position {
                x = 0.0f
            }
        } else if (position.x > screensize.x) {
            position {
                x = screensize.x
            }
        }

        if (position.y < 0) {
            position {
                y = 0.0f
            }
        } else if (position.y > screensize.y) {
            position {
                y = screensize.y
            }
        }

        if (velocity.x != 0.0f) {
            playerSprite.setAnimation("right")
            playerSprite.flipV = false
            playerSprite.flipH = velocity.x < 0
        } else if (velocity.y != 0.0f) {
            playerSprite.setAnimation("up")
            playerSprite.flipH = false
            playerSprite.flipV = velocity.y > 0
        }
    }

    @Suppress("UNUSED_PARAMETER")
    fun on_Player_body_entered(body: Object) {
        hide()
        emitSignal(Signal::hit.name)
        collisionShape.callDeferred("set_disabled", true)
    }

    fun start(pos: Vector2) {
        position = pos
        show()
        collisionShape.callDeferred("set_disabled", false)
    }
}