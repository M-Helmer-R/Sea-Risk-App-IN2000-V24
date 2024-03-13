package no.uio.ifi.in2000.testgit.ui.Activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.example.Timeseries
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.testgit.data.MainRepository

class ActivityScreenViewModel: ViewModel() {
    private val repository: MainRepository = MainRepository()

    private var _nowCastUIState = MutableStateFlow(NowCastUIState(null))
    var nowCastUIState: StateFlow<NowCastUIState> = _nowCastUIState.asStateFlow()

    init {
        viewModelScope.launch {
            loadNowCast()
        }
    }
    private suspend fun loadNowCast(){
        val nowCastData = repository.fetchNowCast()
        val newUIState = _nowCastUIState.value.copy(nowCastData= nowCastData)
        _nowCastUIState.value = newUIState
    }
}

data class NowCastUIState(
    val nowCastData: Timeseries?
)