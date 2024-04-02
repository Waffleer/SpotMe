package com.example.spotme.viewmodels
//
//import android.icu.text.NumberFormat
//import androidx.lifecycle.ViewModel
//import com.example.spotme.data.Group
//import com.example.spotme.data.Profile
//import com.example.spotme.data.StaticDataSource
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.flow.update
//
///**
// * Stores SpotMeApp's UI state for details screen.
// *
// * @property profiles stores the list of profiles.
// */
//data class GroupsUiState (
//    // Put State Values Here:
//    val groups: List<Group> = listOf(),
//    val currentGroup: Group? = null,
//)
//
//class GroupsViewModel : ViewModel() {
//    private val _uiState = MutableStateFlow(GroupsUiState())
//    val uiState: StateFlow<GroupsUiState> = _uiState.asStateFlow()
//
//    init {
//        initializeUIState()
//    }
//
//    private fun initializeUIState() {
//        //Will get profiles from db with desired information
//        //For now im just taking from the StaticDataSource
//        val groups = StaticDataSource.groups
//        //ToDo Change to Database implementation
//
//
//        _uiState.value = GroupsUiState(
//            groups = groups
//        )
//    }
//    public fun setCurrentGroup(group: Group){
//        _uiState.update {currentState ->
//            currentState.copy(
//                currentGroup = group
//            )
//        }
//    }
//
//}