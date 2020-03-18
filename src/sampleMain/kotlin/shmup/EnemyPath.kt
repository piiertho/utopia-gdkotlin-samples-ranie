package shmup

import godot.*
import godot.core.NodePath
import godot.core.Variant

class EnemyPath: Path2D() {

    companion object: GodotClass<Node, EnemyPath>(::EnemyPath) {
        override fun init(registry: ClassMemberRegistry<EnemyPath>) {
            with(registry) {
                registerMethod(EnemyPath::_ready)
            }
        }
    }

    override fun _ready() {
        val follow = getNode(NodePath("PathFollow2D")) ?: throw RuntimeException("cannot find node PathFollow2D")
        val tween = Tween().apply {
            interpolateProperty(follow, NodePath("unit_offset"), Variant(0), Variant(1), 6.0f, Tween.TransitionType.TRANS_LINEAR.value, Tween.EaseType.IN_OUT.value)
            setRepeat(true)
        }
        addChild(tween)
        tween.start()
    }
}