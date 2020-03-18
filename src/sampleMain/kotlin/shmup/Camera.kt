package shmup

import godot.*
import godot.core.Vector2
import platform.posix.rand

class Camera: Camera2D() {

    var duration = 0.0f
    var periodInMs = 0.0f
    var amplitude = 0.0f
    var timer = 0.0f
    var lastShookTimer = 0.0f
    var previousX = 0.0f
    var previousY = 0.0f
    var lastOffset = Vector2(0, 0)

    companion object: GodotClass<Node, Camera>(::Camera) {
        override fun init(registry: ClassMemberRegistry<Camera>) {
            with(registry) {
                registerMethod(Camera::_process)
                registerMethod(Camera::shake)
            }
        }
    }

    override fun _process(delta: Float) {
        if (timer == 0.0f){
            if(offset.length() > 1.0)
                offset /= 4.0f
            else
                offset = Vector2()
            return
        }

        lastShookTimer += delta

        while (lastShookTimer >= periodInMs ){
            lastShookTimer -= periodInMs
            val intensity = amplitude * (1 - ((duration - timer) / duration))

            val newX = ((rand() / 10000) % 2) - 1f
            val xComponent = intensity * (previousX + (delta * (newX - previousX)))

            val newY = ((rand() / 10000) % 2) - 1f
            val yComponent = intensity * (previousY + (delta * (newY - previousY)))

            previousX = newX
            previousY = newY

            val newOffset = Vector2(xComponent, yComponent)

            offset -= lastOffset + newOffset
            lastOffset = newOffset
        }

        timer -= delta

        if (timer <= 0.0f) {
            timer = 0.0f
            offset -= lastOffset
        }
    }

    fun shake(duration: Float, frequency: Float, amplitude: Float){
        this.duration = duration
        timer = duration

        periodInMs = 1.0f / frequency

        this.amplitude = amplitude

        previousX = ((rand() / 10000) % 2) - 1f
        previousY = ((rand() / 10000) % 2) - 1f

        offset -= lastOffset
        lastOffset = Vector2()
    }
}