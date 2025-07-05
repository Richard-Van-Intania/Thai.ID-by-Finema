package co.finema.thaidotidbyfinema.uis.screens.passcodes

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.keyframes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ShakeController(private val scope: CoroutineScope) {
    val offset = Animatable(0f)

    fun triggerShake() {
        scope.launch {
            offset.animateTo(
             targetValue = 0f,
             animationSpec =
              keyframes {
                  durationMillis = 400
                  -20f at 0
                  20f at 50
                  -20f at 100
                  20f at 150
                  -10f at 200
                  10f at 250
                  -5f at 300
                  5f at 350
                  0f at 400
              },
            )
        }
    }
}
