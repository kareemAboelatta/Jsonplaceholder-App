package com.example.androidtaskbosta.presentaion.ui.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtaskbosta.domain.models.Album
import com.example.androidtaskbosta.domain.models.User
import com.example.androidtaskbosta.domain.usecases.FetchAlbumsByUserIdUseCase
import com.example.androidtaskbosta.domain.usecases.FetchUsersUseCase
import com.example.common.utils.CustomError
import com.example.common.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val fetchUsersUseCase: FetchUsersUseCase,
    private val fetchAlbumsByUserIdUseCase: FetchAlbumsByUserIdUseCase
) : ViewModel() {

    private val _userState = mutableStateOf<UIState<User>>(UIState.Empty)
    val userState: State<UIState<User>> get() = _userState

    private val _albumState = mutableStateOf<UIState<List<Album>>>(UIState.Empty)
    val albumState: State<UIState<List<Album>>> get() = _albumState

    init {
        fetchUsers()
    }

    fun fetchUsers() {
        _userState.value = UIState.Loading
        viewModelScope.launch {
            runCatching {
                fetchUsersUseCase.execute()
            }.onSuccess { fetchedUsers ->
                if (fetchedUsers.isEmpty()) {
                    _userState.value = UIState.Empty
                } else {
                    _userState.value = UIState.Success(fetchedUsers.random())
                }
            }.onFailure { throwable ->
                val customError = mapToCustomError(throwable)
                _userState.value = UIState.Error(customError)
            }
        }
    }

    fun fetchAlbums(userId: Int) {
        _albumState.value = UIState.Loading
        viewModelScope.launch {
            runCatching {
                fetchAlbumsByUserIdUseCase.execute(userId)
            }.onSuccess { fetchedAlbums ->
                if (fetchedAlbums.isEmpty()) {
                    _albumState.value = UIState.Empty
                } else {
                    _albumState.value = UIState.Success(fetchedAlbums)
                }
            }.onFailure { throwable ->
                val customError = mapToCustomError(throwable)
                _albumState.value = UIState.Error(customError)
            }
        }
    }


    private fun mapToCustomError(error: Throwable): CustomError {
        return when (error) {
            is IOException -> CustomError.NoInternetError
            is HttpException -> CustomError.ServerError(error.code())
            else -> CustomError.UnknownError
        }
    }


}
