package shmup

import godot.*
import godot.core.NodePath

class Enemy: Area2D() {

    var health = 2
    lateinit var bullet: NativeScript
    lateinit var camera: Node

    companion object: GodotClass<Node, Enemy>(::Enemy) {
        override fun init(registry: ClassMemberRegistry<Enemy>) {
            with(registry) {
                registerMethod(Enemy::_ready)
                registerMethod(Enemy::_process)
                registerMethod(Enemy::checkCollisions)

                registerProperty(Enemy::health, 2)
            }
        }
    }

    override fun _ready() {
        addToGroup("enemies")
        bullet = ResourceLoader.load("res://Games/Shmup/Scripts/Bullet.gdns") as NativeScript
        camera = getTree().getRoot().getNode(NodePath("Stage/Camera2D")) ?: throw RuntimeException("Cannot find node Stage/Camera2D")
    }

    override fun _process(delta: Double) {
        checkCollisions()
    }

    fun checkCollisions() {
        val arr = getOverlappingAreas()
        for (i in 0 until arr.size()){
            val obj = arr[i] as Area2D
            if (bullet.instanceHas(obj)){
                health--
                if (health == 0){
                    (getTree().getRoot().getNode(NodePath("Stage/Explosion")) as AudioStreamPlayer2D).play()
                    val args = godotArrayOf(0.2, 30, 3)
                    camera.callv("shake", args)
                    getParent().getParent().queueFree()
                } else {
                    val args = godotArrayOf(0.15, 5, 2)
                    camera.callv("shake", args)
                }
                obj.queueFree()
            }
        }
    }
}