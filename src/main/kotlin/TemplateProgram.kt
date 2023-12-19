import org.openrndr.application
import org.openrndr.draw.colorBuffer
import org.openrndr.draw.shadeStyle
import org.openrndr.extra.noise.filters.CellNoise
import org.openrndr.math.IntVector2
import org.openrndr.math.Vector2
import org.openrndr.math.Vector4
import kotlin.math.sin

fun main(args: Array<String>) = application {
    val (W, H, N) = if (args.size == 3) args.map { it.toInt() } else listOf(640, 480, 0)
    println("Usage: app WIDTH HEIGHT DISPLAYNUM(0..3)")
    configure {
        width = W
        height = H
        position = IntVector2(
            (N % 2) * width,
            (N / 2) * height
        )
        hideWindowDecorations = true
    }

    program {
        val noise = CellNoise().also {
            it.seed = Vector2(0.0 + (N % 2 ), 0.0 - (N / 2))
        }
        val img = colorBuffer(width, height)

        extend {
            val t = System.currentTimeMillis() * 0.0001
            noise.scale = Vector2(
                sin(t + 4) * 0.1 + 0.4,
                sin(t + 5) * 0.1 + 0.4
            )
            noise.lacunarity = Vector2(
                sin(t + 3) * 0.1 + 2.0,
                sin(t + 1) * 0.1 + 2.0
            )
            noise.gain = Vector4(
                sin(t) * 0.1 + 1.0,
                sin(t + 0.1) * 0.1 + 1.0,
                sin(t + 0.2) * 0.1 + 1.0,
                sin(t + 0.3) * 0.1 + 1.0
            )
            noise.decay = Vector4(
                sin(t * 0.7) * 0.1 + 0.5,
                sin(t * 0.71) * 0.1 + 0.5,
                sin(t * 0.72) * 0.1 + 0.5,
                sin(t * 0.73) * 0.1 + 0.5
            )
            noise.bias = Vector4(
                sin(t * 0.3) * 0.1,
                sin(t * 0.31) * 0.1,
                sin(t * 0.32) * 0.1,
                sin(t * 0.33) * 0.1
            )
            noise.octaves = 6
            noise.apply(emptyArray(), img)
            drawer.shadeStyle = shadeStyle {
                fragmentTransform = """
                    x_fill.rgb = step(fract(x_fill.rgb * 20.0), vec3(0.2));
                """.trimIndent()
            }
            drawer.image(img)
        }
    }
}
