import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.math.IntVector2
import kotlin.math.cos
import kotlin.math.sin

fun main(args: Array<String>) = application {
    configure {
        width = 1920 / 2
        height = 1080 / 2
        if (args.isNotEmpty()) {
            println("the arg is ${args[0]}")
            val n = args[0].toInt()
            if (n in 0..3)
                position = IntVector2(
                    (n % 2) * 1920 / 2,
                    (n / 2) * 1080 / 2 + 1
                )
        }
        hideWindowDecorations = true
    }

    program {
        extend {
            val t = System.currentTimeMillis() * 0.001
            drawer.fill = ColorRGBa.PINK
            drawer.circle(
                cos(t) * width / 2.0 + width / 2.0,
                sin(0.5 * t) * height / 2.0 + height / 2.0, 140.0
            )
        }
    }
}
