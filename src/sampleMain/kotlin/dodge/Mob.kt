package dodge

import godot.*
import godot.core.NodePath
import godot.core.VariantArray
import platform.posix.rand

class Mob: RigidBody2D() {

    var minSpeed = 150
    var maxSpeed = 250
    val animationList = arrayOf("fly", "walk", "swim")

    lateinit var animatedSprite: AnimatedSprite
    lateinit var visibilityNotifier2D: VisibilityNotifier2D

    companion object: GodotClass<Node, Mob>(::Mob) {
        override fun init(registry: ClassMemberRegistry<Mob>) {
            with(registry) {
                registerMethod(Mob::_ready)
                registerMethod(Mob::on_VisibilityNotifier2D_screen_exited)
                registerProperty(Mob::minSpeed, 150)
                registerProperty(Mob::maxSpeed, 250)
            }
        }
    }

    override fun _ready() {
        addToGroup("enemies")
        animatedSprite = getNode(NodePath("AnimatedSprite")) as AnimatedSprite
        visibilityNotifier2D = getNode(NodePath("VisibilityNotifier")) as VisibilityNotifier2D

        visibilityNotifier2D.signalScreenExited.connect(this, this::on_VisibilityNotifier2D_screen_exited)
        animatedSprite.setAnimation(animationList[rand() % animationList.size])
        animatedSprite.play()
    }

    fun on_VisibilityNotifier2D_screen_exited() {
        queueFree()
    }
}