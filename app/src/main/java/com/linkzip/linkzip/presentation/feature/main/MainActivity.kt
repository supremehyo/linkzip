package com.linkzip.linkzip.presentation.feature.main

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.linkzip.linkzip.R
import com.linkzip.linkzip.data.ToastKind
import com.linkzip.linkzip.data.ToastMessage
import com.linkzip.linkzip.data.ToastType
import com.linkzip.linkzip.data.model.MainScreenState
import com.linkzip.linkzip.presentation.BaseViewModel
import com.linkzip.linkzip.presentation.component.CustomToast
import com.linkzip.linkzip.ui.theme.LinkZipTheme
import com.linkzip.linkzip.util.composableActivityViewModel
import com.linkzip.linkzip.util.navigation.MainNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getAppVersion(applicationContext)
        setContent {
            LinkZipTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = LinkZipTheme.color.white
                ) {
                    val spf = getSharedPreferences("spf", MODE_PRIVATE)
                    val isFirst = viewModel.checkFirstStart(spf)

                    if (!isFirst) {
                        viewModel.updateScreenState(MainScreenState.MAIN.state)
                    } else {
                        viewModel.updateScreenState(MainScreenState.ONBOARDING.state)
                    }
                    MainNavigation()
                    ShowToast()
                }
            }
        }
    }
}

@Composable
fun ShowToast(
    baseViewModel: BaseViewModel = composableActivityViewModel()
) {
    val toastValue by baseViewModel.isShowToastMessage.collectAsStateWithLifecycle()
    var msg by remember { mutableStateOf("") }

    LaunchedEffect(true) {
        baseViewModel.isShowToastMessage.collect {
            // visible 이 true일 경우에만 판별
            if(it.isVisible) {
                // success 가 아닐 경우, 고정 응답
                when(it.isType) {
                    ToastType.WAIT -> {
                        baseViewModel.setToastMessage(ToastKind.None(it.isType, it.isVisible))
                        msg = ToastMessage.WAIT_A_MOMENT
                    }
                    ToastType.WRONG_VALUE -> {
                        baseViewModel.setToastMessage(ToastKind.None(it.isType, it.isVisible))
                        msg = ToastMessage.ENTER_WRONG_VALUE
                    }
                    ToastType.SELECT_LINK -> {
                        baseViewModel.setToastMessage(ToastKind.None(it.isType, it.isVisible))
                        msg = ToastMessage.SELECT_LINK
                    }
                    else -> { }
                }

                // success 인 경우
                when(it) {
                    is ToastKind.AddGroup -> {
                        baseViewModel.setToastMessage(ToastKind.AddGroup(it.type, it.visible))
                        if(it.type == ToastType.SUCCESS) {
                            msg = ToastMessage.ADD_GROUP_SUCCESS
                        }
                    }
                    is ToastKind.UpdateGroup -> {
                        baseViewModel.setToastMessage(ToastKind.UpdateGroup(it.type, it.visible))
                        if(it.type == ToastType.SUCCESS) {
                            msg = ToastMessage.UPDATE_GROUP_SUCCESS
                        }
                    }
                    is ToastKind.DeleteLink -> {
                        baseViewModel.setToastMessage(ToastKind.DeleteLink(it.type, it.visible))
                        if(it.type == ToastType.SUCCESS) {
                            msg = ToastMessage.DELETE_LINK_SUCCESS
                        }
                    }
                    is ToastKind.DeleteGroup -> {
                        baseViewModel.setToastMessage(ToastKind.DeleteGroup(it.type, it.visible))
                        if(it.type == ToastType.SUCCESS) {
                            msg = ToastMessage.DELETE_GROUP_SUCCESS
                        }
                    }
                    is ToastKind.FavoriteLink -> {
                        baseViewModel.setToastMessage(ToastKind.FavoriteLink(it.type, it.visible))
                        if(it.type == ToastType.SUCCESS) {
                            msg = ToastMessage.SET_FAVORITE_SUCCESS
                        }
                    }
                    is ToastKind.UnFavoriteLink -> {
                        baseViewModel.setToastMessage(ToastKind.UnFavoriteLink(it.type, it.visible))
                        if(it.type == ToastType.SUCCESS) {
                            msg = ToastMessage.SET_UNFAVORITE_SUCCESS
                        }
                    }
                    else -> {

                    }
                }


            }
        }
    }

    if(toastValue.isVisible) {
        val customToast = CustomToast(LocalContext.current)
        customToast.MakeText(message = msg, icon = R.drawable.ic_check)

        // 토스트 변수 초기화
        toastValue.isVisible = false

        // 초기화
        baseViewModel.setToastMessage(ToastKind.None(ToastType.SUCCESS, false))
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LinkZipTheme {
        Greeting("Android")
    }
}