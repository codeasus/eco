package codeasus.projects.bank.eco.feature.crypto.presentation

import android.graphics.RuntimeShader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.withFrameMillis
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
val CRYPTO_SCREEN_BACKGROUND_SHADER = RuntimeShader(
    """
        layout(color) uniform half4 COLOR_GRADIENT_LAYER_0;
        layout(color) uniform half4 COLOR_GRADIENT_LAYER_1;
        layout(color) uniform half4 COLOR_GRADIENT_LAYER_2;

        uniform vec2 u_resolution;
        uniform float u_time;

        vec2 hash(vec2 p);
        float perlin(vec2 p);
        float fbm(vec2 p);

        half4 main(vec2 fragCoord) {
            vec2 normalized_view = fragCoord.xy / u_resolution;

            float perlin_value = fbm(normalized_view);
            float noise_at_time = perlin_value;

            if (noise_at_time >= 0.95) {
                return COLOR_GRADIENT_LAYER_2;
            }
            else if (noise_at_time >= 0.1 && noise_at_time <= 0.95) {
                return COLOR_GRADIENT_LAYER_1;
            }
            else {
                return COLOR_GRADIENT_LAYER_0;
            }
        }      

        float fbm(vec2 p) {
            float value = 0.9;
            float amplitude = 15.0;
            float frequency = 8.0;
            float lacunarity = 1.0;
            float gain = 0.1;

            for (int i = 0; i < 2; i++) {
                value += amplitude * perlin(p * frequency);
                p *= lacunarity;
                amplitude *= gain;
            }
            
            return value;
        }

        float perlin(vec2 p) {
            vec2 i = floor(p);
            vec2 f = fract(p);

            vec2 u = f * f * f * (f * (f * 6.0 - 15.0) + 10.0);

            return mix(mix(dot(hash(i + vec2(0.0, 0.0)), f - vec2(0.0, 0.0)),
                           dot(hash(i + vec2(1.0, 0.0)), f - vec2(1.0, 0.0)), u.x),
                       mix(dot(hash(i + vec2(0.0, 1.0)), f - vec2(0.0, 1.0)),
                           dot(hash(i + vec2(1.0, 1.0)), f - vec2(1.0, 1.0)), u.x), u.y);
        }

        vec2 hash(vec2 p) {
            p = vec2(dot(p, vec2(127.1, 311.7)), dot(p, vec2(269.5, 183.3)));
            return -1.0 + 2.0 * fract(sin(p) * 43758.5453123);
        }
    """)


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
var CRYPTO_SCREEN_BACKGROUND_SHADER_1 = RuntimeShader(
        """
        layout(color) uniform half4 colorGradientLayer0;
        layout(color) uniform half4 colorGradientLayer1;    
        layout(color) uniform half4 colorGradientLayer2;

        uniform vec2 u_resolution;
        uniform float u_time;

        vec2 hash(vec2 p) {
            p = vec2(dot(p, vec2(127.1, 311.7)), dot(p, vec2(269.5, 183.3)));
            return -1.0 + 2.0 * fract(sin(p) * 43758.5453123);
        }

        float perlin(vec2 p) {
            vec2 i = floor(p);
            vec2 f = fract(p);
            
            vec2 u = f * f * f * (f * (f * 6.0 - 15.0) + 10.0);
            
            return mix(mix(dot(hash(i + vec2(0.0, 0.0)), f - vec2(0.0, 0.0)),
                           dot(hash(i + vec2(1.0, 0.0)), f - vec2(1.0, 0.0)), u.x),
                       mix(dot(hash(i + vec2(0.0, 1.0)), f - vec2(0.0, 1.0)),
                           dot(hash(i + vec2(1.0, 1.0)), f - vec2(1.0, 1.0)), u.x), u.y);
        }

        float fbm(vec2 p) {
            float value = 0.9;
            float amplitude = 10.0;
            float frequency = 5.0;
            float lacunarity = 0.0;
            float gain = .1;
        
            for (int i = 0; i < 2; i++) {
                value += amplitude * perlin(p * frequency);
                p *= lacunarity;
                amplitude *= gain;
            }
            return value;
        }

        half4 main(float2 fragCoord) {
            float2 uv = fragCoord.xy / u_resolution.xy;
        

            float noise = fbm(uv);
            
            float aa = 0.005;

            half4 color = colorGradientLayer0;

            float mask1 = smoothstep(0.05, 0.3, noise);
            half4 layer1 = mix(colorGradientLayer1 * 0.9, colorGradientLayer1, mask1);

            float mask2 = smoothstep(0.65, 0.95, noise);
            half4 layer2 = mix(colorGradientLayer2 * 0.9, colorGradientLayer2, mask2);

            color = mix(color, layer1, smoothstep(0.05 - aa, 0.05 + aa,noise));
            color = mix(color, layer2, smoothstep(0.65 - aa, 0.65 + aa,noise));
        
            return color;
        }
    """
    )


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview
@Composable
fun ShaderPreview() {
    val time by produceState(0f) {
        while (true) {
            withFrameMillis { ms ->
                value = ms / 1000f
            }
        }
    }

    Canvas(modifier = Modifier.size(200.dp)) {
        val colorGradientLayer0 = Color(0.851f, 0.8745f, 0.9882f, 1.0f).toArgb()
        val colorGradientLayer1 = Color(0.6588f, 0.6667f, 0.8157f, 1.0f).toArgb()
        val colorGradientLayer2 = Color(0.4275f, 0.4353f, 0.5804f, 1.0f).toArgb()

        CRYPTO_SCREEN_BACKGROUND_SHADER_1.setColorUniform("colorGradientLayer0", colorGradientLayer0)
        CRYPTO_SCREEN_BACKGROUND_SHADER_1.setColorUniform("colorGradientLayer1", colorGradientLayer1)
        CRYPTO_SCREEN_BACKGROUND_SHADER_1.setColorUniform("colorGradientLayer2", colorGradientLayer2)

        CRYPTO_SCREEN_BACKGROUND_SHADER_1.setFloatUniform("u_resolution", size.width, size.height)
        CRYPTO_SCREEN_BACKGROUND_SHADER_1.setFloatUniform("u_time", time)
        drawRect(brush = ShaderBrush(CRYPTO_SCREEN_BACKGROUND_SHADER_1))
    }
}