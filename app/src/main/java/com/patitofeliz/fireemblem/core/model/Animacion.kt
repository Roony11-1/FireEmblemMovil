import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Animacion(
    var frames: List<Int>,
    var delays: List<Long>,
    var onFrame: ((frameIndex: Int) -> Unit)? = null,
    var onComplete: (() -> Unit)? = null,
    var onUpdateDisplay: ((frameResId: Int) -> Unit)? = null)
{
    private var currentFrame = 0
    private var elapsedTime = 0L
    var isPlaying = false

    private var job: Job? = null

    fun comenzar()
    {
        currentFrame = 0
        elapsedTime = 0
        isPlaying = true

        job = CoroutineScope(Dispatchers.Main).launch {
            while (isPlaying && currentFrame < frames.size)
            {
                onFrame?.invoke(currentFrame)

                onUpdateDisplay?.invoke(frames[currentFrame])

                delay(delays[currentFrame])
                currentFrame++
            }
            isPlaying = false
            onComplete?.invoke()
        }
    }

    fun parar()
    {
        isPlaying = false
        job?.cancel()
        currentFrame = 0
    }
}