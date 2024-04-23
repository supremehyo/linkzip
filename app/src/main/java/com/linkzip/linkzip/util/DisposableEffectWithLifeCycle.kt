package com.linkzip.linkzip.util

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

@Composable
fun DisposableEffectWithLifeCycle(
    onStart: () -> Unit,
    onResume: () -> Unit
) {
    val context = LocalContext.current
    //생명주기 상태 변경을 관찰하는 콜백 인터페이스
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    //rememberUpdatedState 값이 변경되는 경우 다시 시작되지 않아야 하는 효과에서
    //값 참조
    val currentOnResume by rememberUpdatedState(onResume)
    val currentOnStart by rememberUpdatedState(onStart)

    //Composable 의 lifecycle 에 맞춰 정리해야하는 리스너나 작업이 있는경우에
    //리스너나 작업을 제거하기 위해 사용되는 Effect 가 DisposableEffect
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    currentOnResume()
                }
                Lifecycle.Event.ON_START ->{
                    currentOnStart()
                }
                else -> {}
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        //DisposableEffect 는 onDispose 를코드 블록의 최종문장으로 포함해야함
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}